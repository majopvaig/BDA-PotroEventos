package daos;

import Entitys.PerfilFiscal;
import adapters.IdAdapter;
import conexion.ConexionBD;
import excepciones.PersistenciaException;
import interfaces.IPerfilFiscal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PerfilFiscalDAO implements IPerfilFiscal{

    private static PerfilFiscalDAO instance;

    private PerfilFiscalDAO() {
    }

    public static PerfilFiscalDAO getInstance(){
        //  --- ternario y explicacion pa mi yo futuro ---
        //  ? entonces
        //  : else
        // return instance = (instance == null) ? new PerfilFiscalDAO() : instance;
        if (instance == null) {
            instance = new PerfilFiscalDAO();
        }
        return instance;
    }


    @Override
    public boolean guardarPerfilFiscal(PerfilFiscal perfil, String idUsuario) throws PersistenciaException {
        //  --- crear sentencia ---
        String sql =
                """
                    insert into perfilesFiscales 
                    (id_usuario, nombre_completo, rfc, regimenFiscal, correo, codigo_postal) 
                    values(?,?,?,?,?, ?)
                """;
        //  --- conectar ---
        try(Connection con = ConexionBD.crearConexion();
            PreparedStatement ps = con.prepareStatement(sql)){
            //  --- validar entradas ---
            if (perfil == null){
                throw new PersistenciaException("perfil nulo");
            }
            Long idUser = IdAdapter.stringALong(idUsuario);
            if (idUser == null){
                throw new PersistenciaException("no se puede asociar a un usuario.");
            }
            //  --- hacer seteos ---
            ps.setLong(1,idUser);
            ps.setString(2,perfil.getNombre());
            ps.setString(3,perfil.getRfc());
            ps.setString(4,perfil.getRegimenFiscal().toString());
            ps.setString(5, perfil.getCorreo());
            ps.setString(6, perfil.getCodigoPostal());
            //  --- hacer insercion ---
            if(ps.executeUpdate() <=0){
                return false;
            }
            return true;
        }catch(SQLException e){
            throw new PersistenciaException("No pudo registrarse el perfil.");
        }
    }
}
