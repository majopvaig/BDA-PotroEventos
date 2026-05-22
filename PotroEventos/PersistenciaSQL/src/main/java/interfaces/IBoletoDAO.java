/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Entitys.Boleto;
import excepciones.PersistenciaException;

/**
 *
 * @author maria
 */
public interface IBoletoDAO {
    
    Boleto obtenerBoleto(String idBoleto) throws PersistenciaException;
    
    boolean cancelarBoleto(String idBoleto) throws PersistenciaException;
    
    Boleto agregarBoleto(Boleto boleto) throws PersistenciaException;
    
    Boleto buscarPorToken(String token) throws PersistenciaException;
    
    boolean actualizarEstado(Boleto boleto) throws PersistenciaException;
    
    public void actualizarAsiento(String idReservacion, String idAsientoNuevo) throws PersistenciaException;
}
