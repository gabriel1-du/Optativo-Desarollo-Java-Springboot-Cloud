package com.example.api_compras.ServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api_compras.Repository.ListaDeseosRepository;
import com.example.api_compras.Service.ListaDeseosService;
import com.example.api_compras.RestClient.UsuarioClient;
import com.example.api_compras.Model.ListaDeseos;
//imports DTO
import com.example.api_compras.DTO.ListaDeseosDTO.ListaDeseosMapper;
import com.example.api_compras.DTO.ListaDeseosDTO.getListaDeseosDTO;
import com.example.api_compras.DTO.ListaDeseosDTO.postListaDeseosDTO;
import com.example.api_compras.DTO.RestClientDTO.UsuarioExternoDTO;;

@Service 
public class ListaDeseosServiceImpl implements ListaDeseosService {


    @Autowired
    private ListaDeseosRepository listaRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private ListaDeseosMapper listaDTOMapper;

    //Metodos Get
    @Override
    public List<getListaDeseosDTO> getAllListaDeseos() {
        
        List<ListaDeseos> listas = listaRepository.findAll();

        List<getListaDeseosDTO> listaDTO = new ArrayList<>();

        for (ListaDeseos lista : listas) {
            // Consulta de datos de usuario via RestClient por cada carrito
            UsuarioExternoDTO usuarioExterno = usuarioClient.obtenerUsuarioPorId(lista.getId_usuario());
            listaDTO.add(listaDTOMapper.togetListaDeseosoDTO(lista, usuarioExterno));
        }

        return listaDTO;
    }

    @Override
    public getListaDeseosDTO getListaDeseosById(Long id_lista) {

        ListaDeseos lista = listaRepository.findById(id_lista)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con id: " + id_lista));

        // Consulta de datos de usuario via RestClient
        UsuarioExternoDTO usuarioExterno = usuarioClient.obtenerUsuarioPorId(lista.getId_usuario());

        return listaDTOMapper.togetListaDeseosoDTO(lista, usuarioExterno);
    }
    // --- FIN GET

    // Metodo POST
    public getListaDeseosDTO saveListaDeseos(postListaDeseosDTO postListDTO) {
        
        ListaDeseos lista = new ListaDeseos();

        lista.setId_usuario(postListDTO.getId_usuario());
        lista.setFecha_creacion(LocalDateTime.now());
    
        //Guardar lista
        ListaDeseos listaGuardada = listaRepository.save(lista);

        //Ejecutar la petición HTTP a api_usuarios para traer el nombre y apellidos
        UsuarioExternoDTO usuarioExterno = usuarioClient.obtenerUsuarioPorId(listaGuardada.getId_usuario());

        // Retornar el DTO usando el Mapper (combina la entidad local y los datos del RestClient)
        return listaDTOMapper.togetListaDeseosoDTO(listaGuardada, usuarioExterno);
    }

    // Metodo Put
    @Override
    public getListaDeseosDTO putListaDeseos(ListaDeseos lista, Long id_lista) {

        ListaDeseos lista_existente = listaRepository.findById(id_lista)
                .orElseThrow(() -> new RuntimeException("Lista de deseos no encontrada con el id: " + id_lista));

        // Actualizar atributos editables
        lista_existente.setId_usuario(lista.getId_usuario());

        ListaDeseos listaActualizada = listaRepository.save(lista_existente);

        // Llamada a api_usuarios para reflejar los datos del usuario actual
        UsuarioExternoDTO usuarioExterno = usuarioClient.obtenerUsuarioPorId(listaActualizada.getId_usuario());

        return listaDTOMapper.togetListaDeseosoDTO(listaActualizada, usuarioExterno);
    }

    // metodos DELETE
    @Override
    public void deleteListaDeseos(Long id_lista_deseos) {

        ListaDeseos lista_eliminada = listaRepository.findById(id_lista_deseos)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con el id: " + id_lista_deseos));

        listaRepository.delete(lista_eliminada);
    }

    @Override
    public void deleteListaDeseosByUsuario(Long id_usuario) {
        ListaDeseos lista = listaRepository.findById_lista(id_usuario)
                .orElse(null); // Si no tiene carrito, no lanzamos error crítico

        if (lista != null) {
            listaRepository.delete(lista);
        }
    }





}
