package com.proana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa un cliente.
 * 
 * Contiene la información básica del cliente, incluyendo su CUIT, razón social,
 * número de cliente interno y su identificador único.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {

    /**
     * Identificador único del cliente.
     */
    private Integer idCliente;

    /**
     * Razón social del cliente.
     */
    private String razonSocial;

    /**
     * Número de cliente asignado internamente.
     */
    private String numeroCliente;

    /**
     * CUIT del cliente (ejemplo: "20-12345678-9").
     */
    private String cuit;
}
