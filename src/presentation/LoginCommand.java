package presentation;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import auth.AppSession;
import auth.AppUtils;
import domain.Product;
import domain.User;

public class LoginCommand implements Command {

  @Override
  public String execute(RequestContext requestContext) {
	String view = "login";
    String command = requestContext.getParameter("command");
    // prevent error, if user is cleaned, required re-login
    if(AppSession.getUser() == null) {
    	AppSession.logout();
    }
    if(!AppSession.isAuthenticated()) {
	    if (command.equals("login post")) {
	      // get login details
	      String username = requestContext.getParameter("username");
	      String password = requestContext.getParameter("password");
	      
	      UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		  token.setRememberMe(true);
		  
		  Subject currentUser = SecurityUtils.getSubject();
		  
		  try {
			  currentUser.login(token);
			  User user = User.login(username, password);
			  if(user == null) {  
				throw new UnknownAccountException();
			  }
			  AppSession.init(user);
			  // set redirect page depends on user type
			  if(AppUtils.isValidCustomer())
			  {
				  // redirect to all product page, error exist
			      /*List<Product> products = Product.getAvailableProducts();
			      requestContext.setAttribute("products", products);
				  view = "products";*/
				  
				  view = "index";
				  
				  
			  }else if(AppUtils.isValidRetailer()) {
  				  // redirect to store page
				  // all items
				  List<Product> products = Product.getAllProducts();
				  
			      requestContext.setAttribute("products", products);
				  view = "store";
			  }
		  }catch(UnknownAccountException | IncorrectCredentialsException e){
			  // if not correct info is entered, redirect back to login page
			  view = "login";
		  }
	    }
    }else{
      if (command.equals("login delete")) {
        // logout
        AppSession.logout();
        view = "login";
      } else {
    	// if already login
      	if(AppUtils.isValidCustomer()) {
      		view = "user";
      	}else if(AppUtils.isValidRetailer()) {
      		view = "user";
      	}
      }
    }
    return view;
  }
  
  public String hash(String string) {
    return Integer.toString(string.hashCode());
  }

}
