package com.example.api_usuarios.RestClient;

import lombok.Data;


@Data 
public class CrearCarritoDTO {

    private Long id_usuario;

    public CrearCarritoDTO(Long id_usuario) {
        this.id_usuario = id_usuario;
    }


}
