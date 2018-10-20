package concurrency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import datasource.Registry;
import domain.CartItem;
import domain.Order;
import domain.Product;
import domain.User;

public class LockManager {

    private Map < Object, ReadWriteLock > lockMap;

    private static LockManager manager;

    private LockManager() {

        lockMap = new HashMap < Object, ReadWriteLock > ();
    }

    public static LockManager getInstance() {
        if (LockManager.manager == null) {
            LockManager.manager = new LockManager();
        }
        return LockManager.manager;
    }

    public synchronized void acquireReadLock(Object toLock)
    throws InterruptedException {
        getReadWriteLock(toLock).lockRead();
        System.out.println(" acquire read lock");
    }

    /*
     * Lock all objects of one class
     * */
    public synchronized void acquireReadLockAll(Class <?> cls)
    throws InterruptedException {
        List < ReadWriteLock > locks = getAllLockOfClass(cls);
        for (ReadWriteLock rwl: locks) {
            rwl.lockRead();
        }
    }

    /*
     * Lock all objects related to a customer
     * */
    public synchronized void acquireReadLockOnCustomer(int customerID, Class <?> cls)
    throws InterruptedException {
        List < ReadWriteLock > locks = getAllLockOfClassOnCustomer(customerID, cls);
        for (ReadWriteLock rwl: locks) {
            rwl.lockRead();
        }
    }

    public synchronized void acquireWriteLock(Object toLock)
    throws InterruptedException {
        getReadWriteLock(toLock).lockWrite();
        System.out.println(" acquire write lock");
    }


    public synchronized void releaseReadLock(Object toLock) {
        ReadWriteLock rwl = getReadWriteLock(toLock);
        if (rwl.hasReadLock()) {
            rwl.unlockRead();
        }
        // overrwite temp/order object, save memory, only for readlock
        lockMap.put(toLock, rwl);
        System.out.println(" release read lock");

    }

    public synchronized void releaseReadLockAll(Class <?> cls) {
        List < ReadWriteLock > locks = getAllLockOfClass(cls);
        for (ReadWriteLock rwl: locks) {
            if (rwl.hasReadLock()) {
                rwl.unlockRead();
                System.out.println( " acquire read lock");
            }
        }
    }
    /*
     * Unlock all objects related to a customer
     * */
    public synchronized void releaseReadLockOnCustomer(int customerID, Class <?> cls) {
        List < ReadWriteLock > locks = getAllLockOfClassOnCustomer(customerID, cls);
        for (ReadWriteLock rwl: locks) {
            if (rwl.hasReadLock()) {
                rwl.unlockRead();
            }
        }
    }

    public synchronized void releaseWriteLock(Object toLock) {
        getReadWriteLock(toLock).unlockWrite();
    }

    public synchronized void releaseAllLocksOn(Object toLock) {
        getReadWriteLock(toLock).unlock();
    }

    public synchronized void releaseAllLocks() {
        for (Entry < Object, ReadWriteLock > entry: lockMap.entrySet()) {
            entry.getValue().unlock();
        }
    }

    private ReadWriteLock getReadWriteLock(Object toLock) {
        ReadWriteLock lock = lockMap.get(toLock);
        if (lock == null) {
            System.out.println("new lock");
            lockMap.putIfAbsent(toLock, new ReadWriteLock());
            lock = lockMap.get(toLock);
        }
        return lock;
    }

    private List < ReadWriteLock > getAllLockOfClass(Class <?> cls) {
        List < ReadWriteLock > result = new ArrayList < ReadWriteLock > ();
        if (cls == User.class) {
            List < User > users = Registry.getAllUsers();
            for (User u: users) {
                result.add(getReadWriteLock(u));
            }
        } else if (cls == Product.class) {
            List < Product > products = Registry.getAllProducts();
            for (Product p: products) {
                result.add(getReadWriteLock(p));
            }
        } else {
            System.out.println("Unexpected class error <all>");
        }
        return result;
    }

    private List < ReadWriteLock > getAllLockOfClassOnCustomer(int customerID, Class <?> cls) {
        List < ReadWriteLock > result = new ArrayList < ReadWriteLock > ();
        if (cls == Order.class) {
            List < Order > orders = Registry.getAllOrders();
            for (Order o: orders) {
                if (o.getCustomerID() == customerID) {
                    result.add(getReadWriteLock(o));
                }
            }
        } else if (cls == CartItem.class) {
            List < CartItem > cartItems = Registry.getAllCartItems();
            for (CartItem ci: cartItems) {
                if (ci.getCustomerID() == customerID) {
                    result.add(getReadWriteLock(ci));
                }
            }
        } else {
            System.out.println("Unexpected class error <customer>");
        }
        return result;
    }

}