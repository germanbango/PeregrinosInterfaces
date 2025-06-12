package com.german.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "Peregrinos")
public class Peregrino {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false)
	private String nacionalidad;


	@OneToOne(mappedBy = "peregrino", cascade = CascadeType.ALL)
	private Carnet carnet;
	
	@OneToMany(mappedBy = "peregrino", cascade = CascadeType.ALL)
	private List<Estancia> estancias = new ArrayList<>();
	
	@OneToMany(mappedBy = "peregrino", cascade = CascadeType.ALL)
	private List<PeregrinoParada> peregrinosparadas = new ArrayList<>();
	
	
		@OneToOne
		@JoinColumn(name = "usuario_id",nullable = false)
		private User usuario;
    
    
	public Peregrino() {
		
	}
	
	
	
	public Peregrino(Long id, String nombre, String nacionalidad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	



	public Carnet getCarnet() {
		return carnet;
	}



	public void setCarnet(Carnet carnet) {
		this.carnet = carnet;
	}



	public User getUsuario() {
		return usuario;
	}



	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}



	public List<Estancia> getEstancias() {
		return estancias;
	}



	public void setEstancias(List<Estancia> estancias) {
		this.estancias = estancias;
	}




	@Override
	public String toString() {
		return "Peregrino [id=" + id + ", nombre=" + nombre + ", nacionalidad=" + nacionalidad + ", carnet=" + carnet
				+ "]";
	}



	
	
	

}