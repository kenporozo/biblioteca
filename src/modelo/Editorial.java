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
public class Editorial {

    private int idEditorial;
    private String nombreEditorial;

    public Editorial() {

    }

    public Editorial(int idEditorial, String nombreEditorial) {
        this.idEditorial = idEditorial;
        this.nombreEditorial = nombreEditorial;
    }

    public Editorial(String nombreEditorial) {
        this.nombreEditorial = nombreEditorial;
    }

    public String getNombreEditorial() {
        return nombreEditorial;
    }

    public void setNombreEditorial(String nombreEditorial) {
        this.nombreEditorial = nombreEditorial;
    }

    public int getIdEditorial() {
        return idEditorial;
    }

    public void setIdEditorial(int idEditorial) {
        this.idEditorial = idEditorial;
    }

    @Override
    public String toString() {
        return nombreEditorial;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.idEditorial;
        hash = 71 * hash + Objects.hashCode(this.nombreEditorial);
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
        final Editorial other = (Editorial) obj;
        if (this.idEditorial != other.idEditorial) {
            return false;
        }
        if (!Objects.equals(this.nombreEditorial, other.nombreEditorial)) {
            return false;
        }
        return true;
    }

}
