package com.proana.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proana.dto.ClienteDto;
import com.proana.model.Cliente;
import com.proana.repository.ClienteRepository;
import com.proana.service.ClienteService;

/**
 * Implementación del servicio para operaciones sobre Clientes.
 * Proporciona métodos para obtener la lista de clientes desde la base de datos.
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

    @Autowired
    private ClienteRepository clienteRep;

    /**
     * Obtiene la lista completa de Cliente.
     * 
     * @return lista de objetos {@link ClienteDto}
     * @throws RuntimeException si ocurre un error durante la consulta a la base de datos
     */
    @Override
    public List<ClienteDto> listarClientes() {
        try {
            logger.info("Inicio de la obtención de la lista de clientes");
            List<Cliente> entidades = this.clienteRep.findAll();
            List<ClienteDto> dtos = entidades.stream()
                .map(ent -> new ClienteDto(ent.getIdCliente(),ent.getRazonSocial(), ent.getNumeroCliente(), ent.getCuit()))
                .toList();
            logger.info("Se obtuvieron {} clientes", dtos.size());
            return dtos;
        } catch (Exception e) {
            logger.error("Error al obtener Monedas", e);
            throw new RuntimeException("No se pudo obtener los clientes", e);
        }
    }

}
