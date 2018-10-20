package distribution;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class OrderDTO {

    private String orderNum;
    private int customerID;
    private List <ProductDTO> productDTOs;
    private List <Integer> productCounts;
    private float totalPrice;
    private String currency;

    public static void toXML(OrderDTO orderDTO, OutputStream outputStream) {
        XMLEncoder encoder = new XMLEncoder(outputStream);
        encoder.writeObject(orderDTO);
        encoder.close();
    }


    public static OrderDTO fromXML(InputStream inputStream) {
        XMLDecoder decoder = new XMLDecoder(inputStream);
        OrderDTO result = (OrderDTO) decoder.readObject();
        decoder.close();
        return result;
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

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public List < ProductDTO > getProductDTOs() {
        return productDTOs;
    }

    public void setProductDTOs(List <ProductDTO> productDTOs) {
        this.productDTOs = productDTOs;
    }

    public List <Integer> getProductCounts() {
        return this.productCounts;
    }

    public void setProductCounts(List <Integer> productCounts) {
        this.productCounts = productCounts;
    }


    public void setProductCount(ProductDTO productDTO, int count) {
        int index = productDTOs.indexOf(productDTO);
        if (index < 0) {
            if (productCounts.size() == productDTOs.size()) {
                productDTOs.add(productDTO);
                productCounts.add(count);
            } else {
                System.out.println("List size different");
            }
        } else if (index >= productCounts.size()) {
            System.out.println("List size different");
        } else {
            productCounts.set(index, count);
        }
    }

    // TODO:
    public int getProductCount(ProductDTO productDTO) {
        int index = productDTOs.indexOf(productDTO);
        if (index < 0) {
            return 0;
        } else if (index >= productCounts.size()) {
            System.out.println("List size different");
            return 0;
        } else {
            return productCounts.get(index);
        }
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}