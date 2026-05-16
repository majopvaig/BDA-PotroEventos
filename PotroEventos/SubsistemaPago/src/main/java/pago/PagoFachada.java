package pago;

import adaptadores.BancoAdapter;
import dtos.CobroDTO;
import dtos.PagoDTO;
import dtos.TarjetaDTO;
import excepciones.PagoException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fachada del subsistema de pagos.
 *
 * Expone un punto único de acceso para que otras capas del sistema puedan
 * procesar pagos sin conocer la lógica interna del subsistema.
 *
 * La fachada se comunica internamente con la clase de control encargada de
 * ejecutar el proceso de pago.
 *
 * @author Aaron Burciaga - 262788
 * @author Brian Sandoval - 262741
 * @author Dayanara Peralta - 262695
 * @author María Valdez - 262775
 */
public class PagoFachada implements IPago {

    /**
     * Logger de la fachada.
     */
    private static final Logger LOG = Logger.getLogger(PagoFachada.class.getName());

    /**
     * Controlador interno del subsistema.
     */
    private final ControlPago control;

    /**
     * Constructor que inicializa el controlador.
     */
    public PagoFachada() {
        this.control = ControlPago.getInstance();
    }

    /**
     * Procesa un pago delegando la operación al controlador.
     *
     * @param tarjetaDTO datos de la tarjeta
     * @param cobroDTO datos del cobro
     * @return true si el pago fue exitoso
     * @throws PagoException si ocurre un error durante el proceso
     */
    @Override
    public PagoDTO procesarPago(TarjetaDTO tarjetaDTO, CobroDTO cobroDTO) throws PagoException {
        try {
            PagoDTO pago = BancoAdapter.infraestructuraADTO(control.realizarPago(tarjetaDTO, cobroDTO));

            LOG.log(Level.INFO, "Pago procesado correctamente.");
            return pago;

        } catch (PagoException ex) {
            LOG.log(Level.SEVERE, "Error al procesar pago en fachada.", ex);
            throw ex;
        }
    }
}
