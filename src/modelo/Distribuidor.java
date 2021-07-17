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
public class Distribuidor {
    private int idDistribuidor;
    private String nombreDis;
    private String rutDistribuidor;
    private String fechaLaboral;
    private Telefono telefono;
    private Direccion direccion;
    private int estado;

    public Distribuidor() {
    }

    public Distribuidor(String nombreDis, String rutDistribuidor, Telefono telefono, Direccion direccion) {
        this.nombreDis = nombreDis;
        this.rutDistribuidor = rutDistribuidor;
        this.telefono = telefono;
        this.direccion = direccion;
        this.estado = 1;
    }

    public Distribuidor(int idDistribuidor, String nombreDis, String rutDistribuidor,  Telefono telefono, Direccion direccion, String fechaLaboral, int estado) {
        this.idDistribuidor = idDistribuidor;
        this.nombreDis = nombreDis;
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
    
        public String getNombreDis() {
        return nombreDis;
    }

    public void setNombreDis(String nombreDis) {
        this.nombreDis = nombreDis;
    }
    
    @Override
    public String toString() {
        return "Distribuidor: " + idDistribuidor +" "+ nombreDis+ " " + rutDistribuidor + " " + fechaLaboral + " " + telefono + " " + direccion + " " + estado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.idDistribuidor;
        hash = 47 * hash + Objects.hashCode(this.nombreDis);
        hash = 47 * hash + Objects.hashCode(this.rutDistribuidor);
        hash = 47 * hash + Objects.hashCode(this.fechaLaboral);
        hash = 47 * hash + Objects.hashCode(this.telefono);
        hash = 47 * hash + Objects.hashCode(this.direccion);
        hash = 47 * hash + this.estado;
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
        final Distribuidor other = (Distribuidor) obj;
        if (this.idDistribuidor != other.idDistribuidor) {
            return false;
        }
        if (this.estado != other.estado) {
            return false;
        }
        if (!Objects.equals(this.nombreDis, other.nombreDis)) {
            return false;
        }
        if (!Objects.equals(this.rutDistribuidor, other.rutDistribuidor)) {
            return false;
        }
        if (!Objects.equals(this.fechaLaboral, other.fechaLaboral)) {
            return false;
        }
        if (!Objects.equals(this.telefono, other.telefono)) {
            return false;
        }
        if (!Objects.equals(this.direccion, other.direccion)) {
            return false;
        }
        return true;
    }

  

}
