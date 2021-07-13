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
import java.util.ArrayList;
import modelo.Editorial;

/**
 *
 * @author Usuario
 */
public class EditorialDAO extends DAO implements IDAO{

    private static final String SQL_INSERT = "INSERT INTO editorial(nombre_editorial) VALUES (?)";

    private static final String SQL_SELECT = "SELECT * FROM editorial ORDER BY id_editorial";

    private static final String SQL_DELETE = "DELETE FROM editorial WHERE id_editorial = ?";

    private static final String SQL_UPDATE = "UPDATE editorial SET nombre_editorial = ? WHERE id_editorial = ?";
    
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM editorial WHERE id_editorial = ?";

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
                int idEditorial = rs.getInt("id_editorial");
                String nombreEditorial = rs.getString("nombre_editorial");
                Editorial editorial = new Editorial(idEditorial,nombreEditorial);
                list.add(editorial);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar editoriales " + ex.getMessage());
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
        Editorial editorial = (Editorial) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, editorial.getNombreEditorial());
            stmt.executeUpdate();
            estado = true;
            System.out.println("Inserto la editorial");
        } catch (SQLException ex) {
            System.out.println("Error al agregar editorial " + ex.getMessage());
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
        Editorial editorial = (Editorial) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            
            stmt.setString(1, editorial.getNombreEditorial());
            stmt.setInt(2, editorial.getIdEditorial());
            stmt.executeUpdate();
            
            estado = true;
            System.out.println("Actualizo correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al actualizar editorial " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }
    
    @Override
    public boolean eliminar(int idEditorial) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idEditorial);
            stmt.executeUpdate();
            estado = true;
            System.out.println("Elimino correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al eliminar editorial " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }
    
    @Override
    public Editorial buscar(int idEditorial) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);

            stmt.setInt(1, idEditorial);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nombreEditorial = rs.getString("nombre_editorial");

                Editorial editorial = new Editorial(idEditorial, nombreEditorial);
                return editorial;
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
