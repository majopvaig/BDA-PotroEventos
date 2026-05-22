/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Entitys.Evento;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author maria
 */
public interface IEventoDAO {
    
    Evento consultarEvento(String idEvento) throws PersistenciaException;
    
    List<Evento> consultarPorCategoria(String idCategoria) throws PersistenciaException;
    
    boolean reducirCapacidad(String idEvento) throws PersistenciaException;
    
    boolean aumentarCapacidad(String idEvento) throws PersistenciaException;
    
    List<Evento> consultarPorNombre(String nombre) throws PersistenciaException;
    
    List<Evento> consultarEventosActuales() throws PersistenciaException;
}
