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

/**
 * Entidad que representa a un empleado dentro del sistema.
 * 
 * Corresponde a la tabla {@code abm_empleados} en la base de datos.
 * Contiene datos personales, contacto y la relación con el rol del empleado.
 * 
 * Relación: muchos empleados pueden tener un mismo rol (ManyToOne con RolEmpleado).
 * 
 * @author TuNombre
 */
@Entity
@Table(name = "abm_empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empleado {

    /**
     * Identificador único del empleado.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmpleado")
    private Integer idEmpleado;

    /**
     * Nombre del empleado.
     */
    @Column(name = "nombre", length = 50)
    private String nombre;

    /**
     * Apellido del empleado.
     */
    @Column(name = "apellido", length = 50)
    private String apellido;

    /**
     * Teléfono del empleado.
     */
    @Column(name = "telefono")
    private Integer telefono;

    /**
     * Correo electrónico del empleado.
     */
    @Column(name = "email", length = 100)
    private String email;

    /**
     * Sectores en los que trabaja el empleado.
     */
    @Column(name = "sectores", length = 100)
    private String sectores;

    /**
     * Rol asignado al empleado.
     */
    @ManyToOne
    @JoinColumn(name = "idRolEmpleado", referencedColumnName = "idRolEmpleado")
    private RolEmpleado rolEmpleado;
}
