package com.proana.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ABM_Matrices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matriz {

    @Id
    @Column(name = "id_Matriz")
    private Integer id;

    @Column(name = "Nombre", length = 50)
    private String nombre;
}

