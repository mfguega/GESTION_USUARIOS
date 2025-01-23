package model;

import javafx.beans.property.*;

/**
 * Clase que mapea la entidad usuario.
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

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public String getApellido() {
        return apellido.get();
    }

    public void setApellido(String apellido) {
        this.apellido.set(apellido);
    }

    public StringProperty apellidoProperty() {
        return apellido;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public int getIdRol() {
        return idRol.get();
    }

    public void setIdRol(int idRol) {
        this.idRol.set(idRol);
    }

    public IntegerProperty idRolProperty() {
        return idRol;
    }

}
