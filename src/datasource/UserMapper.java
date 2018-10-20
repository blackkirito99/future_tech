package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import auth.AppSession;
import domain.Customer;
import domain.User;
import domain.Retailer;

public class UserMapper implements Mapper <User> {


    private static final String loginStatementString = "SELECT userID" +
    " from users" + " WHERE username = ? AND password = ?";
    private static final String initialLazyLoadStatementString = "SELECT userID, username, type, avatar, email" +
    " from users" + " WHERE userID = ?";
    private static final String initialLazyLoadALLStatementString = "SELECT userID, username, type, avatar, email" +
    " from users";
    private static final String fullLazyLoadStatementString = "SELECT lastName, firstName, password, address" +
    " from users" + " WHERE userID = ?";
    private static final String updateStatementString = "UPDATE users" +
    " set lastName = ?, firstName = ?, password = ?, email = ?, address = ?, avatar = ? " + " WHERE userID = ?";
    private static final String insertStatementString = "INSERT INTO users (lastName, firstName, username, password, email, type, address, avatar) " +
    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String deleteStatementString = "DELETE FROM users" +
    "  WHERE userID = ?";

    private static UserMapper mapper;

    public static UserMapper getInstance() {
        if (UserMapper.mapper == null) {
            UserMapper.mapper = new UserMapper();
        }
        return UserMapper.mapper;
    }

    public String Login(String username, String password) {
    	Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String userID = null;
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(loginStatementString, conn);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                // if username & password match, return userID for session identification
            	userID = Integer.toString(rs.getInt(1));
            } else {
                // if not match, return a null string indicate wrong username/passord
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        return userID;
    }

    public User find(String id, String id2) {
        User result = Registry.getUser(Integer.parseInt(id));
        if (result != null) {
            return result;
        }
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(initialLazyLoadStatementString, conn);
            ps.setInt(1, Integer.parseInt(id));
            rs = ps.executeQuery();
            rs.next();
            user = lazyLoadGhost(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        return user;
    }

    public List <User> findAll() {
        List <User> result = new ArrayList <> ();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(initialLazyLoadALLStatementString, conn);
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
        User result = Registry.getUser(Integer.parseInt(id));
        if (result == null) {
            find(id, "");
            System.out.println("user unexpected action");
            result = Registry.getUser(Integer.parseInt(id));
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
            //return 
            fullLoadGhost(rs, Integer.parseInt(id));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
        //return null;
    }


    @Override
    public void update(User user) {
    	Connection conn = null;
        PreparedStatement ps = null;
        try {
            // type can not be updated.
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(updateStatementString, conn);
           ps.setString(1, user.getLastName());
           ps.setString(2, user.getFirstName());
           ps.setString(3, user.getPassword());
           ps.setString(4, user.getEmail());
            if (user.getType().equals(AppSession.CUSTOMER_ROLE)) {
               ps.setString(5, ((Customer) user).getAddress());
            } else if (user.getType().equals(AppSession.RETAILER_ROLE)) {
               ps.setString(5, "null");
            }
           ps.setString(6, user.getAvatar());
           ps.setInt(7, user.getUserID());
           ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    // feature B
    @Override
    public void insert(User user) {
    	Connection conn = null;
        PreparedStatement ps = null;
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(insertStatementString, conn);
            ps.setString(1, user.getLastName());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getType());
            ps.setString(8, user.getAvatar());
            if (user.getType().equals(AppSession.CUSTOMER_ROLE)) {
                ps.setString(7, ((Customer) user).getAddress());
            } else {
                ps.setString(7, "null");
            }
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }

    }
    @Override
    public void delete(User user) {
    	Connection conn = null;
        PreparedStatement ps = null;
        try {
        	conn = DBConnection.getDBConnection();
            ps = DBConnection.prepare(deleteStatementString, conn);
            ps.setInt(1, user.getUserID());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }
    private User lazyLoadGhost(ResultSet rs) throws SQLException {
        int userID = rs.getInt(1);
        User result = Registry.getUser(userID);
        if (result != null) {
            return result;
        }
        String username = rs.getString(2);
        String type = rs.getString(3);
        String avatar = rs.getString(4);
        String email = rs.getString(5);
        if (type.equals(AppSession.CUSTOMER_ROLE)) {
            result = new Customer(userID, username, type, email, avatar, false);
        } else if (type.equals(AppSession.RETAILER_ROLE)) {
            result = new Retailer(userID, username, type, email, avatar, false);
        }
        Registry.addUser(result);
        return result;
    }

    private User fullLoadGhost(ResultSet rs, int id) throws SQLException {
        User user = Registry.getUser(id);
        if (user == null) {
            // error
            return null;
        }
        user.setLastName(rs.getString(1));
        user.setFirstName(rs.getString(2));
        user.setPassword(rs.getString(3));
//        user.setEmail(rs.getString(4));
        if (user.getType().equals(AppSession.CUSTOMER_ROLE)) {
            ((Customer) user).setAddress(rs.getString(4));
        } else if (user.getType().equals(AppSession.RETAILER_ROLE)) {
            // set retailer attributes
        }
        return user;
    }

}