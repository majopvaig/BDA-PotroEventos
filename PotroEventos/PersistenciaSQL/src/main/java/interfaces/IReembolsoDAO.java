/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Entitys.Reembolso;
import excepciones.PersistenciaException;

/**
 *
 * @author maria
 */
public interface IReembolsoDAO {
    
    Reembolso agregarReembolso(Reembolso reembolso, String idDevolucion) throws PersistenciaException;
    
}
