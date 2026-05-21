package daos;

import Entitys.Empleado;
import com.mongodb.client.MongoCollection;
import conexion.ConexionMongo;
import entidadesmongo.EmpleadoMongoEntidad;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Kaleb
 */
public class EmpleadoDAOTest {

    public EmpleadoDAOTest() {
    }

    /**
     * Test of obtenerEmpleado method, of class EmpleadoDAO.
     */
    @Test
    public void testObtenerEmpleado() throws Exception {

        System.out.println("obtenerEmpleado");

        MongoCollection<EmpleadoMongoEntidad> coleccion = ConexionMongo.obtenerColeccionEmpleados();

        String correo = "admin@itson.edu.mx";

        String passwordPlano = "admin123";

        String hash = BCrypt.hashpw(passwordPlano, BCrypt.gensalt());

        EmpleadoMongoEntidad empleadoMongo = new EmpleadoMongoEntidad();

        empleadoMongo.setCorreo(correo);

        empleadoMongo.setContrasenia(hash);

        coleccion.insertOne(empleadoMongo);

        Empleado empleadoBusqueda = new Empleado();

        empleadoBusqueda.setCorreo(correo);

        EmpleadoDAO instance = EmpleadoDAO.getInstance();

        Empleado result
                = instance.obtenerEmpleado(empleadoBusqueda);

        assertNotNull(result);

        boolean coincide = BCrypt.checkpw(passwordPlano, result.getContrasenia());

        assertTrue(coincide);

        System.out.println("Contraseña verificada correctamente.");
    }
}
