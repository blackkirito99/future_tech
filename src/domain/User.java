package domain;

import java.util.List;

import datasource.UserMapper;
import datasource.UserUnitOfWork;

public class User {

  private int userID;
  private String lastName;
  private String firstName;
  private String username;
  private String password;
  private String email;
  private String type;
  private String avatar;

  public User(int ID, String username, String type, String email, String avatar, boolean newCreated) {
    setUserID(ID);
    setUsername(username);
    setType(type);
    setAvatar(avatar);
    setEmail(email);
    if(newCreated) {
      UserUnitOfWork.getCurrent().registerNewObject(this);
    }else {
      System.out.println(toString() + " is lazy loaded!");
    }
  }

  public static User getUser(int id) {
    return UserMapper.getUser(id);
  }

  public static List<User> getAllUsers() {
    return UserMapper.getAllUsers();
  }

  public int getUserID() {
    // can skip this register
    UserUnitOfWork uuow = UserUnitOfWork.getCurrent();
    if (uuow != null) {
      uuow.registerCleanObject(this);
    }
    return userID;
  }

  // user ID cannot be changed, unless initialization
  private void setUserID(int userID) {
    this.userID = userID;
  }

  public String getLastName() {
    if (lastName == null)
      load();
    // can skip this register
    UserUnitOfWork uuow = UserUnitOfWork.getCurrent();
    if (uuow != null) {
      uuow.registerCleanObject(this);
    }
    return lastName;
  }

  public void setLastName(String lastName) {
    if (this.lastName != null) {
      UserUnitOfWork uuow = UserUnitOfWork.getCurrent();
      if (uuow != null) {
        uuow.registerDirtyObject(this);
      }
    }
    this.lastName = lastName;
  }

  public String getFirstName() {
    if (firstName == null) {
      load();
    }
    // can skip this register
    UserUnitOfWork uuow = UserUnitOfWork.getCurrent();
    if (uuow != null) {
      uuow.registerCleanObject(this);
    }
    return firstName;
  }

  public void setFirstName(String firstName) {
    if (this.firstName != null) {
      UserUnitOfWork uuow = UserUnitOfWork.getCurrent();
      if (uuow != null) {
        uuow.registerDirtyObject(this);
      }
    }
    this.firstName = firstName;
  }

  public String getUsername() {
    // can skip this register
    UserUnitOfWork.getCurrent().registerCleanObject(this);
    return username;
  }

  public void setUsername(String username) {
    if (this.username != null) {
      UserUnitOfWork.getCurrent().registerDirtyObject(this);
    }
    this.username = username;
  }

  public String getPassword() {
    if (password == null) {
      load();
    }
    // can skip this register
    UserUnitOfWork.getCurrent().registerCleanObject(this);
    return password;
  }

  public void setPassword(String password) {
    if (this.password != null) {
      UserUnitOfWork uuow = UserUnitOfWork.getCurrent();
      if (uuow != null) {
        uuow.registerDirtyObject(this);
      }
    }
    this.password = password;
  }

  public String getEmail() {
    // can skip this register
    UserUnitOfWork uuow = UserUnitOfWork.getCurrent();
    if (uuow != null) {
      uuow.registerCleanObject(this);
    }
    return email;
  }

  public void setEmail(String email) {
    if (this.email != null) {
      UserUnitOfWork uuow = UserUnitOfWork.getCurrent();
      if (uuow != null) {
        uuow.registerDirtyObject(this);
      }
    }
    this.email = email;
  }

  public String getType() {
    // can skip this register
    UserUnitOfWork uuow = UserUnitOfWork.getCurrent();
    if (uuow != null) {
      uuow.registerCleanObject(this);
    }
    return type;
  }

  // cannot be changed except initilization
  private void setType(String type) {
    // UserUnitOfWork.getCurrent().registerDirtyObject(this);
    this.type = type;
  }

  public String getAvatar() {
    UserUnitOfWork uuow = UserUnitOfWork.getCurrent();
    if (uuow != null) {
      uuow.registerCleanObject(this);
    }
    return avatar;
  }

  public void setAvatar(String avatar) {
    if (this.avatar != null) {
      UserUnitOfWork uuow = UserUnitOfWork.getCurrent();
      if (uuow != null) {
        uuow.registerDirtyObject(this);
      }
    }
    this.avatar = avatar;
  }

  // load additional info from database
  public void load() {
    System.out.println(toString() + " is full loaded!");
    UserMapper.getFullUser(this.getUserID());
  }

  /* For debugging*/
  @Override
  public String toString() {
    return "User " + username;
  }

}
