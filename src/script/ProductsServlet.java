package script;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datasource.ProductUnitOfWork;
import domain.Product;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet implementation class ViewBooks
 */
@WebServlet("/products")
public class ProductsServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @throws ServletException
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ProductUnitOfWork.newCurrent();
    List<Product> products = Product.getAvailableProducts();
    response.setContentType("text/html");
    if (products != null) {
      request.setAttribute("products", products);
      request.getRequestDispatcher("products.jsp").forward(request, response);
    } else {
      System.err.println("error no products");
    }
  }

}
