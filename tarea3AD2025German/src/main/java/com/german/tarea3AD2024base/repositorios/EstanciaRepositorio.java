package com.german.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.german.tarea3AD2024base.modelo.Estancia;

@Repository
public interface EstanciaRepositorio extends JpaRepository<Estancia, Long>{

    @Query("SELECT e FROM Estancia e WHERE e.peregrino.id = :peregrinoId ORDER BY e.fecha ASC")
    List<Estancia> findByPeregrinoIdOrderByFechaAsc(@Param("peregrinoId") Long peregrinoId);
    
    @Query("SELECT e FROM Estancia e WHERE e.parada.id = :paradaId")
    List<Estancia> findByParadaId(@Param("paradaId") Long paradaId);
	
}
