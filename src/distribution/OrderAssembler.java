package distribution;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import datasource.OrderLockingMapper;
import datasource.Registry;
import domain.Order;
import domain.Product;


public class OrderAssembler {
    public OrderDTO writeDTO(Order order) {
        OrderDTO result = new OrderDTO();

        result.setCustomerID(order.getCustomerID());
        Map <Product, Integer> productCounts = order.getProductCopies();
        for (Product product: productCounts.keySet()) {
            result.setProductCount(new ProductAssembler().writeDTO(product), productCounts.get(product));
        }
        return result;
    }

    public Order readDTO(OrderDTO dto) {
        Order result = OrderLockingMapper.getInstance().find(dto.getOrderNum());
        if (result != null) {
            // should not update allowed to customerID and orderNum of the order
            //result.setCustomerID(dto.getCustomerID());
            //result.setOrderNum(dto.getOrderNum());
        } else {
            result = new Order(dto.getOrderNum(), dto.getCustomerID(), true);
            Registry.addOrder(result);
        }
        result.setTotalPrice(dto.getTotalPrice());
        result.setCurrency(dto.getCurrency());

        // precondition: all productID can be found in DB
        // that is customer can not make order on product not exist
        // and retailer can not delete product purchased by customer
        Map <Product, Integer> productCounts = new HashMap <Product, Integer> ();
        List <ProductDTO> productDTOs = dto.getProductDTOs();
        for (ProductDTO productDTO: productDTOs) {
            Product product = new ProductAssembler().readDTO(productDTO);
            if (product == null) {
                System.out.println("Product not exist, precondition not hold");
            }
            productCounts.put(product, dto.getProductCount(productDTO));
        }
        result.setProductCopies(productCounts);
        return result;
    }
}