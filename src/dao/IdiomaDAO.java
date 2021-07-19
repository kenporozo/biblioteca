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
import modelo.Idioma;

/**
 *
 * @author Usuario
 */
public class IdiomaDAO extends DAO implements IDAO {

    private static final String SQL_INSERT = "INSERT INTO idioma(idioma) VALUES (?)";

    private static final String SQL_SELECT = "SELECT * FROM idioma ORDER BY id_idioma";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM idioma WHERE id_idioma = ?";

    private static final String SQL_DELETE = "DELETE FROM idioma WHERE id_idioma = ?";

    private static final String SQL_UPDATE = "UPDATE idioma SET idioma = ? WHERE id_idioma = ?";

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
                int idIdioma = rs.getInt("id_idioma");
                String idioma = rs.getString("idioma");
                Idioma idi = new Idioma(idIdioma, idioma);
                list.add(idi);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar idiomas " + ex.getMessage());
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
     * @param obj obejto
     * @return Devuelve un booelean
     *
     */
    @Override
    public boolean insertar(Object obj) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        Idioma idioma = (Idioma) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, idioma.getIdioma());
            stmt.executeUpdate();
            estado = true;
            System.out.println("Inserto el idioma");
        } catch (SQLException ex) {
            System.out.println("Error al agregar idioma " + ex.getMessage());
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
        Idioma idioma = (Idioma) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);

            stmt.setString(1, idioma.getIdioma());
            stmt.setInt(2, idioma.getIdIdioma());
            stmt.executeUpdate();

            estado = true;
            System.out.println("Actualizo correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al actualizar idioma " + ex.getMessage());
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
     * @param idIdioma int
     * @return boolean
     *
     */
    @Override
    public boolean eliminar(int idIdioma) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idIdioma);
            stmt.executeUpdate();
            estado = true;
            System.out.println("Elimino correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al eliminar idioma " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

    /**
     * Metodo que se encargar de buscar objetos de la base de datos
     *
     * @param idIdioma int
     * @return object
     */
    @Override
    public Idioma buscar(int idIdioma) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);

            stmt.setInt(1, idIdioma);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String idioma = rs.getString("idioma");

                Idioma varIdioma = new Idioma(idIdioma, idioma);
                return varIdioma;
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
