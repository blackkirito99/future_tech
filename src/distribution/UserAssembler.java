package distribution;

import java.util.ArrayList;
import java.util.List;

import datasource.Registry;
import datasource.UserLockingMapper;
import domain.Customer;
import domain.Order;
import domain.Retailer;
import domain.User;

public class UserAssembler {
    public UserDTO writeDTO(User user) {
        UserDTO result = new UserDTO();
        String type = user.getType();

        result.setUserID(user.getUserID());
        result.setLastName(user.getLastName());
        result.setFirstName(user.getFirstName());
        result.setUsername(user.getUsername());
        result.setPassword(user.getEmail());
        result.setType(type);
        result.setAvatar(user.getAvatar());

        if (type.equals(Customer.TypeString)) {
            result.setAddress(((Customer) user).getAddress());

            result.setShoppingCartDTO(new ShoppingCartAssembler().writeDTO(((Customer) user).getShoppingCart()));

            List <OrderDTO> orderDTOs = new ArrayList <> ();
            OrderAssembler orderAssembler = new OrderAssembler();
            for (Order order: ((Customer) user).getOrders()) {
                orderDTOs.add(orderAssembler.writeDTO(order));
            }
            result.setOrderDTOs(orderDTOs);
        }
        return result;
    }

    public User readDTO(UserDTO dto) {
        User result = UserLockingMapper.getInstance().find(Integer.toString(dto.getUserID()));
        String type = dto.getType();
        if (result != null) {
            result.setUsername(dto.getUsername());
            result.setPassword(dto.getEmail());
            result.setAvatar(dto.getAvatar());
        }
        if (type.equals(Customer.TypeString)) {
            if (result == null) {
                result = new Customer(dto.getUserID(), dto.getUsername(), type, dto.getEmail(),
                    dto.getAvatar(), true);
            }
            result.setPassword(dto.getPassword());
            result.setLastName(dto.getLastName());
            result.setFirstName(dto.getFirstName());
            ((Customer) result).setAddress(dto.getAddress());

            ((Customer) result).setShoppingCart(new ShoppingCartAssembler().readDTO(dto.getShoppingCartDTO()));
            List <Order> orders = new ArrayList <Order> ();
            OrderAssembler orderAssembler = new OrderAssembler();
            for (OrderDTO orderDTO: dto.getOrderDTOs()) {
                orders.add(orderAssembler.readDTO(orderDTO));
            }
            ((Customer) result).setOrders(orders);
            Registry.addUser(result);
        } else if (type.equals(Retailer.TypeString)) {
            if (result == null) {
                result = new Retailer(dto.getUserID(), dto.getUsername(), type, dto.getEmail(),
                    dto.getAvatar(), true);
            }
            result.setPassword(dto.getPassword());
            result.setLastName(dto.getLastName());
            result.setFirstName(dto.getFirstName());
            Registry.addUser(result);
        } else {
            System.out.println("WRONG TYPE");
            result = null;
        }
        return result;
    }
}