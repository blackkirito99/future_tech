package presentation;

import java.util.ArrayList;
import java.util.List;


import domain.Product;


public class StoreCommand implements Command {

  @Override
  public String execute(RequestContext requestContext) {
    String command = requestContext.getParameter("command");
    if (command.equals("store get")) {
      String store = (String) requestContext.getParameter("store");
      List<Product> products = Product.getAvailableProductsOfBrand(store);
      requestContext.setAttribute("store", store);
      requestContext.setAttribute("products", products);
      return "store";
    }
    List<String> stores = Product.getAllBrands();
    requestContext.setAttribute("stores", stores);
    return "stores";
//	if(AppSession.getUser() == null) {
//		AppSession.logout();
//		return "login";
//	}
//	System.out.println(AppSession.hasRole(AppSession.RETAILER_ROLE));
//	if(AppUtils.isValidRetailer()) {
//		String command = requestContext.getParameter("command");
//		if (command.equals("store get")) {
//			String store = requestContext.getParameter("store");
//		    List<Product> products = Product.getAvailableProductsOfBrand(store);
//		    requestContext.setAttribute("products", products);
//		    return "store";
//		}
//		return "store";
//	}
//    return "forbidden";
  }

}
