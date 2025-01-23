package model;

import javafx.beans.property.*;

/**
 *
 * @author mafeg
 */
public class Usuario {

    private IntegerProperty id;
    private StringProperty nombre;
    private StringProperty apellido;
    private StringProperty username;
    private StringProperty password;
    private IntegerProperty idRol;

    public Usuario(int id, String nombre, String apellido, String username, String password, int idRol) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.idRol = new SimpleIntegerProperty(idRol);
    }

    public Usuario(String nombre, String apellido, String username, String password, int idRol) {
        this(0, nombre, apellido, username, password, idRol);
    }

    public IntegerProperty getId() {
        return id;
    }

    public void setId(IntegerProperty id) {
        this.id = id;
    }

    public StringProperty getNombre() {
        return nombre;
    }

    public void setNombre(StringProperty nombre) {
        this.nombre = nombre;
    }

    public StringProperty getApellido() {
        return apellido;
    }

    public void setApellido(StringProperty apellido) {
        this.apellido = apellido;
    }

    public StringProperty getUsername() {
        return username;
    }

    public void setUsername(StringProperty username) {
        this.username = username;
    }

    public StringProperty getPassword() {
        return password;
    }

    public void setPassword(StringProperty password) {
        this.password = password;
    }

    public IntegerProperty getIdRol() {
        return idRol;
    }

    public void setIdRol(IntegerProperty idRol) {
        this.idRol = idRol;
    }

}
