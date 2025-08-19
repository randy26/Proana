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

import com.proana.dto.MonedaDto;
import com.proana.dto.UnidadNegocioDTO;
import com.proana.service.MonedaService;

/**
 * Controlador REST para operaciones relacionadas con las Unidades de Negocio.
 * Expone endpoints para consultar información de la base de datos.
 */
@RestController
@RequestMapping("/api/Moneda")
public class MonedaController {

    private static final Logger logger = LoggerFactory.getLogger(MonedaController.class);

    @Autowired
    private MonedaService service;

    /**
     * Endpoint que devuelve la lista de unidades de negocio disponibles.
     *
     * @return Lista de {@link UnidadNegocioDTO}
     * @throws ResponseStatusException si ocurre un error al recuperar los datos
     */
    @GetMapping("/listMonedas")
    public List<MonedaDto> getUnidades() {
        try {
            List<MonedaDto> monedas = service.listarMonedas();
            logger.info("monedas obtenidas correctamente: {} registros", monedas.size());
            return monedas;
        } catch (Exception e) {
            logger.error("Error al obtener unidades de negocio", e);
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error al obtener las unidades de negocio.",
                e
            );
        }
    }
}


