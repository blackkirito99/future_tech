package domain;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import datasource.OrderLockingMapper;
import datasource.UnitOfWork;

public class Order {

    private String orderNum;
    private int customerID;
    private Map < Product, Integer > orderProducts;
    private float totalPrice;
    private String currency;

    /*Use for temporary lock usage*/
    public Order(String orderNum) {
        setOrderNum(orderNum);
    }

    public Order(String orderNum, int customerID, boolean newCreated) {
        setOrderNum(orderNum);
        setCustomerID(customerID);

        // set as uninitialized
        setTotalPrice(-2);
        if (newCreated) {
            UnitOfWork uow = UnitOfWork.getCurrent();
            if (uow != null) {
                uow.registerNewObject(this);
            }
        } else {
            System.out.println(toString() + " is lazy loaded!");
        }
    }
    public static Order getOrder(String orderNum) {
        return OrderLockingMapper.getInstance().find(orderNum);
    }
    public static List < Order > getAllOrdersOf(int userID) {
        return OrderLockingMapper.getInstance().findOrderOfCustomer(userID);
    }

    public static void newOrder(Order order) {
        OrderLockingMapper.getInstance().insert(order);
    }
    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int cusomerID) {
        this.customerID = cusomerID;
    }

    public Map < Product, Integer > getProductCopies() {
        if (orderProducts == null)
            load();
        return orderProducts;
    }

    public void setProductCopies(Map < Product, Integer > orderProducts) {
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
        OrderLockingMapper.getInstance().getFull(orderNum);
    }

    /* For debugging*/
    @Override
    public String toString() {
        return "Order " + this.orderNum;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Order that = (Order) o;
        return orderNum.equals(that.getOrderNum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNum);
    }

}