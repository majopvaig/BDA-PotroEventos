/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Entitys.Reservacion;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author maria
 */
public interface IReservacionDAO {
    
    Reservacion consultarReservacion(String id) throws PersistenciaException;
    
    List<Reservacion> obtenerReservacionesUsuario(String idUsuario) throws PersistenciaException;
    
    boolean guardarReservacion(Reservacion reservacion) throws PersistenciaException;
    
    boolean cancelarReservacion(String idReservacion) throws PersistenciaException;
    
    boolean tieneFactura(String idReservacion) throws PersistenciaException;
    
    boolean asociarFactura(String idReservacion, String idFactura) throws PersistenciaException;
}
