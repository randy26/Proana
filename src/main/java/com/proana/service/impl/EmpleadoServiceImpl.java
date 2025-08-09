package com.proana.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proana.dto.EmpleadoDTO;
import com.proana.model.Empleado;
import com.proana.repository.EmpleadoRepository;
import com.proana.service.EmpleadoService;

/**
 * Implementación del servicio para operaciones sobre Empleados.
 * Proporciona métodos para obtener la lista de Empleados desde la base de datos.
 */
@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private static final Logger logger = LoggerFactory.getLogger(EmpleadoServiceImpl.class);

    @Autowired
    private EmpleadoRepository empleadoRep;

    /**
     * Obtiene la lista completa de Empleados.
     * 
     * @return lista de objetos {@link EmpleadoDTO}
     * @throws RuntimeException si ocurre un error durante la consulta a la base de datos
     */
    @Override
    public List<EmpleadoDTO> listarEmpleados() {
        try {
            logger.info("Inicio de la obtención de la lista de empleados");

            List<Empleado> entidades = this.empleadoRep.findByRolEmpleadoIdRolEmpleado(1);

            List<EmpleadoDTO> dtos = entidades.stream()
                .map(ent -> EmpleadoDTO.builder()
                    .idEmpleado(ent.getIdEmpleado())
                    .nombre(ent.getNombre())
                    .apellido(ent.getApellido())
                    .telefono(ent.getTelefono())
                    .email(ent.getEmail())
                    .sectores(ent.getSectores())
                    .idRolEmpleado(ent.getRolEmpleado() != null ? ent.getRolEmpleado().getIdRolEmpleado() : null)
                    .build()
                )
                .toList();

            logger.info("Se obtuvieron {} empleados", dtos.size());
            return dtos;
        } catch (Exception e) {
            logger.error("Error al obtener empleados", e);
            throw new RuntimeException("No se pudo obtener la lista de empleados", e);
        }
    }


}
