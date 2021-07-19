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
import modelo.Telefono;
import modelo.Trabajador;

/**
 *
 * @author Usuario
 */
public class TelefonoDAO extends DAO implements IDAO {

    private static final String SQL_INSERT = "INSERT INTO telefono(num_telefono) VALUES (?)";

    private static final String SQL_INSERT_CLIENT_TLF = "INSERT INTO rel_tlf_cliente (id_cliente, id_telefono) VALUES(?, ?)";

    private static final String SQL_INSERT_TRABAJADOR_TLF = "INSERT INTO rel_tlf_trabajador(id_trabajador, id_telefono) VALUES (?, ?)";

    private static final String SQL_SELECT = "SELECT * FROM telefono ORDER BY id_telefono";

    private static final String SQL_SELECT_TRABAJADOR = "SELECT tlf.id_telefono, tlf.num_telefono, t.id_trabajador FROM telefono AS tlf INNER JOIN rel_tlf_trabajador AS rtlf ON tlf.id_telefono = rtlf.id_telefono INNER JOIN trabajador AS t ON rtlf.id_trabajador = t.id_trabajador ORDER BY tlf.id_telefono";

    private static final String SQL_SELECT_CLIENTE = "SELECT tlf.id_telefono, tlf.num_telefono, c.id_cliente FROM telefono AS tlf INNER JOIN rel_tlf_cliente AS rtlf ON tlf.id_telefono = rtlf.id_telefono INNER JOIN cliente AS c ON rtlf.id_cliente = c.id_cliente ORDER BY tlf.id_telefono";

    private static final String SQL_DELETE = "DELETE FROM telefono WHERE id_telefono = ?";

    private static final String SQL_DELETE_TRABAJOR = "DELETE FROM rel_tlf_trabajador WHERE id_telefono = ?";

    private static final String SQL_DELETE_CLIENTE = "DELETE FROM rel_tlf_cliente WHERE id_telefono = ?";

    private static final String SQL_UPDATE = "UPDATE telefono SET num_telefono = ? WHERE id_telefono = ?";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM telefono WHERE id_telefono = ?";

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
                int idTelefono = rs.getInt("id_telefono");
                String numTelf = rs.getString("num_telefono");
                Telefono telefono = new Telefono(idTelefono, numTelf);
                list.add(telefono);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar telefonos " + ex.getMessage());
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
                int idTelefono = rs.getInt("id_telefono");
                String numTelf = rs.getString("num_telefono");
                int idTrabajador = rs.getInt("id_trabajador");
                Telefono telefono = new Telefono(idTelefono, numTelf);
                Trabajador trabajador = (Trabajador) tDAO.buscar(idTrabajador);
                Trabajador varTb = new Trabajador(trabajador.getIdTrabajador(), trabajador.getRut(), trabajador.getNombre(), trabajador.getApellido(), telefono);
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
                int idTelefono = rs.getInt("id_telefono");
                String numTelf = rs.getString("num_telefono");
                int idCliente = rs.getInt("id_cliente");
                Telefono tlf = new Telefono(idTelefono, numTelf);
                Cliente cliente = (Cliente) clDAO.buscar(idCliente);
                Cliente varCl = new Cliente(cliente.getIdCliente(), cliente.getRut(), cliente.getNombre(), cliente.getApellido(), tlf);
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
     * @param idTelefono parametro necesario para buscar en la base de datos
     * @param idTrabajador parametro necesario para buscar en la base de datos
     * @return booelean
     *
     */
    public boolean insertarTlfTrabajador(int idTelefono, int idTrabajador) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_TRABAJADOR_TLF);
            stmt.setInt(1, idTrabajador);
            stmt.setInt(2, idTelefono);
            stmt.executeUpdate();
            stmt.close();
            estado = true;
            System.out.println("Inserto el telefono relacionado al trabajador");
        } catch (SQLException ex) {
            System.out.println("Error al agregar telefono " + ex.getMessage());
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
     * @param idTelefono parametro necesario para buscar en la base de datos
     * @param idCliente parametro necesario para buscar en la base de datos
     * @return booelean
     *
     */
    public boolean insertarTlfCliente(int idTelefono, int idCliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_CLIENT_TLF);
            stmt.setInt(1, idCliente);
            stmt.setInt(2, idTelefono);
            stmt.executeUpdate();
            stmt.close();
            estado = true;
            System.out.println("Inserto el telefono relacionado al cliente");
        } catch (SQLException ex) {
            System.out.println("Error al agregar telefono " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

    /**
     * @param telefono object
     * @return int
     */
    public int insertarID(Telefono telefono) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int id = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, telefono.getNumTelf());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            id = keys.getInt(1);

            stmt.close();

            System.out.println("Inserto el telefono");
        } catch (SQLException ex) {
            System.out.println("Error al agregar telefono " + ex.getMessage());
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
        Telefono telefono = (Telefono) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, telefono.getNumTelf());
            stmt.executeUpdate();
            estado = true;
            System.out.println("Inserto el telefono");
        } catch (SQLException ex) {
            System.out.println("Error al agregar telefono " + ex.getMessage());
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
        Telefono telefono = (Telefono) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);

            stmt.setString(1, telefono.getNumTelf());
            stmt.setInt(2, telefono.getIdTelefono());
            stmt.executeUpdate();

            estado = true;
            System.out.println("Actualizo correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al actualizar telefono " + ex.getMessage());
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
     * @param idTelefono int
     * @return boolean
     *
     */
    @Override
    public boolean eliminar(int idTelefono) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE_TRABAJOR);
            stmt.setInt(1, idTelefono);
            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idTelefono);
            stmt.executeUpdate();
            stmt.close();
            estado = true;
            System.out.println("Elimino correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al eliminar Telefono relacionado al trabajador " + ex.getMessage());
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
     * @param idTelefono int
     * @return boolean
     *
     */
    public boolean eliminarCl(int idTelefono) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE_CLIENTE);
            stmt.setInt(1, idTelefono);
            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idTelefono);
            stmt.executeUpdate();
            stmt.close();
            estado = true;
            System.out.println("Elimino correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al eliminar Telefono relacionado al cliente " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;

    }

    /**
     * Metodo que se encargar de buscar objetos de la base de datos
     *
     * @param idTelefono int
     * @return object
     */
    @Override
    public Object buscar(int idTelefono) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);

            stmt.setInt(1, idTelefono);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String numTelf = rs.getString("num_telefono");

                Telefono telefono = new Telefono(idTelefono, numTelf);
                return telefono;
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
