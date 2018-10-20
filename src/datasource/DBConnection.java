package datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {

    // For local setup, uncomment this line or, better, set the environment variable in your run configuration
    private static final String DB_CONNECTION_LOCAL = "jdbc:derby://localhost:1527/eshop;create=true";
    private static final String DB_CONNECTION_REMOTE = System.getenv().get("JDBC_DATABASE_URL");
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "1234";

    public static PreparedStatement prepare(String stm, Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(stm);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return preparedStatement;
    }

    public static Connection getDBConnection() {
        try {
        	String dbUrl = System.getenv("JDBC_DATABASE_URL");
            return DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
              Connection dbConnection = DriverManager.getConnection(DB_CONNECTION_LOCAL, DB_USER, DB_PASSWORD);
              return dbConnection;
            } catch (SQLException e2) {
              System.out.println(e.getMessage());
            }
        }
        System.out.println("Connection problem");
        return null;
    }
}
