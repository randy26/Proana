package com.proana.repository;

import com.proana.model.Muestreo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuestreoRepository extends JpaRepository<Muestreo, Integer> {

}
