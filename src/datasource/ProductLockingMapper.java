package datasource;

import java.util.List;

import concurrency.LockManager;
import domain.Product;

public class ProductLockingMapper implements Mapper < Product > {
    private Mapper <Product> impl;
    private LockManager lm;
    //private String sessionId;
    private static ProductLockingMapper lockingMapper;

    public static ProductLockingMapper getInstance() {
        if (ProductLockingMapper.lockingMapper == null) {
            ProductLockingMapper.lockingMapper = new ProductLockingMapper(ProductMapper.getInstance());
        }
        return ProductLockingMapper.lockingMapper;
    }

    public ProductLockingMapper(Mapper <Product> impl) {
        this.impl = impl;
        this.lm = LockManager.getInstance();
        //this.sessionId = SessionManager.getSession().getId();
    }

    /* Adaptor for external usage*/
    public Product find(String id) {
        return find(id, "");
    }
    @Override
    public Product find(String id, String id2) {
        Product result = new Product(Integer.parseInt(id));
        try {
            lm.acquireReadLock(result);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        result = (Product) impl.find(id, id2);
        lm.releaseReadLock(result);
        return result;
    }
    public List <Product> findBrand(String brand) {
        try {
            // how to lock all table?
            lm.acquireReadLockAll(Product.class);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List <Product> result = (List <Product> ) ((ProductMapper)impl).findBrand(brand);
        lm.releaseReadLockAll(Product.class);
        return result;
    }
    
    public List <Product> findCategory(String category) {
        try {
            // how to lock all table?
            lm.acquireReadLockAll(Product.class);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List <Product> result = (List <Product> ) ((ProductMapper)impl).findCategory(category);
        lm.releaseReadLockAll(Product.class);
        return result;
    }

    
    public List <Product> findQuery(String query) {
    	try {
    		// how to lock all table?
    		lm.acquireReadLockAll(Product.class);
    	} catch (InterruptedException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	List <Product> result = (List <Product> ) ((ProductMapper)impl).findQuery(query);
    	lm.releaseReadLockAll(Product.class);
    	return result;
    }
    
    @Override
    public List <Product> findAll() {
        try {
            // how to lock all table?
            lm.acquireReadLockAll(Product.class);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List <Product> result = (List <Product> ) impl.findAll();
        lm.releaseReadLockAll(Product.class);
        return result;
    }

    @Override
    public void getFull(String id) {
        Product result = new Product(Integer.parseInt(id));
        try {
            lm.acquireReadLock(result);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        impl.getFull(id);
        lm.releaseReadLock(result);
    }

    @Override
    public void insert(Product obj) {
        // no lock is required, the row is new
        impl.insert(obj);
    }
    @Override
    public void update(Product obj) {
        try {
            lm.acquireWriteLock(obj);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        impl.update(obj);
        lm.releaseWriteLock(obj);
    }
    @Override
    public void delete(Product obj) {

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