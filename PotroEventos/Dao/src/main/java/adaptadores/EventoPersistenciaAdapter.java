/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import Entitys.Categoria;
import Entitys.ENUMS.CategoriaEvento;
import Entitys.ENUMS.EstadoEvento;
import Entitys.ENUMS.TipoEventoP;
import Entitys.ENUMS.TipoUbicacionP;
import Entitys.Evento;
import Entitys.Ubicacion;
import excepciones.PersistenciaException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author maria
 */
public class EventoPersistenciaAdapter {

    public static Evento convertirADominio(Document documento) throws PersistenciaException {
        if (documento == null) {
            return null;
        }
        Evento dominio = new Evento();
        if (documento.getObjectId("_id") != null) {
            dominio.setIdEvento(documento.getObjectId("_id").toHexString());
        } else if (documento.getString("id") != null) {
            dominio.setIdEvento(documento.getString("id"));
        }
        //dominio.setIdEvento(documento.getObjectId("_id").toHexString());
        dominio.setNombreEvento(documento.getString("nombre"));
        dominio.setInformacionEvento(documento.getString("informacion"));
        Date fh = (Date) documento.getDate("fechaHora");
        if (fh != null) {
            dominio.setFechaHora(documento
                    .getDate("fechaHora")
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
        }
        dominio.setEstadoEvento(EstadoEvento.valueOf(documento.getString("estado")));
        dominio.setUrlImagen(documento.getString("urlImagen"));
        dominio.setGratuito(documento.getBoolean("gratuito"));
        String tipo = documento.getString("tipo");
        if (tipo != null) {
            dominio.setTipoEvento(TipoEventoP.valueOf(documento.getString("tipo")));
        }
        dominio.setDisponibilidad(documento.getInteger("disponibilidad"));

        Document catDoc = (Document) documento.get("categoria");
        if (catDoc != null) {
            Categoria cat = new Categoria();
            cat.setId(catDoc.getObjectId("_id").toHexString());
            cat.setNombre(CategoriaEvento.valueOf(catDoc.getString("nombre")));
            cat.setUrlImagen((catDoc.getString("urlImagen")));
            dominio.setCategoriaEvento(cat);
        }

        Document ubiDoc = (Document) documento.get("ubicacion");
        if (ubiDoc != null) {
            Ubicacion ubi = new Ubicacion();
            ubi.setIdUbicacion(ubiDoc.getObjectId("_id").toHexString());
            ubi.setNombre(ubiDoc.getString("nombre"));
            ubi.setTipo(TipoUbicacionP.valueOf(ubiDoc.getString("tipoUbicacion")));
            ubi.setCapacidad(ubiDoc.getInteger("capacidad"));
            ubi.setSecciones(SeccionPersistenciaAdapter.convertirDocsADominio(ubiDoc.getList("secciones", Document.class)));
            dominio.setUbicacion(ubi);
        }

        return dominio;
    }

    public static List<Evento> convetirListaADominio(List<Document> lista) throws PersistenciaException {
        List<Evento> eventos = new ArrayList<>();

        if (lista == null) {
            return eventos;
        }

        for (Document mongo : lista) {
            eventos.add(convertirADominio(mongo));
        }

        return eventos;
    }

}
