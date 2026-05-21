package RevisionBoletos;

import dtos.AsientoEventoDTO;
import dtos.AsistenciaDTO;
import dtos.BoletoDTO;
import dtos.EventoDTO;
import dtos.ReporteAsistenciaDTO;
import excepciones.RevisionBoletosException;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Subsistema de revisión de boletos.
 *
 * @author Kaleb
 */
public class RevisionBoletos implements IRevisionBoletos {

    ControlRevisionBoletos control = ControlRevisionBoletos.getInstance();

    @Override
    public boolean iniciarCamara() throws RevisionBoletosException {
        return control.iniciarCamara();
    }

    @Override
    public BufferedImage obtenerFrameActual() throws RevisionBoletosException {
        return control.obtenerFrameActual();
    }

    @Override
    public String leerQR(BufferedImage frame) throws RevisionBoletosException {
        return control.leerQR(frame);
    }

    @Override
    public AsistenciaDTO registrarAsistencia(String token, AsistenciaDTO asistenciaDTO, String idEvento) throws RevisionBoletosException{
        return control.registrarAsistencia(token, asistenciaDTO, idEvento);
    }

    @Override
    public EventoDTO buscarEvento(String idEvento) throws RevisionBoletosException {
        return control.buscarEvento(idEvento);
    }

    @Override
    public List<EventoDTO> buscarEventosPorNombre(String nombre) throws RevisionBoletosException {
        return control.buscarEventosPorNombre(nombre);
    }

    @Override
    public List<AsientoEventoDTO> obtenerAsientosConAsistencia(String idEvento) throws RevisionBoletosException {
        return control.obtenerAsientosConAsistencia(idEvento);
    }

    @Override
    public List<EventoDTO> obtenerEventosActuales() throws RevisionBoletosException {
        return control.obtenerEventosActuaales();
    }

    @Override
    public BoletoDTO obtenerBoletoPorToken(String tokebn) throws RevisionBoletosException {
        return control.buscarBoleto(tokebn);
    }

    @Override
    public ReporteAsistenciaDTO obtenerResumen(String idEvento) throws RevisionBoletosException {
        return control.obtenerResumenAsistencias(idEvento);
    }

}
