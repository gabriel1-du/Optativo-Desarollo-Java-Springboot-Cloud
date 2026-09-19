package com.example.api_compras.DTO.RestClientDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
public class UsuarioExternoDTO {

    private Long id_usuario;
    private String p_nombre;
    private String s_nombre;
    private String p_apellido;
    private String s_apellido;
    private String correo_elec;

}
