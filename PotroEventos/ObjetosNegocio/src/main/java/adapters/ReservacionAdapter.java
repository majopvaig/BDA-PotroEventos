/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adapters;

import Entitys.Reservacion;
import Entitys.ENUMS.ReservacionEstado;
import dtos.ENUMS.ReservacionEstadoDTO;
import dtos.ReservacionDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maria
 */
public class ReservacionAdapter {

    private ReservacionAdapter() {
    }

    public static ReservacionDTO entidadADTO(Reservacion reservacion) {
        if (reservacion == null) {
            return null;
        }

        return new ReservacionDTO(
                reservacion.getIdReservacion(),
                reservacion.getTotal(),
                BoletoAdapter.entidadADTO(reservacion.getBoleto()),
                PagoAdapter.convertirADTO(reservacion.getPago()),
                UsuarioAdapter.entidadADTO(reservacion.getUsuario()),
                reservacion.getFechaHora(),
                ReservacionEstadoDTO.valueOf(reservacion.getEstado().name()),
                DevolucionAdapter.convertirADTO(reservacion.getDevolucion()));
    }

    public static Reservacion dtoAEntidad(ReservacionDTO reservacionDTO) {
        if (reservacionDTO == null) {
            return null;
        }

        ReservacionEstado estado = null;
        if (reservacionDTO.getEstado() != null) {
            estado = ReservacionEstado.valueOf(reservacionDTO.getEstado().name());
        }

        return new Reservacion(
                reservacionDTO.getIdReservacion(),
                reservacionDTO.getTotal(),
                BoletoAdapter.dtoAEntidad(reservacionDTO.getBoleto()),
                PagoAdapter.convertirAEntidad(reservacionDTO.getPago()),
                UsuarioAdapter.dtoAEntidad(reservacionDTO.getUsuario()),
                reservacionDTO.getFechaHora(),
                ReservacionEstado.valueOf(reservacionDTO.getEstado().name()),
                DevolucionAdapter.convertirAEntidad(reservacionDTO.getDevolucion())
        );
    }

    public static List<ReservacionDTO> listaDTOs(List<Reservacion> lista) {
        List<ReservacionDTO> dtos = new ArrayList<>();

        for (Reservacion r : lista) {
            dtos.add(entidadADTO(r));
        }

        return dtos;
    }
}
