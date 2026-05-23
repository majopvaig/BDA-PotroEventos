/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Entitys.Pago;
import excepciones.PersistenciaException;

/**
 *
 * @author maria
 */
public interface IPagoDAO {
    
    Pago agregarPago(Pago pago, String idReservacion) throws PersistenciaException;
    
}
