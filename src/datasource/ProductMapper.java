package datasource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Computer;
import domain.Product;
import domain.Smartphone;
import domain.User;

public class ProductMapper {

  private static final String initialLazyLoadStatementString = "SELECT productID, name, category, price, number, image"
      + " from APP.products" + " WHERE productID = ?";
  private static final String updateStatementString = "UPDATE APP.products"
      + " set name = ?, brand = ?, category  = ?, price = ?, number = ?, cpu = ?, volume = ?, scrrenSize = ?"
      + " WHERE productID = ?";
  private static final String initialLazyLoadAllProductsStatement = "SELECT productID, name, category, price, number, image"
      + " from APP.products";
  private static final String fullLazyLoadStatementString = "SELECT brand, cpu, volume, screenSize"
      + " from APP.products" + " WHERE productID = ?";
  private static final String ComputerCategoryString = "PC";
  private static final String SmartPhoneCategoryString = "SM";

  public static Product getProduct(int id) {
    Product result = Registry.getProduct(id);
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

  public static void getFullProduct(int id) {
    // execute lazy load first if product does not exist in registry
    Product result = Registry.getProduct(id);
    if (result == null) {
      getProduct(id);
    }
    // then executes full load for remaining fields
    PreparedStatement findStatement = null;
    ResultSet rs = null;
    try {
      findStatement = DBConnection.prepare(fullLazyLoadStatementString);
      findStatement.setInt(1, id);
      rs = findStatement.executeQuery();
      rs.next();
      fullLoadGhost(rs, id);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static List<Product> getAllProducts() {
    List<Product> result = new ArrayList<>();
    try {
      PreparedStatement findStatement = DBConnection.prepare(initialLazyLoadAllProductsStatement);
      ResultSet rs = findStatement.executeQuery();
      while (rs.next()) {
        result.add(lazyLoadGhost(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  private static Product lazyLoadGhost(ResultSet rs) throws SQLException {
    int id = rs.getInt(1);
    Product result = Registry.getProduct(id);
    if (result != null) {
      return result;
    }
    String name = rs.getString(2);
    String category = rs.getString(3);
    double price = rs.getDouble(4);
    int stockNumber = rs.getInt(5);
    String image = rs.getString(6);
    if (category.equals(ComputerCategoryString)) {
      result = new Computer(id, name, category, price, stockNumber, image, false);
    } else if (category.equals(SmartPhoneCategoryString)) {
      result = new Smartphone(id, name, category, price, stockNumber, image, false);
    }
    Registry.addProduct(result);
    return result;
  }

  private static void fullLoadGhost(ResultSet rs, int id) throws SQLException {
    Product product = Registry.getProduct(id);
    if (product == null) {
      // error
      return;
    }
    product.setBrand(rs.getString(1));
    if (product.getCategory().equals(ComputerCategoryString)) {
      ((Computer) product).setCpu(rs.getString(2));
      ((Computer) product).setDiskVolume(rs.getInt(3));
    } else if (product.getCategory().equals(SmartPhoneCategoryString)) {
      ((Smartphone) product).setScreenSize(rs.getDouble(4));
    }
  }

  // no lazy load
  /*
   * public static Product getProduct(int id) { Product result =
   * Registry.getProduct(id); if(result != null) { return result; }
   * PreparedStatement findStatement= null; ResultSet rs = null; try {
   * findStatement = DBConnection.prepare(findStatementString);
   * findStatement.setInt(1, id); rs = findStatement.executeQuery(); rs.next();
   * result = load(rs); }catch (SQLException e) {
   * 
   * } return result; }
   * 
   * 
   * public static List<Product> getAvailableProducts() { List<Product> result =
   * new ArrayList<>(); try { PreparedStatement findStatement =
   * DBConnection.prepare(findAvailableProductsStatement); ResultSet rs =
   * findStatement.executeQuery(); while(rs.next()) {
   * result.add(lazyLoadGhost(rs)); } }catch (SQLException e) {
   * 
   * } return result; }
   * 
   * private static Product load(ResultSet rs) throws SQLException { int id =
   * rs.getInt(1); Product result = Registry.getProduct(id); if(result != null) {
   * return result; } String name = rs.getString(2); String brand =
   * rs.getString(3); String category = rs.getString(4); double price =
   * rs.getDouble(5); int stock = rs.getInt(6);
   * if(category.equals(ComputerCategoryString)) { String cpu = rs.getString(7);
   * int diskVolume = rs.getInt(8); result = new Computer(id, name, brand,
   * category, price, stock, cpu, diskVolume); }else
   * if(category.equals(SmartPhoneCategoryString)) { double screenSize =
   * rs.getDouble(9); result = new Smartphone(id, name, brand, category, price,
   * stock, screenSize); } Registry.addProduct(result); return result; }
   */

  /* For feature B */

  public static void update(Product product) {
    PreparedStatement updateStatement = null;
    try {
      updateStatement = DBConnection.prepare(updateStatementString);
      updateStatement.setInt(1, product.getProductID());
      updateStatement.setString(2, product.getName());
      updateStatement.setString(3, product.getBrand());
      updateStatement.setString(4, product.getCategory());
      updateStatement.setDouble(5, product.getPrice());
      updateStatement.setInt(6, product.getStockNumber());
      if (product.getCategory().equals(ComputerCategoryString)) {
        updateStatement.setString(7, ((Computer) product).getCpu());
        updateStatement.setInt(8, ((Computer) product).getDiskVolume());
      } else if (product.getCategory().equals(SmartPhoneCategoryString)) {
        updateStatement.setDouble(9, ((Smartphone) product).getScreenSize());
      }
      updateStatement.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void add(Product product) {

  }

  public static void delete(Product product) {

  }

}
