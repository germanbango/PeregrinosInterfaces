
package com.german.tarea3AD2024base.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.german.tarea3AD2024base.config.StageManager;
import com.german.tarea3AD2024base.modelo.Rol;
import com.german.tarea3AD2024base.modelo.User;
import com.german.tarea3AD2024base.services.UserService;
import com.german.tarea3AD2024base.utiles.Sesion;
import com.german.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Ram Alapure
 * @since 05-04-2017
 */

@Controller
public class LoginController implements Initializable {

	@FXML
	public Button btnLogin;

	@FXML
	public
	 PasswordField password;

	@FXML
	 public TextField username;
 
	@FXML
	public Label lblLogin;

	@FXML
	private MenuItem itemAyuda;
	
	@Autowired
	public UserService userService;

	@Lazy
	@Autowired
	private StageManager stageManager; 

	
	@FXML
	private void registrarPeregrino(){
		stageManager.switchScene(FxmlView.REGISTRAR_PEREGRINO);
	}
	@FXML
	public void login(ActionEvent event) {

		if (comprobarLogin(getUsername(), getPassword())) {
			stageManager.switchScene(FxmlView.USER);
		} else if (userService.authenticate(getUsername(), getPassword())) {
			User loggedUser = userService.findByEmail(getUsername()); 
			if (loggedUser != null && loggedUser.getRol() != null && loggedUser.getRol().equals(Rol.RESPONSABLE)) { 
				Sesion.setId(loggedUser.getId());
				Sesion.setUsuario(loggedUser.getEmail());
				Sesion.setPerfil(loggedUser.getRol());
				stageManager.switchScene(FxmlView.MENU_RESPONSABLE);
			}else if(loggedUser != null && loggedUser.getRol() != null && loggedUser.getRol().equals(Rol.PEREGRINO)) {
				Sesion.setId(loggedUser.getId());
				Sesion.setUsuario(loggedUser.getEmail());
				Sesion.setPerfil(loggedUser.getRol());
				stageManager.switchScene(FxmlView.MENU_PEREGRINO);
			}
		} else {
			lblLogin.setText("Inicio fallido.");
		}
	}


	public String getPassword() {
		return password.getText();
	}

	public String getUsername() {
		return username.getText();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itemAyuda.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));

	}

	private static boolean comprobarLogin(String nombre, String contrasena) {
		boolean ret = false;
		Properties properties = new Properties();
		/// Comprobar si es el ADMIN
		try {
			FileInputStream fis = new FileInputStream("src/main/resources/application.properties");
			properties.load(fis);
			String adminnombre = properties.getProperty("nombreadmin");
			String admincontrasena = properties.getProperty("passadmin");
			if (nombre.equals(adminnombre) && contrasena.equals(admincontrasena)) {
				Sesion.setId(0L);
				Sesion.setUsuario(nombre);
				Sesion.setPerfil(Rol.ADMIN);
				ret = true;
			} else {
				return ret;
			}
		} catch (IOException e) {
			System.out.println("Se ha producido un error de tipo IOexception:" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return ret;
	}
	
	 @FXML
	    private void mostrarAyuda() {
	        
	    }
	public void setUserService(UserService userServiceMock) {
		// TODO Auto-generated method stub
		
	}
}
