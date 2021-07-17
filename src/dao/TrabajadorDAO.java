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
import modelo.Trabajador;

/**
 *
 * @author Usuario
 */
public class TrabajadorDAO extends DAO implements IDAO{
    
    private static final String SQL_INSERT_TRABAJADOR_CORREO = "INSERT INTO rel_correo_trabajador(id_trabajador, id_correo) VALUES (?, ?)";
    private static final String SQL_INSERT_TRABAJADOR_DIRECCION = "INSERT INTO rel_direcc_trabajador(id_trabajador, id_direccion) VALUES (?, ?)";
    private static final String SQL_INSERT_TRABAJADOR_TLF = "INSERT INTO rel_tlf_trabajador(id_trabajador, id_telefono) VALUES (?, ?)";
    private static final String SQL_INSERT = "INSERT INTO trabajador (id_biblioteca, rut_trabajador, nombre_trabajador, apellido_trabajador, id_estado_entidad, fecha_ini_laboral) VALUES(?, ?, ?, ?, 1, CURDATE())";
    private static final String SQL_SELECT = "SELECT * FROM trabajador ORDER BY id_trabajador";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM trabajador WHERE id_trabajador = ?";
    private static final String SQL_ACTIVAR = "UPDATE trabajador SET id_estado_entidad = 1 WHERE id_trabajador = ?";
    private static final String SQL_UPDATE = "UPDATE trabajador SET rut_trabajador = ?, nombre_trabajador = ?, apellido_trabajador = ? WHERE id_trabajador = ?";
    private static final String SQL_DELETE = "UPDATE trabajador SET id_estado_entidad = 2 WHERE id_trabajador = ?";

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
                int idTrabajador = rs.getInt("id_trabajador");
                String rut = rs.getString("rut_trabajador");
                String nombre = rs.getString("nombre_trabajador");
                String apellido = rs.getString("apellido_trabajador");
                int estado = rs.getInt("id_estado_entidad");
                String fecha = rs.getString("fecha_ini_laboral");

                Trabajador tbj = new Trabajador(idTrabajador, rut, nombre, apellido, fecha, estado);
                list.add(tbj);
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
        Trabajador trabajador = (Trabajador) obj;
        TelefonoDAO tDAO = new TelefonoDAO();
        CorreoDAO cDAO = new CorreoDAO();
        DireccionDAO dDAO = new DireccionDAO();
        try {
            //conn.setAutoCommit(false);
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT,  Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, trabajador.getIdBiblioteca());
            stmt.setString(2, trabajador.getRut());
            stmt.setString(3, trabajador.getNombre());
            stmt.setString(4, trabajador.getApellido());
            stmt.executeUpdate();
            
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);
            stmt.close();
            
            int idTelefono = tDAO.insertarID(trabajador.getTelefono());
            int idDireccion = dDAO.insertarID(trabajador.getDireccion());
            int idCorreo = cDAO.insertarID(trabajador.getCorreo());
            
            if(idTelefono > 0 && idDireccion > 0 && idCorreo > 0 && id > 0){
            
            stmt = conn.prepareStatement(SQL_INSERT_TRABAJADOR_TLF);
            stmt.setInt(1, id);
            stmt.setInt(2, idTelefono);
            stmt.executeUpdate();
            stmt.close();
            System.out.println("Inserto el telefono relacionado al trabajador");
            
            stmt = conn.prepareStatement(SQL_INSERT_TRABAJADOR_CORREO);
            stmt.setInt(1, id);
            stmt.setInt(2, idCorreo);
            stmt.executeUpdate();
            stmt.close();
            System.out.println("Inserto el correo relacionado al trabajador");
            
            stmt = conn.prepareStatement(SQL_INSERT_TRABAJADOR_DIRECCION);
            stmt.setInt(1, id);
            stmt.setInt(2, idDireccion);
            stmt.executeUpdate();
            stmt.close();
            System.out.println("Inserto la direccion relacionada al trabajador");
  
            estado = true;
            System.out.println("Inserto el trabajador");
            //conn.commit();
            }else{
                conn.rollback();
            }
        } catch (SQLException ex) {
            if(conn != null){
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    System.out.println("Error, se hizo rollback " + ex1);
                }
            }
            System.out.println("Error al agregar trabajador " + ex.getMessage());
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
        Trabajador trabajador = (Trabajador) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);

            stmt.setString(1, trabajador.getRut());
            stmt.setString(2, trabajador.getNombre());
            stmt.setString(3, trabajador.getApellido());
            stmt.setInt(4, trabajador.getIdTrabajador());
            stmt.executeUpdate();

            estado = true;
            System.out.println("Actualizo correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al actualizar trabajador " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

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
            System.out.println("Error al desactivar trabajador " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

    @Override
    public Object buscar(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String rut = rs.getString("rut_trabajador");
                String nombre = rs.getString("nombre_trabajador");
                String apellido = rs.getString("apellido_trabajador");
                int estado = rs.getInt("id_estado_entidad");
                String fecha = rs.getString("fecha_ini_laboral");
                Trabajador tbj = new Trabajador(id, rut, nombre, apellido, fecha, estado);
                return tbj;
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
    
    public boolean activar(int id){
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
            System.out.println("Actualizo el estado del trabajador");
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el estado del trabajador " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }
}
