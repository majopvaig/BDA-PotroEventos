package objetosNegocio;

import Entitys.ENUMS.CategoriaEvento;
import Entitys.Categoria;
import adapters.EventoAdapter;
import daos.EventoDAO;
import dtos.CategoriaDTO;
import dtos.EventoDTO;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IEventoBO;
import interfaces.IEventoDAO;
import java.util.List;

/**
 * Objeto de negocio para Evento. Implementa patrón Singleton.
 */
public class EventoBO implements IEventoBO {

    private static EventoBO instancia;
    private final IEventoDAO eventoDAO = EventoDAO.getInstance();

    private EventoBO() {

    }

    public static EventoBO getInstance() {
        if (instancia == null) {
            instancia = new EventoBO();
        }
        return instancia;
    }

    @Override
    public List<EventoDTO> obtenerEventosPorCategoria(CategoriaDTO categoria) throws NegocioException {
        try {
            return EventoAdapter.listaDTOs(eventoDAO.buscarTodosCategoria(new Categoria(categoria.getIdCategoria(), CategoriaEvento.valueOf(categoria.getNombreCategoria().name()), categoria.getUrlImagen())));
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public EventoDTO obtenerEventoPorId(String id) throws NegocioException {
        try {
            return EventoAdapter.entidadADTO(eventoDAO.buscarPorId(id));
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public boolean reducirDisponibilidadEvento(String id) throws NegocioException {
        try {
            return eventoDAO.reducirDisponibilidad(id);
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public boolean aumentarDisponibilidadEvento(String idEvento) throws NegocioException {
        try {
            return eventoDAO.aumentarDisponibilidad(idEvento);
        } catch (PersistenciaException pe) {
            throw new NegocioException(pe.getMessage());
        }
    }

}
