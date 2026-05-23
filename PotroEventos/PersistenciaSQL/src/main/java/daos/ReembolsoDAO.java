package daos;

import Entitys.Reembolso;
import adapters.IdAdapter;
import conexion.ConexionBD;
import excepciones.PersistenciaException;
import interfaces.IReembolsoDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReembolsoDAO implements IReembolsoDAO {

    //  --- instancia ---
    private static ReembolsoDAO instance;
    private ReembolsoDAO() {
    }
    //  --- singleton ---
    public static ReembolsoDAO getInstance(){
        if(instance == null){
            instance = new ReembolsoDAO();
        }
        return instance;
    }


    @Override
    public Reembolso agregarReembolso(Reembolso reembolso, String idDevolucion) throws PersistenciaException {

        String sql =
                """
                insert into reembolsos
                (id_operacion, fechaOperacion, importe, metodoPago)
                values(?, ?, ?, ?)
                """;
        //  --- conexion ---
        try(Connection con = ConexionBD.crearConexion();
            PreparedStatement ps = con.prepareStatement(sql)){

            //  --- setear valores ---
            Long idOperacion = IdAdapter.stringALong(reembolso.getIdOperacion());
            ps.setLong(1, idOperacion);
            ps.setDate(2, Date.valueOf(reembolso.getFechaOperacion().toLocalDate()));
            ps.setDouble(3, reembolso.getImporte());
            ps.setString(4, reembolso.getMetodoPago());

            //  --- ejecutar ---
            if(ps.executeUpdate() <= 0){
                return null;
            }
            return reembolso;
        }catch (SQLException ex){
            throw new PersistenciaException("Error de conexion.");
        }
    }

}
