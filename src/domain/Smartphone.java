package domain;

import datasource.ProductUnitOfWork;

public class Smartphone extends Product {

  private static final String categoryString = "SmartPhone";
  private double screenSize;

  public Smartphone(int productID, String name, String category, double price, int stockNumber, String image,  boolean newCreated) {
    super(productID, name, category, price, stockNumber, image, newCreated);
  }

  public double getScreenSize() {
    if (screenSize == 0) {
      load();
    }
    return screenSize;
  }

  public void setScreenSize(double screenSize) {
    if (this.screenSize != 0) {
      ProductUnitOfWork.getCurrent().registerDirtyObject(this);
    }
    this.screenSize = screenSize;
  }

  @Override
  public String getCategory() {
    return categoryString;
  }

}
