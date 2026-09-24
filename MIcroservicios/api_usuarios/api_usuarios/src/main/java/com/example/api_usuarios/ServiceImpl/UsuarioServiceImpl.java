package com.example.api_usuarios.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.api_usuarios.DTO.usuarioDTO.UsuarioDTOMapper;
import com.example.api_usuarios.DTO.usuarioDTO.getUsuarioDTO;
import com.example.api_usuarios.DTO.usuarioDTO.getUsuarioDTOAdmin;
import com.example.api_usuarios.DTO.usuarioDTO.postUsuarioDTO;
import com.example.api_usuarios.DTO.usuarioDTO.putUsuarioDTO;
import com.example.api_usuarios.DTO.usuarioDTO.putUsuarioDTOAdmin;
import com.example.api_usuarios.Model.Comuna;
import com.example.api_usuarios.Model.Region;
import com.example.api_usuarios.Model.Usuario;
import com.example.api_usuarios.RestClient.CrearCarritoDTO;
import com.example.api_usuarios.Repository.RegionRepository;
import com.example.api_usuarios.Repository.UsuarioRepository;
import com.example.api_usuarios.Repository.ComunaRepository;
import com.example.api_usuarios.RestClient.RestClientConfig;
import com.example.api_usuarios.RestClient.CrearListaDeDeseosDTO;

import com.example.api_usuarios.Service.UsuarioService;

@Service 
public class UsuarioServiceImpl implements UsuarioService {

    // Inyeccion de repositorios y dependencias
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private UsuarioDTOMapper usuarioDTOMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired 
    private ComunaRepository comunaRepostiory;

    @Autowired
    private RestClientConfig rest;


    // metodos GET
    @Override
    public List<getUsuarioDTO> getAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<getUsuarioDTO> usuariosDTO = new ArrayList<>();

