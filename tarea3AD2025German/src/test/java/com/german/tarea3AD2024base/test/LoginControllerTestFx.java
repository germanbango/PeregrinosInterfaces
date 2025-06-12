package com.german.tarea3AD2024base.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import com.german.tarea3AD2024base.controller.LoginController;
import com.german.tarea3AD2024base.services.UserService;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginControllerTestFx extends ApplicationTest {

    private LoginController loginController;
    private UserService mockUserService;

    @Override
    public void start(Stage stage) {
        loginController = new LoginController();

        // Mock del UserService
        mockUserService = mock(UserService.class);
        loginController.userService = mockUserService;

        // Inicializar controles manualmente (como si fueran @FXML)
        loginController.username = new TextField();
        loginController.password = new PasswordField();
        loginController.lblLogin = new Label(); 
        loginController.btnLogin = new Button("Login");

        // (opcional) puedes configurar una mini escena si lo deseas
        // Aquí no es necesario porque trabajamos directamente con los controles
    }

    @BeforeEach
    public void resetMock() {
        reset(mockUserService);
    }

    @Test
    public void testLoginCorrecto() {
        // Preparar mock para aceptar el login
        when(mockUserService.authenticate("admin", "admin123")).thenReturn(true);

        // Simular entrada del usuario
        interact(() -> {
            loginController.username.setText("admin");
            loginController.password.setText("admin123");
            loginController.login(null);  // Ejecutar acción como si se pulsara el botón
        });

        // Verificar que el mensaje sea el correcto
        assertEquals("Login correcto.", loginController.lblLogin.getText());
        verify(mockUserService).authenticate("admin", "admin123");
    }

    @Test
    public void testLoginFallido() {
        // Simular fallo en login
        when(mockUserService.authenticate("user", "wrongpass")).thenReturn(false);

        interact(() -> {
            loginController.username.setText("user");
            loginController.password.setText("wrongpass");
            loginController.login(null);
        });

        assertEquals("Inicio fallido.", loginController.lblLogin.getText());
        verify(mockUserService).authenticate("user", "wrongpass");
    }
}
