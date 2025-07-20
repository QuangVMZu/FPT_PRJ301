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
public class RoomForRentDao {

    //-----            your code here   --------------------------------
    private static final String GET_ALL_PROJECTS = "SELECT * FROM RoomForRent";
    private static final String GET_PROJECTS_BY_PROJECT_NAME = "SELECT * FROM RoomForRent WHERE location LIKE ?";
    private static final String GET_PROJECT_BY_ID = "SELECT * FROM RoomForRent WHERE id = ?";
    private static final String UPDATE_PROJECT = "UPDATE RoomForRent SET status = -2 WHERE id = ?";

    public List<RoomForRentDto> getAll() {
        List<RoomForRentDto> projects = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_PROJECTS);
            rs = ps.executeQuery();

            while (rs.next()) {
                RoomForRentDto project = new RoomForRentDto();
                project.setId(rs.getInt("id"));
                project.setTitle(rs.getString("title"));
                project.setPrice(rs.getDouble("price"));
                project.setLocation(rs.getString("location"));
                project.setDescription(rs.getString("description"));
                project.setPostedDate(rs.getDate("postedDate"));
                project.setStatus(rs.getInt("status"));
                project.setUsername(rs.getString("username"));
                projects.add(project);
            }
        } catch (Exception e) {
            System.err.println("Error in getAll(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return projects;
    }

    public boolean isProjectExists(String id) {
        return getProjectByID(id) != null;
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

    public RoomForRentDto getProjectByID(String id) {
        RoomForRentDto product = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_PROJECT_BY_ID);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                product = new RoomForRentDto();
                product.setId(rs.getInt("id"));
                product.setTitle(rs.getString("title"));
                product.setPrice(rs.getDouble("price"));
                product.setLocation(rs.getString("location"));
                product.setDescription(rs.getString("description"));
                product.setPostedDate(rs.getDate("postedDate"));
                product.setStatus(rs.getInt("status"));
                product.setUsername(rs.getString("username"));
                
            }
        } catch (Exception e) {
            System.err.println("Error in getProductByID(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return product;
    }

    public boolean update(RoomForRentDto project) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(UPDATE_PROJECT);
            ps.setInt(1, project.getId());

            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);

        } catch (Exception e) {
            System.err.println("Error in update(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }

        return success;
    }

    public List<RoomForRentDto> getProductsByName(String location) {
        List<RoomForRentDto> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_PROJECTS_BY_PROJECT_NAME);
            ps.setString(1, "%" + location + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                RoomForRentDto product = new RoomForRentDto();
                product.setId(rs.getInt("id"));
                product.setTitle(rs.getString("title"));
                product.setPrice(rs.getDouble("price"));
                product.setLocation(rs.getString("location"));
                product.setDescription(rs.getString("description"));
                product.setPostedDate(rs.getDate("postedDate"));
                product.setStatus(rs.getInt("status"));
                product.setUsername(rs.getString("username"));

                products.add(product);
            }
        } catch (Exception e) {
            System.err.println("Error in getProductsByName(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return products;
    }
}
