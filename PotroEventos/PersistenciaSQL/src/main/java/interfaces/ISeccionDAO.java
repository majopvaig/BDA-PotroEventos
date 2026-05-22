/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Entitys.Seccion;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author maria
 */
public interface ISeccionDAO {
    
    Seccion buscarSeccionPorId(String idSeccion) throws PersistenciaException;
    
    List<Seccion> buscarPorEvento(String idEvento) throws PersistenciaException;
}
