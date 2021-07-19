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
public class DireccionDAO extends DAO implements IDAO {

    private static final String SQL_INSERT = "INSERT INTO direccion(direccion) VALUES (?)";

    private static final String SQL_INSERT_CLIENT_DIRECCION = "INSERT INTO rel_direcc_cliente (id_cliente, id_direccion) VALUES(?, ?)";

    private static final String SQL_INSERT_TRABAJADOR_DIRECCION = "INSERT INTO rel_direcc_trabajador(id_trabajador, id_direccion) VALUES (?, ?)";

    private static final String SQL_SELECT_TRABAJADOR = "SELECT d.id_direccion, d.direccion, t.id_trabajador FROM direccion AS d INNER JOIN rel_direcc_trabajador AS rdc ON d.id_direccion = rdc.id_direccion INNER JOIN trabajador AS t ON rdc.id_trabajador = t.id_trabajador ORDER BY d.id_direccion";

    private static final String SQL_SELECT_CLIENTE = "SELECT d.id_direccion, d.direccion, c.id_cliente FROM direccion AS d INNER JOIN rel_direcc_cliente AS rdc ON d.id_direccion = rdc.id_direccion INNER JOIN cliente AS c ON rdc.id_cliente = c.id_cliente ORDER BY d.id_direccion";

    private static final String SQL_SELECT = "SELECT * FROM direccion ORDER BY id_direccion";

    private static final String SQL_DELETE = "DELETE FROM direccion WHERE id_direccion = ?";

    private static final String SQL_DELETE_TRABAJOR = "DELETE FROM rel_direcc_trabajador WHERE id_direccion = ?";

    private static final String SQL_DELETE_CLIENTE = "DELETE FROM rel_direcc_cliente WHERE id_direccion = ?";

    private static final String SQL_UPDATE = "UPDATE direccion SET direccion = ? WHERE id_direccion = ?";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM direccion WHERE id_direccion = ?";

    /**
     * Metodo que sirve para listar informacion de la base de datos
     *
     * @return ArrayList de objetos 
     *
     */
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
                int idDireccion = rs.getInt("id_direccion");
                String direccion = rs.getString("direccion");

