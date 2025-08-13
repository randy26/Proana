package com.proana.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proana.dto.ClienteDto;
import com.proana.dto.ContactoDTO;
import com.proana.dto.DerivanteDTO;
import com.proana.dto.MonedaDto;
import com.proana.dto.PresupuestoDTO;
import com.proana.dto.PresupuestoResumenDTO;
import com.proana.exception.EntidadNoEncontradaException;
import com.proana.model.Cliente;
import com.proana.model.Contacto;
import com.proana.model.Derivante;
import com.proana.model.Empleado;
import com.proana.model.EstadoPresupuesto;
import com.proana.model.Moneda;
import com.proana.model.Presupuesto;
import com.proana.model.UnidadNegocio;
import com.proana.repository.PresupuestoRepository;
import com.proana.repository.UnidadNegocioRepository;
import com.proana.service.PresupuestoService;
import com.proana.utils.ProanaUtil;
import com.proana.utils.TypeConverter;

/**
 * Implementación del servicio para operaciones sobre Presupuestos.
 */
@Service
public class PresupuestoServiceImpl implements PresupuestoService {

    private static final Logger logger = LoggerFactory.getLogger(PresupuestoServiceImpl.class);

    @Autowired
    private PresupuestoRepository presupuestoRep;
    @Autowired
    private UnidadNegocioRepository unidadNegocioRep;

    /**
     * Obtiene la lista completa de Presupuestos.
     *
     * @return lista de objetos {@link PresupuestoResumenDTO}
     * @throws RuntimeException si ocurre un error durante la consulta a la base de datos
     */
    @Override
    public List<PresupuestoResumenDTO> listarPresupuestos() {
        try {
            List<Object[]> rows = presupuestoRep.findPresupuestosResumen();

            return rows.stream()
                    .map(row -> new PresupuestoResumenDTO(
                            TypeConverter.toInteger(row[0]),
                            TypeConverter.toStringSafe(row[1]),
                            TypeConverter.toStringSafe(row[2]),
                            TypeConverter.toDate(row[3]),
                            TypeConverter.toInteger(row[4]),
                            TypeConverter.toDate(row[5]),
                            TypeConverter.toInteger(row[6]),
                            TypeConverter.toDate(row[7]),
                            TypeConverter.toStringSafe(row[8]),
                            TypeConverter.toStringSafe(row[9]),
                            TypeConverter.toLong(row[10]),
                            TypeConverter.toBoolean(row[11])
                    ))
                    .toList();
        } catch (Exception e) {
            logger.error("Error al obtener presupuestos", e);
            throw new RuntimeException("No se pudo obtener la lista de presupuestos", e);
        }
    }

    /**
     * Guarda un nuevo presupuesto a partir de un DTO.
     *
     * @param dto Datos del presupuesto a guardar.
     * @throws IllegalArgumentException       Si algún dato obligatorio es inválido o faltante.
     * @throws EntidadNoEncontradaException   Si alguna entidad referenciada no existe en la base.
     */
    @Override
    public void guardarPresupuesto(final PresupuestoDTO dto) {
        Presupuesto presupuesto = new Presupuesto();

        presupuesto.setUnidadNegocio(mapUnidadNegocio(dto.getUnidadNegocio()));
        if(dto.isBpl()) {
        	presupuesto.setBpl(1);
        }else {
        	presupuesto.setBpl(0);
        }
        presupuesto.setTitulo(dto.getTitulo());
        presupuesto.setFechaPresupuesto(ProanaUtil.parseDateSql(dto.getFechaPresupuesto()));
        presupuesto.setValidezDelPresupuesto(parseInteger(dto.getValidezPresupuesto()));
        presupuesto.setFechaAceptacion(ProanaUtil.parseDateSql(dto.getFechaAceptacion()));
        presupuesto.setDuracionDelContrato(parseInteger(dto.getDuracionContrato()));
        presupuesto.setFechaInicio(ProanaUtil.parseDateSql(dto.getFechaInicio()));
        presupuesto.setOrdenDeCompra(dto.getOrdenCompra());
        presupuesto.setReferencia(dto.getReferencia());

        presupuesto.setCliente(mapCliente(dto.getCliente()));
        presupuesto.setMoneda(mapMoneda(dto.getMoneda()));
        presupuesto.setDerivante(mapDerivante(dto.getDerivante()));

        presupuesto.setComercial(mapEmpleado(dto.getComercial(), "Comercial"));
        presupuesto.setResponsableContrato(mapEmpleado(dto.getResponsableContrato(), "Responsable del Contrato"));

        presupuesto.setRevision(parseInteger(dto.getRevision()));

        presupuesto.setContacto(mapContacto(dto.getContacto()));

        EstadoPresupuesto estado = new EstadoPresupuesto();
        if(dto.getFacturacion().isModo()) {
        estado.setIdEstadoPresupuesto(1);
        }else {
        	estado.setIdEstadoPresupuesto(0);
        }
        presupuesto.setEstadoPresupuesto(estado);

        // Guardar presupuesto en DB:
        presupuestoRep.save(presupuesto);
    }

    private UnidadNegocio mapUnidadNegocio(Integer unidadNegocioIdStr) {
        if (unidadNegocioIdStr == null) {
            throw new IllegalArgumentException("Unidad de negocio es obligatoria");
        }
        Integer id;
        try {
            id = Integer.valueOf(unidadNegocioIdStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Unidad de negocio inválida: " + unidadNegocioIdStr);
        }
        return unidadNegocioRep.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Unidad de negocio no encontrada con id: " + id));
    }

    private Cliente mapCliente(final ClienteDto clienteDto) {
        if (clienteDto == null || clienteDto.getIdCliente() == null) {
            throw new IllegalArgumentException("Cliente es obligatorio");
        }
        Cliente cliente = new Cliente();
        cliente.setIdCliente(clienteDto.getIdCliente());
        return cliente;
    }

    private Moneda mapMoneda(final MonedaDto monedaDto) {
        Moneda moneda = new Moneda();
        if (monedaDto != null && monedaDto.getIdMoneda() != null) {
            moneda.setIdMoneda(monedaDto.getIdMoneda());
        }
        return moneda;
    }

    private Derivante mapDerivante(final DerivanteDTO derivanteDto) {
        if (derivanteDto == null || derivanteDto.getIdDerivante() == null) {
            return null;
        }
        Derivante derivante = new Derivante();
        derivante.setIdDerivante(derivanteDto.getIdDerivante());
        return derivante;
    }

    private Empleado mapEmpleado(final String empleadoIdStr, final String campo) {
        if (empleadoIdStr == null || empleadoIdStr.isEmpty()) {
            return null;
        }
        try {
            Empleado empleado = new Empleado();
            empleado.setIdEmpleado(Integer.valueOf(empleadoIdStr));
            return empleado;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(campo + " inválido: " + empleadoIdStr);
        }
    }

    private Contacto mapContacto(final ContactoDTO contactoDto) {
        if (contactoDto == null || contactoDto.getIdContacto() == null) {
            return null;
        }
        Contacto contacto = new Contacto();
        contacto.setIdContacto(contactoDto.getIdContacto());
        return contacto;
    }

    private Integer parseInteger(final String valor) {
        if (valor == null || valor.isEmpty()) {
            return null;
        }
        try {
            return Integer.valueOf(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor numérico inválido: " + valor);
        }
    }
}
