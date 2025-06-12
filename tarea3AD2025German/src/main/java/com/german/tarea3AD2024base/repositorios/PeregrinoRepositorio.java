package com.german.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.german.tarea3AD2024base.modelo.Peregrino;

@Repository
public interface PeregrinoRepositorio extends JpaRepository<Peregrino, Long> {
	Peregrino findByNombre(String nombre);
	Peregrino findByUsuario_id(Long id);
}
