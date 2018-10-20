package datasource;

import java.util.List;

import concurrency.LockManager;
import domain.CartItem;
import domain.Product;


public class CartLockingMapper implements Mapper < CartItem > {
    private Mapper < CartItem > impl;
    private LockManager lm;
    //private String sessionId;
    private static CartLockingMapper lockingMapper;

    public static CartLockingMapper getInstance() {
        if (CartLockingMapper.lockingMapper == null) {
            CartLockingMapper.lockingMapper = new CartLockingMapper(CartMapper.getInstance());
        }
        return CartLockingMapper.lockingMapper;
    }

    public CartLockingMapper(Mapper < CartItem > impl) {
        this.impl = impl;
        this.lm = LockManager.getInstance();
        //this.sessionId = SessionManager.getSession().getId();
    }
    @Override
    public CartItem find(String customerID, String productID) {
        CartItem result = new CartItem(Integer.parseInt(customerID), Integer.parseInt(productID));
        try {
            lm.acquireReadLock(result);

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        result = (CartItem) impl.find(customerID, productID);
        lm.releaseReadLock(result);
        return result;
    }

    public List < CartItem > findAll() {
        try {
            // how to lock all table?
            lm.acquireReadLockAll(CartItem.class);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List < CartItem > result = (List < CartItem > ) impl.findAll();
        lm.releaseReadLockAll(Product.class);
        return result;
    }

    public List < CartItem > findCartOfCustomer(int customerID) {
        try {
            // how to lock all table?
            lm.acquireReadLockOnCustomer(customerID, CartItem.class);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List < CartItem > result = (List < CartItem > )((CartMapper) impl).findCartOfCustomer(customerID);
        lm.releaseReadLockOnCustomer(customerID, CartItem.class);
        return result;
    }

    @Override
    public void getFull(String id) {
        /*
         * Will not be used, follow interface pattern
         * */
    }


    public void insert(CartItem obj) {
        // no lock is required, the row is new
        impl.insert(obj);
    }

    public void update(CartItem obj) {
        try {
            lm.acquireWriteLock(obj);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        impl.update(obj);
        lm.releaseWriteLock(obj);
    }

    public void delete(CartItem obj) {

        try {
            lm.acquireWriteLock(obj);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        impl.delete(obj);
        lm.releaseWriteLock(obj);
    }

}