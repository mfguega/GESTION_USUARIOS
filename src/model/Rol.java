package model;

import javafx.beans.property.*;

/**
 * Clase que mapea la entidad rol.
 *
 * @author mafeg
 */
public class Rol {

    private IntegerProperty idRol;
    private StringProperty nombreRol;

    public Rol(int idRol, String nombreRol) {
        this.idRol = new SimpleIntegerProperty(idRol);
        this.nombreRol = new SimpleStringProperty(nombreRol);
    }

    public int getIdRol() {
        return idRol.get();
    }

    public void setIdRol(int id) {
        this.idRol.set(id);
    }

    public IntegerProperty idRolProperty() {
        return idRol;
    }

    public String getNombreRol() {
        return nombreRol.get();
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol.set(nombreRol);
    }

    public StringProperty nombreProperty() {
        return nombreRol;
    }

}
