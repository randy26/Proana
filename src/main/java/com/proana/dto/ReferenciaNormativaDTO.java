package com.proana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReferenciaNormativaDTO {

    private Integer idReferenciaNormativa;
    private String nombre;
    private String observacion;
}
