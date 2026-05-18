/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import Entitys.Boleto;
import Entitys.ENUMS.ReservacionEstado;
import Entitys.Reservacion;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import conexion.ConexionMongo;
import dtos.ENUMS.CategoriaEventoDTO;
import dtos.ENUMS.EstadoAsientoDTO;
import dtos.ENUMS.EstadoBoletoDTO;
import dtos.ENUMS.EstadoEventoDTO;
import dtos.ENUMS.ReservacionEstadoDTO;
import dtos.ENUMS.TipoEventoN;
import dtos.ENUMS.TipoUbicacionN;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
public class ReservacionDAOTest {

    private static String databaseName;
    private ReservacionDAO reservacionDAO;

    public ReservacionDAOTest() {
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
        reservacionDAO = ReservacionDAO.getInstance();
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();

        MongoCollection<Document> asientoEventosCol = base.getCollection("asientoEventos");
        MongoCollection<Document> asientosCol = base.getCollection("asientos");
        MongoCollection<Document> ubicacionesCol = base.getCollection("ubicaciones");
        MongoCollection<Document> seccionesCol = base.getCollection("secciones");
        MongoCollection<Document> eventosCol = base.getCollection("eventos");
        MongoCollection<Document> categoriaCol = base.getCollection("categorias");
        MongoCollection<Document> reservacionCol = base.getCollection("reservacion");
        MongoCollection<Document> boletoCol = base.getCollection("boleto");
        MongoCollection<Document> pagoCol = base.getCollection("pago");
        MongoCollection<Document> usuarioCol = base.getCollection("usuario");

        asientoEventosCol.deleteMany(new Document());
        asientosCol.deleteMany(new Document());
        ubicacionesCol.deleteMany(new Document());
        seccionesCol.deleteMany(new Document());
        eventosCol.deleteMany(new Document());
        categoriaCol.deleteMany(new Document());
        reservacionCol.deleteMany(new Document());
        boletoCol.deleteMany(new Document());
        pagoCol.deleteMany(new Document());
        usuarioCol.deleteMany(new Document());
    }

    /**
     * Test of getInstance method, of class ReservacionDAO.
     */
    @Test
    public void testGetInstance() {
        ReservacionDAO instancia1 = ReservacionDAO.getInstance();
        ReservacionDAO instancia2 = ReservacionDAO.getInstance();
        assertNotNull(instancia1);
        assertEquals(instancia1, instancia2);
    }

    /**
     * Test of guardarReservacion method, of class ReservacionDAO.
     */
    @Test
    public void testGuardarReservacion() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();

        MongoCollection<Document> asientoEventosCol = base.getCollection("asientoEventos");
        MongoCollection<Document> asientosCol = base.getCollection("asientos");
        MongoCollection<Document> ubicacionesCol = base.getCollection("ubicaciones");
        MongoCollection<Document> seccionesCol = base.getCollection("secciones");
        MongoCollection<Document> eventosCol = base.getCollection("eventos");
        MongoCollection<Document> categoriaCol = base.getCollection("categorias");
        MongoCollection<Document> reservacionCol = base.getCollection("reservacion");
        MongoCollection<Document> boletoCol = base.getCollection("boleto");
        MongoCollection<Document> pagoCol = base.getCollection("pago");
        MongoCollection<Document> usuarioCol = base.getCollection("usuario");

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
        asientoEventosCol.insertOne(asientoEve);

        ObjectId boletoId = new ObjectId();
        Document boletoDoc = new Document("_id", boletoId)
                .append("codigoQR", "testQR")
                .append("estado", EstadoBoletoDTO.ACTIVO.name())
                .append("evento", new Document("_id", eventoId)
                        .append("nombre", "Nado sincronizado"))
                .append("asientoEvento", new Document("_id", asientoEveId)
                        .append("estado", EstadoAsientoDTO.DISPONIBLE.name())
                        .append("precio", 200))
                .append("token", "testToken");
        boletoCol.insertOne(boletoDoc);

        ObjectId pagoId = new ObjectId();
        Document pagoDoc = new Document("_id", pagoId)
                .append("fechaOperacion", new Date())
                .append("importe", 120.0)
                .append("metodoPago", "TARJETA");
        pagoCol.insertOne(pagoDoc);

        ObjectId usuarioId = new ObjectId();
        Document usuarioDoc = new Document("_id", usuarioId)
                .append("nombre", "Aaron")
                .append("apellidoPaterno", "Burciaga")
                .append("apellidoMaterno", "Alcantar")
                .append("correo", "aaronA@gmail.com")
                .append("creditos", 230);
        usuarioCol.insertOne(usuarioDoc);

