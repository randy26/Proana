package com.proana.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un cliente en la base de datos.
 * Mapea la tabla 'abm_clientes'.
 */
@Entity
@Table(name = "abm_clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    /**
     * Identificador único del cliente. Es la clave primaria y se genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCliente")
    private Integer idCliente;

    /**
     * Razón social del cliente.
     */
    @Column(name = "razonSocial", length = 100)
    private String razonSocial;

    /**
     * Número de cliente asignado internamente.
     */
    @Column(name = "numeroCliente", length = 50)
    private String numeroCliente;

    /**
     * CUIT del cliente (Clave Única de Identificación Tributaria).
     */
    @Column(name = "cuit", length = 20)
    private String cuit;
}

