package adaptadores;

import Entitys.Empleado;
import entidadesmongo.EmpleadoMongoEntidad;
import excepciones.PersistenciaException;
import org.bson.types.ObjectId;

/**
 * Clase adaptadora encargada de realizar la conversión entre los objetos del
 * dominio {@link Empleado} y las entidades de persistencia
 * {@link EmpleadoMongoEntidad}.
 *
 * <p>
 * Esta clase permite transformar los datos utilizados dentro de la lógica de
 * negocio a objetos compatibles con MongoDB, así como realizar la conversión
 * inversa desde las entidades persistidas hacia los objetos del dominio.
 * </p>
 *
 * <p>
 * También proporciona utilidades internas para convertir identificadores en
 * formato {@link String} a objetos {@link ObjectId}, validando que el formato
 * recibido sea válido para MongoDB.
 * </p>
 *
 * <p>
 * Todos los métodos de esta clase son estáticos, por lo que no es necesario
 * crear instancias de la clase para utilizar sus funcionalidades.
 * </p>
 *
 * @author Brian Sandoval - 262741
 */
public class EmpleadoPersistenciaAdapter {

    /**
     * Convierte un objeto del dominio {@link Empleado} a una entidad de
     * persistencia {@link EmpleadoMongoEntidad}.
     *
     * <p>
     * Este método transforma la información del empleado utilizada en la lógica
     * de negocio a una representación compatible con MongoDB.
     * </p>
     *
     * <p>
     * El identificador del empleado es convertido de {@link String} a
     * {@link ObjectId} utilizando el método
     * {@link #convertirStringAObjectId(String)}.
     * </p>
     *
     * @param empleado Objeto del dominio que contiene la información del
     * empleado a convertir.
     *
     * @return Una instancia de {@link EmpleadoMongoEntidad} con los datos
     * convertidos, o {@code null} si el parámetro recibido es {@code null}.
     *
     * @throws PersistenciaException Se lanza cuando el identificador del
     * empleado no tiene un formato válido para convertirse a {@link ObjectId}.
     */
    public static EmpleadoMongoEntidad convertirAMongo(Empleado empleado) throws PersistenciaException {
        if (empleado == null) {
            return null;
        }

        return new EmpleadoMongoEntidad(convertirStringAObjectId(empleado.getIdEmpleado()), empleado.getCorreo(), empleado.getContrasenia());
    }

    /**
     * Convierte una entidad de persistencia {@link EmpleadoMongoEntidad} a un
     * objeto del dominio {@link Empleado}.
     *
     * <p>
     * Este método permite transformar la representación almacenada en MongoDB a
     * un objeto utilizado por la lógica de negocio de la aplicación.
     * </p>
     *
     * @param mongo Entidad de persistencia que contiene la información del
     * empleado almacenado en MongoDB.
     *
     * @return Una instancia de {@link Empleado} con los datos convertidos, o
     * {@code null} si el parámetro recibido es {@code null}.
     *
     * @throws PersistenciaException Se lanza si ocurre algún problema durante
     * el proceso de conversión.
     */
    public static Empleado convertirADominio(EmpleadoMongoEntidad mongo) throws PersistenciaException {
        if (mongo == null) {
            return null;
        }

        return new Empleado(mongo.getIdComoTexto(), mongo.getCorreo(), mongo.getContrasenia());
    }

    /**
     * Convierte un identificador en formato {@link String} a un objeto
     * {@link ObjectId}.
     *
     * <p>
     * Este método valida que el identificador recibido tenga un formato válido
     * compatible con MongoDB antes de realizar la conversión.
     * </p>
     *
     * <p>
     * Si el identificador es {@code null} o está vacío, el método retorna
     * {@code null}.
     * </p>
     *
     * @param id Identificador en formato texto que se desea convertir a
     * {@link ObjectId}.
     *
     * @return Un objeto {@link ObjectId} generado a partir del texto recibido,
     * o {@code null} si el identificador es nulo o vacío.
     *
     * @throws PersistenciaException Se lanza cuando el identificador recibido
     * no tiene un formato válido de {@link ObjectId}.
     */
    private static ObjectId convertirStringAObjectId(String id) throws PersistenciaException {
        if (id == null || id.isBlank()) {
            return null;
        }

        if (!ObjectId.isValid(id)) {
            throw new PersistenciaException("El id recibido no tiene formato válido de ObjectId.");
        }

        return new ObjectId(id);
    }

}
