package com.proana.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "abm_unidad_determinacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AbmUnidadDeterminacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUnidadDeterminacion")
	private Integer idUnidadDeterminacion;

	@Column(name = "valor", length = 50, nullable = false)
	private String valor;
}
