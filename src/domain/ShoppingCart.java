package domain;
import java.util.List;

import datasource.CartLockingMapper;

public class ShoppingCart {

    private List < CartItem > items;
    private int customerID;

    public ShoppingCart(int customerID) {
        this.customerID = customerID;
        fetchItemsInCart();
    }

    public List < CartItem > getAllItemsInCart() {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getQuantity() <= 0) {
                items.remove(i);
                i--;
            }
        }
        return items;
    }


    public void fetchItemsInCart() {
        this.items = CartLockingMapper.getInstance().findCartOfCustomer(customerID);
    }

    public static ShoppingCart getCartOf(int customerID) {
        ShoppingCart result = new ShoppingCart(customerID);
        List < CartItem > items = CartLockingMapper.getInstance().findCartOfCustomer(customerID);
        result.setItemsInCart(items);
        return result;
    }

    public void setItemsInCart(List < CartItem > items) {
        this.items = items;
    }

    public void addItem(Product item, int quantity) {
        for (CartItem cartItem: items) {
            if (cartItem.getProductID() == item.getProductID()) {
                cartItem.addQuantity(quantity);
                return;
            }
        }
        items.add(new CartItem(customerID, item.getProductID(), quantity, true));
    }

    public void decreaseItemCount(Product item, int quantity) {
        int deleteIndex = -1;
        for (CartItem cartItem: items) {
            if (cartItem.getProductID() == item.getProductID()) {
                if (cartItem.removeQuantity(quantity)) {
                    items.indexOf(cartItem);
                }
                break;
            }
        }
        if (deleteIndex > 0) {
            items.remove(deleteIndex);
        }
    }

    public void increaseItemCount(Product item, int quantity) {
        for (CartItem cartItem: items) {
            if (cartItem.getProductID() == item.getProductID()) {
                cartItem.addQuantity(quantity);
                break;
            }
        }
    }


    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}