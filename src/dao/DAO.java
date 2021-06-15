/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Usuario
 */
public class DAO {

    protected String dbURL;
    protected String dbDatabase;
    protected String dbUser;
    protected String dbPass;

    public DAO() {
    dbURL       = "jdbc:mysql://localhost:3306/";
    dbDatabase  = "biblioteca_leon_jonayker?autoReconnect=true&useSSL=false";
    dbUser      = "root";
    dbPass      = "admin";
  }
    
     protected Connection getConnection() {
    try {
      return DriverManager.getConnection(dbURL + dbDatabase, dbUser, dbPass);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
    
     protected void close(Connection conn) {
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }
     
      protected void close(Statement stmt) {
    if (stmt != null) {
      try {
        stmt.close();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }
  
}
