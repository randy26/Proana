package com.proana.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
    private int ubicacion;
    private String nombreUbicacion;

	public ItemDTO(int id, String titulo, int muestras, boolean oos,
                   int ubicacion, String nombreUbicacion, List<DeterminacionDTO> determinaciones) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.muestras = muestras;
		this.oos = oos;
        this.determinaciones = determinaciones;
        this.ubicacion = ubicacion;
        this.nombreUbicacion = nombreUbicacion;
	}
    
    
}
