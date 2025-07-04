<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO" %>
<%@page import="utils.AuthUtils" %>
<%@page import="java.util.List" %>
<%@page import="model.ExamCategoriesDTO" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="model.ExamsDTO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Exam Home</title>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                background-color: #f4f6f8;
                margin: 0;
                padding: 0;
            }

            .container {
                max-width: 700px;
                margin: 50px auto;
                padding: 30px;
                background-color: #ffffff;
                border-radius: 12px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            }

            .header {
                margin-bottom: 30px;
            }

            .header h1 {
                text-align: center;
                color: #333;
                margin-bottom: 10px;
            }

            .back-link {
                display: inline-block;
                text-decoration: none;
                background-color: #6c757d;
                color: white;
                padding: 8px 14px;
                border-radius: 5px;
                margin-bottom: 20px;
                font-weight: bold;
            }

            .form-container form {
                display: flex;
                flex-direction: column;
            }

            .form-group {
                margin-bottom: 15px;
            }

            label {
                font-weight: bold;
                margin-bottom: 5px;
                display: block;
            }

            .required {
                color: red;
            }

            input[type="text"],
            select {
                padding: 10px;
                width: 100%;
                border-radius: 6px;
                border: 1px solid #ccc;
                font-size: 14px;
            }

            .button-group {
                display: flex;
                justify-content: space-between;
                margin-top: 20px;
            }

            input[type="submit"],
            input[type="reset"] {
                padding: 10px 18px;
                font-size: 14px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                color: white;
            }

            input[type="submit"] {
                background-color: #007bff;
            }

            input[type="reset"] {
                background-color: #dc3545;
            }

            .error-message {
                color: #d9534f;
                font-weight: bold;
                margin-top: 20px;
            }

            .success-message {
                color: #28a745;
                font-weight: bold;
                margin-top: 20px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <%
                String checkError = (String) request.getAttribute("checkError");
                String message = (String) request.getAttribute("message");
                ExamsDTO edto = (ExamsDTO) request.getAttribute("exams");
                List<ExamCategoriesDTO> categories = (List<ExamCategoriesDTO>) request.getAttribute("categories");
            %>
            <div class="header">
                <a href="javascript:history.back()" class="back-link">← Back to Exam List</a>
                <h1>CREATE EXAMS</h1>
            </div>

            <div class="form-container">
                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="addExams"/>

                    <div class="form-group">
                        <label for="exam_id">Exam Id <span class="required">*</span></label>
                        <input type="text" id="exam_id" name="exam_id" required
                               value="<%= (edto != null) ? edto.getExamId() : "" %>"/>
                    </div>

                    <div class="form-group">
                        <label for="exam_title">Exam Title <span class="required">*</span></label>
                        <input type="text" id="exam_title" name="exam_title" required
                               value="<%= (edto != null) ? edto.getExamTitle() : "" %>"/>
                    </div>

                    <div class="form-group">
                        <label for="subject">Subject <span class="required">*</span></label>
                        <input type="text" id="subject" name="subject" required
                               value="<%= (edto != null) ? edto.getSubject() : "" %>"/>
                    </div>

                    <div class="form-group">
                        <label for="category_id">Exam Category <span class="required">*</span></label>
                        <select name="category_id" id="category_id" required>
                            <option value="">-- Select Category --</option>
                            <option value="1" <%= edto != null && edto.getCategoryId() == 1 ? "selected" : "" %>>Quiz</option>
                            <option value="2" <%= edto != null && edto.getCategoryId() == 2 ? "selected" : "" %>>Midterm</option>
                            <option value="3" <%= edto != null && edto.getCategoryId() == 3 ? "selected" : "" %>>Final</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="total_marks">Total Marks <span class="required">*</span></label>
                        <input type="text" id="total_marks" name="total_marks" required
                               value="<%= (edto != null) ? edto.getTotalMarks() : "" %>"/>
                    </div>

                    <div class="form-group">
                        <label for="duration">Duration (minutes) <span class="required">*</span></label>
                        <input type="text" id="duration" name="duration" required min="1"
                               value="<%= (edto != null) ? edto.getDuration() : "" %>"/>
                    </div>

                    <div class="button-group">
                        <input type="submit" value="Add Exams"/>
                        <input type="reset" value="Reset"/> 
                    </div>
                </form>

                <% if (checkError != null && !checkError.isEmpty()) { %>
                <div class="error-message"><%= checkError %></div>
                <% } else if (message != null && !message.isEmpty()) { %>
                <div class="success-message"><%= message %></div>
                <% } %>
            </div>
        </div>
    </body>
</html>
