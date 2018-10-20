package presentation;

import java.util.List;

import domain.Product;

public class SearchCommand implements Command {

  @Override
  public String execute(RequestContext requestContext) {
    String command = requestContext.getParameter("command");
    if (command.equals("search post")) {
      String query = requestContext.getParameter("query");
      requestContext.setAttribute("query", query);
      List<Product> products = Product.getAvailableProductsOfQuery(query);
      requestContext.setAttribute("products", products);
    }
    // get search page
    return "search";
  }

}
