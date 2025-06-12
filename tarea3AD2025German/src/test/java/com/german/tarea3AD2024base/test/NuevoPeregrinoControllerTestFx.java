package com.german.tarea3AD2024base.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.german.tarea3AD2024base.modelo.Carnet;
import com.german.tarea3AD2024base.modelo.Parada;
import com.german.tarea3AD2024base.modelo.Peregrino;
import com.german.tarea3AD2024base.modelo.Rol;
import com.german.tarea3AD2024base.modelo.User;
import com.german.tarea3AD2024base.services.CarnetServicio;
import com.german.tarea3AD2024base.services.ParadaServicio;
import com.german.tarea3AD2024base.services.PeregrinoParadaServicio;
import com.german.tarea3AD2024base.services.PeregrinoServicio;
import com.german.tarea3AD2024base.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NuevoPeregrinoControllerTestFx {

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

    @Test
    void testRegistroExitosoPeregrino() {
        // Crear usuario
        User user = new User();
        user.setEmail("peregrinoTest@example.com");
        user.setPassword("password123");
        user.setRol(Rol.PEREGRINO);
        userService.save(user);

        // Obtener parada
        Parada parada = paradaServicio.paradaPorNombreyRegion("Santiago", 'A');
        assertNotNull(parada);

        // Crear peregrino
        Peregrino peregrino = new Peregrino();
        peregrino.setNombre("Peregrino Test");
        peregrino.setNacionalidad("España");
        peregrino.setUsuario(user);
        peregrinoServicio.guardar(peregrino);

        // Recuperar peregrino
        Peregrino recuperado = peregrinoServicio.encontrarPorNombre("Peregrino Test");
        assertNotNull(recuperado);
        assertEquals("España", recuperado.getNacionalidad());

        // Crear carnet
        Carnet carnet = new Carnet();
        carnet.setPeregrino(recuperado);
        carnet.setParadaInicial(parada);
        carnet.setFechaexp(java.time.LocalDate.now());
        carnet.setNvips(0);
        carnet.setDistancia(0.0);
        carnetServicio.guardar(carnet);

        Carnet carnetGuardado = carnetServicio.encontrarCarnetPorPeregrino(recuperado.getId());
        assertNotNull(carnetGuardado);
        assertEquals("Santiago", carnetGuardado.getParadaInicial().getNombre());
    }
    
    @Test
    void testRegistroConUsuarioExistente() {
        // Crear usuario original
        User user = new User();
        user.setEmail("duplicado@example.com");
        user.setPassword("1234");
        user.setRol(Rol.PEREGRINO);
        userService.save(user);

        // Intentar crear otro con el mismo email
        boolean yaExiste = userService.existeUsuarioConEmail("duplicado@example.com");
        assertEquals(true, yaExiste, "El sistema debería detectar el correo duplicado");
    }

}