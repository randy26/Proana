package com.proana.dto;

import java.util.List;

import lombok.Data;

	@Data
	public class PresupuestoDTO {
	    private String tipoPresupuesto;
	    private UnidadNegocioDto unidadNegocio;
	    private boolean bpl;
	    private String nombreCliente;
	    private String fecha;

	    private PublicacionDTO publicacion;
	    private String titulo;
	    private String fechaPresupuesto;
	    private String validezPresupuesto;
	    private String fechaAceptacion;
	    private String duracionContrato;
	    private String fechaInicio;
	    private String ordenCompra;
	    private String referencia;

	    private ClienteDto cliente;
	    private MonedaDto moneda;
	    private DerivanteDTO derivante;
	    private EmpleadoDTO comercial;
	    private EmpleadoDTO responsableContrato;
	    private String revision;
	    private ContactoDTO contacto;
	    private List<ItemDTO> items;
	    private List<ViajeDTO> viajes;
	    private FacturacionDTO facturacion;

}

