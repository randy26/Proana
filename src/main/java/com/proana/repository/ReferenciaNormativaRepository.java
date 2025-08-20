package com.proana.repository;

import com.proana.model.ReferenciaNormativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferenciaNormativaRepository extends JpaRepository<ReferenciaNormativa, Integer> {

}
