package datasource;

import java.util.ArrayList;

import domain.Order;

public class OrderUnitOfWork extends UnitOfWork<Order>{
   private static ThreadLocal<OrderUnitOfWork> current = new ThreadLocal<OrderUnitOfWork>();

     public static void newCurrent() {
       setCurrent(new OrderUnitOfWork());
     }

     public static void setCurrent(OrderUnitOfWork ouow) {
       current.set(ouow);
     }

     public static OrderUnitOfWork getCurrent() {
       return (OrderUnitOfWork) current.get();
     }

     @Override
     public void commit() {
       for (Order object : newObjects) {
         OrderMapper.insertNewOrder(object);
       }
       // no change to order allowed at this point of time
       /*
       for (CartItem object : dirtyObjects) {
         CartMapper.update(object);
       }
       // when quantity get to 0, delete
       for (CartItem object : deletedObjects) {
         CartMapper.delete(object);
       }*/
       // clear deleted object in memory
       //deletedObjects = new ArrayList<>();
       // clear new object in memory
       newObjects = new ArrayList<>();

     }
}
