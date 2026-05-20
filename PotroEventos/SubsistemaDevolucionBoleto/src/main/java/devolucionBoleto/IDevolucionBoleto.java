/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package devolucionBoleto;

import dtos.DevolucionDTO;
import dtos.ENUMS.TipoDevolucionN;
import dtos.ReservacionDTO;
import excepciones.DevolucionBoletoException;
import java.time.LocalDateTime;

/**
 *
 * @author maria
 */
public interface IDevolucionBoleto {
    
    boolean validarTiempoDevolucion(LocalDateTime fechaEvento);
    
    boolean realizarDevolucionGratuita(ReservacionDTO reservacion, DevolucionDTO devolucion) throws DevolucionBoletoException;
    
    boolean realizarDevolucionPago(ReservacionDTO reservacion, TipoDevolucionN tipo, DevolucionDTO devolucion) throws DevolucionBoletoException;
    
}
