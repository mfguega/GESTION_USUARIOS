package model;

import javafx.beans.property.*;

/**
 *
 * @author mafeg
 */
public class Rol {

    private IntegerProperty id;
    private StringProperty nombre;

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

}
