/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Entitys.Devolucion;
import excepciones.PersistenciaException;

/**
 *
 * @author maria
 */
public interface IDevolucionDAO {
    
    boolean agregarDevolucion(Devolucion devolucion, String idReservacion) throws PersistenciaException;
    
}
