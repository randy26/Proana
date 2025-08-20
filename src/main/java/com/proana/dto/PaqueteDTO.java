package com.proana.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para representar un Paquete. Contiene información sobre descuentos,
 * precios de lista y finales.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaqueteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador único del paquete */
	private Integer idPaquete;

	/** Descuento por cantidad */
	private Integer dtoCantidad;

	/** Descuento arbitrario */
	private Integer dtoArbitrario;

	/** Descuento aplicado al cliente */
	private Integer dtoCliente;

	/** Descuento en porcentaje */
	private Integer dtoPorcentaje;

	/** Precio de lista antes de descuentos */
	private Double precioLista;

	/** Precio final luego de aplicar descuentos */
	private Double precioFinal;
}