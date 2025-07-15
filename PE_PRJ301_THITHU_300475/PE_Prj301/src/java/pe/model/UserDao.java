/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import pe.utils.DbUtils;

/**
 *
 * @author Computing Fundamental - HCM Campus
 */
public class UserDao {
    //-----            your code here   --------------------------------

    private static final String GET_ALL = "SELECT * FROM tblUsers";
    private static final String GET_USER_BY_USERNAME = "SELECT * FROM tblUsers WHERE userID=?";

    public UserDao() {

    }

    public boolean login(String userID, String password) {
        UserDto user = getUserByUserName(userID);
        return user != null && user.getPassword().equals(password);
    }

    public UserDto getUserByUserName(String uId) {
        UserDto user = null;
        try {
            Connection conn = DbUtils.getConnection();

            PreparedStatement pr = conn.prepareStatement(GET_USER_BY_USERNAME);
            pr.setString(1, uId);

            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                String userID = rs.getString("userID");
                String fullName = rs.getString("fullName");
                String password = rs.getString("password");
                String roleID = rs.getString("roleID");
                int status = rs.getInt("status");

                user = new UserDto(userID, fullName, password, roleID, status);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }

    public List<UserDto> getAllUsers() {
        List<UserDto> userList = new ArrayList<>();
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(GET_ALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserDto user = new UserDto();
                user.setUserID(rs.getString("userID"));
                user.setFullName(rs.getString("fullName"));
                user.setPassword(rs.getString("password"));
                user.setRoleID(rs.getString("role"));
                user.setStatus(rs.getInt("status"));
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.err.println("Error closing resources: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
