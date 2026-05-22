/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Entitys.Asiento;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author maria
 */
public interface IAsientoDAO {

    public Asiento consultarPorID(String idAsiento) throws PersistenciaException;

    public List<Asiento> consultarAsientos() throws PersistenciaException;
}
