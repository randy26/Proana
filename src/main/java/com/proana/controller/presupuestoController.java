package com.proana.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proana.dto.PresupuestoDTO;

@RestController
@RequestMapping("/api/presupuestos")
public class presupuestoController {

	@PostMapping("/nuevoPresupuesto")
    public ResponseEntity<String> nuevoPresupuesto(@RequestBody PresupuestoDTO presupuesto) {
        System.out.println("📥 Presupuesto recibido: " + presupuesto);
        return ResponseEntity.ok("Presupuesto recibido correctamente.");
    }	
}
