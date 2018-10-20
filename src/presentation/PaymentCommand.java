package presentation;

import auth.AppUtils;

public class PaymentCommand implements Command {

  @Override
  public String execute(RequestContext requestContext) {
	if(AppUtils.isValidCustomer()) {
			
		
	    String command = requestContext.getParameter("command");
	    if (command.equals("payment post")) {
	      return "success";
	    }
	    // get payment
	    Double amount = Double.parseDouble(requestContext.getParameter("amount"));
	    requestContext.setAttribute("amount", amount);
	    return "payment";
	}else if(AppUtils.isValidRetailer()) {
		return "frobidden";
	}else {
		return "login";
	}
  }

}
