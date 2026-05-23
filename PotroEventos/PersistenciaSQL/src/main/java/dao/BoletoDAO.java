/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Entitys.Asiento;
import Entitys.AsientoEvento;
import Entitys.Asistencia;
import Entitys.Boleto;
import Entitys.ENUMS.EstadoAsiento;
import Entitys.ENUMS.EstadoBoleto;
import Entitys.Empleado;
import Entitys.Evento;
import adapters.IdAdapter;
import conexion.ConexionBD;
import excepciones.PersistenciaException;
import interfaces.IBoletoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Dayanara Peralta G
 */
public class BoletoDAO implements IBoletoDAO {

    private Boleto mapearBoleto(ResultSet rs) throws SQLException {
        Boleto boleto = new Boleto();

        boleto.setIdBoleto(IdAdapter.LongAString(rs.getLong("boleto_id")));
        boleto.setCodigoQR(rs.getString("codigoQR"));
        boleto.setPrecio(rs.getDouble("boleto_precio"));
        boleto.setToken(rs.getString("token"));

        String est = rs.getString("estado");
        if (est != null) {
            boleto.setEstadoBoleto(EstadoBoleto.valueOf(est.toUpperCase()));
        }

        // Mapeo de Evento
        Evento evento = new Evento();
        evento.setIdEvento(IdAdapter.LongAString(rs.getLong("id_evento")));
        evento.setNombreEvento(rs.getString("nombreEvento")); 
        boleto.setEvento(evento);

        // Mapeo de AsientoEvento
        AsientoEvento ae = new AsientoEvento();
        ae.setIdAsientoEvento(IdAdapter.LongAString(rs.getLong("id_asientoAsiento")));
        ae.setPrecio(rs.getDouble("asientoEvento_precio"));
        String estEA = rs.getString("estadoAsiento");
        if (estEA != null) {
            ae.setEstadoAsiento(EstadoAsiento.valueOf(estEA.toUpperCase()));
        }

        // Mapeo de Asiento interno
        Asiento asiento = new Asiento();
        asiento.setIdAsiento(IdAdapter.LongAString(rs.getLong("asiento_id")));
        asiento.setNumero(rs.getInt("asiento_numero"));
        asiento.setFila(rs.getString("asiento_fila"));

        ae.setAsiento(asiento);
        ae.setEvento(evento);
        boleto.setAsiento(ae);

        // Mapeo de Asistencia
        long asistenciaId = rs.getLong("id_asistencia");
        if (!rs.wasNull()) { 
            Asistencia asis = new Asistencia();
            asis.setIdAsistencia(IdAdapter.LongAString(asistenciaId));
            if (rs.getTimestamp("fechaHoraRegistro") != null) {
                asis.setFechaHoraRegistro(rs.getTimestamp("fechaHoraRegistro").toLocalDateTime());
            }

            // Mapeo de Empleado interno
            long empleadoId = rs.getLong("empleado_id");
            if (!rs.wasNull()) {
                Empleado emp = new Empleado();
                emp.setIdEmpleado(IdAdapter.LongAString(empleadoId));
                emp.setCorreo(rs.getString("correo"));
                emp.setContrasenia(rs.getString("contrasenia"));
                asis.setEmpleado(emp);
            }
            boleto.setAsistencia(asis);
        }

        return boleto;
    }

