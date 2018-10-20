package presentation;

import java.util.ArrayList;
import java.util.List;

import domain.Computer;
import domain.Product;
import domain.Smartphone;

public class CategoryCommand implements Command {

  @Override
  public String execute(RequestContext requestContext) {
    String command = requestContext.getParameter("command");
    if (command.equals("category get")) {
      String category = requestContext.getParameter("category");
      List<Product> products = Product.getAvailableProductsOfCategory(category);
      requestContext.setAttribute("category", category);
      requestContext.setAttribute("products", products);
      return "category";
    }
    List<String> categories = Product.getAllCategories();
    requestContext.setAttribute("categories", categories);
    return "categories";
  }

}
