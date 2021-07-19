/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.*;

/**
 *
 * @author Usuario
 */
public class VentaDAO extends DAO {

    private static final String SQL_SELECT = "SELECT v.id_venta, t.id_trabajador, t.nombre_trabajador, c.id_cliente, c.nombre_cliente, b.id_boleta, b.precio_neto_bol, b.precio_iva_bol, b.fecha_venta, b.id_tipo_pago, b.id_libro FROM venta AS v INNER JOIN boleta AS b ON v.id_boleta = b.id_boleta INNER JOIN trabajador AS t ON v.id_trabajador = t.id_trabajador INNER JOIN cliente AS c ON v.id_cliente = c.id_cliente INNER JOIN libro AS l ON b.id_libro = l.id_libro ORDER BY id_venta";

    private static final String SQL_INSERT_VENTA = "INSERT INTO venta(id_cliente, id_trabajador, id_boleta) VALUES (?, ?, ?)";

    private static final String SQL_INSERT_BOLETA = "INSERT INTO boleta(precio_neto_bol, precio_iva_bol, costo_iva_bol, fecha_venta, hora_venta, id_tipo_pago, id_libro) VALUES (?, ?, ?, curdate(), curtime(), ?, ?)";

    private static final String SQL_DELETE = "DELETE FROM venta WHERE id_venta = ?";

    private static final String SQL_UPDATE_LIBRO = "UPDATE inventario SET id_estado = 1 WHERE id_libro = ?";

    /**
     * Metodo que sirve para listar informacion de la base de datos
     *
     * @return ArrayList de objetos 
     *
     */
    public ArrayList<Object> getList() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Object> list = new ArrayList<>();
        LibroDAO lDAO = new LibroDAO();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int idVenta = rs.getInt("v.id_venta");
                int idTrabajador = rs.getInt("t.id_trabajador");
                String nombreTrabajador = rs.getString("t.nombre_trabajador");
                int idCliente = rs.getInt("c.id_cliente");
                String nombreCliente = rs.getString("c.nombre_cliente");
                int idBoleta = rs.getInt("b.id_boleta");
                double precioNeto = (double) rs.getInt("b.precio_neto_bol");
                double precioIva = (double) rs.getInt("b.precio_iva_bol");
                String fechaCompra = rs.getString("b.fecha_venta");
                int idTipoPago = rs.getInt("b.id_tipo_pago");
                int idLibro = rs.getInt("b.id_libro");

                Trabajador trabajador = new Trabajador(idTrabajador, nombreTrabajador);
                Cliente cliente = new Cliente(idCliente, nombreCliente);
                Factura factura = new Factura(idBoleta, precioNeto, precioIva, fechaCompra, idTipoPago);
                Libro libro = lDAO.buscar(idLibro);

                Venta venta = new Venta(idVenta, factura, trabajador, cliente, libro);
                list.add(venta);

            }

        } catch (SQLException ex) {
            System.out.println("Error al listar ventas " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }

        return list;
    }

    /**
     * Metodo cuya funcionalidad es agregar objetos a la base de datos
     * dependiendo de su clase
     *
     * @param obj objeto
     * @return Devuelve un booelean
     *
     */
    public boolean insertar(Object obj) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        Venta venta = (Venta) obj;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_BOLETA, Statement.RETURN_GENERATED_KEYS);

            stmt.setDouble(1, venta.getFactura().getPrecioNeto());
            stmt.setDouble(2, venta.getFactura().getPrecioIva());
            stmt.setInt(3, venta.getFactura().getCostoIva());
            stmt.setInt(4, venta.getFactura().getIdPago());
            stmt.setInt(5, venta.getLibro().getIdLibro());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);

            stmt.close();

            stmt = conn.prepareStatement(SQL_INSERT_VENTA);
            stmt.setInt(1, venta.getCliente().getIdCliente());
            stmt.setInt(2, venta.getTrabajador().getIdTrabajador());
            stmt.setInt(3, id);
            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement(SQL_UPDATE_LIBRO);
            stmt.setInt(1, venta.getLibro().getIdLibro());
            stmt.executeUpdate();
            stmt.close();

            estado = true;
            System.out.println("Registro la venta y la boleta");
        } catch (SQLException ex) {
            System.out.println("Error al registrar venta " + ex.getMessage());

        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

    /**
     * Metodo que sirve para eliminar o cambiar el estado de los objetos en la
     * base de datos
     *
     * @param id int
     * @return boolean
     *
     */
    public boolean eliminar(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
            estado = true;
            System.out.println("Elimino correctamente la venta");
        } catch (SQLException ex) {
            System.out.println("Error al eliminar venta" + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

}
