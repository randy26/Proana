package com.proana.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proana.dto.PresupuestoMuestraDTO;
import com.proana.model.Presupuesto;

@Repository
public interface PresupuestoRepository extends JpaRepository<Presupuesto, Integer> {
	
	@Query("""
		       SELECT
		           p.idPresupuesto,
		           p.bpl,
		           p.titulo,
		           p.fechaPresupuesto,
		           p.validezDelPresupuesto,
		           p.fechaAceptacion,
		           p.duracionDelContrato,
		           p.fechaInicio,
		           p.ordenDeCompra,
		           p.referencia,
		           p.estadoPresupuesto.idEstadoPresupuesto,
		           p.estadoPresupuesto.estadoPresupuesto
		       FROM Presupuesto p
		       """)
		List<Object[]> findPresupuestosResumen();
		
		@Query("SELECT DISTINCT p FROM Presupuesto p " +
			       "JOIN FETCH p.cliente c " +
			       "LEFT JOIN FETCH p.muestras m")
			List<Presupuesto> findPresupuestosConClienteYMuestras();

}