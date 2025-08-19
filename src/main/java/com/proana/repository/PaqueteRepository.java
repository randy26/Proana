package com.proana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proana.model.AbmPaquete;

@Repository
public interface PaqueteRepository extends JpaRepository<AbmPaquete, Integer> {

}
