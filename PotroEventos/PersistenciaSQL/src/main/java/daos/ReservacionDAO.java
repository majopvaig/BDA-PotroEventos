/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import Entitys.Asiento;
import Entitys.AsientoEvento;
import Entitys.Boleto;
import Entitys.Categoria;
import Entitys.Devolucion;
import Entitys.ENUMS.CategoriaEvento;
import Entitys.ENUMS.EstadoAsiento;
import Entitys.ENUMS.EstadoBoleto;
import Entitys.ENUMS.EstadoEvento;
import Entitys.ENUMS.MotivoDevolucionP;
import Entitys.ENUMS.ReservacionEstado;
import Entitys.ENUMS.TipoDevolucionP;
import Entitys.ENUMS.TipoUbicacionP;
import Entitys.Evento;
import Entitys.Pago;
import Entitys.Reembolso;
import Entitys.Reservacion;
import Entitys.Seccion;
import Entitys.Ubicacion;
import Entitys.Usuario;
import adapters.IdAdapter;
import conexion.ConexionBD;
import excepciones.PersistenciaException;
import interfaces.IReservacionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maria
 */
public class ReservacionDAO implements IReservacionDAO {
    
    private static ReservacionDAO instance;
    
    private static final Logger LOG = Logger.getLogger(ReservacionDAO.class.getName());
    
    private ReservacionDAO(){}
    
    public static ReservacionDAO getInstance(){
        if(instance ==  null){
            instance = new ReservacionDAO();
        }
        return instance;
    }

