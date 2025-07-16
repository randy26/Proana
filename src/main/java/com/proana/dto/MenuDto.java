package com.proana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor // <-- Requerido para MapStruct
@Data
@AllArgsConstructor
public class MenuDto {
    private Integer id;
    private String nombre;
    private String url;
    private String icono;
}
