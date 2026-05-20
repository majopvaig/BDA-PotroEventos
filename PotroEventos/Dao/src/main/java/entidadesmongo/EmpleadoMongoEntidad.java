package entidadesmongo;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

/**
 * Clase que representa la entidad de persistencia de un empleado dentro de
 * MongoDB.
 *
 * <p>
 * Esta clase es utilizada para mapear los documentos almacenados en la
 * colección de empleados de MongoDB mediante el codec POJO proporcionado por el
 * driver de MongoDB.
 * </p>
 *
 * <p>
 * Contiene la información básica de autenticación del empleado, incluyendo el
 * identificador único, el correo electrónico y la contraseña.
 * </p>
 *
 * <p>
 * El atributo {@code id} se encuentra anotado con {@link BsonId}, indicando que
 * corresponde al identificador principal del documento dentro de MongoDB.
 * </p>
 *
 * @author Brian Sandoval - 262741
 */
public class EmpleadoMongoEntidad {

    /**
     * Identificador único del empleado dentro de MongoDB.
     *
     * <p>
     * Este atributo corresponde al campo {@code _id} del documento almacenado
     * en la base de datos.
     * </p>
     */
    @BsonId
    private ObjectId id;

    /**
     * Correo electrónico del empleado.
     */
    private String correo;

    /**
     * Contraseña del empleado.
     *
     * <p>
     * Este atributo almacena la contraseña asociada al empleado. Idealmente, la
     * contraseña debe almacenarse utilizando hashing para mejorar la seguridad.
     * </p>
     */
    private String contrasenia;

    /**
     * Constructor vacío de la clase.
     *
     * <p>
     * Este constructor es requerido por el codec POJO de MongoDB para poder
     * instanciar automáticamente los objetos durante el proceso de
     * deserialización.
     * </p>
     */
    public EmpleadoMongoEntidad() {
    }

    /**
     * Constructor que inicializa todos los atributos de la entidad.
     *
     * @param id Identificador único del empleado en MongoDB.
     * @param correo Correo electrónico del empleado.
     * @param contrasenia Contraseña del empleado.
     */
    public EmpleadoMongoEntidad(ObjectId id, String correo, String contrasenia) {
        this.id = id;
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    /**
     * Constructor que inicializa la información básica del empleado sin
     * especificar un identificador.
     *
     * <p>
     * El identificador puede ser generado automáticamente por MongoDB al
     * momento de insertar el documento.
     * </p>
     *
     * @param correo Correo electrónico del empleado.
     * @param contrasenia Contraseña del empleado.
     */
    public EmpleadoMongoEntidad(String correo, String contrasenia) {
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    /**
     * Obtiene el identificador del empleado en formato texto.
     *
     * <p>
     * Este método convierte el {@link ObjectId} a su representación hexadecimal
     * mediante el método {@code toHexString()}.
     * </p>
     *
     * @return El identificador del empleado como cadena de texto, o
     * {@code null} si el identificador no ha sido asignado.
     */
    public String getIdComoTexto() {

        if (id == null) {
            return null;
        }

        return id.toHexString();
    }

    /**
     * Obtiene el identificador único del empleado.
     *
     * @return Objeto {@link ObjectId} correspondiente al identificador del
     * empleado.
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Establece el identificador único del empleado.
     *
     * @param id Nuevo identificador del empleado.
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Obtiene el correo electrónico del empleado.
     *
     * @return Correo electrónico del empleado.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del empleado.
     *
     * @param correo Nuevo correo electrónico del empleado.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la contraseña del empleado.
     *
     * @return Contraseña del empleado.
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * Establece la contraseña del empleado.
     *
     * @param contrasenia Nueva contraseña del empleado.
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

}
