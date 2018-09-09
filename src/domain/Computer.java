package domain;

import datasource.ProductUnitOfWork;

public class Computer extends Product {

  private static final String categoryString = "Computer";
  private String cpu;
  private int diskVolume;

  public Computer(int productID, String name, String category, double price, int stockNumber, String image, boolean newCreated) {
    super(productID, name, category, price, stockNumber, image, newCreated);
  }

  public String getCpu() {
    if (cpu == null) {
      load();
    }
    return cpu;
  }

  /* */
  public void setCpu(String cpu) {
    if (this.cpu != null) {
      ProductUnitOfWork.getCurrent().registerDirtyObject(this);
    }
    this.cpu = cpu;
  }

  public int getDiskVolume() {
    if (diskVolume == 0) {
      load();
    }
    return diskVolume;
  }

  public void setDiskVolume(int diskVolume) {
    if (this.diskVolume != 0) {
      ProductUnitOfWork.getCurrent().registerDirtyObject(this);
    }
    this.diskVolume = diskVolume;
  }

  @Override
  public String getCategory() {
    return categoryString;
  }

}
