package datasource;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import domain.CartItem;

public class CartMapper implements Mapper <CartItem> {

    private final static String findCartOfUser = "SELECT * " + "  FROM cartItems " + "  WHERE customerID = ?";
    private final static String finditemOfUser = "SELECT * " + "  FROM cartItems " +
    "  WHERE customerID = ? AND productID = ?";
    private static final String updateQuantityString = "UPDATE cartItems " + "  SET quantity = ? " +
    "  WHERE customerID = ? AND productID = ?";
    private static final String insertString = "INSERT INTO cartItems VALUES (?, ?, ?)";
    private static final String deleteString = "DELETE FROM cartItems" +
    "  WHERE customerID = ? AND productID = ?";

    private static CartMapper mapper;

    public static CartMapper getInstance() {
        if (CartMapper.mapper == null) {
            CartMapper.mapper = new CartMapper();
        }
        return CartMapper.mapper;
    }

    // return single pair map
    public CartItem find(String customerID, String productID) {
        CartItem result = Registry.getCartItem(Objects.hash(customerID, productID));
        if (result != null) {
            return result;
        }
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        CartItem cartItem = null;
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(finditemOfUser, conn);
            ps.setInt(1, Integer.parseInt(customerID));
            ps.setInt(2, Integer.parseInt(productID));
            rs = ps.executeQuery();
            if (rs.next()) {
            	cartItem = load(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        return cartItem;
    }

    // meanless action, should not be made.
    public List <CartItem> findAll() {
        return null;
    }
    // find cart of a customer
    public List <CartItem> findCartOfCustomer(int customerID) {
        List <CartItem> result = new ArrayList <CartItem> ();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {        	
        	conn = DBConnection.getDBConnection();
        	ps = DBConnection.prepare(findCartOfUser, conn);
            ps.setInt(1, customerID);
            rs = ps.executeQuery();
            while (rs.next()) {
                result.add(load(rs));
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


    public void insert(CartItem object) {
    	Connection conn = null;
        PreparedStatement ps = null;
        try {
            // update quantity in database
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(insertString, conn);
            ps.setInt(1, object.getCustomerID());
            ps.setInt(2, object.getProductID());
            ps.setInt(3, object.getQuantity());
            ps.execute();
            // update from memory should shouldn't need to update register
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public void update(CartItem object) {
    	Connection conn = null;
        PreparedStatement ps = null;
        try {
            // update quantity in database
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(updateQuantityString, conn);
            ps.setInt(1, object.getQuantity());
            ps.setInt(2, object.getCustomerID());
            ps.setInt(3, object.getProductID());
            ps.execute();
            // update from memory should shouldn't need to update register
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public void delete(CartItem object) {
    	Connection conn = null;
        PreparedStatement ps = null;
        try {
            // update quantity in database
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(deleteString, conn);
            ps.setInt(1, object.getCustomerID());
            ps.setInt(2, object.getProductID());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    private CartItem load(ResultSet rs) throws SQLException {
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

    // lazy load is not for cartItem, so not in use
    @Override
    public void getFull(String id) {
        // TODO Auto-generated method stub

    }

}