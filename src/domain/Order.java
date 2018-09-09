package domain;

import java.util.Map;

import datasource.OrderMapper;
import datasource.OrderUnitOfWork;

public class Order {

  private String orderNum;
  private int customerID;
  private Map<Product, Integer> orderProducts;
  private float totalPrice;
  private String currency;

  public Order(String orderNum, int customerID, boolean newCreated) {
    setOrderNum(orderNum);
    setCusomerID(customerID);
    // set as uninitialized
    setTotalPrice(-1);
    if (newCreated) {
      OrderUnitOfWork ouow = OrderUnitOfWork.getCurrent();
      if (ouow != null) {
        ouow.registerNewObject(this);
      }
    } else {
      System.out.println(toString() + " is lazy loaded!");
    }
  }

   public String getOrderNum() {
       return orderNum;
   }

   private void setOrderNum(String orderNum) {
       this.orderNum = orderNum;
   }

   public int getCusomerID() {
       return customerID;
   }

   private void setCusomerID(int cusomerID) {
       this.customerID = cusomerID;
   }

   public Map<Product, Integer> getProductCopies() {
       if (orderProducts == null)
           load();
       return orderProducts;
   }

   public void setProductCopies(Map<Product, Integer> orderProducts) {
       this.orderProducts = orderProducts;
   }

   public float getTotalPrice() {
       if (totalPrice < 0)
           load();
       return totalPrice;
   }

   public void setTotalPrice(float totalPrice) {
       this.totalPrice = totalPrice;
   }

   public String getCurrency() {
       if (currency == null)
           load();
       return currency;
   }

   public void setCurrency(String currency) {
       this.currency = currency;
   }

   private void load() {
       System.out.println(toString() + " is full loaded!");
       OrderMapper.getFullOrder(orderNum);
   }

   /* For debugging*/
   @Override
   public String toString() {
       return "Order " + this.orderNum;
   }

}
