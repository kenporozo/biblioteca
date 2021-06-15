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
        return "Idioma: " + idIdioma + " " + idioma;
    }
}
