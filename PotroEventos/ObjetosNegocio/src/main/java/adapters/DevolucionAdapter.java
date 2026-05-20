/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adapters;

import Entitys.Devolucion;
import Entitys.ENUMS.MotivoDevolucionP;
import Entitys.ENUMS.TipoDevolucionP;
import dtos.DevolucionDTO;
import dtos.ENUMS.MotivoDevolucionN;
import dtos.ENUMS.TipoDevolucionN;

/**
 *
 * @author maria
 */
public class DevolucionAdapter {

    public static DevolucionDTO convertirADTO(Devolucion entidad) {
        if (entidad == null) {
            return null;
        }

        DevolucionDTO dto = new DevolucionDTO();
        dto.setMotivo(MotivoDevolucionN.valueOf(entidad.getMotivo().name()));
        dto.setDescripcion(entidad.getDescripcion());
        dto.setFechaHoraDevolucion(entidad.getFechaHoraDevolucion());
        if (entidad.getTipo() != null) {
            dto.setTipo(TipoDevolucionN.valueOf(entidad.getTipo().name()));
        }
        if (entidad.getReembolso() != null) {
            dto.setReembolso(ReembolsoAdapter.convertirADTO(entidad.getReembolso()));
        }
        return dto;
    }

    public static Devolucion convertirAEntidad(DevolucionDTO dto) {
        if (dto == null) {
            return null;
        }

        Devolucion entidad = new Devolucion();
        entidad.setMotivo(MotivoDevolucionP.valueOf(dto.getMotivo().name()));
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setFechaHoraDevolucion(dto.getFechaHoraDevolucion());
        if (dto.getTipo() != null) {
            entidad.setTipo(TipoDevolucionP.valueOf(dto.getTipo().name()));
        }
        if (dto.getReembolso() != null) {
            entidad.setReembolso(ReembolsoAdapter.convertirAEntidad(dto.getReembolso()));
        }
        return entidad;
    }
}
