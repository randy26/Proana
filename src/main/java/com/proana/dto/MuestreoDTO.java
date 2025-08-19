package com.proana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MuestreoDTO {

    private Integer idMuestreo;
    private ItemDTO muestra;
    private String ubicacion;
    private Date fechaEstimada;
    private Integer cantidadMinima;
    private Integer unidad;
    private String muestreadores;
    private Integer tiempoTotal;
    private String consumibles;
    private Integer precioMuestreo;

}
