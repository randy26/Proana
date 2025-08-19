package com.proana.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "determinaciones")
@Getter
@Setter
@NoArgsConstructor
public class Determinacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDeterminacionPresupuesto")
    private Integer idDeterminacionPresupuesto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMuestra", nullable = false)
    private Muestra muestra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDeterminacion", nullable = false)
    private AbmDeterminacion determinacion;

    @Column(length = 100)
    private String especificacion;

    @Column(length = 100)
    private String limite;

    private Boolean informa;

    @Column(length = 100)
    private String condicionantes;

    private Float dtoCantidad;
    private Float dtoArbitrario;
    private Float dtoCliente;
    private Float dtoPorcentaje;
    private Float precioLista;
    private Float precioFinal;

    private Boolean crudos;
    private Boolean derivado;

    @Column(length = 100)
    private String resultado;

    @Column(length = 100)
    private String referencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUnidadDeterminacion")
    private AbmUnidadDeterminacion unidadDeterminacion;

    @Lob
    private String datosCrudos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idFti")
    private AbmFti fti;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEstadoDeterminacion")
    private AbmEstadoDeterminaciones estadoDeterminacion;
}
