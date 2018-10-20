package domain;

import java.util.Map;

// for retailer usage?
public class Inventory {

    private Map < Product, Integer > stock;

    public Inventory(Map < Product, Integer > stock) {
        this.stock = stock;
    }

    public Map < Product, Integer > getStock() {
        return stock;
    }

    public void setStock(Map < Product, Integer > stock) {
        this.stock = stock;
    }

}