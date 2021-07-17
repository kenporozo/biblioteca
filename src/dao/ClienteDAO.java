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
import modelo.Cliente;

/**
 *
 * @author Usuario
 */
public class ClienteDAO extends DAO implements IDAO {

    private static final String SQL_INSERT_CLIENTE_CORREO = "INSERT INTO rel_correo_cliente(id_cliente, id_correo) VALUES (?, ?)";
    private static final String SQL_INSERT_CLIENTE_DIRECCION = "INSERT INTO rel_direcc_cliente(id_cliente, id_direccion) VALUES (?, ?)";
    private static final String SQL_INSERT_CLIENTE_TLF = "INSERT INTO rel_tlf_cliente(id_cliente, id_telefono) VALUES (?, ?)";
    private static final String SQL_INSERT = "INSERT INTO cliente (rut_cliente, nombre_cliente, apellido_cliente, id_estado_entidad, fecha_nacimiento) VALUES(?, ?, ?, 1, ?)";
    private static final String SQL_SELECT = "SELECT * FROM cliente ORDER BY id_cliente";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM cliente WHERE id_cliente = ?";
    private static final String SQL_ACTIVAR = "UPDATE cliente SET id_estado_entidad = 1 WHERE id_cliente = ?";
    private static final String SQL_UPDATE = "UPDATE cliente SET rut_cliente = ?, nombre_cliente = ?, apellido_cliente = ? WHERE id_cliente = ?";
    private static final String SQL_DELETE = "UPDATE cliente SET id_estado_entidad = 2 WHERE id_cliente = ?";

    @Override
    public ArrayList<Object> getList() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Object> list = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idCliente = rs.getInt("id_cliente");
                String rut = rs.getString("rut_cliente");
                String nombre = rs.getString("nombre_cliente");
                String apellido = rs.getString("apellido_cliente");
                int estado = rs.getInt("id_estado_entidad");
                String fecha = rs.getString("fecha_nacimiento");

                Cliente cl = new Cliente(idCliente, rut, nombre, apellido, estado, fecha);
                list.add(cl);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
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
        Cliente cliente = (Cliente) obj;
        TelefonoDAO tDAO = new TelefonoDAO();
        CorreoDAO cDAO = new CorreoDAO();
        DireccionDAO dDAO = new DireccionDAO();
        try {
            //conn.setAutoCommit(false);
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, cliente.getRut());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getApellido());
            stmt.setString(4, cliente.getFechaNacimiento());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);
            stmt.close();

            int idTelefono = tDAO.insertarID(cliente.getTelefono());
            int idDireccion = dDAO.insertarID(cliente.getDireccion());
            int idCorreo = cDAO.insertarID(cliente.getCorreo());

            if (idTelefono > 0 && idDireccion > 0 && idCorreo > 0 && id > 0) {

                stmt = conn.prepareStatement(SQL_INSERT_CLIENTE_TLF);
                stmt.setInt(1, id);
                stmt.setInt(2, idTelefono);
                stmt.executeUpdate();
                stmt.close();
                System.out.println("Inserto el telefono relacionado al cliente");

                stmt = conn.prepareStatement(SQL_INSERT_CLIENTE_CORREO);
                stmt.setInt(1, id);
                stmt.setInt(2, idCorreo);
                stmt.executeUpdate();
                stmt.close();
                System.out.println("Inserto el correo relacionado al cliente");

                stmt = conn.prepareStatement(SQL_INSERT_CLIENTE_DIRECCION);
                stmt.setInt(1, id);
                stmt.setInt(2, idDireccion);
                stmt.executeUpdate();
                stmt.close();
                System.out.println("Inserto la direccion relacionada al cliente");

                estado = true;
                System.out.println("Inserto el cliente");
                //conn.commit();
            } else {
                conn.rollback();
            }
        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    System.out.println("Error, se hizo rollback " + ex1);
                }
            }
            System.out.println("Error al agregar cliente " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

    @Override
    public boolean modificar(Object obj) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        Cliente cliente = (Cliente) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);

            stmt.setString(1, cliente.getRut());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getApellido());
            stmt.setInt(4, cliente.getIdCliente());
            stmt.executeUpdate();

            estado = true;
            System.out.println("Actualizo correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al actualizar cliente " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

    @Override
    public boolean eliminar(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            estado = true;
            System.out.println("Desactivo correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al desactivar cliente " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

    @Override
    public Object buscar(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String rut = rs.getString("rut_cliente");
                String nombre = rs.getString("nombre_cliente");
                String apellido = rs.getString("apellido_cliente");
                int estado = rs.getInt("id_estado_entidad");
                String fecha = rs.getString("fecha_nacimiento");
                Cliente cl = new Cliente(id, rut, nombre, apellido, estado, fecha);
                return cl;
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

    public boolean activar(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean estado = false;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_ACTIVAR);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
            estado = true;
            System.out.println("Actualizo el estado del trabajador");
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el estado del trabajador " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

}
