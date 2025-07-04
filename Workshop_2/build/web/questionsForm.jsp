<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.QuestionsDTO" %>
<%@page import="model.ExamsDTO" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add Question</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
            }

            .container {
                width: 500px;
                margin: 50px auto;
                background: white;
                padding: 30px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                border-radius: 8px;
            }

            h1 {
                text-align: center;
                color: #333;
            }

            .form-group {
                margin-bottom: 15px;
            }

            label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
            }

            input[type="text"] {
                width: 100%;
                padding: 8px;
                box-sizing: border-box;
            }

            .button-group {
                text-align: center;
                margin-top: 20px;
            }

            .button-group input {
                padding: 10px 20px;
                margin: 0 10px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            input[type="submit"] {
                background-color: #28a745;
                color: white;
            }

            input[type="reset"] {
                background-color: #dc3545;
                color: white;
            }

            .back-link {
                display: block;
                margin-bottom: 20px;
                color: #007bff;
                text-decoration: none;
            }

            .error-message {
                color: red;
                margin-top: 15px;
                text-align: center;
            }

            .success-message {
                color: green;
                margin-top: 15px;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <%
                String checkError = (String) request.getAttribute("checkError");
                String message = (String) request.getAttribute("message");
                QuestionsDTO question = (QuestionsDTO) request.getAttribute("question");
                int examId = (request.getAttribute("exam_id") != null) ? (Integer) request.getAttribute("exam_id") : -1;
            %>

            <a href="javascript:history.back()" class="back-link">← Back to Category</a>
            <h1>Add Question</h1>

            <div class="form-container">
                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="addQuestionsByExam"/>
                    <input type="hidden" name="exam_id" value="<%= examId %>" />

                    <div class="form-group">
                        <label for="question_text">Question Text <span class="required">*</span></label>
                        <input type="text" id="question_text" name="question_text" required
                               value="<%= (question != null) ? question.getQuestionText() : "" %>"/>
                    </div>

                    <div class="form-group">
                        <label for="option_a">Option A <span class="required">*</span></label>
                        <input type="text" id="option_a" name="option_a" required
                               value="<%= (question != null) ? question.getOption_a() : "" %>"/>
                    </div>

                    <div class="form-group">
                        <label for="option_b">Option B <span class="required">*</span></label>
                        <input type="text" id="option_b" name="option_b" required
                               value="<%= (question != null) ? question.getOption_b() : "" %>"/>
                    </div>

                    <div class="form-group">
                        <label for="option_c">Option C <span class="required">*</span></label>
                        <input type="text" id="option_c" name="option_c" required
                               value="<%= (question != null) ? question.getOption_c() : "" %>"/>
                    </div>

                    <div class="form-group">
                        <label for="option_d">Option D <span class="required">*</span></label>
                        <input type="text" id="option_d" name="option_d" required
                               value="<%= (question != null) ? question.getOption_d() : "" %>"/>
                    </div>

                    <div class="form-group">
                        <label for="correct_option">Correct Option (A/B/C/D) <span class="required">*</span></label>
                        <input type="text" id="correct_option" name="correct_option" required
                               value="<%= (question != null) ? question.getCorrectOption() : "" %>"/>
                    </div>

                    <div class="button-group">
                        <input type="submit" value="Add Question"/>
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
