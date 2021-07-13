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
public class DireccionDAO extends DAO implements IDAO{
    
    private static final String SQL_INSERT = "INSERT INTO direccion(direccion) VALUES (?)";

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
