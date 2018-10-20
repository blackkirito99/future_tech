package datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.CartItem;
import domain.Order;
import domain.Product;
import domain.User;

public class Registry {

    private static Map <Integer, Product> products = new HashMap <> ();
    private static Map <Integer, User> users = new HashMap <> ();
    private static Map <Integer, CartItem> cartItems = new HashMap <> ();
    private static Map <String, Order> orders = new HashMap <String, Order> ();

    public static Product getProduct(int id) {
        return products.get(id);
    }

    public static List <Product> getAllProducts() {
        return new ArrayList < Product > (products.values());
    }

    public static void addProduct(Product product) {
        Registry.products.put(product.getProductID(), product);
    }

    public static User getUser(int id) {
        return users.get(id);
    }

    public static List <User> getAllUsers() {
        return new ArrayList <User> (users.values());
    }

    public static void addUser(User user) {
        Registry.users.put(user.getUserID(), user);
    }

    public static CartItem getCartItem(int hash) {
        return cartItems.get(hash);
    }
    public static List <CartItem> getAllCartItems() {
        return new ArrayList <CartItem> (cartItems.values());
    }
    public static void addCartItem(CartItem cartItem) {
        Registry.cartItems.put(cartItem.hashCode(), cartItem);
    }

    public static Order getOrder(String orderNum) {
        return orders.get(orderNum);
    }

    public static List <Order> getAllOrders() {
        return new ArrayList <Order> (orders.values());
    }
    public static void addOrder(Order order) {
        Registry.orders.put(order.getOrderNum(), order);
    }
    
    public static void deleteProduct(Product product) {
      if (products.containsValue(product)) {
        products.remove(product.getProductID(), product);
      }
    }

}