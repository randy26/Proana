package com.proana.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbmPaqueteDto {
	private Integer idPaquete;
	private String nombre;
	private BigDecimal precioLista;
}
