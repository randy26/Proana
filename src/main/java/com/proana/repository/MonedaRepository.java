package com.proana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proana.model.Moneda;

@Repository
public interface MonedaRepository extends JpaRepository<Moneda, Integer> {

}
