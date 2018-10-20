package distribution;

import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import datasource.OrderLockingMapper;
import datasource.UnitOfWork;
import domain.Order;

public class OrderServiceBean {


    public OrderDTO getOrder(String OrderNum) throws RemoteException {
        Order order = OrderLockingMapper.getInstance().find(OrderNum);
        // Call one method to get object fully loaded
        // as in distributed system, we need to save 
        // time to transfer data
        order.getTotalPrice();
        return new OrderAssembler().writeDTO(order);
    }

    public void getOrderXml(String OrderNum, OutputStream outputStream) throws RemoteException {
        OrderDTO dto = getOrder(OrderNum);
        OrderDTO.toXML(dto, outputStream);
    }

    public List <OrderDTO> getOrderOfCustomer(String id) throws RemoteException {
        List <Order> orders = OrderLockingMapper.getInstance().findOrderOfCustomer(Integer.parseInt(id));
        List <OrderDTO> orderDTOs = new ArrayList <OrderDTO> ();
        OrderAssembler orderAssembler = new OrderAssembler();
        for (Order order: orders) {
            // Call one method to get object fully loaded
            // as in distributed system, we need to save 
            // time to transfer data
            order.getTotalPrice();
            orderDTOs.add(orderAssembler.writeDTO(order));
        }
        return orderDTOs;
    }

    public void getOrderOfCustomerXml(String id, OutputStream outputStream) throws RemoteException {
        for (OrderDTO dto: getOrderOfCustomer(id)) {
            OrderDTO.toXML(dto, outputStream);
        }
    }

    public void insertOrder(OrderDTO dto) throws RemoteException {
        UnitOfWork ouow = new UnitOfWork();
        // already refer to registry in assembler, so no need to further check
        Order order = new OrderAssembler().readDTO(dto);
        ouow.commit();
    }

    public void insertOrder(InputStream inputStream) throws RemoteException {
        OrderDTO dto = OrderDTO.fromXML(inputStream);
        insertOrder(dto);
    }

}