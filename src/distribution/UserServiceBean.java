package distribution;

import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import datasource.UnitOfWork;
import datasource.UserLockingMapper;
import domain.User;

public class UserServiceBean {
    public UserDTO getUser(String id) throws RemoteException {
        return new UserAssembler().writeDTO(UserLockingMapper.getInstance().find(id));
    }
    public void getUserXml(String id, OutputStream outputStream) throws RemoteException {
        UserDTO dto = getUser(id);
        UserDTO.toXML(dto, outputStream);
    }

    public List <UserDTO> getAllUsers() throws RemoteException {
        List <User> products = UserLockingMapper.getInstance().findAll();
        UserAssembler PA = new UserAssembler();
        List <UserDTO> dtos = new ArrayList <UserDTO> ();
        for (User user: products) {
            // Call one method to get object fully loaded
            // as in distributed system, we need to save 
            // time to transfer data
            user.getLastName();

            dtos.add(PA.writeDTO(user));
        }
        return dtos;
    }
    public void getUserXml(OutputStream outputStream) throws RemoteException {
        for (UserDTO dto: getAllUsers()) {
            UserDTO.toXML(dto, outputStream);
        }
    }

    public void updateUser(UserDTO dto) throws RemoteException {
        UnitOfWork uuow = new UnitOfWork();
        // already refer to registry in assembler, so no need to further check
        new UserAssembler().readDTO(dto);
        uuow.commit();
    }

    public void updateUser(InputStream inputStream) throws RemoteException {
        UserDTO dto = UserDTO.fromXML(inputStream);
        updateUser(dto);
    }

    public void deleteUser(UserDTO dto) throws RemoteException {
        UnitOfWork uuow = new UnitOfWork();
        User user = new UserAssembler().readDTO(dto);
        uuow.registerDeletedObject(user);
        uuow.commit();
    }

    public void deleteUser(InputStream inputStream) throws RemoteException {
        UserDTO dto = UserDTO.fromXML(inputStream);
        deleteUser(dto);
    }
    public void insertUser(UserDTO dto) throws RemoteException {
        UnitOfWork uuow = new UnitOfWork();
        // already refer to registry in assembler, so no need to further check
        new UserAssembler().readDTO(dto);
        uuow.commit();
    }

    public void insertUser(InputStream inputStream) throws RemoteException {
        UserDTO dto = UserDTO.fromXML(inputStream);
        insertUser(dto);
    }
}