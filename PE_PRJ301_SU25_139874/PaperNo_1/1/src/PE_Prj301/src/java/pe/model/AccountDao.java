/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import pe.utils.DbUtils;

/**
 *
 * @author Computing Fundamental - HCM Campus
 */
public class AccountDao {

    //-----            your code here   --------------------------------
    private static final String GET_ALL = "SELECT * FROM Account";
    private static final String GET_USER_BY_USERNAME = "SELECT * FROM Account WHERE username=?";

    public AccountDao() {

    }

    public boolean login(String userName, String password) {
        AccountDto user = getUserByUserName(userName);
        return user != null && user.getPassword().equals(password);
    }

    public AccountDto getUserByUserName(String userN) {
        AccountDto user = null;
        try {
            Connection conn = DbUtils.getConnection();

            PreparedStatement pr = conn.prepareStatement(GET_USER_BY_USERNAME);
            pr.setString(1, userN);

            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                String userName = rs.getString("userName");
                String password = rs.getString("password");
                String fullName = rs.getString("fullName");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                int role = rs.getInt("role");
                int status = rs.getInt("status");

                user = new AccountDto(userName, password, fullName, phone, email, status, role);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }

    public List<AccountDto> getAllUsers() {
        List<AccountDto> userList = new ArrayList<>();
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(GET_ALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AccountDto user = new AccountDto();
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("fullName"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getInt("role"));
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
