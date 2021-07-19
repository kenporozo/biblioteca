/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Objects;

/**
 *
 * @author Usuario
 */
public class Autor {

    private int idAutor;
    private String nombreAutor;
    private String apellidoPaterno;

    public Autor() {
    }

    public Autor(int idAutor, String nombreAutor, String apellidoPaterno) {
        this.idAutor = idAutor;
        this.nombreAutor = nombreAutor;
        this.apellidoPaterno = apellidoPaterno;
    }

    public Autor(int idAutor, String nombreAutor) {
        this.idAutor = idAutor;
        this.nombreAutor = nombreAutor;
    }

    public Autor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public Autor(String nombreAutor, String apellidoPatero) {
        this.nombreAutor = nombreAutor;
        this.apellidoPaterno = apellidoPatero;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    @Override
    public String toString() {
        return nombreAutor + " " + apellidoPaterno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.idAutor;
        hash = 59 * hash + Objects.hashCode(this.nombreAutor);
        hash = 59 * hash + Objects.hashCode(this.apellidoPaterno);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Autor other = (Autor) obj;
        if (this.idAutor != other.idAutor) {
            return false;
        }
        if (!Objects.equals(this.nombreAutor, other.nombreAutor)) {
            return false;
        }
        if (!Objects.equals(this.apellidoPaterno, other.apellidoPaterno)) {
            return false;
        }
        return true;
    }

}
