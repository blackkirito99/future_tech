package datasource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Order;
import domain.Product;

public class OrderMapper {
  
  private static final String initialLazyLoadStatementString = "SELECT orderNum" + " from APP.orders"
      + " WHERE customerID = ?";
  private static final String fullLazyLoadStatementString = "SELECT subtotal, currency" + " from APP.orders"
      + " WHERE orderNum = ?";
  private static final String getProductInOrder = "SELECT productID, quantity" + " from APP.orderItems"
      + " WHERE orderNum = ?";
  private static final String insertOrderStatementString = "INSERT INTO APP.orders (orderNum, customerID, subtotal, currency) " + "VALUES (?, ?, ?, ?)";
  private static final String insertOrderItemStatementString = "INSERT INTO APP.orderItems (productID, quantity, orderNum) " + "VALUES (?, ?, ?)";

  public static List<Order> findOrdersOf(int id) {
    List<Order> result = new ArrayList<>();
    try {
      PreparedStatement findStatement = DBConnection.prepare(initialLazyLoadStatementString);
      findStatement.setInt(1, id);
      ResultSet rs = findStatement.executeQuery();
      while (rs.next()) {
        result.add(lazyLoadGhost(rs, id));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }
//
  public static void getFullOrder(String orderNum) {
    // get order's all internal data
    try {
      PreparedStatement findStatement = DBConnection.prepare(fullLazyLoadStatementString);
      findStatement.setString(1, orderNum);
      ResultSet rs = findStatement.executeQuery();
      rs.next();
      fullLoadGhost(rs, orderNum);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // get order's all foreign data (products in order)
    try {
      PreparedStatement findStatement = DBConnection.prepare(getProductInOrder);
      findStatement.setString(1, orderNum);
      ResultSet rs = findStatement.executeQuery();
      loadOrderProducts(rs, orderNum);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void insertNewOrder(Order order) {
    try {
      PreparedStatement insertStatement = DBConnection.prepare(insertOrderStatementString);
      insertStatement.setString(1, order.getOrderNum());
      insertStatement.setInt(2, order.getCusomerID());
      insertStatement.setDouble(3, order.getTotalPrice());
      insertStatement.setString(4, order.getCurrency());
      insertStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    insertItemsInOrder(order);
  }

  private static void insertItemsInOrder(Order order) {
    try {
      Map<Product, Integer> items = order.getProductCopies();
      for (Product product : items.keySet()) {
        PreparedStatement insertStatement = DBConnection.prepare(insertOrderItemStatementString);
        insertStatement.setInt(1, product.getProductID());
        insertStatement.setInt(2, items.get(product));
        insertStatement.setString(3, order.getOrderNum());
        insertStatement.execute();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static Order lazyLoadGhost(ResultSet rs, int customerID) throws SQLException {
    String orderNum = rs.getString(1);
    Order result = Registry.getOrder(orderNum);
    if (result != null) {
      return result;
    }
    return new Order(orderNum, customerID, false);
  }

  private static void fullLoadGhost(ResultSet rs, String orderNum) throws SQLException {
    Order order = Registry.getOrder(orderNum);
    if (order == null) {
      // error
      return;
    }
    order.setTotalPrice(rs.getInt(1));
    order.setCurrency(rs.getString(2));
  }

  private static void loadOrderProducts(ResultSet rs, String orderNum) {
    Order order = Registry.getOrder(orderNum);
    if (order == null) {
      // error
      return;
    }
    Map<Product, Integer> orderProducts = new HashMap<Product, Integer>();
    try {
      while (rs.next()) {
        int productID = rs.getInt(1);
        Product product = ProductMapper.getProduct(productID);
        int quantity = rs.getInt(2);
        orderProducts.put(product, quantity);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    order.setProductCopies(orderProducts);
  }
}
