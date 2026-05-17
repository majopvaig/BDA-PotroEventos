/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import Entitys.Asiento;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import conexion.ConexionMongo;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import dtos.ENUMS.TipoUbicacionN;

public class AsientoDAOTest {

    private static String databaseName;
    private AsientoDAO asientoDAO;

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
        asientoDAO = AsientoDAO.getInstance();
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        
        MongoCollection<Document> asientosCol = base.getCollection("asientos");
        MongoCollection<Document> ubicacionesCol = base.getCollection("ubicaciones");
        MongoCollection<Document> seccionesCol = base.getCollection("secciones");

        asientosCol.deleteMany(new Document());
        ubicacionesCol.deleteMany(new Document());
        seccionesCol.deleteMany(new Document());
    }

    @Test
    public void testConsultarAsientos() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        
        MongoCollection<Document> asientosCol = base.getCollection("asientos");
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

        Document asiento1 = new Document("_id", new ObjectId())
                .append("fila", "A")
                .append("numero", 1)
                .append("ubicacion", ubicacionId)
                .append("seccion", seccionId);
        asientosCol.insertOne(asiento1);
        
        Document asiento2 = new Document("_id", new ObjectId())
                .append("fila", "B")
                .append("numero", 2)
                .append("ubicacion", ubicacionId)
                .append("seccion", seccionId);
        asientosCol.insertOne(asiento2);

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
        assertEquals(instance1, instance2);
    }
    
    @Test
    public void testConsultarPorID() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        
        MongoCollection<Document> asientosCol = base.getCollection("asientos");
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
        
        ObjectId idAsiento = new ObjectId();
        Document asientoDoc = new Document("_id", idAsiento)
                .append("fila", "C")
                .append("numero", 2)
                .append("ubicacion", ubicacionId)
                .append("seccion", seccionId);
        asientosCol.insertOne(asientoDoc);
        Asiento resultado = asientoDAO.consultarPorID(idAsiento.toString());
        assertNotNull(resultado);
    }
}