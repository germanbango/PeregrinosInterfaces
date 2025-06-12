package com.german.tarea3AD2024base.repositorios;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.german.tarea3AD2024base.modelo.Carnet;

@Repository
public interface CarnetRepositorio extends JpaRepository<Carnet, Long> {
	Carnet findByPeregrino_id(Long peregrino_id);
}
