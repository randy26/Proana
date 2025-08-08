package com.proana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proana.model.Contacto;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Integer> {

}
