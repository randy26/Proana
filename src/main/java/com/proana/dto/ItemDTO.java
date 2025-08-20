package com.proana.dto;

import java.util.List;

import lombok.Data;

@Data
public class ItemDTO {
    private int id;
    private String titulo;
    private Integer referencia;
    private Integer matriz;
    private int pe;
    private int veces;
    private int frecuencia;
    private int muestras;
    private boolean oos;
    private boolean roos;
    private boolean sCrudos;
    private String paquete;
    private List<DeterminacionDTO> determinaciones;
    private List<PaqueteDTO> paquetes;
    private List<MuestreoDTO> muestreos;
}
