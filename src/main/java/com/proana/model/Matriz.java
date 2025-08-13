package com.proana.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "abm_matrices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matriz {

    @Id
    @Column(name = "idMatriz")
    private Integer id;

    @Column(name = "nombre", length = 50)
    private String nombre;
}

