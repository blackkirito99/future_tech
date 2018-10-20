package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Computer;
import domain.Product;
import domain.Smartphone;

public class ProductMapper implements Mapper <Product> {

    private static final String initialLazyLoadStatementString = "SELECT productID, name, category, price, number, image" +
    " from products" + " WHERE productID = ?";
    private static final String initialLazyLoadAllProductsStatement = "SELECT productID, name, category, price, number, image" +
    " from products";
    private static final String initialLazyLoadBrandStatementString = "SELECT productID, name, category, price, number, image" +
    	    " from products" + " WHERE brand = ?";
    private static final String initialLazyLoadCategoryStatementString = "SELECT productID, name, category, price, number, image" +
    	    " from products" + " WHERE category = ?";
    private static final String initialLazyLoadQueryStatementString = "SELECT productID, name, category, price, number, image" +
    	    " from products" + " WHERE UPPER(name) LIKE UPPER(?) OR UPPER(category) LIKE UPPER(?)";
    private static final String fullLazyLoadStatementString = "SELECT brand, cpu, volume, screenSize" +
    " from products" + " WHERE productID = ?";
    private static final String updateStatementString = "UPDATE products" +
    " set name = ?, brand = ?, category  = ?, price = ?, number = ?, cpu = ?, volume = ?, screenSize = ?, image = ?" +
    " WHERE productID = ?";
    private static final String insertStatementString = "INSERT INTO products" + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String deleteStatementString1 = "DELETE FROM cartItems WHERE productID = ?";
    private static final String deleteStatementString2 = "DELETE FROM orderItems WHERE productID = ?";
    private static final String deleteStatementString3 = "DELETE FROM products" +
    " WHERE productID = ?";
    private static final String ComputerCategoryString = "PC";
    private static final String SmartPhoneCategoryString = "SM";

    private static ProductMapper mapper;

    public static ProductMapper getInstance() {
        if (ProductMapper.mapper == null) {
            ProductMapper.mapper = new ProductMapper();
        }
        return ProductMapper.mapper;
    }
    public Product find(String id, String id2) {
        Product result = Registry.getProduct(Integer.parseInt(id));
        if (result != null) {
            return result;
        }
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(initialLazyLoadStatementString, conn);
            ps.setInt(1, Integer.parseInt(id));
            rs = ps.executeQuery();
            rs.next();
            Product product = lazyLoadGhost(rs);
            ps.close();
            return product;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        return null;
    }
    

    public List<Product> findBrand(String brand) {
    	List <Product> result = new ArrayList <> ();
    	Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(initialLazyLoadBrandStatementString, conn);
            ps.setString(1, brand);
            rs = ps.executeQuery();
            while (rs.next()) {
                result.add(lazyLoadGhost(rs));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        return result;
    }
    public List<Product> findCategory(String category) {
    	List <Product> result = new ArrayList <> ();
    	Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(initialLazyLoadCategoryStatementString, conn);
            ps.setString(1, category);
            rs = ps.executeQuery();
            while (rs.next()) {
                result.add(lazyLoadGhost(rs));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        return result;
    }
    
    public List<Product> findQuery(String query) {
      List <Product> result = new ArrayList <> ();
      Connection conn = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      try {
    	  conn = DBConnection.getDBConnection();
          ps = DBConnection.prepare(initialLazyLoadQueryStatementString, conn);
          ps.setString(1, "%" + query + "%");
          ps.setString(2, "%" + query + "%");
          rs = ps.executeQuery();
          while (rs.next()) {
              result.add(lazyLoadGhost(rs));
          }
      } catch (SQLException e) {
          e.printStackTrace();
      } finally {
          try { rs.close(); } catch (Exception e) { /* ignored */ }
          try { ps.close(); } catch (Exception e) { /* ignored */ }
          try { conn.close(); } catch (Exception e) { /* ignored */ }
      }
      return result;
  }

    public void getFull(String id) {
        // execute lazy load first if product does not exist in registry
        Product result = Registry.getProduct(Integer.parseInt(id));
        if (result == null) {
            find(id, "");
            System.out.println("product unexpected action");
            result = Registry.getProduct(Integer.parseInt(id));
        }
        // then executes full load for remaining fields
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(fullLazyLoadStatementString, conn);
            ps.setInt(1, Integer.parseInt(id));
            rs = ps.executeQuery();
            rs.next();
            fullLoadGhost(rs, Integer.parseInt(id));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public List <Product> findAll() {
        List <Product> result = new ArrayList <> ();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(initialLazyLoadAllProductsStatement, conn);
            rs = ps.executeQuery();
            while (rs.next()) {
                result.add(lazyLoadGhost(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        return result;
    }

    private Product lazyLoadGhost(ResultSet rs) throws SQLException {
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

    private void fullLoadGhost(ResultSet rs, int id) throws SQLException {
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
    /* For feature B */

    public void update(Product product) {
    	Connection conn = null;
        PreparedStatement ps = null;
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(updateStatementString, conn);
            ps.setString(1, product.getName());
            ps.setString(2, product.getBrand());
            String category = product.getCategory();
            ps.setString(3, category);
            ps.setDouble(4, product.getPrice());
            ps.setInt(5, product.getStockNumber());
            if (category.equals(ComputerCategoryString)) {
                ps.setString(6, ((Computer) product).getCpu());
                ps.setInt(7, ((Computer) product).getDiskVolume());
                ps.setDouble(8, -1);
            } else if (category.equals(SmartPhoneCategoryString)) {
                ps.setString(6, "null");
                ps.setInt(7, -1);
                ps.setDouble(8, ((Smartphone) product).getScreenSize());
            }
            ps.setString(9, product.getImage());
            ps.setInt(10, product.getProductID());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public void insert(Product product) {
    	Connection conn = null;
        PreparedStatement ps = null;
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(insertStatementString, conn);
            ps.setInt(1, product.getProductID());
            ps.setString(2, product.getName());
            ps.setString(3, product.getBrand());
            String category = product.getCategory();
            ps.setString(4, category);
            ps.setDouble(5, product.getPrice());
            ps.setInt(6, product.getStockNumber());
            if (category.equals(ComputerCategoryString)) {
                ps.setString(7, ((Computer) product).getCpu());
                ps.setInt(8, ((Computer) product).getDiskVolume());
                ps.setDouble(9, -1);
            } else if (category.equals(SmartPhoneCategoryString)) {
                ps.setString(7, "null");
                ps.setInt(8, -1);
                ps.setDouble(9, ((Smartphone) product).getScreenSize());
            }
            ps.setString(10, product.getImage());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public void delete(Product product) {
    	Connection conn = null;
        PreparedStatement ps = null;
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(deleteStatementString1, conn);
            ps.setInt(1, product.getProductID());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(deleteStatementString2, conn);
            ps.setInt(1, product.getProductID());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(deleteStatementString3, conn);
            ps.setInt(1, product.getProductID());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        Registry.deleteProduct(product);
    }

}