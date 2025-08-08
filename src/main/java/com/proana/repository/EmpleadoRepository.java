package com.proana.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proana.model.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

	List<Empleado> findByRolEmpleadoIdRolEmpleado(Integer idRolEmpleado);

}
