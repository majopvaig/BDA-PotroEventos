package daos;

import Entitys.Pago;
import adapters.IdAdapter;
import conexion.ConexionBD;
import excepciones.PersistenciaException;
import interfaces.IPagoDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PagoDAO implements IPagoDAO {

    private static PagoDAO instance;

    private PagoDAO() {

    }
    public static PagoDAO getInstance(){
        if(instance == null){
            instance = new PagoDAO();
        }
        return instance;
    }

    @Override
    public Pago agregarPago(Pago pago, String idReservacion) throws PersistenciaException {
        if(pago == null){
            throw new PersistenciaException("No hay pago por registrar.");
        }
        if(idReservacion == null){
            throw new PersistenciaException("No hay puede asociarse con una reserva.");
        }
        //  --- sentencia ---
        String sql =
                """
                insert into pagos 
                (fecha_operacion, importe, metodoPago, idReservacion)
                values(?, ?, ?, ?)
                """;
        //  --- conexion ---
        try(Connection con = ConexionBD.crearConexion();
            PreparedStatement ps = con.prepareStatement(sql)){
            //  --- setear valores ---
            ps.setDate(1, Date.valueOf(pago.getFechaOperacion().toLocalDate()));
            ps.setDouble(2, pago.getImporte());
            ps.setString(3, pago.getMetodoPago());
            ps.setLong(4, IdAdapter.stringALong(idReservacion));
            //  --- ejecutar ---
            if(ps.executeUpdate() <= 0){
                return null;
            }
            return pago;
        }catch (SQLException e){
            throw new PersistenciaException("Error al establecer conexión.");
        }
    }

}
