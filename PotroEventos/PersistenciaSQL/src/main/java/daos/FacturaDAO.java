package daos;

import Entitys.Factura;
import adapters.IdAdapter;
import conexion.ConexionBD;
import excepciones.PersistenciaException;
import interfaces.IFacturaDAO;

import java.sql.*;

public class FacturaDAO implements IFacturaDAO {

    private static FacturaDAO instance;

    private FacturaDAO() {

    }

    public static FacturaDAO getInstance(){
        if(instance == null){
            instance = new FacturaDAO();
        }
        return instance;
    }

    @Override
    public String guardarFactura(Factura factura) throws PersistenciaException {
        //  --- ya m dio hueva comentar ---

        String sql =
                """
                
                        insert into facturas(
                
                nombre_completo, rfc, regimen_fiscal, codigo_postal,
                uso_cfdi, correo, totalPagado, moneda, fecha_compra,
                fecha_hora_timbrado, xmlTimbrado, uuid, id_reservacion
                
                )
                values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                 """;
        try(Connection con = ConexionBD.
             crearConexion();
            PreparedStatement ps = con.prepareStatement(sql)){
            //  --- setear valores ---
            ps.setString(1, factura.getPerfil().getNombre());
            ps.setString(2, factura.getPerfil().getRfc());
            ps.setString(3, factura.getPerfil().getRegimenFiscal().toString());
            ps.setString(4, factura.getPerfil().getCodigoPostal());
            ps.setString(5, factura.getUsoCfdi().toString()) ;
            ps.setString(6, factura.getPerfil().getCorreo());
            ps.setDouble(7, factura.getTotal());
            ps.setString(8, factura.getMoneda() );
            ps.setDate(9,  Date.valueOf(factura.getFechaCompra().toLocalDate()));
            ps.setTimestamp(10, Timestamp.valueOf(factura.getFechaTimbrado()));
            ps.setString(11, factura.getXmlTimbrado() );
            ps.setString(12, factura.getUuid());
            ps.setLong(13, IdAdapter.stringALong(factura.getIdReserva()));

            int filasAfectadas = ps.executeUpdate();
            
            if(filasAfectadas <= 0){
                throw new PersistenciaException("No se pudo insertar la factura, ninguna fila fue afectada.");
            }
            
            return factura.getUuid();
        }catch(SQLException e){
            throw new PersistenciaException("Error al guardar factura.");
        }
    }
}