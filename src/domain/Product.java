package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import concurrency.LockManager;
import datasource.ProductLockingMapper;
import datasource.UnitOfWork;

public class Product {

    private int productID;
    private String name;
    private String brand;
    private String category;
    private double price;
    private Integer stockNumber;
    private String image;

    /*Use for temporary lock usage*/
    public Product(int productID) {
        setProductID(productID);
    }
    // only load productID, name, category and price at first
    public Product(int productID, String name, String category, double price, int stockNumber, String image, boolean newCreated) {
        setProductID(productID);
        setName(name);
        setCategory(category);
        setPrice(price);
        setStockNumber(stockNumber);
        setImage(image);
        if (newCreated) {
        	UnitOfWork uow = UnitOfWork.getCurrent();
        	if(uow == null) {
        		uow.newCurrent();
        	}
        	UnitOfWork.getCurrent().registerNewObject(this);
        } else {
            System.out.println(toString() + " is lazy loaded!");
        }
    }
    /*
     * Only return products with stock number more than 0
     * */
    public static List < Product > getAvailableProducts()  {
        List < Product > allProducts = ProductLockingMapper.getInstance().findAll();
        List < Product > result = new ArrayList < > ();
        for (Product product: allProducts) {
            if (product.getStockNumber() > 0) {
                result.add(product);
            }
        }
        return result;
    }
    
    public static List < Product > getAllProducts()  {
        return ProductLockingMapper.getInstance().findAll();
    }
    
    public static Integer getNewId() {
      List<Product> products = getAllProducts();
      Integer id = -1;
      for (Product product: products) {
        if (product.getProductID() > id) {
          id = product.getProductID();
        }
      }
      return id + 1;
    }
    
    public static List<String> getAllCategories() {
      List<Product> products = getAllProducts();
      List<String> categories = new ArrayList<String>();
      for (Product product: products) {
        if (!categories.contains(product.getCategory())) {
          categories.add(product.getCategory());
        }
      }
      return categories;
    }
    
    public static List<String> getAllBrands() {
      List<Product> products = getAllProducts();
      List<String> brands = new ArrayList<String>();
      for (Product product: products) {
        if (!brands.contains(product.getBrand())) {
          brands.add(product.getBrand());
        }
      }
      return brands;
    }

    public static List < Product > getAvailableProductsOfBrand(String brand)  {
        List < Product > allProducts = ProductLockingMapper.getInstance().findBrand(brand);
        List < Product > result = new ArrayList < > ();
        for (Product product: allProducts) {
            if (product.getStockNumber() > 0) {
                result.add(product);
            }
        }
        return result;
    }
    
    public static List < Product > getAvailableProductsOfCategory(String category)  {
        List < Product > allProducts = ProductLockingMapper.getInstance().findCategory(category);
        List < Product > result = new ArrayList < > ();
        for (Product product: allProducts) {
            if (product.getStockNumber() > 0) {
                result.add(product);
            }
        }
        return result;
    }
    
    public static List < Product > getAvailableProductsOfQuery(String query)  {
      List < Product > allProducts = ProductLockingMapper.getInstance().findQuery(query);
      List < Product > result = new ArrayList < > ();
      for (Product product: allProducts) {
          if (product.getStockNumber() > 0) {
              result.add(product);
          }
      }
      return result;
  }
    public static void newProduct(Product product) {
        ProductLockingMapper.getInstance().insert(product);
    }
    public static void updateProduct(Product product) {
        ProductLockingMapper.getInstance().update(product);
    }
    public static void deleteProduct(Product product) {
        ProductLockingMapper.getInstance().delete(product);
    }
    public int getProductID() {
        return productID;
    }
    
    // cannot be changed, so no lock is needed
    public static Product getProduct(int id) {
        return ProductLockingMapper.getInstance().find(Integer.toString(id), "");
    }

    // product ID cannot be changed, except initilization, so set as private
    private void setProductID(int productID){
        this.productID = productID;
    }

