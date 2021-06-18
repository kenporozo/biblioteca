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
public class FacturaDAO extends DAO implements IDAO {

    private static final String SQL_INSERT_COMPRA = "INSERT INTO compra(id_distribuidor, id_factura) VALUES (?, ?)";

    private static final String SQL_INSERT = "INSERT INTO factura(precio_neto, precio_iva, costo_iva, fecha_compra, hora_compra, id_distribuidor, id_tipo_pago, detalle_compra) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_SELECT = "SELECT * FROM factura ORDER BY id_factura";

    private static final String SQL_DELETE = "DELETE FROM factura WHERE id_factura = ?";

    private static final String SQL_UPDATE = "UPDATE factura SET precio_neto = ?, precio_iva = ?, costo_iva = ?, fecha_compra = ?, hora_compra = ?, id_distribuidor = ?, id_tipo_pago = ?, detalle_compra = ? WHERE id_factura = ?";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM factura WHERE id_factura = ?";

    @Override
    public ArrayList<Object> getList() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Object> list = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            ResultSet rs = stmt.executeQuery();
            DistribuidorDAO distDAO = new DistribuidorDAO();

            while (rs.next()) {

                int idFactura = rs.getInt("id_factura");
                double precioNeto = (double) rs.getInt("precio_neto");
                double precioIva = (double) rs.getInt("precio_iva");
                int costoIva = rs.getInt("costo_iva");
                String fechaCompra = rs.getString("fecha_compra");
                String horaCompra = rs.getString("hora_compra");
                int idDistribuidor = rs.getInt("id_distribuidor");
                int idTipoPago = rs.getInt("id_tipo_pago");
                String detalleCompra = rs.getString("detalle_compra");

                Distribuidor distribuidor = distDAO.buscar(idDistribuidor);

                Factura factura = new Factura(idFactura, precioNeto, precioIva, costoIva, fechaCompra, horaCompra, distribuidor, idTipoPago, detalleCompra);
                list.add(factura);

            }

        } catch (SQLException ex) {
            System.out.println("Error al listar facturas " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }

        return list;
    }

    @Override
    public boolean insertar(Object obj) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        Factura factura = (Factura) obj;
        DistribuidorDAO distDAO = new DistribuidorDAO();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            stmt.setDouble(1, factura.getPrecioNeto());
            stmt.setDouble(2, factura.getPrecioIva());
            stmt.setInt(3, factura.getCostoIva());
            stmt.setString(4, factura.getFechaCompra());
            stmt.setString(5, factura.getHoraCompra());
            stmt.setInt(6, factura.getDistribuidor().getIdDistribuidor());
            stmt.setInt(7, factura.getIdPago());
            stmt.setString(8, factura.getDetalleCompra());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);
             
            factura.setIdFactura(id);
            stmt.close();
            
            stmt = conn.prepareStatement(SQL_INSERT_COMPRA);
            stmt.setInt(1, factura.getDistribuidor().getIdDistribuidor());
            stmt.setInt(2, id);
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

    @Override
    public boolean modificar(int id, Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object buscar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
