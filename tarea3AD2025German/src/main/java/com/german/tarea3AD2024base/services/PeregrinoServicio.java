package com.german.tarea3AD2024base.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.german.tarea3AD2024base.modelo.Peregrino;
import com.german.tarea3AD2024base.repositorios.PeregrinoRepositorio;

@Service
public class PeregrinoServicio {

	@Autowired
	private PeregrinoRepositorio peregrinoRepositorio;

	public Peregrino guardar(Peregrino entity) {
		return peregrinoRepositorio.save(entity);
	}

	public Peregrino actualizar(Peregrino entity) {
		return peregrinoRepositorio.save(entity);
	}

	public void borrar(Peregrino entity) {
		peregrinoRepositorio.delete(entity);
	}

	public void borrarPorId(Long id) {
		peregrinoRepositorio.deleteById(id);
	}

	public void borrarPorLote(List<Peregrino> peregrinos) {
		peregrinoRepositorio.deleteAll(peregrinos);
	}

	public Peregrino encontrarPorId(Long id) {
		return peregrinoRepositorio.findById(id).get();
	}

	public List<Peregrino> encontrarTodos() {
		return peregrinoRepositorio.findAll();
	}

	public List<String> listaDePeregrinos() {
		List<String> ret = new ArrayList<>();
		for (Peregrino peregrino : encontrarTodos()) {
			ret.add(peregrino.getNombre());
		}
		return ret;
	}

	public Peregrino encontrarPorNombre(String nombre) {
		return peregrinoRepositorio.findByNombre(nombre);
	}

	public Peregrino encontrarPorUsuario(Long id) {
		return peregrinoRepositorio.findByUsuario_id(id);
	}

}
