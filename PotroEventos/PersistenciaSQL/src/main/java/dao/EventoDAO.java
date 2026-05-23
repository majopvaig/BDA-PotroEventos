/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Entitys.Categoria;
import Entitys.ENUMS.CategoriaEvento;
import Entitys.ENUMS.EstadoEvento;
import Entitys.ENUMS.TipoEventoP;
import Entitys.ENUMS.TipoUbicacionP;
import Entitys.Evento;
import Entitys.Seccion;
import Entitys.Ubicacion;
import adapters.IdAdapter;
import conexion.ConexionBD;
import excepciones.PersistenciaException;
import interfaces.IEventoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maria
 */
public class EventoDAO implements IEventoDAO {
    
    private static EventoDAO instance;
    
    private static final Logger LOG = Logger.getLogger(EventoDAO.class.getName());
    
    private EventoDAO(){}
    
    public static EventoDAO getInstancia(){
        if(instance == null){
            instance = new EventoDAO();
        }
        return instance;
    }

    @Override
    public Evento consultarEvento(String idEvento) throws PersistenciaException {
        if(idEvento == null || idEvento.isEmpty() || idEvento.isBlank()){
            throw new PersistenciaException("Por favor, proporcione un ID.");
        }
        String comando = """
                         select 
                             e.id as id_evento,
                             e.nombre as evento_nombre,
                             e.informacion as evento_informacion,
                             e.fecha_hora as evento_fechaHora,
                             e.estado as evento_estado,
                             e.urlimagen as evento_urlImagen,
                             e.gratuito as evento_gratuito,
                             e.tipo as evento_tipo,
                             e.disponibilidad as evento_disponibilidad,
                             
                             c.id as id_categoria,
                             c.nombre as categoria_nombre,
                             c.urlimagen as categoria_urlImagen,
                             
                             u.id as id_ubicacion,
                             u.nombre as ubicacion_nombre,
                             u.tipo as ubicacion_tipo,
                             u.capacidad as ubicacion_capacidad,
                             
                             s.id as id_seccion,
                             s.nombre as seccion_nombre,
                             s.capacidad as seccion_capacidad,
                             s.precio_base as seccion_precio
                         from eventos e
                         inner join categorias c on e.id_categoria = c.id
                         inner join ubicaciones u on e.id_ubicacion = u.id
                         left join secciones s on s.id_ubicacion = u.id
                         where e.id_evento = ?
                         """;
        Long idLong = IdAdapter.stringALong(idEvento);
        try(Connection con = ConexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(comando)){
            ps.setLong(1, idLong);
            try(ResultSet rs = ps.executeQuery()){
                if(!rs.next()){
                    LOG.log(Level.WARNING, "No se encontró una evento con dicho ID: {0}", idLong);
                    throw new PersistenciaException("No hay registros con el ID proporcionado.");
                }
                return extraerEvento(rs);
            }
        } catch(SQLException ex){
            LOG.log(Level.SEVERE, "Error de SQL al consultar el evento.", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }

    @Override
    public List<Evento> consultarPorCategoria(String idCategoria) throws PersistenciaException {
        if(idCategoria == null || idCategoria.isEmpty() || idCategoria.isBlank()){
            throw new PersistenciaException("Por favor, proporcione un ID.");
        }
        String comando = """
                         select 
                             e.id as id_evento,
                             e.nombre as evento_nombre,
                             e.informacion as evento_informacion,
                             e.fecha_hora as evento_fechaHora,
                             e.estado as evento_estado,
                             e.urlimagen as evento_urlImagen,
                             e.gratuito as evento_gratuito,
                             e.tipo as evento_tipo,
                             e.disponibilidad as evento_disponibilidad,
                             
                             c.id as id_categoria,
                             c.nombre as categoria_nombre,
                             c.urlimagen as categoria_urlImagen,
                             
                             u.id as id_ubicacion,
                             u.nombre as ubicacion_nombre,
                             u.tipo as ubicacion_tipo,
                             u.capacidad as ubicacion_capacidad,
                             
                             s.id as id_seccion,
                             s.nombre as seccion_nombre,
                             s.capacidad as seccion_capacidad,
                             s.precio_base as seccion_precio
                         from eventos e
                         inner join categorias c on e.id_categoria = c.id
                         inner join ubicaciones u on e.id_ubicacion = u.id
                         left join secciones s on s.id_ubicacion = u.id
                         where e.id_categoria = ?
                         """;
        Long idLong = IdAdapter.stringALong(idCategoria);
        try(Connection con = ConexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(comando)){
            ps.setLong(1, idLong);
            try(ResultSet rs = ps.executeQuery()){
                return extraerEventos(rs);
            }
        } catch(SQLException ex){
            LOG.log(Level.SEVERE, "Error de SQL al consultar eventos de la categoría especificada.", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }

    @Override
    public boolean reducirCapacidad(String idEvento) throws PersistenciaException {
        if(idEvento == null || idEvento.isEmpty() || idEvento.isBlank()){
            throw new PersistenciaException("Se ocupa el ID.");
        }
        String comando = """
                         update eventos set disponibilidad = disponibilidad - 1 where id = ? and disponibilidad >= 1
                         """;
        Long idLong = IdAdapter.stringALong(idEvento);
        try(Connection con = ConexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(comando)){
            ps.setLong(1, idLong);
            int filasAfectadas = ps.executeUpdate();
            if(filasAfectadas == 0){
                LOG.log(Level.WARNING, "No se pudo reducir la capacidad del Evento: {0}", idLong);
                throw new PersistenciaException("No se pudo insertar la reservación.");
            }
            return true;
        } catch(SQLException ex){
            LOG.log(Level.SEVERE, "Error de SQL al reducir la capacidad del evento.", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }

    @Override
    public boolean aumentarCapacidad(String idEvento) throws PersistenciaException {
        if(idEvento == null || idEvento.isEmpty() || idEvento.isBlank()){
            throw new PersistenciaException("Se ocupa el ID.");
        }
        String comando = """
                         update eventos set disponibilidad = disponibilidad + 1 where id = ?
                         """;
        Long idLong = IdAdapter.stringALong(idEvento);
        try(Connection con = ConexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(comando)){
            ps.setLong(1, idLong);
            int filasAfectadas = ps.executeUpdate();
            if(filasAfectadas == 0){
                LOG.log(Level.WARNING, "No se pudo aumentar la capacidad del Evento: {0}", idLong);
                throw new PersistenciaException("No se pudo aumentar la reservación.");
            }
            return true;
        } catch(SQLException ex){
            LOG.log(Level.SEVERE, "Error de SQL al aumentar la capacidad del evento.", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }

    @Override
    public List<Evento> consultarPorNombre(String nombre) throws PersistenciaException {
        if(nombre == null || nombre.isEmpty() || nombre.isBlank()){
            throw new PersistenciaException("Por favor, proporcione un nombre.");
        }
        String comando = """
                         select 
                             e.id as id_evento,
                             e.nombre as evento_nombre,
                             e.informacion as evento_informacion,
                             e.fecha_hora as evento_fechaHora,
                             e.estado as evento_estado,
                             e.urlimagen as evento_url_imagen,
                             e.gratuito as evento_gratuito,
                             e.tipo as evento_tipo,
                             e.disponibilidad as evento_disponibilidad,
                             
                             c.id as id_categoria,
                             c.nombre as categoria_nombre,
                             c.urlimagen as categoria_urlImagen,
                             
                             u.id as id_ubicacion,
                             u.nombre as ubicacion_nombre,
                             u.tipo as ubicacion_tipo,
                             u.capacidad as ubicacion_capacidad,
                             
                             s.id as id_seccion,
                             s.nombre as seccion_nombre,
                             s.capacidad as seccion_capacidad,
                             s.precio_base as seccion_precio
                         from eventos e
                         inner join categorias c on e.id_categoria = c.id
                         inner join ubicaciones u on e.id_ubicacion = u.id
                         left join secciones s on s.id_ubicacion = u.id
                         where e.evento_nombre = ?
                         """;
        try(Connection con = ConexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(comando)){
            ps.setString(1, nombre);
            try(ResultSet rs = ps.executeQuery()){
                return extraerEventos(rs);
            }
        } catch(SQLException ex){
            LOG.log(Level.SEVERE, "Error de SQL al consultar el evento.", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }

    @Override
    public List<Evento> consultarEventosActuales() throws PersistenciaException {
        String comando = """
                         select 
                             e.id as id_evento,
                             e.nombre as evento_nombre,
                             e.informacion as evento_informacion,
                             e.fecha_hora as evento_fechaHora,
                             e.estado as evento_estado,
                             e.urlimagen as evento_url_imagen,
                             e.gratuito as evento_gratuito,
                             e.tipo as evento_tipo,
                             e.disponibilidad as evento_disponibilidad,
                             
                             c.id as id_categoria,
                             c.nombre as categoria_nombre,
                             c.urlimagen as categoria_urlImagen,
                             
                             u.id as id_ubicacion,
                             u.nombre as ubicacion_nombre,
                             u.tipo as ubicacion_tipo,
                             u.capacidad as ubicacion_capacidad,
                             
                             s.id as id_seccion,
                             s.nombre as seccion_nombre,
                             s.capacidad as seccion_capacidad,
                             s.precio_base as seccion_precio
                         from eventos e
                         inner join categorias c on e.id_categoria = c.id
                         inner join ubicaciones u on e.id_ubicacion = u.id
                         left join secciones s on s.id_ubicacion = u.id
                         where date(e.fecha_hora) = curdate()
                         """;
        try (Connection con = ConexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(comando)) {
            try (ResultSet rs = ps.executeQuery()) {
                return extraerEventos(rs);
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Error de SQL al consultar el evento.", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }
    
    private Evento extraerEvento(ResultSet rs) throws SQLException {
        if (rs == null) {
            return null;
        }

        Evento evento = null;

        while (rs.next()) {

            if (evento == null) {
                evento = new Evento();
                evento.setIdEvento(IdAdapter.LongAString(rs.getLong("id_evento")));
                evento.setNombreEvento(rs.getString("evento_nombre"));
                evento.setInformacionEvento(rs.getString("evento_informacion"));
                evento.setUrlImagen(rs.getString("evento_urlImagen"));
                evento.setGratuito(rs.getBoolean("evento_gratuito"));

                int disp = rs.getInt("evento_disponibilidad");
                if (!rs.wasNull()) {
                    evento.setDisponibilidad(disp);
                }

                Timestamp tsFecha = rs.getTimestamp("evento_fechaHora");
                if (tsFecha != null) {
                    evento.setFechaHora(tsFecha.toLocalDateTime());
                }

                String estadoStr = rs.getString("evento_estado");
                if (estadoStr != null) {
                    evento.setEstadoEvento(EstadoEvento.valueOf(estadoStr));
                }
                String tipoStr = rs.getString("evento_tipo");
                if (tipoStr != null) {
                    evento.setTipoEvento(TipoEventoP.valueOf(tipoStr));
                }

                Categoria categoria = new Categoria();
                categoria.setId(IdAdapter.LongAString(rs.getLong("id_categoria")));
                String nombreC = rs.getString("categoria_nombre");
                if(nombreC != null){
                    categoria.setNombre(CategoriaEvento.valueOf(nombreC));
                }
                categoria.setUrlImagen(rs.getString("categoria_urlImagen"));
                evento.setCategoriaEvento(categoria);

                Ubicacion ubicacion = new Ubicacion();
                ubicacion.setIdUbicacion(IdAdapter.LongAString(rs.getLong("id_ubicacion")));
                ubicacion.setNombre(rs.getString("ubicacion_nombre"));
                ubicacion.setCapacidad(rs.getInt("ubicacion_capacidad"));
                ubicacion.setSecciones(new ArrayList<>()); 

                String tipoUbiStr = rs.getString("ubicacion_tipo");
                if (tipoUbiStr != null) {
                    ubicacion.setTipo(TipoUbicacionP.valueOf(tipoUbiStr));
                }
                evento.setUbicacion(ubicacion);
            }

            long idSeccion = rs.getLong("id_seccion");

            if (!rs.wasNull()) {
                Seccion seccion = new Seccion();
                seccion.setIdSeccion(IdAdapter.LongAString(idSeccion));
                seccion.setNombre(rs.getString("seccion_nombre"));
                seccion.setCapacidad(rs.getInt("seccion_capacidad"));

                double precio = rs.getDouble("seccion_precio");
                seccion.setPrecioBase((long) precio);

                evento.getUbicacion().getSecciones().add(seccion);
            }
        }
        return evento;
    }
    
    private List<Evento> extraerEventos(ResultSet rs) throws SQLException {
        if (rs == null) {
            return new ArrayList<>();
        }

        Map<String, Evento> mapaEventos = new LinkedHashMap<>();

        while (rs.next()) {
            String idEvento = IdAdapter.LongAString(rs.getLong("id_evento"));

            if (!mapaEventos.containsKey(idEvento)) {
                Evento evento = new Evento();
                evento.setIdEvento(idEvento);
                evento.setNombreEvento(rs.getString("evento_nombre"));
                evento.setInformacionEvento(rs.getString("evento_informacion"));
                evento.setUrlImagen(rs.getString("evento_urlImagen"));
                evento.setGratuito(rs.getBoolean("evento_gratuito"));

                int disp = rs.getInt("evento_disponibilidad");
                if (!rs.wasNull()) {
                    evento.setDisponibilidad(disp);
                }

                Timestamp tsFecha = rs.getTimestamp("evento_fechaHora");
                if (tsFecha != null) {
                    evento.setFechaHora(tsFecha.toLocalDateTime());
                }

                String estadoStr = rs.getString("evento_estado");
                if (estadoStr != null) {
                    evento.setEstadoEvento(EstadoEvento.valueOf(estadoStr));
                }
                String tipoStr = rs.getString("evento_tipo");
                if (tipoStr != null) {
                    evento.setTipoEvento(TipoEventoP.valueOf(tipoStr));
                }

                Categoria categoria = new Categoria();
                categoria.setId(IdAdapter.LongAString(rs.getLong("id_categoria")));
                String nombreC = rs.getString("categoria_nombre");
                if (nombreC != null) {
                    categoria.setNombre(CategoriaEvento.valueOf(nombreC));
                }
                categoria.setUrlImagen(rs.getString("categoria_urlImagen"));
                evento.setCategoriaEvento(categoria);

                Ubicacion ubicacion = new Ubicacion();
                ubicacion.setIdUbicacion(IdAdapter.LongAString(rs.getLong("id_ubicacion")));
                ubicacion.setNombre(rs.getString("ubicacion_nombre"));
                ubicacion.setCapacidad(rs.getInt("ubicacion_capacidad"));
                ubicacion.setSecciones(new ArrayList<>());

                String tipoUbiStr = rs.getString("ubicacion_tipo");
                if (tipoUbiStr != null) {
                    ubicacion.setTipo(TipoUbicacionP.valueOf(tipoUbiStr));
                }
                evento.setUbicacion(ubicacion);

                mapaEventos.put(idEvento, evento);
            }

            Evento eventoActual = mapaEventos.get(idEvento);

            long idSeccion = rs.getLong("id_seccion");
            if (!rs.wasNull()) {
                Seccion seccion = new Seccion();
                seccion.setIdSeccion(IdAdapter.LongAString(idSeccion));
                seccion.setNombre(rs.getString("seccion_nombre"));
                seccion.setCapacidad(rs.getInt("seccion_capacidad"));

                double precio = rs.getDouble("seccion_precio");
                seccion.setPrecioBase((long) precio);

                eventoActual.getUbicacion().getSecciones().add(seccion);
            }
        }
        return new ArrayList<>(mapaEventos.values());
    }
    
}
