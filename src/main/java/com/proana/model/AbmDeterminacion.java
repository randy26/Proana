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

@Entity
@Table(name = "abm_determinaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AbmDeterminacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idDeterminacion")
	private Integer idDeterminacion;

	@Column(name = "nombreUnico", length = 100)
	private String nombreUnico;

	@Column(name = "nombre", length = 50)
	private String nombre;

	@Column(name = "metodo", length = 100)
	private String metodo;

	// --- Relaciones ---
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idMatriz")
	private Matriz matriz;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unidadPorDefecto")
	private AbmUnidadDeterminacion unidadPorDefecto;

	@Column(name = "limiteDeCuantificacion", length = 50)
	private String limiteDeCuantificacion;

	@Column(name = "limiteDeDeteccion", length = 50)
	private String limiteDeDeteccion;

	@Column(name = "incertidumbre", length = 50)
	private String incertidumbre;

	@Column(name = "precioLocal")
	private Integer precioLocal;

	
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "idMonedaLocal") 
	 private Moneda monedaLocal;
	 @Column(name = "precioExtranjero") 
	 private Integer precioExtranjero;
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "idMonedaExtranjera") 
	 private Moneda monedaExtranjera;

}
