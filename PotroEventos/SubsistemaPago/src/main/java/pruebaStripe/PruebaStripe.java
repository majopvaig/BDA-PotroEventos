package pruebaStripe;

import adaptadores.BancoAdapter;
import com.stripe.Stripe;
import pago.ControlPago;
import dtos.CobroDTO;
import dtos.PagoDTO;
import dtos.TarjetaDTO;
import excepciones.PagoException;

/**
 * Clase de prueba para validar el subsistema de pagos.
 *
 * La clave secreta de Stripe se obtiene desde una variable de entorno para
 * evitar exponer credenciales en el código fuente.
 *
 * Variable requerida: STRIPE_SECRET_KEY
 *
 * @author Aaron Burciaga - 262788
 * @author Brian Sandoval - 262741
 * @author Dayanara Peralta - 262695
 * @author María Valdez - 262775
 */
public class PruebaStripe {

    /**
     * Método principal de prueba.
     *
     * @param args argumentos de ejecución
     */
    public static void main(String[] args) {
        /**
        String apiKey = "Clave_Privada";

        if (apiKey == null || apiKey.isBlank()) {
            System.out.println("No se encontró la variable STRIPE_SECRET_KEY");
            return;
        }

        Stripe.apiKey = apiKey;

        try {
            TarjetaDTO tarjeta = new TarjetaDTO(
                    "4000000000009995",
                    12,
                    2030,
                    "123"
            );

            CobroDTO cobro = new CobroDTO(
                    1000004L,
                    "mxn",
                    "Compra de boleto"
            );

            ControlPago control = ControlPago.getInstance();

            PagoDTO exito = BancoAdapter.infraestructuraADTO(control.realizarPago(tarjeta, cobro));

            if (exito != null) {
                System.out.println("Pago procesado correctamente.");
            } else {
                System.out.println("El pago no fue aprobado.");
            }

        } catch (PagoException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        */
    }
}
