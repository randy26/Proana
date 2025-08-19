package com.proana.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proana.dto.AbmPaqueteDto;
import com.proana.model.AbmPaquete;
import com.proana.repository.PaqueteRepository;
import com.proana.service.PaqueteService;

@Service
public class PaqueteServiceImp implements PaqueteService {

	private static final Logger logger = LoggerFactory.getLogger(PaqueteServiceImp.class);

	@Autowired
	private PaqueteRepository paqueteRep;

	@Override
	public List<AbmPaqueteDto> listarPaquetes() {
		try {
			logger.info("Inicio de la obtención de paquetes");
			List<AbmPaquete> entidades = this.paqueteRep.findAll();
			List<AbmPaqueteDto> dtos = entidades.stream()
					.map(ent -> new AbmPaqueteDto(ent.getIdPaquete(), ent.getNombre(), ent.getPrecioLista())).toList();
			logger.info("Se obtuvieron {} paquetes", dtos.size());
			return dtos;
		} catch (Exception e) {
			logger.error("Error al obtener Paquetes", e);
			throw new RuntimeException("No se pudo obtener las paquetes", e);
		}
	}

}
