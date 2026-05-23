/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Entitys.AsientoEvento;
import Entitys.Asistencia;
import Entitys.ReporteAsistencia;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author maria
 */
public interface IAsistenciaDAO {
    
    Asistencia registrarAsistencia(String idBoleto, Asistencia asistencia) throws PersistenciaException;
    
    ReporteAsistencia obtenerReporteAsistencia(String idEvento);
    
    //public List<AsientoEvento> obtenerAsientosConAsistencia(String idEvento) throws PersistenciaException;
    
}
