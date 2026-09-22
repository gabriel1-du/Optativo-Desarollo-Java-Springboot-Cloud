package com.example.api_compras.Service;

import java.util.List;


import com.example.api_compras.DTO.CarritoDTO.getCarritoDTO;
import com.example.api_compras.DTO.CarritoDTO.postCarritoDTO;
import com.example.api_compras.Model.Carrito;


public interface CarritoService {

    // Metodos GET
    public List<getCarritoDTO> getAllCarrito(); // Trae todos los medios de pago

    public getCarritoDTO getCarritoById(Long id_carrito); // Por id
    // --- FIN GET

    // Metodos POST
    public getCarritoDTO saveCarrito(postCarritoDTO postCarritoDTO); // Guardar medio de pago

    // Metodos PUT
    public getCarritoDTO putCarrito(Carrito carrito, Long id_carrito); // Actualizar medio de pago

    // Metodos DELETE
    public void deleteCarrito(Long id_carrito); // Eliminar medio de pago
    

} 
