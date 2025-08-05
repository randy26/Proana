package com.proana.dto;

import lombok.Data;

@Data
public class PublicacionDTO {
    private boolean autorizacionPrevia;
    private boolean conAutorizacion;
    private boolean automatica;
    private boolean conReferencias;
}

