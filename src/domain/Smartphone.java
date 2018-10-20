package domain;

import concurrency.LockManager;
import datasource.UnitOfWork;

public class Smartphone extends Product {

    public static final String CategoryString = "SM";
    private double screenSize;

    public Smartphone(int productID, String name, String category, double price, int stockNumber, String image, boolean newCreated) {
        super(productID, name, category, price, stockNumber, image, newCreated);
    }

    public double getScreenSize() {
    	try {
			LockManager.getInstance().acquireReadLock(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (screenSize == 0) {
            load();
        }
        double screenSize = this.screenSize;
        LockManager.getInstance().releaseReadLock(this);
        return screenSize;
    }

    public void setScreenSize(double screenSize)  {
    	try {
			LockManager.getInstance().acquireWriteLock(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (this.screenSize != 0) {
        	UnitOfWork uow = UnitOfWork.getCurrent();
        	if(uow == null) {
        		uow.newCurrent();
        	}
            UnitOfWork.getCurrent().registerDirtyObject(this);
        }
        this.screenSize = screenSize;
        LockManager.getInstance().releaseWriteLock(this);
    }

    @Override
    public String getCategory() {
        return CategoryString;
    }

}