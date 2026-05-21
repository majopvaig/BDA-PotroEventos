/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fabricas;

import dtos.FacturaDTO;
import dtos.PagoDTO;
import dtos.PerfilFiscalDTO;
import dtos.ReservacionDTO;
import java.time.LocalDateTime;

/**
 *
 * @author aaron
 */
public class FabricaFacturaBO implements IFabricaFacturaBO{

    private static FabricaFacturaBO instance;
    private FabricaFacturaBO() {
    }
    
    public static FabricaFacturaBO getInstance(){
        if(instance == null){
            instance = new FabricaFacturaBO();
        }
        return instance;
    }
    
    @Override
    public FacturaDTO crearFactura(PerfilFiscalDTO perfil, ReservacionDTO reserva){
        
        FacturaDTO factura = new FacturaDTO();
        
        factura.setPerfil(perfil);
        factura.setMoneda("MXN");
        factura.setFechaCompra(reserva.getPago().getFechaOperacion());
        factura.setTotal(reserva.getPago().getImporte());
        factura.setMetodoPago(reserva.getPago().getMetodoPago());
        factura.setIdReservacion(reserva.getIdReservacion());
        return factura;
    }
    
}
