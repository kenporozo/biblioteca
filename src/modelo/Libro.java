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
public class Libro {

    private int idLibro;
    private int isbn;
    private String titulo;
    private int cantidadPaginas;
    private String fechaPublicacion;
    private int precio;
    private Idioma idioma;
    private Editorial editorial;

    public Libro() {
    }

    public Libro(int isbn, String titulo, int cantidadPaginas, String fechaPublicacion, int precio, Idioma idioma, Editorial editorial) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.cantidadPaginas = cantidadPaginas;
        this.fechaPublicacion = fechaPublicacion;
        this.precio = precio;
        this.idioma = idioma;
        this.editorial = editorial;

    }

    public Libro(int idLibro, int isbn, String titulo, int cantidadPaginas, String fechaPublicacion, int precio, Idioma idioma, Editorial editorial) {
        this.idLibro = idLibro;
        this.isbn = isbn;
        this.titulo = titulo;
        this.cantidadPaginas = cantidadPaginas;
        this.fechaPublicacion = fechaPublicacion;
        this.precio = precio;
        this.idioma = idioma;
        this.editorial = editorial;

    }



    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getCantidadPaginas() {
        return cantidadPaginas;
    }

    public void setCantidadPaginas(int cantidadPaginas) {
        this.cantidadPaginas = cantidadPaginas;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }


    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }


    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    @Override
    public String toString() {
        return "Libro: " + idLibro + ", " + isbn + ", " + titulo + ", " + cantidadPaginas + ", " + fechaPublicacion + ", " + precio + ", " + idioma + ", " + editorial;
    }

}
