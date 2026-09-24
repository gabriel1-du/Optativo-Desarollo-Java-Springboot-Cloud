package com.example.api_usuarios.DTO.usuarioDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
public class getUsuarioDTO {

    private Long id_usuario;
    private String rut;
    private String p_nombre;
    private String s_nombre;
    private String p_apellido;
    private String s_apellido;
    private String correo_elec;
    private String num_telefono;
    private Boolean permiso_admin;
    private Long id_region;
    private Long id_comuna;
}
