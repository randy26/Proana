package com.proana.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa un derivante dentro del sistema.
 * 
 * Corresponde a la tabla {@code abm_derivantes} en la base de datos.
 * Contiene información como la razón social, número de derivante y CUIT.
 * 
 * Cada campo está mapeado explícitamente al nombre de columna correspondiente.
 * 
 * @author TuNombre
 */
@Entity
@Table(name = "abm_derivantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Derivante {

    /**
     * Identificador único del derivante.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDerivante")
    private Integer idDerivante;

    /**
     * Razón social del derivante.
     */
    @Column(name = "razonSocial", length = 100)
    private String razonSocial;

    /**
     * Número de derivante asignado.
     */
    @Column(name = "numeroDerivante", length = 50)
    private String numeroDerivante;

    /**
     * CUIT del derivante.
     */
    @Column(name = "cuit", length = 20)
    private String cuit;
}