    @Override
    public Reservacion consultarReservacion(String id) throws PersistenciaException {
        if (id == null) {
            throw new PersistenciaException("El ID no puede ser nulo.");
        }

        Long idLong = IdAdapter.stringALong(id);

        String comando = """
                         select 
                             r.id as id_reservacion,
                             r.estado as reservacion_estado,
                             r.total as reservacion_total,
                             r.fechahora as reservacion_fecha,
                             
                             u.id as id_usuario,
                             u.nombre as usuario_nombre,
                             u.apellidopaterno as usuario_apellido_paterno,
                             u.apellidomaterno as usuario_apellido_materno,
                             u.correo as usuario_correo,
                             u.creditos as usuario_creditos,
                             
                             b.id as id_boleto,
                             b.codigoqr as boleto_qr,
                             b.precio as boleto_precio,
                             b.estado as boleto_estado,
                             b.token as boleto_token,
                             
                             ae.id as id_asiento_evento,
                             ae.estado as asiento_evento_estado,
                             ae.precio as asiento_evento_precio,
                             
                             a.id as id_asiento_fisico,
                             a.fila as asiento_fila,
                             a.numero as asiento_numero,
                             
                             s.id as id_seccion,
                             s.nombre as seccion_nombre,
                             s.precio_base as seccion_precio_base,
                             
                             e.id as id_evento,
                             e.nombre as evento_nombre,
                             e.informacion as evento_info,
                             e.fecha_hora as evento_fecha,
                             e.gratuito as evento_es_gratuito,
                             e.tipo as evento_tipo,
                             e.disponibilidad as evento_disponibilidad,
                             e.estado as evento_estado,
                             
                             c.nombre as categoria_nombre,
                             c.id as id_categoria
                             
                             ub.nombre as ubicacion_nombre,
                             ub.tipo as ubicacion_tipo,
                             
                             p.id as id_pago,
                             p.idtransaccion as pago_id_transaccion,
                             p.fechaoperacion as pago_fecha,
                             p.importe as pago_importe,
                             p.metodopago as pago_metodo,
                             
                             d.id as id_devolucion,
                             d.motivo as devolucion_motivo,
                             d.comentarios as devolucion_comentarios,
                             d.tipo as devolucion_tipo_reembolso,
                             
                             rem.id as id_reembolso,
                             rem.idtransaccion as reembolso_id_transaccion,
                             rem.fechaoperacion as reembolso_fecha,
                             rem.importe as reembolso_importe,
                             rem.metodoreembolso as reembolso_metodo,
                             
                             f.id as id_factura
                         
                         from reservaciones r
                         inner join usuarios u on r.id_usuario = u.id
                         inner join boletos b on r.id_boleto = b.id
                         inner join asientosevento ae on b.id_asiento_evento = ae.id
                         inner join asientos a on ae.id_asiento = a.id
                         inner join secciones s on a.id_seccion = s.id
                         inner join eventos e on ae.id_evento = e.id
                         inner join categorias c on e.id_categoria = c.id
                         inner join ubicaciones ub on e.id_ubicacion = ub.id
                         
                         left join pagos p on p.id_reservacion = r.id
                         left join devoluciones d on d.id_reservacion = r.id
                         left join reembolsos rem on rem.id_devolucion = d.id
                         
                         where r.id = ?;
                         """;
        try (Connection con = ConexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(comando)) {
            ps.setLong(1, idLong);

            try(ResultSet rs = ps.executeQuery()) {
                if(!rs.next()){
                    LOG.log(Level.WARNING, "No se encontró una reservación con dicho ID: {0}", idLong);
                    throw new PersistenciaException("No hay registros con el ID proporcionado.");
                }
                return extraerReservacion(rs);
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al establecer conexión.");
        }
    }

    @Override
    public List<Reservacion> obtenerReservacionesUsuario(String idUsuario) throws PersistenciaException {
        if (idUsuario == null) {
            throw new PersistenciaException("El ID no puede ser nulo.");
        }

        Long idLong = IdAdapter.stringALong(idUsuario);

        String comando = """
                         select 
                             r.id as id_reservacion,
                             r.estado as reservacion_estado,
                             r.total as reservacion_total,
                             r.fechahora as reservacion_fecha,
                             
                             u.id as id_usuario,
                             u.nombre as usuario_nombre,
                             u.apellidopaterno as usuario_apellido_paterno,
                             u.apellidomaterno as usuario_apellido_materno,
                             u.correo as usuario_correo,
                             u.creditos as usuario_creditos,
                             
                             b.id as id_boleto,
                             b.codigoqr as boleto_qr,
                             b.precio as boleto_precio,
                             b.estado as boleto_estado,
                             b.token as boleto_token,
                             
                             ae.id as id_asiento_evento,
                             ae.estado as asiento_evento_estado,
                             ae.precio as asiento_evento_precio,
                             
                             a.id as id_asiento_fisico,
                             a.fila as asiento_fila,
                             a.numero as asiento_numero,
                             
                             s.id as id_seccion,
                             s.nombre as seccion_nombre,
                             s.precio_base as seccion_precio_base,
                             
                             e.id as id_evento,
                             e.nombre as evento_nombre,
                             e.informacion as evento_info,
                             e.fecha_hora as evento_fecha,
                             e.gratuito as evento_es_gratuito,
                             e.tipo as evento_tipo,
                             e.disponibilidad as evento_disponibilidad,
                             e.estado as evento_estado,
                             
                             c.nombre as categoria_nombre,
                             c.id as id_categoria
                             
                             ub.nombre as ubicacion_nombre,
                             ub.tipo as ubicacion_tipo,
                             
                             p.id as id_pago,
                             p.idtransaccion as pago_id_transaccion,
                             p.fechaoperacion as pago_fecha,
                             p.importe as pago_importe,
                             p.metodopago as pago_metodo,
                             
                             d.id as id_devolucion,
                             d.motivo as devolucion_motivo,
                             d.comentarios as devolucion_comentarios,
                             d.tipo as devolucion_tipo_reembolso,
                             
                             rem.id as id_reembolso,
                             rem.idtransaccion as reembolso_id_transaccion,
                             rem.fechaoperacion as reembolso_fecha,
                             rem.importe as reembolso_importe,
                             rem.metodoreembolso as reembolso_metodo,
                             
                             f.id as id_factura
                         
                         from reservaciones r
                         inner join usuarios u on r.id_usuario = u.id
                         inner join boletos b on r.id_boleto = b.id
                         inner join asientosevento ae on b.id_asiento_evento = ae.id
                         inner join asientos a on ae.id_asiento = a.id
                         inner join secciones s on a.id_seccion = s.id
                         inner join eventos e on ae.id_evento = e.id
                         inner join categorias c on e.id_categoria = c.id
                         inner join ubicaciones ub on e.id_ubicacion = ub.id
                         
                         left join pagos p on p.id_reservacion = r.id
                         left join devoluciones d on d.id_reservacion = r.id
                         left join reembolsos rem on rem.id_devolucion = d.id
                         
                         where id_usuario = ?
                         """;
        try (Connection con = ConexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(comando)) {
            ps.setLong(1, idLong);
            List<Reservacion> reservaciones = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    reservaciones.add(extraerReservacion(rs));
                }
                return reservaciones;
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error en la cosulta.");
        }
    }

