package datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {
  
  // For local setup, uncomment this line or, better, set the environment variable
  // in your run configuration
  private static final String DB_CONNECTION = "jdbc:derby://localhost:1527/eshop;create=true";
  // private static final String DB_CONNECTION =
  // System.getenv().get("JDBC_DATABASE_URL");
  private static final String DB_USER = "user";
  private static final String DB_PASSWORD = "1234";

  public static PreparedStatement prepare(String stm) {
    PreparedStatement preparedStatement = null;
    try {
      Connection dbConnection = getDBConnection();
      preparedStatement = dbConnection.prepareStatement(stm);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return preparedStatement;
  }

  private static Connection getDBConnection() {
    try {
      DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
      Connection dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
      return dbConnection;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    System.out.println("Connection problem");
    return null;
  }
}
