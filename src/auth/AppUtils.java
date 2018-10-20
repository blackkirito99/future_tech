package auth;

import java.util.List;

import domain.CartItem;
import domain.Customer;

public class AppUtils {
	  public static boolean isValidCustomer() {
		  return AppSession.isAuthenticated() && AppSession.hasRole(AppSession.CUSTOMER_ROLE);
	  }
	  public static boolean isValidRetailer() {
		  return AppSession.isAuthenticated() && AppSession.hasRole(AppSession.RETAILER_ROLE);
	  }
	  public static int getUserID() {
		  return AppSession.getUser().getUserID();
	  }
	  public static List<CartItem> getCustomerItemsInCart() {
		  return ((Customer) AppSession.getUser()).getShoppingCart().getAllItemsInCart();
	  }
}
