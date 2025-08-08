package com.proana.dto;

import lombok.*;

/**
 * Data Transfer Object (DTO) para la entidad {@link Derivante}.
 * 
 * Esta clase se utiliza para transportar datos de derivantes entre capas
 * de la aplicación sin exponer directamente la entidad JPA.
 * 
 * Incluye información básica como el ID, razón social, número de derivante y CUIT.
 * 
 * @author TuNombre
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DerivanteDTO {

    /**
     * Identificador único del derivante.
     */
    private Integer idDerivante;

    /**
     * Razón social del derivante.
     */
    private String razonSocial;

    /**
     * Número de derivante asignado.
     */
    private String numeroDerivante;

    /**
     * CUIT del derivante.
     */
    private String cuit;
}