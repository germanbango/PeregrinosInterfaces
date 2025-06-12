package com.german.tarea3AD2024base.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.german.tarea3AD2024base.config.StageManager;
import com.german.tarea3AD2024base.controller.LoginController;
import com.german.tarea3AD2024base.modelo.Rol;
import com.german.tarea3AD2024base.modelo.User;
import com.german.tarea3AD2024base.services.UserService;
import com.german.tarea3AD2024base.utiles.Sesion;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private StageManager stageManager;

    @InjectMocks
    private LoginController loginController;


    @BeforeAll
    public static void initToolkit() {
        new JFXPanel(); // inicializa JavaFX toolkit
    }
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializamos los controles FXML manualmente
        loginController.password = new PasswordField();
        loginController.username = new TextField();
        loginController.lblLogin = new Label();
    }

    @Test 
    void testLoginResponsableExitoso() {
        User user = new User();
        user.setEmail("resp@correo.com");
        user.setRol(Rol.RESPONSABLE);
        user.setId(10L);

        when(userService.authenticate("resp@correo.com", "1234")).thenReturn(true);
        when(userService.findByEmail("resp@correo.com")).thenReturn(user);

        loginController.username.setText("resp@correo.com");
        loginController.password.setText("1234");

        loginController.login(null);

        assertEquals("resp@correo.com", Sesion.getUsuario());
        assertEquals(Rol.RESPONSABLE, Sesion.getPerfil());
        verify(stageManager).switchScene(any());
    }

    @Test
    void testLoginPeregrinoExitoso() {
        User user = new User();
        user.setEmail("pere@correo.com");
        user.setRol(Rol.PEREGRINO);
        user.setId(5L);

        when(userService.authenticate("pere@correo.com", "abcd")).thenReturn(true);
        when(userService.findByEmail("pere@correo.com")).thenReturn(user);

        loginController.username.setText("pere@correo.com");
        loginController.password.setText("abcd");

        loginController.login(null);

        assertEquals("pere@correo.com", Sesion.getUsuario());
        assertEquals(Rol.PEREGRINO, Sesion.getPerfil());
        verify(stageManager).switchScene(any());
    }

    @Test
    void testLoginFallido() {
        when(userService.authenticate("fallo@correo.com", "fail")).thenReturn(false);

        loginController.username.setText("fallo@correo.com");
        loginController.password.setText("fail");

        loginController.login(null); 

        assertEquals("Inicio fallido.", loginController.lblLogin.getText());
    }

   
}
