/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import Entitys.Categoria;
import Entitys.Evento;
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
public class EventoDAOTest {
    
    public EventoDAOTest() {
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
     * Test of getInstance method, of class EventoDAO.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        EventoDAO expResult = null;
        EventoDAO result = EventoDAO.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarPorId method, of class EventoDAO.
     */
    @Test
    public void testBuscarPorId() throws Exception {
        System.out.println("buscarPorId");
        String idEvento = "";
        EventoDAO instance = null;
        Evento expResult = null;
        Evento result = instance.buscarPorId(idEvento);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarTodosCategoria method, of class EventoDAO.
     */
    @Test
    public void testBuscarTodosCategoria() throws Exception {
        System.out.println("buscarTodosCategoria");
        Categoria categoria = null;
        EventoDAO instance = null;
        List<Evento> expResult = null;
        List<Evento> result = instance.buscarTodosCategoria(categoria);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reducirDisponibilidad method, of class EventoDAO.
     */
    @Test
    public void testReducirDisponibilidad() throws Exception {
        System.out.println("reducirDisponibilidad");
        String idEvento = "";
        EventoDAO instance = null;
        boolean expResult = false;
        boolean result = instance.reducirDisponibilidad(idEvento);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
