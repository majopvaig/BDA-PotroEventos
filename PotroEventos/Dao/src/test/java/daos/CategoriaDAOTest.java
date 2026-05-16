/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import Entitys.Categoria;
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
public class CategoriaDAOTest {
    
    public CategoriaDAOTest() {
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
     * Test of getInstance method, of class CategoriaDAO.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        CategoriaDAO expResult = null;
        CategoriaDAO result = CategoriaDAO.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of consultarCategorias method, of class CategoriaDAO.
     */
    @Test
    public void testConsultarCategorias() throws Exception {
        System.out.println("consultarCategorias");
        CategoriaDAO instance = null;
        List<Categoria> expResult = null;
        List<Categoria> result = instance.consultarCategorias();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of consultarPorId method, of class CategoriaDAO.
     */
    @Test
    public void testConsultarPorId() throws Exception {
        System.out.println("consultarPorId");
        String idCategoria = "";
        CategoriaDAO instance = null;
        Categoria expResult = null;
        Categoria result = instance.consultarPorId(idCategoria);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
