package IpWebcam;

import excepciones.CamaraException;
import java.awt.image.BufferedImage;

/**
 *
 * @author Kaleb
 */
public interface IIpWebcam {

    BufferedImage obtenerCamara() throws CamaraException;
}
