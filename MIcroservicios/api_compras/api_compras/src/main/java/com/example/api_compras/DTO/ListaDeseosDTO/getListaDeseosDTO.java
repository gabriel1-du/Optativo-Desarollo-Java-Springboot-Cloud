package com.example.api_compras.DTO.ListaDeseosDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
public class getListaDeseosDTO {

    private Long id_lista_deseos;
    private Long id_usuario;

    //atributo personalizado para nombre de usuario
    private String nombre_usuario;

    //atributos personalizados para la fecha
    private String dia_creacion;
    private String anio_creacion;
    private String hora_creacion;
    

}
