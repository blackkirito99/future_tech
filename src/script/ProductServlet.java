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
@WebServlet("/product")
public class ProductServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @throws ServletException
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ProductUnitOfWork.newCurrent();
    try {
      Integer id = Integer.parseInt(request.getParameter("id"));
      Product product = Product.getProduct(id);
      response.setContentType("text/html");
      if (product != null) {
        request.setAttribute("product", product);
        request.getRequestDispatcher("product.jsp").forward(request, response);
      } else {
        System.err.println("product not found");
      }
    } catch (NumberFormatException e) {
      response.sendRedirect("missing.jsp");
    }
  }

}
