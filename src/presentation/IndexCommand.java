package presentation;

import java.util.List;

import auth.AppUtils;
import datasource.UnitOfWork;
import domain.Product;

public class IndexCommand implements Command {

  @Override
  public String execute(RequestContext requestContext) {
	  // retail redirect to store page
	  UnitOfWork uow = UnitOfWork.getCurrent();
  	  if(uow == null) {
  		uow.newCurrent();
  	  }
	  if(AppUtils.isValidRetailer()) {
		  
		  List<Product> products = Product.getAllProducts();	  
	      requestContext.setAttribute("products", products);
		  return "store";
	  }
	  
	  // customer or non-login user, redirect to index page
	  return "index";
  }

}
