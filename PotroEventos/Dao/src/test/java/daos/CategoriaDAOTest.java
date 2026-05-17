/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import Entitys.Categoria;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import conexion.ConexionMongo;
import dtos.ENUMS.CategoriaEventoDTO;
import java.util.List;
import java.util.UUID;
import org.bson.Document;
import org.bson.types.ObjectId;
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
    
    private static String databaseName;
    private CategoriaDAO categoriaDAO;
    
    public CategoriaDAOTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        databaseName = "test_potro_eventos_" + UUID.randomUUID().toString().substring(0, 8);
        ConexionMongo.useTestDatabase(databaseName);
    }
    
    @AfterAll
    public static void tearDownClass() {
        MongoDatabase db = ConexionMongo.obtenerBaseDatos();
        if (db != null) {
            db.drop();
        }
        ConexionMongo.resetToProductionDatabase();
        ConexionMongo.cerrarCliente();
    }
    
    @BeforeEach
    public void setUp() {
        categoriaDAO = CategoriaDAO.getInstance();
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        MongoCollection<Document> categoriaCol = base.getCollection("categorias");
        categoriaCol.deleteMany(new Document());
    }
    
    /**
     * Test of getInstance method, of class CategoriaDAO.
     */
    @Test
    public void testGetInstance() {
        CategoriaDAO instance1 = CategoriaDAO.getInstance();
        CategoriaDAO instance2 = CategoriaDAO.getInstance();
        assertNotNull(instance1);
        assertEquals(instance1, instance2);
    }

    /**
     * Test of consultarCategorias method, of class CategoriaDAO.
     */
    @Test
    public void testConsultarCategorias() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        MongoCollection<Document> categoriaCol = base.getCollection("categorias");
        
        ObjectId categoriaId = new ObjectId();
        Document categoriaDoc = new Document("_id", categoriaId)
                .append("urlImagen", "test.jpg")
                .append("nombre", CategoriaEventoDTO.ARTE);
        categoriaCol.insertOne(categoriaDoc);
        
        List<Categoria> result = categoriaDAO.consultarCategorias();
        assertNotNull(result);
        assertEquals(1, result.size());
                
    }

    /**
     * Test of consultarPorId method, of class CategoriaDAO.
     */
    @Test
    public void testConsultarPorId() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        MongoCollection<Document> categoriaCol = base.getCollection("categorias");
        
        ObjectId categoriaId = new ObjectId();
        Document categoriaDoc = new Document("_id", categoriaId)
                .append("urlImagen", "test.jpg")
                .append("nombre", CategoriaEventoDTO.ARTE);
        categoriaCol.insertOne(categoriaDoc);
        
        Categoria result = categoriaDAO.consultarPorId(categoriaId.toString());
        assertNotNull(result);
    }
    
}
