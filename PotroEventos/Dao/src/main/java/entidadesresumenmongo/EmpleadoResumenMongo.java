package entidadesresumenmongo;

import org.bson.types.ObjectId;

/**
 * Entidad resumen de un empleado.
 *
 * <p>
 * Esta clase almacena únicamente la información mínima necesaria para
 * identificar al empleado dentro de otros documentos MongoDB.
 * </p>
 *
 * <p>
 * Se utiliza principalmente en:
 * </p>
 *
 * <ul>
 * <li>Asistencias.</li>
 * <li>Registros de auditoría.</li>
 * <li>Historiales del sistema.</li>
 * </ul>
 *
 * @author Brian Sandoval - 262741
 */
public class EmpleadoResumenMongo {

    /**
     * Identificador único del empleado.
     */
    private ObjectId idEmpleado;

    /**
     * Constructor vacío requerido por MongoDB.
     */
    public EmpleadoResumenMongo() {

    }

    /**
     * Constructor que inicializa todos los atributos.
     *
     * @param idEmpleado Identificador del empleado.
     */
    public EmpleadoResumenMongo(ObjectId idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    /**
     * Obtiene el identificador del empleado.
     *
     * @return Identificador del empleado.
     */
    public ObjectId getIdEmpleado() {
        return idEmpleado;
    }

    /**
     * Establece el identificador del empleado.
     *
     * @param idEmpleado Nuevo identificador.
     */
    public void setIdEmpleado(ObjectId idEmpleado) {

        this.idEmpleado = idEmpleado;
    }

    /**
     * Obtiene el identificador del empleado en formato texto.
     *
     * @return Identificador hexadecimal o {@code null} si no existe.
     */
    public String getIdComoTexto() {

        if (idEmpleado == null) {
            return null;
        }

        return idEmpleado.toHexString();
    }

}
