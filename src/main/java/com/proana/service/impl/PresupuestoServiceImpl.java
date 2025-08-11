package com.proana.service.impl;

import com.proana.dto.ClienteDto;
import com.proana.dto.PresupuestoDTO;
import com.proana.model.Presupuesto;
import com.proana.repository.PresupuestoRepository;
import com.proana.service.PresupuestoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio para operaciones sobre Presupuestos.
 * Proporciona métodos para obtener la lista de Presupuestos desde la base de datos.
 */
@Service
public class PresupuestoServiceImpl implements PresupuestoService {

    private static final Logger logger = LoggerFactory.getLogger(PresupuestoServiceImpl.class);

    @Autowired
    private PresupuestoRepository presupuestoRep;

    /**
     * Obtiene la lista completa de Presupuestos.
     *
     * @return lista de objetos {@link ClienteDto}
     * @throws RuntimeException si ocurre un error durante la consulta a la base de datos
     */
    @Override
    public List<PresupuestoDTO> listarPresupuestos() {
        try {
            logger.info("Inicio de la obtención de la lista de empleados");

            List<Presupuesto> entidades = this.presupuestoRep.findAll();

            List<PresupuestoDTO> dtos = entidades.stream()
                    .map(ent -> PresupuestoDTO.builder()
                            .idPresupuesto(ent.getIdPresupuesto())
                            .nombreCliente(ent.getNombreCliente())
                            .titulo(ent.getTitulo())
                            .fechaPresupuesto(ent.getFechaPresupuesto())
                            .validezPresupuesto(ent.getValidezPresupuesto())
                            .fechaAceptacion(ent.getFechaAceptacion())
                            .duracionContrato(ent.getDuracionDelContrato())
                            .fechaInicio(ent.getFechaInicio())
                            .ordenCompra(ent.getOrdenDeCompra())
                            .referencia(ent.getReferencia())
                            .cliente(ent.getCliente())
                            .moneda(ent.getMoneda())
                            .derivante(ent.getDerivante())
                            //FALTA mapear en el modelo
                            //.nroCliente(ent.getNroDeCliente())
                            //.nroClienteDerivante(ent.getNroDeClienteDerivante())
                            .comercial(ent.getComercial())
                            .responsableContrato(ent.getResponsableContrato())
                            .contacto(ent.getContacto())
                            .revision(ent.getRevision())
                            .emailContacto(ent.getEmailContacto())
                            .telefonoContacto(ent.getTelefonoContacto())
                            //FALTA
                            //.idRolEmpleado(ent.getRolEmpleado() != null ? ent.getRolEmpleado().getIdRolEmpleado() : null)
                            .build()
                    )
                    .toList();

            logger.info("Se obtuvieron {} presupuestos", dtos.size());
            return dtos;
        } catch (Exception e) {
            logger.error("Error al obtener presupuestos", e);
            throw new RuntimeException("No se pudo obtener la lista de presupuestos", e);
        }
    }
}