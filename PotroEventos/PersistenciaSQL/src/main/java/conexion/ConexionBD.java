/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import excepciones.PersistenciaException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author maria
 */
public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/potroeventosbd ";
    private static final String USUARIO = "potroeventosbd";
    private static final String CONTRASENA = "potroeventosbd";

    private ConexionBD() {

    }

    public static Connection crearConexion() {
        try {
            return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (SQLException e) {
            throw new PersistenciaException("error al establecer conexión.");
        }
    }
}
