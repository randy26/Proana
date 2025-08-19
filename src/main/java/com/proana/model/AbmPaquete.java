package com.proana.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "abm_paquetes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbmPaquete {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPaquete;

	private String nombre;
	@Column(name ="precio_lista")
	private BigDecimal precioLista;
}
