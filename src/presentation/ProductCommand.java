package presentation;

import java.util.List;

import auth.AppUtils;
import datasource.UnitOfWork;
import domain.Computer;
import domain.Product;
import domain.Smartphone;
import service.ApplicationService;

public class ProductCommand implements Command {

  @Override
  public String execute(RequestContext requestContext) {
    Product.getAvailableProductsOfBrand("Apple");
    String command = requestContext.getParameter("command");
    if (command.equals("product get")) {
      try {
        Integer id = Integer.parseInt(requestContext.getParameter("id"));
        Product product = Product.getProduct(id);
        if (product != null) {
          requestContext.setAttribute("product", product);
        } else {
          System.err.println("product not found");
        }
      } catch (NumberFormatException e) {
        return "error";
      }
      List<String> categories = Product.getAllCategories();
      requestContext.setAttribute("categories", categories);
      System.out.println("here");
      return "product";
    } else if (command.equals("product put")) {

      if(AppUtils.isValidRetailer()) {
        Integer id = (Integer) Integer.parseInt(requestContext.getParameter("id"));
        String name = (String) requestContext.getParameter("name");
        String brand = (String) requestContext.getParameter("brand");
        String category = (String) requestContext.getParameter("category");
        Double price = (Double) Double.parseDouble(requestContext.getParameter("price"));
        Integer stock = (Integer) Integer.parseInt(requestContext.getParameter("stock"));

        System.out.println("product update: {name: "
            + name + ", brand: "
            + brand + ", category: "
            + category + ", price: "
            + price.toString() + ", stock: "
            + stock.toString() + "}"
        );

        // get the product to be updated, and update all data
        ApplicationService.updateProductInformtion(id, name, brand, category, price, stock);
        requestContext.setAttribute("product", Product.getProduct(id));
        return "product";
      }else {
        return "forbidden";
      }
    } else if (command.equals("product delete")) {
      Integer id = (Integer) Integer.parseInt(requestContext.getParameter("id"));
      String name = (String) requestContext.getParameter("name");
      String brand = (String) requestContext.getParameter("brand");
      String category = (String) requestContext.getParameter("category");
      Double price = (Double) Double.parseDouble(requestContext.getParameter("price"));
      Integer stock = (Integer) Integer.parseInt(requestContext.getParameter("stock"));

      System.out.println("product delete: {name: "
          + name + ", brand: "
          + brand + ", category: "
          + category + ", price: "
          + price.toString() + ", stock: "
          + stock.toString() + "}"
      );

      // get the product to be deleted
      Product product = Product.getProduct(id);
      Product.deleteProduct(product);
      
      List<Product> products = Product.getAvailableProducts();
      requestContext.setAttribute("products", products);
      return "store";
    } else if (command.equals("product patch")) {
      // display create new product page
      List<String> categories = Product.getAllCategories();
      requestContext.setAttribute("categories", categories);
      return "new";
    } else if (command.equals("product post")) {
      // create a new product
      Integer id = Product.getNewId();
      String name = (String) requestContext.getParameter("name");
      String image = (String) requestContext.getParameter("image");
      String brand = (String) requestContext.getParameter("brand");
      String category = (String) requestContext.getParameter("category");
      Double price = (Double) Double.parseDouble(requestContext.getParameter("price"));
      Integer stock = (Integer) Integer.parseInt(requestContext.getParameter("stock"));

      System.out.println("product create: {name: "
          + name + ", brand: "
          + brand + ", category: "
          + category + ", price: "
          + price.toString() + ", stock: "
          + stock.toString() + "}"
      );

      Product product;
      if (category.equals("SM")) {
        product = new Smartphone(id, name, category, price, stock, image, true);
        product.setBrand(brand);
        Product.newProduct(product);
      } else if (category.equals("PC")) {
        product = new Computer(id, name, category, price, stock, image, true);
        product.setBrand(brand);
        Product.newProduct(product);
      }
    }
    // product get
    List<Product> products = Product.getAvailableProducts();
    if (products != null) {
      requestContext.setAttribute("products", products);
      return "products";
    } else {
      System.err.println("error no products");
      return "error";
    }
  }

}
