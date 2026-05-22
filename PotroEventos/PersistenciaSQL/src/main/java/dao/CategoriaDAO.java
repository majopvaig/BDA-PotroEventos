package dao;

import Entitys.Categoria;
import adapters.IdAdapter;
import conexion.ConexionBD;
import excepciones.PersistenciaException;
import interfaces.ICategoriaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CategoriaDAO implements ICategoriaDAO {

    private static CategoriaDAO instance;

    private CategoriaDAO() {
    }

    public static CategoriaDAO getInstance(){
        if(instance == null){
            instance = new CategoriaDAO();
        }
        return instance;
    }

    @Override
    public Categoria obtenerPorId(String id) throws PersistenciaException {
        if(id == null){
            throw new PersistenciaException("El id no existe.");
        }
        // transformar el tipo de dato
        Long idConvertido = IdAdapter.stringALong(id);

        String sql = "Select into categorias (id, url_imagen, nombre) values (?,?,?)";

        try(Connection con = ConexionBD.crearConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, idConvertido);

            return null; // provicional jeje
        } catch (SQLException e) {
            throw new PersistenciaException("Error al establecer conexión.");
        }
    }

    @Override
    public List<Categoria> consultarCategorias() throws PersistenciaException {
        return List.of();
    }
}
