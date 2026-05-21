package interfaces;

import Entitys.Categoria;
import Entitys.Evento;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Interfaz de acceso a datos para la entidad Evento.
 *
 * Define operaciones CRUD utilizando entidades del dominio.
 *
 * @author Kaleb
 */
public interface IEventoDAO {

    Evento buscarPorId(String idEvento) throws PersistenciaException;

    List<Evento> buscarTodosCategoria(Categoria categoria) throws PersistenciaException;

    boolean reducirDisponibilidad(String idEvento) throws PersistenciaException;

    boolean aumentarDisponibilidad(String idEvento) throws PersistenciaException;

    List<Evento> obtenerEventosActuales() throws PersistenciaException;

    List<Evento> buscarPorNombre(String nombre) throws PersistenciaException;

}
