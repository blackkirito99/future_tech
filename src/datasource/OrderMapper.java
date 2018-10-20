package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.CartItem;
import domain.Order;
import domain.Product;

public class OrderMapper implements Mapper <Order> {
    private static final String initialSingleLazyLoadStatementString = "SELECT customerID" + " from orders" +
    " WHERE orderNum = ?";
    private static final String initialLazyLoadStatementString = "SELECT orderNum" + " from orders" +
    " WHERE customerID = ?";
    private static final String fullLazyLoadStatementString = "SELECT subtotal, currency" + " from orders" +
    " WHERE orderNum = ?";
    private static final String getProductInOrder = "SELECT productID, quantity" + " from orderItems" +
    " WHERE orderNum = ?";
    private static final String insertOrderStatementString = "INSERT INTO orders (orderNum, customerID, subtotal, currency) " + "VALUES (?, ?, ?, ?)";
    private static final String insertOrderItemStatementString = "INSERT INTO orderItems (productID, quantity, orderNum) " + "VALUES (?, ?, ?)";

    private static OrderMapper mapper;

    public static OrderMapper getInstance() {
        if (OrderMapper.mapper == null) {
            OrderMapper.mapper = new OrderMapper();
        }
        return OrderMapper.mapper;
    }

    public Order find(String orderNum, String id2) {
        Order result = Registry.getOrder(orderNum);
        if (result != null) {
            return result;
        }
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Order order = null;
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(initialSingleLazyLoadStatementString, conn);
            ps.setString(1, orderNum);
            rs = ps.executeQuery();
            rs.next();
            order = new Order(orderNum, rs.getInt(1), false);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        return order;
    }
    public List <Order> findAll() {
        return null;
    }
    public List <Order> findOrdersOfCustomer(int customerID) {
        List <Order> result = new ArrayList <> ();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(initialLazyLoadStatementString, conn);
            ps.setInt(1, customerID);
            rs = ps.executeQuery();
            while (rs.next()) {
                result.add(lazyLoadGhost(rs, customerID));
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
    //
    public void getFull(String orderNum) {
        // execute lazy load first if product does not exist in registry
        Order result = Registry.getOrder(orderNum);
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        if (result == null) {
            find(orderNum, "");
            System.out.println("order unexpected action");
            result = Registry.getOrder(orderNum);
        }
        // get order's all internal data
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(fullLazyLoadStatementString, conn);
            ps.setString(1, orderNum);
            rs = ps.executeQuery();
            rs.next();
            fullLoadGhost(rs, orderNum);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        // get order's all foreign data (products in order)
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(getProductInOrder, conn);
            ps.setString(1, orderNum);
            rs = ps.executeQuery();
            loadOrderProducts(rs, orderNum);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public void insert(Order order) {
    	Connection conn = null;
        PreparedStatement ps = null;
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(insertOrderStatementString, conn);
            ps.setString(1, order.getOrderNum());
            ps.setInt(2, order.getCustomerID());
            ps.setDouble(3, order.getTotalPrice());
            ps.setString(4, order.getCurrency());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        insertItemsInOrder(order);
    }

    private void insertItemsInOrder(Order order) {
    	Connection conn = null;
        PreparedStatement ps = null;
        try {
            Map <Product, Integer> items = order.getProductCopies();
            for (Product product: items.keySet()) {
            	conn = DBConnection.getDBConnection();
                ps = DBConnection.prepare(insertOrderItemStatementString, conn);
                ps.setInt(1, product.getProductID());
                ps.setInt(2, items.get(product));
                ps.setString(3, order.getOrderNum());
                ps.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    private Order lazyLoadGhost(ResultSet rs, int customerID) throws SQLException {
        String orderNum = rs.getString(1);
        Order result = Registry.getOrder(orderNum);
        if (result != null) {
            return result;
        }
        return new Order(orderNum, customerID, false);
    }

    private void fullLoadGhost(ResultSet rs, String orderNum) throws SQLException {
        Order order = Registry.getOrder(orderNum);
        if (order == null) {
            // error
            return;
        }
        order.setTotalPrice(rs.getInt(1));
        order.setCurrency(rs.getString(2));
    }

    private void loadOrderProducts(ResultSet rs, String orderNum) {
        Order order = Registry.getOrder(orderNum);
        if (order == null) {
            // error
            return;
        }
        Map <Product, Integer> orderProducts = new HashMap <Product, Integer> ();
        try {
            while (rs.next()) {
                int productID = rs.getInt(1);
                Product product = ProductMapper.getInstance().find(Integer.toString(productID), "");
                int quantity = rs.getInt(2);
                orderProducts.put(product, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        order.setProductCopies(orderProducts);
    }

    //update and delete is not allowed for order, so leave blank 

    @Override
    public void update(Order obj) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(Order obj) {
        // TODO Auto-generated method stub

    }

}