    public String getName() {
    	try {
			LockManager.getInstance().acquireReadLock(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String name = this.name;
        LockManager.getInstance().releaseReadLock(this);
        return name;
    }

    public void setName(String name){
    	try {
			LockManager.getInstance().acquireWriteLock(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (this.name != null) {
        	UnitOfWork uow = UnitOfWork.getCurrent();
        	if(uow == null) {
        		uow.newCurrent();
        	}
            UnitOfWork.getCurrent().registerDirtyObject(this);
        }
        this.name = name;
        LockManager.getInstance().releaseWriteLock(this);
    }

    public String getBrand(){
    	try {
			LockManager.getInstance().acquireReadLock(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (brand == null) {
            load();
        }
        String brand = this.brand;
        LockManager.getInstance().releaseReadLock(this);
        return brand;
    }

    public void setBrand(String brand) {
    	try {
			LockManager.getInstance().acquireWriteLock(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (this.brand != null) {
        	UnitOfWork uow = UnitOfWork.getCurrent();
        	if(uow == null) {
        		uow.newCurrent();
        	}
            UnitOfWork.getCurrent().registerDirtyObject(this);
        }
        this.brand = brand;
        LockManager.getInstance().releaseWriteLock(this);
    }

    public String getCategory() {
    	try {
			LockManager.getInstance().acquireReadLock(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String category = this.category;
        LockManager.getInstance().releaseReadLock(this);
        return category;
    }

    public void setCategory(String category){
    	try {
			LockManager.getInstance().acquireWriteLock(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (this.category != null) {
        	UnitOfWork uow = UnitOfWork.getCurrent();
        	if(uow == null) {
        		uow.newCurrent();
        	}
            UnitOfWork.getCurrent().registerDirtyObject(this);
        }
        this.category = category;
        LockManager.getInstance().releaseWriteLock(this);
    }

    public double getPrice() {
    	try {
			LockManager.getInstance().acquireReadLock(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        double price = this.price;
        LockManager.getInstance().releaseReadLock(this);
        return price;
    }

    public void setPrice(double price)  {
    	try {
			LockManager.getInstance().acquireWriteLock(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (this.price != 0.0) {
        	UnitOfWork uow = UnitOfWork.getCurrent();
        	if(uow == null) {
        		uow.newCurrent();
        	}
            UnitOfWork.getCurrent().registerDirtyObject(this);
        }
        this.price = price;
        LockManager.getInstance().releaseWriteLock(this);
        
    }

    public int getStockNumber() {
    	try {
			LockManager.getInstance().acquireReadLock(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int stockNumber = this.stockNumber;
        LockManager.getInstance().releaseReadLock(this);
        return stockNumber;
    }

    public void setStockNumber(int stockNumber)  {
    	try {
			LockManager.getInstance().acquireWriteLock(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (this.stockNumber != null) {
        	UnitOfWork uow = UnitOfWork.getCurrent();
        	if(uow == null) {
        		uow.newCurrent();
        	}
            UnitOfWork.getCurrent().registerDirtyObject(this);
        }
        this.stockNumber = stockNumber;
        LockManager.getInstance().releaseWriteLock(this);
    }

    public String getImage()  {
    	try {
			LockManager.getInstance().acquireReadLock(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String image = this.image;
        LockManager.getInstance().releaseReadLock(this);
        return image;
    }

    public void setImage(String image) {
    	try {
			LockManager.getInstance().acquireWriteLock(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (this.image != null) {
        	UnitOfWork uow = UnitOfWork.getCurrent();
        	if(uow == null) {
        		uow.newCurrent();
        	}
            UnitOfWork.getCurrent().registerDirtyObject(this);
        }
        this.image = image;
        LockManager.getInstance().releaseWriteLock(this);
    }

    // load additional info from database
    public void load() {
        System.out.println(toString() + " is full loaded");
        ProductLockingMapper.getInstance().getFull(Integer.toString(this.getProductID()));
    }

    /* For debugging*/
    @Override
    public String toString() {
    	try {
			LockManager.getInstance().acquireReadLock(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String toString = "Product " + name;
    	LockManager.getInstance().releaseReadLock(this);
        return toString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Product that = (Product) o;
        return productID == that.getProductID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID);
    }

}