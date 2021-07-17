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
public class DireccionDAO extends DAO implements IDAO{
    
    private static final String SQL_INSERT = "INSERT INTO direccion(direccion) VALUES (?)";
    
    private static final String SQL_INSERT_CLIENT_DIRECCION = "INSERT INTO rel_direcc_cliente (id_cliente, id_direccion) VALUES(?, ?)";

    private static final String SQL_INSERT_TRABAJADOR_DIRECCION = "INSERT INTO rel_direcc_trabajador(id_trabajador, id_direccion) VALUES (?, ?)";

    private static final String SQL_SELECT = "SELECT * FROM direccion ORDER BY id_direccion";

    private static final String SQL_DELETE = "DELETE FROM direccion WHERE id_direccion = ?";

    private static final String SQL_UPDATE = "UPDATE direccion SET direccion = ? WHERE id_direccion = ?";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM direccion WHERE id_direccion = ?";
    
    
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
    
    public boolean insertarDireccionTrabajador(int idDireccion, int idTrabajador) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
          
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
    
    @Override
    public boolean eliminar(int idDireccion) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idDireccion);
            stmt.executeUpdate();
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