                Direccion direcc = new Direccion(idDireccion, direccion);
                list.add(direcc);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } finally {
            close(stmt);
            close(conn);
        }

        return list;
    }

    /**
     * Metodo que sirve para listar informacion de la base de datos
     *
     * @return ArrayList de objetos 
     *
     */
    public ArrayList<Object> getListTrabajadores() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Object> list = new ArrayList<>();
        TrabajadorDAO tDAO = new TrabajadorDAO();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_TRABAJADOR);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idDireccion = rs.getInt("d.id_direccion");
                String direccion = rs.getString("d.direccion");
                int idTrabajador = rs.getInt("t.id_trabajador");
                Direccion varDireccion = new Direccion(idDireccion, direccion);
                Trabajador trabajador = (Trabajador) tDAO.buscar(idTrabajador);
                Trabajador varTb = new Trabajador(trabajador.getIdTrabajador(), trabajador.getRut(), trabajador.getNombre(), trabajador.getApellido(), varDireccion);
                list.add(varTb);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar telefonos de trabajadores" + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }

        return list;
    }

    /**
     * Metodo que sirve para listar informacion de la base de datos
     *
     * @return ArrayList de objetos 
     *
     */
    public ArrayList<Object> getListClientes() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Object> list = new ArrayList<>();
        ClienteDAO clDAO = new ClienteDAO();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_CLIENTE);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idDireccion = rs.getInt("d.id_direccion");
                String direccion = rs.getString("d.direccion");
                int idCliente = rs.getInt("c.id_cliente");
                Direccion varDireccion = new Direccion(idDireccion, direccion);
                Cliente cliente = (Cliente) clDAO.buscar(idCliente);
                Cliente varCl = new Cliente(cliente.getIdCliente(), cliente.getRut(), cliente.getNombre(), cliente.getApellido(), varDireccion);
                list.add(varCl);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar telefonos de clientes" + ex.getMessage());
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
     * @param idDireccion int
     * @param idTrabajador int
     * @return Devuelve un booelean
     *
     */
    public boolean insertarDireccionTrabajador(int idDireccion, int idTrabajador) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {

            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_TRABAJADOR_DIRECCION);
            stmt.setInt(1, idTrabajador);
            stmt.setInt(2, idDireccion);
            stmt.executeUpdate();
            stmt.close();
            estado = true;
            System.out.println("Inserto la direccion relacionada al trabajador");
        } catch (SQLException ex) {
            System.out.println("Error al agregar direccion " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

    /**
     * Metodo cuya funcionalidad es agregar objetos a la base de datos
     * dependiendo de su clase
     *
     * @param idDireccion int
     * @param idCliente int
     * @return Devuelve un booelean
     *
     */
    public boolean insertarDireccionCliente(int idDireccion, int idCliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_CLIENT_DIRECCION);
            stmt.setInt(1, idCliente);
            stmt.setInt(2, idDireccion);
            stmt.executeUpdate();
            stmt.close();
            estado = true;
            System.out.println("Inserto la direccion relacionada al cliente");
        } catch (SQLException ex) {
            System.out.println("Error al agregar direccion " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

    /**
     *  Metodo cuya funcionalidad es agregar objetos a la base de datos
     * dependiendo de su clase.
     * @param direccion objeto
     * 
     * @return int
     */
    public int insertarID(Direccion direccion) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int id = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, direccion.getDireccion());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            id = keys.getInt(1);

            stmt.close();

            System.out.println("Inserto la direccion");
        } catch (SQLException ex) {
            System.out.println("Error al agregar direccion " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return id;
    }

    /**
     * Metodo cuya funcionalidad es agregar objetos a la base de datos
     * dependiendo de su clase
     *
     * @param obj obejto
     * @return Devuelve un booelean
     *
     */
    @Override
    public boolean insertar(Object obj) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        Direccion direccion = (Direccion) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, direccion.getDireccion());
            stmt.executeUpdate();
            estado = true;
            System.out.println("Inserto la direccion");
        } catch (SQLException ex) {
            System.out.println("Error al agregar direccion " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

    /**
     * Metodo encargado de modificar objetos en la base de datos
     *
     * @param obj objeto
     * @return boolean
     */
    @Override
    public boolean modificar(Object obj) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        Direccion direccion = (Direccion) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);

            stmt.setString(1, direccion.getDireccion());
            stmt.setInt(2, direccion.getIdDireccion());
            stmt.executeUpdate();

            estado = true;
            System.out.println("Actualizo correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al actualizar direccion " + ex.getMessage());
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
     * @param idDireccion int
     * @return boolean
     *
     */
    @Override
    public boolean eliminar(int idDireccion) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE_TRABAJOR);
            stmt.setInt(1, idDireccion);
            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idDireccion);
            stmt.executeUpdate();
            stmt.close();
            estado = true;
            System.out.println("Elimino correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al eliminar direccion " + ex.getMessage());
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
     * @param idDireccion int
     * @return boolean
     *
     */
    public boolean eliminarCl(int idDireccion) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE_CLIENTE);
            stmt.setInt(1, idDireccion);
            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idDireccion);
            stmt.executeUpdate();
            stmt.close();
            estado = true;
            System.out.println("Elimino correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al eliminar la direccion relacionado al cliente " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;

    }

    /**
     * Metodo que se encargar de buscar objetos de la base de datos
     *
     * @param idDireccion int
     * @return object
     */
    @Override
    public Direccion buscar(int idDireccion) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);

            stmt.setInt(1, idDireccion);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String direccion = rs.getString("direccion");

                Direccion direc = new Direccion(idDireccion, direccion);
                return direc;
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
