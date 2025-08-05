package com.proana.dto;

import lombok.Data;

@Data
public class FacturacionDTO {
    private boolean finContrato;
    private EntreFechasDTO entreFechasEntrada;
    private EntreFechasDTO terminadasEntreFechas;
    private String modo;
}
