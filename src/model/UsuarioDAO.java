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
        String sql = "SELECT * FROM usuario INNER JOIN rol ON usuario.id_rol = rol.id_rol";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("id_rol"),
                        rs.getString("nombre_rol")
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
            stmt.setInt(6, usuario.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un usuario de la base de datos
    public void eliminarUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para validar las credenciales del usuario.
     *
     * @param username Nombre de usuario.
     * @param password Contrasena del usuario.
     * @return Usuario
     */
    public Usuario autenticarUsuario(String username, String password) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuario INNER JOIN rol ON usuario.id_rol = rol.id_rol WHERE BINARY username = ? AND BINARY password = ? LIMIT 1";
        
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    usuario = new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getInt("id_rol"),
                            rs.getString("nombre_rol")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return usuario;
    }
    
    public Usuario validarUsername(String username) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuario INNER JOIN rol ON usuario.id_rol = rol.id_rol WHERE BINARY username = ?";
        
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    usuario = new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getInt("id_rol"),
                            rs.getString("nombre_rol")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return usuario;
    }
}
