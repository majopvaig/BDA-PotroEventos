/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import Entitys.AsientoEvento;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import conexion.ConexionMongo;
import dtos.ENUMS.CategoriaEventoDTO;
import dtos.ENUMS.EstadoAsientoDTO;
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
public class AsientoEventoDAOTest {
    
    private static String databaseName;
    private AsientoEventoDAO asientoEventoDAO;
    
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
        asientoEventoDAO = AsientoEventoDAO.getInstancia();
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        
        MongoCollection<Document> coleccion = base.getCollection("asientoEventos");
        MongoCollection<Document> asientosCol = base.getCollection("asientos");
        MongoCollection<Document> ubicacionesCol = base.getCollection("ubicaciones");
        MongoCollection<Document> seccionesCol = base.getCollection("secciones");
        MongoCollection<Document> eventosCol = base.getCollection("eventos");
        MongoCollection<Document> categoriaCol = base.getCollection("categorias");
        
        coleccion.deleteMany(new Document());
        asientosCol.deleteMany(new Document());
        ubicacionesCol.deleteMany(new Document());
        seccionesCol.deleteMany(new Document());
        eventosCol.deleteMany(new Document());
        categoriaCol.deleteMany(new Document());
    }
    
    /**
     * Test of getInstancia method, of class AsientoEventoDAO.
     */
    @Test
    public void testGetInstancia() {
        AsientoEventoDAO instancia1 = AsientoEventoDAO.getInstancia();
        AsientoEventoDAO instancia2 = AsientoEventoDAO.getInstancia();
        assertNotNull(instancia1);
        assertEquals(instancia1, instancia2);
    }

    /**
     * Test of buscarPorEvento method, of class AsientoEventoDAO.
     */
    @Test
    public void testBuscarPorEvento() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        MongoCollection<Document> coleccion = base.getCollection("asientoEventos");
        MongoCollection<Document> asientosCol = base.getCollection("asientos");
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
        
        ObjectId idAsiento = new ObjectId();
        Document asientoDoc = new Document("_id", idAsiento)
                .append("fila", "A")
                .append("numero", 1)
                .append("ubicacion", ubicacionId)
                .append("seccion", seccionId);
        asientosCol.insertOne(asientoDoc);
        
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
           
        ObjectId asientoEveId = new ObjectId();
        Document asientoEve = new Document("_id", asientoEveId)
                .append("precio", 200)
                .append("estado", EstadoAsientoDTO.DISPONIBLE.name())
                .append("asiento", idAsiento)
                .append("evento", eventoId);
        coleccion.insertOne(asientoEve);
        
        List<AsientoEvento> asientosEventos = asientoEventoDAO.buscarPorEvento(eventoId.toString());
        assertNotNull(asientosEventos);
        assertEquals(1, asientosEventos.size());
    }
    
    /**
     * Test of reservarAsiento method, of class AsientoEventoDAO.
     */
    @Test
    public void testReservarAsiento() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        MongoCollection<Document> coleccion = base.getCollection("asientoEventos");
        MongoCollection<Document> asientosCol = base.getCollection("asientos");
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
        
        ObjectId idAsiento = new ObjectId();
        Document asientoDoc = new Document("_id", idAsiento)
                .append("fila", "A")
                .append("numero", 1)
                .append("ubicacion", ubicacionId)
                .append("seccion", seccionId);
        asientosCol.insertOne(asientoDoc);
        
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
           
        ObjectId asientoEveId = new ObjectId();
        Document asientoEve = new Document("_id", asientoEveId)
                .append("precio", 200)
                .append("estado", EstadoAsientoDTO.DISPONIBLE.name())
                .append("asiento", idAsiento)
                .append("evento", eventoId);
        coleccion.insertOne(asientoEve);

        boolean result = asientoEventoDAO.reservarAsiento(asientoEveId.toString());
        assertTrue(result);
        assertEquals("RESERVADO", coleccion.find(Filters.eq("_id", asientoEveId)).first().getString("estado"));
    }

    /**
     * Test of liberarAsiento method, of class AsientoEventoDAO.
     */
    @Test
    public void testLiberarAsiento() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        MongoCollection<Document> coleccion = base.getCollection("asientoEventos");
        MongoCollection<Document> asientosCol = base.getCollection("asientos");
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
        
        ObjectId idAsiento = new ObjectId();
        Document asientoDoc = new Document("_id", idAsiento)
                .append("fila", "A")
                .append("numero", 1)
                .append("ubicacion", ubicacionId)
                .append("seccion", seccionId);
        asientosCol.insertOne(asientoDoc);
        
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
           
        ObjectId asientoEveId = new ObjectId();
        Document asientoEve = new Document("_id", asientoEveId)
                .append("precio", 200)
                .append("estado", EstadoAsientoDTO.RESERVADO.name())
                .append("asiento", idAsiento)
                .append("evento", eventoId);
        coleccion.insertOne(asientoEve);

        boolean result = asientoEventoDAO.liberarAsiento(asientoEveId.toString());
        assertTrue(result);
        assertEquals("DISPONIBLE", coleccion.find(Filters.eq("_id", asientoEveId)).first().getString("estado"));
    }

    /**
     * Test of venderAsiento method, of class AsientoEventoDAO.
     */
    @Test
    public void testVenderAsiento() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        MongoCollection<Document> coleccion = base.getCollection("asientoEventos");
        MongoCollection<Document> asientosCol = base.getCollection("asientos");
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
        
        ObjectId idAsiento = new ObjectId();
        Document asientoDoc = new Document("_id", idAsiento)
                .append("fila", "A")
                .append("numero", 1)
                .append("ubicacion", ubicacionId)
                .append("seccion", seccionId);
        asientosCol.insertOne(asientoDoc);
        
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
           
        ObjectId asientoEveId = new ObjectId();
        Document asientoEve = new Document("_id", asientoEveId)
                .append("precio", 200)
                .append("estado", EstadoAsientoDTO.DISPONIBLE.name())
                .append("asiento", idAsiento)
                .append("evento", eventoId);
        coleccion.insertOne(asientoEve);
        
        boolean result = asientoEventoDAO.venderAsiento(asientoEveId.toString());
        assertTrue(result);
        assertEquals("VENDIDO", coleccion.find(Filters.eq("_id", asientoEveId)).first().getString("estado"));
    }

    /**
     * Test of consultarPorId method, of class AsientoEventoDAO.
     */
    @Test
    public void testConsultarPorId() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        MongoCollection<Document> coleccion = base.getCollection("asientoEventos");
        MongoCollection<Document> asientosCol = base.getCollection("asientos");
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
        
        ObjectId idAsiento = new ObjectId();
        Document asientoDoc = new Document("_id", idAsiento)
                .append("fila", "A")
                .append("numero", 1)
                .append("ubicacion", ubicacionId)
                .append("seccion", seccionId);
        asientosCol.insertOne(asientoDoc);
        
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
           
        ObjectId asientoEveId = new ObjectId();
        Document asientoEve = new Document("_id", asientoEveId)
                .append("precio", 200)
                .append("estado", EstadoAsientoDTO.DISPONIBLE.name())
                .append("asiento", idAsiento)
                .append("evento", eventoId);
        coleccion.insertOne(asientoEve);
        
        AsientoEvento result = asientoEventoDAO.consultarPorId(asientoEveId.toString());
        assertNotNull(result);
    }
    
}
