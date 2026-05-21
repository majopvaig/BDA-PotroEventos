package daos;

import Entitys.Empleado;
import adaptadores.EmpleadoPersistenciaAdapter;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;
import conexion.ConexionMongo;
import entidadesmongo.EmpleadoMongoEntidad;
import excepciones.PersistenciaException;
import interfaces.IEmpleadoDAO;

/**
 * Clase encargada de gestionar las operaciones de persistencia relacionadas con
 * los empleados dentro de la base de datos MongoDB.
 *
 * <p>
 * Esta clase implementa la interfaz {@link IEmpleadoDAO}, proporcionando
 * métodos para consultar información de empleados almacenados en la base de
 * datos.
 * </p>
 *
 * <p>
 * La clase utiliza el patrón de diseño Singleton para garantizar que únicamente
 * exista una instancia del DAO durante la ejecución de la aplicación.
 * </p>
 *
 * <p>
 * También utiliza la clase {@link EmpleadoPersistenciaAdapter} para realizar
 * las conversiones entre los objetos del dominio {@link Empleado} y las
 * entidades de persistencia {@link EmpleadoMongoEntidad}.
 * </p>
 *
 * @author Brian Sandoval - 262741
 */
public class EmpleadoDAO implements IEmpleadoDAO {

    /**
     * Instancia única de la clase {@link EmpleadoDAO}.
     */
    private static EmpleadoDAO instancia;

    /**
     * Colección de MongoDB utilizada para almacenar y consultar los documentos
     * correspondientes a los empleados.
     */
    private final MongoCollection<EmpleadoMongoEntidad> coleccionEmpleados;

    /**
     * Constructor privado de la clase.
     *
     * <p>
     * Inicializa la colección de empleados obteniendo la referencia desde la
     * conexión de MongoDB.
     * </p>
     *
     * <p>
     * Este constructor es privado para implementar el patrón Singleton e
     * impedir la creación de instancias desde otras clases.
     * </p>
     */
    private EmpleadoDAO() {
        this.coleccionEmpleados = ConexionMongo.obtenerColeccionEmpleados();
    }

    /**
     * Obtiene la instancia única de la clase {@link EmpleadoDAO}.
     *
     * <p>
     * Si la instancia aún no ha sido creada, este método la inicializa. En caso
     * contrario, retorna la instancia ya existente.
     * </p>
     *
     * @return Instancia única de {@link EmpleadoDAO}.
     */
    public static EmpleadoDAO getInstance() {
        if (instancia == null) {
            instancia = new EmpleadoDAO();
        }
        return instancia;
    }

    /**
     * Obtiene un empleado registrado en la base de datos utilizando el correo y
     * la contraseña proporcionados.
     *
     * <p>
     * Este método realiza una búsqueda en la colección de empleados utilizando
     * filtros sobre los campos {@code correo} y {@code contrasenia}.
     * </p>
     *
     * <p>
     * Si se encuentra un documento que coincida con los datos proporcionados,
     * este será convertido a un objeto del dominio {@link Empleado} y
     * retornado.
     * </p>
     *
     * @param empleado Objeto que contiene el correo y la contraseña del
     * empleado que se desea consultar.
     *
     * @return Un objeto {@link Empleado} correspondiente al empleado encontrado
     * en la base de datos, o {@code null} si no existe coincidencia.
     *
     * @throws PersistenciaException Se lanza cuando:
     * <ul>
     * <li>El parámetro {@code empleado} es {@code null}.</li>
     * <li>Ocurre un error durante la consulta en MongoDB.</li>
     * </ul>
     */
    @Override
    public Empleado obtenerEmpleado(Empleado empleado) throws PersistenciaException {

        if (empleado == null) {
            throw new PersistenciaException("El empleado no puede ser null.");
        }

        try {

            EmpleadoMongoEntidad resultado = coleccionEmpleados.find(eq("correo", empleado.getCorreo())).first();

            return EmpleadoPersistenciaAdapter.convertirADominio(resultado);

        } catch (MongoException e) {

            throw new PersistenciaException("No fue posible obtener al usuario");
        }
    }

}
