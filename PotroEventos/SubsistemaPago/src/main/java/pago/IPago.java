package pago;

import dtos.CobroDTO;
import dtos.PagoDTO;
import dtos.TarjetaDTO;
import excepciones.PagoException;

/**
 * Interfaz que define las operaciones del subsistema de pagos. Permite procesar
 * pagos utilizando los datos proporcionados por la capa de presentación.
 *
 * Esta interfaz actúa como contrato para la fachada del subsistema.
 *
 * @author Aaron Burciaga - 262788
 * @author Brian Sandoval - 262741
 * @author Dayanara Peralta - 262695
 * @author María Valdez - 262775
 */
public interface IPago {

    /**
     * Procesa un pago a partir de los datos de tarjeta y cobro.
     *
     * @param tarjetaDTO datos de la tarjeta
     * @param cobroDTO datos del cobro
     * @return true si el pago fue exitoso, false en caso contrario
     * @throws PagoException si ocurre un error durante el proceso
     */
    PagoDTO procesarPago(TarjetaDTO tarjetaDTO, CobroDTO cobroDTO) throws PagoException;
}
