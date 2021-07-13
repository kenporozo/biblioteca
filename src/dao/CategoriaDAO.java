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
import modelo.Categoria;

/**
 *
 * @author Usuario
 */
public class CategoriaDAO extends DAO implements IDAO{

    private static final String SQL_INSERT = "INSERT INTO categoria(categoria) VALUES (?)";

    private static final String SQL_SELECT = "SELECT * FROM categoria ORDER BY id_categoria";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM categoria WHERE id_categoria = ?";

    private static final String SQL_DELETE = "DELETE FROM categoria WHERE id_categoria = ?";

    private static final String SQL_UPDATE = "UPDATE categoria SET categoria = ? WHERE id_categoria = ?";

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
                int idCategoria = rs.getInt("id_categoria");
                String categoria = rs.getString("categoria");

                Categoria cat = new Categoria(idCategoria, categoria);
                list.add(cat);
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
        Categoria categoria = (Categoria) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, categoria.getNombreCategoria());
            stmt.executeUpdate();
            estado = true;
            System.out.println("Inserto la categoria");
        } catch (SQLException ex) {
            System.out.println("Error al agregar categoria " + ex.getMessage());
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
        Categoria categoria = (Categoria) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);

            stmt.setString(1, categoria.getNombreCategoria());
            stmt.setInt(2, categoria.getIdCategoria());
            stmt.executeUpdate();

            estado = true;
            System.out.println("Actualizo correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al actualizar categoria " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

    @Override
    public boolean eliminar(int idCategoria) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idCategoria);
            stmt.executeUpdate();
            estado = true;
            System.out.println("Elimino correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al eliminar categoria " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

    @Override
    public Categoria buscar(int idCategoria) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);

            stmt.setInt(1, idCategoria);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String categoria = rs.getString("categoria");

                Categoria cat = new Categoria(idCategoria, categoria);
                return cat;
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
