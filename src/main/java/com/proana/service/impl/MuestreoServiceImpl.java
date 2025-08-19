package com.proana.service.impl;

import com.proana.dto.ItemDTO;
import com.proana.dto.MonedaDto;
import com.proana.dto.MuestreoDTO;
import com.proana.model.Muestra;
import com.proana.model.Muestreo;
import com.proana.repository.MuestreoRepository;
import com.proana.service.MuestreoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio para operaciones sobre Muestreos.
 * Proporciona métodos para obtener la lista de muestreos desde la base de datos.
 */
@Service
public class MuestreoServiceImpl implements MuestreoService {

    private static final Logger logger = LoggerFactory.getLogger(MuestreoServiceImpl.class);

    @Autowired
    private MuestreoRepository muestreoRep;

    /**
     * Obtiene la lista completa de Muestreos.
     *
     * @return lista de objetos {@link MuestreoDTO}
     * @throws RuntimeException si ocurre un error durante la consulta a la base de datos
     */
    @Override
    public List<MuestreoDTO> listarMuestreos() {
        try {
            logger.info("Inicio de la obtención de muestreos");
            List<Muestreo> entidades = this.muestreoRep.findAll();
            List<MuestreoDTO> dtos = entidades.stream()
                    .map(ent -> new MuestreoDTO(ent.getIdMuestreo(), mapearItem(ent.getMuestra()),
                            ent.getUbicacion(), ent.getFechaEstimada(), ent.getCantidadMinima(),
                            ent.getUnidad(), ent.getMuestreadores(), ent.getTiempoTotal(),
                            ent.getConsumibles(), ent.getPrecioMuestreo()))
                    .toList();
            logger.info("Se obtuvieron {} Muestreos", dtos.size());
            return dtos;
        } catch (Exception e) {
            logger.error("Error al obtener Muestreos", e);
            throw new RuntimeException("No se pudo obtener las muestreos", e);
        }
    }

    public ItemDTO mapearItem(Muestra muestra) {

        ItemDTO item = new ItemDTO();
        // Item
        if (muestra.getIdMuestra() != null) {
            item.setId(muestra.getIdMuestra());
            item.setTitulo(muestra.getTitulo());
            item.setReferencia(muestra.getReferenciaNormativa().getId());
            item.setMatriz(muestra.getMatriz().getId());
            item.setPe(muestra.getPe());
            item.setVeces(muestra.getCantidadVeces());
            item.setFrecuencia(muestra.getFrecuencia());
            item.setMuestras(muestra.getCantidadMuestras());
            item.setOos(muestra.getOos());
            item.setRoos(muestra.getRoos());
            item.setSCrudos(muestra.getSCrudos());
            //item.setDeterminaciones(muestra.getMatriz());
        }

        return item;
    }

}