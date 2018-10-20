package distribution;

import java.util.List;

import datasource.CartLockingMapper;
import datasource.Registry;
import datasource.UserLockingMapper;
import domain.CartItem;
import domain.Customer;
import domain.ShoppingCart;
import domain.User;

public class ShoppingCartAssembler {
    public ShoppingCartDTO writeDTO(ShoppingCart shoppingCart) {
        ShoppingCartDTO result = new ShoppingCartDTO();

        result.setCustomerID(shoppingCart.getCustomerID());
        List <CartItem> cartItems = shoppingCart.getAllItemsInCart();
        for (CartItem ci: cartItems) {
            result.setProductCount(ci.getCustomerID(), ci.getQuantity());
        }
        return result;
    }


    public ShoppingCart readDTO(ShoppingCartDTO dto) {
        User user = UserLockingMapper.getInstance().find(Integer.toString(dto.getCustomerID()));
        if (user.getType().equals(Customer.TypeString)) {
            ShoppingCart shoppingCart = ((Customer) user).getShoppingCart();
            List <CartItem> cartItems = shoppingCart.getAllItemsInCart();
            List <Integer> productIDs = dto.getProductIDs();
            // mark those deleted in shopping cart (not include in DTO, but in cache)
            for (CartItem ci: cartItems) {
                if (!productIDs.contains(ci.getProductID())) {
                    ci.setQuantity(0);
                }
            }
            for (int id: productIDs) {
                CartItem ci = CartLockingMapper.getInstance()
                    .find(Integer.toString(dto.getCustomerID()), Integer.toString(id));
                if (ci != null) {
                    ci.setQuantity(dto.getProductCount(id));
                } else {
                    ci = new CartItem(dto.getCustomerID(), id, dto.getProductCount(id), true);
                    Registry.addCartItem(ci);
                    cartItems.add(ci);
                }
            }
            return shoppingCart;
        } else {
            System.out.println("Wrong user type");
            return null;
        }
    }
}