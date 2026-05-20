package RevisionBoletos;

import IpWebcam.IIpWebcam;
import IpWebcam.IpWebcam;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import dtos.AsientoEventoDTO;
import dtos.AsistenciaDTO;
import dtos.BoletoDTO;
import dtos.CategoriaDTO;
import dtos.ENUMS.EstadoBoletoDTO;
import dtos.EventoDTO;
import dtos.ReporteAsistenciaDTO;
import excepciones.CamaraException;
import excepciones.NegocioException;
import excepciones.RevisionBoletosException;
import interfaces.IEventoBO;
import interfaces.IReservacionBO;
import java.awt.image.BufferedImage;
import java.util.List;
import objetosNegocio.EventoBO;
import objetosNegocio.ReservacionBO;

/**
 * Control encargado de gestionar el proceso de revisión de boletos mediante
 * lectura de códigos QR y registro de asistencia.
 *
 * @author Kaleb
 */
public class ControlRevisionBoletos {

    private static ControlRevisionBoletos instancia;
    private final IReservacionBO reservacionBO;
    private final IEventoBO eventoBO;
    private final IIpWebcam camara = new IpWebcam();
    private String ultimoQR = "";

    /**
     * Constructor privado utilizado para implementar el patrón Singleton.
     */
    private ControlRevisionBoletos() {
        this.reservacionBO = ReservacionBO.getInstance();
        this.eventoBO = EventoBO.getInstance();
    }

    /**
     * Obtiene la única instancia del controlador.
     *
     * @return Instancia única de ControlRevisionBoletos.
     */
    public static ControlRevisionBoletos getInstance() {
        if (instancia == null) {
            instancia = new ControlRevisionBoletos();
        }
        return instancia;
    }

    /**
     * Inicia la cámara IP verificando que sea posible obtener un frame.
     *
     * @return true si la cámara respondió correctamente, false en caso
     * contrario.
     */
    protected boolean iniciarCamara() {
        try {
            BufferedImage prueba = obtenerFrameActual();
            return prueba != null;
        } catch (RevisionBoletosException e) {
            return false;
        }
    }

    /**
     * Obtiene el frame actual capturado por la cámara IP.
     *
     * @return Imagen capturada desde la cámara.
     *
     * @throws RevisionBoletosException Se lanza cuando ocurre un error de
     * comunicación con la cámara.
     */
    protected BufferedImage obtenerFrameActual() throws RevisionBoletosException {
        try {
            return camara.obtenerCamara();
        } catch (CamaraException e) {
            throw new RevisionBoletosException("Error al obtener frame de imagen desde el módulo de cámara: " + e.getMessage());
        }
    }

