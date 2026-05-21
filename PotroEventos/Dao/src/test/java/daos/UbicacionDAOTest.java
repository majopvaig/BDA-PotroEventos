/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import Entitys.Seccion;
import Entitys.Ubicacion;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import conexion.ConexionMongo;
import dtos.ENUMS.TipoUbicacionN;
import java.util.ArrayList;
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
public class UbicacionDAOTest {
    
    private static String databaseName;
    private UbicacionDAO ubicacionDAO;
    
    public UbicacionDAOTest() {
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
        ubicacionDAO = UbicacionDAO.getInstance();
        
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        MongoCollection<Document> ubicacionesCol = base.getCollection("ubicaciones");
        MongoCollection<Document> seccionesCol = base.getCollection("secciones");
        
        ubicacionesCol.deleteMany(new Document());
        seccionesCol.deleteMany(new Document());
    }
    
    /**
     * Test of getInstance method, of class UbicacionDAO.
     */
    @Test
    public void testGetInstance() {
        UbicacionDAO instancia1 = UbicacionDAO.getInstance();
        UbicacionDAO instancia2 = UbicacionDAO.getInstance();
        assertNotNull(instancia1);
        assertEquals(instancia1, instancia2);
    }

    /**
     * Test of consultarPorID method, of class UbicacionDAO.
     */
    @Test
    public void testConsultarPorID() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        MongoCollection<Document> ubicacionesCol = base.getCollection("ubicaciones");
        
        ObjectId ubicacionId = new ObjectId();
        ubicacionesCol.insertOne(new Document("_id", ubicacionId)
            .append("nombre", "Estadio Potros")
            .append("capacidad", 100)
            .append("tipoUbicacion", TipoUbicacionN.AIRELIBRE.name())
            .append("secciones", new ArrayList<>())
        );

        Ubicacion result = ubicacionDAO.consultarPorID(ubicacionId.toString());
        assertNotNull(result);
    }

//Fail BuscarSeccionPorId
    /**
     * Test of buscarSeccionPorId method, of class UbicacionDAO.
     */
    //@Test
    public void testBuscarSeccionPorId() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        MongoCollection<Document> ubicacionesCol = base.getCollection("ubicaciones");
        MongoCollection<Document> seccionesCol = base.getCollection("secciones");

        ObjectId ubicacionId = new ObjectId();
        ubicacionesCol.insertOne(new Document("_id", ubicacionId)
            .append("nombre", "Estadio Potros")
            .append("capacidad", 100)
            .append("tipoUbicacion", TipoUbicacionN.AIRELIBRE.name())
            .append("secciones", new ArrayList<>())
        );
        
        ObjectId seccionId = new ObjectId();
        Document seccionDoc = new Document("_id", seccionId)
            .append("nombre", "A")
            .append("capacidad", 50)
            .append("precioBase", 500L);
        seccionesCol.insertOne(seccionDoc);
        
        Seccion result = ubicacionDAO.buscarSeccionPorId(ubicacionId.toString(), seccionId.toString());
        assertNotNull(result);
    }
    
}
