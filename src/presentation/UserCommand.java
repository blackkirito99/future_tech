package presentation;

import auth.AppSession;
import auth.AppUtils;
import service.ApplicationService;

public class UserCommand implements Command {

  @Override
  public String execute(RequestContext requestContext) {
	String view;
    String command = requestContext.getParameter("command");
    // prevent error, if user is cleaned, required re-login
    if(AppSession.getUser() == null) {
    	AppSession.logout();
    }
    if(!AppSession.isAuthenticated()) {
      // not logged in
	    view = "login";
    }else{
      // if already login
      if(AppUtils.isValidCustomer()) {
        if (command.equals("user put")) {
          // update personal details
          String firstName = (String) requestContext.getParameter("first_name");
          String lastName = (String) requestContext.getParameter("last_name");
          String email = (String) requestContext.getParameter("email");
          String address = (String) requestContext.getParameter("address");
          
          ApplicationService.updateCustomerPersonalDetail(AppUtils.getUserID(), lastName, firstName, email, address);
        }
        requestContext.setAttribute("user", AppSession.getUser());
      } else if(AppUtils.isValidRetailer()) {
    	  if (command.equals("user put")) {
	        String firstName = (String) requestContext.getParameter("first_name");
	        String lastName = (String) requestContext.getParameter("last_name");
	        String email = (String) requestContext.getParameter("email");
	        
	        ApplicationService.updateRetailerPersonalDetail(AppUtils.getUserID(), lastName, firstName, email);
    	  }
          requestContext.setAttribute("user", AppSession.getUser());
      }	
      view = "user";
    }
    return view;
  }
  
  public String hash(String string) {
    return Integer.toString(string.hashCode());
  }

}
