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
public class Direccion {
    private int idDireccion;
    private String direccion;
    
    public Direccion(){
        
    }
    
    public Direccion(String direccion){
        this.direccion = direccion;
    }

    public Direccion(int idDireccion, String direccion) {
        this.idDireccion = idDireccion;
        this.direccion = direccion;
    }
    
    

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Direccion: " + idDireccion + " " + direccion;
    }
    
    
}
