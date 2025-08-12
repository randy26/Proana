package com.proana.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa el estado de un presupuesto.
 * Contiene un identificador único y un indicador booleano que
 * indica el estado del presupuesto.
 */
@Entity
@Table(name = "abm_estados_presupuestos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoPresupuesto {

    /**
     * Identificador único del estado del presupuesto.
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEstadoPresupuesto")
    private Integer idEstadoPresupuesto;

    @Column(name = "estadoPresupuesto")
    private Boolean estadoPresupuesto;
}

