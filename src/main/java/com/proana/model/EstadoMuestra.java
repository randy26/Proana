package com.proana.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ABM_Estados_Muestras")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoMuestra {

    @Id
    @Column(name = "id_Estado_Muestra")
    private Integer id;

    @Column(name = "Nombre", length = 50)
    private String nombre;
}
