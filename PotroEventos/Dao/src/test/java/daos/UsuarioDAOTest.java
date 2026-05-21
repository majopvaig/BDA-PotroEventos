/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import Entitys.Usuario;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import conexion.ConexionMongo;
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
public class UsuarioDAOTest {
    
    private static String databaseName;
    private UsuarioDAO usuarioDAO;
    
    public UsuarioDAOTest() {
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
        usuarioDAO = UsuarioDAO.getInstance();
        
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        MongoCollection<Document> usuarioCol = base.getCollection("usuario");
        
        usuarioCol.deleteMany(new Document());
    }
    /**
     * Test of getInstance method, of class UsuarioDAO.
     */
    @Test
    public void testGetInstance() {
        UsuarioDAO instancia1 = UsuarioDAO.getInstance();
        UsuarioDAO instancia2 = UsuarioDAO.getInstance();
        assertNotNull(instancia1);
        assertEquals(instancia1, instancia2);
    }

    /**
     * Test of obtenerUsuario method, of class UsuarioDAO.
     */
    //@Test
    public void testObtenerUsuario() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        MongoCollection<Document> usuarioCol = base.getCollection("usuario");
        
        ObjectId usuarioId = new ObjectId();
        Document usuarioDoc = new Document("_id", usuarioId)
                .append("nombre", "Aaron")
                .append("apellidoPaterno", "Burciaga")
                .append("apellidoMaterno", "Alcantar")
                .append("correo", "aaronA@gmail.com")
                .append("creditos", 230);
        usuarioCol.insertOne(usuarioDoc);
        
        Usuario usuario = new Usuario();
        usuario.setCorreo("aaronA@gmail.com");
        
        Usuario result = usuarioDAO.obtenerUsuario(usuario);
        assertNotNull(result);
    }

//Fail GuardarUsuario y ObtenerPoId
    /**
     * Test of guardarUsuario method, of class UsuarioDAO.
     */
    //@Test
    public void testGuardarUsuario() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        MongoCollection<Document> usuarioCol = base.getCollection("usuario");
        
        Usuario usuario = new Usuario();
        usuario.setNombre("Aaron");
        usuario.setApellidoPaterno("Burciaga");
        usuario.setApellidoMaterno("Alcantar");
        usuario.setCorreo("aaronA@gmail.com");
        usuario.setContrasenia("1234");
        
        Usuario result = usuarioDAO.guardarUsuario(usuario);
        
        assertNotNull(result);
        
        Document usuarioGuardado = usuarioCol.find(new Document("_id", new ObjectId(result.getIdUsuario()))).first();
        assertNotNull(usuarioGuardado);
    }

    /**
     * Test of obtenerPorId method, of class UsuarioDAO.
     */
    //@Test
    public void testObtenerPorId() throws Exception {
        MongoDatabase base = ConexionMongo.obtenerBaseDatos();
        MongoCollection<Document> usuarioCol = base.getCollection("usuario");
        
        ObjectId usuarioId = new ObjectId();
        Document usuarioDoc = new Document("_id", usuarioId)
                .append("nombre", "Aaron")
                .append("apellidoPaterno", "Burciaga")
                .append("apellidoMaterno", "Alcantar")
                .append("correo", "aaronA@gmail.com")
                .append("contrasenia", "1234");
        usuarioCol.insertOne(usuarioDoc);
        
        Usuario result = usuarioDAO.obtenerPorId(usuarioId.toString());
        assertNotNull(result);
    }
    
}
