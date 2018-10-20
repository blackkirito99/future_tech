package datasource;

import java.util.List;

import concurrency.LockManager;
import domain.Order;

public class OrderLockingMapper implements Mapper <Order> {

    private Mapper <Order> impl;
    private LockManager lm;
    //private String sessionId;
    private static OrderLockingMapper lockingMapper;

    public static OrderLockingMapper getInstance() {
        if (OrderLockingMapper.lockingMapper == null) {
            OrderLockingMapper.lockingMapper = new OrderLockingMapper(OrderMapper.getInstance());
        }
        return OrderLockingMapper.lockingMapper;
    }

    public OrderLockingMapper(Mapper <Order> impl) {
        this.impl = impl;
        this.lm = LockManager.getInstance();
        //this.sessionId = SessionManager.getSession().getId();
    }

    /* Adaptor for external usage*/
    public Order find(String orderNum) {
        return find(orderNum, "");
    }
    @Override
    public Order find(String orderNum, String id2) {
        Order result = new Order(orderNum);
        try {
            lm.acquireReadLock(result);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        result = (Order) impl.find(orderNum, id2);
        lm.releaseReadLock(result);
        return result;
    }
    @Override
    public List <Order> findAll() {
        try {
            // how to lock all table?
            lm.acquireReadLockAll(Order.class);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List <Order> result = (List <Order> ) impl.findAll();
        lm.releaseReadLockAll(Order.class);
        return result;
    }

    public List < Order > findOrderOfCustomer(int customerID) {
        try {
            // how to lock all table?
            lm.acquireReadLockOnCustomer(customerID, Order.class);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List <Order> result = (List <Order> )((OrderMapper) impl).findOrdersOfCustomer(customerID);
        lm.releaseReadLockOnCustomer(customerID, Order.class);
        return result;
    }

    @Override
    public void getFull(String orderNum) {
        Order result = new Order(orderNum);
        try {
            lm.acquireReadLock(result);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        impl.getFull(orderNum);
        lm.releaseReadLock(result);
    }


    public void insert(Order obj) {
        // no lock is required, the row is new
        impl.insert(obj);
    }

    /*These should not be called, no change will be made to DB*/
    public void update(Order obj) {
        try {
            lm.acquireWriteLock(obj);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        impl.update(obj);
        lm.releaseWriteLock(obj);
    }

    public void delete(Order obj) {

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