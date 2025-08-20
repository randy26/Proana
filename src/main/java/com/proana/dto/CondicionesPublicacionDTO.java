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
public class CondicionesPublicacionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idCondicionPublicacion;

	private Integer idPresupuesto;

	private Boolean autorizacionComercialPreviaDT = false;

	private Boolean autorizacionComercial = false;

	private Boolean automaticamenteFirmaDT = false;

	private Boolean seInformaConReferencias = false;
}
