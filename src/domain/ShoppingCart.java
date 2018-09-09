package domain;

import java.util.ArrayList;
import java.util.List;

import datasource.CartMapper;

public class ShoppingCart {

  private List<CartItem> items;
  private int CustomerID;

  public ShoppingCart(int CustomerID) {
    //this.items = new ArrayList<>();
    this.items = CartMapper.findCartOf(CustomerID);
    if (this.items == null) {
      this.items = new ArrayList<>();
    }
    this.CustomerID = CustomerID;
  }

  public List<CartItem> getAllItemsInCart() {
    return items;
  }

  //get only those with more than 1 count
  public List<CartItem> getValidCartItems(){
     List<CartItem> result = new ArrayList<>();
     for(CartItem item : items) {
         if(item.getQuantity() > 0) {
             result.add(item);
         }
     }
     return result;
  }

  public void setItemsInCart(List<CartItem> items) {
    this.items = items;
  }

  public void addItem(Product item, int quantity) {
    for (CartItem cartItem : items) {
      if (cartItem.getProductID() == item.getProductID()) {
        cartItem.addQuantity(quantity);
        // test
        CartMapper.update(cartItem);
        return;
      }
    }
    items.add(new CartItem(CustomerID, item.getProductID(), quantity, true));
  }

  public void decreaseItemCount(Product item, int quantity) {
    int deleteIndex = -1;
    for (CartItem cartItem : items) {
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
    for (CartItem cartItem : items) {
      if (cartItem.getProductID() == item.getProductID()) {
        cartItem.addQuantity(quantity);
        break;
      }
    }
    // guard for quantity over stock count
  }


  public static ShoppingCart getCartOf(int userID) {
    ShoppingCart result = new ShoppingCart(userID);
    List<CartItem> items = CartMapper.findCartOf(userID);
    result.setItemsInCart(items);
    return result;
  }

  public void updateShoppingCart(int userID, Product item) {
//        CartFinder finder = new CartFinder();
//        CartItemGateway cartRecord = finder.findItem(userID, Integer.parseInt(item.getProductID()));
//        if (cartRecord == null) {
//                cartRecord = new CartItemGateway(userID, Integer.parseInt(item.getProductID()),items.get(item));
//                cartRecord.insert();
//        }
//        else{
//            cartRecord.setQuantity(items.get(item));
//
//            cartRecord.updateQuantity();
//        }
  }

  public int getCustomerID() {
    return CustomerID;
  }

  public void setCustomerID(int customerID) {
    CustomerID = customerID;
  }
}