        ObjectId reservacionId = new ObjectId();
        Document reservacionDoc = new Document("_id", reservacionId)
                .append("total", 120.0)
                .append("boleto", boletoId)
                .append("pago", pagoId)
                .append("usuario", usuarioId)
                .append("fechaHora", new Date())
                .append("estado", ReservacionEstadoDTO.ACTIVA.name());
        reservacionCol.insertOne(reservacionDoc);

        Reservacion reservacion = new Reservacion();
        reservacion.setIdReservacion(reservacionId.toString());
        reservacion.setTotal(120.0);
        reservacion.setEstado(ReservacionEstado.ACTIVA);

        boolean result = reservacionDAO.guardarReservacion(reservacion);
        assertNotNull(result);
    }

    /**
     * Test of obtenerReservacionesUsuario method, of class ReservacionDAO.
     */
    @Test
    public void testObtenerReservacionesUsuario() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();

        MongoCollection<Document> asientoEventosCol = base.getCollection("asientoEventos");
        MongoCollection<Document> asientosCol = base.getCollection("asientos");
        MongoCollection<Document> ubicacionesCol = base.getCollection("ubicaciones");
        MongoCollection<Document> seccionesCol = base.getCollection("secciones");
        MongoCollection<Document> eventosCol = base.getCollection("eventos");
        MongoCollection<Document> categoriaCol = base.getCollection("categorias");
        MongoCollection<Document> reservacionCol = base.getCollection("reservacion");
        MongoCollection<Document> boletoCol = base.getCollection("boleto");
        MongoCollection<Document> pagoCol = base.getCollection("pago");
        MongoCollection<Document> usuarioCol = base.getCollection("usuario");

        ObjectId ubicacionId = new ObjectId();
        ubicacionesCol.insertOne(new Document("_id", ubicacionId)
                .append("nombre", "Estadio Potros")
                .append("capacidad", 100)
                .append("tipo", TipoUbicacionN.AIRELIBRE.name())
                .append("secciones", new ArrayList<>())
        );

        ObjectId seccionId = new ObjectId();
        Document seccionDoc = new Document("_id", seccionId)
                .append("nombre", "A")
                .append("capacidad", 50)
                .append("precioBase", 500L);
        seccionesCol.insertOne(seccionDoc);

        ObjectId asientoId = new ObjectId();
        Document asientoDoc = new Document("_id", asientoId)
                .append("fila", "A")
                .append("numero", 1)
                .append("ubicacion", ubicacionId)
                .append("seccion", seccionId);
        asientosCol.insertOne(asientoDoc);

        ObjectId categoriaId = new ObjectId();
        categoriaCol.insertOne(new Document("_id", categoriaId)
                .append("UrlImagen", "test.jpg")
                .append("nombreCategoria", CategoriaEventoDTO.NATACION.name())
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
                .append("estadoAsiento", EstadoAsientoDTO.DISPONIBLE.name())
                .append("asiento", asientoId)
                .append("evento", eventoId);
        asientoEventosCol.insertOne(asientoEve);

        ObjectId boletoId = new ObjectId();
        Document boletoDoc = new Document("_id", boletoId)
                .append("codigoQR", "testQR")
                .append("estado", EstadoBoletoDTO.ACTIVO.name())
                .append("evento", new Document("_id", eventoId)
                        .append("nombre", "Nado sincronizado"))
                .append("asientoEvento", new Document("_id", asientoEveId)
                        .append("estado", EstadoAsientoDTO.DISPONIBLE.name())
                        .append("precio", 200))
                .append("token", "testToken");
        boletoCol.insertOne(boletoDoc);

        ObjectId pagoId = new ObjectId();
        Document pagoDoc = new Document("_id", pagoId)
                .append("fechaOperacion", new Date())
                .append("importe", 120.0)
                .append("metodoPago", "TARJETA");
        pagoCol.insertOne(pagoDoc);

        ObjectId usuarioId = new ObjectId();
        Document usuarioDoc = new Document("_id", usuarioId)
                .append("nombre", "Aaron")
                .append("apellidoPaterno", "Burciaga")
                .append("apellidoMaterno", "Alcantar")
                .append("correo", "aaronA@gmail.com")
                .append("creditos", 230);
        usuarioCol.insertOne(usuarioDoc);

        ObjectId reservacionId = new ObjectId();
        Document reservacionDoc = new Document("_id", reservacionId)
                .append("total", 120.0)
                .append("boleto", boletoId)
                .append("pago", pagoId)
                .append("usuario", usuarioId)
                .append("fechaHora", new Date())
                .append("estado", ReservacionEstadoDTO.ACTIVA.name());
        reservacionCol.insertOne(reservacionDoc);

        List<Reservacion> result = reservacionDAO.obtenerReservacionesUsuario(usuarioId.toString());
        assertNotNull(result);
    }

