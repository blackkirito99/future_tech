package datasource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Customer;
import domain.User;
import domain.Retailer;

public class UserMapper {

  private static final String initialLazyLoadStatementString = "SELECT userID, username, type, avatar, email"
      + " from APP.users" + " where userID = ?";
  private static final String initialLazyLoadALLStatementString = "SELECT userID, username, type, avatar, email"
      + " from APP.users";
  private static final String fullLazyLoadStatementString = "SELECT lastName, firstName, password, address"
      + " from APP.users" + " where userID = ?";
  private static final String updateStatementString = "UPDATE APP.users"
      + " set lastName = ?, firstName = ?, password = ?, email = ?, address = ?" + " WHERE userID = ?";
  private static final String CustomerString = "CU";
  private static final String RetellerString = "RT";

  public static User getUser(int id) {
    User result = Registry.getUser(id);
    if (result != null) {
      return result;
    }
    PreparedStatement findStatement = null;
    ResultSet rs = null;
    try {
      findStatement = DBConnection.prepare(initialLazyLoadStatementString);
      findStatement.setInt(1, id);
      rs = findStatement.executeQuery();
      rs.next();
      result = lazyLoadGhost(rs);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  public static User getFullUser(int id) {
    // execute lazy load first if user does not exist in registry
    User result = Registry.getUser(id);
    if (result == null) {
      getUser(id);
    }
    // then executes full load for remaining fields
    PreparedStatement findStatement = null;
    ResultSet rs = null;
    try {
      findStatement = DBConnection.prepare(fullLazyLoadStatementString);
      findStatement.setInt(1, id);
      rs = findStatement.executeQuery();
      rs.next();
      return fullLoadGhost(rs, id);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static List<User> getAllUsers() {
    List<User> result = new ArrayList<>();
    try {
      PreparedStatement findStatement = DBConnection.prepare(initialLazyLoadALLStatementString);
      ResultSet rs = findStatement.executeQuery();
      while (rs.next()) {
        result.add(lazyLoadGhost(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  private static User lazyLoadGhost(ResultSet rs) throws SQLException {
    int userID = rs.getInt(1);
    User result = Registry.getUser(userID);
    if (result != null) {
      return result;
    }
    String username = rs.getString(2);
    String type = rs.getString(3);
    String avatar = rs.getString(4);
    String email = rs.getString(5);
    if (type.equals(CustomerString)) {
      result = new Customer(userID, username, type, email,avatar, false);
    } else if (type.equals(RetellerString)) {
      result = new Retailer(userID, username, type, email,avatar, false);
    }
    Registry.addUser(result);
    return result;
  }

  private static User fullLoadGhost(ResultSet rs, int id) throws SQLException {
    User user = Registry.getUser(id);
    if (user == null) {
      // error
      return null;
    }
    user.setLastName(rs.getString(1));
    user.setFirstName(rs.getString(2));
    user.setPassword(rs.getString(3));
    user.setEmail(rs.getString(4));
    if (user.getType().equals(CustomerString)) {
      ((Customer) user).setAddress(rs.getString(4));
    } else if (user.getType().equals(RetellerString)) {
      // set retailer attributes
    }
    return user;
  }

  /*
   * public static User getUser(int userId) { User result =
   * Registry.getUser(userId); if(result != null) { return result; }
   * PreparedStatement findStatement= null; ResultSet rs = null; try {
   * findStatement = DBConnection.prepare(findStatementString);
   * findStatement.setInt(1, userId); rs = findStatement.executeQuery();
   * rs.next(); result = load(rs); }catch (SQLException e) {
   *
   * } return result; }
   *
   *
   * public static List<User> getAllUsers() { List<User> result = new
   * ArrayList<>(); try { PreparedStatement findStatement =
   * DBConnection.prepare(findAllStatementString); ResultSet rs =
   * findStatement.executeQuery(); while(rs.next()) { result.add(load(rs)); }
   * }catch (SQLException e) {
   *
   * } return result; } private static User load(ResultSet rs) throws SQLException
   * { int id = rs.getInt(1); User result = Registry.getUser(id); if(result !=
   * null) { return result; } String lastName = rs.getString(2); String firstName
   * = rs.getString(3); String username = rs.getString(4); String password =
   * rs.getString(5); String email = rs.getString(6); String type =
   * rs.getString(7); if(type.equals(CustomerString)) { String address =
   * rs.getString(8); result = new Customer(id, lastName, firstName, username,
   * password, email, type, address); }else if(type.equals(RetellerString)) {
   * /*Reteller for feature B
   */
  // double screenSize = rs.getDouble(9);
  // result = new Smartphone(id, name, brand, category, price, stock, screenSize);
  /*
   * } Registry.addUser(result); return result; }
   */

  public static void update(User user) {
    try {
      PreparedStatement updateStatement = DBConnection.prepare(updateStatementString);
      updateStatement.setString(1, user.getLastName());
      updateStatement.setString(2, user.getFirstName());
      updateStatement.setString(3, user.getPassword());
      updateStatement.setString(4, user.getEmail());
      if (user.getType().equals(CustomerString)) {
        updateStatement.setString(4, ((Customer) user).getAddress());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // feature B
  public static void add(User object) {
    // TODO Auto-generated method stub

  }

  public static void delete(User object) {
    // TODO Auto-generated method stub

  }
}
