package IpWebcam;

import excepciones.CamaraException;
import java.awt.image.BufferedImage;

/**
 *
 * @author Kaleb
 */
public class IpWebcam implements IIpWebcam {

    private ControlIpWebcam control = ControlIpWebcam.getInstance();

    public IpWebcam() {
    }

    @Override
    public BufferedImage obtenerCamara() throws CamaraException {
        return control.obtenerCamara();
    }

}
