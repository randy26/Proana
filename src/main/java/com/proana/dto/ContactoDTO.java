package com.proana.dto;
import lombok.*;

/**
 * DTO (Data Transfer Object) que representa los datos del contacto.
 * 
 * Se utiliza para transportar datos entre capas sin exponer la entidad JPA directamente.
 * Incluye el ID del cliente al que está asociado.
 * 
 * @author TuNombre
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactoDTO {

    private Integer idContacto;

    private Integer idCliente;

    private String nombre;

    private String apellido;

    private Integer telefono;

    private String email;

    private String observacion;
}