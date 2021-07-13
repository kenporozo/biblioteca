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
import modelo.Autor;

/**
 *
 * @author Usuario
 */
public class AutorDAO extends DAO implements IDAO{

    private static final String SQL_INSERT = "INSERT INTO autor(nombre_autor, apellido_autor) VALUES (?, ?)";

    private static final String SQL_SELECT = "SELECT * FROM autor ORDER BY id_autor";

    private static final String SQL_DELETE = "DELETE FROM autor WHERE id_autor = ?";

    private static final String SQL_UPDATE = "UPDATE autor SET nombre_autor = ?, apellido_autor = ? WHERE id_autor = ?";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM autor WHERE id_autor = ?";

    @Override
    public ArrayList<Object> getList() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Object> list  = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id_autor");
                String nombreAutor = rs.getString("nombre_autor");
                String apellidoAutor = rs.getString("apellido_autor");

                if (apellidoAutor == null) {
                    Autor autor = new Autor(id, nombreAutor);
                    list.add(autor);
                } else {

                    Autor autor = new Autor(id, nombreAutor, apellidoAutor);
                    list.add(autor);
                }

            }

        } catch (SQLException ex) {
            System.out.println("Error al listar autores " + ex.getMessage());
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
        Autor autor = (Autor) obj;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);

            stmt.setString(1, autor.getNombreAutor());
            stmt.setString(2, autor.getApellidoPaterno());
            stmt.executeUpdate();
            estado = true;
            System.out.println("Inserto el autor");
        } catch (SQLException ex) {
            System.out.println("Error al agregar autor " + ex.getMessage());

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
        Autor autor = (Autor) obj;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, autor.getNombreAutor());
            stmt.setString(2, autor.getApellidoPaterno());
            stmt.setInt(3, autor.getIdAutor());
            stmt.executeUpdate();
            estado = true;
            System.out.println("Actualizo correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al agregar autor " + ex.getMessage());

        } finally {
            close(stmt);
            close(conn);
        }

        return estado;
    }

    @Override
    public boolean eliminar(int idAutor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idAutor);
            stmt.executeUpdate();
            estado = true;
            System.out.println("Elimino correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al agregar autor " + ex.getMessage());

        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

    @Override
    public Object buscar(int idAutor) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);

            stmt.setInt(1, idAutor);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nombreAutor = rs.getString("nombre_autor");
                String apellidoAutor = rs.getString("apellido_autor");

                if (apellidoAutor != null) {
                    Autor autor = new Autor(idAutor, nombreAutor, apellidoAutor);
                    return autor;
                } else {
                    Autor autor = new Autor(idAutor, nombreAutor);
                    return autor;
                }
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
