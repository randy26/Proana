package com.proana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proana.model.Derivante;

@Repository
public interface DerivanteRepository extends JpaRepository<Derivante, Integer> {

}
