package com.proana.controller;

import com.proana.dto.MatrizDTO;
import com.proana.service.MatrizService;
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
 * Controlador REST para operaciones relacionadas con los Matrices.
 * Expone endpoints para consultar información de la base de datos.
 */
@RestController
@RequestMapping("/api/Matriz")
public class MatrizController {

    private static final Logger logger = LoggerFactory.getLogger(MatrizController.class);

    @Autowired
    private MatrizService service;

    /**
     * Endpoint que devuelve la lista de matrices disponibles.
     *
     * @return Lista de {@link MatrizDTO}
     * @throws ResponseStatusException si ocurre un error al recuperar los datos
     */
    @GetMapping("/listarMatrices")
    public List<MatrizDTO> getMatrices() {
        try {
            List<MatrizDTO> Matrices = service.listarMatrices();
            logger.info("Matrices obtenidas correctamente: {} registros", Matrices.size());
            return Matrices;
        } catch (Exception e) {
            logger.error("Error al obtener matrices", e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al obtener los matrices.",
                    e
            );
        }
    }
}
