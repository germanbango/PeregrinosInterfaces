package com.german.tarea3AD2024base.utiles;

import com.german.tarea3AD2024base.modelo.Rol;

public class Sesion {
	private static Long id;
	private static String usuario;
	private static Rol perfil;

	private Sesion() {

	}

	public static Long getId() {
		return id;
	}

	public static void setId(Long id) {
		Sesion.id = id;
	}

	public static String getUsuario() {
		return usuario;
	}

	public static void setUsuario(String usuario) {
		Sesion.usuario = usuario;
	}

	public static Rol getPerfil() {
		return perfil;
	}

	public static void setPerfil(Rol perfil) {
		Sesion.perfil = perfil;
	}
	
	

}
