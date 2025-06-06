/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import static java.util.Collections.list;

/**
 *
 * @author ADMIN
 */
public class UserDAO {

    ArrayList<UserDTO> list;

    public UserDAO() {
        this.list = new ArrayList<>();
        list.add(new UserDTO("admin", "admin", "Administrator", "AD", true));
        list.add(new UserDTO("Se1234", "hcm", "Nguyen Van A", "MB", true));
        list.add(new UserDTO("vminhquang05", "Nhuff888677@", "Vu Minh Quang", "MB", true));
        list.add(new UserDTO("He1234", "A123", "Nguyen Van B", "MB", true));
        list.add(new UserDTO("He5678", "B123", "Nguyen Van C", "MB", true));
        list.add(new UserDTO("Ce5678", "C123", "Nguyen Van D", "MB", true));
    }

    public boolean login(String userID, String password) {
        for (UserDTO userDTO : list) {
            if (userDTO.getUserID().equalsIgnoreCase(userID)
                    && userDTO.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public UserDTO getUserById(String id) {
        for (UserDTO userDTO : list) {
            if (userDTO.getUserID().equalsIgnoreCase(id)) {
                return userDTO;
            }
        }
        return null;
    }
}
