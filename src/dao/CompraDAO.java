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
public class CompraDAO extends DAO {

    private static final String SQL_INSERT_COMPRA = "INSERT INTO compra(id_distribuidor, id_factura) VALUES (?, ?)";

    private static final String SQL_INSERT_FACTURA = "INSERT INTO factura(precio_neto, precio_iva, costo_iva, fecha_compra, hora_compra, id_tipo_pago, detalle_compra) VALUES (?, ?, ?, curdate(), curtime(), ?, ?)";

    private static final String SQL_SELECT = "SELECT c.id_compra, c.id_distribuidor, d.empresa, c.id_factura, f.precio_neto, f.precio_iva, f.fecha_compra, f.id_tipo_pago, f.detalle_compra FROM compra AS c INNER JOIN distribuidor AS d ON c.id_distribuidor = d.id_distribuidor INNER JOIN factura AS f ON c.id_factura = f.id_factura ORDER BY c.id_compra";

    private static final String SQL_DELETE = "DELETE FROM compra WHERE id_compra = ?";

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

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int idCompra = rs.getInt("c.id_compra");
                int idDistribuidor = rs.getInt("c.id_distribuidor");
                String empresa = rs.getString("d.empresa");
                int idFactura = rs.getInt("c.id_factura");
                double precioNeto = (double) rs.getInt("f.precio_neto");
                double precioIva = (double) rs.getInt("f.precio_iva");
                String fechaCompra = rs.getString("f.fecha_compra");
                int idTipoPago = rs.getInt("f.id_tipo_pago");
                String detalleCompra = rs.getString("f.detalle_compra");

                Distribuidor distribuidor = new Distribuidor(idDistribuidor, empresa);
                Factura factura = new Factura(idFactura, precioNeto, precioIva, fechaCompra, detalleCompra, idTipoPago);

                Compra compra = new Compra(idCompra, distribuidor, factura);
                list.add(compra);

            }

        } catch (SQLException ex) {
            System.out.println("Error al listar compras " + ex.getMessage());
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
     * @return booelean
     *
     */
    public boolean insertar(Object obj) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        Compra compra = (Compra) obj;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_FACTURA, Statement.RETURN_GENERATED_KEYS);

            stmt.setDouble(1, compra.getFactura().getPrecioNeto());
            stmt.setDouble(2, compra.getFactura().getPrecioIva());
            stmt.setInt(3, compra.getFactura().getCostoIva());
            stmt.setInt(4, compra.getFactura().getIdPago());
            stmt.setString(5, compra.getFactura().getDetalleCompra());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);

            stmt.close();

            stmt = conn.prepareStatement(SQL_INSERT_COMPRA);
            stmt.setInt(1, compra.getDistribuidor().getIdDistribuidor());
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt.close();

            estado = true;
            System.out.println("Registro la compra y la factura");
        } catch (SQLException ex) {
            System.out.println("Error al registrar compra " + ex.getMessage());

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
            System.out.println("Elimino correctamente la compra");
        } catch (SQLException ex) {
            System.out.println("Error al eliminar compra" + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }
}