    /**
     * Lee un código QR contenido dentro de una imagen.
     *
     * @param frame Imagen que será analizada.
     *
     * @return Token leído desde el QR o null si no se detectó ninguno.
     *
     * @throws RevisionBoletosException Se lanza cuando ocurre un error durante
     * el procesamiento.
     */
    protected String leerQR(BufferedImage frame) throws RevisionBoletosException {
        try {
            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(frame);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = new MultiFormatReader().decode(bitmap);
            String textoQR = result.getText();

            if (!textoQR.equals(ultimoQR)) {
                ultimoQR = textoQR;
                return textoQR;
            }

            return null;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Busca un boleto utilizando el token generado en el código QR.
     *
     * @param token Token único del boleto.
     *
     * @return Boleto encontrado o null si no existe.
     *
     * @throws RevisionBoletosException Se lanza cuando ocurre un error en la
     * búsqueda.
     */
    protected BoletoDTO buscarBoleto(String token) throws RevisionBoletosException {
        try {
            return reservacionBO.buscarPorToken(token);
        } catch (NegocioException e) {
            throw new RevisionBoletosException(e.getMessage());
        }
    }

    /**
     * Valida si un boleto puede registrar asistencia.
     *
     * @param boletoDTO Boleto a validar.
     *
     * @return true si el boleto es válido y no ha sido utilizado.
     */
    private boolean validarBoleto(BoletoDTO boletoDTO) {
        if (boletoDTO == null) {
            return false;
        }

        return boletoDTO.getEstadoBoleto() != EstadoBoletoDTO.USADO;
    }

    /**
     * Registra la asistencia de un boleto utilizando el token QR.
     *
     * @param token Token del boleto.
     * @param asistenciaDTO Información de asistencia a registrar.
     * @param idEventoActual ID del evento en el que está operando la cámara
     * actualmente.
     *
     * @return AsistenciaDTO si la asistencia fue registrada correctamente.
     *
     * @throws RevisionBoletosException Se lanza cuando el boleto no existe, no
     * pertenece al evento, ya fue utilizado o ocurre un error.
     */
    protected AsistenciaDTO registrarAsistencia(String token, AsistenciaDTO asistenciaDTO, String idEventoActual) throws RevisionBoletosException {

        try {

            BoletoDTO boletoDTO = buscarBoleto(token);

            if (boletoDTO == null) {
                throw new RevisionBoletosException("El boleto no existe.");
            }

            if (boletoDTO.getEvento() == null || !boletoDTO.getEvento().getIdEvento().equals(idEventoActual)) {
                String nombreEventoCorrecto = (boletoDTO.getEvento() != null && boletoDTO.getEvento().getNombreEvento() != null)
                        ? boletoDTO.getEvento().getNombreEvento()
                        : "otro evento";
                throw new RevisionBoletosException("ACCESO DENEGADO: El boleto pertenece a '" + nombreEventoCorrecto + "'.");
            }

            if (!validarBoleto(boletoDTO)) {
                throw new RevisionBoletosException("El boleto ya fue utilizado.");
            }

            AsistenciaDTO asistencia = reservacionBO.registrarAsistencia(boletoDTO, asistenciaDTO);
            boletoDTO.setEstadoBoleto(EstadoBoletoDTO.USADO);

            reservacionBO.actualizarEstado(boletoDTO);

            return asistencia;

        } catch (NegocioException e) {
            throw new RevisionBoletosException(e.getMessage());
        }
    }

    /**
     * Obtiene los eventos activos disponibles para revisión.
     *
     * @return Lista de eventos actuales.
     *
     * @throws RevisionBoletosException Se lanza cuando ocurre un error.
     */
    protected List<EventoDTO> obtenerEventosActuaales() throws RevisionBoletosException {
        try {
            return eventoBO.obtenerEventosActuales();
        } catch (NegocioException e) {
            throw new RevisionBoletosException(e.getMessage());
        }
    }

    /**
     * Busca un evento por su identificador.
     *
     * @param idEvento Identificador del evento.
     *
     * @return Evento encontrado.
     *
     * @throws RevisionBoletosException Se lanza cuando ocurre un error.
     */
    protected EventoDTO buscarEvento(String idEvento) throws RevisionBoletosException {
        try {
            return eventoBO.obtenerEventoPorId(idEvento);
        } catch (NegocioException e) {
            throw new RevisionBoletosException(e.getMessage());
        }
    }

    /**
     * Busca eventos por coincidencia de nombre.
     *
     * @param nombre Nombre del evento a buscar.
     *
     * @return Lista de eventos encontrados.
     *
     * @throws RevisionBoletosException Se lanza cuando ocurre un error.
     */
    protected List<EventoDTO> buscarEventosPorNombre(String nombre) throws RevisionBoletosException {
        try {
            return eventoBO.buscarPorNombre(nombre);
        } catch (NegocioException e) {
            throw new RevisionBoletosException(e.getMessage());
        }
    }

    /**
     * Obtiene los asientos que ya registraron asistencia en un evento.
     *
     * @param idEvento Identificador del evento.
     *
     * @return Lista de asientos con asistencia registrada.
     *
     * @throws RevisionBoletosException Se lanza cuando ocurre un error.
     */
    protected List<AsientoEventoDTO> obtenerAsientosConAsistencia(String idEvento) throws RevisionBoletosException {
        try {
            return reservacionBO.obtenerAsientosConAsistencia(idEvento);
        } catch (NegocioException e) {
            throw new RevisionBoletosException(e.getMessage());
        }
    }

    protected ReporteAsistenciaDTO obtenerResumenAsistencias(String idEvento) throws RevisionBoletosException {
        try {
            return reservacionBO.obtenerResumenBoletosEvento(idEvento);
        } catch (NegocioException e) {
            throw new RevisionBoletosException(e.getMessage());
        }
    }
}
