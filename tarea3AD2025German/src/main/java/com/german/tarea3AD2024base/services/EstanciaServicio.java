package com.german.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.german.tarea3AD2024base.modelo.Estancia;
import com.german.tarea3AD2024base.repositorios.EstanciaRepositorio;

@Service
public class EstanciaServicio {

	@Autowired
	private EstanciaRepositorio estanciaRepositorio;
	
	public Estancia guardar(Estancia entity) {
		return estanciaRepositorio.save(entity);
	}
	
	public Estancia actualizar(Estancia entity) {
		return estanciaRepositorio.save(entity);
	}
	
	public void borrar(Estancia entity ) {
		estanciaRepositorio.delete(entity);
	}
	
	public void borrarPorId(Long id) {
		estanciaRepositorio.deleteById(id);
	}
	
	public void borrarPorLote(List<Estancia> estancias) {
		estanciaRepositorio.deleteAll(estancias);
	}
	
	public Estancia encontrarPorId(Long id) {
		return estanciaRepositorio.findById(id).get();
	}
	
	public List<Estancia> encontrarTodos() {
		return estanciaRepositorio.findAll();
	}
	
	public List<Estancia> estanciasPorPeregrino(Long idPeregrino){
		return estanciaRepositorio.findByPeregrinoIdOrderByFechaAsc(idPeregrino);
	}
	
	public List<Estancia> estanciasPorParada(Long idParada){
		return estanciaRepositorio.findByParadaId(idParada);
	}
}
