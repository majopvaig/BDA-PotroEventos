/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Entitys.Asiento;
import Entitys.AsientoEvento;
import Entitys.ENUMS.EstadoAsiento;
import Entitys.Evento;
import adapters.IdAdapter;
import conexion.ConexionBD;
import excepciones.PersistenciaException;
import interfaces.IAsientoEventoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dayanara Peralta G
 */
public class AsientoEventoDAO implements IAsientoEventoDAO{
    private static AsientoEventoDAO instancia;
    private AsientoEventoDAO() {
    }

    public static AsientoEventoDAO getInstancia() {
        if (instancia == null) {
            instancia = new AsientoEventoDAO();
        }
        return instancia;
    }

    @Override
    public List<AsientoEvento> buscarPorEvento(String idEvento) throws PersistenciaException {
        List<AsientoEvento> listaAsientoEvento = new ArrayList<>();
        String sql = """
                     select ae.id AS ae_id, 
                            ae.precio AS ae_precio, 
                            ae.estado AS ae_estado,
                            a.id AS a_id, 
                            a.numero AS a_numero, 
                            a.fila AS a_fila, 
                            e.id AS e_id, 
                            e.nombre AS e_nombre
                     FROM asientosEvento ae 
                     INNER JOIN asientos a ON ae.id_asiento = a.id 
                     INNER JOIN eventos e ON ae.id_evento = e.id 
                     WHERE ae.id_evento = ?
                     """;
        
        try(Connection conex = ConexionBD.crearConexion();
                PreparedStatement ps = conex.prepareStatement(sql)){
            
            Long idBDEvent = IdAdapter.stringALong(idEvento);
            ps.setLong(1, idBDEvent);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AsientoEvento ae = new AsientoEvento();
                    ae.setIdAsientoEvento(IdAdapter.LongAString(rs.getLong("ae_id")));
                    ae.setPrecio(rs.getDouble("ae_precio"));
                    
                    String estadoStr = rs.getString("ae_estado");
                    if(estadoStr != null){
                        ae.setEstadoAsiento(EstadoAsiento.valueOf(estadoStr));
                    }
                    
                    Asiento asiento = new Asiento();
                    asiento.setIdAsiento(IdAdapter.LongAString(rs.getLong("a_id")));
                    
                    ae.setAsiento(asiento);
                    
                    Evento evento = new Evento();
                    evento.setIdEvento(IdAdapter.LongAString(rs.getLong("e_id")));
                    ae.setEvento(evento);
                    
                    listaAsientoEvento.add(ae);
                }
            }
        } catch (SQLException ex) {
            System.getLogger(AsientoEventoDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return listaAsientoEvento;
    }

    @Override
    public boolean reservarAsiento(String idAsiento) throws PersistenciaException {
        String sql = """
                     update asientosEvento
                     set estado¿ = 'RESERVADO'
                     where id = ?
                     """;
        
        try(Connection conex = ConexionBD.crearConexion();
                PreparedStatement ps = conex.prepareStatement(sql)){
            
            Long idBD = IdAdapter.stringALong(idAsiento);
            ps.setLong(1, idBD);
            
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException ex) {
            System.getLogger(AsientoEventoDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new PersistenciaException("Error al liberar el asiento");

        }
    }

    @Override
    public boolean liberarAsiento(String idAsiento) throws PersistenciaException {
        String sql = """
                     update asientosEvento
                     set estado = 'RESERVADO'
                     where id = ?
                     """;
        try (Connection conex = ConexionBD.crearConexion(); PreparedStatement ps = conex.prepareStatement(sql)) {

            Long idBD = IdAdapter.stringALong(idAsiento);
            ps.setLong(1, idBD);

            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException ex) {
            System.getLogger(AsientoEventoDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new PersistenciaException("Error al liberar el asiento");
        }
    }
    
    @Override
    public boolean ocuparAsiento(String idAsientoNuevo) throws PersistenciaException{
        String sql = """
                     update asientosEvento
                     set estado = 'VENDIDO'
                     where id = ?
                     """;
        try(Connection conex = ConexionBD.crearConexion();
                PreparedStatement ps = conex.prepareStatement(sql)){
            
            Long idBD = IdAdapter.stringALong(idAsientoNuevo);
            ps.setLong(1, idBD);
            int filas = ps.executeUpdate();
            return filas > 0;
            
        } catch (SQLException ex) {
            System.getLogger(AsientoEventoDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new PersistenciaException("Error al liberar el asiento");
        }
    }

    @Override
    public boolean venderAsiento(String idAsiento) throws PersistenciaException {
        String sql = """
                     update asientosEvento
                     set estado = 'vendido'
                     where id = ?
                     """;
        try(Connection conex = ConexionBD.crearConexion();
                PreparedStatement ps = conex.prepareStatement(sql)){
            
            Long idBD = IdAdapter.stringALong(idAsiento);
            ps.setLong(1, idBD);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException ex) {
            System.getLogger(AsientoEventoDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new PersistenciaException("Error al liberar el asiento");
        }
    }

    @Override
    public AsientoEvento consultarPorId(String idAsiento) throws PersistenciaException {
        String sql = """
                     select ae.id AS ae_id, 
                            ae.precio AS ae_precio, 
                            ae.estado AS ae_estado,
                            a.id AS a_id, 
                            a.numero AS a_numero, 
                            a.fila AS a_fila, 
                            e.id AS e_id, 
                            e.nombre AS e_nombre
                     FROM asientosEvento ae 
                     INNER JOIN asientos a ON ae.id_asiento = a.id 
                     INNER JOIN eventos e ON ae.id_evento = e.id 
                     WHERE ae.id_evento = ?
                     """;
        try(Connection conex = ConexionBD.crearConexion();
                PreparedStatement ps = conex.prepareStatement(sql)){
            
            Long idBD = IdAdapter.stringALong(idAsiento);
            ps.setLong(1, idBD);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AsientoEvento ae = new AsientoEvento();
                    ae.setIdAsientoEvento(IdAdapter.LongAString(rs.getLong("ae_id")));
                    ae.setPrecio(rs.getDouble("ae_precio"));
                    
                    String estadoStr = rs.getString("ae_estado");
                    if(estadoStr != null){
                        ae.setEstadoAsiento(EstadoAsiento.valueOf(estadoStr.toUpperCase()));
                    }
                    
                    Asiento asiento = new Asiento();
                    asiento.setIdAsiento(IdAdapter.LongAString(rs.getLong("a_id")));
                    asiento.setNumero(rs.getInt("a_numero"));
                    asiento.setFila(rs.getString("a_fila"));
                    ae.setAsiento(asiento);
                    
                    Evento evento = new Evento();
                    evento.setIdEvento(IdAdapter.LongAString(rs.getLong("e_id")));
                    evento.setNombreEvento(rs.getString("e_nombre"));
                    ae.setEvento(evento);
                    
                    return ae;
                }
            }
            
        } catch (SQLException ex) {
            System.getLogger(AsientoEventoDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return null;
    }
    
    @Override
    public List<AsientoEvento> obtenerAsientosConAsistencia(String idEvento) throws PersistenciaException{
        List<AsientoEvento> listaAsientoEvento = new ArrayList<>();
    String sql = """
                 SELECT ae.id AS ae_id, 
                        ae.precio AS ae_precio, 
                        ae.estado AS ae_estado,
                        a.id AS a_id, 
                        a.numero AS a_numero, 
                        a.fila AS a_fila, 
                        e.id AS e_id, 
                        e.nombre AS e_nombre
                 FROM asientosEvento ae 
                 INNER JOIN asientos a ON ae.id_asiento = a.id 
                 INNER JOIN eventos e ON ae.id_evento = e.id 
                 WHERE ae.id_evento = ? AND ae.estado = 'OCUPADO'
                 """;
    
    try (Connection conex = ConexionBD.crearConexion();
         PreparedStatement ps = conex.prepareStatement(sql)) {
        
        ps.setLong(1, IdAdapter.stringALong(idEvento));
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                AsientoEvento ae = new AsientoEvento();
                ae.setIdAsientoEvento(IdAdapter.LongAString(rs.getLong("ae_id")));
                ae.setPrecio(rs.getDouble("ae_precio"));
                
                String estadoStr = rs.getString("ae_estado");
                if (estadoStr != null) {
                    ae.setEstadoAsiento(EstadoAsiento.valueOf(estadoStr.toUpperCase()));
                }
                
                Asiento asiento = new Asiento();
                asiento.setIdAsiento(IdAdapter.LongAString(rs.getLong("a_id")));
                asiento.setNumero(rs.getInt("a_numero"));
                asiento.setFila(rs.getString("a_fila"));
                ae.setAsiento(asiento);
                
                Evento evento = new Evento();
                evento.setIdEvento(IdAdapter.LongAString(rs.getLong("e_id")));
                evento.setNombreEvento(rs.getString("e_nombre"));
                ae.setEvento(evento);
                
                listaAsientoEvento.add(ae);
            }
        }
    } catch (SQLException ex) {
        java.util.logging.Logger.getLogger(AsientoEventoDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        throw new PersistenciaException("Error al obtener asientos con asistencia", ex);
    }
    return listaAsientoEvento;
    }

}
