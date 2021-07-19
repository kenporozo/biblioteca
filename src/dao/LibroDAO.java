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
public class LibroDAO extends DAO implements IDAO {

    private static final String SQL_INSERT = "INSERT INTO libro (isbn, titulo, cant_paginas, fecha_publicacion, precio, id_idioma, id_editorial)VALUES(?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_AUTOR_LIBRO = "INSERT INTO autor_libro (id_libro, id_autor) VALUES(?, ?)";

    private static final String SQL_INSERT_CATEGORIA_LIBRO = "INSERT INTO categoria_libro(id_categoria, id_libro) VALUES(?, ?)";

    private static final String SQL_SELECT = "SELECT l.id_libro, l.isbn, l.titulo, l.cant_paginas, l.fecha_publicacion, l.precio, i.id_idioma, e.id_editorial, c.id_categoria, a.id_autor, es.id_estado FROM libro AS l INNER JOIN idioma AS i ON l.id_idioma = i.id_idioma INNER JOIN editorial AS e ON l.id_editorial = e.id_editorial INNER JOIN autor_libro AS al ON l.id_libro = al.id_libro INNER JOIN autor AS a ON al.id_autor = a.id_autor INNER JOIN categoria_libro AS cl ON l.id_libro = cl.id_libro INNER JOIN categoria AS c ON cl.id_categoria = c.id_categoria INNER JOIN inventario AS inv ON l.id_libro = inv.id_libro INNER JOIN estado AS es ON inv.id_estado = es.id_estado ORDER BY l.id_libro";

    private static final String SQL_SELECT_BY_ID = "SELECT l.id_libro, l.isbn, l.titulo, l.cant_paginas, l.fecha_publicacion, l.precio, i.id_idioma, e.id_editorial, c.id_categoria, a.id_autor, es.id_estado FROM libro AS l INNER JOIN idioma AS i ON l.id_idioma = i.id_idioma INNER JOIN editorial AS e ON l.id_editorial = e.id_editorial INNER JOIN autor_libro AS al ON l.id_libro = al.id_libro INNER JOIN autor AS a ON al.id_autor = a.id_autor INNER JOIN categoria_libro AS cl ON l.id_libro = cl.id_libro INNER JOIN categoria AS c ON cl.id_categoria = c.id_categoria INNER JOIN inventario AS inv ON l.id_libro = inv.id_libro INNER JOIN estado AS es ON inv.id_estado = es.id_estado WHERE l.id_libro = ?";

    private static final String SQL_INSERT_INVENTARIO = "INSERT INTO inventario (id_libro, id_estado, id_biblioteca) VALUES (?, ?, ?)";

    private static final String SQL_UPDATE = "UPDATE libro SET isbn = ?, titulo = ?, cant_paginas = ?, fecha_publicacion = ?, precio = ?, id_idioma = ?, id_editorial = ? WHERE id_libro = ? ";

    private static final String SQL_UPDATE_AUTOR_LIBRO = "UPDATE autor_libro SET id_autor = ? WHERE id_libro = ?";

    private static final String SQL_UPDATE_CATEGORIA_LIBRO = "UPDATE categoria_libro SET id_categoria = ? WHERE id_libro = ?";

    private static final String SQL_UPDATE_INVENTARIO = "UPDATE inventario SET id_estado = ? WHERE id_libro = ?";

    private static final String SQL_DELETE = "UPDATE inventario SET id_estado = 4 WHERE id_libro = ?";

    private static final String SQL_ACTIVAR = "UPDATE inventario SET id_estado = 3 WHERE id_libro = ?";

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
            IdiomaDAO idiomaDAO = new IdiomaDAO();
            EditorialDAO editorialDAO = new EditorialDAO();
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            AutorDAO autorDAO = new AutorDAO();

