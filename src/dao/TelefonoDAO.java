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
import modelo.Telefono;

/**
 *
 * @author Usuario
 */
public class TelefonoDAO extends DAO implements IDAO{
    
     private static final String SQL_INSERT = "INSERT INTO telefono(num_telefono) VALUES (?)";

    private static final String SQL_SELECT = "SELECT * FROM telefono ORDER BY id_telefono";

    private static final String SQL_DELETE = "DELETE FROM telefono WHERE id_telefono = ?";

    private static final String SQL_UPDATE = "UPDATE telefono SET num_telefono = ? WHERE id_telefono = ?";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM telefono WHERE id_telefono = ?";

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
                Telefono telefono = new Telefono(idTelefono,numTelf);
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

    @Override
    public boolean eliminar(int idTelefono) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idTelefono);
            stmt.executeUpdate();
            estado = true;
            System.out.println("Elimino correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al eliminar Telefono " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    
    }

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
