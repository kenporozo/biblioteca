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
public class DistribuidorDAO extends DAO implements IDAO {

    private static final String SQL_INSERT = "INSERT INTO distribuidor(empresa, rut_distribuidor, id_direccion, id_telefono, fecha_ini_laboral, id_entidad_estado) VALUES (?, ?, ?, ?, CURDATE(), ?)";

    private static final String SQL_SELECT = "SELECT * FROM distribuidor ORDER BY id_distribuidor";

    private static final String SQL_UPDATE = "UPDATE distribuidor SET empresa = ?, rut_distribuidor = ? WHERE id_distribuidor = ?";

    private static final String SQL_DELETE = "UPDATE distribuidor SET id_entidad_estado = 2 WHERE id_distribuidor = ?";

    private static final String SQL_ACTIVAR = "UPDATE distribuidor SET id_entidad_estado = 1 WHERE id_distribuidor = ?";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM distribuidor WHERE id_distribuidor = ?";

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
            TelefonoDAO telfDAO = new TelefonoDAO();
            DireccionDAO direcDAO = new DireccionDAO();

            while (rs.next()) {
                int idDistribuidor = rs.getInt("id_distribuidor");
                String empresa = rs.getString("empresa");
                String rut = rs.getString("rut_distribuidor");
                int idDirec = rs.getInt("id_direccion");
                int idTelf = rs.getInt("id_telefono");
                String fechaLaboral = rs.getString("fecha_ini_laboral");
                int estado = rs.getInt("id_entidad_estado");

                Telefono telefono = (Telefono) telfDAO.buscar(idTelf);
                Direccion direccion = (Direccion) direcDAO.buscar(idDirec);

                Distribuidor distribuidor = new Distribuidor(idDistribuidor, empresa, rut, telefono, direccion, fechaLaboral, estado);

                list.add(distribuidor);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar distribuidores " + ex.getMessage());
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
     * @param obj objeto
     * @return Devuelve un booelean
     *
     */
    @Override
    public boolean insertar(Object obj) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        Distribuidor distribuidor = (Distribuidor) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, distribuidor.getNombreDis());
            stmt.setString(2, distribuidor.getRutDistribuidor());
            stmt.setInt(3, distribuidor.getDireccion().getIdDireccion());
            stmt.setInt(4, distribuidor.getTelefono().getIdTelefono());
            stmt.setInt(5, distribuidor.getEstado());
            stmt.executeUpdate();
            estado = true;
            System.out.println("Inserto el distribuidor");
        } catch (SQLException ex) {
            System.out.println("Error al agregar distribuidor " + ex.getMessage());
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
        Distribuidor distribuidor = (Distribuidor) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, distribuidor.getNombreDis());
            stmt.setString(2, distribuidor.getRutDistribuidor());
            stmt.setInt(3, distribuidor.getIdDistribuidor());
            stmt.executeUpdate();

            estado = true;
            System.out.println("Actualizo correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al actualizar distribuidor " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

    /**
     * Metodo que se encargar de buscar objetos de la base de datos
     *
     * @param idDistribuidor int
     * @return object
     */
    @Override
    public Distribuidor buscar(int idDistribuidor) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            TelefonoDAO telfDAO = new TelefonoDAO();
            DireccionDAO direcDAO = new DireccionDAO();
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);

            stmt.setInt(1, idDistribuidor);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String empresa = rs.getString("empresa");
                String rut = rs.getString("rut_distribuidor");
                int idDirec = rs.getInt("id_direccion");
                int idTelf = rs.getInt("id_telefono");
                String fechaLaboral = rs.getString("fecha_ini_laboral");
                int estado = rs.getInt("id_entidad_estado");

                Telefono telefono = (Telefono) telfDAO.buscar(idTelf);
                Direccion direccion = (Direccion) direcDAO.buscar(idDirec);

                Distribuidor distribuidor = new Distribuidor(idDistribuidor, empresa, rut, telefono, direccion, fechaLaboral, estado);
                return distribuidor;
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
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            estado = true;
            System.out.println("Desactivo correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al desactivar distribuidor " + ex.getMessage());
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
            System.out.println("Actualizo el estado del distribuidor");
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el estado del distribuidor " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

}
