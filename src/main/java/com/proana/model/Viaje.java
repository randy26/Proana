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
    @Column(name = "id_Viaje")
    private Integer idViaje;

    @ManyToOne
    @JoinColumn(name = "id_Presupuesto", nullable = false)
    private Presupuesto presupuesto;

    @Column(name = "Ubicacion", length = 100)
    private String ubicacion;

    @Column(name = "Costo_Viatico_Por_Viaje")
    private Integer costoViaticoPorViaje;

    @Column(name = "Cantidad_Viajes")
    private Integer cantidadViajes;

    @Column(name = "Traslado")
    private Integer traslado;

    @Column(name = "Alojamiento")
    private Integer alojamiento;

    @Column(name = "Viaticos")
    private Integer viaticos;
}
