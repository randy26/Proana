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
@Table(name = "abm_estado_determinaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AbmEstadoDeterminaciones {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEstadoDeterminacion")
	private Integer idEstadoDeterminacion;

	@Column(name = "nombre", length = 50, nullable = false)
	private String nombre;
}
