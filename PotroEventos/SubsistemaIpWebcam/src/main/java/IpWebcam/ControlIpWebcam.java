package IpWebcam;

import dtos.ConfiguracionCamaraDTO;
import excepciones.CamaraException;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Kaleb
 */
public class ControlIpWebcam {

    private static ControlIpWebcam instancia;

    private ControlIpWebcam() {
    }

    public static ControlIpWebcam getInstance() {
        if (instancia == null) {
            instancia = new ControlIpWebcam();
        }
        return instancia;
    }

    protected BufferedImage obtenerCamara() throws CamaraException {
        try {
            // Concatenamos el tiempo actual para saltar la caché de red
            URL url = new URL(new ConfiguracionCamaraDTO().getURLCAMARA() + System.currentTimeMillis());

            // Abrimos el flujo de red usando el Buffer de alta velocidad
            try (InputStream input = url.openStream(); BufferedInputStream bis = new BufferedInputStream(input)) {

                BufferedImage imagen = ImageIO.read(bis);

                if (imagen == null) {
                    throw new CamaraException("El servidor IP Webcam devolvió un flujo de imagen inválido o vacío.");
                }

                return imagen;
            }
        } catch (IOException e) {
            throw new CamaraException("Error de red al conectar con IP Webcam: " + e.getMessage());
        }
    }
}
