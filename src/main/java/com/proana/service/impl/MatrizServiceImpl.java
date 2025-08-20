package com.proana.service.impl;

import com.proana.dto.MatrizDTO;
import com.proana.model.Matriz;
import com.proana.repository.MatrizRepository;
import com.proana.service.MatrizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio para operaciones sobre Matrices.
 * Proporciona métodos para obtener la lista de matrices desde la base de datos.
 */
@Service
public class MatrizServiceImpl implements MatrizService {

    private static final Logger logger = LoggerFactory.getLogger(MatrizServiceImpl.class);

    @Autowired
    private MatrizRepository matrizRep;

    /**
     * Obtiene la lista completa de Matrices.
     *
     * @return lista de objetos {@link MatrizDTO}
     * @throws RuntimeException si ocurre un error durante la consulta a la base de datos
     */
    @Override
    public List<MatrizDTO> listarMatrices() {
        try {
            logger.info("Inicio de la obtención de matrices");
            List<Matriz> entidades = this.matrizRep.findAll();
            List<MatrizDTO> dtos = entidades.stream()
                    .map(ent -> new MatrizDTO(ent.getId(), ent.getNombre()))
                    .toList();
            logger.info("Se obtuvieron {} Muestreos", dtos.size());
            return dtos;
        } catch (Exception e) {
            logger.error("Error al obtener Matrices", e);
            throw new RuntimeException("No se pudo obtener las matrices", e);
        }
    }
}
