package com.proana.service;

import java.util.List;

import com.proana.dto.PresupuestoDTO;
import com.proana.dto.PresupuestoResumenDTO;

public interface PresupuestoService {
    List<PresupuestoResumenDTO> listarPresupuestos();
    void guardarPresupuesto(PresupuestoDTO dto);
}
