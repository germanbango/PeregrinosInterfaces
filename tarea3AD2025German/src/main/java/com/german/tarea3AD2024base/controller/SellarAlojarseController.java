package com.german.tarea3AD2024base.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.german.tarea3AD2024base.config.StageManager;
import com.german.tarea3AD2024base.modelo.Carnet;
import com.german.tarea3AD2024base.modelo.Estancia;
import com.german.tarea3AD2024base.modelo.Parada;
import com.german.tarea3AD2024base.modelo.Peregrino;
import com.german.tarea3AD2024base.modelo.PeregrinoParada;
import com.german.tarea3AD2024base.services.CarnetServicio;
import com.german.tarea3AD2024base.services.EstanciaServicio;
import com.german.tarea3AD2024base.services.ParadaServicio;
import com.german.tarea3AD2024base.services.PeregrinoParadaServicio;
import com.german.tarea3AD2024base.services.PeregrinoServicio;
import com.german.tarea3AD2024base.utiles.Sesion;

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
import javafx.scene.control.RadioButton;

@Controller
public class SellarAlojarseController implements Initializable {

	@FXML
	private ComboBox<String> cmbPeregrinos;
	@FXML
	private RadioButton rdbEstanciaNormal;
	@FXML
	private RadioButton rdbEstanciaVIP;
	@FXML
	private MenuItem itemAyuda;

	@Autowired
	private PeregrinoServicio peregrinoServicio;

	@Autowired
	private ParadaServicio paradaServicio;

	@Autowired
	private CarnetServicio carnetServicio;

	@Autowired
	private EstanciaServicio estanciaServicio;

	@Autowired
	private PeregrinoParadaServicio peregrinoParadaServicio;

	@Lazy
	@Autowired
	StageManager stageManager;

	Parada parada;

	private List<String> peregrinos;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itemAyuda.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
		peregrinos = peregrinoServicio.listaDePeregrinos();
		cmbPeregrinos.getItems().addAll(peregrinos);

	}

	@FXML
	public void sellarAlojarse() {

		parada = paradaServicio.encontrarParadaPorUsuario(Sesion.getId());
		Estancia estancia = new Estancia();
		Peregrino peregrino = peregrinoServicio.encontrarPorNombre(cmbPeregrinos.getValue());
		Carnet carnet = carnetServicio.encontrarCarnetPorPeregrino(peregrino.getId());
		carnet.setDistancia(5.0);
		PeregrinoParada peregrinoParada = new PeregrinoParada();
		peregrinoParada.setParada(parada);
		peregrinoParada.setPeregrino(peregrino);
		if (rdbEstanciaNormal.isSelected() && !(rdbEstanciaVIP.isSelected())) {
			estancia.setFecha(LocalDateTime.now());
			estancia.setVip(false);
			estancia.setParada(parada);
			estancia.setPeregrino(peregrino);
			estanciaServicio.guardar(estancia);
			carnetServicio.actualizar(carnet);
			peregrinoParadaServicio.guardar(peregrinoParada);
			mostrarAlerta(AlertType.CONFIRMATION, "Estancia guardada", "La estancia gaurdada es normal");
		} else if (!(rdbEstanciaNormal.isSelected()) && rdbEstanciaVIP.isSelected()) {
			estancia.setFecha(LocalDateTime.now());
			estancia.setVip(true);
			estancia.setParada(parada);
			estancia.setPeregrino(peregrino);
			estanciaServicio.guardar(estancia);
			carnet.setNvips(carnet.getNvips() + 1);
			carnetServicio.actualizar(carnet);
			peregrinoParadaServicio.guardar(peregrinoParada);
			mostrarAlerta(AlertType.CONFIRMATION, "Estancia guardada", "La estancia gaurdada es VIP");
		} else if (rdbEstanciaNormal.isSelected() && rdbEstanciaVIP.isSelected()) {
			mostrarAlerta(AlertType.ERROR, "Error:", "Solo puedes Seleccionar una opcion");
		} else {
			carnetServicio.actualizar(carnet);
			peregrinoParadaServicio.guardar(peregrinoParada);
			mostrarAlerta(AlertType.CONFIRMATION, "Visita guardada", "Se ha registrado la visita");
		}
	}

	@FXML
	public void salir() {
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
        try {
            Stage stage = new Stage();
            stage.setTitle("Ayuda - Nueva Parada");

            WebView webView = new WebView();

            // Carga del archivo HTML desde resources
            URL url = getClass().getResource("/ayuda/SellarAlojarse.html");
            if (url != null) {
                webView.getEngine().load(url.toExternalForm());
            } else {
                webView.getEngine().loadContent("<h1>Error</h1><p>No se encontró el archivo de ayuda.</p>");
            }

            Scene scene = new Scene(webView, 800, 600);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            // Obtener ventana principal como owner
            Stage primaryStage = (Stage) rdbEstanciaVIP.getScene().getWindow(); 
            stage.initOwner(primaryStage);

            stage.setResizable(false);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
