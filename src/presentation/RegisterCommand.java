package presentation;

import service.ApplicationService;

public class RegisterCommand implements Command {

  @Override
  public String execute(RequestContext requestContext) {
    String command = requestContext.getParameter("command");
    if (command.equals("register post")) {
      String username = requestContext.getParameter("username");
      String passwordHash = hash(requestContext.getParameter("password"));
      String password = requestContext.getParameter("password");
      String type = requestContext.getParameter("type");
      String firstName = requestContext.getParameter("first_name");
      String lastName = requestContext.getParameter("last_name");
      String emailAddress = requestContext.getParameter("email_address");
      String address = requestContext.getParameter("address");
      System.out.println("register: {username: "
          + username + ", password: "
          + passwordHash + ", first_name: "
          + firstName + ", last_name: "
          + lastName + ", email_address: "
          + emailAddress + ", address: "
          + address + "}"
      );
      
      if (type.equals("RT")) {
        ApplicationService.registerNewRetailer(username, lastName, firstName, emailAddress, password);
      } else {
        ApplicationService.registerNewCustomer(username, lastName, firstName, emailAddress, address, password);
      }
      
      // get generated userID from db and update cache
    
      return "login";
    }
    return "register";
  }
  
  public String hash(String string) {
    return Integer.toString(string.hashCode());
  }
  
}
