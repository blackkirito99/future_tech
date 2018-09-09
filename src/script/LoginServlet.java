package script;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// leave for feature b
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  // script to view cart
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    response.setContentType("text/html");
    request.getRequestDispatcher("login.jsp").forward(request, response);
  }

}
