package model;

import javafx.beans.property.*;

/**
 * Clase que mapea la entidad rol.
 *
 * @author mafeg
 */
public class Rol {

    private IntegerProperty id;
    private StringProperty nombre;

    public Rol(int id, String nombre) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
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

}
