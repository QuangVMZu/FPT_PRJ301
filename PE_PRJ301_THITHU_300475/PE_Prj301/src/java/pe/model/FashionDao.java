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
public class FashionDao {

    //-----            your code here   --------------------------------
    private static final String GET_ALL_PROJECTS = "SELECT * FROM tblFashion";
    private static final String GET_PROJECTS_BY_PROJECT_NAME = "SELECT * FROM tblFashion WHERE name LIKE ?";
    private static final String GET_PROJECT_BY_ID = "SELECT * FROM tblFashion WHERE id = ?";
    private static final String UPDATE_PROJECT = "UPDATE tblFashion SET name = ?, description = ?, price = ?, size = ?, status = ? WHERE id = ?";

    public List<FashionDto> getAll() {
        List<FashionDto> projects = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_PROJECTS);
            rs = ps.executeQuery();

            while (rs.next()) {
                FashionDto project = new FashionDto();
                project.setId(rs.getString("id"));
                project.setName(rs.getString("name"));
                project.setDescription(rs.getString("description"));
                project.setPrice(rs.getFloat("price"));
                project.setSize(rs.getString("size"));
                project.setStatus(rs.getInt("status"));
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

    public FashionDto getProjectByID(String id) {
        FashionDto product = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_PROJECT_BY_ID);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                product = new FashionDto();
                product.setId(rs.getString("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getFloat("price"));
                product.setSize(rs.getString("size"));
                product.setStatus(rs.getInt("status"));
            }
        } catch (Exception e) {
            System.err.println("Error in getProductByID(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return product;
    }

    public boolean update(FashionDto project) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(UPDATE_PROJECT);

            ps.setString(1, project.getName());
            ps.setString(2, project.getDescription());
            ps.setFloat(3, project.getPrice());
            ps.setString(4, project.getSize());
            ps.setInt(5, project.getStatus());
            ps.setString(6, project.getId());

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

    public List<FashionDto> getProductsByName(String projectName) {
        List<FashionDto> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_PROJECTS_BY_PROJECT_NAME);
            ps.setString(1, "%" + projectName + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                FashionDto product = new FashionDto();
                product.setId(rs.getString("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getFloat("price"));
                product.setSize(rs.getString("size"));
                product.setStatus(rs.getInt("status"));

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
