package com.example.api_compras.Model;

import java.math.BigDecimal;
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
@Table(name = "BOLETAS")
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_boleta;

    // Relación física con HistorialCompras
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_historial", nullable = false)
    private HistorialCompras historial;

    // Referencia lógica a ms-usuarios
    @Column(name = "id_usuario", nullable = false)
    private Long id_usuario;

    // Relación física con MedioPago
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medio_pago", nullable = false)
    private MedioPago medioPago;

    @Column(name = "monto_total", nullable = false)
    private BigDecimal monto_total;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDateTime fecha_emision;

    @Column(name = "rut_comprador", nullable = false)
    private String rut_comprador;

    @Column(name = "nombre_comprador", nullable = false)
    private String nombre_comprador;

}
