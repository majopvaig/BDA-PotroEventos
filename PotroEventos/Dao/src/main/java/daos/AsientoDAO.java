package daos;

import Entitys.Asiento;
import adaptadores.AsientoPersistenciaAdapter;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UnwindOptions;
import conexion.ConexionMongo;
import entidadesmongo.AsientoMongoEntidad;
import excepciones.PersistenciaException;
import interfaces.IAsientoDAO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * @author Kaleb
 */
public class AsientoDAO implements IAsientoDAO {

    private final MongoCollection<AsientoMongoEntidad> coleccionAsientos;

    private static AsientoDAO instancia;

    private AsientoDAO() {
        this.coleccionAsientos = ConexionMongo.obtenerColeccionAsientos();
    }

    public static AsientoDAO getInstance() {
        if (instancia == null) {
            instancia = new AsientoDAO();
        }
        return instancia;
    }

    @Override
    public List<Asiento> consultarAsientos() throws PersistenciaException {
        try {
            /*
            esto funciona igual a cómo está explicado allá abajo
            */
            List<Document> asientos = coleccionAsientos
                    .withDocumentClass(Document.class)
                    .aggregate(Arrays.asList(
                            Aggregates.lookup("ubicaciones", "ubicacion", "_id", "ubicacion_temp"),
                            Aggregates.unwind("$ubicacion_temp", new UnwindOptions().preserveNullAndEmptyArrays(true)),
                            Aggregates.addFields(
                                    new Field<>("ubicacion", "$ubicacion_temp"), // Colocamos la ubicación completa
                                    new Field<>("seccion",
                                            new Document("$arrayElemAt", Arrays.asList(
                                                    new Document("$filter",
                                                            new Document("input", "$ubicacion_temp.secciones")
                                                                    .append("as", "s")
                                                                    .append("cond", new Document("$eq", Arrays.asList("$$s._id", "$seccion")))
                                                    ),
                                                    0
                                            ))
                                    )
                            ),
                            Aggregates.project(Projections.exclude("ubicacion_temp"))
                    )).into(new ArrayList<>());

            return AsientoPersistenciaAdapter.convertirDocumentosADominio(asientos);
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible obtener los asientos");
        }
    }

    @Override
    public Asiento consultarPorID(String idAsiento) throws PersistenciaException {
        try {
            Document asiento = coleccionAsientos
                    .withDocumentClass(Document.class)
                    .aggregate(Arrays.asList(
                            // buscamos su id
                            Aggregates.match(Filters.eq("_id", new ObjectId(idAsiento))),
                            // guardamos temporalmente su ubicación
                            Aggregates.lookup("ubicaciones", "ubicacion", "_id", "ubicacion_temp"),
                            Aggregates.unwind("$ubicacion_temp", new UnwindOptions().preserveNullAndEmptyArrays(true)),
                            // como la sección no es una colección, creamos el documento específico
                            Aggregates.addFields(
                                    // agrega la ubicación
                                    new Field<>("ubicacion", "$ubicacion_temp"),
                                    // agrega la sección
                                    new Field<>("seccion",
                                            // saca el elemnto de la sección especifica
                                            new Document("$arrayElemAt", Arrays.asList(
                                                    new Document("$filter",
                                                            new Document("input", "$ubicacion_temp.secciones")
                                                                    .append("as", "s")
                                                                    .append("cond", new Document("$eq", Arrays.asList("$$s._id", "$seccion")))
                                                    ),
                                                    0
                                            ))
                                    )
                            ),
                            // quitamos la ubicación temporal
                            Aggregates.project(Projections.exclude("ubicacion_temp"))
                    )).first();

            return AsientoPersistenciaAdapter.convertirADominio(asiento);
        } catch (MongoException me) {
            throw new PersistenciaException("No fue posible obtener el asiento.");
        }
    }

}
