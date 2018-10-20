package domain;

import java.util.List;

import auth.AppSession;
import datasource.UnitOfWork;

public class Customer extends User {

    private String address;
    private List < Order > orders;
    private ShoppingCart cart;
    public final static String TypeString = AppSession.CUSTOMER_ROLE;


    public Customer(int id, String username, String type, String email, String avatar, boolean newCreated) {
        super(id, username, type, email, avatar, newCreated);
        setType(TypeString);
    }

    public String getAddress() {
        if (address == null) {
            load();
        }

        return address;
    }

    public void setAddress(String address) {
        if (this.address != null) {
        	UnitOfWork uow = UnitOfWork.getCurrent();
        	if(uow == null) {
        		uow.newCurrent();
        	}
            uow.registerDirtyObject(this);
        }
        this.address = address;
    }
    @Override
    public String getType() {
        return TypeString;
    }
    public List < Order > getOrders() {
        if (orders == null) {
            orders = Order.getAllOrdersOf(getUserID());
        }
        return orders;
    }

    // only use for initialization, no change to order allowed further more
    public void setOrders(List < Order > orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public ShoppingCart getShoppingCart() {
        cart = new ShoppingCart(getUserID());
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