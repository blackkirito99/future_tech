package presentation;

public class CommandHelper {

  public Command getCommand(String uri) {
      
    System.out.println("uri: " + uri);
  
    Command comm = null;
    if (uri.contains("index")) {
      comm = new IndexCommand();
    } else if (uri.contains("about")) {      // user views about page
      comm = new AboutCommand();
//    } else if (uri.contains("cart delete")) {    // customer deletes an item from cart
//      comm = new CartDeleteCommand();
    } else if (uri.contains("cart")) {       // customer accesses cart page
      comm = new CartCommand();
//    } else if (uri.contains("cart post")) {      // customer adds an item to cart
//      comm = new CartPostCommand();
//    } else if (uri.contains("cart put")) {       // customer updates quantity of item in cart
//      comm = new CartPutCommand();
    } else if (uri.contains("categories")) { // user views categories
      comm = new CategoryCommand();
    } else if (uri.contains("category")) {   // user views products by category
      comm = new CategoryCommand();
    } else if (uri.contains("checkout")) {   // customer checks out a cart
      comm = new CheckoutCommand();
    } else if (uri.contains("login")) {      // user accesses login page
      comm = new LoginCommand();
//    } else if (uri.contains("login post")) {     // user logs in
//      comm = new LoginPostCommand();
    } else if (uri.contains("payment")) {    // customer accesses payment confirmation page
      comm = new PaymentCommand();
//    } else if (uri.contains("payment post")) {   // customer confirms payment details
//      comm = new PaymentPostCommand();
//    } else if (uri.contains("product delete")) { // retailer deletes a product
//      comm = new ProductDeleteCommand();
    } else if (uri.contains("product")) {    // user views a product
      comm = new ProductCommand();
    } else if (uri.contains("new")) {   // retailer adds a new product
      comm = new ProductCommand();
//    } else if (uri.contains("product put")) {    // retailer updates an existing product
//      comm = new ProductPutCommand();
    } else if (uri.contains("products")) {   // user views all products
      comm = new ProductCommand();
    } else if (uri.contains("register")) {   // user accesses register page
      comm = new RegisterCommand();
//    } else if (uri.contains("register post")) {  // user registers for an account
//      comm = new RegisterPostCommand();
    } else if (uri.contains("search")) {     // user views stores
      comm = new SearchCommand();
    } else if (uri.contains("stores")) {     // user views stores
      comm = new StoreCommand();
    } else if (uri.contains("store")) {      // user views products by store
      comm = new StoreCommand();
    } else if (uri.contains("user")) {       // customer/retailer views user details
      comm = new UserCommand();
//    } else if (uri.contains("user put")) {       // customer/retailer updates user details
//      comm = new UserPutCommand();
    } else {
      comm = new IndexCommand();
    }
    return comm;
  }

}