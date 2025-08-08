package com.proana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proana.model.UnidadNegocio;

@Repository
public interface UnidadNegocioRepository extends JpaRepository<UnidadNegocio, Integer> {

}
