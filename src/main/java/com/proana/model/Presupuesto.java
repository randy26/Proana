package com.proana.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa a un presupuesto dentro del sistema.
 *
 * Corresponde a la tabla {@code presupuestos} en la base de datos.
 * Contiene datos del
 *
 * Relación: un presupuesto tiene una unidad de negocio (OneToOne con UnidadNegocio).
 *
 * @author Eze
 */
@Entity
@Table(name = "presupuestos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Presupuesto {

    /**
     * Identificador único del presupuesto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPresupuesto")
    private Integer idPresupuesto;

    /**
     * Bpl del presupuesto.
     */
    @Column(name = "bpl")
    private Integer bpl;

    /**
     * Titulo del presupuesto.
     */
    @Column(name = "titulo", length = 50)
    private String titulo;

    /**
     * Fecha del presupuesto.
     */
    @Column(name = "fechaPresupuesto")
    private Date fechaPresupuesto;

    /**
     * validezDelPresupuesto del presupuesto.
     */
    @Column(name = "validezDelPresupuesto")
    private Integer validezDelPresupuesto;

    /**
     * Fecha Aceptacion del presupuesto.
     */
    @Column(name = "fechaAceptacion")
    private Date fechaAceptacion;

    /**
     * Duracion del Contrato del presupuesto.
     */
    @Column(name = "duracionDelContrato")
    private Integer duracionDelContrato;

    /**
     * Fecha de Inicio del presupuesto.
     */
    @Column(name = "fechaInicio")
    private Date fechaInicio;

    /**
     * Orden de Compra del presupuesto.
     */
    @Column(name = "ordenDeCompra", length = 50)
    private String ordenDeCompra;

    /**
     * Referencia del presupuesto.
     */
    @Column(name = "referencia", length = 50)
    private String referencia;

    /**
     * Revision del presupuesto.
     */
    @Column(name = "revision")
    private Integer revision;

    /**
     * Email de contacto del presupuesto.
     */
    @Column(name = "emailDeContacto", length = 50)
    private String emailDeContacto;

    /**
     * Telefono de Contacto del presupuesto.
     */
    @Column(name = "telefonoDeContacto", length = 50)
    private String telefonoDeContacto;

    /**
     * Unidad de Negocio del presupuesto.
     */
    @OneToOne
    @JoinColumn(name = "idUnidadNegocio", referencedColumnName = "idUnidadNegocio")
    private UnidadNegocio unidadNegocio;

    /**
     * Cliente del presupuesto.
     */
    @OneToOne
    @JoinColumn(name = "idCliente", referencedColumnName = "idCliente")
    private Cliente cliente;

    /**
     * Moneda del presupuesto.
     */
    @OneToOne
    @JoinColumn(name = "idMoneda", referencedColumnName = "idMoneda")
    private Moneda moneda;

    /**
     * Derivante del presupuesto.
     */
    @OneToOne
    @JoinColumn(name = "idDerivante", referencedColumnName = "idDerivante")
    private Derivante derivante;

    @ManyToOne
    @JoinColumn(name = "idComercial", referencedColumnName = "idEmpleado")
    private Empleado comercial;

    /**
     * Responsable del Contrato del presupuesto.
     */
    @ManyToOne
    @JoinColumn(name = "idResponsableDelContrato", referencedColumnName = "idEmpleado")
    private Empleado responsableContrato;

    /**
     * Contacto del presupuesto.
     */
    @OneToOne
    @JoinColumn(name = "idContacto", referencedColumnName = "idContacto")
    private Contacto contacto;

    @OneToOne
    @JoinColumn(name = "idEstadoPresupuesto")
    private EstadoPresupuesto estadoPresupuesto;
    
    @OneToMany(mappedBy = "presupuesto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Viaje> viajes = new ArrayList<>();
    
    @OneToMany(mappedBy = "presupuesto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Muestra> muestras = new ArrayList<>();
    
    @OneToMany(mappedBy = "presupuesto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CondicionesPublicacion> condicionesPublicacion = new ArrayList<>();

    @OneToMany(mappedBy = "presupuesto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CondicionFacturacion> condicionesFacturacion = new ArrayList<>();
}