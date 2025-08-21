package com.proana.dto;

import java.io.Serializable;

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

	private String fechaInicioIngreso;

	private String fechaFinIngreso;

	private Boolean autoTerminadasEntre = false;

	private String fechaInicioTerminada;

	private String fechaFinTerminada;

	private String manual;

	private String muestraAMuestra;

	// Solo el ID del presupuesto relacionado
	private Integer idPresupuesto;
}