//fail obtenerBoleto
    /**
     * Test of obtenerBoleto method, of class ReservacionDAO.
     */
    @Test
    public void testObtenerBoleto() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();

        MongoCollection<Document> asientoEventosCol = base.getCollection("asientoEventos");
        MongoCollection<Document> asientosCol = base.getCollection("asientos");
        MongoCollection<Document> ubicacionesCol = base.getCollection("ubicaciones");
        MongoCollection<Document> seccionesCol = base.getCollection("secciones");
        MongoCollection<Document> eventosCol = base.getCollection("eventos");
        MongoCollection<Document> categoriaCol = base.getCollection("categorias");
        MongoCollection<Document> reservacionCol = base.getCollection("reservacion");
        MongoCollection<Document> boletoCol = base.getCollection("boleto");
        MongoCollection<Document> pagoCol = base.getCollection("pago");
        MongoCollection<Document> usuarioCol = base.getCollection("usuario");

        ObjectId ubicacionId = new ObjectId();
        Document ubicacionDoc = new Document("_id", ubicacionId)
                .append("nombre", "Estadio Potros")
                .append("capacidad", 100)
                .append("tipoUbicacion", TipoUbicacionN.AIRELIBRE.name())
                .append("secciones", new ArrayList<>()
        );
        ubicacionesCol.insertOne(ubicacionDoc);
                
        ObjectId seccionId = new ObjectId();
        Document seccionDoc = new Document("_id", seccionId)
                .append("nombre", "A")
                .append("capacidad", 50)
                .append("precioBase", 500L);
        seccionesCol.insertOne(seccionDoc);

        ObjectId asientoId = new ObjectId();
        Document asientoDoc = new Document("_id", asientoId)
                .append("fila", "A")
                .append("numero", 1)
                .append("ubicacion", ubicacionId)
                .append("seccion", seccionId);
        asientosCol.insertOne(asientoDoc);

        ObjectId categoriaId = new ObjectId();
        Document categoriaDoc = new Document("_id", categoriaId)
                .append("urlImagen", "test.jpg")
                .append("nombre", CategoriaEventoDTO.NATACION.name()
        );
        categoriaCol.insertOne(categoriaDoc);

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
                .append("asiento", asientoId)
                .append("evento", eventoId);
        asientoEventosCol.insertOne(asientoEve);

        ObjectId boletoId = new ObjectId();
        Document boletoDoc = new Document("_id", boletoId)
                .append("codigoQR", "testQR")
                .append("estado", EstadoBoletoDTO.ACTIVO.name())
                .append("evento", eventoId)
                .append("asientoEvento", asientoEveId)
                .append("token", "testToken");
        boletoCol.insertOne(boletoDoc);

        ObjectId pagoId = new ObjectId();
        Document pagoDoc = new Document("_id", pagoId)
                .append("fechaOperacion", new Date())
                .append("importe", 120.0)
                .append("metodoPago", "TARJETA");
        pagoCol.insertOne(pagoDoc);

        ObjectId usuarioId = new ObjectId();
        Document usuarioDoc = new Document("_id", usuarioId)
                .append("nombre", "Aaron")
                .append("apellidoPaterno", "Burciaga")
                .append("apellidoMaterno", "Alcantar")
                .append("correo", "aaronA@gmail.com")
                .append("creditos", 230);
        usuarioCol.insertOne(usuarioDoc);

        ObjectId reservacionId = new ObjectId();
        Document reservacionDoc = new Document("_id", reservacionId)
                .append("total", 120.0)
                .append("boleto", boletoId)
                .append("pago", pagoId)
                .append("usuario", usuarioId)
                .append("fechaHora", new Date())
                .append("estado", ReservacionEstadoDTO.ACTIVA.name());
        reservacionCol.insertOne(reservacionDoc);

        Boleto result = reservacionDAO.obtenerBoleto(boletoId.toString());
        assertNotNull(result);
    }

}
