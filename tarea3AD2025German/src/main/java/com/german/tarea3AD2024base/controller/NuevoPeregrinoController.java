
package com.german.tarea3AD2024base.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import com.german.tarea3AD2024base.modelo.Carnet;
import com.german.tarea3AD2024base.modelo.Parada;
import com.german.tarea3AD2024base.modelo.Peregrino;
import com.german.tarea3AD2024base.modelo.PeregrinoParada;
import com.german.tarea3AD2024base.modelo.Rol;
import com.german.tarea3AD2024base.modelo.User;
import com.german.tarea3AD2024base.services.CarnetServicio;
import com.german.tarea3AD2024base.services.ParadaServicio;
import com.german.tarea3AD2024base.services.PeregrinoParadaServicio;
import com.german.tarea3AD2024base.services.PeregrinoServicio;
import com.german.tarea3AD2024base.services.UserService;
import com.german.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCombination;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Controller
public class NuevoPeregrinoController implements Initializable {

	@FXML
	public ComboBox<String> cmbParadas;
	@FXML
	public TextField txtNombrePeregrino;
	@FXML
	public ComboBox<String> cmbNacionalidad;
	@FXML
	public TextField txtUsuario;
	@FXML
	public PasswordField txtContrasena;
	@FXML
	private MenuItem itemAyuda;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ParadaServicio paradaServicio;

	@Autowired
	private UserService userService;

	@Autowired
	private PeregrinoServicio peregrinoServicio;
	
	@Autowired
	private CarnetServicio carnetServicio;
	
	@Autowired
	private PeregrinoParadaServicio peregrinoParadaServicio;

	private List<String> paradas;
	private List<String> paises;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itemAyuda.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
		paradas = paradaServicio.nombreyRegiones();
		cmbParadas.getItems().addAll(paradas);
		paises = cargarPaises();
		cmbNacionalidad.getItems().addAll(paises);

	}
	
	@FXML
	public void GuardarPeregrino() {
		if(validar()) {
			Peregrino peregrino = new Peregrino();
			Parada paradaInicial = new Parada();
			Carnet carnet = new Carnet();
			PeregrinoParada peregrinoParada = new PeregrinoParada();
			User usuario = new User();
			crearPeregrino(usuario,peregrino, paradaInicial, carnet, peregrinoParada);
			mostrarAlerta(AlertType.CONFIRMATION, "Peregrino creado correctamente", "Se ha podido crear el peregrino sin probelmas");
		}
	}

	private void crearPeregrino(User usuario,Peregrino peregrino, Parada paradaInicial, Carnet carnet, PeregrinoParada peregrinoParada) {
		String usuarionuevo = txtUsuario.getText();
		String contrasena = txtContrasena.getText();
		String nombre = txtNombrePeregrino.getText();
		String nacionalidad = cmbNacionalidad.getValue();
		String paradaSeleccionada = cmbParadas.getValue();
		String[] partes = paradaSeleccionada.split("  ");
		String parada = partes[0];
		char region = partes[1].charAt(0);
		usuario.setEmail(usuarionuevo);
		usuario.setPassword(contrasena);
		usuario.setRol(Rol.PEREGRINO);
		userService.save(usuario);
		paradaInicial = paradaServicio.paradaPorNombreyRegion(parada, region);
		peregrino.setNombre(nombre);
		peregrino.setNacionalidad(nacionalidad);
		peregrino.setUsuario(usuario);
		peregrinoServicio.guardar(peregrino);
		peregrino = peregrinoServicio.encontrarPorNombre(nombre);
		carnet.setDistancia(0.0);
		carnet.setFechaexp(LocalDate.now());
		carnet.setNvips(0);
		carnet.setParadaInicial(paradaInicial);
		carnet.setPeregrino(peregrino);
		carnetServicio.guardar(carnet);
		peregrinoParada.setParada(paradaInicial);
		peregrinoParada.setPeregrino(peregrino);
		peregrinoParadaServicio.guardar(peregrinoParada);

	}

	@FXML
	private void salir() {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	public boolean validar() {

		String nombre = txtNombrePeregrino.getText();
		String parada = cmbParadas.getValue();
		String nacionalidad = cmbNacionalidad.getValue();
		String usuario = txtUsuario.getText();
		String contrasena = txtContrasena.getText();

		if (nombre.isEmpty()) {
			mostrarAlerta(AlertType.ERROR, "ERROR:", "debe introducir su nombre");
			return false;
		}
		if (parada == null || parada.isEmpty()) {
			mostrarAlerta(AlertType.ERROR, "ERROR:", "debe seleccionar una parada");
			return false;
		}
		if (usuario.isEmpty() || usuario.contains(" ")) {
			mostrarAlerta(AlertType.ERROR, "ERROR:", "formato de usuario incorrecto");
			return false;
		}
		if(userService.existeUsuarioConEmail(usuario)) {
			mostrarAlerta(AlertType.ERROR, "ERROR:",  "Usuario ya existe");
			return false;
		}
		if (contrasena.isEmpty() || contrasena.contains(" ")) {
			mostrarAlerta(AlertType.ERROR, "ERROR:", "formato de contrasena incorrecto");
			return false;
		}
		if (nacionalidad == null || nacionalidad.isEmpty()) {
			mostrarAlerta(AlertType.ERROR, "ERROR:", "debe seleccionar una nacionalidad");
			return false;
		}
		return true;
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

	private void mostrarAlerta(AlertType tipo, String titulo, String mensaje) {
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}
	 @FXML
	    private void mostrarAyuda() {
	        try {
	            Stage stage = new Stage();
	            stage.setTitle("Ayuda - Nueva Parada");

	            WebView webView = new WebView();

	            // Carga del archivo HTML desde resources
	            URL url = getClass().getResource("/ayuda/NuevoPeregrino.html");
	            if (url != null) {
	                webView.getEngine().load(url.toExternalForm());
	            } else {
	                webView.getEngine().loadContent("<h1>Error</h1><p>No se encontró el archivo de ayuda.</p>");
	            }

	            Scene scene = new Scene(webView, 800, 600);
	            stage.setScene(scene);
	            stage.initModality(Modality.APPLICATION_MODAL);

	            // Obtener ventana principal como owner
	            Stage primaryStage = (Stage) txtContrasena.getScene().getWindow(); 
	            stage.initOwner(primaryStage);

	            stage.setResizable(false);
	            stage.showAndWait();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }


}
