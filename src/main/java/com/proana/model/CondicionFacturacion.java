package com.proana.model;

import java.io.Serializable;
import java.time.LocalDate;

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

@Entity
@Table(name = "Condiciones_Facturacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CondicionFacturacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Condicion_Facturacion")
	private Integer idCondicionFacturacion;

	@Column(name = "Auto_Ultima_Muestra", nullable = false)
	private Boolean autoUltimaMuestra = false;

	@Column(name = "Auto_Ingresaron_Entre", nullable = false)
	private Boolean autoIngresaronEntre = false;

	@Column(name = "Fecha_Inicio_Ingreso")
	private LocalDate fechaInicioIngreso;

	@Column(name = "Fecha_Fin_Ingreso")
	private LocalDate fechaFinIngreso;

	@Column(name = "Auto_Terminadas_Entre", nullable = false)
	private Boolean autoTerminadasEntre = false;

	@Column(name = "Fecha_Inicio_Terminada")
	private LocalDate fechaInicioTerminada;

	@Column(name = "Fecha_Fin_Terminada")
	private LocalDate fechaFinTerminada;

	@Column(name = "Manual", nullable = false)
	private Boolean manual = false;

	@Column(name = "Muestra_A_Muestra", nullable = false)
	private Boolean muestraAMuestra = false;

	// Relación con Presupuesto
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPresupuesto", nullable = false)
	private Presupuesto presupuesto;
}