        for (Usuario u : usuarios) {
            usuariosDTO.add(usuarioDTOMapper.toGetUsuarioDTO(u));
        }
        return usuariosDTO;
    }

    @Override
    public List<getUsuarioDTOAdmin> getAllUsuariosAdmin() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<getUsuarioDTOAdmin> usuariosAdminDTO = new ArrayList<>();

        for (Usuario u : usuarios) {
            usuariosAdminDTO.add(usuarioDTOMapper.toGetUsuarioDTOAdmin(u));
        }
        return usuariosAdminDTO;
    }

    @Override
    public getUsuarioDTO getUsuarioById(Long id_usuario) {
        Usuario usuario = usuarioRepository.findById(id_usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id_usuario));

        return usuarioDTOMapper.toGetUsuarioDTO(usuario);
    }

    @Override
    public getUsuarioDTOAdmin getUsuarioByIdAdmin(Long id_usuario) {
        Usuario usuario = usuarioRepository.findById(id_usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id_usuario));

        return usuarioDTOMapper.toGetUsuarioDTOAdmin(usuario);
    }
    // --- FIN GET


    // metodos POST
    @Override
    public getUsuarioDTO saveUsuario(postUsuarioDTO postUsuarioDTO) {
        // Validar que la region exista
        Region region = regionRepository.findById(postUsuarioDTO.getId_region())
                .orElseThrow(() -> new RuntimeException("Region no encontrada con id: " + postUsuarioDTO.getId_region()));

        Comuna comuna = comunaRepostiory.findById(postUsuarioDTO.getId_comuna())
            .orElseThrow(() -> new RuntimeException("Comuna no encontrada con id: " + postUsuarioDTO.getId_comuna()));


        // Mapear DTO a Entidad aplicando cifrado de contrasena
        Usuario usuario = usuarioDTOMapper.toEntity(postUsuarioDTO, region, comuna);

        // Guardar entidad localmente en la base de datos de usuarios
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // --- INICIO DE COMUNICACIÓN CON API_COMPRAS ---
        try {
            // Empaquetar el ID autogenerado
            CrearCarritoDTO dtoCarrito = new CrearCarritoDTO(usuarioGuardado.getId_usuario());

            // Enviar la petición POST
            rest.comprasRestClient().post()
                    .uri("/") // Se concatena a la baseUrl: http://localhost:8082/api/carritosApi/
                    .body(dtoCarrito)
                    .retrieve()
                    .toBodilessEntity(); // Ejecuta la petición sin mapear un cuerpo de respuesta complejo

        } catch (Exception e) {
            // Bloque catch vital: si api_compras está apagado o falla, capturamos el error
            // para que no detenga el flujo y el usuario sí se devuelva exitosamente.
            System.err.println("Advertencia: Usuario creado, pero falló la creación del carrito. Detalle: " + e.getMessage());
        }

        //----Metodo para creacion de lista de deseos
        try {
            // Empaquetar el ID autogenerado
            CrearListaDeDeseosDTO dtolista = new CrearListaDeDeseosDTO(usuarioGuardado.getId_usuario());

            // Enviar la petición POST
            rest.listaDeseosRestClient().post()
                    .uri("/") // Se concatena a la baseUrl: http://localhost:8082/api/carritosApi/
                    .body(dtolista)
                    .retrieve()
                    .toBodilessEntity(); // Ejecuta la petición sin mapear un cuerpo de respuesta complejo

        } catch (Exception e) {
            // Bloque catch vital: si api_compras está apagado o falla, capturamos el error
            // para que no detenga el flujo y el usuario sí se devuelva exitosamente.
            System.err.println("Advertencia: Usuario creado, pero falló la creación de la lista de deseos. Detalle: " + e.getMessage());
        }
        // --- FIN DE COMUNICACIÓN ---

        // Retornar DTO sin password
        return usuarioDTOMapper.toGetUsuarioDTO(usuarioGuardado);
    }
    

    // metodos PUT
    @Override
    public getUsuarioDTO putUsuario(putUsuarioDTO putUsuarioDTO, Long id_usuario) {
        Usuario usuario_existente = usuarioRepository.findById(id_usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id_usuario));

        Region region = regionRepository.findById(putUsuarioDTO.getId_region())
                .orElseThrow(() -> new RuntimeException("Region no encontrada con id: " + putUsuarioDTO.getId_region()));

        // Actualizacion de atributos
        usuario_existente.setP_nombre(putUsuarioDTO.getP_nombre());
        usuario_existente.setS_nombre(putUsuarioDTO.getS_nombre());
        usuario_existente.setP_apellido(putUsuarioDTO.getP_apellido());
        usuario_existente.setS_apellido(putUsuarioDTO.getS_apellido());
        usuario_existente.setCorreo_elec(putUsuarioDTO.getCorreo_elec());
        usuario_existente.setNum_telefono(putUsuarioDTO.getNum_telefono());

        // Si se envia nueva contrasena, se encripta
        if (putUsuarioDTO.getContrasena() != null && !putUsuarioDTO.getContrasena().isEmpty()) {
            usuario_existente.setContrasena(passwordEncoder.encode(putUsuarioDTO.getContrasena()));
        }

        usuario_existente.setRegion_usuario(region);

        Usuario usuarioActualizado = usuarioRepository.save(usuario_existente);
        return usuarioDTOMapper.toGetUsuarioDTO(usuarioActualizado);
    }

    @Override
    public getUsuarioDTOAdmin putUsuarioAdmin(putUsuarioDTOAdmin putUsuarioDTOAdmin, Long id_usuario) {
        Usuario usuario_existente = usuarioRepository.findById(id_usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id_usuario));

        Region region = regionRepository.findById(putUsuarioDTOAdmin.getId_region())
                .orElseThrow(() -> new RuntimeException("Region no encontrada con id: " + putUsuarioDTOAdmin.getId_region()));

        // Actualizacion de atributos
        usuario_existente.setP_nombre(putUsuarioDTOAdmin.getP_nombre());
        usuario_existente.setS_nombre(putUsuarioDTOAdmin.getS_nombre());
        usuario_existente.setP_apellido(putUsuarioDTOAdmin.getP_apellido());
        usuario_existente.setS_apellido(putUsuarioDTOAdmin.getS_apellido());
        usuario_existente.setCorreo_elec(putUsuarioDTOAdmin.getCorreo_elec());
        usuario_existente.setNum_telefono(putUsuarioDTOAdmin.getNum_telefono());

        if (putUsuarioDTOAdmin.getContrasena() != null && !putUsuarioDTOAdmin.getContrasena().isEmpty()) {
            usuario_existente.setContrasena(passwordEncoder.encode(putUsuarioDTOAdmin.getContrasena()));
        }

        if (putUsuarioDTOAdmin.getPermiso_admin() != null) {
            usuario_existente.setPermiso_admin(putUsuarioDTOAdmin.getPermiso_admin());
        }

        usuario_existente.setRegion_usuario(region);

        Usuario usuarioActualizado = usuarioRepository.save(usuario_existente);
        return usuarioDTOMapper.toGetUsuarioDTOAdmin(usuarioActualizado);
    }


    // metodos DELETE
    @Override
    public void deleteUsuario(Long id_usuario) {
        Usuario usuario_eliminado = usuarioRepository.findById(id_usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id_usuario));


        try {

            rest.comprasRestClient().delete()
                    .uri("/usuario/{id_usuario}", id_usuario)
                    .retrieve()
                    .toBodilessEntity(); // Envía la petición sin esperar respuesta compleja

        } catch (Exception e) {
            // Manejo de contingencia: si el servicio de compras falla o está apagado
            System.err.println("Advertencia: No se pudo eliminar el carrito remoto en api_compras: " + e.getMessage());
        }

        
        usuarioRepository.delete(usuario_eliminado);
    }

}
