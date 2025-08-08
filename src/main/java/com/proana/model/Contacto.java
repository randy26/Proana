package com.proana.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa un contacto asociado a un cliente.
 * 
 * Corresponde a la tabla {@code abm_contactos} en la base de datos.
 * Contiene los datos personales y de contacto de una persona vinculada a un cliente.
 * 
 * Relación: muchos contactos pueden estar asociados a un único cliente (ManyToOne).
 * 
 * @author TuNombre
 */
@Entity
@Table(name = "abm_contactos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contacto {

    /**
     * Identificador único del contacto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idContacto")
    private Integer idContacto;

    /**
     * Cliente al que pertenece el contacto.
     */
    @ManyToOne
    @JoinColumn(name = "idCliente", referencedColumnName = "idCliente")
    private Cliente cliente;

    /**
     * Nombre del contacto.
     */
    @Column(name = "nombre", length = 50)
    private String nombre;

    /**
     * Apellido del contacto.
     */
    @Column(name = "apellido", length = 50)
    private String apellido;

    /**
     * Teléfono del contacto.
     */
    @Column(name = "telefono")
    private Integer telefono;

    /**
     * Correo electrónico del contacto.
     */
    @Column(name = "email", length = 100)
    private String email;

    /**
     * Observaciones adicionales sobre el contacto.
     */
    @Column(name = "observacion", length = 255)
    private String observacion;
}
