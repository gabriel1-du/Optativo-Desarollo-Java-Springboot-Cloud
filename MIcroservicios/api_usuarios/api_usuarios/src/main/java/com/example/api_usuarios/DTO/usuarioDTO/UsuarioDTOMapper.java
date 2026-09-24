package com.example.api_usuarios.DTO.usuarioDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.api_usuarios.Model.Comuna;
import com.example.api_usuarios.Model.Region;
import com.example.api_usuarios.Model.Usuario;

@Component 
public class UsuarioDTOMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Metodo auxiliar para formatear y unificar el RUT (ej: 21765994-9)
    private String construirRut(String rutCuerpo, String rutDv) {
        if (rutCuerpo == null || rutDv == null) {
            return null;
        }
        return rutCuerpo + "-" + rutDv;
    }

    // Convertir de Entidad a GetUsuarioDTO (NO muestra contrasena)
    public getUsuarioDTO toGetUsuarioDTO(Usuario usuario) {
        if (usuario == null) return null;

        getUsuarioDTO dto = new getUsuarioDTO();
        dto.setId_usuario(usuario.getId_usuario());
        // Asumiendo que tu entidad Usuario tiene getRut_cuerpo() y getRut_dv() o getRut()
        dto.setRut(construirRut(usuario.getRut_cuerpo(), usuario.getRut_dv()));
        dto.setP_nombre(usuario.getP_nombre());
        dto.setS_nombre(usuario.getS_nombre());
        dto.setP_apellido(usuario.getP_apellido());
        dto.setS_apellido(usuario.getS_apellido());
        dto.setCorreo_elec(usuario.getCorreo_elec());
        dto.setNum_telefono(usuario.getNum_telefono());
        dto.setPermiso_admin(usuario.getPermiso_admin());

        if (usuario.getRegion_usuario() != null) {
            dto.setId_region(usuario.getRegion_usuario().getId_region());
        }

        if (usuario.getComuna_usuario() != null) {
            dto.setId_comuna(usuario.getComuna_usuario().getId_comuna());
        }

        return dto;
    }

    // Convertir de Entidad a GetUsuarioDTOAdmin (SI muestra contrasena)
    public getUsuarioDTOAdmin toGetUsuarioDTOAdmin(Usuario usuario) {
        if (usuario == null) return null;

        getUsuarioDTOAdmin dto = new getUsuarioDTOAdmin();
        dto.setId_usuario(usuario.getId_usuario());
        dto.setRut(construirRut(usuario.getRut_cuerpo(), usuario.getRut_dv()));
        dto.setP_nombre(usuario.getP_nombre());
        dto.setS_nombre(usuario.getS_nombre());
        dto.setP_apellido(usuario.getP_apellido());
        dto.setS_apellido(usuario.getS_apellido());
        dto.setCorreo_elec(usuario.getCorreo_elec());
        dto.setNum_telefono(usuario.getNum_telefono());
        dto.setContrasena(usuario.getContrasena()); // Incluye password
        dto.setPermiso_admin(usuario.getPermiso_admin());

        if (usuario.getRegion_usuario() != null) {
            dto.setId_region(usuario.getRegion_usuario().getId_region());
        }

        return dto;
    }

    // Convertir de PostUsuarioDTO a Entidad Usuario con encriptacion alfanumerica
    public Usuario toEntity(postUsuarioDTO dto , Region region, Comuna comuna) {
        if (dto == null) return null;

        Usuario usuario = new Usuario();
        usuario.setRut_cuerpo(dto.getRut_cuerpo());
        usuario.setRut_dv(dto.getRut_dv());
        usuario.setP_nombre(dto.getP_nombre());
        usuario.setS_nombre(dto.getS_nombre());
        usuario.setP_apellido(dto.getP_apellido());
        usuario.setS_apellido(dto.getS_apellido());
        usuario.setCorreo_elec(dto.getCorreo_elec());
        usuario.setNum_telefono(dto.getNum_telefono());

        // Encriptar contrasena con BCrypt (alfanumerico hash seguro)
        if (dto.getContrasena() != null && !dto.getContrasena().isEmpty()) {
            usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        }

        usuario.setPermiso_admin(dto.getPermiso_admin() != null ? dto.getPermiso_admin() : false);
        usuario.setRegion_usuario(region);
        usuario.setComuna_usuario(comuna);

        return usuario;
    }
}


