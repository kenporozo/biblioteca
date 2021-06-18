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
public class DistribuidorDAO extends DAO{

    private static final String SQL_INSERT = "INSERT INTO distribuidor(rut_distribuidor, id_direccion, id_telefono, fecha_ini_laboral, id_entidad_estado) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_SELECT = "SELECT * FROM distribuidor ORDER BY id_distribuidor";

    private static final String SQL_UPDATE = "UPDATE distribuidor SET rut_distribuidor = ?, id_direccion = ?, id_telefono = ?, fecha_ini_laboral = ?, id_entidad_estado = ? WHERE id_distribuidor = ?";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM distribuidor WHERE id_distribuidor = ?";

    
    public ArrayList<Distribuidor> getList() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Distribuidor> list = new ArrayList<>();

        try {
            conn = getConnection();
        stmt = conn.prepareStatement(SQL_SELECT);
            ResultSet rs = stmt.executeQuery();
            TelefonoDAO telfDAO = new TelefonoDAO();
            DireccionDAO direcDAO = new DireccionDAO();

            while (rs.next()) {
                int idDistribuidor = rs.getInt("id_distribuidor");
                String rut = rs.getString("rut_distribuidor");
                int idDirec = rs.getInt("id_direccion");
                int idTelf = rs.getInt("id_telefono");
                String fechaLaboral = rs.getString("fecha_ini_laboral");
                int estado = rs.getInt("id_entidad_estado");

                Telefono telefono = (Telefono) telfDAO.buscar(idTelf);
                Direccion direccion = (Direccion) direcDAO.buscar(idDirec);

                Distribuidor distribuidor = new Distribuidor(idDistribuidor, rut, telefono, direccion, fechaLaboral,estado);

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


    public boolean insertar(Distribuidor distribuidor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
     
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, distribuidor.getRutDistribuidor());
            stmt.setInt(2, distribuidor.getDireccion().getIdDireccion());
            stmt.setInt(3, distribuidor.getTelefono().getIdTelefono());
            stmt.setString(4, distribuidor.getFechaLaboral());
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


    public boolean modificar(int idDistribuidor, Distribuidor distribuidor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, distribuidor.getRutDistribuidor());
            stmt.setInt(2, distribuidor.getDireccion().getIdDireccion());
            stmt.setInt(3, distribuidor.getTelefono().getIdTelefono());
            stmt.setString(4, distribuidor.getFechaLaboral());
            stmt.setInt(5, distribuidor.getEstado());
            stmt.setInt(6, idDistribuidor);
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
                String rut = rs.getString("rut_distribuidor");
                int idDirec = rs.getInt("id_direccion");
                int idTelf = rs.getInt("id_telefono");
                String fechaLaboral = rs.getString("fecha_ini_laboral");
                int estado = rs.getInt("id_entidad_estado");

                Telefono telefono = (Telefono) telfDAO.buscar(idTelf);
                Direccion direccion = (Direccion) direcDAO.buscar(idDirec);

               Distribuidor distribuidor = new Distribuidor(idDistribuidor, rut, telefono, direccion, fechaLaboral, estado);
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

}
