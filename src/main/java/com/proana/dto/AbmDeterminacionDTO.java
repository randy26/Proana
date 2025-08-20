package com.proana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbmDeterminacionDTO {

    private Integer idDeterminacion;
    private String nombreUnico;
    private String nombre;
    private String metodo;
    private Integer matriz;
    private Integer unidadPorDefecto;
    private String limiteDeCuantificacion;
    private String limiteDeDeteccion;
    private String incertidumbre;
    private Integer precioLocal;
    private Integer monedaLocal;
    private Integer precioExtranjero;
    private Integer monedaExtranjera;
}
