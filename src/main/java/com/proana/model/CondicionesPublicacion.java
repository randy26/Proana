package com.proana.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Condiciones_Publicacion")
public class CondicionesPublicacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Condicion_Publicacion")
	private Integer idCondicionPublicacion;

	// Relación con Presupuesto
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPresupuesto", nullable = false)
	private Presupuesto presupuesto;

	@Column(name = "Autorizacion_Comercial_Previa_DT", nullable = false)
	private Boolean autorizacionComercialPreviaDT = false;

	@Column(name = "Autorizacion_Comercial", nullable = false)
	private Boolean autorizacionComercial = false;

	@Column(name = "Automaticamente_Firma_DT", nullable = false)
	private Boolean automaticamenteFirmaDT = false;

	@Column(name = "Se_Informa_Con_Referencias", nullable = false)
	private Boolean seInformaConReferencias = false;
}
