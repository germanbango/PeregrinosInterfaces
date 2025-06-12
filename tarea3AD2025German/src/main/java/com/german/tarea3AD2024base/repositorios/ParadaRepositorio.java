package com.german.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.german.tarea3AD2024base.modelo.Parada;

@Repository
public interface ParadaRepositorio extends JpaRepository<Parada, Long>{
    boolean existsByNombreAndRegion(String nombre, char region);
    Parada findByNombreAndRegion(String nombre, char region);
    Parada findByUsuario_id(Long usuario_id);
    @Query("SELECT pp.id FROM PeregrinoParada pp WHERE pp.peregrino.id = :peregrinoId ORDER BY pp.id")
    List<Long> findParadasByPeregrinoId(@Param("peregrinoId") Long peregrinoId);
    @Query("SELECT p.nombre FROM Parada p WHERE p.id = :paradaId")
    String findNombreParadaById(@Param("paradaId") Long paradaId);

}