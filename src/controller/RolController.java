package controller;

import java.util.List;
import model.RolDAO;
import model.Rol;

/**
 * Clase controladora que actua como intermediario entre la vista y el modelo
 * Rol.
 *
 * @author mafeg
 */
public class RolController {

    private RolDAO rolDao;

    /**
     * Constructor que inicializa el DAO.
     */
    public RolController() {
        this.rolDao = new RolDAO();
    }

    /**
     * Metodo que obtiene el listado de roles.
     *
     * @return List<Rol>.
     */
    public List<Rol> listarRoles() {
        return rolDao.listarRoles();
    }

}
