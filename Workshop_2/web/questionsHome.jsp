<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO" %>
<%@page import="utils.AuthUtils" %>
<%@page import="java.util.List" %>
<%@page import="model.ExamsDTO"%>
<%@page import="model.QuestionsDTO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Exam Questions</title>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                background-color: #f4f7fa;
                margin: 0;
                padding: 0;
            }

            .container {
                width: 96%;
                max-width: 1100px;
                margin: 40px auto;
                background-color: #ffffff;
                padding: 40px;
                border-radius: 12px;
                box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
            }

            h2 {
                text-align: center;
                color: #2c3e50;
                margin-bottom: 30px;
                font-size: 28px;
                letter-spacing: 1px;
            }

            .header {
                margin-bottom: 24px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                flex-wrap: wrap;
            }

            .back-link,
            .add-question-link {
                color: #007bff;
                text-decoration: none;
                font-weight: 600;
                font-size: 15px;
                margin: 6px 0;
            }

            .back-link:hover,
            .add-question-link:hover {
                text-decoration: underline;
            }

            .info-text {
                color: #666;
                font-style: italic;
                margin-bottom: 10px;
                font-size: 15px;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            th, td {
                border: 1px solid #e0e0e0;
                padding: 12px 14px;
                text-align: left;
            }

            th {
                background-color: #f8f9fa;
                color: #333;
                font-weight: 600;
            }

            label {
                display: block;
                margin: 4px 0;
                font-size: 15px;
                cursor: pointer;
            }

            input[type="radio"] {
                margin-right: 6px;
                transform: scale(1.1);
            }

            .center {
                text-align: center;
                margin-top: 30px;
            }

            button {
                background-color: #28a745;
                color: white;
                border: none;
                padding: 12px 28px;
                font-size: 16px;
                border-radius: 6px;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            button:hover {
                background-color: #218838;
            }

            p {
                text-align: center;
                color: #999;
                font-size: 16px;
                margin-top: 30px;
            }

            @media (max-width: 768px) {
                .header {
                    flex-direction: column;
                    align-items: flex-start;
                }

                .back-link, .add-question-link {
                    width: 100%;
                    margin-bottom: 10px;
                }

                table, th, td {
                    font-size: 14px;
                }

                button {
                    width: 100%;
                }
            }
        </style>
    </head>
    <body>
        <div class="container">
            <%
                boolean isInstructor = AuthUtils.isInstructor(request);
                String checkError = (String) request.getAttribute("checkError");
                String message = (String) request.getAttribute("message");
                UserDTO user = AuthUtils.getCurrentUser(request);
                List<QuestionsDTO> ques = (List<QuestionsDTO>) request.getAttribute("ques");
                ExamsDTO edto = (ExamsDTO) request.getAttribute("exams");
                int question = 0;
                int examId = (request.getAttribute("exam_id") != null) ? (Integer) request.getAttribute("exam_id") : -1;
            %>

            <h2>Exam Questions</h2>

            <div class="header">
                <a href="javascript:history.back()" class="back-link">← Back to Exams</a>
                <% if(isInstructor) { %>
                <a href="MainController?action=addQuestionsByExam&exam_id=<%= examId %>" class="add-question-link">+ Add Question</a>
                <% } %>
            </div>

            <% if (edto != null) { %>
            <div class="info-text">Duration of this exam: <%= edto.getDuration() %> minutes</div>
            <% } %>

            <% if (!isInstructor && ques != null && !ques.isEmpty()) { %>
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="submitAnswer" />
                <input type="hidden" name="exam_id" value="<%= examId %>" />

                <table>
                    <tr>
                        <th>Question</th>
                        <th>Question Text</th>
                        <th>Options</th>
                    </tr>
                    <% for (QuestionsDTO q : ques) { question++; %>
                    <tr>
                        <td><%= question %></td>
                        <td><%= q.getQuestionText() %></td>
                        <td>
                            <label><input type="radio" name="answer_<%= q.getQuestionId() %>" value="A" /> A. <%= q.getOption_a() %></label>
                            <label><input type="radio" name="answer_<%= q.getQuestionId() %>" value="B" /> B. <%= q.getOption_b() %></label>
                            <label><input type="radio" name="answer_<%= q.getQuestionId() %>" value="C" /> C. <%= q.getOption_c() %></label>
                            <label><input type="radio" name="answer_<%= q.getQuestionId() %>" value="D" /> D. <%= q.getOption_d() %></label>
                        </td>
                    </tr>
                    <% } %>
                </table>

                <div class="center">
                    <button type="submit">Submit Answers</button>
                </div>
            </form>

            <% } else if (isInstructor && ques != null && !ques.isEmpty()) { %>
            <table>
                <tr>
                    <th>Question</th>
                    <th>Exam ID</th>
                    <th>Question Text</th>
                    <th>Option A</th>
                    <th>Option B</th>
                    <th>Option C</th>
                    <th>Option D</th>
                    <th>Correct</th>
                </tr>
                <% for (QuestionsDTO q : ques) { question++; %>
                <tr>
                    <td><%= question %></td>
                    <td><%= q.getExamId() %></td>
                    <td><%= q.getQuestionText() %></td>
                    <td><%= q.getOption_a() %></td>
                    <td><%= q.getOption_b() %></td>
                    <td><%= q.getOption_c() %></td>
                    <td><%= q.getOption_d() %></td>
                    <td><%= q.getCorrectOption() %></td>
                </tr>
                <% } %>
            </table>
            <% } %>
            <% if (checkError != null && !checkError.isEmpty()) { %>
            <p class="error-message"><%= checkError %></p>
            <% } else if (message != null && !message.isEmpty()) { %>
            <p class="success-message"><%= message %></p>
            <% } %>
        </div>
    </body>
</html>
