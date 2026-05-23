/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

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
                     
                     """;
        Long idLong = IdAdapter.stringALong(idBoleto);
        try (Connection con = ConexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, idLong);

        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Error de SQL al registrar la asistencia.", ex);
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
