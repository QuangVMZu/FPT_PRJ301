package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

public class UserDAO {

    private static final String GET_ALL = "SELECT * FROM tblUsers";
    private static final String GET_USER_BY_USERNAME = "SELECT * FROM tblUsers WHERE userName=?";
    private static final String UPDATE_PASSWORD_BY_USERNAME = "UPDATE tblUsers SET password = ? WHERE userName = ?";

    public UserDAO() {

    }

    public boolean login(String userName, String password) {
        UserDTO user = getUserByUserName(userName);
        return user != null && user.getPassword().equals(password);
    }

    public UserDTO getUserByUserName(String uName) {
        UserDTO user = null;
        try {
            Connection conn = DbUtils.getConnection();

            PreparedStatement pr = conn.prepareStatement(GET_USER_BY_USERNAME);
            pr.setString(1, uName);

            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                String userName = rs.getString("userName");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String role = rs.getString("role");

                user = new UserDTO(userName, name, password, role);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }

    public List<UserDTO> getAllUsers() {
        List<UserDTO> userList = new ArrayList<>();
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(GET_ALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserDTO user = new UserDTO();
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setRole(rs.getString("role"));
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public boolean updatePassword(String userName, String newPassword) {
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(UPDATE_PASSWORD_BY_USERNAME);
            ps.setString(1, newPassword);
            ps.setString(2, userName);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
