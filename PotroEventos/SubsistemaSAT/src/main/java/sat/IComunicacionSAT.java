/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sat;

import dtos.PerfilFiscalInfraestructuraDTO;
import excepciones.SatException;

/**
 *
 * @author aaron
 */
public interface IComunicacionSAT {
    
    public PerfilFiscalInfraestructuraDTO obtenerPerfilFiscal(String rfc) throws SatException;
    
}
