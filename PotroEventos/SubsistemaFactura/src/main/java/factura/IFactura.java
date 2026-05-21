/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package factura;

import dtos.BoletoDTO;
import dtos.FacturaDTO;
import dtos.PagoDTO;
import dtos.PerfilFiscalDTO;
import dtos.ReservacionDTO;
import excepciones.FacturaException;

/**
 *
 * @author aaron
 */
public interface IFactura {
    
    boolean obtenerFactura(String idReservacion)throws FacturaException;
    
    PerfilFiscalDTO buscarPerfil(String idUsuario) throws FacturaException;
    
    PerfilFiscalDTO buscarPerfilFiscal(String rfc, String idUsuario)throws FacturaException;
    
    FacturaDTO crearFactura(PerfilFiscalDTO perfil, ReservacionDTO reserva)throws FacturaException;
    
    boolean generarFactura(FacturaDTO factura)throws FacturaException;
}
