
package com.german.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.german.tarea3AD2024base.modelo.Parada;
import com.german.tarea3AD2024base.modelo.PeregrinoParada;

@Repository
public interface PeregrinoParadaRepositorio extends JpaRepository<PeregrinoParada, Long>{

    @Query("SELECT pp.parada FROM PeregrinoParada pp WHERE pp.parada.id = :idParada")
    Parada findParadaByParadaId(@Param("idParada") Long idParada);
}
