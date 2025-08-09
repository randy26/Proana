package com.proana.dto;

import lombok.Data;
import java.util.List;

@Data
public class PresupuestoDTO {
    private Integer idPresupuesto;
    private String tipoPresupuesto;
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
    private String cliente;
    private String moneda;
    private String derivante;
    private String nroCliente;
    private String nroClienteDerivante;
    private String comercial;
    private String responsableContrato;
    private String revision;
    private String contacto;
    private String emailContacto;
    private String telefonoContacto;

    private List<ItemDTO> items;
    private List<ViajeDTO> viajes;
    private FacturacionDTO facturacion;
}

