package com.german.tarea3AD2024base.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.german.tarea3AD2024base.config.StageManager;
import com.german.tarea3AD2024base.controller.NuevoPeregrinoController;
import com.german.tarea3AD2024base.services.CarnetServicio;
import com.german.tarea3AD2024base.services.ParadaServicio;
import com.german.tarea3AD2024base.services.PeregrinoParadaServicio;
import com.german.tarea3AD2024base.services.PeregrinoServicio;
import com.german.tarea3AD2024base.services.UserService;

import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@ExtendWith(MockitoExtension.class)
class NuevoPeregrinoControllerTest {

    @InjectMocks
    private NuevoPeregrinoController controller;

    @Mock
    private ParadaServicio paradaServicio;
    @Mock
    private UserService userService;
    @Mock
    private PeregrinoServicio peregrinoServicio;
    @Mock
    private CarnetServicio carnetServicio;
    @Mock
    private PeregrinoParadaServicio peregrinoParadaServicio;

    @Mock
    private StageManager stageManager;

    @Mock
    private ComboBox<String> cmbParadas;
    @Mock
    private ComboBox<String> cmbNacionalidad;
    @Mock
    private TextField txtNombrePeregrino;
    @Mock
    private TextField txtUsuario;
    @Mock
    private PasswordField txtContrasena;

    @BeforeEach
    void setup() {
        controller.cmbParadas = cmbParadas;
        controller.cmbNacionalidad = cmbNacionalidad;
        controller.txtNombrePeregrino = txtNombrePeregrino;
        controller.txtUsuario = txtUsuario;
        controller.txtContrasena = txtContrasena;
    }

    @Test
    void testValidarTodosLosCamposValidos() {
        when(txtNombrePeregrino.getText()).thenReturn("Juan");
        when(cmbParadas.getValue()).thenReturn("Santiago  A");
        when(txtUsuario.getText()).thenReturn("juan@email.com");
        when(txtContrasena.getText()).thenReturn("claveSegura123");
        when(cmbNacionalidad.getValue()).thenReturn("España");
        when(userService.existeUsuarioConEmail("juan@email.com")).thenReturn(false);

        boolean resultado = controller.validar();

        assertTrue(resultado);
    }

    @Test
    void testValidarNombreVacio() {
        when(txtNombrePeregrino.getText()).thenReturn("");
        boolean resultado = controller.validar();
        assertFalse(resultado);
    }

    @Test
    void testValidarUsuarioYaExiste() {
        when(txtNombrePeregrino.getText()).thenReturn("Juan");
        when(cmbParadas.getValue()).thenReturn("Santiago  A");
        when(txtUsuario.getText()).thenReturn("existente@email.com");
        when(txtContrasena.getText()).thenReturn("claveSegura123");
        when(cmbNacionalidad.getValue()).thenReturn("España");
        when(userService.existeUsuarioConEmail("existente@email.com")).thenReturn(true);

        boolean resultado = controller.validar();

        assertFalse(resultado);
    }
}
