package com.proana.dto;
import lombok.*;

/**
 * DTO (Data Transfer Object) para representar los datos del empleado.
 * 
 * Se utiliza para transferir información entre capas de forma desacoplada de la entidad JPA.
 * Puede incluir o no el nombre del rol dependiendo de la necesidad del cliente.
 * 
 * @author TuNombre
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpleadoDTO {

    private Integer idEmpleado;

    private String nombre;

    private String apellido;

    private Integer telefono;

    private String email;

    private String sectores;

    private Integer idRolEmpleado;
}
