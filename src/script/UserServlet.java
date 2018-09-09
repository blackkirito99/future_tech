package script;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datasource.ProductUnitOfWork;
import datasource.UserMapper;
import domain.User;

import java.io.IOException;

/**
 * Servlet implementation class ViewBooks
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @throws ServletException
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    try {
      Integer id = Integer.parseInt(request.getParameter("id"));
      User user = UserMapper.getFullUser(id);
      response.setContentType("text/html");
      if (user != null) {
        request.setAttribute("user", user);
        request.getRequestDispatcher("user.jsp").forward(request, response);
      } else {
        System.err.println("user not found");
      }
    } catch (NumberFormatException e) {
      response.sendRedirect("missing.jsp");
    }
  }

}
