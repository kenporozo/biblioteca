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
import modelo.Correo;
import modelo.Trabajador;

/**
 *
 * @author Usuario
 */
public class CorreoDAO extends DAO implements IDAO {

    private static final String SQL_SELECT = "SELECT * FROM correo ORDER BY id_correo";

    private static final String SQL_INSERT = "INSERT INTO correo(correo) VALUES (?)";

    private static final String SQL_INSERT_CLIENT_CORREO = "INSERT INTO rel_correo_cliente (id_cliente, id_correo) VALUES(?, ?)";

    private static final String SQL_INSERT_TRABAJADOR_CORREO = "INSERT INTO rel_correo_trabajador(id_trabajador, id_correo) VALUES (?, ?)";

    private static final String SQL_DELETE = "DELETE FROM correo WHERE id_correo = ?";

    private static final String SQL_DELETE_TRABAJOR = "DELETE FROM rel_correo_trabajador WHERE id_correo = ?";

    private static final String SQL_DELETE_CLIENTE = "DELETE FROM rel_correo_cliente WHERE id_correo = ?";

    private static final String SQL_UPDATE = "UPDATE correo SET correo = ? WHERE id_correo = ?";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM correo WHERE id_correo = ?";

    private static final String SQL_SELECT_TRABAJADOR = "SELECT c.id_correo, c.correo, t.id_trabajador, t.nombre_trabajador, t.apellido_trabajador, t.rut_trabajador FROM correo AS c INNER JOIN rel_correo_trabajador AS rc ON c.id_correo = rc.id_correo INNER JOIN trabajador AS t ON rc.id_trabajador = t.id_trabajador ORDER BY c.id_correo";

    private static final String SQL_SELECT_CLIENTE = "SELECT c.id_correo, c.correo, cl.id_cliente, cl.nombre_cliente, cl.apellido_cliente, cl.rut_cliente FROM correo AS c INNER JOIN rel_correo_cliente AS rc ON c.id_correo = rc.id_correo INNER JOIN cliente AS cl ON rc.id_cliente = cl.id_cliente ORDER BY c.id_correo";

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
                int idCorreo = rs.getInt("id_correo");
                String correo = rs.getString("correo");
                Correo varCorreo = new Correo(idCorreo, correo);
                list.add(varCorreo);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar correos " + ex.getMessage());
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

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_TRABAJADOR);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idCorreo = rs.getInt("c.id_correo");
                String correo = rs.getString("c.correo");
                String nombre = rs.getString("t.nombre_trabajador");
                String apellido = rs.getString("t.apellido_trabajador");
                String rut = rs.getString("rut_trabajador");
                int idTrabajador = rs.getInt("t.id_trabajador");
                Correo varCorreo = new Correo(idCorreo, correo);

                Trabajador varTb = new Trabajador(idTrabajador, rut, nombre, apellido, varCorreo);
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

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_CLIENTE);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idCorreo = rs.getInt("c.id_correo");
                String correo = rs.getString("c.correo");
                String nombre = rs.getString("cl.nombre_cliente");
                String apellido = rs.getString("cl.apellido_cliente");
                String rut = rs.getString("cl.rut_cliente");
                int idCliente = rs.getInt("id_cliente");
                Correo varCorreo = new Correo(idCorreo, correo);
                Cliente varCl = new Cliente(idCliente, rut, nombre, apellido, varCorreo);
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
     * @param idCorreo int
     * @param idTrabajador int
     * @return booelean
     *
     */
    public boolean insertarCorreoTrabajador(int idCorreo, int idTrabajador) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_TRABAJADOR_CORREO);
            stmt.setInt(1, idTrabajador);
            stmt.setInt(2, idCorreo);
            stmt.executeUpdate();
            stmt.close();
            estado = true;
            System.out.println("Inserto el correo relacionado al trabajador");
        } catch (SQLException ex) {
            System.out.println("Error al agregar correo " + ex.getMessage());
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
     * @param idCorreo int
     * @param idCliente int
     * @return booelean
     *
     */
    public boolean insertarCorreoCliente(int idCorreo, int idCliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_CLIENT_CORREO);
            stmt.setInt(1, idCliente);
            stmt.setInt(2, idCorreo);
            stmt.executeUpdate();
            stmt.close();
            estado = true;
            System.out.println("Inserto el correo relacionado al cliente");
        } catch (SQLException ex) {
            System.out.println("Error al agregar correo " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

    /**
     * @param correo objeto
     * @return int
     */
    public int insertarID(Correo correo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int id = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, correo.getCorreo());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            id = keys.getInt(1);

            stmt.close();

            System.out.println("Inserto el correo");
        } catch (SQLException ex) {
            System.out.println("Error al agregar correo " + ex.getMessage());
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
     * @param obj objeto
     * @return Devuelve un booelean
     *
     */
    @Override
    public boolean insertar(Object obj) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        Correo correo = (Correo) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, correo.getCorreo());
            stmt.executeUpdate();
            estado = true;
            System.out.println("Inserto el correo");
        } catch (SQLException ex) {
            System.out.println("Error al agregar correo " + ex.getMessage());
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
        Correo correo = (Correo) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);

            stmt.setString(1, correo.getCorreo());
            stmt.setInt(2, correo.getIdCorreo());
            stmt.executeUpdate();

            estado = true;
            System.out.println("Actualizo correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al actualizar correo " + ex.getMessage());
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
    @Override
    public boolean eliminar(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE_TRABAJOR);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
            estado = true;
            System.out.println("Elimino correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al eliminar correo relacionado al trabajador" + ex.getMessage());
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
    public boolean eliminarCl(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE_CLIENTE);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
            estado = true;
            System.out.println("Elimino correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al eliminar correo relacionado al cliente " + ex.getMessage());
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
                String correo = rs.getString("correo");

                Correo varCorreo = new Correo(id, correo);
                return varCorreo;
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
