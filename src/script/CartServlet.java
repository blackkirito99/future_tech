package script;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datasource.CartItemUnitOfWork;
import datasource.ProductMapper;
import datasource.ProductUnitOfWork;
import datasource.Registry;
import datasource.UserMapper;
import domain.ApplicationService;
import domain.CartItem;
import domain.Product;
import domain.ShoppingCart;
import domain.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class ViewBooks
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @throws ServletException
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ProductUnitOfWork.newCurrent();
    List<CartItem> cartItems = ShoppingCart.getCartOf(1).getValidCartItems();
    response.setContentType("text/html");
    if (cartItems != null) {
      request.setAttribute("cartItems", cartItems);
      request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if (request.getParameter("delete") != null) {
      doDelete(request, response);
    } else if (request.getParameter("put") != null) {
      doPut(request, response);
    } else {
      CartItemUnitOfWork.newCurrent();
      Integer id = Integer.parseInt(request.getParameter("id"));
      // assume customer with id 1 is logged in
      ApplicationService.addToCart(1, id, 1);
      CartItemUnitOfWork.getCurrent().commit();
      doGet(request, response);
    }
  }

  protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    CartItemUnitOfWork.newCurrent();
    Integer id = Integer.parseInt(request.getParameter("put"));
    
    // assume customer with id 1 is logged in
    ApplicationService.increaseSingleProductInCart(1, id, 1);
    
    CartItemUnitOfWork.getCurrent().commit();
    doGet(request, response);
  }

  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    CartItemUnitOfWork.newCurrent();
    Integer id = Integer.parseInt(request.getParameter("delete"));
    
    // assume customer with id 1 is logged in
    ApplicationService.decreaseSingleProductInCart(1, id, 1);
   
    CartItemUnitOfWork.getCurrent().commit();
    doGet(request, response);
  }

}
