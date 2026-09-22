package com.example.api_compras.ServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api_compras.DTO.CarritoDTO.CarritoDTOMapper;
import com.example.api_compras.DTO.CarritoDTO.getCarritoDTO;
import com.example.api_compras.DTO.CarritoDTO.postCarritoDTO;
import com.example.api_compras.DTO.RestClientDTO.UsuarioExternoDTO;
import com.example.api_compras.Model.Carrito;
import com.example.api_compras.Repository.CarritoRepository;
import com.example.api_compras.RestClient.UsuarioClient;
import com.example.api_compras.Service.CarritoService;

@Service 
public class CarritoServiceImpl implements CarritoService {


    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private CarritoDTOMapper carritoDTOMapper;

    // metodos GET
    @Override
    public List<getCarritoDTO> getAllCarrito() {
        List<Carrito> carritos = carritoRepository.findAll();
        List<getCarritoDTO> listaDTO = new ArrayList<>();

        for (Carrito carrito : carritos) {
            // Consulta de datos de usuario via RestClient por cada carrito
            UsuarioExternoDTO usuarioExterno = usuarioClient.obtenerUsuarioPorId(carrito.getId_usuario());
            listaDTO.add(carritoDTOMapper.toGetCarritoDTO(carrito, usuarioExterno));
        }

        return listaDTO;
    }

    @Override
    public getCarritoDTO getCarritoById(Long id_carrito) {
        Carrito carrito = carritoRepository.findById(id_carrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con id: " + id_carrito));

        // Consulta de datos de usuario via RestClient
        UsuarioExternoDTO usuarioExterno = usuarioClient.obtenerUsuarioPorId(carrito.getId_usuario());

        return carritoDTOMapper.toGetCarritoDTO(carrito, usuarioExterno);
    }
    // --- FIN GET

    // Metodo POST
    public getCarritoDTO saveCarrito(postCarritoDTO postCarritoDTO) {
        
        
        Carrito carrito = new Carrito();
        carrito.setId_usuario(postCarritoDTO.getId_usuario());
        carrito.setFecha_creacion(LocalDateTime.now());
        carrito.setTotal(BigDecimal.ZERO);

        // 2. Guardar el carrito en la base de datos local (MySQL compras)
        Carrito carritoGuardado = carritoRepository.save(carrito);

        // 3. Ejecutar la petición HTTP a api_usuarios para traer el nombre y apellidos
        UsuarioExternoDTO usuarioExterno = usuarioClient.obtenerUsuarioPorId(carritoGuardado.getId_usuario());

        // 4. Retornar el DTO usando el Mapper (combina la entidad local y los datos del RestClient)
        return carritoDTOMapper.toGetCarritoDTO(carritoGuardado, usuarioExterno);
    }

    // metodos PUT
    @Override
    public getCarritoDTO putCarrito(Carrito carrito, Long id_carrito) {
        Carrito carrito_existente = carritoRepository.findById(id_carrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con el id: " + id_carrito));

        // Actualizar atributos editables
        carrito_existente.setTotal(carrito.getTotal());
        carrito_existente.setId_usuario(carrito.getId_usuario());

        Carrito carritoActualizado = carritoRepository.save(carrito_existente);

        // Llamada a api_usuarios para reflejar los datos del usuario actual
        UsuarioExternoDTO usuarioExterno = usuarioClient.obtenerUsuarioPorId(carritoActualizado.getId_usuario());

        return carritoDTOMapper.toGetCarritoDTO(carritoActualizado, usuarioExterno);
    }


    // metodos DELETE
    @Override
    public void deleteCarrito(Long id_carrito) {
        Carrito carrito_eliminado = carritoRepository.findById(id_carrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con el id: " + id_carrito));

        carritoRepository.delete(carrito_eliminado);
    }

}
