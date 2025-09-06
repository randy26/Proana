package com.proana.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "abm_ubicaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AbmUbicaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Ubicacion")
    private Integer id_Ubicacion;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;
}
