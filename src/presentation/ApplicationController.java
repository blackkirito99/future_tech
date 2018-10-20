package presentation;

public class ApplicationController {

  public String process(RequestContext requestContext) {
      String view = null;
      Command command = null;
      CommandHelper commandHelper = null;

      commandHelper = new CommandHelper();
      command = commandHelper.getCommand(requestContext.getRequestUri());
      view = command.execute(requestContext);

      return view;
  }

  public String mapViewToPage(String view) {
      if (view.equals("")) {
        return "view/index.jsp";
      }
      return "view/" + view + ".jsp";
  }

}