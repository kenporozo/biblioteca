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
    
    public Autor(int idAutor, String nombreAutor){
        this.idAutor = idAutor;
        this.nombreAutor = nombreAutor;
    }
    
    public Autor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }
    
    public Autor(String nombreAutor, String apellidoPatero){
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
        return "Autor: " + idAutor + " " + nombreAutor + " " + apellidoPaterno;
    }

}
