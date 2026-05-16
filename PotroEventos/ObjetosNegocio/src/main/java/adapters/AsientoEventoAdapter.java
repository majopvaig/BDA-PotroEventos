package adapters;

import Entitys.AsientoEvento;
import Entitys.ENUMS.EstadoAsiento;
import dtos.AsientoEventoDTO;
import dtos.ENUMS.EstadoAsientoDTO; // Tu Enum
import java.util.ArrayList;
import java.util.List;

public class AsientoEventoAdapter {

    public static AsientoEventoDTO entidadADTO(AsientoEvento entidad) {
        if (entidad == null) {
            return null;
        }
        AsientoEventoDTO dto = new AsientoEventoDTO();
        dto.setIdAsientoEvento(entidad.getIdAsientoEvento());
        dto.setPrecio(entidad.getPrecio());

        if (entidad.getEstadoAsiento() != null) {
            dto.setEstadoAsiento(EstadoAsientoDTO.valueOf(entidad.getEstadoAsiento().name()));
        }

        dto.setAsiento(AsientoAdapter.entidadADTO(entidad.getAsiento()));
        dto.setEvento(EventoAdapter.entidadADTO(entidad.getEvento()));
        return dto;
    }
    
    public static AsientoEvento dtoAEntidad(AsientoEventoDTO dto) {
        if (dto == null) {
            return null;
        }

        AsientoEvento entidad = new AsientoEvento();

        entidad.setIdAsientoEvento(dto.getIdAsientoEvento());
        entidad.setPrecio(dto.getPrecio());
        entidad.setEstadoAsiento(EstadoAsiento.valueOf(dto.getEstadoAsiento().name()));
        entidad.setAsiento(AsientoAdapter.dtoAEntidad(dto.getAsiento()));
        entidad.setEvento(EventoAdapter.dtoAEntidad(dto.getEvento()));

        return entidad;
    }

    public static List<AsientoEventoDTO> listaEntidadADTO(List<AsientoEvento> entidades) {
        List<AsientoEventoDTO> dtos = new ArrayList<>();
        if (entidades != null) {
            for (AsientoEvento ent : entidades) {
                dtos.add(entidadADTO(ent));
            }
        }
        return dtos;
    }
}
