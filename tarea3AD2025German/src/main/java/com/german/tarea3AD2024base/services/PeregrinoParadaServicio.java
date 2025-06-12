
package com.german.tarea3AD2024base.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.german.tarea3AD2024base.modelo.Peregrino;
import com.german.tarea3AD2024base.modelo.PeregrinoParada;
import com.german.tarea3AD2024base.repositorios.PeregrinoParadaRepositorio;

@Service
public class PeregrinoParadaServicio {

	@Autowired
	private PeregrinoParadaRepositorio peregrinoParadarepositorio;

	public PeregrinoParada guardar(PeregrinoParada entity) {
		return peregrinoParadarepositorio.save(entity);
	}

	public PeregrinoParada actualizar(PeregrinoParada entity) {
		return peregrinoParadarepositorio.save(entity);
	}

	public void borrar(PeregrinoParada entity) {
		peregrinoParadarepositorio.delete(entity);
	}

	public void borrarPorId(Long id) {
		peregrinoParadarepositorio.deleteById(id);
	}

	public void borrarPorLote(List<PeregrinoParada> peregrinosParadas) {
		peregrinoParadarepositorio.deleteAll(peregrinosParadas);
	}

	public PeregrinoParada encontrarPorId(Long id) {
		return peregrinoParadarepositorio.findById(id).get();
	}

	public List<PeregrinoParada> encontrarTodos() {
		return peregrinoParadarepositorio.findAll();
	}
	
	}
