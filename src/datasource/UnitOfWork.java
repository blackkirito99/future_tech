package datasource;

import java.util.ArrayList;
import java.util.List;

import domain.CartItem;
import domain.Computer;
import domain.Customer;
import domain.Order;
import domain.Retailer;
import domain.Smartphone;

/*abstract class for different unit of work*/
public class UnitOfWork {

    protected List <Object> newObjects = new ArrayList <> ();
    protected List <Object> dirtyObjects = new ArrayList <> ();
    protected List <Object> deletedObjects = new ArrayList <> ();
    private static ThreadLocal <UnitOfWork> current = new ThreadLocal <UnitOfWork> ();

    public static void newCurrent() {
        setCurrent(new UnitOfWork());
    }

    public static void setCurrent(UnitOfWork uow) {
        current.set(uow);
    }

    public static UnitOfWork getCurrent() {
        return (UnitOfWork) current.get();
    }

    public void registerNewObject(Object object) {
        if (object == null) {
            System.out.println("object is null");
            return;
        }
        if (dirtyObjects.contains(object)) {
            System.out.println("object is dirty");
            return;
        }
        if (deletedObjects.contains(object)) {
            System.out.println("objec is deleted");
            return;
        }
        if (newObjects.contains(object)) {
            System.out.println(object.toString() + " is new");
            return;
        }
        newObjects.add(object);
        System.out.println(object.toString() + " is new object!");
    }

    public void registerDirtyObject(Object object) {
        if (object == null) {
            System.out.println("object is null");
            return;
        }
        if (dirtyObjects.contains(object)) {
            System.out.println(object.toString() + " is dirty");
            return;
        }
        if (deletedObjects.contains(object)) {
            System.out.println(object.toString() + " deleted");
            return;
        }
        if (!dirtyObjects.contains(object) && !newObjects.contains(object)) {
            dirtyObjects.add(object);
        }
        System.out.println(object.toString() + " becomes dirty!");
    }

    public void registerDeletedObject(Object object) {
        if (object == null) {
            System.out.println("object is null");
            return;
        }
        if (newObjects.remove(object)) {
            return;
        }
        dirtyObjects.remove(object);
        if (!deletedObjects.contains(object)) {
            deletedObjects.add(object);
        }
        System.out.println(object.toString() + " is going to be deleted!");
    }

    public void commit() {
        for (Object object: newObjects) {
            getProperMapper(object).insert(object);
        }
        for (Object object: dirtyObjects) {
            getProperMapper(object).update(object);
        }
        for (Object object: deletedObjects) {
            getProperMapper(object).delete(object);
        }
        // clear new and deleted object in memory
        newObjects = new ArrayList <> ();
        dirtyObjects = new ArrayList <> ();
        deletedObjects = new ArrayList <> ();
    }

    private Mapper getProperMapper(Object object) {
        if (object instanceof Customer || object instanceof Retailer) {
            return UserLockingMapper.getInstance();
        } else if (object instanceof Computer || object instanceof Smartphone) {
            return ProductLockingMapper.getInstance();
        } else if (object instanceof CartItem) {
            return CartLockingMapper.getInstance();
        } else if (object instanceof Order) {
            return OrderLockingMapper.getInstance();
        }
        return null;
    }

}