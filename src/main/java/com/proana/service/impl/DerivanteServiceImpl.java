package com.proana.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proana.dto.ClienteDto;
import com.proana.dto.DerivanteDTO;
import com.proana.model.Derivante;
import com.proana.repository.DerivanteRepository;
import com.proana.service.DerivanteService;

/**
 * Implementación del servicio para operaciones sobre Derivantes.
 * Proporciona métodos para obtener la lista de Derivante desde la base de datos.
 */
@Service
public class DerivanteServiceImpl implements DerivanteService {

    private static final Logger logger = LoggerFactory.getLogger(DerivanteServiceImpl.class);

    @Autowired
    private DerivanteRepository derivanteRep;

    /**
     * Obtiene la lista completa de Cliente.
     * 
     * @return lista de objetos {@link ClienteDto}
     * @throws RuntimeException si ocurre un error durante la consulta a la base de datos
     */
    @Override
    public List<DerivanteDTO> listarDerivantes() {
        try {
            logger.info("Inicio de la obtención de la lista de clientes");
            List<Derivante> entidades = this.derivanteRep.findAll();
            List<DerivanteDTO> dtos = entidades.stream()
                .map(ent -> new DerivanteDTO(ent.getIdDerivante(),ent.getRazonSocial(), ent.getNumeroDerivante(), ent.getCuit()))
                .toList();
            logger.info("Se obtuvieron {} Derivantes", dtos.size());
            return dtos;
        } catch (Exception e) {
            logger.error("Error al obtener Derivantes", e);
            throw new RuntimeException("No se pudo obtener los derivantes", e);
        }
    }

}
