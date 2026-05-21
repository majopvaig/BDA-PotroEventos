/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import dtos.FacturaDTO;
import dtos.PerfilFiscalDTO;
import dtos.ReservacionDTO;
import excepciones.NegocioException;

/**
 *
 * @author aaron
 */
public interface IFacturaBO {
    
    
    public boolean guardarFactura(FacturaDTO factura)throws NegocioException;
    
    public FacturaDTO crearFactura(PerfilFiscalDTO perfil, ReservacionDTO reserva)throws NegocioException;

}
