package domain;

import java.util.Objects;

import datasource.CartItemUnitOfWork;
import datasource.UserUnitOfWork;

public class CartItem {

  private int customerID;
  private int productID;
  private int quantity;

  public CartItem(int customerID, int productID, int quantity, boolean newCreated) {
    setCustomerID(customerID);
    setProductID(productID);
    setQuantity(quantity);
    // not sure about how to distinct between new created and loaded
    if(newCreated)
      CartItemUnitOfWork.getCurrent().registerNewObject(this);
  }

  public int getCustomerID() {
    // can skip this register
    CartItemUnitOfWork cuow = CartItemUnitOfWork.getCurrent();
    if (cuow != null) {
      cuow.registerCleanObject(this);
    }
    return customerID;
  }

  public int getProductID() {
    // can skip this register
    CartItemUnitOfWork cuow = CartItemUnitOfWork.getCurrent();
    if (cuow != null) {
      cuow.registerCleanObject(this);
    }
    return productID;
  }

  // relation cannot be changed after setup, so set as private methods
  private void setCustomerID(int customerID) {
    this.customerID = customerID;
  }

  private void setProductID(int productID) {
    this.productID = productID;
  }

  public int getQuantity() {
    // can skip this register
    CartItemUnitOfWork cuow = CartItemUnitOfWork.getCurrent();
    if (cuow != null) {
      cuow.registerCleanObject(this);
    }
    return quantity;
  }

  public void setQuantity(int quantity) {
    if (this.quantity != 0) {
      CartItemUnitOfWork cuow = CartItemUnitOfWork.getCurrent();
      if (cuow != null) {
        cuow.registerDirtyObject(this);
      }
    }
    this.quantity = quantity;
  }

  public boolean removeQuantity(int quantity) {
    // cannot remove below 0
    this.quantity = (this.quantity - quantity) > 0 ? (this.quantity - quantity) : 0;
    boolean zero = this.quantity == 0;
    if (this.quantity == 0) {
      CartItemUnitOfWork.getCurrent().registerDeletedObject(this);
    }else {
    	CartItemUnitOfWork.getCurrent().registerDirtyObject(this);
    }
    return zero;
  }

  public void addQuantity(int quantity) {
    this.quantity+= quantity;
    CartItemUnitOfWork.getCurrent().registerDirtyObject(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    CartItem that = (CartItem) o;
    return customerID == that.customerID && productID == that.productID;
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerID, productID);
  }

  /* For debugging*/
  @Override
  public String toString() {
     return "Cart item " + customerID + "-" + productID;
  }
}
