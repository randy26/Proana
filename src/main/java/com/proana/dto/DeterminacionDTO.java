package com.proana.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeterminacionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idDeterminacionPresupuesto;
	private Integer idMuestra;
	private Integer idDeterminacion;
	private String especificacion;
	private String limite;
	private Boolean informa;
	private String condicionantes;
	private Float dtoCantidad;
	private Float dtoArbitrario;
	private Float dtoCliente;
	private Float dtoPorcentaje;
	private Float precioLista;
	private Float precioFinal;
	private Boolean crudos;
	private Boolean derivado;
	private String resultado;
	private String referencia;
	private Integer idUnidadDeterminacion;
	private Boolean datosCrudos;
	private Integer idFti;
	private Integer idEstadoDeterminacion;

}
