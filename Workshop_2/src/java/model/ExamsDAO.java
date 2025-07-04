/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

/**
 *
 * @author ADMIN
 */
public class ExamsDAO {

    private static final String GET_ALL = "SELECT * FROM tblExams";
    private static final String GET_EXAMS_BY_EXAMS_ID = "SELECT * FROM tblExams WHERE exam_id LIKE ?";
    private static final String CREATE_EXAM = "INSERT INTO tblExams (exam_id, exam_title, subject, category_id, total_marks, duration) VALUES (?, ?, ?, ?, ?, ?)";

    public List<ExamsDTO> getAll() {
        List<ExamsDTO> exs = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL);
            rs = ps.executeQuery();

            while (rs.next()) {
                ExamsDTO exams = new ExamsDTO();
                exams.setExamId(rs.getInt("exam_id"));
                exams.setExamTitle(rs.getString("exam_title"));
                exams.setSubject(rs.getString("subject"));
                exams.setCategoryId(rs.getInt("category_id"));
                exams.setTotalMarks(rs.getInt("total_marks"));
                exams.setDuration(rs.getInt("duration"));

                exs.add(exams);
            }
        } catch (Exception e) {
            System.err.println("Error in getAll(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return exs;
    }

    public boolean isExamsExists(int examId) {
        return getExamsByID(examId) != null;
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

    public ExamsDTO getExamsByID(int exam_id) {
        ExamsDTO exams = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_EXAMS_BY_EXAMS_ID);
            ps.setInt(1, exam_id);
            rs = ps.executeQuery();

            if (rs.next()) {
                exams = new ExamsDTO();
                exams.setExamId(rs.getInt("exam_id"));
                exams.setExamTitle(rs.getString("exam_title"));
                exams.setSubject(rs.getString("subject"));
                exams.setCategoryId(rs.getInt("category_id"));
                exams.setTotalMarks(rs.getInt("total_marks"));
                exams.setDuration(rs.getInt("duration"));
            }
        } catch (Exception e) {
            System.err.println("Error in getExams_id: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return exams;
    }

    public List<ExamsDTO> getExamsByCategoryId(int categoryId) throws SQLException, ClassNotFoundException {
        List<ExamsDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblExams WHERE category_id = ?";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ExamsDTO e = new ExamsDTO();
                e.setExamId(rs.getInt("exam_id"));
                e.setExamTitle(rs.getString("exam_title"));
                e.setSubject(rs.getString("subject"));
                e.setCategoryId(categoryId);
                e.setTotalMarks(rs.getInt("total_marks"));
                e.setDuration(rs.getInt("duration"));
                list.add(e);
            }
        }
        return list;
    }

    public boolean create(ExamsDTO edto) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(CREATE_EXAM);

            ps.setInt(1, edto.getExamId());
            ps.setString(2, edto.getExamTitle());
            ps.setString(3, edto.getSubject());
            ps.setInt(4, edto.getCategoryId());
            ps.setInt(5, edto.getTotalMarks());
            ps.setInt(6, edto.getDuration());

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

    public List<String> getAllExamSubject() throws SQLException, ClassNotFoundException {
        List<String> titles = new ArrayList<>();
        String sql = "SELECT DISTINCT subject FROM tblExams";
        try ( Connection con = DbUtils.getConnection(); 
                PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                titles.add(rs.getString("subject"));
            }
        }
        return titles;
    }

    public List<ExamsDTO> getExamsBySubject(String title) throws SQLException, ClassNotFoundException {
        List<ExamsDTO> exams = new ArrayList<>();
        String sql = "SELECT * FROM tblExams WHERE subject like ?";

        try ( Connection con = DbUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title);

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ExamsDTO exam = new ExamsDTO();
                    exam.setExamId(rs.getInt("exam_id"));
                    exam.setExamTitle(rs.getString("exam_title"));
                    exam.setSubject(rs.getString("subject"));
                    exam.setCategoryId(rs.getInt("category_id"));
                    exam.setTotalMarks(rs.getInt("total_marks"));
                    exam.setDuration(rs.getInt("duration"));
                    exams.add(exam);
                }
            }
        }
        return exams;
    }
}
