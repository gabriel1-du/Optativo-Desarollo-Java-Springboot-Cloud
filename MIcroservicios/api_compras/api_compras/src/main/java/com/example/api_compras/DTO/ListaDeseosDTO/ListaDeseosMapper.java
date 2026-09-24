package com.example.api_compras.DTO.ListaDeseosDTO;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;


import com.example.api_compras.Model.ListaDeseos;
import com.example.api_compras.DTO.RestClientDTO.UsuarioExternoDTO;

@Component
public class ListaDeseosMapper {


    public ListaDeseos toEntity(postListaDeseosDTO dto) {
        if (dto == null) return null;

        ListaDeseos lista = new ListaDeseos();
        lista.setId_usuario(dto.getId_usuario());
        lista.setFecha_creacion(LocalDateTime.now()); // Fecha y hora automática
                

        return lista;
    }

    public getListaDeseosDTO togetListaDeseosoDTO(ListaDeseos listaDeseos, UsuarioExternoDTO usuarioExterno) {

        if (listaDeseos == null) return null;

        getListaDeseosDTO dto = new getListaDeseosDTO();
        dto.setId_lista_deseos(listaDeseos.getId_lista_deseos());
        dto.setId_usuario(listaDeseos.getId_usuario());

        // Nombre de usuario obtenido del microservicio externo
        if (usuarioExterno != null) {
            String nombreCompleto = usuarioExterno.getP_nombre() + " " + usuarioExterno.getP_apellido();
            dto.setNombre_usuario(nombreCompleto);
        } else {
            dto.setNombre_usuario("Usuario no disponible");
        }

        // Desglose de la fecha de creación
        if (listaDeseos.getFecha_creacion() != null) {
            DateTimeFormatter formatoDia = DateTimeFormatter.ofPattern("dd/MM");
            DateTimeFormatter formatoAnio = DateTimeFormatter.ofPattern("yyyy");
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

            dto.setDia_creacion(listaDeseos.getFecha_creacion().format(formatoDia));
            dto.setAnio_creacion(listaDeseos.getFecha_creacion().format(formatoAnio));
            dto.setHora_creacion(listaDeseos.getFecha_creacion().format(formatoHora));
        }

        return dto;
    }

    

}
