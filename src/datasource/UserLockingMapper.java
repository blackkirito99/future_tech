package datasource;

import java.util.List;

import concurrency.LockManager;
import domain.User;

public class UserLockingMapper implements Mapper <User> {

    private Mapper <User> impl;
    private LockManager lm;
    //private String sessionId;
    private static UserLockingMapper lockingMapper;

    public static UserLockingMapper getInstance() {
        if (UserLockingMapper.lockingMapper == null) {
            UserLockingMapper.lockingMapper = new UserLockingMapper(UserMapper.getInstance());
        }
        return UserLockingMapper.lockingMapper;
    }

    public UserLockingMapper(Mapper <User> impl) {
        this.impl = impl;
        this.lm = LockManager.getInstance();
        //this.sessionId = SessionManager.getSession().getId();
    }


    public String Login(String username, String password) {
        // we assume no change will present when a user is logining
        // so no need for lock.
        return ((UserMapper) impl).Login(username, password);
    }

    /* Adaptor for external usage*/
    public User find(String id) {
        return find(id, "");
    }

    @Override
    public User find(String id, String id2) {
        User result = new User(Integer.parseInt(id));
        try {
            lm.acquireReadLock(result);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        result = (User) impl.find(id, id2);
        lm.releaseReadLock(result);
        return result;
    }

    @Override
    public List <User> findAll() {
        try {
            // how to lock all table?
            lm.acquireReadLockAll(User.class);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List <User> result = (List <User> ) impl.findAll();
        lm.releaseReadLockAll(User.class);
        return result;
    }

    @Override
    public void getFull(String id) {
        User result = new User(Integer.parseInt(id));
        try {
            lm.acquireReadLock(result);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        impl.getFull(id);
        lm.releaseReadLock(result);
    }


    public void insert(User obj) {
        // no lock is required, the row is new
        impl.insert(obj);
    }

    public void update(User obj) {
        try {
            lm.acquireWriteLock(obj);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        impl.update(obj);
        lm.releaseWriteLock(obj);
    }

    public void delete(User obj) {

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