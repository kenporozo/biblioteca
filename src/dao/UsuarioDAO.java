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
import modelo.Trabajador;
import modelo.Usuario;

/**
 *
 * @author Usuario
 */
public class UsuarioDAO extends DAO implements IDAO{
    
    private static final String SQL_INSERT = "INSERT INTO usuario (id_trabajador, usuario, clave, id_estado) VALUES(?, ?, ?, ?)";
    private static final String SQL_SELECT = "SELECT * FROM usuario ORDER BY id_usuario";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM usuario WHERE id_usuario = ?";
    private static final String SQL_UPDATE = "UPDATE usuario SET usuario = ?, clave = ? WHERE id_usuario = ?";
    private static final String SQL_DELETE = "UPDATE usuario SET id_estado = 2 WHERE id_usuario = ?";
    private static final String SQL_ACTIVAR = "UPDATE usuario SET id_estado = 1 WHERE id_usuario = ?";
    private static final String SQL_LOGIN = "SELECT * FROM usuario WHERE usuario = ? and clave = ? and id_estado = 1";

    @Override
    public ArrayList<Object> getList() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Object> list = new ArrayList<>();
        TrabajadorDAO tDAO = new TrabajadorDAO();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                int idTrabajador = rs.getInt("id_trabajador");
                String usu = rs.getString("usuario");
                String clave = rs.getString("clave");
                int idEstado = rs.getInt("id_estado");
                
                Trabajador trabajador = (Trabajador) tDAO.buscar(idTrabajador);
                
                Usuario usuario = new Usuario(idUsuario, trabajador, usu, clave, idEstado);
                list.add(usuario);
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
        Usuario usuario = (Usuario) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setInt(1, usuario.getTrabajador().getIdTrabajador());
            stmt.setString(2, usuario.getUsuario());
            stmt.setString(3, usuario.getClave());
            stmt.setInt(4, usuario.getEstado());
            stmt.executeUpdate();
            estado = true;
            System.out.println("Inserto el usuario");
        } catch (SQLException ex) {
            System.out.println("Error al agregar usuario " + ex.getMessage());
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
        Usuario usuario = (Usuario) obj;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);

            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getClave());
            stmt.setInt(3, usuario.getIdUsuario());
            stmt.executeUpdate();

            estado = true;
            System.out.println("Actualizo correctamente");
        } catch (SQLException ex) {
            System.out.println("Error al actualizar usuario " + ex.getMessage());
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
            System.out.println("Error al desactivar usuario " + ex.getMessage());
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
        TrabajadorDAO tDAO = new TrabajadorDAO();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                int idTrabajador = rs.getInt("id_trabajador");
                String usu = rs.getString("usuario");
                String clave = rs.getString("clave");
                int idEstado = rs.getInt("id_estado");
                
                Trabajador trabajador = (Trabajador) tDAO.buscar(idTrabajador);
                
                Usuario usuario = new Usuario(idUsuario, trabajador, usu, clave, idEstado);
                return usuario;
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
    
    public Usuario login(String usuario, String clave) {
        Connection conn = null;
        PreparedStatement stmt = null;
        TrabajadorDAO tDAO = new TrabajadorDAO();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_LOGIN);

            stmt.setString(1, usuario);
            stmt.setString(2, clave);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                int idTrabajador = rs.getInt("id_trabajador");
                
                Trabajador trabajador = (Trabajador) tDAO.buscar(idTrabajador);
                
                Usuario user = new Usuario(idUsuario, trabajador, usuario, clave, 1);
                return user;
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
            System.out.println("Actualizo el estado del usuario");
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el estado del usuario " + ex.getMessage());
        } finally {
            close(stmt);
            close(conn);
        }
        return estado;
    }
    
}
