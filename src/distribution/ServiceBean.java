package distribution;

import java.io.InputStream;
import java.io.OutputStream;

public class ServiceBean {

  public static void getUser(UserDTO user, OutputStream outputStream) {
    UserDTO.toXML(user, outputStream);
  }
  
  public static UserDTO fromUser(InputStream inputStream) {
    return UserDTO.fromXML(inputStream);
  }
  
  public static void getOrder(OrderDTO order, OutputStream outputStream) {
    OrderDTO.toXML(order, outputStream);
  }
  
  public static OrderDTO fromOrder(InputStream inputStream) {
    return OrderDTO.fromXML(inputStream);
  }
  
  public static void getProduct(ProductDTO product, OutputStream outputStream) {
    ProductDTO.toXML(product, outputStream);
  }
  
  public static ProductDTO fromProduct(InputStream inputStream) {
    return ProductDTO.fromXML(inputStream);
  }
  
  public static void getCart(ShoppingCartDTO cart, OutputStream outputStream) {
    ShoppingCartDTO.toXML(cart, outputStream);
  }
  
  public static ShoppingCartDTO fromCart(InputStream inputStream) {
    return ShoppingCartDTO.fromXML(inputStream);
  }
 
}
