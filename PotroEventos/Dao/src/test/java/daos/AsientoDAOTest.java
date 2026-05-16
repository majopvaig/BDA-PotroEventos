/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import Entitys.Asiento;
import Entitys.ENUMS.TipoUbicacionP;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import conexion.ConexionMongo;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class AsientoDAOTest {

    private static MongoClient mongoClient;
    private static String databaseName;
    private AsientoDAO asientoDAO;

    @BeforeAll
    public static void setUpClass() {
        String connectionString = "mongodb://localhost:27017";
        databaseName = "test_potro_eventos_" + UUID.randomUUID().toString().substring(0, 8);
        mongoClient = MongoClients.create(connectionString);
        mongoClient.listDatabaseNames().first();
        ConexionMongo.useTestDatabase(databaseName);
    }

    @AfterAll
    public static void tearDownClass() {
        if (mongoClient != null) {
            mongoClient.getDatabase(databaseName).drop();
            mongoClient.close();
        }
        ConexionMongo.resetToProductionDatabase();
    }

    @BeforeEach
    public void setUp() {
        asientoDAO = AsientoDAO.getInstance();
        MongoCollection<Document> coleccion = mongoClient.getDatabase(databaseName).getCollection("asientos");
        coleccion.deleteMany(new Document());
    }

    @Test
    public void testConsultarAsientos() throws Exception {
        
        MongoCollection<Document> coleccion = mongoClient.getDatabase(databaseName).getCollection("asientos");
        MongoCollection<Document> ubicacionesCol = mongoClient.getDatabase(databaseName).getCollection("ubicaciones");
        MongoCollection<Document> seccionesCol = mongoClient.getDatabase(databaseName).getCollection("secciones");

        ObjectId ubicacionId = new ObjectId();
        ObjectId seccionId = new ObjectId();

        ubicacionesCol.insertOne(new Document("_id", ubicacionId)
            .append("nombre", "Estadio Potros")
            .append("capacidad", 100)
            .append("tipo", TipoUbicacionP.AIRELIBRE)
            .append("secciones", new ArrayList<>()));
        
        seccionesCol.insertOne(new Document("_id", seccionId)
            .append("nombre", "A")
            .append("ubicacionId", ubicacionId)
            .append("capacidad", 50)
            .append("precioBase", 500L));

        Document asiento1 = new Document("_id", new ObjectId())
                .append("fila", "A")
                .append("numero", 1)
                .append("ubicacion", ubicacionId)
                .append("seccion", seccionId);

        Document asiento2 = new Document("_id", new ObjectId())
                .append("fila", "B")
                .append("numero", 2)
                .append("ubicacion", ubicacionId)
                .append("seccion", seccionId);

        coleccion.insertOne(asiento1);
        coleccion.insertOne(asiento2);

        List<Asiento> asientos = asientoDAO.consultarAsientos();

        assertNotNull(asientos);
        assertEquals(2, asientos.size());
    }

    @Test
    public void testConsultarAsientos_Vacio() throws Exception {
        List<Asiento> asientos = asientoDAO.consultarAsientos();
        assertNotNull(asientos);
        assertTrue(asientos.isEmpty());
    }
    
    @Test
    public void testGetInstance() {
        AsientoDAO instance1 = AsientoDAO.getInstance();
        AsientoDAO instance2 = AsientoDAO.getInstance();
        assertNotNull(instance1);
        assertSame(instance1, instance2);
    }
    
    @Test
    public void testConsultarPorID() throws Exception {
        MongoCollection<Document> coleccion = mongoClient.getDatabase(databaseName).getCollection("asientos");
        ObjectId id = new ObjectId();
        Document asiento = new Document("_id", id)
                .append("fila", "C")
                .append("numero", 3);
        coleccion.insertOne(asiento);
        Asiento resultado = asientoDAO.consultarPorID(id.toString());
        assertNotNull(resultado);
    }
}