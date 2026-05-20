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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
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

    private static final Logger LOG = Logger.getLogger(EventoDAO.class.getName());

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
        if (idEvento == null) {
            throw new PersistenciaException("El id del evento es requerido.");
        }
        try {
            Bson filtro = Filters.and(
                    Filters.eq("_id", new ObjectId(idEvento)),
                    Filters.ne("disponibilidad", 0));

            Bson disminucion = inc("disponibilidad", -1);

            UpdateResult resultado = coleccionEventos.updateOne(filtro, disminucion);

            return resultado.getModifiedCount() > 0;

        } catch (MongoException me) {
            throw new PersistenciaException("No fue posible disminuir la capacidad del evento.");
        }
    }

    @Override
    public boolean aumentarDisponibilidad(String idEvento) throws PersistenciaException {
        if (idEvento == null) {
            throw new PersistenciaException("El id del evento es requerido.");
        }
        try {
            Bson filtro = Filters.and(
                    Filters.eq("_id", new ObjectId(idEvento)));

            Bson aumento = inc("disponibilidad", +1);

            UpdateResult resultado = coleccionEventos.updateOne(filtro, aumento);

            return resultado.getModifiedCount() > 0;

        } catch (MongoException me) {
            throw new PersistenciaException("No fue posible aumentar la capacidad del evento.");
        }
    }

    /**
     * Obtiene todos los eventos activos programados desde el instante actual
     * exacto hasta que termine el día de hoy (23:59:59).
     *
     * @return Lista de eventos disponibles desde la hora actual en adelante.
     * @throws PersistenciaException Se lanza cuando ocurre un error en MongoDB.
     */
    @Override
    public List<Evento> obtenerEventosActuales() throws PersistenciaException {

        try {

            LOG.info("Buscando eventos programados desde la hora actual exacta en adelante.");

            // 1. Forzar a que el inicio y el fin del filtro se calculen en la zona UTC (Z)
            ZoneId zonaUTC = ZoneId.of("UTC");

            // 2. Obtener la hora actual simulando que estás en la zona horaria de los datos (UTC)
            ZonedDateTime ahoraZoned = ZonedDateTime.now(zonaUTC);

            // 3. Definir el fin del día a las 23:59:59 de la zona UTC
            ZonedDateTime finDiaZoned = ahoraZoned.toLocalDate().atTime(LocalTime.MAX).atZone(zonaUTC);

            // 4. Convertir a Instant para enviarlo a MongoDB
            Instant horaActualUTC = ahoraZoned.toInstant();
            Instant finDiaUTC = finDiaZoned.toInstant();

            Bson filtro = Filters.and(
                    Filters.eq("estado", "ACTIVO"),
                    Filters.ne("disponibilidad", 0),
                    Filters.gte("fechaHora", horaActualUTC),
                    Filters.lte("fechaHora", finDiaUTC)
            );

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

            LOG.info(() -> "Eventos encontrados desde la hora actual: " + eventos.size());

            return EventoPersistenciaAdapter.convetirListaADominio(eventos);

        } catch (MongoException e) {

            LOG.severe(() -> "Error al obtener los eventos desde la hora actual: " + e.getMessage());

            throw new PersistenciaException("No fue posible obtener los eventos actuales.");
        }
    }

    /**
     * Busca eventos activos del día actual cuyo nombre coincida parcial o
     * totalmente con el texto proporcionado.
     *
     * <p>
     * La búsqueda se realiza utilizando una expresión regular insensible a
     * mayúsculas y minúsculas sobre el campo {@code nombre}.
     * </p>
     *
     * <p>
     * Solamente se consideran eventos que:
     * </p>
     *
     * <ul>
     * <li>Tengan estado {@code ACTIVO}.</li>
     * <li>Cuenten con disponibilidad mayor a cero.</li>
     * <li>Pertenezcan al día actual.</li>
     * </ul>
     *
     * <p>
     * Si el texto proporcionado es nulo o vacío, se devuelven todos los eventos
     * actuales registrados en el sistema.
     * </p>
     *
     * @param nombre Texto utilizado para buscar coincidencias parciales o
     * totales dentro del nombre de los eventos.
     *
     * @return Lista de eventos activos del día actual que coinciden con el
     * filtro de búsqueda proporcionado.
     *
     * @throws PersistenciaException Se lanza cuando ocurre un error durante la
     * consulta de eventos en MongoDB.
     */
    @Override
    public List<Evento> buscarPorNombre(String nombre) throws PersistenciaException {

        if (nombre == null || nombre.isBlank()) {

            LOG.warning("Se intentó buscar eventos con un nombre vacío.");

            return obtenerEventosActuales();
        }

        try {

            LOG.info(() -> "Buscando eventos por nombre: " + nombre);

            LocalDate hoy = LocalDate.now();

            Date inicioDia = Date.from(hoy.atStartOfDay(ZoneId.systemDefault()).toInstant());

            Date finDia = Date.from(hoy.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

            Bson filtro = Filters.and(
                    Filters.regex("nombre", ".*" + Pattern.quote(nombre) + ".*", "i"),
                    Filters.eq("estado", "ACTIVO"),
                    Filters.gt("disponibilidad", 0),
                    Filters.gte("fechaHora", inicioDia),
                    Filters.lt("fechaHora", finDia)
            );

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

            LOG.info(() -> "Eventos encontrados con el filtro '" + nombre + "': " + eventos.size());

            return EventoPersistenciaAdapter.convetirListaADominio(eventos);

        } catch (MongoException e) {

            LOG.severe(() -> "Error al buscar eventos por nombre: " + e.getMessage());

            throw new PersistenciaException("No fue posible buscar los eventos.");
        }
    }

}
