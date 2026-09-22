package com.example.api_compras.Model;

import java.time.LocalDateTime;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@Table(name = "LISTAS_DESEOS")
public class ListaDeseos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_lista_deseos;

    // Referencia lógica a ms-usuarios
    @Column(name = "id_usuario", nullable = false)
    private Long id_usuario;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fecha_creacion;

    // Relación bidireccional física
    @OneToMany(mappedBy = "listaDeseos", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoListaDeseos> productosLista;

}
