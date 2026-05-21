/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fabricas;

import dtos.FacturaDTO;
import dtos.PagoDTO;
import dtos.PerfilFiscalDTO;
import dtos.ReservacionDTO;

/**
 *
 * @author aaron
 */
public interface IFabricaFacturaBO {
    
    public FacturaDTO crearFactura(PerfilFiscalDTO perfil, ReservacionDTO reserva);
}
