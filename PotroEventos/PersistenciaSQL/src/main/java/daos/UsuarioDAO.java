/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import Entitys.Usuario;
import adapters.IdAdapter;
import conexion.ConexionBD;
import excepciones.PersistenciaException;
import interfaces.IUsuarioDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maria
 */
public class UsuarioDAO implements IUsuarioDAO {
    
    private static UsuarioDAO instance;
    
    private static final Logger LOG = Logger.getLogger(UsuarioDAO.class.getName());
    
    private UsuarioDAO(){}
    
    public static UsuarioDAO getInstance(){
        if(instance == null){
            instance = new UsuarioDAO();
        }
        return instance;
    }
    
    @Override
    public Usuario obtenerUsuario(Usuario usuario) throws PersistenciaException {
        if (usuario == null || usuario.getCorreo() == null) {
            throw new PersistenciaException("El correo del usuario es requerido.");
        }
        
        String sql = """
                     select id,
                            nombre,
                            apellidoPaterno,
                            apellidoMaterno,
                            correo,
                            creditos
                     from usuarios
                     where correo = ?
                     """;
        try (Connection con = ConexionBD.crearConexion(); 
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario.getCorreo());
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Usuario usu = new Usuario();
                    usu.setIdUsuario(IdAdapter.LongAString(rs.getLong("id_usuario")));
                    usu.setNombre(rs.getString("nombre"));
                    usu.setApellidoPaterno(rs.getString("apellidoPaterno"));
                    usu.setApellidoMaterno(rs.getString("apellidoMaterno"));
                    usu.setCorreo(rs.getString("correo"));
                    usu.setCreditos(rs.getInt("creditos"));
                    return usu;
                }
            }
        } catch (SQLException ex) {
            System.getLogger(UsuarioDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return null;
    }

    @Override
    public Usuario obtenerPorId(String idUsuario) throws PersistenciaException {
        String comando = """
                         select id, nombre, apellidoPaterno, apellidoMaterno, correo, creditos
                         from usuarios where id = ?
                         """;
        Long idLong = IdAdapter.stringALong(idUsuario);
        try (Connection con = ConexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(comando)) {
            ps.setLong(1, idLong);
            try(ResultSet rs = ps.executeQuery()){
                if(!rs.next()){
                    LOG.log(Level.WARNING, "No se encontró un usuario con dicho ID: {0}", idLong);
                    throw new PersistenciaException("No hay registros con el ID proporcionado.");
                }
                return extraerUsuario(rs);
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Error de SQL al consultar al usuario.", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) throws PersistenciaException {
        if(usuario == null){
            throw new PersistenciaException("Por favor proporcione el usuario que quiera agregar.");
        }
        String comando = """
                         insert into usuarios(nombre, apellidoPaterno, apellidoMaterno, correo, contrasenia, creditos) 
                         values (?, ?, ?, ?, ?, ?)
                         """;
        try(Connection con = ConexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(comando, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidoPaterno());
            if(usuario.getApellidoMaterno() == null || usuario.getApellidoMaterno().isEmpty() || usuario.getApellidoMaterno().isBlank()){
                ps.setNull(3, Types.VARCHAR);
            } else {
                ps.setString(3, usuario.getApellidoMaterno());
            }
            ps.setString(4, usuario.getCorreo());
            ps.setString(5, usuario.getContrasenia());
            if(usuario.getCreditos() == null || usuario.getCreditos() < 0){
                ps.setInt(6, 0);
            } else {
                ps.setInt(6, usuario.getCreditos());
            }     
            int filasInsertadas = ps.executeUpdate();
            if(filasInsertadas == 0){
                LOG.log(Level.WARNING, "No se pudo insertar el usuario {0}", usuario);
                throw new PersistenciaException("No se pudo insertar el usuario.");
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setIdUsuario(IdAdapter.LongAString(rs.getLong(1)));
                } else {
                    throw new PersistenciaException("Error al obtener el ID generado del nuevo usuario.");
                }
            }
            LOG.log(Level.INFO, "Usuario insertado con éxito. ID: {0}", usuario.getIdUsuario());
            return usuario;
        } catch(SQLException ex){
            LOG.log(Level.SEVERE, "Error de SQL al agregar al usuario.", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }

    @Override
    public Usuario obtenerPorCorreo(String correo) throws PersistenciaException {
        if(correo == null || correo.isEmpty() || correo.isBlank()){
            throw new PersistenciaException("Se requiere el correo para realizar la consulta.");
        }
        String comando = """
                         select id, nombre, apellidoPaterno, apellidoMaterno, correo, creditos
                         from usuarios where correo = ?
                         """;
        try(Connection con = ConexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(comando)){
            ps.setString(1, correo);
            try(ResultSet rs = ps.executeQuery()){
                if(!rs.next()){
                    LOG.log(Level.WARNING, "No se encontró un usuario con dicho corre");
                    throw new PersistenciaException("No hay registros con el correo proporcionado.");
                }
                return extraerUsuario(rs);
            }
        } catch(SQLException ex){
            LOG.log(Level.SEVERE, "Error de SQL al consultar al usuario.", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }

    @Override
    public boolean restarCreditos(String idUsuario, Integer cantidad) throws PersistenciaException {
        if(idUsuario == null || cantidad == null || cantidad < 1){
            throw new PersistenciaException("Falta de información para realizar la operación.");
        }
        String comando = """
                         update usuarios set creditos = creditos - ? where id = ?
                         """;
        Long idLong = IdAdapter.stringALong(idUsuario);
        try(Connection con = ConexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(comando)){
            ps.setInt(1, cantidad);
            ps.setLong(2, idLong);
            int filasAfectadas = ps.executeUpdate();
            if(filasAfectadas == 0){
                LOG.log(Level.WARNING, "No se pudo restar los créditos al usuario {0}", idUsuario);
                throw new PersistenciaException("No se pudo restar los créditos al usuario.");
            }
            return true;
        } catch(SQLException ex){
            LOG.log(Level.SEVERE, "Error de SQL restar los créditos al usuario.", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }

    @Override
    public boolean aumentarCreditos(String idUsuario, Integer cantidad) throws PersistenciaException {
        if(idUsuario == null || cantidad == null || cantidad < 1){
            throw new PersistenciaException("Falta de información para realizar la operación.");
        }
        String comando = """
                         update usuarios set creditos = creditos + ? where id = ?
                         """;
        Long idLong = IdAdapter.stringALong(idUsuario);
        try(Connection con = ConexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(comando)){
            ps.setInt(1, cantidad);
            ps.setLong(2, idLong);
            int filasAfectadas = ps.executeUpdate();
            if(filasAfectadas == 0){
                LOG.log(Level.WARNING, "No se pudo aumentar los créditos al usuario {0}", idUsuario);
                throw new PersistenciaException("No se pudo aumentar los créditos al usuario.");
            }
            return true;
        } catch(SQLException ex){
            LOG.log(Level.SEVERE, "Error de SQL aumentar los créditos al usuario.", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }
    
    private Usuario extraerUsuario(ResultSet rs) throws SQLException {
        if(rs == null){
            return null;
        }
        
        Usuario u = new Usuario();
        u.setIdUsuario(IdAdapter.LongAString(rs.getLong("id")));
        u.setNombre(rs.getString("nombre"));
        u.setApellidoPaterno(rs.getString("apellidoPaterno"));
        String apellidoM = rs.getString("apellidoMaterno");
        if(apellidoM != null){
            u.setApellidoMaterno(apellidoM);
        }
        u.setCorreo(rs.getString("correo"));
        u.setCreditos(rs.getInt("creditos"));
        return u;
    }
}
