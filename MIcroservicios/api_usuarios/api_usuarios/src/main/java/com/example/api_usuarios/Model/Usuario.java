package com.example.api_usuarios.Model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data 
@NoArgsConstructor 
@Table(name = "USUARIOS")
public class Usuario {

    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    //datos personales
    @Column(name = "p_nombre", nullable = false)
    private String p_nombre;

    @Column(name = "s_nombre", nullable = false)
    private String s_nombre;

    @Column (name = "p_apellido", nullable = false)
    private String p_apellido;

    @Column (name = "s_apellido", nullable = false)
    private String s_apellido;

    //datos de contacto
    @Column(name = "correo_elec", nullable = false)
    private String correo_elec;

    @Column (name = "num_telefono", nullable = false)
    private String num_telefono;

    //rut_dv VARCHAR(1) NOT NUll ,
    //rut_cuerpo VARCHAR(9) NOT NULL,

    @Column (name = "rut_dv", nullable = false)
    private String rut_dv;

    @Column (name = "rut_cuerpo", nullable = false)
    private String rut_cuerpo;

    //seguridad
    @Column (name = "contrasena", nullable = false)
    private String contrasena;

    //Permiso de administrador
    @Column(name = "permiso_admin", nullable = false)
    private Boolean permiso_admin;

    //datos de ubicación
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_region", nullable = false)
    private Region region_usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comuna", nullable = false)
    private Comuna comuna_usuario;

}
