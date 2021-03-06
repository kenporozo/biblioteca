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
public class Factura {

    private int idFactura;
    private double precioNeto;
    private double precioIva;
    private final static double COSTO_IVA = 0.19;
    private int costoIva = (int) (COSTO_IVA * 100);
    private String fechaCompra;
    private String horaCompra;
    private String detalleCompra;
    private String fechaEstimada;
    private int idPago;

    public Factura() {
    }

    public Factura(int idFactura, double precioNeto, double precioIva, String fechaCompra, int idPago) {
        this.idFactura = idFactura;
        this.precioNeto = precioNeto;
        this.precioIva = precioIva;
        this.fechaCompra = fechaCompra;
        this.idPago = idPago;
    }

    public Factura(int idFactura, double precioNeto, double precioIva, String fechaCompra, String detalleCompra, int idPago) {
        this.idFactura = idFactura;
        this.precioNeto = precioNeto;
        this.precioIva = precioIva;
        this.fechaCompra = fechaCompra;
        this.detalleCompra = detalleCompra;
        this.idPago = idPago;
    }

    public Factura(double precioNeto, int idPago, String detalleCompra) {
        this.precioNeto = precioNeto;
        this.precioIva = (precioNeto * COSTO_IVA) + precioNeto;
        this.idPago = idPago;
        this.detalleCompra = detalleCompra;
    }

    public Factura(double precioNeto, int idPago) {
        this.precioNeto = precioNeto;
        this.precioIva = (precioNeto * COSTO_IVA) + precioNeto;
        this.idPago = idPago;
    }

    public Factura(double precioNeto, String fechaEstimada) {
        this.precioNeto = precioNeto;
        this.precioIva = (precioNeto * COSTO_IVA) + precioNeto;
        this.fechaEstimada = fechaEstimada;
    }

    public Factura(int idFactura, double costoNeto, String fechaCompra, String horaCompra, int idPago, String detalleCompra) {
        this.idFactura = idFactura;
        this.precioNeto = costoNeto;
        this.precioIva = (costoNeto * COSTO_IVA) + costoNeto;
        this.fechaCompra = fechaCompra;
        this.horaCompra = horaCompra;
        this.idPago = idPago;
        this.detalleCompra = detalleCompra;
    }

    public Factura(int idFactura, double costoNeto, double precioIva, String fechaCompra, String fechaEstimada) {
        this.idFactura = idFactura;
        this.precioNeto = costoNeto;
        this.precioIva = precioIva;
        this.fechaCompra = fechaCompra;
        this.fechaEstimada = fechaEstimada;
    }

    public Factura(int idFactura, double costoNeto, double precioIva, int costoIva, String fechaCompra, String horaCompra, int idPago, String detalleCompra) {
        this.idFactura = idFactura;
        this.precioNeto = costoNeto;
        this.precioIva = precioIva;
        this.costoIva = costoIva;
        this.fechaCompra = fechaCompra;
        this.horaCompra = horaCompra;
        this.idPago = idPago;
        this.detalleCompra = detalleCompra;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public double getPrecioNeto() {
        return precioNeto;
    }

    public void setPrecioNeto(double precioNeto) {
        this.precioNeto = precioNeto;
    }

    public double getPrecioIva() {
        return precioIva;
    }

    public void setPrecioIva(double precioIva) {
        this.precioIva = precioIva;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getHoraCompra() {
        return horaCompra;
    }

    public void setHoraCompra(String horaCompra) {
        this.horaCompra = horaCompra;
    }

    public String getDetalleCompra() {
        return detalleCompra;
    }

    public void setDetalleCompra(String detalleCompra) {
        this.detalleCompra = detalleCompra;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getCostoIva() {
        return costoIva;
    }

    public String getFechaEstimada() {
        return fechaEstimada;
    }

    public void setFechaEstimada(String fechaEstimada) {
        this.fechaEstimada = fechaEstimada;
    }

    @Override
    public String toString() {
        return "Factura: " + idFactura + ", " + precioNeto + ", " + precioIva + ", " + costoIva + "%" + ", " + fechaCompra + ", " + horaCompra + ", " + idPago + ", " + detalleCompra;
    }

}
