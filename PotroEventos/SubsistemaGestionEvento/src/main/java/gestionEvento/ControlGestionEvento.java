/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionEvento;

import dtos.CategoriaDTO;
import dtos.ENUMS.CategoriaEventoDTO;
import dtos.ENUMS.EstadoEventoDTO;
import dtos.EventoDTO;
import excepciones.GestionEventoException;
import excepciones.NegocioException;
import interfaces.ICategoriaBO;
import interfaces.IEventoBO;
import java.util.List;
import java.util.ArrayList;
import objetosNegocio.CategoriaBO;
import objetosNegocio.EventoBO;

/**
 *
 * @author maria
 */
public class ControlGestionEvento {

    private List<EventoDTO> listaEventos = new ArrayList<>();

    private static ControlGestionEvento instance;

    private ICategoriaBO categoriaBO = CategoriaBO.getInstance();

    private IEventoBO eventoBO = EventoBO.getInstance();

    private ControlGestionEvento() {
    }

    public static ControlGestionEvento getInstance() {
        if (instance == null) {
            instance = new ControlGestionEvento();
        }
        return instance;
    }

    protected EventoDTO consultarEvento(String idEvento) {
        for (EventoDTO e : listaEventos) {
            if (e.getIdEvento() == idEvento) {
                return e;
            }
        }
        return null;
    }

    protected List<EventoDTO> consultarEventosPorCategoria(CategoriaDTO categoria) throws NegocioException {
        return eventoBO.obtenerEventosPorCategoria(categoria);
    }

    protected List<CategoriaDTO> consultarCategorias() throws GestionEventoException {
        try {
            return categoriaBO.consultarCategorias();
        } catch (NegocioException ex) {
            throw new GestionEventoException(ex.getMessage());
        }
    }
}
