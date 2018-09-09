package script;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datasource.CartItemUnitOfWork;
import datasource.UserUnitOfWork;
import domain.ShoppingCart;
import domain.User;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

  private static final long serialVersionUID = 2L;

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    UserUnitOfWork.newCurrent();
    CartItemUnitOfWork.newCurrent();
    List<User> users = User.getAllUsers();
    response.setContentType("text/html");
    if (users != null) {
      request.setAttribute("users", users);
      request.getRequestDispatcher("users.jsp").forward(request, response);
    } else {
      System.err.println("error no users");
    }
  }

}
