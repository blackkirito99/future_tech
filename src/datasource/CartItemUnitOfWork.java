package datasource;

import java.util.ArrayList;

import domain.CartItem;

public class CartItemUnitOfWork extends UnitOfWork<CartItem> {

  private static ThreadLocal<CartItemUnitOfWork> current = new ThreadLocal<CartItemUnitOfWork>();

  public static void newCurrent() {
    setCurrent(new CartItemUnitOfWork());
  }

  public static void setCurrent(CartItemUnitOfWork ciuow) {
    current.set(ciuow);
  }

  public static CartItemUnitOfWork getCurrent() {
    return (CartItemUnitOfWork) current.get();
  }

  @Override
  public void commit() {
    for (CartItem object : newObjects) {
      CartMapper.add(object);
    }
    // only update is their quantity
    for (CartItem object : dirtyObjects) {
      CartMapper.update(object);
    }
    // when quantity get to 0, delete
    for (CartItem object : deletedObjects) {
      CartMapper.delete(object);
    }
    // clear new and deleted object in memory
    newObjects = new ArrayList<>();
    dirtyObjects = new ArrayList<>();
    deletedObjects = new ArrayList<>();
  }
}
