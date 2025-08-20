package com.proana.model;

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
@Table(name = "paquetes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paquete {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPaquetePresupuesto;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMuestra", nullable = false)
    private Muestra muestra;
	
	 @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPaquete")
	private AbmPaquete abmPaquete;

	private Float Dto_Cantidad;
	private Float Dto_Arbitrario;
	private Float Dto_Cliente;
	private Float Dto_Porcentaje;
	private Float Precio_Lista;
	private Float Precio_Final;

	
}
