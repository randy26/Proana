package com.proana.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "abm_referencias_normativas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReferenciaNormativa {

    @Id
    @Column(name = "idReferenciaNormativa")
    private Integer id;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "observacion", length = 255)
    private String observacion;
}
