package com.proana.controller;

import com.proana.dto.AbmDeterminacionDTO;
import com.proana.service.AbmDeterminacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Controlador REST para operaciones relacionadas con los ABM Determinaciones.
 * Expone endpoints para consultar información de la base de datos.
 */
@RestController
@RequestMapping("/api/AbmDeterminacion")
public class AbmDeterminacionController {

    private static final Logger logger = LoggerFactory.getLogger(AbmDeterminacionController.class);

    @Autowired
    private AbmDeterminacionService service;

    /**
     * Endpoint que devuelve la lista de abm determinaciones disponibles.
     *
     * @return Lista de {@link AbmDeterminacionDTO}
     * @throws ResponseStatusException si ocurre un error al recuperar los datos
     */
    @GetMapping("/listarAbmDeterminaciones")
    public List<AbmDeterminacionDTO> getAbmDeterminaciones() {
        try {
            List<AbmDeterminacionDTO> AbmDeterminaciones = service.listarAbmDeterminacion();
            logger.info("ABM Determinaciones obtenidas correctamente: {} registros", AbmDeterminaciones.size());
            return AbmDeterminaciones;
        } catch (Exception e) {
            logger.error("Error al obtener abm determinaciones", e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al obtener los abm determinaciones.",
                    e
            );
        }
    }
}
