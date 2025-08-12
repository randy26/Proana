package com.proana.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.proana.dto.PresupuestoDTO;
import com.proana.dto.PresupuestoResumenDTO;
import com.proana.service.PresupuestoService;

@RestController
@RequestMapping("/api/presupuestos")
public class PresupuestoController {

    private static final Logger logger = LoggerFactory.getLogger(EmpleadoController.class);

    @Autowired
    private PresupuestoService service;

	@PostMapping("/nuevoPresupuesto")
    public ResponseEntity<String> nuevoPresupuesto(@RequestBody PresupuestoDTO presupuesto) {
        System.out.println("📥 Presupuesto recibido: " + presupuesto);
        return ResponseEntity.ok("Presupuesto recibido correctamente.");
    }

    /**
     * Endpoint que devuelve la lista de presupuestos disponibles.
     *
     * @return Lista de {@link PresupuestoDTO}
     * @throws ResponseStatusException si ocurre un error al recuperar los datos
     */
    @GetMapping("/presupuestos")
    public List<PresupuestoResumenDTO> getPresupuestos() {
        try {
            List<PresupuestoResumenDTO> presupuestos = service.listarPresupuestos();

            logger.info("presupuestos obtenidas correctamente: {} registros", presupuestos.size());
            return presupuestos;
        } catch (Exception e) {
            logger.error("Error al obtener presupuestos", e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al obtener los presupuestos.",
                    e
            );
        }
    }
}
