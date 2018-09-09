package domain;

import java.util.List;

import datasource.UserUnitOfWork;

public class Customer extends User {

  private String address;
  private List<Order> orders;
  private ShoppingCart cart;

  public Customer(int id, String username, String type, String email, String avatar, boolean newCreated) {
    super(id, username, type, email,avatar, newCreated);
  }

  public String getAddress() {
    if (address == null) {
      load();
    }
    // can skip this register
    UserUnitOfWork.getCurrent().registerCleanObject(this);
    return address;
  }

  public void setAddress(String address) {
    if (this.address != null) {
      UserUnitOfWork uuow = UserUnitOfWork.getCurrent();
      if (uuow != null) {
        uuow.registerDirtyObject(this);
      }
    }
    this.address = address;
  }

  public List<Order> getOrders() {
    return orders;
  }

  // only use for initialization, no change to order allowed further more
  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  public void addOrder(Order order) {
    this.orders.add(order);
  }

  public ShoppingCart getShoppingCart(ShoppingCart cart) {
    return this.cart;
  }

  // only use for initialization, customer side shopping cart change use
  // getShoppingCart().xxx()
  public void setShoppingCart(ShoppingCart cart) {
    // no need to register here
    // CartItems are registered at lower level
    this.cart = cart;
  }

}
