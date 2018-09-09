package datasource;

import java.util.ArrayList;

import domain.User;

public class UserUnitOfWork extends UnitOfWork<User> {

  private static ThreadLocal<UserUnitOfWork> current = new ThreadLocal<UserUnitOfWork>();

  public static void newCurrent() {
    setCurrent(new UserUnitOfWork());
  }

  public static void setCurrent(UserUnitOfWork uuow) {
    current.set(uuow);
  }

  public static UserUnitOfWork getCurrent() {
    return (UserUnitOfWork) current.get();
  }

  @Override
  public void commit() {
    for (User object : newObjects) {
      UserMapper.add(object);
    }
    // only update is their quantity
    for (User object : dirtyObjects) {
      UserMapper.update(object);
    }
    // when quantity get to 0, delete
    for (User object : deletedObjects) {
      UserMapper.delete(object);
    }
    // clear new and deleted object in memory
    newObjects = new ArrayList<>();
    dirtyObjects = new ArrayList<>();
    deletedObjects = new ArrayList<>();
  }
}
