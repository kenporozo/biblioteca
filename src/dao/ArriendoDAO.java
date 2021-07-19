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
import java.util.ArrayList;
import modelo.*;

/**
 *
 * @author Usuario
 */
public class ArriendoDAO extends DAO {

    private static final String SQL_INSERT_BOLETA_ARRRIENDO = "INSERT INTO boleta_arriendo(precio_neto_bol_arriendo, precio_iva_bol_arriendo, costo_iva_bol_arriendo, fecha_arriendo, fecha_estimada_dev, id_libro, id_cliente, id_trabajador)VALUES(?,?,?, CURDATE(), ?, ?, ?, ?)";

    private static final String SQL_INSERT_ARRIENDO = "INSERT INTO arriendo(fecha_real_entrega, id_boleta_arriendo, id_cliente, id_trabajador, id_pago)VALUES(?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_MULTA = "INSERT INTO multa(total_multa, id_arriendo) VALUES(?, ?)";
    
    private static final String SQL_UPDATE_LIBRO = "UPDATE inventario SET id_estado = 2 WHERE id_libro = ?";

    private static final String SQL_UPDATE_LIBRO_PAGO = "UPDATE inventario SET id_estado = 3 WHERE id_libro = ?";
    
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM boleta_arriendo WHERE id_boleta_arriendo = ?";

    private static final String SQL_SELECT_ARRIENDOS_PEND = "SELECT b.id_boleta_arriendo, b.precio_neto_bol_arriendo, b.precio_iva_bol_arriendo, b.fecha_arriendo, b.fecha_estimada_dev, b.id_libro, c.id_cliente, c.nombre_cliente, t.id_trabajador, t.nombre_trabajador FROM boleta_arriendo as b inner join trabajador AS t ON b.id_trabajador = t.id_trabajador INNER JOIN cliente as c on b.id_cliente = c.id_cliente WHERE curdate() <= b.fecha_estimada_dev ORDER BY b.id_boleta_arriendo";

