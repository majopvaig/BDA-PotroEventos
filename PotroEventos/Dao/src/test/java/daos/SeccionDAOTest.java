/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import Entitys.Seccion;
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
public class SeccionDAOTest {
    
    public SeccionDAOTest() {
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
     * Test of getInstance method, of class SeccionDAO.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        SeccionDAO expResult = null;
        SeccionDAO result = SeccionDAO.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarPorEvento method, of class SeccionDAO.
     */
    @Test
    public void testBuscarPorEvento() throws Exception {
        System.out.println("buscarPorEvento");
        String idEvento = "";
        SeccionDAO instance = new SeccionDAO();
        List<Seccion> expResult = null;
        List<Seccion> result = instance.buscarPorEvento(idEvento);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarPorId method, of class SeccionDAO.
     */
    @Test
    public void testBuscarPorId() throws Exception {
        System.out.println("buscarPorId");
        String idSeccion = "";
        SeccionDAO instance = new SeccionDAO();
        Seccion expResult = null;
        Seccion result = instance.buscarPorId(idSeccion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
