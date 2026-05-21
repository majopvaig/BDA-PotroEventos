/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Entitys.AsientoEvento;
import Entitys.Asistencia;
import Entitys.Boleto;
import Entitys.Devolucion;
import Entitys.Reservacion;
import entidadesmongo.ReporteAsistencia;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author maria
 */
public interface IReservacionDAO {

    boolean guardarReservacion(Reservacion reservacion) throws PersistenciaException;

    List<Reservacion> obtenerReservacionesUsuario(String idUsuario) throws PersistenciaException;

    Boleto obtenerBoleto(String idReservacion) throws PersistenciaException;

    boolean cancelarReservacion(Devolucion devolucion, String idReservacion) throws PersistenciaException;

    /**
     * Busca un boleto utilizando su token.
     *
     * @param token Token del boleto.
     *
     * @return Boleto encontrado o {@code null} si no existe.
     *
     * @throws PersistenciaException Se lanza cuando ocurre un error durante la
     * consulta.
     */
    Boleto buscarPorToken(String token) throws PersistenciaException;

    /**
     * Actualiza el estado de un boleto.
     *
     * @param boleto boleto a actualziar.
     * @return {@code true} si la actualización fue exitosa.
     *
     * @throws PersistenciaException Se lanza cuando ocurre un error durante la
     * actualización.
     */
    boolean actualizarEstado(Boleto boleto) throws PersistenciaException;

    /**
     * Registra una asistencia utilizando el token de un boleto.
     *
     * @param boleto boleto a registrar la asistencia
     *
     * @param asistencia Asistencia a registrar.
     *
     * @return Asistencia registrada.
     *
     * @throws PersistenciaException Se lanza cuando ocurre un error durante la
     * operación.
     */
    Asistencia registrarAsistencia(Boleto boleto, Asistencia asistencia) throws PersistenciaException;

    /**
     * Obtiene un resumen estadístico de los boletos pertenecientes a un evento.
     *
     * @param idEvento Identificador del evento del cual se desea obtener el
     * resumen estadístico.
     *
     * @return Objeto {@link ResumenBoletosEvento} con la información calculada.
     *
     */
    public ReporteAsistencia obtenerReporteAsistencia(String idEvento);

    /**
     * Obtiene los asientos correspondientes a boletos que registraron
     * asistencia.
     *
     * @param idEvento Identificador del evento.
     *
     * @return Lista de asientos utilizados.
     *
     * @throws PersistenciaException Se lanza cuando ocurre un error durante la
     * consulta.
     */
    List<AsientoEvento> obtenerAsientosConAsistencia(String idEvento) throws PersistenciaException;
}