    private static final String SQL_DIAS_DIFF = "SELECT DATEDIFF(a.fecha_real_entrega, b.fecha_estimada_dev) as dias FROM arriendo AS a INNER JOIN boleta_arriendo AS b ON a.id_boleta_arriendo = b.id_boleta_arriendo WHERE a.id_boleta_arriendo = ?";

    
    /** 
     * Metodo que sirve para listar arriendos
     * @return ArrayList de objetos 
     **/
    public ArrayList<Object> getListArriendosPend() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Object> list = new ArrayList<>();
        LibroDAO lDAO = new LibroDAO();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_ARRIENDOS_PEND);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int idBoleta = rs.getInt("b.id_boleta_arriendo");
                int idCliente = rs.getInt("c.id_cliente");
                String nombreCliente = rs.getString("c.nombre_cliente");
                double precioNeto = (double) rs.getInt("b.precio_neto_bol_arriendo");
                double precioIva = (double) rs.getInt("b.precio_iva_bol_arriendo");
                String fechaCompra = rs.getString("b.fecha_arriendo");
                String fechaEstimada = rs.getString("b.fecha_estimada_dev");
                int idTrabajador = rs.getInt("t.id_trabajador");
                String nombreTrabajador = rs.getString("t.nombre_trabajador");
                int idLibro = rs.getInt("b.id_libro");

                //creo variables necesarias para crear objeto arriendo
                Cliente cliente = new Cliente(idCliente, nombreCliente);
                Trabajador trabajador = new Trabajador(idTrabajador, nombreTrabajador);
                Libro libro = lDAO.buscar(idLibro);
                Factura factura = new Factura(idBoleta, precioNeto, precioIva, fechaCompra, fechaEstimada);

                Arriendo arriendo = new Arriendo(trabajador, cliente, factura, libro);
                list.add(arriendo);

            }

        } catch (SQLException ex) {
            System.out.println("Error al listar arriendos" + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }

        return list;
    }

    /** 
     * Metodo cuya funcionalidad es agregar objetos a la base de datos dependiendo de su clase
     * @param obj objeto
     * @return Devuelve un booelean
     **/
    public boolean insertar(Object obj) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        Arriendo arriendo = (Arriendo) obj;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_BOLETA_ARRRIENDO);

            stmt.setDouble(1, arriendo.getFactura().getPrecioNeto());
            stmt.setDouble(2, arriendo.getFactura().getPrecioIva());
            stmt.setInt(3, arriendo.getFactura().getCostoIva());
            stmt.setString(4, arriendo.getFactura().getFechaEstimada());
            stmt.setInt(5, arriendo.getLibro().getIdLibro());
            stmt.setInt(6, arriendo.getCliente().getIdCliente());
            stmt.setInt(7, arriendo.getTrabajador().getIdTrabajador());
            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement(SQL_UPDATE_LIBRO);
            stmt.setInt(1, arriendo.getLibro().getIdLibro());
            stmt.executeUpdate();
            stmt.close();

            estado = true;
            System.out.println("Registro el arriendo y la boleta");
        } catch (SQLException ex) {
            System.out.println("Error al registrar arriendo " + ex.getMessage());

        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

    /** 
     Metodo que se encargar de averiguar si existen días atrasados en la fecha de entrega de libros
     * @param id int
     * @return int
     */
    public int buscarMulta(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int dias = 0;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DIAS_DIFF);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
               dias = rs.getInt("dias");
            }
            System.out.println(dias);
            System.out.println("Registro el arriendo y la boleta");
        } catch (SQLException ex) {
            System.out.println("Error al registrar arriendo " + ex.getMessage());

        } finally {
            close(stmt);
            close(conn);
        }
        return dias;
    }

     /** 
     * Metodo cuya funcionalidad es agregar objetos a la base de datos dependiendo de su clase
     * @param obj objeto
     * @return booelean
     **/
    public boolean insertarArriendo(Object obj) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Arriendo arriendo = (Arriendo) obj;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_ARRIENDO);

            stmt.setString(1, arriendo.getFechaEntrega());
            stmt.setInt(2, arriendo.getFactura().getIdFactura());
            stmt.setInt(3, arriendo.getCliente().getIdCliente());
            stmt.setInt(4, arriendo.getTrabajador().getIdTrabajador());
            stmt.setInt(5, arriendo.getFactura().getIdPago());
            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement(SQL_UPDATE_LIBRO_PAGO);
            stmt.setInt(1, arriendo.getLibro().getIdLibro());
            stmt.executeUpdate();
            stmt.close();

            estado = true;
            System.out.println("Registro el arriendo y la boleta");
        } catch (SQLException ex) {
            System.out.println("Error al registrar arriendo " + ex.getMessage());

        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

     /** 
     * Metodo cuya funcionalidad es agregar objetos a la base de datos dependiendo de su clase
     * @param id int
     * @param totalMulta int
     * @return Devuelve un booelean
     **/
    public boolean insertarMulta(int id, int totalMulta) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_MULTA);
            stmt.setInt(1, totalMulta);
            stmt.setInt(2, id);
            stmt.executeUpdate();

           estado = true;
            System.out.println("Registro la multa");
        } catch (SQLException ex) {
            System.out.println("Error al registrar multa " + ex.getMessage());

        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }
    
    /**
     * Metodo que se encargar de buscar objetos de la base de datos
     *
     * @param id int
     * @return object
     */
     public Object buscar(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        TrabajadorDAO tDAO = new TrabajadorDAO();
        ClienteDAO cDAO = new ClienteDAO();
        LibroDAO lDAO = new LibroDAO();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idCliente = rs.getInt("id_cliente");
                double precioNeto = (double) rs.getInt("precio_neto_bol_arriendo");
                double precioIva = (double) rs.getInt("precio_iva_bol_arriendo");
                String fechaArriendo = rs.getString("fecha_arriendo");
                String fechaEstimada = rs.getString("fecha_estimada_dev");
                int idTrabajador = rs.getInt("id_trabajador");
                int idLibro = rs.getInt("id_libro");

                Cliente cliente = (Cliente) cDAO.buscar(idCliente);
                Trabajador trabajador = (Trabajador) tDAO.buscar(idTrabajador);
                Libro libro = lDAO.buscar(idLibro);
                Factura factura = new Factura(id, precioNeto, precioIva, fechaArriendo, fechaEstimada);

                Arriendo arriendo = new Arriendo(trabajador, cliente, factura, libro);
                return arriendo;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);

        } finally {
            close(stmt);
            close(conn);
        }
    }
}
