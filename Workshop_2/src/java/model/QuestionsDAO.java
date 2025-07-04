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
public class QuestionsDAO {

    private static final String GET_QUESTIONS_BY_EXAMS_ID = "SELECT * FROM tblQuestions WHERE exam_id = ?";
    private static final String CREATE_QUESTION = "INSERT INTO tblQuestions (exam_id, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public boolean isQuestionsExists(int questionId) {
        return getQuestionsByID(questionId) != null;
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

    public List<QuestionsDTO> getQuestionsByID(int questionId) {
        List<QuestionsDTO> listQuestions = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_QUESTIONS_BY_EXAMS_ID);
            ps.setInt(1, questionId);
            rs = ps.executeQuery();

            while (rs.next()) {
                QuestionsDTO question = new QuestionsDTO();
                question.setQuestionId(rs.getInt("question_id"));
                question.setExamId(rs.getInt("exam_id"));
                question.setQuestionText(rs.getString("question_text"));
                question.setOption_a(rs.getString("option_a"));
                question.setOption_b(rs.getString("option_b"));
                question.setOption_c(rs.getString("option_c"));
                question.setOption_d(rs.getString("option_d"));
                question.setCorrectOption(rs.getString("correct_option"));
                
                listQuestions.add(question);
            }
        } catch (Exception e) {
            System.err.println("Error in getExams_id: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return listQuestions;
    }
    
    public boolean create(QuestionsDTO qdto) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(CREATE_QUESTION);

//            ps.setInt(1, qdto.getQuestionId());
            ps.setInt(1, qdto.getExamId());
            ps.setString(2, qdto.getQuestionText());
            ps.setString(3, qdto.getOption_a());
            ps.setString(4, qdto.getOption_b());
            ps.setString(5, qdto.getOption_c());
            ps.setString(6, qdto.getOption_d());
            ps.setString(7, qdto.getCorrectOption());

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

}
