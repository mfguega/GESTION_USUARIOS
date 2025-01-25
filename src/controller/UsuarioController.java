package controller;

import java.util.List;
import model.Usuario;
import model.UsuarioDAO;

/**
 *
 * @author pao_l
 */
public class UsuarioController {

    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public void registrarUsuario(String nombre, String apellido, String username, String password, int idRol, String nombreRol) {
        Usuario usuario = new Usuario(nombre, apellido, username, password, idRol, nombreRol);
        usuarioDAO.registrarUsuario(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listarUsuarios();
    }

    public void actualizarUsuario(String nombre, String apellido, String username, String password, int idRol, String nombreRol, int id) {
        Usuario usuario = new Usuario(id, nombre, apellido, username, password, idRol, nombreRol);
        usuarioDAO.actualizarUsuario(usuario);
    }

    public void eliminarUsuario(int id) {
        usuarioDAO.eliminarUsuario(id);
    }

    public Usuario autenticarUsuario(String username, String password) {
        return usuarioDAO.autenticarUsuario(username, password);
    }
    
    public Usuario validarUsername(String username) {
        return usuarioDAO.validarUsername(username);
    }

    public String cifrarPassword(String password) {
        return usuarioDAO.cifrarPassword(password);
    }

    public String descrifrarPassword(String password) {
        return usuarioDAO.descrifrarPassword(password);
    }
}
