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
public class Idioma {
    private int idIdioma;
   
    private String idioma;
    
    public Idioma(){
        
    }
    
    public Idioma(String idioma){
        this.idioma = idioma;
    }
    
    public Idioma(int idIdioma, String idioma){
        this.idIdioma = idIdioma;
        this.idioma = idioma;
    }

    public int getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(int idIdioma) {
        this.idIdioma = idIdioma;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
            
      @Override
    public String toString() {
        return idioma;
    } 

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + this.idIdioma;
        hash = 43 * hash + Objects.hashCode(this.idioma);
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
        final Idioma other = (Idioma) obj;
        if (this.idIdioma != other.idIdioma) {
            return false;
        }
        if (!Objects.equals(this.idioma, other.idioma)) {
            return false;
        }
        return true;
    }
    
    
}
