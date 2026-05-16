/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionEvento;

import dtos.CategoriaDTO;
import java.util.List;
import dtos.EventoDTO;
import excepciones.GestionEventoException;
import excepciones.NegocioException;

/**
 *
 * @author maria
 */
public class GestionEventoFachada implements IFachadaGestionEvento {

    private ControlGestionEvento control = ControlGestionEvento.getInstance();

    @Override
    public EventoDTO consultarEvento(String idEvento) {
        return control.consultarEvento(idEvento);
    }

    @Override
    public List<EventoDTO> consultarEventosPorCategoria(CategoriaDTO categoria) throws GestionEventoException {
        try {
            return control.consultarEventosPorCategoria(categoria);
        } catch (NegocioException ex) {
            throw new GestionEventoException("No fue posible obtener los eventos por categoria");
        }
    }

    @Override
    public List<CategoriaDTO> consultarCategorias() throws GestionEventoException {
        return control.consultarCategorias();
    }

}
