package com.proana.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.proana.dto.ContactoDTO;
import com.proana.service.ContactoService;

/**
 * Controlador REST para operaciones relacionadas con las Unidades de Negocio.
 * Expone endpoints para consultar información de la base de datos.
 */
@RestController
@RequestMapping("/api/contacto")
public class ContactoController {

    private static final Logger logger = LoggerFactory.getLogger(ContactoController.class);

    @Autowired
    private ContactoService service;

    /**
     * Endpoint que devuelve la lista de Contactos disponibles.
     *
     * @return Lista de {@link ContactoDto}
     * @throws ResponseStatusException si ocurre un error al recuperar los datos
     */
    @GetMapping("/contactos")
    public List<ContactoDTO> getContactos() {
        try {
            List<ContactoDTO> contactos = service.listarContactos();
            logger.info("contactos obtenidas correctamente: {} registros", contactos.size());
            return contactos;
        } catch (Exception e) {
            logger.error("Error al obtener derivantes", e);
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error al obtener los derivantes.",
                e
            );
        }
    }
}


