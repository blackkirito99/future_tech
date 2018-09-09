package domain;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import datasource.ProductMapper;
import datasource.Registry;
import datasource.UserMapper;

public class ApplicationService {

   public static void addToCart(int CustomerID, int productID, int quantity) {
       // check if already in chart
	   Product item = Product.getProduct(productID);
       List<CartItem> items = ShoppingCart.getCartOf(CustomerID).getAllItemsInCart();
       int index = getIndexOfProduct(items, item);
       if(index >= 0) {
           items.get(index).addQuantity(quantity);
       }else {
           // if not, create new a CartItem
    	   CartItem cartItem = new CartItem(CustomerID, item.getProductID(), quantity, true);
    	   // save to register
    	   Registry.addCartItem(cartItem);
           items.add(cartItem);
       }
   }


   public static void increaseSingleProductInCart(int CustomerID, int productID, int delta) {
       // find item in chart
	   Product item = Product.getProduct(productID);
       List<CartItem> items = ShoppingCart.getCartOf(CustomerID).getValidCartItems();
       int index = getIndexOfProduct(items, item);
       if(index >= 0) {
           items.get(index).addQuantity(delta);
           System.out.println(delta + "added to " + CustomerID + "-" + productID);
       }else {
           // if not, error
           System.out.println("Increase Error!");
       }
   }
   public static void decreaseSingleProductInCart(int CustomerID, int productID, int delta) {
       // find item in chart
	   Product item = Product.getProduct(productID);
       List<CartItem> items = ShoppingCart.getCartOf(CustomerID).getValidCartItems();
       int index = getIndexOfProduct(items, item);
       if(index >= 0 ) {
           items.get(index).removeQuantity(delta);
       }else {
           // if not, error
           System.out.println("Decrease Error!");
       }
   }

   public static void updateSingleProductInCart(int CustomerID, int productID, int quantity) {
       // find item in chart
	   Product item = Product.getProduct(productID);
       List<CartItem> items = ShoppingCart.getCartOf(CustomerID).getValidCartItems();
       int index = getIndexOfProduct(items, item);
       if(index >= 0 ) {
           items.get(index).setQuantity(quantity);
       }else {
           // if not, error
           System.out.println("Update Error!");
       }
   }

   // precondition: index of quantity is same as the change target
   public static void updateProductsInCart(int CustomerID, List<Product> items, List<Integer> quantities) {
       // find each item in chart
       List<CartItem> cartItems = ShoppingCart.getCartOf(CustomerID).getValidCartItems();
       for(int i = 0; i < items.size(); i++) {
           int index = getIndexOfProduct(cartItems, items.get(i));
           if(index >= 0 ) {
               cartItems.get(index).setQuantity(quantities.get(index));

           }
       }
   }

   public static void deleteProductInCart(int CustomerID, int productID) {
       // find item in chart
	   Product item = Product.getProduct(productID);
       List<CartItem> items = ShoppingCart.getCartOf(CustomerID).getValidCartItems();
       int index = getIndexOfProduct(items, item);
       if(index >= 0 ) {
           items.get(index).setQuantity(0);
       }else {
           // if not, error
           System.out.println("Error!");
       }
   }


   public static void checkout(int CustomerID) {
       List<CartItem> items = ShoppingCart.getCartOf(CustomerID).getValidCartItems();
       Order order = new Order("FT_000" + (int) new Date().getTime(), CustomerID, true);
       float subtotal = 0;
       Map<Product, Integer> products = new HashMap<Product, Integer>();
       for(CartItem item : items) {
           Product p = ProductMapper.getProduct(item.getProductID());
           products.put(p, item.getQuantity());
           // mark it as deleted
           subtotal += p.getPrice() * item.getQuantity();
           //remove all
           item.removeQuantity(item.getQuantity());
       }
       // we set it default as AUD
       order.setCurrency("AUD");
       order.setTotalPrice(subtotal);
       order.setProductCopies(products);
       
       // save to register
       Registry.addOrder(order);
   }


   public static void updatePersonalDetail(int customerID, String lastName, String firstName,
           String password, String email, String avatar, String address){
       Customer customer = (Customer)UserMapper.getUser(customerID);
       customer.setFirstName(firstName);
       customer.setLastName(lastName);
       customer.setPassword(password);
       customer.setEmail(email);
       customer.setAddress(address);
       customer.setAvatar(avatar);
   }

   private static int getIndexOfProduct(List<CartItem> items, Product item) {
       for (int i = 0; i < items.size(); i++) {
           if (items.get(i).getProductID() == item.getProductID()) {
               return i;
           }
       }
       return -1;
   }
}
