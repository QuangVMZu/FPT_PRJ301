<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="utils.AuthUtils" %>
<%@page import="model.ExamsDTO"%>
<%@page import="model.UserDTO" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Exam List</title>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                background-color: #eef2f5;
                margin: 0;
                padding: 0;
            }

            .container {
                max-width: 1100px;
                margin: 40px auto;
                background-color: #fff;
                padding: 40px;
                border-radius: 12px;
                box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
            }

            h2 {
                text-align: center;
                color: #2c3e50;
                font-size: 28px;
                margin-bottom: 30px;
            }

            .header {
                margin-bottom: 24px;
            }

            .back-link {
                text-decoration: none;
                background-color: #6c757d;
                color: white;
                padding: 10px 18px;
                border-radius: 6px;
                font-weight: 600;
                transition: background-color 0.3s ease;
            }

            .back-link:hover {
                background-color: #5a6268;
            }

            .header-actions {
                text-align: right;
                margin-bottom: 20px;
            }

            .logout-btn {
                text-decoration: none;
                background-color: #007bff;
                color: white;
                padding: 10px 18px;
                border-radius: 6px;
                font-weight: 600;
                transition: background-color 0.3s ease;
            }

            .logout-btn:hover {
                background-color: #0056b3;
            }

            form {
                margin-top: 25px;
                display: flex;
                flex-wrap: wrap;
                align-items: center;
                gap: 10px;
            }

            select, button {
                padding: 10px 14px;
                border: 1px solid #ccc;
                border-radius: 6px;
                font-size: 15px;
            }

            button {
                background-color: #28a745;
                color: white;
                font-weight: 600;
                border: none;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            button:hover {
                background-color: #218838;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 25px;
            }

            th, td {
                padding: 14px 12px;
                border: 1px solid #dee2e6;
                text-align: center;
                font-size: 15px;
            }

            th {
                background-color: #f8f9fa;
                color: #343a40;
                font-weight: 600;
            }

            tr:hover {
                background-color: #f1f1f1;
            }

            a {
                color: #007bff;
                text-decoration: none;
                font-weight: 500;
            }

            a:hover {
                text-decoration: underline;
            }

            .no-data {
                text-align: center;
                padding: 24px;
                color: #dc3545;
                font-weight: 600;
                font-size: 16px;
            }

            @media (max-width: 768px) {
                .container {
                    padding: 20px;
                }

                table, th, td {
                    font-size: 14px;
                }

                form {
                    flex-direction: column;
                    align-items: stretch;
                }

                select, button {
                    width: 100%;
                }
            }
        </style>

    </head>
    <body>
        <div class="container">
            <%
                boolean isInstructor = AuthUtils.isInstructor(request);
                boolean isStudent = AuthUtils.isStudent(request);
                String checkError = (String) request.getAttribute("checkError");
                UserDTO user = AuthUtils.getCurrentUser(request);
            %>
            <h2>Exams in Selected Category</h2>

            <div class="header">
                <a href="javascript:history.back()" class="back-link">← Back to Category</a>
            </div>

            <div class="header-actions">
                <% if (user == null) { %>
                <a href="login.jsp" class="logout-btn">Login</a>
                <% } %>
            </div>

            <% if(isStudent) { %>
            <div style="margin-top: 20px;">
                <form method="get" action="MainController">
                    <input type="hidden" name="action" value="filterExamsByTitle">
                    <label for="searchTitle"><strong>Filter by subject</strong></label>
                    <select name="searchTitle" id="searchTitle"
                            style="padding: 8px; width: 250px; border-radius: 4px; border: 1px solid #ccc;">
                        <option value="">-- Select Exam Subject --</option>
                        <%
                            List<String> titles = (List<String>) request.getAttribute("examTitles");
                            String selectedTitle = request.getParameter("searchTitle");
                            if (titles != null) {
                                for (String t : titles) {
                        %>
                        <option value="<%= t %>" <%= t.equals(selectedTitle) ? "selected" : "" %>>
                            <%= t %>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                    <button type="submit" style="padding: 8px 14px; background-color: #007bff; color: white; border: none; border-radius: 5px;">
                        Filter
                    </button>
                </form>
            </div>
            <% } %>

            <table>
                <tr>
                    <th>Title</th>
                    <th>Subject</th>
                    <th>Total Marks</th>
                    <th>Duration (min)</th>
                    <th>Action</th>
                </tr>
                <%
                    List<ExamsDTO> exams = (List<ExamsDTO>) request.getAttribute("exams");
                    if (exams != null && !exams.isEmpty()) {
                        for (ExamsDTO exam : exams) {
                %>
                <tr>
                    <td><%= exam.getExamTitle() %></td>
                    <td><%= exam.getSubject() %></td>
                    <td><%= exam.getTotalMarks() %></td>
                    <td><%= exam.getDuration() %></td>
                    <td>
                        <a href="MainController?action=viewQuestionsByExams&examId=<%= exam.getExamId() %>">
                            Take Exam
                        </a>
                    </td>
                </tr>
                <%   }
                    } else {
                %>
                <tr>
                    <td colspan="5" class="no-data">No exams found for this category.</td>
                </tr>
                <% } %>
            </table>
        </div>
    </body>
</html>
