package com.proana.repository;

import com.proana.model.AbmDeterminacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbmDeterminacionRepository extends JpaRepository<AbmDeterminacion, Integer> {

}
