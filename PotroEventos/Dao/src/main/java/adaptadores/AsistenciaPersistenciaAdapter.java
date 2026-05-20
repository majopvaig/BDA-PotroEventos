package adaptadores;

import Entitys.Asistencia;
import Entitys.Empleado;
import entidadesmongo.AsistenciaMongoEntidad;
import entidadesresumenmongo.EmpleadoResumenMongo;
import excepciones.PersistenciaException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Adaptador de persistencia para la entidad Asistencia.
 *
 * @author Brian Sandoval - 262741
 */
public class AsistenciaPersistenciaAdapter {

    public static AsistenciaMongoEntidad convertirAMongo(Asistencia dominio) throws PersistenciaException {
        if (dominio == null) {
            return null;
        }

        AsistenciaMongoEntidad mongo = new AsistenciaMongoEntidad();

        if (dominio.getIdAsistencia() != null) {
            mongo.setId(convertirStringAObjectId(dominio.getIdAsistencia()));
        }

        if (dominio.getEmpleado() != null && dominio.getEmpleado().getIdEmpleado() != null) {
            EmpleadoResumenMongo empleadoResumen = new EmpleadoResumenMongo();
            empleadoResumen.setIdEmpleado(convertirStringAObjectId(dominio.getEmpleado().getIdEmpleado()));
            mongo.setEmpleado(empleadoResumen);
        } else {
            mongo.setEmpleado(null);
        }

        mongo.setFechaHoraRegistro(dominio.getFechaHoraRegistro());

        return mongo;
    }

    public static Asistencia convertirADominio(Document mongo) throws PersistenciaException {
        if (mongo == null) {
            return null;
        }

        Asistencia dominio = new Asistencia();

        if (mongo.getObjectId("_id") != null) {
            dominio.setIdAsistencia(mongo.getObjectId("_id").toHexString());
        }

        Document empleadoDoc = (Document) mongo.get("empleado");
        if (empleadoDoc != null && empleadoDoc.getObjectId("idEmpleado") != null) {
            Empleado empleado = new Empleado();
            empleado.setIdEmpleado(empleadoDoc.getObjectId("idEmpleado").toHexString());
            dominio.setEmpleado(empleado);
        }

        if (mongo.getDate("fechaHoraRegistro") != null) {
            dominio.setFechaHoraRegistro(mongo.getDate("fechaHoraRegistro")
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
        }

        return dominio;
    }

    public static List<Asistencia> convertirListaADominio(List<Document> lista) throws PersistenciaException {
        List<Asistencia> asistencias = new ArrayList<>();

        if (lista == null) {
            return new ArrayList<>();
        }

        for (Document mongo : lista) {
            asistencias.add(convertirADominio(mongo));
        }

        return asistencias;
    }

    private static ObjectId convertirStringAObjectId(String id) throws PersistenciaException {
        if (id == null || id.isBlank()) {
            return null;
        }
        if (!ObjectId.isValid(id)) {
            throw new PersistenciaException(
                    "El id recibido no tiene formato válido de ObjectId."
            );
        }
        return new ObjectId(id);
    }
}
