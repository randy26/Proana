package com.proana.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CondicionFacturacionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idCondicionFacturacion;

	private Boolean autoUltimaMuestra = false;

	private Boolean autoIngresaronEntre = false;

	private LocalDate fechaInicioIngreso;

	private LocalDate fechaFinIngreso;

	private Boolean autoTerminadasEntre = false;

	private LocalDate fechaInicioTerminada;

	private LocalDate fechaFinTerminada;

	private Boolean manual = false;

	private Boolean muestraAMuestra = false;

	// Solo el ID del presupuesto relacionado
	private Integer idPresupuesto;
}