package com.proana.controller;

import com.proana.dto.ReferenciaNormativaDTO;
import com.proana.service.ReferenciaNormativaService;
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
 * Controlador REST para operaciones relacionadas con los Referencias Normativas.
 * Expone endpoints para consultar información de la base de datos.
 */
@RestController
@RequestMapping("/api/ReferenciaNormativa")
public class ReferenciaNormativaController {

    private static final Logger logger = LoggerFactory.getLogger(ReferenciaNormativaController.class);

    @Autowired
    private ReferenciaNormativaService service;

    /**
     * Endpoint que devuelve la lista de referencias disponibles.
     *
     * @return Lista de {@link ReferenciaNormativaDTO}
     * @throws ResponseStatusException si ocurre un error al recuperar los datos
     */
    @GetMapping("/listarReferencias")
    public List<ReferenciaNormativaDTO> getReferencias() {
        try {
            List<ReferenciaNormativaDTO> Referencias = service.listarReferencias();
            logger.info("Referencias obtenidas correctamente: {} registros", Referencias.size());
            return Referencias;
        } catch (Exception e) {
            logger.error("Error al obtener referencias", e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al obtener los referencias.",
                    e
            );
        }
    }
}
