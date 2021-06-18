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
public class Distribuidor {
    private int idDistribuidor;
    private String rutDistribuidor;
    private String fechaLaboral;
    private Telefono telefono;
    private Direccion direccion;
    private int estado;

    public Distribuidor() {
    }

    public Distribuidor(String rutDistribuidor, Telefono telefono, Direccion direccion,  String fechaLaboral, int estado) {
        this.rutDistribuidor = rutDistribuidor;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fechaLaboral = fechaLaboral;
        this.estado = estado;
    }

    public Distribuidor(int idDistribuidor, String rutDistribuidor,  Telefono telefono, Direccion direccion, String fechaLaboral, int estado) {
        this.idDistribuidor = idDistribuidor;
        this.rutDistribuidor = rutDistribuidor;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fechaLaboral = fechaLaboral;
        this.estado = estado;
    }

    public int getIdDistribuidor() {
        return idDistribuidor;
    }

    public void setIdDistribuidor(int idDistribuidor) {
        this.idDistribuidor = idDistribuidor;
    }

    public String getRutDistribuidor() {
        return rutDistribuidor;
    }

    public void setRutDistribuidor(String rutDistribuidor) {
        this.rutDistribuidor = rutDistribuidor;
    }

    public String getFechaLaboral() {
        return fechaLaboral;
    }

    public void setFechaLaboral(String fechaLaboral) {
        this.fechaLaboral = fechaLaboral;
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
    
     public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return "Distribuidor: " + idDistribuidor + " " + rutDistribuidor + " " + fechaLaboral + " " + telefono + " " + direccion + " " + estado;
    }

}
