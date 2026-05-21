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
 * Define las operaciones del subsistema de revisión de boletos.
 *
 * @author Kaleb
 */
public interface IRevisionBoletos {

    /**
     * Inicia la cámara IP.
     *
     * @return true si la cámara fue iniciada correctamente.
     *
     * @throws RevisionBoletosException Se lanza cuando ocurre un error.
     */
    boolean iniciarCamara() throws RevisionBoletosException;

    /**
     * Obtiene el frame actual de la cámara.
     *
     * @return Imagen actual.
     *
     * @throws RevisionBoletosException Se lanza cuando ocurre un error.
     */
    BufferedImage obtenerFrameActual() throws RevisionBoletosException;

    /**
     * Lee un código QR desde un frame.
     *
     * @param frame Imagen capturada.
     *
     * @return Token leído o null si no se detecta QR.
     *
     * @throws RevisionBoletosException Se lanza cuando ocurre un error.
     */
    String leerQR(BufferedImage frame) throws RevisionBoletosException;

    /**
     * Registra asistencia de un boleto.
     *
     * @param token Token del boleto.
     *
     * @param asistenciaDTO Información de asistencia.
     *
     * @return true si se registró correctamente.
     *
     * @throws RevisionBoletosException Se lanza cuando ocurre un error.
     */
    public AsistenciaDTO registrarAsistencia(String token, AsistenciaDTO asistenciaDTO, String idEvento) throws RevisionBoletosException;

    BoletoDTO obtenerBoletoPorToken(String tokebn) throws RevisionBoletosException;

    /**
     * Busca un evento por ID.
     *
     * @param idEvento ID evento.
     *
     * @return Evento encontrado.
     *
     * @throws RevisionBoletosException Se lanza cuando ocurre un error.
     */
    EventoDTO buscarEvento(String idEvento) throws RevisionBoletosException;

    /**
     * Busca eventos por nombre.
     *
     * @param nombre Nombre evento.
     *
     * @return Lista de eventos.
     *
     * @throws RevisionBoletosException Se lanza cuando ocurre un error.
     */
    List<EventoDTO> buscarEventosPorNombre(String nombre) throws RevisionBoletosException;

    /**
     * Obtiene asientos con asistencia.
     *
     * @param idEvento ID evento.
     *
     * @return Lista de asientos.
     *
     * @throws RevisionBoletosException Se lanza cuando ocurre un error.
     */
    List<AsientoEventoDTO> obtenerAsientosConAsistencia(String idEvento) throws RevisionBoletosException;

    List<EventoDTO> obtenerEventosActuales() throws RevisionBoletosException;

    ReporteAsistenciaDTO obtenerResumen(String idEvento) throws RevisionBoletosException;
}
