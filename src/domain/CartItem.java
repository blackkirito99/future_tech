package domain;

import java.util.Objects;

import datasource.UnitOfWork;
import datasource.ProductLockingMapper;

public class CartItem {

    private int customerID;
    private int productID;
    private int quantity;

    /*Use for temporary lock usage*/
    public CartItem(int customerID, int productID) {
        setCustomerID(customerID);
        setProductID(productID);
    }
    public CartItem(int customerID, int productID, int quantity, boolean newCreated) {
        setCustomerID(customerID);
        setProductID(productID);
        setQuantity(quantity);
        // not sure about how to distinct between new created and loaded
        if (newCreated) {
        	UnitOfWork uow = UnitOfWork.getCurrent();
	    	if(uow == null) {
	    		uow.newCurrent();
	    	}
            UnitOfWork.getCurrent().registerNewObject(this);
    	}	
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getProductID() {
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
        return quantity;
    }

    public boolean setQuantity(int quantity) {
        boolean zero = false;
        if (this.quantity != 0 && this.quantity != quantity) {
            this.quantity = quantity > 0 ? quantity : 0;
            zero = this.quantity == 0;
        	UnitOfWork uow = UnitOfWork.getCurrent();
        	if(uow == null) {
        		uow.newCurrent();
        	}
            if (zero) {
                UnitOfWork.getCurrent().registerDeletedObject(this);
            } else {
                UnitOfWork.getCurrent().registerDirtyObject(this);
            }
        } else if (this.quantity == 0) {
            this.quantity = quantity;
        }
        return zero;
    }
    /*Use setQuantity to check unit of work*/
    public boolean removeQuantity(int quantity) {
        if (quantity < 0) {
            System.out.println("Negative change ,Error");
            return false;
        } else if (quantity == 0) {
            return false;
        }
        return setQuantity(this.quantity - quantity);
    }
    /*Use setQuantity to check unit of work*/
    public void addQuantity(int quantity) {
        int newQuantity = this.quantity + quantity;
        if (quantity < 0) {
            System.out.println("Negative change ,Error");
            return;
        } else if (quantity == 0) {
            return;
            // guard for out of stock
        } else if (this.quantity + quantity > ProductLockingMapper.getInstance()
            .find(Integer.toString(productID)).getStockNumber()) {
            System.out.println("You choose more than stock level.");
            newQuantity = ProductLockingMapper.getInstance()
                .find(Integer.toString(productID)).getStockNumber();
        }
        setQuantity(newQuantity);
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