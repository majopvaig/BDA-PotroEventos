/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import Entitys.Seccion;
import Entitys.Ubicacion;
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
public class UbicacionDAOTest {
    
    public UbicacionDAOTest() {
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
     * Test of getInstance method, of class UbicacionDAO.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        UbicacionDAO expResult = null;
        UbicacionDAO result = UbicacionDAO.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of consultarPorID method, of class UbicacionDAO.
     */
    @Test
    public void testConsultarPorID() throws Exception {
        System.out.println("consultarPorID");
        String idUbicacion = "";
        UbicacionDAO instance = null;
        Ubicacion expResult = null;
        Ubicacion result = instance.consultarPorID(idUbicacion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarSeccionPorId method, of class UbicacionDAO.
     */
    @Test
    public void testBuscarSeccionPorId() throws Exception {
        System.out.println("buscarSeccionPorId");
        String idUbicacion = "";
        String idSeccion = "";
        UbicacionDAO instance = null;
        Seccion expResult = null;
        Seccion result = instance.buscarSeccionPorId(idUbicacion, idSeccion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
