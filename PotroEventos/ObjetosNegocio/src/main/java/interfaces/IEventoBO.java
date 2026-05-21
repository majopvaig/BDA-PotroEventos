package interfaces;

import dtos.CategoriaDTO;
import dtos.EventoDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 * Define la lógica de negocio para la gestión de eventos.
 *
 * Se encarga de validar reglas antes de interactuar con la DAO.
 *
 * @author Kaleb
 */
public interface IEventoBO {

    List<EventoDTO> obtenerEventosPorCategoria(CategoriaDTO categoria) throws NegocioException;

    EventoDTO obtenerEventoPorId(String id) throws NegocioException;

    boolean reducirDisponibilidadEvento(String id) throws NegocioException;

    boolean aumentarDisponibilidadEvento(String idEvento) throws NegocioException;

    List<EventoDTO> obtenerEventosActuales() throws NegocioException;

    List<EventoDTO> buscarPorNombre(String nombre) throws NegocioException;
}
