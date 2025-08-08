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
import com.proana.dto.DerivanteDTO;
import com.proana.service.DerivanteService;

/**
 * Controlador REST para operaciones relacionadas con las Unidades de Negocio.
 * Expone endpoints para consultar información de la base de datos.
 */
@RestController
@RequestMapping("/api/derivante")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private DerivanteService service;

    /**
     * Endpoint que devuelve la lista de Clientes disponibles.
     *
     * @return Lista de {@link ClienteDto}
     * @throws ResponseStatusException si ocurre un error al recuperar los datos
     */
    @GetMapping("/derivantes")
    public List<DerivanteDTO> getDerivantes() {
        try {
            List<DerivanteDTO> derivantes = service.listarDerivantes();
            logger.info("derivantes obtenidas correctamente: {} registros", derivantes.size());
            return derivantes;
        } catch (Exception e) {
            logger.error("Error al obtener derivantes", e);
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error al obtener los derivantes.",
                e
            );
        }
    }
}


