	
	package com.german.tarea3AD2024base.modelo;
	
	import java.io.Serializable;
	import java.util.ArrayList;
	import java.util.List;
	
	import jakarta.persistence.CascadeType;
	import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.JoinColumn;
	import jakarta.persistence.OneToMany;
	import jakarta.persistence.OneToOne;
	import jakarta.persistence.Table;
	
	@Entity
	@Table(name = "Paradas")
	public class Parada {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id", updatable = false, nullable = false)
		private Long id;
	
		@Column(nullable = false)
		private String nombre;
	
		@Column(nullable = false)
		private char region;
	
		@Column(nullable = false)
		private String responsable;
	
		@OneToOne
		@JoinColumn(name = "usuario_id", nullable = false)
		private User usuario;
	
		@OneToMany(mappedBy = "parada", cascade = CascadeType.ALL)
		private List<Estancia> estancias = new ArrayList<>();
	
		public Parada() {
	
		}
	
		public Parada(Long id, String nombre, char region, String responsable) {
			super();
			this.id = id;
			this.nombre = nombre;
			this.region = region;
			this.responsable = responsable;
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
	
		public char getRegion() {
			return region;
		}
	
		public void setRegion(char region) {
			this.region = region;
		}
	
		public String getResponsable() {
			return responsable;
		}
	
		public void setResponsable(String responsable) {
			this.responsable = responsable;
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
			return "Parada [id=" + id + ", nombre=" + nombre + ", region=" + region + ", responsable=" + responsable + "]";
		}
	
	}
