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
public class Usuario {

    private int idUsuario;
    private Trabajador trabajador;
    private String usuario;
    private String clave;
    private int estado;

    public Usuario() {
    }

    public Usuario(Trabajador trabajador, String usuario, String clave) {
        this.trabajador = trabajador;
        this.usuario = usuario;
        this.clave = clave;
        this.estado = 1;
    }

    public Usuario(int idUsuario, String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
        this.idUsuario = idUsuario;
    }

    public Usuario(int idUsuario, Trabajador trabajador, String usuario, String clave, int estado) {
        this.idUsuario = idUsuario;
        this.trabajador = trabajador;
        this.usuario = usuario;
        this.clave = clave;
        this.estado = estado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", trabajador=" + trabajador + ", usuario=" + usuario + ", clave=" + clave + ", estado=" + estado + '}';
    }

}
