package com.proana.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.proana.dto.ClienteDto;
import com.proana.dto.EmpleadoDTO;
import com.proana.service.EmpleadoService;

/**
 * Controlador REST para operaciones relacionadas con las Unidades de Negocio.
 * Expone endpoints para consultar información de la base de datos.
 */
@RestController
@RequestMapping("/api/empleado")
public class EmpleadoController {

    private static final Logger logger = LoggerFactory.getLogger(EmpleadoController.class);

    @Autowired
    private EmpleadoService service;

    /**
     * Endpoint que devuelve la lista de empleados disponibles.
     *
     * @return Lista de {@link ClienteDto}
     * @throws ResponseStatusException si ocurre un error al recuperar los datos
     */
    @GetMapping("/empleados")
    public List<EmpleadoDTO> getEmpleados() {
        try {
            List<EmpleadoDTO> empleados = service.listarEmpleados();
            logger.info("derivantes obtenidas correctamente: {} registros", empleados.size());
            return empleados;
        } catch (Exception e) {
            logger.error("Error al obtener empleados", e);
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error al obtener los empleados.",
                e
            );
        }
    }
}


