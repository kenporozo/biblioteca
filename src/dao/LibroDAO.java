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
public class LibroDAO extends DAO {

    private static final String SQL_INSERT = "INSERT INTO libro (isbn, titulo, cant_paginas, fecha_publicacion, precio, id_idioma, id_editorial)VALUES(?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_AUTOR_LIBRO = "INSERT INTO autor_libro (id_libro, id_autor) VALUES(?, ?)";

    private static final String SQL_INSERT_CATEGORIA_LIBRO = "INSERT INTO categoria_libro(id_categoria, id_libro) VALUES(?, ?)";

    private static final String SQL_SELECT = "SELECT * FROM libro ORDER BY id_libro";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM libro WHERE id_libro = ?";

    private static final String SQL_INSERT_INVENTARIO = "INSERT INTO inventario (id_libro, id_estado, id_biblioteca) VALUES (?, ?, ?)";

    private static final String SQL_UPDATE = "UPDATE libro SET isbn = ?, titulo = ?, cant_paginas = ?, fecha_publicacion = ?, precio = ?, id_idioma = ?, id_editorial = ? WHERE id_libro = ? ";

    private static final String SQL_UPDATE_AUTOR_LIBRO = "UPDATE autor_libro SET id_autor = ? WHERE id_libro = ?";

    private static final String SQL_UPDATE_CATEGORIA_LIBRO = "UPDATE categoria_libro SET id_categoria = ? WHERE id_libro = ?";

    private static final String SQL_UPDATE_INVENTARIO = "UPDATE inventario SET id_estado = ? WHERE id_libro = ?";

    public ArrayList<Libro> getList() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Libro> list = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            ResultSet rs = stmt.executeQuery();
            IdiomaDAO idiomaDAO = new IdiomaDAO();
            EditorialDAO editorialDAO = new EditorialDAO();

            while (rs.next()) {
                int idLibro = rs.getInt("id_libro");
                int isbn = rs.getInt("isbn");
                String titulo = rs.getString("titulo");
                int cant_paginas = rs.getInt("cant_paginas");
                String fechaPublicacion = rs.getString("fecha_publicacion");
                int precio = rs.getInt("precio");
                int idIdioma = rs.getInt("id_idioma");
                int idEditorial = rs.getInt("id_editorial");

                Idioma varIdioma = idiomaDAO.buscar(idIdioma);
                Editorial varEditorial = editorialDAO.buscar(idEditorial);

                Libro libro = new Libro(idLibro, isbn, titulo, cant_paginas, fechaPublicacion, precio, varIdioma, varEditorial);

                list.add(libro);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar libros " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }

        return list;
    }

    public boolean insertar(Libro libro, int idAutor, int idCategoria, int idEstado) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, libro.getIsbn());
            stmt.setString(2, libro.getTitulo());
            stmt.setInt(3, libro.getCantidadPaginas());
            stmt.setString(4, libro.getFechaPublicacion());
            stmt.setInt(5, libro.getPrecio());
            stmt.setInt(6, libro.getIdioma().getIdIdioma());
            stmt.setInt(7, libro.getEditorial().getIdEditorial());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);

            libro.setIdLibro(id);
            stmt.close();

            stmt = conn.prepareStatement(SQL_INSERT_AUTOR_LIBRO);
            stmt.setInt(1, id);
            stmt.setInt(2, idAutor);
            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement(SQL_INSERT_CATEGORIA_LIBRO);
            stmt.setInt(1, idCategoria);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement(SQL_INSERT_INVENTARIO);
            stmt.setInt(1, id);
            stmt.setInt(2, idEstado);
            stmt.setInt(3, 1);
            stmt.executeUpdate();
            stmt.close();

            estado = true;
            System.out.println("Inserto el libro");
        } catch (SQLException ex) {
            System.out.println("Error al agregar libro " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

    public boolean actualizar(Libro libro, int idAutor, int idCategoria, int idEstado) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, libro.getIsbn());
            stmt.setString(2, libro.getTitulo());
            stmt.setInt(3, libro.getCantidadPaginas());
            stmt.setString(4, libro.getFechaPublicacion());
            stmt.setInt(5, libro.getPrecio());
            stmt.setInt(6, libro.getIdioma().getIdIdioma());
            stmt.setInt(7, libro.getEditorial().getIdEditorial());
            stmt.setInt(8, 7);
            stmt.executeUpdate();

            /*ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);*/
            //libro.setIdLibro(id);
            stmt.close();

            stmt = conn.prepareStatement(SQL_UPDATE_AUTOR_LIBRO);
            stmt.setInt(1, idAutor);
            stmt.setInt(2, 7);
            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement(SQL_UPDATE_CATEGORIA_LIBRO);
            stmt.setInt(1, idCategoria);
            stmt.setInt(2, 7);
            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement(SQL_UPDATE_INVENTARIO);
            stmt.setInt(1, idEstado);
            stmt.setInt(2, 7);
            stmt.executeUpdate();
            stmt.close();

            estado = true;
            System.out.println("Actualizo el libro");
        } catch (SQLException ex) {
            System.out.println("Error al actualizar libro " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

    public Libro buscar(int idLibro) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {

            IdiomaDAO idiomaDAO = new IdiomaDAO();
            EditorialDAO editorialDAO = new EditorialDAO();
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);

            stmt.setInt(1, idLibro);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                int isbn = rs.getInt("isbn");
                String titulo = rs.getString("titulo");
                int cant_paginas = rs.getInt("cant_paginas");
                String fechaPublicacion = rs.getString("fecha_publicacion");
                int precio = rs.getInt("precio");
                int idIdioma = rs.getInt("id_idioma");
                int idEditorial = rs.getInt("id_editorial");

                Idioma varIdioma = idiomaDAO.buscar(idIdioma);
                Editorial varEditorial = editorialDAO.buscar(idEditorial);

                Libro libro = new Libro(idLibro, isbn, titulo, cant_paginas, fechaPublicacion, precio, varIdioma, varEditorial);
                return libro;

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
