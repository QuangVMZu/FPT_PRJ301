/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import model.ExamCategoriesDAO;
import model.ExamCategoriesDTO;
import model.ExamsDAO;
import model.ExamsDTO;
import model.QuestionsDAO;
import model.QuestionsDTO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ExamsCategoryController", urlPatterns = {"/ExamsCategoryController"})
public class ExamsCategoryController extends HttpServlet {

    private final ExamCategoriesDAO pdao = new ExamCategoriesDAO();
    private final ExamsDAO exdao = new ExamsDAO();
    private final QuestionsDAO questiondao = new QuestionsDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "welcome.jsp";
        try {
            String action = request.getParameter("action");
            switch (action) {
                case "viewAllCategory":
                    url = handleViewAllCategory(request, response);
                    break;
                case "viewExamsByCategory":
                    url = handleViewExamsByCategory(request, response);
                    break;
                case "addExams":
                    url = handleAddExams(request, response);
                    break;
                case "viewQuestionsByExams":
                    url = handleViewQuestionsByExams(request, response);
                    break;
                case "addQuestionsByExam":
                    url = handleAddQuestionsByExam(request, response);
                    break;
                case "submitAnswer":
                    url = handleSubmitAnswer(request, response);
                    break;
                case "filterExamsByTitle":
                    url = handleFilterExamsBySubject(request, response);
                    break;
                default:
                    request.setAttribute("message", "Invalid product action: " + action);
                    url = "error.jsp";
                    break;
            }
        } catch (Exception e) {
            request.setAttribute("checkError", "Unexpected error: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String handleViewAllCategory(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        List<ExamCategoriesDTO> list = pdao.getAll();
        if (list == null || list.isEmpty()) {
            request.setAttribute("checkError", "Don't have Exam Category.");
        }
        request.setAttribute("checkError", checkError);
        request.setAttribute("list", list);
        return "welcome.jsp";
    }

    private String handleViewExamsByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {

        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        List<ExamsDTO> exams = exdao.getExamsByCategoryId(categoryId);
        if (categoryId < 1 && categoryId > 3) {
            request.setAttribute("checkError", "Don't have this category!");
            return "welcome.jsp";
        } else if (exams == null && exams.isEmpty()) {
            request.setAttribute("checkError", "This list Exams not exits!");
            return "welcome.jsp";
        }
        List<String> examTitles = exdao.getAllExamSubject();
        request.setAttribute("examTitles", examTitles);
        request.setAttribute("exams", exams);
        return "examList.jsp";
    }

    private String handleAddExams(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        String message = "";

        try {
            int examId = Integer.parseInt(request.getParameter("exam_id"));
            String examTitle = request.getParameter("exam_title");
            String subject = request.getParameter("subject");
            int categoryId = Integer.parseInt(request.getParameter("category_id"));
            int totalMarks = Integer.parseInt(request.getParameter("total_marks"));
            int duration = Integer.parseInt(request.getParameter("duration"));

            ExamsDTO edto = new ExamsDTO(examId, examTitle, subject, categoryId, totalMarks, duration);

            if (exdao.isExamsExists(examId)) {
                checkError = "Exam ID already exists.";
            } else {
                boolean success = exdao.create(edto);
                if (success) {
                    message = "Exam added successfully.";
                } else {
                    checkError = "Failed to add exam.";
                }
            }

            request.setAttribute("exam", edto);
            request.setAttribute("checkError", checkError);
            request.setAttribute("message", message);

        } catch (Exception e) {
            request.setAttribute("checkError", "Error adding exam: " + e.getMessage());
            e.printStackTrace();
        }

        return "examsHome.jsp";
    }

    private String handleAddQuestionsByExam(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        String message = "";

        int examId = Integer.parseInt(request.getParameter("exam_id"));
        ExamsDTO edto = exdao.getExamsByID(examId);
        List<QuestionsDTO> questions = questiondao.getQuestionsByID(examId);

        try {
            String questionText = request.getParameter("question_text");
            if (questionText != null && !questionText.trim().isEmpty()) {
                String option_a = request.getParameter("option_a");
                String option_b = request.getParameter("option_b");
                String option_c = request.getParameter("option_c");
                String option_d = request.getParameter("option_d");
                String correctOption = request.getParameter("correct_option");

                QuestionsDTO questiondto = new QuestionsDTO();
                questiondto.setExamId(examId);
                questiondto.setQuestionText(questionText);
                questiondto.setOption_a(option_a);
                questiondto.setOption_b(option_b);
                questiondto.setOption_c(option_c);
                questiondto.setOption_d(option_d);
                questiondto.setCorrectOption(correctOption);

                boolean success = questiondao.create(questiondto);
                if (success) {
                    message = "Question added successfully.";
                } else {
                    checkError = "Failed to add question.";
                }
                request.setAttribute("question", questiondto);
            }

        } catch (Exception e) {
            checkError = "Error adding question: " + e.getMessage();
            e.printStackTrace();
        }

        request.setAttribute("exam_id", examId);
        request.setAttribute("exams", edto);
        request.setAttribute("checkError", checkError);
        request.setAttribute("message", message);

        return "questionsForm.jsp";
    }

    private String handleViewQuestionsByExams(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        int examsId = Integer.parseInt(request.getParameter("examId"));
        ExamsDTO edto = exdao.getExamsByID(examsId);
        List<QuestionsDTO> ques = questiondao.getQuestionsByID(examsId);

        if (ques == null || ques.isEmpty()) {
            request.setAttribute("checkError", "This list Exams not exits!");
            return "questionsHome.jsp";
        }

        request.setAttribute("exam_id", examsId);
        request.setAttribute("exams", edto);
        request.setAttribute("checkError", checkError);
        request.setAttribute("ques", ques);
        return "questionsHome.jsp";
    }

    private String handleSubmitAnswer(HttpServletRequest request, HttpServletResponse response) {
        int examId = Integer.parseInt(request.getParameter("exam_id"));
        ExamsDTO edto = exdao.getExamsByID(examId);
        List<QuestionsDTO> questions = questiondao.getQuestionsByID(examId);

        int score = 0;
        int total = questions.size();
        float mark = 0;

        if (edto == null || questions == null) {
            request.setAttribute("checkError", "Exam not found or no questions available.");
            return "questionsHome.jsp";
        }

        for (QuestionsDTO q : questions) {
            String userAnswer = request.getParameter("answer_" + q.getQuestionId());
            if (userAnswer != null && userAnswer.equalsIgnoreCase(q.getCorrectOption())) {
                score++;
            }
        }

        if (total > 0) {
            float pointPerQuestion = (float) edto.getTotalMarks() / total;
            mark = score * pointPerQuestion;
        } else {
            mark = 0;
        }

        request.setAttribute("score", score);
        request.setAttribute("total", total);
        request.setAttribute("mark", String.format("%.1f", mark));
        return "result.jsp";
    }

    private String handleFilterExamsBySubject(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        String subject = request.getParameter("searchTitle");
        List<ExamsDTO> exams = exdao.getExamsBySubject(subject);
        List<String> examTitles = exdao.getAllExamSubject();
        request.setAttribute("examTitles", examTitles);
        request.setAttribute("exams", exams);
        return "examList.jsp";
    }
}
