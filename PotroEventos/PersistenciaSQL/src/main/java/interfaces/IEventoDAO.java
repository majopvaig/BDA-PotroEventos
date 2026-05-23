/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Entitys.Categoria;
import Entitys.Evento;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author maria
 */
public interface IEventoDAO {
    
    Evento buscarPorId(String idEvento) throws PersistenciaException;
    
    List<Evento> buscarTodosCategoria(Categoria categoria) throws PersistenciaException;
    
    boolean reducirDisponibilidad(String idEvento) throws PersistenciaException;
    
    boolean aumentarDisponibilidad(String idEvento) throws PersistenciaException;
    
    List<Evento> buscarPorNombre(String nombre) throws PersistenciaException;
    
    List<Evento> obtenerEventosActuales() throws PersistenciaException;
}
