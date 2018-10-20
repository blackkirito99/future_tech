package presentation;

import java.util.List;

import auth.AppUtils;
import domain.CartItem;
import service.ApplicationService;

public class CartCommand implements Command {

  @Override
  public String execute(RequestContext requestContext) {
	if(AppUtils.isValidCustomer()) {
	    String command = requestContext.getParameter("command");
	    int userID = AppUtils.getUserID();
	    if (command.equals("cart post")) {
	      // add item to cart
	      Integer id = Integer.parseInt(requestContext.getParameter("id"));
	      // assume customer with id 1 is logged in
	      System.out.println(userID);
	      ApplicationService.addToCart(userID, id, 1);
	    } else if (command.equals("cart put")) {
	      // increase item quantity
	      Integer id = Integer.parseInt(requestContext.getParameter("id"));
	      
	      ApplicationService.increaseSingleProductInCart(userID, id, 1);
	    } else if (command.equals("cart delete")) {
	      // reduce cart item quantity
	      Integer id = Integer.parseInt(requestContext.getParameter("id"));
	      
	      // assume customer with id 1 is logged in
	      ApplicationService.decreaseSingleProductInCart(userID, id, 1);
	    }
	    // get cart
	    List<CartItem> cartItems = AppUtils.getCustomerItemsInCart();
	    if (cartItems == null) {
	      return "error";
	    } else {
	      System.out.println("shopping cart contains " + cartItems.size() + " items");
	      requestContext.setAttribute("cartItems", cartItems);
	      return "cart";
	    }
	}else if(AppUtils.isValidRetailer()) {
		// retailer doesn't has a shopping cart
		return "forbidden";
	}else {
		// if not yet login, redirect to login page
		return "login";
	}
  }
  

}
