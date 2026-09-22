package com.example.api_compras.Model;

import java.time.LocalDateTime;

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
@Table(name = "PRODUCTOS_LISTA_DESEOS")
public class ProductoListaDeseos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_prod_lista_deseos;

    // Relación física con ListaDeseos
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lista_deseos", nullable = false)
    private ListaDeseos listaDeseos;

    // Referencia lógica a ms-productos
    @Column(name = "id_producto", nullable = false)
    private Long id_producto;

    @Column(name = "fecha_agregado", nullable = false)
    private LocalDateTime fecha_agregado;

}
