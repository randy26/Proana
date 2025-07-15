package com.proana.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Representa un menú disponible en la aplicación.
 */
@Entity
@Table(name = "menu")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    private String url;

    private String icono;

    @ManyToMany(mappedBy = "menus")
    private Set<Usuario> usuarios;
}
