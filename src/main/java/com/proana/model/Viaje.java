package com.proana.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "viajes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idViaje")
    private Integer idViaje;

    @ManyToOne
    @JoinColumn(name = "idPresupuesto", nullable = false)
    private Presupuesto presupuesto;

    @Column(name = "ubicacion", length = 100)
    private String ubicacion;

    @Column(name = "costoViaticoPorViaje")
    private Integer costoViaticoPorViaje;

    @Column(name = "cantidadViajes")
    private Integer cantidadViajes;

    @Column(name = "traslado")
    private Integer traslado;

    @Column(name = "alojamiento")
    private Integer alojamiento;

    @Column(name = "viaticos")
    private Integer viaticos;
}
