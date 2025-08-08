package com.proana.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proana.dto.ClienteDto;
import com.proana.dto.ContactoDTO;
import com.proana.model.Contacto;
import com.proana.repository.ContactoRepository;
import com.proana.service.ContactoService;

/**
 * Implementación del servicio para operaciones sobre Clientes.
 * Proporciona métodos para obtener la lista de clientes desde la base de datos.
 */
@Service
public class ContactoServiceImpl implements ContactoService {

    private static final Logger logger = LoggerFactory.getLogger(ContactoServiceImpl.class);

    @Autowired
    private ContactoRepository contactoRep;

    /**
     * Obtiene la lista completa de Cliente.
     * 
     * @return lista de objetos {@link ClienteDto}
     * @throws RuntimeException si ocurre un error durante la consulta a la base de datos
     */
    @Override
    public List<ContactoDTO> listarContactos() {
        try {
            logger.info("Inicio de la obtención de la lista de contactos");
            List<Contacto> entidades = this.contactoRep.findAll();
            List<ContactoDTO> dtos = entidades.stream()
                .map(ent -> new ContactoDTO(
                    ent.getIdContacto(),
                    ent.getCliente() != null ? ent.getCliente().getIdCliente() : null,
                    ent.getNombre(),
                    ent.getApellido(),
                    ent.getTelefono(),
                    ent.getEmail(),
                    ent.getObservacion()
                ))
                .toList();
            logger.info("Se obtuvieron {} contactos", dtos.size());
            return dtos;
        } catch (Exception e) {
            logger.error("Error al obtener contactos", e);
            throw new RuntimeException("No se pudo obtener los contactos", e);
        }
    }
}
