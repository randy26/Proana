package com.proana.dto;

import lombok.Data;
import java.util.List;

@Data
public class ItemDTO {
    private int id;
    private String titulo;
    private String referencia;
    private String matriz;
    private int pe;
    private int veces;
    private int frecuencia;
    private int muestras;
    private boolean oos;
    private boolean roos;
    private boolean sCrudos;
    private String paquete;
    private List<DeterminacionDTO> determinaciones;
}
