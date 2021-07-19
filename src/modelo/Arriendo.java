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
public class Arriendo {

    private int idArriendo;
    private Trabajador trabajador;
    private Cliente cliente;
    private Factura factura;
    private Libro libro;
    private String fechaEntrega;

    public Arriendo(int idArriendo, Trabajador trabajador, Cliente cliente, Factura factura, Libro libro) {
        this.idArriendo = idArriendo;
        this.trabajador = trabajador;
        this.cliente = cliente;
        this.factura = factura;
        this.libro = libro;
    }

    public Arriendo(int idArriendo, Trabajador trabajador, Cliente cliente, Factura factura, Libro libro, String fechaEntrega) {
        this.idArriendo = idArriendo;
        this.trabajador = trabajador;
        this.cliente = cliente;
        this.factura = factura;
        this.libro = libro;
        this.fechaEntrega = fechaEntrega;
    }

    public Arriendo(Trabajador trabajador, Cliente cliente, Factura factura, Libro libro) {
        this.trabajador = trabajador;
        this.cliente = cliente;
        this.factura = factura;
        this.libro = libro;
    }

    public int getIdArriendo() {
        return idArriendo;
    }

    public void setIdArriendo(int idArriendo) {
        this.idArriendo = idArriendo;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return "Arriendo{" + "idArriendo=" + idArriendo + ", trabajador=" + trabajador + ", cliente=" + cliente + ", factura=" + factura + ", fechaEntrega=" + fechaEntrega + '}';
    }

}
