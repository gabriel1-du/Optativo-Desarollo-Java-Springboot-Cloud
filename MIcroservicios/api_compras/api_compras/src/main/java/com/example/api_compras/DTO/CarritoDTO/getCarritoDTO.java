package com.example.api_compras.DTO.CarritoDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
public class getCarritoDTO {

    private Long id_carrito;
    private Long id_usuario;

    //atributo personalizado para nombre de usuario
    private String nombre_usuario;

    //atributos personalizados para la fecha
    private String dia_creacion;
    private String anio_creacion;
    private String hora_creacion ;

    private Float total;
    

}
