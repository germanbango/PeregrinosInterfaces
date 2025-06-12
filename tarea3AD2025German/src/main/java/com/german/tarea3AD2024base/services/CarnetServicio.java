package com.german.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.german.tarea3AD2024base.modelo.Carnet;
import com.german.tarea3AD2024base.repositorios.CarnetRepositorio;


@Service
public class CarnetServicio {

	@Autowired
	private CarnetRepositorio carnetRepositorio;
	
	public Carnet guardar(Carnet entity) {
		return carnetRepositorio.save(entity);
	}
	
	public Carnet actualizar(Carnet entity) {
		return carnetRepositorio.save(entity);
	}
	
	public void borrar(Carnet entity ) {
		carnetRepositorio.delete(entity);
	}
	
	public void borrarPorId(Long id) {
		carnetRepositorio.deleteById(id);
	}
	
	public void borrarPorLote(List<Carnet> carnets) {
		carnetRepositorio.deleteAll(carnets);
	}
	
	public Carnet encontrarPorId(Long id) {
		return carnetRepositorio.findById(id).get();
	}
	
	public List<Carnet> encontrarTodos() {
		return carnetRepositorio.findAll();
	}
	
	public Carnet encontrarCarnetPorPeregrino(Long peregrino_id) {
		return carnetRepositorio.findByPeregrino_id(peregrino_id);
	}
}
