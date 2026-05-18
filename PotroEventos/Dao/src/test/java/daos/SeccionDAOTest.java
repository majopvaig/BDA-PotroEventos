/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import Entitys.Seccion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import conexion.ConexionMongo;
import dtos.ENUMS.CategoriaEventoDTO;
import dtos.ENUMS.EstadoEventoDTO;
import dtos.ENUMS.TipoEventoN;
import dtos.ENUMS.TipoUbicacionN;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bson.Document;
import org.bson.types.ObjectId;
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

    private static String databaseName;
    private SeccionDAO seccionDAO;

    public SeccionDAOTest() {
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
        seccionDAO = SeccionDAO.getInstance();
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();

        MongoCollection<Document> ubicacionesCol = base.getCollection("ubicaciones");
        MongoCollection<Document> seccionesCol = base.getCollection("secciones");
        MongoCollection<Document> eventosCol = base.getCollection("eventos");
        MongoCollection<Document> categoriaCol = base.getCollection("categorias");

        ubicacionesCol.deleteMany(new Document());
        seccionesCol.deleteMany(new Document());
        eventosCol.deleteMany(new Document());
        categoriaCol.deleteMany(new Document());
    }

    /**
     * Test of getInstance method, of class SeccionDAO.
     */
    @Test
    public void testGetInstance() {
        SeccionDAO instancia1 = SeccionDAO.getInstance();
        SeccionDAO instancia2 = SeccionDAO.getInstance();
        assertNotNull(instancia1);
        assertEquals(instancia1, instancia2);
    }

//Fali BuscarPorEvento
    /**
     * Test of buscarPorEvento method, of class SeccionDAO.
     */
    @Test
    public void testBuscarPorEvento() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        MongoCollection<Document> ubicacionesCol = base.getCollection("ubicaciones");
        MongoCollection<Document> seccionesCol = base.getCollection("secciones");
        MongoCollection<Document> eventosCol = base.getCollection("eventos");
        MongoCollection<Document> categoriaCol = base.getCollection("categorias");

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

        ObjectId categoriaId = new ObjectId();
        categoriaCol.insertOne(new Document("_id", categoriaId)
                .append("urlImagen", "test.jpg")
                .append("nombre", CategoriaEventoDTO.NATACION.name())
        );

        ObjectId eventoId = new ObjectId();
        Document eventoDoc = new Document("_id", eventoId)
                .append("categoria", new Document("_id", categoriaId)
                        .append("nombre", CategoriaEventoDTO.NATACION.name())
                        .append("urlImagen", "test.jpg"))
                .append("nombre", "Nado sincronizado")
                .append("informacion", "Patos nadando sincronizadamente")
                .append("fechaHora", new java.util.Date())
                .append("ubicacion", new Document("_id", ubicacionId)
                        .append("nombre", "Estadio Potros")
                        .append("capacidad", 100)
                        .append("tipoUbicacion", TipoUbicacionN.AIRELIBRE.name()))
                .append("estado", EstadoEventoDTO.ACTIVO.name())
                .append("urlImagen", "test.jpg")
                .append("gratuito", false)
                .append("tipo", TipoEventoN.ITSON.name())
                .append("disponibilidad", 100);
        eventosCol.insertOne(eventoDoc);

        List<Seccion> secciones = seccionDAO.buscarPorEvento(eventoId.toString());
        assertNotNull(secciones);
        assertEquals(1, secciones);
    }

}
