package datasource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import domain.CartItem;

public class CartMapper {

  private final static String findCartOfUser = "SELECT * " + "  FROM APP.cartItems " + "  WHERE customerID = ?";
  private final static String finditemOfUser = "SELECT * " + "  FROM APP.cartItems "
      + "  WHERE customerID = ? AND productID = ?";
  private static final String updateQuantityString = "UPDATE APP.cartItems " + "  SET quantity = ? "
      + "  WHERE customerID = ? AND productID = ?";
  private static final String insertStatementString = "INSERT INTO APP.cartItems VALUES (?, ?, ?)";
  private static final String deleteStatementString = "DELETE FROM APP.cartItems"
      + "  WHERE customerID = ? AND productID = ?";
  private static int customerId;

  public static List<CartItem> findCartOf(int id) {
    customerId = id;
    List<CartItem> result = new ArrayList<CartItem>();
    try {
      PreparedStatement findStatement = DBConnection.prepare(findCartOfUser);
      findStatement.setInt(1, id);
      ResultSet rs = findStatement.executeQuery();
      while (rs.next()) {
        result.add(load(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  // return single pair map
  public CartItem findItem(int userID, int productID) {
    try {
      PreparedStatement findStatement = DBConnection.prepare(finditemOfUser);
      findStatement.setInt(1, userID);
      findStatement.setInt(2, productID);
      ResultSet rs = findStatement.executeQuery();
      if (rs.next()) {
        return load(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void add(CartItem object) {
    try {
      // update quantity in database
      PreparedStatement insertStatement = DBConnection.prepare(insertStatementString);
      insertStatement.setInt(1, object.getCustomerID());
      insertStatement.setInt(2, object.getProductID());
      insertStatement.setInt(3, object.getQuantity());
      insertStatement.execute();

      // update from memory should shouldn't need to update register
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void update(CartItem object) {
    try {
      // update quantity in database
      PreparedStatement updateStatement = DBConnection.prepare(updateQuantityString);
      updateStatement.setInt(1, object.getQuantity());
      updateStatement.setInt(2, object.getCustomerID());
      updateStatement.setInt(3, object.getProductID());
      updateStatement.execute();

      // update from memory should shouldn't need to update register
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void delete(CartItem object) {
    try {
      // update quantity in database
      PreparedStatement deleteStatement = DBConnection.prepare(deleteStatementString);
      deleteStatement.setInt(1, object.getCustomerID());
      deleteStatement.setInt(2, object.getProductID());
      deleteStatement.execute();

      // update from memory should shouldn't need to update register
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static CartItem load(ResultSet rs) throws SQLException {
    int customerId = rs.getInt(1);
    int productID = rs.getInt(2);
    CartItem result = Registry.getCartItem(Objects.hash(customerId, productID));
    if (result != null) {
      return result;
    }
    int quantityArg = rs.getInt(3);
    result = new CartItem(customerId, productID, quantityArg, false);
    Registry.addCartItem(result);
    return result;
  }

}
