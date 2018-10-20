package presentation;

import auth.AppUtils;
import datasource.UnitOfWork;
import domain.Order;
import service.ApplicationService;

public class CheckoutCommand implements Command {

  @Override
  public String execute(RequestContext requestContext) {
	if(AppUtils.isValidCustomer()) {  
	    String command = requestContext.getParameter("command");
	    if (command.equals("payment get")) {
	      return "payment";
	    }
	    // get checkout
	    UnitOfWork.newCurrent();
	    UnitOfWork uow = UnitOfWork.getCurrent();
	    Order order = ApplicationService.checkout(AppUtils.getUserID());
	    // if cart is empty, cannot checkout and return to cart page
	    if(order == null) {
	    	return "cart";
	    }
	    uow.commit();
	    requestContext.setAttribute("order", order);
	    return "checkout";
	}else if(AppUtils.isValidRetailer()) { 
		return "forbidden";
	}else {
		return "login";
	}
 }

}
