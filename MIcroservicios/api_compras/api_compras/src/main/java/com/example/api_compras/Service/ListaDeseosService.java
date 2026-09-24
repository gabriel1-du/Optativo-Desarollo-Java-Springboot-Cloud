package com.example.api_compras.Service;

import java.util.List;

import com.example.api_compras.DTO.ListaDeseosDTO.getListaDeseosDTO;
import com.example.api_compras.DTO.ListaDeseosDTO.postListaDeseosDTO;
import com.example.api_compras.Model.ListaDeseos;

public interface ListaDeseosService {


     // Metodos GET
    public List<getListaDeseosDTO> getAllListaDeseos(); // Trae todos los medios de pago

    public getListaDeseosDTO getListaDeseosById(Long id_lista); // Por id
    // --- FIN GET

    // Metodos POST
    public getListaDeseosDTO saveListaDeseos(postListaDeseosDTO postListaDTO); // Guardar medio de pago

    // Metodos PUT
    public getListaDeseosDTO putListaDeseos(ListaDeseos lista, Long id_lista_deseos); // Actualizar medio de pago

    // Metodos DELETE
    public void deleteListaDeseos(Long id_carrito); // Eliminar medio de pago

    public void deleteListaDeseosByUsuario(Long id_usuario);
    


}
