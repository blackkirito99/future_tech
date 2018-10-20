package domain;

import java.util.List;
import java.util.Objects;

import datasource.UserLockingMapper;
import datasource.UnitOfWork;

public class User {

    private int userID;
    private String lastName;
    private String firstName;
    private String username;
    private String password;
    private String email;
    private String type;
    private String avatar;

    /*Use for temporary lock usage*/
    public User(int userID) {
        setUserID(userID);
    }

    public User(int ID, String username, String type, String email, String avatar, boolean newCreated) {
        setUserID(ID);
        setUsername(username);
        setType(type);
        setAvatar(avatar);
        setEmail(email);
        if (newCreated) {
        	UnitOfWork uow = UnitOfWork.getCurrent();
        	if(uow == null) {
        		uow.newCurrent();
        	}
        	UnitOfWork.getCurrent().registerNewObject(this);
        } else {
            System.out.println(toString() + " is lazy loaded!");
        }
    }
    public static User login(String username, String password) {
        String userID = UserLockingMapper.getInstance().Login(username, password);
        if (userID != null) {
            return getUser(userID);
        }
        return null;
    }
    public static User getUser(int userID) {
        return getUser(Integer.toString(userID));
    }
    public static User getUser(String userID) {
        return UserLockingMapper.getInstance().find(userID, "");
    }

    public static List < User > getAllUsers() {
        return UserLockingMapper.getInstance().findAll();
    }

    public static void newUser(User user) {
        UserLockingMapper.getInstance().insert(user);
    }

    public static void updateUser(User user) {
        UserLockingMapper.getInstance().update(user);
    }

    public static void deleteUser(User user) {
        UserLockingMapper.getInstance().delete(user);
    }
    
    public int getUserID() {
        return userID;
    }

    // user ID cannot be changed, unless initialization
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getLastName() {
        if (lastName == null)
            load();
        return lastName;
    }

    public void setLastName(String lastName) {
        if (this.lastName != null) {
        	UnitOfWork uow = UnitOfWork.getCurrent();
        	if(uow == null) {
        		uow.newCurrent();
        	}
        	UnitOfWork.getCurrent().registerDirtyObject(this);
        }
        this.lastName = lastName;
    }

    public String getFirstName() {
        if (firstName == null) {
            load();
        }
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (this.firstName != null) {
        	UnitOfWork uow = UnitOfWork.getCurrent();
        	if(uow == null) {
        		uow.newCurrent();
        	}
        	UnitOfWork.getCurrent().registerDirtyObject(this);
        }
        this.firstName = firstName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (this.username != null) {
        	UnitOfWork uow = UnitOfWork.getCurrent();
        	if(uow == null) {
        		uow.newCurrent();
        	}
        	UnitOfWork.getCurrent().registerDirtyObject(this);
        }
        this.username = username;
    }

    public String getPassword() {
        if (password == null) {
            load();
        }
        return password;
    }

    public void setPassword(String password) {
        if (this.password != null) {
        	UnitOfWork uow = UnitOfWork.getCurrent();
        	if(uow == null) {
        		uow.newCurrent();
        	}
        	UnitOfWork.getCurrent().registerDirtyObject(this);
        }
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (this.email != null) {
        	UnitOfWork uow = UnitOfWork.getCurrent();
        	if(uow == null) {
        		uow.newCurrent();
        	}
        	UnitOfWork.getCurrent().registerDirtyObject(this);
        }
        this.email = email;
    }

    public String getType() {
        return type;
    }

    // cannot be changed except initilization
    protected void setType(String type) {
        // UnitOfWork.getCurrent().registerDirtyObject(this);
        this.type = type;
    }



    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        if (this.avatar != null) {
        	UnitOfWork uow = UnitOfWork.getCurrent();
        	if(uow == null) {
        		uow.newCurrent();
        	}
        	UnitOfWork.getCurrent().registerDirtyObject(this);
        }
        this.avatar = avatar;
    }

    // load additional info from database
    public void load() {
        System.out.println(toString() + " is full loaded!");
        UserLockingMapper.getInstance().getFull(Integer.toString(this.getUserID()));
    }

    /* For debugging*/
    @Override
    public String toString() {
        return "User " + username;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User that = (User) o;
        return userID == that.getUserID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID);
    }


}