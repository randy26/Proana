package com.proana.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proana.dto.MonedaDto;
import com.proana.model.Moneda;
import com.proana.repository.MonedaRepository;
import com.proana.service.MonedaService;

/**
 * Implementación del servicio para operaciones sobre Unidades de Negocio.
 * Proporciona métodos para obtener la lista de unidades desde la base de datos.
 */
@Service
public class MonedaServiceImpl implements MonedaService {

    private static final Logger logger = LoggerFactory.getLogger(MonedaServiceImpl.class);

    @Autowired
    private MonedaRepository monedaRep;

    /**
     * Obtiene la lista completa de Moneda.
     * 
     * @return lista de objetos {@link MonedaDto}
     * @throws RuntimeException si ocurre un error durante la consulta a la base de datos
     */
    @Override
    public List<MonedaDto> listarMonedas() {
        try {
            logger.info("Inicio de la obtención de unidades de negocio");
            List<Moneda> entidades = this.monedaRep.findAll();
            List<MonedaDto> dtos = entidades.stream()
                .map(ent -> new MonedaDto(ent.getIdMoneda(), ent.getNombre()))
                .toList();
            logger.info("Se obtuvieron {} Monedas", dtos.size());
            return dtos;
        } catch (Exception e) {
            logger.error("Error al obtener Monedas", e);
            throw new RuntimeException("No se pudo obtener las monedas", e);
        }
    }

}
