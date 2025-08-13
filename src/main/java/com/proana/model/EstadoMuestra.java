package com.proana.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "abm_estados_muestras")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoMuestra {

    @Id
    @Column(name = "idEstadoMuestra")
    private Integer id;

    @Column(name = "nombre", length = 50)
    private String nombre;
}