            while (rs.next()) {
                int idLibro = rs.getInt("l.id_libro");
                int isbn = rs.getInt("l.isbn");
                String titulo = rs.getString("l.titulo");
                int cant_paginas = rs.getInt("l.cant_paginas");
                String fechaPublicacion = rs.getString("l.fecha_publicacion");
                int precio = rs.getInt("l.precio");
                int idIdioma = rs.getInt("i.id_idioma");
                int idEditorial = rs.getInt("e.id_editorial");
                int idCategoria = rs.getInt("c.id_categoria");
                int idAutor = rs.getInt("a.id_autor");
                int estado = rs.getInt("es.id_estado");

                Idioma varIdioma = idiomaDAO.buscar(idIdioma);
                Editorial varEditorial = editorialDAO.buscar(idEditorial);
                Categoria varCategoria = categoriaDAO.buscar(idCategoria);
                Autor varAutor = (Autor) autorDAO.buscar(idAutor);

                Libro libro = new Libro(idLibro, isbn, titulo, cant_paginas, fechaPublicacion, precio, varIdioma, varEditorial, varCategoria, varAutor, estado);

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

    /**
     * Metodo cuya funcionalidad es agregar objetos a la base de datos
     * dependiendo de su clase
     *
     * @param obj object
     * @return Devuelve un booelean
     *
     */
    @Override
    public boolean insertar(Object obj) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        Libro libro = (Libro) obj;
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

            // libro.setIdLibro(id);
            stmt.close();

            stmt = conn.prepareStatement(SQL_INSERT_AUTOR_LIBRO);
            stmt.setInt(1, id);
            stmt.setInt(2, libro.getAutor().getIdAutor());
            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement(SQL_INSERT_CATEGORIA_LIBRO);
            stmt.setInt(1, libro.getCategoria().getIdCategoria());
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement(SQL_INSERT_INVENTARIO);
            stmt.setInt(1, id);
            stmt.setInt(2, libro.getEstado());
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

    /**
     * Metodo encargado de modificar objetos en la base de datos
     *
     * @param obj object
     * @return boolean
     */
    @Override
    public boolean modificar(Object obj) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        Libro libro = (Libro) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setInt(1, libro.getIsbn());
            stmt.setString(2, libro.getTitulo());
            stmt.setInt(3, libro.getCantidadPaginas());
            stmt.setString(4, libro.getFechaPublicacion());
            stmt.setInt(5, libro.getPrecio());
            stmt.setInt(6, libro.getIdioma().getIdIdioma());
            stmt.setInt(7, libro.getEditorial().getIdEditorial());
            stmt.setInt(8, libro.getIdLibro());
            stmt.executeUpdate();

            stmt.close();

            stmt = conn.prepareStatement(SQL_UPDATE_AUTOR_LIBRO);
            stmt.setInt(1, libro.getAutor().getIdAutor());
            stmt.setInt(2, libro.getIdLibro());
            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement(SQL_UPDATE_CATEGORIA_LIBRO);
            stmt.setInt(1, libro.getCategoria().getIdCategoria());
            stmt.setInt(2, libro.getIdLibro());
            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement(SQL_UPDATE_INVENTARIO);
            stmt.setInt(1, libro.getEstado());
            stmt.setInt(2, libro.getIdLibro());
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

    /**
     * Metodo que se encargar de buscar objetos de la base de datos
     *
     * @param idLibro int 
     * @return object
     */
    @Override
    public Libro buscar(int idLibro) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {

            IdiomaDAO idiomaDAO = new IdiomaDAO();
            EditorialDAO editorialDAO = new EditorialDAO();
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            AutorDAO autorDAO = new AutorDAO();

            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);

            stmt.setInt(1, idLibro);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                int isbn = rs.getInt("l.isbn");
                String titulo = rs.getString("l.titulo");
                int cant_paginas = rs.getInt("l.cant_paginas");
                String fechaPublicacion = rs.getString("l.fecha_publicacion");
                int precio = rs.getInt("l.precio");
                int idIdioma = rs.getInt("i.id_idioma");
                int idEditorial = rs.getInt("e.id_editorial");
                int idCategoria = rs.getInt("c.id_categoria");
                int idAutor = rs.getInt("a.id_autor");
                int estado = rs.getInt("es.id_estado");

                Idioma varIdioma = idiomaDAO.buscar(idIdioma);
                Editorial varEditorial = editorialDAO.buscar(idEditorial);
                Categoria varCategoria = categoriaDAO.buscar(idCategoria);
                Autor varAutor = (Autor) autorDAO.buscar(idAutor);

                Libro libro = new Libro(idLibro, isbn, titulo, cant_paginas, fechaPublicacion, precio, varIdioma, varEditorial, varCategoria, varAutor, estado);
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
        boolean estado = false;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
            estado = true;
            System.out.println("Actualizo el estado del libro");
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el estado del libro " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

    /**
     * Metodo que sirve para activar objetos de la base de datos
     *
     * @param id int
     * @return boolean
     */
    public boolean activar(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean estado = false;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_ACTIVAR);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
            estado = true;
            System.out.println("Actualizo el estado del libro");
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el estado del libro " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }
}
