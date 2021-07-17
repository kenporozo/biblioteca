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
public class Trabajador {

    private int idTrabajador;
    private int idBiblioteca;
    private static final int ID_BIBLIOTECA = 1;
    private String rut;
    private String nombre;
    private String apellido;
    private String fechaLaboral;
    private Telefono telefono;
    private Direccion direccion;
    private Correo correo;
    private int estado;

    public Trabajador() {
    }

    public Trabajador(int idTrabajador, String rut, String nombre, String apellido, Telefono telefono) {
        this.idTrabajador = idTrabajador;
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    public Trabajador(String rut, String nombre, String apellido, Telefono telefono, Direccion direccion, Correo correo) {
        this.idBiblioteca = Trabajador.ID_BIBLIOTECA;
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
    }

    public Trabajador(int idTrabajador, String rut, String nombre, String apellido) {
        this.idTrabajador = idTrabajador;
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Trabajador(int idTrabajador, String rut, String nombre, String apellido, String fechaLaboral, int estado) {
        this.idTrabajador = idTrabajador;
        this.idBiblioteca = Trabajador.ID_BIBLIOTECA;
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaLaboral = fechaLaboral;
        this.estado = estado;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaLaboral() {
        return fechaLaboral;
    }

    public void setFechaLaboral(String fechaLaboral) {
        this.fechaLaboral = fechaLaboral;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getIdBiblioteca() {
        return idBiblioteca;
    }

    public Telefono getTelefono() {
        return telefono;
    }

    public void setTelefono(Telefono telefono) {
        this.telefono = telefono;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Correo getCorreo() {
        return correo;
    }

    public void setCorreo(Correo correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return  idTrabajador + " " + nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.idTrabajador;
        hash = 97 * hash + Objects.hashCode(this.rut);
        hash = 97 * hash + Objects.hashCode(this.nombre);
        hash = 97 * hash + Objects.hashCode(this.apellido);
        hash = 97 * hash + Objects.hashCode(this.fechaLaboral);
        hash = 97 * hash + this.estado;
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
        final Trabajador other = (Trabajador) obj;
        if (this.idTrabajador != other.idTrabajador) {
            return false;
        }
        if (this.estado != other.estado) {
            return false;
        }
        if (!Objects.equals(this.rut, other.rut)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.apellido, other.apellido)) {
            return false;
        }
        if (!Objects.equals(this.fechaLaboral, other.fechaLaboral)) {
            return false;
        }
        return true;
    }

}
