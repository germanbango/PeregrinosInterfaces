package com.german.tarea3AD2024base.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.german.tarea3AD2024base.config.StageManager;
import com.german.tarea3AD2024base.modelo.Peregrino;
import com.german.tarea3AD2024base.modelo.User;
import com.german.tarea3AD2024base.services.PeregrinoServicio;
import com.german.tarea3AD2024base.services.UserService;
import com.german.tarea3AD2024base.utiles.Sesion;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Controller
public class ModificarPeregrinoController implements Initializable {

	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtUsuario;
	@FXML
	private PasswordField txtContrasena;
	@FXML
	private ComboBox<String> txtNacionaldiad;
	@FXML
	private MenuItem itemAyuda;

	@Autowired
	private PeregrinoServicio peregrinoSerivicio;
	@Autowired
	private UserService usuarioServicio;
	@Lazy
	@Autowired
	private StageManager stageManager;

	private Peregrino peregrino = new Peregrino();
	private User usuario = new User();
	private List<String> paises = new ArrayList<String>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itemAyuda.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
		peregrino = peregrinoSerivicio.encontrarPorUsuario(Sesion.getId());
		usuario = usuarioServicio.find(Sesion.getId());

		txtNombre.setText(peregrino.getNombre());
		txtUsuario.setText(usuario.getEmail());
		txtContrasena.setText(usuario.getPassword());

		List<String> paises = cargarPaises();
		txtNacionaldiad.getItems().addAll(paises);

		txtNacionaldiad.setValue(peregrino.getNacionalidad());

	}

	private List<String> cargarPaises() {
		List<String> ret = new ArrayList<>();
		File paises = new File("src/main/resources/paises.xml");

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(paises);
			doc.getDocumentElement().normalize();
			NodeList listapaises = doc.getElementsByTagName("pais");

			for (int i = 0; i < listapaises.getLength(); i++) {
				Node nodo = listapaises.item(i);

				if (nodo.getNodeType() == Node.ELEMENT_NODE) {
					Element pais = (Element) nodo;

					String nombrep = pais.getElementsByTagName("nombre").item(0).getTextContent();
					ret.add(nombrep);
				}
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	@FXML
	public void modificarDatos() {
		if (validar()) {
			ActualizarDatos();
			mostrarAlerta(AlertType.CONFIRMATION, "Datos actualizados.", "Se han cambiado los datos");
		}
	}

	private void ActualizarDatos() {
		String nombre = txtNombre.getText();
		String nacionalidad = txtNacionaldiad.getValue();
		String usuarioNuevo = txtUsuario.getText();
		String contrasenaNueva = txtContrasena.getText();

		usuario.setEmail(usuarioNuevo);
		usuario.setPassword(contrasenaNueva);
		peregrino.setNombre(nombre);
		peregrino.setNacionalidad(nacionalidad);

		usuarioServicio.update(usuario);
		peregrinoSerivicio.actualizar(peregrino);
	}

	private boolean validar() {
		String nombre = txtNombre.getText();
		String usuarioNuevo = txtUsuario.getText();
		String contrasenaNueva = txtContrasena.getText();

		if (nombre.isEmpty()) {
			mostrarAlerta(AlertType.ERROR, "ERROR:", "Debe introducir un nombre");
			return false;
		}if(!usuarioNuevo.equals(usuario.getEmail())){
			if (usuarioNuevo.isEmpty() || usuarioNuevo.contains(" ")
					|| usuarioServicio.existeUsuarioConEmail(usuarioNuevo)) {
				mostrarAlerta(AlertType.ERROR, "ERROR:", "Debe introducir un usuario sin espacios o ya existe ese usuario");
				return false;
			}
		}
		
		if (contrasenaNueva.isEmpty() || contrasenaNueva.contains(" ")) {
			mostrarAlerta(AlertType.ERROR, "ERROR:", "Debe introducir una contrasena o no debe contener espacios");
			return false;
		}
		return true;
	}
	
	@FXML
	public void cerrarVentana() {
		stageManager.closeModal();
	}

	private void mostrarAlerta(AlertType tipo, String titulo, String mensaje) {
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}
	
	 @FXML
	    private void mostrarAyuda() {
	       
	    }

}