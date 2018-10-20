package domain;

import concurrency.LockManager;
import datasource.UnitOfWork;

public class Computer extends Product {

    public static final String CategoryString = "PC";
    private String cpu;
    private int diskVolume;

    public Computer(int productID, String name, String category, double price, int stockNumber, String image, boolean newCreated){
        super(productID, name, category, price, stockNumber, image, newCreated);
    }

    public String getCpu() {
    	try {
			LockManager.getInstance().acquireReadLock(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (cpu == null) {
            load();
        }
        String cpu = this.cpu;
        LockManager.getInstance().releaseReadLock(this);
        return cpu;
    }

    /* */
    public void setCpu(String cpu)  {
    	try {
			LockManager.getInstance().acquireWriteLock(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (this.cpu != null) {
        	UnitOfWork uow = UnitOfWork.getCurrent();
        	if(uow == null) {
        		uow.newCurrent();
        	}
            UnitOfWork.getCurrent().registerDirtyObject(this);
        }
        this.cpu = cpu;
        LockManager.getInstance().releaseWriteLock(this);
    }

    public int getDiskVolume()  {
    	try {
			LockManager.getInstance().acquireReadLock(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (diskVolume == 0) {
            load();
        }
        int diskVolume = this.diskVolume;
        LockManager.getInstance().releaseReadLock(this);
        return diskVolume;
    }

    public void setDiskVolume(int diskVolume) {
    	try {
			LockManager.getInstance().acquireWriteLock(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (this.diskVolume != 0) {
        	UnitOfWork uow = UnitOfWork.getCurrent();
        	if(uow == null) {
        		uow.newCurrent();
        	}
            UnitOfWork.getCurrent().registerDirtyObject(this);
        }
        this.diskVolume = diskVolume;
        LockManager.getInstance().releaseWriteLock(this);
    }

    @Override
    public String getCategory() {
        return CategoryString;
    }

}