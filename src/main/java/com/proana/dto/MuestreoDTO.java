package com.proana.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MuestreoDTO {

	
	private Integer idMuestreo;

    /** Identificador de la muestra asociada */
    private Integer idMuestra;

    private String ubicacion;

    private LocalDate fechaEstimada;

    private Integer cantidadMinima;

    /** Identificador de la unidad de determinación */
    private Integer idUnidadDeterminacion;

    private String muestreadores;

    private Integer tiempoTotal;

    private String consumibles;

    private Integer precioMuestreo;
   /* private Integer idMuestreo;
    private ItemDTO muestra;
    private String ubicacion;
    private Date fechaEstimada;
    private Integer cantidadMinima;
    private Integer unidad;
    private String muestreadores;
    private Integer tiempoTotal;
    private String consumibles;
    private Integer precioMuestreo;*/

}
