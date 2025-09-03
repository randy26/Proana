package com.proana.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.proana.dto.PresupuestoDTO;
import com.proana.dto.PresupuestoMuestraDTO;
import com.proana.dto.PresupuestoResumenDTO;
import com.proana.service.PresupuestoService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/presupuestos")
public class PresupuestoController {

    private static final Logger logger = LoggerFactory.getLogger(EmpleadoController.class);

    @Autowired
    private PresupuestoService service;

	@PostMapping("/nuevoPresupuesto")
    public ResponseEntity<String> nuevoPresupuesto(@RequestBody PresupuestoDTO presupuesto) {
        System.out.println("📥 Presupuesto recibido: " + presupuesto);
        service.guardarPresupuesto(presupuesto);
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
    
    @GetMapping("/{id}")
    public ResponseEntity<PresupuestoDTO> getPresupuestoPorId(@PathVariable Integer id) {
        try {
            PresupuestoDTO presupuesto = service.obtenerPresupuestoPorId(id);

            if (presupuesto == null) {
                logger.warn("Presupuesto con id {} no encontrado", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(null);
            }

            logger.info("Presupuesto {} obtenido correctamente", id);
            return ResponseEntity.ok(presupuesto);
        } catch (Exception e) {
            logger.error("Error al obtener presupuesto con id {}", id, e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al obtener el presupuesto.",
                    e
            );
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarPresupuesto(
            @PathVariable Integer id,
            @RequestBody PresupuestoDTO presupuesto) {
        try {
            logger.info("🔄 Actualizando presupuesto con id {}: {}", id, presupuesto);
            service.actualizarPresupuesto(id, presupuesto);
            return ResponseEntity.ok("Presupuesto actualizado correctamente.");
        } catch (EntityNotFoundException e) {
            logger.warn("Presupuesto con id {} no encontrado", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Presupuesto no encontrado.");
        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al actualizar presupuesto con id {}", id, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error interno al actualizar presupuesto con id {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Ocurrió un error al actualizar el presupuesto.");
        }
    }
    
    /**
     * Endpoint que devuelve la lista de presupuestos con cliente y muestras.
     *
     * @return Lista de {@link PresupuestoMuestraDTO}
     */
    @GetMapping("/presupuestos-muestras")
    public ResponseEntity<List<PresupuestoMuestraDTO>> getPresupuestosConMuestras() {
        try {
            logger.info("🚀 Consultando presupuestos con cliente y muestras...");
            List<PresupuestoMuestraDTO> resultados = service.obtenerPresupuestosConClienteYMuestras();

            logger.info("✅ Se obtuvieron {} registros de presupuestos con muestras", 
                        resultados != null ? resultados.size() : 0);

            return ResponseEntity.ok(resultados);

        } catch (Exception e) {
            logger.error("❌ Error al obtener presupuestos con cliente y muestras", e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al obtener los presupuestos con muestras.",
                    e
            );
        }
    }
    
}
