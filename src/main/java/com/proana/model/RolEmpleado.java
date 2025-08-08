package com.proana.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa un rol asignado a los empleados.
 * 
 * Corresponde a la tabla {@code abm_rol_empleado} en la base de datos.
 * Cada empleado puede estar asociado a un rol.
 * 
 * Esta entidad puede ser referenciada desde {@link Empleado}.
 * 
 * @author TuNombre
 */
@Entity
@Table(name = "abm_rol_empleado")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolEmpleado {

    /**
     * Identificador único del rol.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRolEmpleado")
    private Integer idRolEmpleado;

    /**
     * Nombre del rol.
     */
    @Column(name = "nombre", length = 50)
    private String nombre;
}
