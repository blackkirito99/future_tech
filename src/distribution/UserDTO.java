package distribution;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class UserDTO {

    private int userID;
    private String lastName;
    private String firstName;
    private String username;
    private String password;
    private String email;
    private String type;
    private String address;
    private String avatar;
    private ShoppingCartDTO shoppingCartDTO;
    private List <OrderDTO> orderDTOs;

    public static void toXML(UserDTO userDTO, OutputStream outputStream) {
        XMLEncoder encoder = new XMLEncoder(outputStream);
        encoder.writeObject(userDTO);
        encoder.close();
    }


    public static UserDTO fromXML(InputStream inputStream) {
        XMLDecoder decoder = new XMLDecoder(inputStream);
        UserDTO result = (UserDTO) decoder.readObject();
        decoder.close();
        return result;
    }


    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ShoppingCartDTO getShoppingCartDTO() {
        return shoppingCartDTO;
    }

    public void setShoppingCartDTO(ShoppingCartDTO shoppingCartDTO) {
        this.shoppingCartDTO = shoppingCartDTO;
    }

    public List <OrderDTO> getOrderDTOs() {
        return orderDTOs;
    }

    public void setOrderDTOs(List < OrderDTO > orderDTOs) {
        this.orderDTOs = orderDTOs;
    }

}