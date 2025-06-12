package com.german.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.german.tarea3AD2024base.config.StageManager;
import com.german.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class MenuResponsableController implements Initializable {

	@FXML
	private MenuBar barraMenuResponsable;
	@FXML
	private Menu menuGestion;
	@FXML
	private MenuItem menuItemSellar;
	@FXML
	private MenuItem menuItemExportar;
	@FXML
	private MenuItem itemMenuSalir;
	@FXML
	private MenuItem itemAyuda;
	
	@Lazy
	@Autowired
	StageManager stageManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itemAyuda.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
		menuItemSellar.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		menuItemExportar.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
		itemMenuSalir.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));

	}
	
	@FXML
	private void abrirSellarAlojarse() {
		stageManager.showModal(FxmlView.SELLAR_ALOJARSE);
	}
	
	@FXML
	private void abirExportarParadas() {
		stageManager.showModal(FxmlView.EXPORTAR_ALOJARSE);
	}
	
	@FXML
	public void cerrarSesion() {
		stageManager.switchScene(FxmlView.LOGIN);
	}  
	 @FXML
	    private void mostrarAyuda() {
	        try {
	            Stage stage = new Stage();
	            stage.setTitle("Ayuda - Nueva Parada");

	            WebView webView = new WebView();

	            // Carga del archivo HTML desde resources
	            URL url = getClass().getResource("/ayuda/MenuResponsable.html");
	            if (url != null) {
	                webView.getEngine().load(url.toExternalForm());
	            } else {
	                webView.getEngine().loadContent("<h1>Error</h1><p>No se encontró el archivo de ayuda.</p>");
	            }

	            Scene scene = new Scene(webView, 800, 600);
	            stage.setScene(scene);
	            stage.initModality(Modality.APPLICATION_MODAL);

	            // Obtener ventana principal como owner
	            Stage primaryStage = (Stage) barraMenuResponsable.getScene().getWindow(); 
	            stage.initOwner(primaryStage);

	            stage.setResizable(false);
	            stage.showAndWait();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }


}
