package presentation;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Dispatcher {

  public void dispatch(HttpServletRequest request, HttpServletResponse response, String page) {

      RequestDispatcher rd = null;

      rd = request.getRequestDispatcher(page);
      try {
          rd.forward(request, response);
      } catch (ServletException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

}