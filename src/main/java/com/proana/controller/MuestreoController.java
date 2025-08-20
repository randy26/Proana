package com.proana.controller;

import com.proana.dto.MuestreoDTO;
import com.proana.service.MuestreoService;
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
 * Controlador REST para operaciones relacionadas con los Muestreos.
 * Expone endpoints para consultar información de la base de datos.
 */
@RestController
@RequestMapping("/api/Muestreo")
public class MuestreoController {

    private static final Logger logger = LoggerFactory.getLogger(MuestreoController.class);

    @Autowired
    private MuestreoService service;

    /**
     * Endpoint que devuelve la lista de muestreos disponibles.
     *
     * @return Lista de {@link MuestreoDTO}
     * @throws ResponseStatusException si ocurre un error al recuperar los datos
     */
   /* @GetMapping("/listarMuestreos")
    public List<MuestreoDTO> getMuestreos() {
        try {
            List<MuestreoDTO> Muestreos = service.listarMuestreos();
            logger.info("Muestreos obtenidas correctamente: {} registros", Muestreos.size());
            return Muestreos;
        } catch (Exception e) {
            logger.error("Error al obtener muestreos", e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al obtener los muestreos.",
                    e
            );
        }
    }*/
}
