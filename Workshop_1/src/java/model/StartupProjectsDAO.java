package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

public class StartupProjectsDAO {

    private static final String GET_ALL_PROJECTS = "SELECT * FROM tblStartupProjects";
    private static final String GET_PROJECTS_BY_PROJECT_NAME = "SELECT * FROM tblStartupProjects WHERE project_name LIKE ?";
    private static final String GET_PROJECT_BY_ID = "SELECT * FROM tblStartupProjects WHERE project_id = ?";
    private static final String CREATE_PROJECT = "INSERT INTO tblStartupProjects (project_id, project_name, description, status, estimated_launch) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_PROJECT = "UPDATE tblStartupProjects SET project_name = ?, description = ?, status = ?, estimated_launch = ? WHERE project_id = ?";

    public List<StartupProjectsDTO> getAll() {
        List<StartupProjectsDTO> projects = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_PROJECTS);
            rs = ps.executeQuery();

            while (rs.next()) {
                StartupProjectsDTO project = new StartupProjectsDTO();
                project.setProjectID(rs.getInt("project_id"));
                project.setProjectName(rs.getString("project_name"));
                project.setDescription(rs.getString("description"));
                project.setStatus(rs.getString("status"));
                project.setEstimated(rs.getDate("estimated_launch"));

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

    public boolean isProjectExists(int projectID) {
        return getProjectByID(projectID) != null;
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

    public StartupProjectsDTO getProjectByID(int projectID) {
        StartupProjectsDTO project = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_PROJECT_BY_ID);
            ps.setInt(1, projectID);
            rs = ps.executeQuery();

            if (rs.next()) {
                project = new StartupProjectsDTO();
                project.setProjectID(rs.getInt("project_id"));
                project.setProjectName(rs.getString("project_name"));
                project.setDescription(rs.getString("description"));
                project.setStatus(rs.getString("status"));
                project.setEstimated(rs.getDate("estimated_launch"));
            }
        } catch (Exception e) {
            System.err.println("Error in getProductByID(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return project;
    }

    public boolean create(StartupProjectsDTO project) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            java.util.Date utilDate = project.getEstimated();
            if (utilDate == null || !utilDate.after(new java.util.Date())) {
                System.err.println("Estimated launch date must be in the future.");
                return false;
            }

            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(CREATE_PROJECT);

            ps.setInt(1, project.getProjectID());
            ps.setString(2, project.getProjectName());
            ps.setString(3, project.getDescription());
            ps.setString(4, project.getStatus());

            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            ps.setDate(5, sqlDate);

            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);

        } catch (Exception e) {
            System.err.println("Error in create(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }

        return success;
    }

    public boolean update(StartupProjectsDTO project) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            java.util.Date utilDate = project.getEstimated();
            if (utilDate == null || !utilDate.after(new java.util.Date())) {
                System.err.println("Estimated launch date must be in the future.");
                return false;
            }

            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(UPDATE_PROJECT);

            ps.setString(1, project.getProjectName());
            ps.setString(2, project.getDescription());
            ps.setString(3, project.getStatus());

            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            ps.setDate(4, sqlDate);
            ps.setInt(5, project.getProjectID());

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

    public List<StartupProjectsDTO> getProductsByName(String projectName) {
        List<StartupProjectsDTO> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_PROJECTS_BY_PROJECT_NAME);
            ps.setString(1, "%" + projectName + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                StartupProjectsDTO product = new StartupProjectsDTO();
                product.setProjectID(rs.getInt("project_id"));
                product.setProjectName(rs.getString("project_name"));
                product.setDescription(rs.getString("description"));
                product.setStatus(rs.getString("status"));
                product.setEstimated(rs.getDate("estimated_launch"));

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
