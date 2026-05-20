package entidadesmongo;

import entidadesresumenmongo.EmpleadoResumenMongo;
import java.time.LocalDateTime;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

/**
 * Entidad de persistencia que representa el registro de asistencia de un boleto
 * dentro de un evento.
 *
 * <p>
 * Esta clase almacena la información relacionada con:
 * </p>
 *
 * <ul>
 * <li>El boleto utilizado.</li>
 * <li>El empleado que registró el acceso.</li>
 * <li>La fecha y hora del registro.</li>
 * </ul>
 *
 * <p>
 * Cada documento de asistencia representa un acceso válido realizado por un
 * usuario al momento de ingresar a un evento.
 * </p>
 *
 * <p>
 * La clase es utilizada por MongoDB como entidad de persistencia.
 * </p>
 *
 * @author Brian Sandoval - 262741
 */
public class AsistenciaMongoEntidad {

    /**
     * Identificador único de la asistencia.
     */
    @BsonId
    private ObjectId id;

    /**
     * Información resumida del empleado que realizó el registro de asistencia.
     */
    private EmpleadoResumenMongo empleado;

    /**
     * Fecha y hora exacta en la que se registró la asistencia.
     */
    private LocalDateTime fechaHoraRegistro;

    /**
     * Constructor vacío requerido por MongoDB.
     */
    public AsistenciaMongoEntidad() {
    }

    /**
     * Constructor que inicializa todos los atributos de la asistencia.
     *
     * @param id Identificador único de la asistencia.
     *
     *
     * @param empleado Empleado que registró la asistencia.
     *
     * @param fechaHoraRegistro Fecha y hora del registro.
     */
    public AsistenciaMongoEntidad(ObjectId id, EmpleadoResumenMongo empleado, LocalDateTime fechaHoraRegistro) {

        this.id = id;
        this.empleado = empleado;
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    /**
     * Constructor sin identificador.
     *
     * @param empleado Empleado que registró la asistencia.
     *
     * @param fechaHoraRegistro Fecha y hora del registro.
     */
    public AsistenciaMongoEntidad(EmpleadoResumenMongo empleado, LocalDateTime fechaHoraRegistro) {
        this.empleado = empleado;
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    /**
     * Obtiene el identificador de la asistencia.
     *
     * @return Identificador de la asistencia.
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Establece el identificador de la asistencia.
     *
     * @param id Nuevo identificador.
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Obtiene el identificador de la asistencia en formato texto.
     *
     * @return Identificador hexadecimal o {@code null} si no existe.
     */
    public String getIdComoTexto() {

        if (id == null) {
            return null;
        }

        return id.toHexString();
    }

    /**
     * Obtiene el empleado que registró la asistencia.
     *
     * @return Empleado resumido.
     */
    public EmpleadoResumenMongo getEmpleado() {
        return empleado;
    }

    /**
     * Establece el empleado que registró la asistencia.
     *
     * @param empleado Nuevo empleado.
     */
    public void setEmpleado(EmpleadoResumenMongo empleado) {
        this.empleado = empleado;
    }

    /**
     * Obtiene la fecha y hora del registro de asistencia.
     *
     * @return Fecha y hora del registro.
     */
    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    /**
     * Establece la fecha y hora del registro de asistencia.
     *
     * @param fechaHoraRegistro Nueva fecha y hora.
     */
    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {

        this.fechaHoraRegistro = fechaHoraRegistro;
    }

}
