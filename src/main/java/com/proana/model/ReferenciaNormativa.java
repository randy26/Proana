package com.proana.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ABM_Referencias_Normativas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReferenciaNormativa {

    @Id
    @Column(name = "id_Referencias_Normativas")
    private Integer id;

    @Column(name = "Nombre", length = 100)
    private String nombre;

    @Column(name = "Observacion", length = 255)
    private String observacion;
}
