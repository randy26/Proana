package com.proana.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "muestras")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Muestra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Muestra")
    private Integer idMuestra;

    @ManyToOne
    @JoinColumn(name = "id_Presupuesto", nullable = false)
    private Presupuesto presupuesto;

    @Column(name = "Titulo", length = 100)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "id_Referencia_normativa", referencedColumnName = "id_Referencias_Normativas")
    private ReferenciaNormativa referenciaNormativa;

    @ManyToOne
    @JoinColumn(name = "id_Matriz", referencedColumnName = "id_Matriz")
    private Matriz matriz;

    @Column(name = "P_E")
    private Integer pe;

    @Column(name = "Cantidad_veces")
    private Integer cantidadVeces;

    @Column(name = "Frecuencia")
    private Integer frecuencia;

    @Column(name = "Cantidad_muestras")
    private Integer cantidadMuestras;

    @Column(name = "OOS")
    private Boolean oos;

    @Column(name = "ROOS")
    private Boolean roos;

    @Column(name = "S_Crudos")
    private Boolean sCrudos;

    @ManyToOne
    @JoinColumn(name = "id_Estado_muestra", referencedColumnName = "id_Estado_Muestra")
    private EstadoMuestra estadoMuestra;
}
