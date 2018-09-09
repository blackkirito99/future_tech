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
@WebServlet("/about")
public class AboutServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @throws ServletException
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    response.setContentType("text/html");
    request.getRequestDispatcher("about.jsp").forward(request, response);
  }

}
