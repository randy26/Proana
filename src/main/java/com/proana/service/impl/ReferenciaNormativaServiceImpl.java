package com.proana.service.impl;

import com.proana.dto.ReferenciaNormativaDTO;
import com.proana.model.ReferenciaNormativa;
import com.proana.repository.ReferenciaNormativaRepository;
import com.proana.service.ReferenciaNormativaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Implementación del servicio para operaciones sobre Referencias Normativas.
 * Proporciona métodos para obtener la lista de referencias normativas desde la base de datos.
 */
@Service
public class ReferenciaNormativaServiceImpl implements ReferenciaNormativaService {

    private static final Logger logger = LoggerFactory.getLogger(ReferenciaNormativaServiceImpl.class);

    @Autowired
    private ReferenciaNormativaRepository referenciaRep;

    /**
     * Obtiene la lista completa de Referencias Normativas.
     *
     * @return lista de objetos {@link ReferenciaNormativaDTO}
     * @throws RuntimeException si ocurre un error durante la consulta a la base de datos
     */
    @Override
    public List<ReferenciaNormativaDTO> listarReferencias() {
        try {
            logger.info("Inicio de la obtención de matrices");
            List<ReferenciaNormativa> entidades = this.referenciaRep.findAll();
            List<ReferenciaNormativaDTO> dtos = entidades.stream()
                    .map(ent -> new ReferenciaNormativaDTO(ent.getId(), ent.getNombre()
                            , ent.getObservacion()))
                    .toList();
            logger.info("Se obtuvieron {} Muestreos", dtos.size());
            return dtos;
        } catch (Exception e) {
            logger.error("Error al obtener Referencias Normativas", e);
            throw new RuntimeException("No se pudo obtener las referencias", e);
        }
    }
}
