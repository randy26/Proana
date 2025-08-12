package com.proana.dto; // 👈 Usá tu paquete real, no "com.tuempresa"

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PresupuestoResumenDTO {
	private Integer idPresupuesto;
    private String bpl;
    private String titulo;
    private Date fechaPresupuesto;
    private Integer validezDelPresupuesto;
    private Date fechaAceptacion;
    private Integer duracionDelContrato;
    private Date fechaInicio;
    private String ordenDeCompra;
    private String referencia;
    private Long idEstadoPresupuesto;
    private Boolean estadoPresupuesto;
}
