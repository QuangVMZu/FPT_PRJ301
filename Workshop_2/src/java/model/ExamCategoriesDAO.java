/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

/**
 *
 * @author ADMIN
 */
public class ExamCategoriesDAO {

    private static final String GET_ALL = "SELECT * FROM tblExamCategories";
    private static final String GET_CATEGORIES_BY_CATEGORIES_ID = "SELECT * FROM tblExamCategories WHERE category_id LIKE ?";

    public List<ExamCategoriesDTO> getAll() {
        List<ExamCategoriesDTO> ExamC = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL);
            rs = ps.executeQuery();

            while (rs.next()) {
                ExamCategoriesDTO examCategories = new ExamCategoriesDTO();
                examCategories.setCategoryId(rs.getInt("category_id"));
                examCategories.setCategoryName(rs.getString("category_name"));
                examCategories.setDescription(rs.getString("description"));

                ExamC.add(examCategories);
            }
        } catch (Exception e) {
            System.err.println("Error in getAll(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return ExamC;
    }

    public boolean isCategoriesExists(int categoryId) {
        return getCategoriesByID(categoryId) != null;
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

    public ExamCategoriesDTO getCategoriesByID(int category_id) {
        ExamCategoriesDTO examCategories = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_CATEGORIES_BY_CATEGORIES_ID);
            ps.setInt(1, category_id);
            rs = ps.executeQuery();

            if (rs.next()) {
                examCategories = new ExamCategoriesDTO();
                examCategories.setCategoryId(rs.getInt("category_id"));
                examCategories.setCategoryName(rs.getString("category_name"));
                examCategories.setDescription(rs.getString("description"));
            }
        } catch (Exception e) {
            System.err.println("Error in getcategory_id: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return examCategories;
    }

}
