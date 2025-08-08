package com.proana.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proana.dto.UnidadNegocioDto;
import com.proana.model.UnidadNegocio;
import com.proana.repository.UnidadNegocioRepository;
import com.proana.service.UnidadNegocioService;

/**
 * Implementación del servicio para operaciones sobre Unidades de Negocio.
 * Proporciona métodos para obtener la lista de unidades desde la base de datos.
 */
@Service
public class UnidadNegocioServiceImpl implements UnidadNegocioService {

    private static final Logger logger = LoggerFactory.getLogger(UnidadNegocioServiceImpl.class);

    @Autowired
    private UnidadNegocioRepository unidadNegocioRep;

    /**
     * Obtiene la lista completa de Unidades de Negocio.
     * 
     * @return lista de objetos {@link UnidadNegocioDto}
     * @throws RuntimeException si ocurre un error durante la consulta a la base de datos
     */
    @Override
    public List<UnidadNegocioDto> listarUnidades() {
        try {
            logger.info("Inicio de la obtención de unidades de negocio");
            List<UnidadNegocio> entidades = this.unidadNegocioRep.findAll();
            List<UnidadNegocioDto> dtos = entidades.stream()
                .map(ent -> new UnidadNegocioDto(ent.getIdUnidadNegocio(), ent.getNombre()))
                .toList();
            logger.info("Se obtuvieron {} unidades de negocio", dtos.size());
            return dtos;
        } catch (Exception e) {
            logger.error("Error al obtener unidades de negocio", e);
            throw new RuntimeException("No se pudo obtener las unidades de negocio", e);
        }
    }

}
