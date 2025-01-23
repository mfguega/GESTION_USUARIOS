package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pao_l
 */
public class UsuarioDAO {

    //Constantes para la conexión bd
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_usuario";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Método para obtener la conexión a la base de datos
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Método para registrar un usuario en la base de datos
    public void registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, apellido, username, password, id_rol) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getUsername());
            stmt.setString(4, usuario.getPassword());
            stmt.setInt(5, usuario.getIdRol());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para listar todos los usuarios almacenados en la base de datos
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("idRol")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // Método para actualizar la información de un usuario en la base de datos
    public void actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre = ?, apellido = ?, username = ?, password = ?, id_rol = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getUsername());
            stmt.setString(4, usuario.getPassword());
            stmt.setInt(5, usuario.getIdRol());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un usuario de la base de datos
    public void eliminarUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection conn = getConnection(); 
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
