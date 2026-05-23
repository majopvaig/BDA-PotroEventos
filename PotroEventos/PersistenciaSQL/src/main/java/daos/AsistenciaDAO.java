/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import Entitys.Asistencia;
import Entitys.ReporteAsistencia;
import adapters.IdAdapter;
import conexion.ConexionBD;
import excepciones.PersistenciaException;
import interfaces.IAsistenciaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maria
 */
public class AsistenciaDAO implements IAsistenciaDAO {

    private static AsistenciaDAO instance;

    private static final Logger LOG = Logger.getLogger(AsistenciaDAO.class.getName());

    public AsistenciaDAO() {
    }

    public static AsistenciaDAO getInstancia() {
        if (instance == null) {
            instance = new AsistenciaDAO();
        }
        return instance;
    }

    @Override
    public Asistencia registrarAsistencia(String idBoleto, Asistencia asistencia) throws PersistenciaException {
        if (idBoleto == null || asistencia == null) {
            throw new PersistenciaException("No puede haber IDs nulos");
        }
        String sql = """
                        insert into asistencias(fechaHora, id_empleado) values (?, ?)
                     """;
        Long idBoletoLong = IdAdapter.stringALong(idBoleto);
        try(Connection cone = ConexionBD.crearConexion(); PreparedStatement ps = cone.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setObject(1, asistencia.getFechaHoraRegistro());
            ps.setLong(2, IdAdapter.stringALong(asistencia.getEmpleado().getIdEmpleado()));
            int filasAfectadas = ps.executeUpdate();
            if(filasAfectadas == 0){
                LOG.log(Level.WARNING, "No se pudo agregar la asistencia.");
                throw new PersistenciaException("No se pudo agregar la asistencia.");
            }
            Long idAsistencia = null;
            try(ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()){
                    idAsistencia = rs.getLong(1);
                    asistencia.setIdAsistencia(IdAdapter.LongAString(idAsistencia));
                } else {
                    throw new PersistenciaException("Error al obtener el ID generado para la asistencia.");
                }
             if(asociarAsistencia(idBoletoLong, idAsistencia)){
                 return asistencia;
             }
             throw new PersistenciaException("La operación falló.");
            }
        } catch (SQLException | PersistenciaException ex) {
            LOG.log(Level.SEVERE, "Error de SQL al registrar la asistencia.", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }
    
    private boolean asociarAsistencia(Long idBoleto, Long idAsistencia) throws PersistenciaException {
        if(idBoleto == null || idAsistencia == null){
            throw new PersistenciaException("Los valores no pueden ser nulos.");
        }
        String comando = """
                           update boletos set id_asistencia = ? where id = ?
                           """;
        try(Connection cone = ConexionBD.crearConexion(); PreparedStatement ps = cone.prepareStatement(comando)){
            ps.setLong(1, idAsistencia);
            ps.setLong(2, idBoleto);
            int filasAfectadas = ps.executeUpdate();
            if(filasAfectadas == 0){
                LOG.log(Level.WARNING, "No se pudo agregar la asistencia al boleto.");
                throw new PersistenciaException("No se agregó la asistencia al boleto.");
            }
            return true;
        } catch(SQLException ex){
            throw new PersistenciaException(ex.getMessage());
        }
    }

    @Override
    public ReporteAsistencia obtenerReporteAsistencia(String idEvento) {
        if (idEvento == null) {
            throw new PersistenciaException("No puede haber IDs nulos");
        }

        String sql = """
                     select 
                        count(b.estado = 'USADO') as asistidos,
                        count(b.estado = 'ACTIVO') as faltantes
                     from boletos b
                     where b.id_evento = ?
                     """;
        Long idL = IdAdapter.stringALong(idEvento);
        try (Connection con = ConexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, idL);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    long asistidos = rs.getLong("asistidos");
                    long pendientes = rs.getLong("faltantes");

                    return new ReporteAsistencia(asistidos, pendientes);
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Error de SQL al obtener el reporte de las asistencias.", ex);
            throw new PersistenciaException(ex.getMessage());
        }
        return new ReporteAsistencia(0, 0);
    }

}
