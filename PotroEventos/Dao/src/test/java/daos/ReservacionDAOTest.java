/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import Entitys.Boleto;
import Entitys.Reservacion;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Dayanara Peralta G
 */
public class ReservacionDAOTest {
    
    public ReservacionDAOTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class ReservacionDAO.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        ReservacionDAO expResult = null;
        ReservacionDAO result = ReservacionDAO.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of guardarReservacion method, of class ReservacionDAO.
     */
    @Test
    public void testGuardarReservacion() throws Exception {
        System.out.println("guardarReservacion");
        Reservacion reservacion = null;
        ReservacionDAO instance = null;
        Reservacion expResult = null;
        Reservacion result = instance.guardarReservacion(reservacion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtenerReservacionesUsuario method, of class ReservacionDAO.
     */
    @Test
    public void testObtenerReservacionesUsuario() throws Exception {
        System.out.println("obtenerReservacionesUsuario");
        String idUsuario = "";
        ReservacionDAO instance = null;
        List<Reservacion> expResult = null;
        List<Reservacion> result = instance.obtenerReservacionesUsuario(idUsuario);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtenerBoleto method, of class ReservacionDAO.
     */
    @Test
    public void testObtenerBoleto() throws Exception {
        System.out.println("obtenerBoleto");
        String idReservacion = "";
        ReservacionDAO instance = null;
        Boleto expResult = null;
        Boleto result = instance.obtenerBoleto(idReservacion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
