/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import Entitys.AsientoEvento;
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
public class AsientoEventoDAOTest {
    
    public AsientoEventoDAOTest() {
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
     * Test of getInstancia method, of class AsientoEventoDAO.
     */
    @Test
    public void testGetInstancia() {
        System.out.println("getInstancia");
        AsientoEventoDAO expResult = null;
        AsientoEventoDAO result = AsientoEventoDAO.getInstancia();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarPorEvento method, of class AsientoEventoDAO.
     */
    @Test
    public void testBuscarPorEvento() throws Exception {
        System.out.println("buscarPorEvento");
        String idEvento = "";
        AsientoEventoDAO instance = null;
        List<AsientoEvento> expResult = null;
        List<AsientoEvento> result = instance.buscarPorEvento(idEvento);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reservarAsiento method, of class AsientoEventoDAO.
     */
    @Test
    public void testReservarAsiento() throws Exception {
        System.out.println("reservarAsiento");
        String idAsiento = "";
        AsientoEventoDAO instance = null;
        boolean expResult = false;
        boolean result = instance.reservarAsiento(idAsiento);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of liberarAsiento method, of class AsientoEventoDAO.
     */
    @Test
    public void testLiberarAsiento() throws Exception {
        System.out.println("liberarAsiento");
        String idAsiento = "";
        AsientoEventoDAO instance = null;
        boolean expResult = false;
        boolean result = instance.liberarAsiento(idAsiento);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of venderAsiento method, of class AsientoEventoDAO.
     */
    @Test
    public void testVenderAsiento() throws Exception {
        System.out.println("venderAsiento");
        String idAsiento = "";
        AsientoEventoDAO instance = null;
        boolean expResult = false;
        boolean result = instance.venderAsiento(idAsiento);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of consultarPorId method, of class AsientoEventoDAO.
     */
    @Test
    public void testConsultarPorId() throws Exception {
        System.out.println("consultarPorId");
        String idAsiento = "";
        AsientoEventoDAO instance = null;
        AsientoEvento expResult = null;
        AsientoEvento result = instance.consultarPorId(idAsiento);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