    @Override
    public boolean guardarReservacion(Reservacion reservacion) throws PersistenciaException {
        String comandoSQL = """
                            insert into reservaciones (estado, total, fechaHora, id_boleto, id_usuario) values (?, ?, ?, ?, ?, ?)
                            """;

        try (Connection conn = ConexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comandoSQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, reservacion.getEstado().name());
            ps.setDouble(2, reservacion.getTotal());
            ps.setObject(3, reservacion.getFechaHora());
            ps.setLong(4, IdAdapter.stringALong(reservacion.getBoleto().getIdBoleto()));
            ps.setLong(5, IdAdapter.stringALong(reservacion.getUsuario().getIdUsuario()));

            int filasInsertadas = ps.executeUpdate();

            if (filasInsertadas == 0) {
                LOG.log(Level.WARNING, "No se pudo insertar la Reservación: {0}", reservacion);
                throw new PersistenciaException("No se pudo insertar la reservación.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    reservacion.setIdReservacion(IdAdapter.LongAString(rs.getLong(1)));
                } else {
                    throw new PersistenciaException("Error al obtener el ID generado de la nueva reservación.");
                }
            }

            LOG.log(Level.INFO, "Reservación insertada con éxito. ID: {0}", reservacion.getIdReservacion());
            return true;

        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Error de SQL al insertar la reservación", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }

    @Override
    public boolean cancelarReservacion(String idReservacion) throws PersistenciaException {
        String comando = """
                         update reservaciones set estado = 'CANCELADA' where id = ?
                         """;
        Long idLong = IdAdapter.stringALong(idReservacion);
        try(Connection con = ConexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(comando)){
            ps.setLong(1, idLong);
            
            int filasActualizadas = ps.executeUpdate();
            
            if(filasActualizadas == 0){
                LOG.log(Level.WARNING, "No se pudo cancelar la Reservación: {0}", idLong);
                throw new PersistenciaException("No se pudo insertar la reservación.");
            }
            return true;
        } catch(SQLException ex){
            LOG.log(Level.SEVERE, "Error de SQL al cancelar la reservación", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }

    @Override
    public boolean tieneFactura(String idReservacion) throws PersistenciaException {
        String comando = """
                         select id from facturas where id_reservacion = ?
                         """;
        Long idLong = IdAdapter.stringALong(idReservacion);
        try(Connection con = ConexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(comando)){
            ps.setLong(1, idLong);
            try(ResultSet rs = ps.executeQuery()){
                if(!rs.next()){
                    throw new PersistenciaException("No se encontaron datos de factura en la reservación.");
                }
                return true;
            }
        } catch(SQLException ex){
            LOG.log(Level.SEVERE, "Error de SQL al consultar factura de la reservación", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }

    // aquí no supe que show
    @Override
    public boolean asociarFactura(String idReservacion, String idFactura) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private Reservacion extraerReservacion(ResultSet rs) throws SQLException {

        Usuario u = new Usuario();
        u.setIdUsuario(IdAdapter.LongAString(rs.getLong("id_usuario")));
        u.setNombre(rs.getString("usuario_nombre"));
        u.setApellidoPaterno(rs.getString("usuario_apellido_paterno"));
        u.setApellidoMaterno(rs.getString("usuario_apellido_materno"));
        u.setCorreo(rs.getString("usuario_correo"));
        u.setCreditos(rs.getInt("usuario_creditos"));

        Categoria c = new Categoria();
        String nombreC = rs.getString("categoria_nombre");
        if(nombreC != null){
            c.setNombre(CategoriaEvento.valueOf(nombreC.toUpperCase()));
        }
        c.setId(IdAdapter.LongAString(rs.getLong("id_categoria")));

        Ubicacion ub = new Ubicacion();
        ub.setNombre(rs.getString("ubicacion_nombre"));
        String tipoU = rs.getString("ubicacion_tipo");
        if(tipoU != null){
            ub.setTipo(TipoUbicacionP.valueOf(tipoU.toUpperCase()));
        }

        Evento e = new Evento();
        e.setIdEvento(IdAdapter.LongAString(rs.getLong("id_evento")));
        e.setNombreEvento(rs.getString("evento_nombre"));
        e.setInformacionEvento(rs.getString("evento_info"));
        e.setGratuito(rs.getBoolean("evento_es_gratuito"));
        e.setDisponibilidad(rs.getInt("evento_disponibilidad"));
        String estadoE = rs.getString("evento_estado");
        if(estadoE != null){
            e.setEstadoEvento(EstadoEvento.valueOf(estadoE.toUpperCase()));
        }

        Timestamp tsEvento = rs.getTimestamp("evento_fecha");
        e.setFechaHora(tsEvento != null ? tsEvento.toLocalDateTime() : null);
        e.setCategoriaEvento(c);
        e.setUbicacion(ub);

        Seccion s = new Seccion();
        s.setIdSeccion(IdAdapter.LongAString(rs.getLong("id_seccion")));
        s.setNombre(rs.getString("seccion_nombre"));
        Double pb = rs.getDouble("seccion_precio_base");
        s.setPrecioBase(pb.longValue());

        Asiento a = new Asiento();
        a.setIdAsiento(IdAdapter.LongAString(rs.getLong("id_asiento_fisico")));
        a.setFila(rs.getString("asiento_fila"));
        a.setNumero(rs.getInt("asiento_numero"));
        a.setSeccion(s);

        AsientoEvento ae = new AsientoEvento();
        ae.setIdAsientoEvento(IdAdapter.LongAString(rs.getLong("id_asiento_evento")));
        ae.setPrecio(rs.getDouble("asiento_evento_precio"));
        String estadoAE = rs.getString("asiento_evento_estado");
        if(estadoAE != null){
            ae.setEstadoAsiento(EstadoAsiento.valueOf(estadoAE.toUpperCase()));
        }
        ae.setAsiento(a);
        ae.setEvento(e);

        Boleto b = new Boleto();
        b.setCodigoQR(rs.getString("boleto_qr"));
        b.setPrecio(rs.getDouble("boleto_precio"));
        b.setToken(rs.getString("boleto_token"));
        String estadoB = rs.getString("boleto_estado");
        if(estadoB != null){
            b.setEstadoBoleto(EstadoBoleto.valueOf(estadoB.toUpperCase()));
        }
        b.setAsiento(ae);

        Pago p = null;
        long idPago = rs.getLong("id_pago");
        if (!rs.wasNull()) {
            p = new Pago();
            p.setIdTransaccion(rs.getString("pago_id_transaccion"));
            p.setImporte(rs.getDouble("pago_importe"));
            p.setMetodoPago(rs.getString("pago_metodo"));

            Timestamp tsPago = rs.getTimestamp("pago_fecha");
            p.setFechaOperacion(tsPago != null ? tsPago.toLocalDateTime() : null);
        }

        Reembolso rem = null;
        long idReembolso = rs.getLong("id_reembolso");
        if (!rs.wasNull()) {
            rem = new Reembolso();
            rem.setIdOperacion(rs.getString("reembolso_id_transaccion"));
            rem.setImporte(rs.getDouble("reembolso_importe"));
            rem.setMetodoPago(rs.getString("reembolso_metodo"));

            Timestamp tsRem = rs.getTimestamp("reembolso_fecha");
            rem.setFechaOperacion(tsRem != null ? tsRem.toLocalDateTime() : null);
        }

        Devolucion d = null;
        long idDevolucion = rs.getLong("id_devolucion");
        if (!rs.wasNull()) {
            d = new Devolucion();
            String motivoD = rs.getString("devolucion_motivo");
            if(motivoD != null){
                d.setMotivo(MotivoDevolucionP.valueOf(motivoD.toUpperCase()));
            }
            d.setDescripcion(rs.getString("devolucion_comentarios"));
            String tipoD = rs.getString("devolucion_tipo_reembolso");
            if(tipoD != null){
                d.setTipo(TipoDevolucionP.valueOf(tipoD.toUpperCase()));
            }
            d.setReembolso(rem);
        }

        Reservacion r = new Reservacion();
        r.setIdReservacion(rs.getString("id_reservacion"));
        r.setTotal(rs.getDouble("reservacion_total"));

        Timestamp tsRes = rs.getTimestamp("reservacion_fecha");
        r.setFechaHora(tsRes != null ? tsRes.toLocalDateTime() : null);

        String estadoResStr = rs.getString("reservacion_estado");
        if (estadoResStr != null) {
            r.setEstado(ReservacionEstado.valueOf(estadoResStr));
        }

        r.setUsuario(u);
        r.setBoleto(b);
        r.setPago(p);
        r.setDevolucion(d); 
        r.setIdFactura(IdAdapter.LongAString(rs.getLong("id_factura")));
        return r;
    }

}
