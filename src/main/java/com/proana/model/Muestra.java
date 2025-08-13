package com.proana.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "muestras")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Muestra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMuestra")
    private Integer idMuestra;

    @ManyToOne
    @JoinColumn(name = "idPresupuesto", nullable = false)
    private Presupuesto presupuesto;

    @Column(name = "titulo", length = 100)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "idReferenciaNormativa", referencedColumnName = "idReferenciaNormativa")
    private ReferenciaNormativa referenciaNormativa;

    @ManyToOne
    @JoinColumn(name = "idMatriz", referencedColumnName = "idMatriz")
    private Matriz matriz;

    @Column(name = "pe")
    private Integer pe;

    @Column(name = "cantidadVeces")
    private Integer cantidadVeces;

    @Column(name = "frecuencia")
    private Integer frecuencia;

    @Column(name = "cantidadMuestras")
    private Integer cantidadMuestras;

    @Column(name = "oos")
    private Boolean oos;

    @Column(name = "roos")
    private Boolean roos;

    @Column(name = "sCrudos")
    private Boolean sCrudos;

    @ManyToOne
    @JoinColumn(name = "idEstadoMuestra", referencedColumnName = "idEstadoMuestra")
    private EstadoMuestra estadoMuestra;
}
