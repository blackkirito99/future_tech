package distribution;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class ShoppingCartDTO {

    private int CustomerID;
    private List <Integer> productIDs;
    private List <Integer> productCounts;

    public static void toXML(ShoppingCartDTO shoppingCartDTO, OutputStream outputStream) {
        XMLEncoder encoder = new XMLEncoder(outputStream);
        encoder.writeObject(shoppingCartDTO);
        encoder.close();
    }


    public static ShoppingCartDTO fromXML(InputStream inputStream) {
        XMLDecoder decoder = new XMLDecoder(inputStream);
        ShoppingCartDTO result = (ShoppingCartDTO) decoder.readObject();
        decoder.close();
        return result;
    }


    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public List <Integer> getProductIDs() {
        return productIDs;
    }

    public void setProductIDs(List <Integer> productIDs) {
        this.productIDs = productIDs;
    }

    public List <Integer> getProductCounts() {
        return this.productCounts;
    }

    public void setProductCounts(List <Integer> productCounts) {
        this.productCounts = productCounts;
    }

    // TODO:
    public void setProductCount(int productID, int count) {
        int index = productIDs.indexOf(productID);
        if (index < 0) {
            if (productCounts.size() == productIDs.size()) {
                productIDs.add(productID);
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
    public int getProductCount(int productID) {
        int index = productIDs.indexOf(productID);
        if (index < 0) {
            return 0;
        } else if (index >= productCounts.size()) {
            System.out.println("List size different");
            return 0;
        } else {
            return productCounts.get(index);
        }
    }

}