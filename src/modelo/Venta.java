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
public class Venta {

    private int idVenta;
    private Factura factura;
    private Trabajador trabajador;
    private Cliente cliente;
    private Libro libro;

    public Venta(int idVenta, Factura factura, Trabajador trabajador, Cliente cliente, Libro libro) {
        this.idVenta = idVenta;
        this.factura = factura;
        this.trabajador = trabajador;
        this.cliente = cliente;
        this.libro = libro;
    }

    public Venta(Factura factura, Trabajador trabajador, Cliente cliente, Libro libro) {
        this.factura = factura;
        this.trabajador = trabajador;
        this.cliente = cliente;
        this.libro = libro;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
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

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return "Venta{" + "idVenta=" + idVenta + ", factura=" + factura + ", trabajador=" + trabajador + ", cliente=" + cliente + ", libro=" + libro + '}';
    }

}
