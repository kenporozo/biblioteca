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
public class Correo {

    private int idCorreo;
    private String correo;

    public Correo(String correo) {
        this.correo = correo;
    }

    public Correo(int idCorreo, String correo) {
        this.idCorreo = idCorreo;
        this.correo = correo;
    }

    public int getIdCorreo() {
        return idCorreo;
    }

    public void setIdCorreo(int idCorreo) {
        this.idCorreo = idCorreo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Correo" + idCorreo + ", " + correo;
    }

}