    @Override
    public Boleto obtenerBoleto(String idBoleto) throws PersistenciaException {
        if (idBoleto == null) {
            throw new PersistenciaException("El id no existe.");
        }

        Long id = IdAdapter.stringALong(idBoleto);

        String sql = """
                     select b.id as boleto_id,
                            b.codigoQR,
                            b.precio as boleto_precio,
                            b.estado,
                            b.token,
                            e.nombreEvenro,
                            e.id as id_evento,
                            e.fecha as evento_fecha,
                            ae.id as id_asientoAsiento,
                            ae.precio as asientoEvento_precio,
                            ae.estadoAsiento,
                            a.id as asiento_id,
                            a.numero as asiento_numero,
                            a.fila as asiento_fila, 
                            asis.id as id_asistencia,
                            asis.fechaHoraRegistro,
                            emp.id as empleado_id,
                            emp.correo,
                            emp.contrasenia
                     from boletos b
                     join eventos e on b.id_evento = e.id
                     join asientosEvento ae on b.id_asientoAsiento = ae.id
                     join asiento a on ae.asiento_id = a.id
                     left join asistencia asis on b.id_asistencia = asis.id
                     left join empleado emp on asis.empleado_id = emp.id
                     where b.id = ?
                     """;

        try (Connection conex = ConexionBD.crearConexion(); PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Boleto boleto = new Boleto();

                    Long idB = rs.getLong("boleto_id");

                    //idBoleto
                    boleto.setIdBoleto(IdAdapter.LongAString(idB));
                    //codigoQR
                    boleto.setCodigoQR(rs.getString("codigoQR"));
                    //precio
                    boleto.setPrecio(rs.getDouble("boleto_precio"));
                    //token
                    boleto.setToken(rs.getString("token"));
                    //estadoBoleto
                    String est = rs.getString("estado");
                    if (est != null) {
                        boleto.setEstadoBoleto(EstadoBoleto.valueOf(est.toUpperCase()));
                    }
                    //evento
                    Evento evento = new Evento();
                    evento.setIdEvento(IdAdapter.LongAString(rs.getLong("id_evento")));
                    evento.setNombreEvento(rs.getString("nombreEvento"));
                    boleto.setEvento(evento);
                    //AsientoEvento
                    AsientoEvento ae = new AsientoEvento();
                    ae.setIdAsientoEvento(IdAdapter.LongAString(rs.getLong("id_asientoAsiento")));
                    ae.setPrecio(rs.getDouble("asientoEvento_precio"));
                    String estEA = rs.getString("ae_estado");
                    if (estEA != null) {
                        ae.setEstadoAsiento(EstadoAsiento.valueOf(estEA.toUpperCase()));
                    }
                    //asiento
                    Asiento asiento = new Asiento();
                    asiento.setIdAsiento(IdAdapter.LongAString(rs.getLong("asiento_id")));
                    asiento.setNumero(rs.getInt("asiento_numero"));
                    asiento.setFila(rs.getString("asiento_fila"));
                    ae.setAsiento(asiento);
                    ae.setEvento(evento);
                    boleto.setAsiento(ae);

                    Asistencia asis = new Asistencia();
                    asis.setIdAsistencia(IdAdapter.LongAString(rs.getLong("id_asistencia")));
                    if (rs.getTimestamp("fechaHoraRegistro") != null) {
                        asis.setFechaHoraRegistro(rs.getTimestamp("fechaHoraRegistro").toLocalDateTime());
                    }

                    Empleado emp = new Empleado();
                    emp.setIdEmpleado(IdAdapter.LongAString(rs.getLong("empleado_id")));
                    emp.setCorreo(rs.getString("correo"));
                    emp.setContrasenia(rs.getString("contrasenia"));

                    asis.setEmpleado(emp);
                    boleto.setAsistencia(asis);
                    return boleto;
                }

            }
        } catch (SQLException ex) {
            System.getLogger(BoletoDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new PersistenciaException("Error al obtener el boleto con ID: " + idBoleto, ex);
        }
        return null;
    }

    @Override
    public boolean cancelarBoleto(String idBoleto) throws PersistenciaException {
        String sql = """
                     update boletos 
                     set estado = 'CANCELADO' 
                     where id = ?;
                     """;
        try (Connection conex = ConexionBD.crearConexion(); PreparedStatement ps = conex.prepareStatement(sql)) {

            Long idBD = IdAdapter.stringALong(idBoleto);
            ps.setLong(1, idBD);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException ex) {
            System.getLogger(AsientoEventoDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new PersistenciaException("Error al cancalar el boleto");
        }
    }

    @Override
    public Boleto agregarBoleto(Boleto boleto) throws PersistenciaException {
        String sql = """
                     insert into boletos (
                         codigoQR, 
                         precio, 
                         estado, 
                         id_evento, 
                         id_asientoAsiento, 
                         token, 
                         id_asistencia
                     ) values (?, ?, ?, ?, ?, ?, ?)
                     """;
        try (Connection conex = ConexionBD.crearConexion(); PreparedStatement ps = conex.prepareStatement(sql)) {

            ps.setString(1, boleto.getCodigoQR());
            ps.setDouble(2, boleto.getPrecio());
            ps.setString(3, boleto.getEstadoBoleto().toString());
            ps.setLong(4, IdAdapter.stringALong(boleto.getEvento().getIdEvento()));
            ps.setLong(5, IdAdapter.stringALong(boleto.getAsiento().getIdAsientoEvento()));
            ps.setString(6, boleto.getToken());
            if (boleto.getAsistencia() != null && boleto.getAsistencia().getIdAsistencia() != null) {
                ps.setLong(7, IdAdapter.stringALong(boleto.getAsistencia().getIdAsistencia()));
            } else {
                ps.setNull(7, java.sql.Types.BIGINT);
            }

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        Long id = rs.getLong(1);
                        boleto.setIdBoleto(IdAdapter.LongAString(id));
                        return boleto;
                    }
                }
            }
            throw new PersistenciaException("No se pudo insertar el boleto");
        } catch (SQLException ex) {
            System.getLogger(AsientoEventoDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new PersistenciaException("Error al agregar el boleto");
        }
    }

    @Override
    public Boleto buscarPorToken(String token) throws PersistenciaException {
        String sql = """
                     SELECT 
                         b.id as boleto_id,
                         b.codigoQR,
                         b.precio as boleto_precio,
                         b.estado,
                         b.token,
                         e.id as id_evento,
                         e.nombreEvento,
                         e.fecha as evento_fecha,
                         e.descripcion as evento_descripcion,
                         ae.id as id_asientoAsiento,
                         ae.precio as asientoEvento_precio,
                         ae.estadoAsiento,
                         a.id as asiento_id,
                         a.numero as asiento_numero,
                         a.fila as asiento_fila,
                         asis.id as id_asistencia,
                         asis.fechaHoraRegistro,
                         emp.id as empleado_id,
                         emp.correo,
                         emp.contrasenia
                     FROM boletos b
                     JOIN eventos e ON b.id_evento = e.id
                     JOIN asientosEvento ae ON b.id_asientoAsiento = ae.id
                     JOIN asiento a ON ae.asiento_id = a.id
                     LEFT JOIN asistencia asis ON b.id_asistencia = asis.id
                     LEFT JOIN empleado emp ON asis.empleado_id = emp.id
                     WHERE b.token = ?;
                     """;

        try (Connection conex = ConexionBD.crearConexion(); PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setString(1, token);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearBoleto(rs);
                }
            }
        } catch (SQLException ex) {
            System.getLogger(dao.BoletoDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new PersistenciaException("Error al obtener el boleto con token: " + token);
        }
        return null;
    }

    @Override
    public boolean actualizarEstado(Boleto boleto) throws PersistenciaException {
        String sql = """
                     update boletos
                     set estado = ?
                     where id = ?
                     """;
        try (Connection conex = ConexionBD.crearConexion(); PreparedStatement ps = conex.prepareStatement(sql)) {
            String esta = boleto.getEstadoBoleto().toString();
            Long id = IdAdapter.stringALong(boleto.getIdBoleto());
            ps.setString(1, esta);
            ps.setLong(2, id);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException ex) {
            System.getLogger(dao.BoletoDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new PersistenciaException("Error al actualizar el estado del boleto");
        }
    }

    @Override
    public void actualizarAsiento(String idReservacion, String idAsientoNuevo) throws PersistenciaException {
        String sql = """
                     update boletos
                     set id_asientoAsiento = ?
                     where id = ?
                     """;
        try (Connection conex = ConexionBD.crearConexion(); PreparedStatement ps = conex.prepareStatement(sql)) {

            Long idRLong = IdAdapter.stringALong(idReservacion);
            Long idALong = IdAdapter.stringALong(idAsientoNuevo);

            ps.setLong(1, idALong);
            ps.setLong(2, idRLong);

            int filasActualizadas = ps.executeUpdate();
            if (filasActualizadas == 0) {
                throw new PersistenciaException("No se encontró la reservación con ID: " + idReservacion);
            }

        } catch (SQLException ex) {
            throw new PersistenciaException("Error al actualizar el asiento del boleto", ex);
        }
    }

}
