package com.proana.dto;

import lombok.Data;

@Data
public class DeterminacionDTO {
    private String nombre;
    private String metodo;
    private boolean informa;
    private boolean condicionantes;
    private String dtoQ;
    private String dtoC;
    private String dtoP;
    private String lista;
    private String final_;
    private boolean sCrudos;
}

