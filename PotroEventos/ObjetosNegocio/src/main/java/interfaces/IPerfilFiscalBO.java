/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.FacturaDTO;
import dtos.PagoDTO;
import dtos.PerfilFiscalDTO;
import dtos.ReservacionDTO;
import excepciones.NegocioException;

/**
 *
 * @author aaron
 */
public interface IPerfilFiscalBO {
    
    public boolean guardarPerfilFiscal(PerfilFiscalDTO guardar, String idUsuario);
    
}
