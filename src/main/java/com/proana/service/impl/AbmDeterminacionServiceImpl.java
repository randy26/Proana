package com.proana.service.impl;

import com.proana.dto.AbmDeterminacionDTO;
import com.proana.model.AbmDeterminacion;
import com.proana.repository.AbmDeterminacionRepository;
import com.proana.service.AbmDeterminacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio para operaciones sobre ABM Determinaciones.
 * Proporciona métodos para obtener la lista de abm determinaciones desde la base de datos.
 */
@Service
public class AbmDeterminacionServiceImpl implements AbmDeterminacionService {

    private static final Logger logger = LoggerFactory.getLogger(AbmDeterminacionServiceImpl.class);

    @Autowired
    private AbmDeterminacionRepository abmDetRep;

    /**
     * Obtiene la lista completa de ABM Determinaciones.
     *
     * @return lista de objetos {@link AbmDeterminacionDTO}
     * @throws RuntimeException si ocurre un error durante la consulta a la base de datos
     */
    @Override
    public List<AbmDeterminacionDTO> listarAbmDeterminacion() {
        try {
            logger.info("Inicio de la obtención de abm determinaciones");
            List<AbmDeterminacion> entidades = this.abmDetRep.findAll();
            List<AbmDeterminacionDTO> dtos = entidades.stream()
                    .map(ent -> new AbmDeterminacionDTO(ent.getIdDeterminacion(), ent.getNombreUnico(),
                            ent.getNombre(), ent.getMetodo(), ent.getMatriz().getId(),
                            ent.getUnidadPorDefecto().getIdUnidadDeterminacion(), ent.getLimiteDeCuantificacion()
                            , ent.getLimiteDeDeteccion(), ent.getIncertidumbre()
                            , ent.getPrecioLocal(), ent.getMonedaLocal().getIdMoneda()
                            , ent.getPrecioExtranjero(), ent.getMonedaExtranjera().getIdMoneda()))
                    .toList();
            logger.info("Se obtuvieron {} ABM Determinaciones", dtos.size());
            return dtos;
        } catch (Exception e) {
            logger.error("Error al obtener Abm determinaciones", e);
            throw new RuntimeException("No se pudo obtener las abm determinaciones", e);
        }
    }
}
