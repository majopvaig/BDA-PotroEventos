package daos;

import Entitys.Categoria;
import Entitys.Evento;
import adaptadores.EventoPersistenciaAdapter;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UnwindOptions;
import static com.mongodb.client.model.Updates.inc;
import com.mongodb.client.result.UpdateResult;
import conexion.ConexionMongo;
import entidadesmongo.EventoMongoEntidad;
import excepciones.PersistenciaException;
import interfaces.IEventoDAO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Implementación mock de la DAO de Evento. Simula persistencia en memoria
 * utilizando una lista.
 *
 * * @author Kaleb
 */
public class EventoDAO implements IEventoDAO {

    private final MongoCollection<EventoMongoEntidad> coleccionEventos;

    private static EventoDAO instancia;

    private EventoDAO() {
        this.coleccionEventos = ConexionMongo.obtenerColeccionEventos();
    }

    public static EventoDAO getInstance() {
        if (instancia == null) {
            instancia = new EventoDAO();
        }
        return instancia;
    }

    @Override
    public Evento buscarPorId(String idEvento) throws PersistenciaException {
        try {
            Document evento = coleccionEventos
                    .withDocumentClass(Document.class)
                    .aggregate(Arrays.asList(
                            Aggregates.match(Filters.eq("_id", new ObjectId(idEvento))),
                            Aggregates.lookup("categorias", "categoria._id", "_id", "categoria"),
                            Aggregates.unwind("$categoria", new UnwindOptions().preserveNullAndEmptyArrays(true)),
                            Aggregates.lookup("ubicaciones", "ubicacion._id", "_id", "ubicacion"),
                            Aggregates.unwind("$ubicacion", new UnwindOptions().preserveNullAndEmptyArrays(true))
                    )).first();

            if (evento == null) {
                return null;
            }

            // Pasamos el Document al adapter
            return EventoPersistenciaAdapter.convertirADominio(evento);

        } catch (MongoException e) {
            throw new PersistenciaException("Error al buscar evento completo: " + e.getMessage());
        }
    }

    @Override
    public List<Evento> buscarTodosCategoria(Categoria categoria) throws PersistenciaException {
        try {
            Bson filtro = Filters.and(
                    Filters.eq("categoria._id", new ObjectId(categoria.getId())),
                    Filters.eq("estado", "ACTIVO"),
                    Filters.ne("disponibilidad", 0),
                    Filters.gt("fechaHora", LocalDateTime.now()));

            List<Document> eventos = coleccionEventos
                    .withDocumentClass(Document.class)
                    .aggregate(Arrays.asList(
                            Aggregates.match(filtro),
                            Aggregates.lookup("categorias", "categoria._id", "_id", "categoria"),
                            Aggregates.unwind("$categoria", new UnwindOptions().preserveNullAndEmptyArrays(true)),
                            Aggregates.lookup("ubicaciones", "ubicacion._id", "_id", "ubicacion"),
                            Aggregates.unwind("$ubicacion", new UnwindOptions().preserveNullAndEmptyArrays(true))
                    ))
                    .into(new ArrayList<>());
            return EventoPersistenciaAdapter.convetirListaADominio(eventos);
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible obtener los eventos");
        }
    }

    @Override
    public boolean reducirDisponibilidad(String idEvento) throws PersistenciaException {
        if(idEvento == null){
            throw new PersistenciaException("El id del evento es requerido.");
        }
        try{  
            Bson filtro = Filters.and(
                    Filters.eq("_id", new ObjectId(idEvento)), 
                    Filters.ne("disponibilidad", 0));
            
            Bson disminucion = inc("disponibilidad", -1);
            
            UpdateResult resultado = coleccionEventos.updateOne(filtro, disminucion);
            
            return resultado.getModifiedCount() > 0;
            
        } catch(MongoException me){
            throw new PersistenciaException("No fue posible disminuir la capacidad del evento.");
        }
    }

}
