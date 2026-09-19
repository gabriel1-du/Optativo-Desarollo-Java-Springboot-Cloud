package com.example.api_compras.DTO.CarritoDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.example.api_compras.DTO.RestClientDTO.UsuarioExternoDTO;
import com.example.api_compras.Model.Carrito;

@Component 
public class CarritoDTOMapper {


    public Carrito toEntity(postCarritoDTO dto) {
        if (dto == null) return null;

        Carrito carrito = new Carrito();
        carrito.setId_usuario(dto.getId_usuario());
        carrito.setFecha_creacion(LocalDateTime.now()); // Fecha y hora automática
        carrito.setTotal(BigDecimal.ZERO);             // Total inicial en 0.00

        return carrito;
    }

    public getCarritoDTO toGetCarritoDTO(Carrito carrito, UsuarioExternoDTO usuarioExterno) {
        if (carrito == null) return null;

        getCarritoDTO dto = new getCarritoDTO();
        dto.setId_carrito(carrito.getId_carrito());
        dto.setId_usuario(carrito.getId_usuario());

        // Nombre de usuario obtenido del microservicio externo
        if (usuarioExterno != null) {
            String nombreCompleto = usuarioExterno.getP_nombre() + " " + usuarioExterno.getP_apellido();
            dto.setNombre_usuario(nombreCompleto);
        } else {
            dto.setNombre_usuario("Usuario no disponible");
        }

        // Desglose de la fecha de creación
        if (carrito.getFecha_creacion() != null) {
            DateTimeFormatter formatoDia = DateTimeFormatter.ofPattern("dd/MM");
            DateTimeFormatter formatoAnio = DateTimeFormatter.ofPattern("yyyy");
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

            dto.setDia_creacion(carrito.getFecha_creacion().format(formatoDia));
            dto.setAnio_creacion(carrito.getFecha_creacion().format(formatoAnio));
            dto.setHora_creacion(carrito.getFecha_creacion().format(formatoHora));
        }

        if (carrito.getTotal() != null) {
            dto.setTotal(carrito.getTotal().floatValue());
        }

        return dto;
    }

}
