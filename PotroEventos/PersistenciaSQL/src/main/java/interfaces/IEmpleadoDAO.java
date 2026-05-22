/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Entitys.Empleado;
import excepciones.PersistenciaException;

/**
 *
 * @author maria
 */
public interface IEmpleadoDAO {
    
    Empleado obtenerEmpleado(Empleado empleado) throws PersistenciaException;
    
}
