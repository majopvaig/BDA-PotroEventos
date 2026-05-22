/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Entitys.Factura;
import excepciones.PersistenciaException;

/**
 *
 * @author maria
 */
public interface IFacturaDAO {

    boolean guardarFactura(Factura factura) throws PersistenciaException;
}
