/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Entitys.Asistencia;
import Entitys.ReporteAsistencia;
import excepciones.PersistenciaException;
import interfaces.IAsistenciaDAO;

/**
 *
 * @author maria
 */
public class AsistenciaDAO implements IAsistenciaDAO {

    @Override
    public Asistencia registrarAsistencia(String idBoleto, Asistencia asistencia) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ReporteAsistencia obtenerReporteAsistencia(String idEvento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
