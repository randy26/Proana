package com.proana.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "muestreos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Muestreo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMuestreo")
    private Integer idMuestreo;

    @ManyToOne
    @JoinColumn(name = "idMuestra", nullable = false)
    private Muestra muestra;

    @Column(name = "ubicacion", length = 100)
    private String ubicacion;

    @Column(name = "fechaEstimada")
    private Date fechaEstimada;

    @Column(name = "cantidadMinima")
    private Integer cantidadMinima;

    @Column(name = "unidad")
    private Integer unidad;

    @Column(name = "muestreadores", length = 255)
    private String muestreadores;

    @Column(name = "tiempoTotal")
    private Integer tiempoTotal;

    @Column(name = "consumibles", length = 255)
    private String consumibles;

    @Column(name = "precioMuestreo")
    private Integer precioMuestreo;

}
