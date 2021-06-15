/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Usuario
 */
public class Telefono {

    private int idTelefono;
    private String numTelf;

    public Telefono() {
    }

    public Telefono(int idTelefono, String numTelf) {
        this.idTelefono = idTelefono;
        this.numTelf = numTelf;
    }

    public Telefono(String numTelf) {
        this.numTelf = numTelf;
    }

    public int getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(int idTelefono) {
        this.idTelefono = idTelefono;
    }

    public String getNumTelf() {
        return numTelf;
    }

    public void setNumTelf(String numTelf) {
        this.numTelf = numTelf;
    }

    @Override
    public String toString() {
        return "Telefono " + idTelefono + ", " + numTelf;
    }
 
}
