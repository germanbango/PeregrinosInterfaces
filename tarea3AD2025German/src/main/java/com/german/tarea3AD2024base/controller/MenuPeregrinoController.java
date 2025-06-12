	package com.german.tarea3AD2024base.controller;
	
	import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.german.tarea3AD2024base.config.StageManager;
import com.german.tarea3AD2024base.modelo.Carnet;
import com.german.tarea3AD2024base.modelo.Estancia;
import com.german.tarea3AD2024base.modelo.Parada;
import com.german.tarea3AD2024base.modelo.Peregrino;
import com.german.tarea3AD2024base.services.CarnetServicio;
import com.german.tarea3AD2024base.services.EstanciaServicio;
import com.german.tarea3AD2024base.services.ParadaServicio;
import com.german.tarea3AD2024base.services.PeregrinoServicio;
import com.german.tarea3AD2024base.utiles.Sesion;
import com.german.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
	
	@Controller
	public class MenuPeregrinoController implements Initializable {
	
		@FXML
		private MenuBar menubarPeregrino;
		@FXML
		private Menu menuGestion;
		@FXML
		private MenuItem menuitemExportarCarnet;
		@FXML
		private MenuItem menuItemModificarPeregrino;
		@FXML
		private MenuItem itemMenuSalir;
		@FXML 
		private MenuItem itemAyuda;
	
		@Autowired
		private PeregrinoServicio peregrinoServicio = new PeregrinoServicio();
		@Autowired
		private CarnetServicio carnetServicio = new CarnetServicio();
		@Autowired
		private ParadaServicio paradaServicio = new ParadaServicio();
		@Autowired
		private EstanciaServicio estanciaServicio = new EstanciaServicio();
		@Autowired
		private DataSource dt;
		
		@Lazy
		@Autowired
		StageManager stageManger;
		
		
		
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			itemAyuda.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
			menuitemExportarCarnet.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
			menuItemModificarPeregrino.setAccelerator(KeyCombination.keyCombination("Ctrl+M"));
			itemMenuSalir.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
			
	
		}
		
		@FXML
		public void abrirModificarPeregrino() {
			stageManger.showModal(FxmlView.MODIFICAR_PEREGRINO);
		}
		
		@FXML
		public void generarInformeYXml() {
			
			Peregrino peregrino = peregrinoServicio.encontrarPorUsuario(Sesion.getId());
			mostrarInformeCarnet();
			if(!carnetXML(peregrino)){
				mostrarAlerta(AlertType.ERROR, "ERROR:", "No se ha podido generar el archivo XML");
			}else {
				mostrarAlerta(AlertType.CONFIRMATION, "XMl guardado", "Se ha creado correctamente el fichero xml");
				
			}
		}
	
		private boolean carnetXML(Peregrino p) {
			boolean ret = false;
			Carnet carnet = carnetServicio.encontrarCarnetPorPeregrino(p.getId());
			List<Parada> paradas = paradaServicio.encontrarParadasPorPeregrino(p.getId());
			List<Estancia> estancias = estanciaServicio.estanciasPorPeregrino(p.getId());
			String paradaInicial = paradaServicio.encontrarNombreParadaPorId(carnet.getParadaInicial().getId());
	
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.newDocument();
	
				Element carnetE = doc.createElement("carnet");
				doc.appendChild(carnetE);
	
				Element id = doc.createElement("id");
				id.appendChild(doc.createTextNode(String.valueOf(p.getId())));
				carnetE.appendChild(id);
	
				Element fechaExp = doc.createElement("fechaexp");
				fechaExp.appendChild(doc.createTextNode(carnet.getFechaexp().toString()));
				carnetE.appendChild(fechaExp);
	
				Element expedidoEn = doc.createElement("expedidoen");
				expedidoEn.appendChild(doc.createTextNode(paradaInicial));
				carnetE.appendChild(expedidoEn);
	
				Element peregrino = doc.createElement("peregrino");
	
				Element nombre = doc.createElement("nombre");
				nombre.appendChild(doc.createTextNode(p.getNombre()));
				peregrino.appendChild(nombre);
	
				Element nacionalidad = doc.createElement("nacionalidad");
				nacionalidad.appendChild(doc.createTextNode(p.getNacionalidad()));
				peregrino.appendChild(nacionalidad);
	
				carnetE.appendChild(peregrino);
	
				Element hoy = doc.createElement("hoy");
				hoy.appendChild(doc.createTextNode(LocalDate.now().toString()));
				carnetE.appendChild(hoy);
	
				Element distanciaTotal = doc.createElement("distanciatotal");
				distanciaTotal.appendChild(doc.createTextNode(String.valueOf(carnet.getDistancia())));
				carnetE.appendChild(distanciaTotal);
	
				Element paradasElem = doc.createElement("paradas");
				int orden = 1;
				for (Parada parada : paradas) {
					Element paradaElem = doc.createElement("parada");
	
					Element ordenElem = doc.createElement("orden");
					ordenElem.appendChild(doc.createTextNode(String.valueOf(orden++)));
					paradaElem.appendChild(ordenElem);
	
					Element nombreParada = doc.createElement("nombre");
					nombreParada.appendChild(doc.createTextNode(parada.getNombre()));
					paradaElem.appendChild(nombreParada);
	
					Element region = doc.createElement("region");
					region.appendChild(doc.createTextNode(String.valueOf(parada.getRegion())));
					paradaElem.appendChild(region);
	
					paradasElem.appendChild(paradaElem);
				}
				carnetE.appendChild(paradasElem);
	
				Element estanciasElem = doc.createElement("estancias");
				for (Estancia e : estancias) {
					Element estanciaElem = doc.createElement("estancia");
	
					Element idEstancia = doc.createElement("id");
					idEstancia.appendChild(doc.createTextNode(String.valueOf(e.getId())));
					estanciaElem.appendChild(idEstancia);
	
					Element fecha = doc.createElement("fecha");
					fecha.appendChild(doc.createTextNode(e.getFecha().toString()));
					estanciaElem.appendChild(fecha);
	
					Element paradaEst = doc.createElement("parada");
					paradaEst.appendChild(doc.createTextNode(e.getParada().getNombre()));
					estanciaElem.appendChild(paradaEst);
	
					if (e.isVip()) {
						Element vip = doc.createElement("vip");
						estanciaElem.appendChild(vip); 
					}
	
					estanciasElem.appendChild(estanciaElem);
				}
				carnetE.appendChild(estanciasElem);
					File carpeta = new File("ficheros");
				if (!carpeta.exists()) {
					carpeta.mkdir();
				}
	

				File file = new File("ficheros/carnet_" + p.getNombre() + ".xml");
				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(file);
				transformer.transform(source, result);
	
				ret = true;
			} catch (Exception e) {
				System.out.println("Error al exportar carnet a XML: " + e.getMessage());
				e.printStackTrace();
			}
			
	
			return ret;
	
		}
		public void mostrarInformeCarnet() {
		    try {
		        String reportPath = "src/main/resources/reports/DatosCarnet.jasper";

		        Map<String, Object> parameters = new HashMap<>();
		        parameters.put("usuario_id", Sesion.getId());

		        try (Connection conn = dt.getConnection()) {
		            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, conn);

		            JasperViewer viewer = new JasperViewer(jasperPrint, false);
		            viewer.setTitle("Informe del Carnet");
		            viewer.setVisible(true);
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		        mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo mostrar el informe: " + e.getMessage());
		    }
		}

		@FXML
		public void cerrarSesion() {
			stageManger.switchScene(FxmlView.LOGIN);
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
	            URL url = getClass().getResource("/ayuda/MenuPeregrino.html");
	            if (url != null) {
	                webView.getEngine().load(url.toExternalForm());
	            } else {
	                webView.getEngine().loadContent("<h1>Error</h1><p>No se encontró el archivo de ayuda.</p>");
	            }

	            Scene scene = new Scene(webView, 800, 600);
	            stage.setScene(scene);
	            stage.initModality(Modality.APPLICATION_MODAL);

	            // Obtener ventana principal como owner
	            Stage primaryStage = (Stage) menubarPeregrino.getScene().getWindow(); 
	            stage.initOwner(primaryStage);

	            stage.setResizable(false);
	            stage.showAndWait();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}