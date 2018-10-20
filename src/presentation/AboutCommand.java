package presentation;

public class AboutCommand implements Command {

  @Override
  public String execute(RequestContext requestContext) {
	String command = requestContext.getParameter("command");
	if (command.equals("about get")) {
	  return "about";
	}
	return "frobidden";
  }

}
