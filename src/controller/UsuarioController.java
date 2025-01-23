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

    public void registrarUsuario(String nombre, String apellido, String username, String password, int id_rol) {
        Usuario usuario = new Usuario(nombre, apellido, username, password, id_rol);
        usuarioDAO.registrarUsuario(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listarUsuarios();
    }

    public void actualizarUsuario(String nombre, String apellido, String username, String password, int id_rol) {
        Usuario usuario = new Usuario(nombre, apellido, username, password, id_rol);
        usuarioDAO.actualizarUsuario(usuario);
    }

    public void eliminarUsuario(int id) {
        usuarioDAO.eliminarUsuario(id);
    }
}
