package domain;

import java.util.ArrayList;
import java.util.List;

import datasource.ProductMapper;
import datasource.ProductUnitOfWork;
import datasource.UserUnitOfWork;

public abstract class Product {

  private static final String categoryString = "Product";
  private int productID;
  private String name;
  private String brand;
  private String category;
  private double price;
  private Integer stockNumber;
  private String image;

  // only load productID, name, category and price at first
  public Product(int productID, String name, String category, double price, int stockNumber, String image,  boolean newCreated) {
    setProductID(productID);
    setName(name);
    setCategory(category);
    setPrice(price);
    setStockNumber(stockNumber);
    setImage(image);
    if(newCreated) {
      ProductUnitOfWork.getCurrent().registerNewObject(this);
    }else {
      System.out.println(toString() + " is lazy loaded!");
    }
  }

  public static Product getProduct(int id) {
    return ProductMapper.getProduct(id);
  }

  public static List<Product> getAvailableProducts() {
    List<Product> allProducts = ProductMapper.getAllProducts();
    List<Product> result = new ArrayList<>();
    for (Product product : allProducts) {
      if (product.getStockNumber() > 0) {
        result.add(product);
      }
    }
    return result;
  }

  public int getProductID() {
    // can skip this register
    ProductUnitOfWork puow = ProductUnitOfWork.getCurrent();
    if (puow != null) {
      puow.registerCleanObject(this);
    }
    return productID;
  }

  // product ID cannot be changed, except initilization, so set as private
  private void setProductID(int productID) {
    this.productID = productID;
  }

  public String getName() {
    // can skip this register
    ProductUnitOfWork.getCurrent().registerCleanObject(this);
    return name;
  }

  public void setName(String name) {
    if (this.name != null) {
      ProductUnitOfWork.getCurrent().registerDirtyObject(this);
    }
    this.name = name;
  }

  public String getBrand() {
    if (brand == null) {
      load();
    }
    // can skip this register
    ProductUnitOfWork.getCurrent().registerCleanObject(this);
    return brand;
  }

  public void setBrand(String brand) {
    if (this.brand != null) {
      ProductUnitOfWork.getCurrent().registerDirtyObject(this);
    }
    this.brand = brand;
  }

  public String getCategory() {
    // can skip this register
    ProductUnitOfWork.getCurrent().registerCleanObject(this);
    return categoryString;
  }

  public void setCategory(String category) {
    if (this.category != null) {
      ProductUnitOfWork.getCurrent().registerDirtyObject(this);
    }
    this.category = category;
  }

  public double getPrice() {
    // can skip this register
    ProductUnitOfWork puow = ProductUnitOfWork.getCurrent();
    if (puow != null) {
      puow.registerCleanObject(this);
    }
    return price;
  }

  public void setPrice(double price) {
    if (this.price != 0.0) {
      ProductUnitOfWork.getCurrent().registerDirtyObject(this);
    }
    this.price = price;
  }

  public int getStockNumber() {
    // can skip this register
    ProductUnitOfWork.getCurrent().registerCleanObject(this);
    return stockNumber;
  }

  public void setStockNumber(int stockNumber) {
    if (this.stockNumber != null) {
      ProductUnitOfWork.getCurrent().registerDirtyObject(this);
    }
    this.stockNumber = stockNumber;
  }

  public String getImage() {
    // can skip this register
    ProductUnitOfWork.getCurrent().registerCleanObject(this);
    return image;
  }

  public void setImage(String image) {
    if (this.image != null) {
      ProductUnitOfWork.getCurrent().registerDirtyObject(this);
    }
    this.image = image;
  }

  // load additional info from database
  public void load() {
    System.out.println(toString() + " is full loaded");
    ProductMapper.getFullProduct(this.getProductID());
  }

  /* For debugging*/
  @Override
  public String toString() {
    return "Product " + name;
  }

}
