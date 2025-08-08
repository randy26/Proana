package com.proana.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa la tabla ABM_MONEDAS.
 * 
 * Contiene la definición de la moneda utilizada en el sistema,
 * incluyendo su identificador y nombre (por ejemplo, "ARS").
 */
@Entity
@Table(name = "abm_monedas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Moneda {

    /**
     * Identificador único de la moneda.
     * Se corresponde con la columna 'id_Moneda' en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMoneda")
    private Integer idMoneda;

    /**
     * Nombre de la moneda (ejemplo: "ARS").
     */
    @Column(name = "nombre")
    private String nombre;
}
