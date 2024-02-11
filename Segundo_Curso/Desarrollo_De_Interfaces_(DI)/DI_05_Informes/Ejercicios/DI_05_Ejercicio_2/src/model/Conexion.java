package model;

import java.sql.*;

/**
 *
 * @author MONTENEGRO
 */
public class Conexion {

  Connection con;

  private final String URL = "jdbc:mysql://localhost:3306/fabrica?serverTimezone=UTC";
  private final String USER = "root";
  private final String PASSWORD = "";

  public Connection getConnection() {
    try {
      con = DriverManager.getConnection(URL, USER, PASSWORD);
      System.out.println("Conexion exitosa");
      return con;

    } catch (SQLException e) {
      System.out.println("Error en la conexion");
      System.out.println(e.toString());
    }
    return null;
  }
}
