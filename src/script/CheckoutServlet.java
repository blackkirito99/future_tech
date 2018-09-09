package script;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datasource.CartItemUnitOfWork;
import datasource.OrderMapper;
import datasource.OrderUnitOfWork;
import datasource.ProductUnitOfWork;
import domain.ApplicationService;
import domain.Order;
import domain.Product;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet implementation class ViewBooks
 */
@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  /**
   * @throws ServletException
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    OrderUnitOfWork.newCurrent();
    CartItemUnitOfWork.newCurrent();
    OrderUnitOfWork ouow = OrderUnitOfWork.getCurrent();
    CartItemUnitOfWork ciuow = CartItemUnitOfWork.getCurrent();
    ApplicationService.checkout(1);
    ouow.commit();
    ciuow.commit();
    List<Order> orders = OrderMapper.findOrdersOf(1);
    if (orders != null) {
      Order order = orders.get(0);
      response.setContentType("text/html");
      request.setAttribute("order", order);
      request.getRequestDispatcher("checkout.jsp").forward(request, response);
    } 
  }

}
