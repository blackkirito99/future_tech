package distribution;

import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;


import datasource.UnitOfWork;
import datasource.UserLockingMapper;
import domain.Customer;
import domain.ShoppingCart;

public class ShoppingCartServiceBean {

    public ShoppingCartDTO getShoppingCartOfCustomer(String id) {
        ShoppingCart shoppingCart = ((Customer) UserLockingMapper.getInstance().find(id)).getShoppingCart();
        return new ShoppingCartAssembler().writeDTO(shoppingCart);
    }

    public void getShoppingCartOfCustomerXml(String id, OutputStream outputStream) throws RemoteException {
        ShoppingCartDTO dto = getShoppingCartOfCustomer(id);
        ShoppingCartDTO.toXML(dto, outputStream);
    }

    public void updateShoppingCart(ShoppingCartDTO dto) throws RemoteException {
        UnitOfWork ciuw = UnitOfWork.getCurrent();
        // already refer to registry in assembler, so no need to further check
        ShoppingCart cartItems = new ShoppingCartAssembler().readDTO(dto);
        ciuw.commit();
    }

    public void updateShoppingCart(InputStream inputStream) throws RemoteException {
        ShoppingCartDTO dto = ShoppingCartDTO.fromXML(inputStream);
        updateShoppingCart(dto);
    }
}