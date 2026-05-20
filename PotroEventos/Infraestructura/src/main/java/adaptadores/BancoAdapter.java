/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import dtos.CobroDTO;
import dtos.PagoDTO;
import dtos.PaymentDTO;
import dtos.ReembolsoDTO;
import dtos.RefundDTO;
import dtos.StripeChargeDTO;

/**
 *
 * @author maria
 */
public class BancoAdapter {
    
    public static StripeChargeDTO dtoAInfraestructura(CobroDTO cobro, String token){
        if(cobro == null){
            return null;
        }
        
        if(token == null || token.isEmpty() || token.isBlank()){
            return null;
        }
        
        return new StripeChargeDTO(
                    cobro.getMonto(),
                    cobro.getMoneda(),
                    cobro.getDescripcion(),
                    token
            ); 
    }
    
    public static PagoDTO infraestructuraADTO(PaymentDTO payment){
        if(payment == null){
            return null;
        }
        
        return new PagoDTO(
                payment.getPaymentID(), 
                payment.getOperationDate(), 
                payment.getCharge(), 
                payment.getPaymentMethod());
    } 
    
    public static RefundDTO dtoAInfraestructura(ReembolsoDTO dto){
        if(dto == null){
            return null;
        }
        
        return new RefundDTO(
                dto.getIdOperacion(), 
                dto.getFechaOperacion(), 
                dto.getImporte(), 
                dto.getMetodoPago());
    }
    
    public static ReembolsoDTO infraestructuraADTO(RefundDTO infraestructura){
        if(infraestructura == null){
            return null;
        }
        
        return new ReembolsoDTO(
                infraestructura.getRefundID(), 
                infraestructura.getRefundDate(), 
                infraestructura.getAmountRefunded(), 
                infraestructura.getRefundMethod());
    }
}
