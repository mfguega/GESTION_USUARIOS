package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la clase rol.
 *
 * @author mafeg
 */
public class RolDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/gestion_usuario";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Metodo para obtener la conexion a la bdd.
     *
     * @return Conecction
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Metodo para listar los roles.
     *
     * @return List<Rol>.
     */
    public List<Rol> listarRoles() {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT * FROM rol";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                roles.add(new Rol(
                        rs.getInt("id_rol"),
                        rs.getString("nombre_rol")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

}
