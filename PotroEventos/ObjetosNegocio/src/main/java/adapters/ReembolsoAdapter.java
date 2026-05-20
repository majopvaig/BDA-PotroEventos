/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adapters;

import Entitys.Reembolso;
import dtos.ReembolsoDTO;

/**
 *
 * @author maria
 */
public class ReembolsoAdapter {

    public static ReembolsoDTO convertirADTO(Reembolso entidad) {
        if (entidad == null) {
            return null;
        }

        return new ReembolsoDTO(
                entidad.getIdOperacion(),
                entidad.getFechaOperacion(),
                entidad.getImporte(),
                entidad.getMetodoPago());
    }

    public static Reembolso convertirAEntidad(ReembolsoDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Reembolso(
                dto.getIdOperacion(),
                dto.getFechaOperacion(),
                dto.getImporte(),
                dto.getMetodoPago());
    }
}
