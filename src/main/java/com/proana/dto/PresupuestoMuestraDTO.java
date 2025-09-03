package com.proana.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresupuestoMuestraDTO {
	Integer idPresupuesto;
	String titulo;
	String cliente;
	List<ItemDTO> muestras;
	
	
}
