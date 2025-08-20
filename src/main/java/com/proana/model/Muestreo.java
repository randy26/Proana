package com.proana.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Entidad que representa un muestreo realizado sobre una muestra.
 * Cada muestreo contiene información sobre la ubicación,
 * la fecha estimada, cantidad mínima requerida, unidad de medida,
 * muestreadores, consumibles utilizados y el precio del muestreo.
 */
@Entity
@Table(name = "muestreos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Muestreo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMuestreo")
    private Integer idMuestreo;

    /**
     * Relación muchos-a-uno con la muestra a la que pertenece este muestreo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMuestra", nullable = false)
    private Muestra muestra;

    @Column(name = "ubicacion", length = 100)
    private String ubicacion;

    @Column(name = "fechaEstimada")
    private LocalDate fechaEstimada;

    @Column(name = "cantidadMinima")
    private Integer cantidadMinima;

    /**
     * Relación con la unidad de determinación.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidad")
    private AbmUnidadDeterminacion unidad;

    @Column(name = "muestreadores", length = 255)
    private String muestreadores;

    @Column(name = "tiempoTotal")
    private Integer tiempoTotal;

    @Column(name = "consumibles", length = 255)
    private String consumibles;

    @Column(name = "precioMuestreo")
    private Integer precioMuestreo;
}