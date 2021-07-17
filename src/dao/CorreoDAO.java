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
import modelo.Correo;

/**
 *
 * @author Usuario
 */
public class CorreoDAO extends DAO implements IDAO{
    
    private static final String SQL_SELECT = "SELECT * FROM correo ORDER BY id_correo";
    
     private static final String SQL_INSERT = "INSERT INTO correo(correo) VALUES (?)";

    private static final String SQL_INSERT_CLIENT_CORREO = "INSERT INTO rel_correo_cliente (id_cliente, id_correo) VALUES(?, ?)";

    private static final String SQL_INSERT_TRABAJADOR_CORREO = "INSERT INTO rel_correo_trabajador(id_trabajador, id_correo) VALUES (?, ?)";
    
    private static final String SQL_DELETE = "DELETE FROM correo WHERE id_correo = ?";

    private static final String SQL_UPDATE = "UPDATE correo SET correo = ? WHERE id_correo = ?";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM correo WHERE id_correo = ?";


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
                int idCorreo = rs.getInt("id_correo");
                String correo = rs.getString("correo");
                Correo varCorreo = new Correo(idCorreo, correo);
                list.add(varCorreo);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar correos " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }

        return list;
    }
    
    public boolean insertarCorreoTrabajador(int idCorreo, int idTrabajador) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            stmt = conn.prepareStatement(SQL_INSERT_TRABAJADOR_CORREO);
            stmt.setInt(1, idTrabajador);
            stmt.setInt(2, idCorreo);
            stmt.executeUpdate();
            stmt.close();
            estado = true;
            System.out.println("Inserto el correo relacionado al trabajador");
        } catch (SQLException ex) {
            System.out.println("Error al agregar correo " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }

      public boolean insertarCorreoCliente(Correo correo, int idCliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, correo.getCorreo());
            stmt.executeUpdate();
            
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);
            
            stmt.close();

            stmt = conn.prepareStatement(SQL_INSERT_CLIENT_CORREO);
            stmt.setInt(1, idCliente);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt.close();
            estado = true;
            System.out.println("Inserto el correo relacionado al cliente");
        } catch (SQLException ex) {
            System.out.println("Error al agregar telefono " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }
      
        public int insertarID(Correo correo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int id = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, correo.getCorreo());
            stmt.executeUpdate();
            
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            id = keys.getInt(1);
            
            stmt.close();

           
            System.out.println("Inserto el correo");
        } catch (SQLException ex) {
            System.out.println("Error al agregar correo " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return id;
    }

    @Override
    public boolean insertar(Object obj) {
       Connection conn = null;
        PreparedStatement stmt = null;
        Boolean estado = false;
        Correo correo = (Correo) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, correo.getCorreo());
            stmt.executeUpdate();
            estado = true;
            System.out.println("Inserto el correo");
        } catch (SQLException ex) {
            System.out.println("Error al agregar correo " + ex.getMessage());
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
        Correo correo = (Correo) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);

            stmt.setString(1, correo.getCorreo());
            stmt.setInt(2, correo.getIdCorreo());
            stmt.executeUpdate();

            estado = true;
            System.out.println("Actualizo correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al actualizar correo " + ex.getMessage());
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
            System.out.println("Elimino correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al eliminar correo " + ex.getMessage());
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
                String correo = rs.getString("correo");

                Correo varCorreo = new Correo(id, correo);
                return varCorreo;
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
