/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import Entitys.Categoria;
import Entitys.ENUMS.CategoriaEvento;
import Entitys.Evento;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import conexion.ConexionMongo;
import dtos.ENUMS.CategoriaEventoDTO;
import dtos.ENUMS.EstadoEventoDTO;
import dtos.ENUMS.TipoEventoN;
import dtos.ENUMS.TipoUbicacionN;
import java.util.ArrayList;
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
public class EventoDAOTest {
    private static String databaseName;
    private EventoDAO eventoDAO;
    
    public EventoDAOTest() {
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
        eventoDAO = EventoDAO.getInstance();
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        MongoCollection<Document> eventosCol = base.getCollection("eventos");
        MongoCollection<Document> categoriaCol = base.getCollection("categorias");
        MongoCollection<Document> ubicacionesCol = base.getCollection("ubicaciones");
        
        eventosCol.deleteMany(new Document());
        categoriaCol.deleteMany(new Document());
        ubicacionesCol.deleteMany(new Document());
    }
    
    /**
     * Test of getInstance method, of class EventoDAO.
     */
    @Test
    public void testGetInstance() {
        EventoDAO instance1 = EventoDAO.getInstance();
        EventoDAO instance2 = EventoDAO.getInstance();
        assertNotNull(instance1);
        assertEquals(instance1, instance2);
    }

    /**
     * Test of buscarPorId method, of class EventoDAO.
     */
    @Test
    public void testBuscarPorId() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        MongoCollection<Document> eventosCol = base.getCollection("eventos");
        MongoCollection<Document> categoriaCol = base.getCollection("categorias");
        MongoCollection<Document> ubicacionesCol = base.getCollection("ubicaciones");
        
        ObjectId categoriaId = new ObjectId();
        categoriaCol.insertOne(new Document("_id", categoriaId)
                .append("urlImagen", "test.jpg")
                .append("nombre", CategoriaEventoDTO.NATACION.name())
        );
        
        ObjectId ubicacionId = new ObjectId();
        ubicacionesCol.insertOne(new Document("_id", ubicacionId)
            .append("nombre", "Estadio Potros")
            .append("capacidad", 100)
            .append("tipoUbicacion", TipoUbicacionN.AIRELIBRE.name())
            .append("secciones", new ArrayList<>())
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
        
        Evento result = eventoDAO.buscarPorId(eventoId.toString());
        assertNotNull(result);
    }

    /**
     * Test of buscarTodosCategoria method, of class EventoDAO.
     */
    @Test
    public void testBuscarTodosCategoria() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        MongoCollection<Document> eventosCol = base.getCollection("eventos");
        MongoCollection<Document> categoriaCol = base.getCollection("categorias");
        MongoCollection<Document> ubicacionesCol = base.getCollection("ubicaciones");

        ObjectId categoriaId = new ObjectId();
        categoriaCol.insertOne(new Document("_id", categoriaId)
                .append("urlImagen", "test.jpg")
                .append("nombre", CategoriaEventoDTO.NATACION.name())
        );

        ObjectId ubicacionId = new ObjectId();
        ubicacionesCol.insertOne(new Document("_id", ubicacionId)
                .append("nombre", "Estadio Potros")
                .append("capacidad", 100)
                .append("tipoUbicacion", TipoUbicacionN.AIRELIBRE.name())
                .append("secciones", new ArrayList<>())
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

        Categoria categoria = new Categoria();
        categoria.setId(categoriaId.toString());
        categoria.setNombre(CategoriaEvento.NATACION);
        categoria.setUrlImagen("test.jpg");

        List<Evento> result = eventoDAO.buscarTodosCategoria(categoria);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    /**
     * Test of reducirDisponibilidad method, of class EventoDAO.
     */
    @Test
    public void testReducirDisponibilidad() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        MongoCollection<Document> eventosCol = base.getCollection("eventos");
        MongoCollection<Document> categoriaCol = base.getCollection("categorias");
        MongoCollection<Document> ubicacionesCol = base.getCollection("ubicaciones");

        ObjectId categoriaId = new ObjectId();
        categoriaCol.insertOne(new Document("_id", categoriaId)
                .append("urlImagen", "test.jpg")
                .append("nombre", CategoriaEventoDTO.NATACION.name())
        );

        ObjectId ubicacionId = new ObjectId();
        ubicacionesCol.insertOne(new Document("_id", ubicacionId)
                .append("nombre", "Estadio Potros")
                .append("capacidad", 100)
                .append("tipoUbicacion", TipoUbicacionN.AIRELIBRE.name())
                .append("secciones", new ArrayList<>())
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
        
        boolean result = eventoDAO.reducirDisponibilidad(eventoId.toString());
        assertTrue(result);
        
        Document eventoActualizado = eventosCol.find(new Document("_id", eventoId)).first();
        assertEquals(99, eventoActualizado.getInteger("disponibilidad"));
    }
    
}
