<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO" %>
<%@page import="utils.AuthUtils" %>
<%@page import="java.util.List" %>
<%@page import="model.ExamCategoriesDTO" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Welcome Page</title>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                margin: 0;
                padding: 0;
                background: #f4f4f4;
            }

            .container {
                max-width: 1000px;
                margin: 40px auto;
                padding: 20px;
                background: #fff;
                border-radius: 10px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            }

            .header-section {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .header-section h1 {
                color: #333;
            }

            .header-actions a {
                text-decoration: none;
                padding: 8px 14px;
                border-radius: 6px;
                margin-left: 10px;
                font-size: 14px;
                font-weight: bold;
            }

            .add-product-btn {
                background-color: #28a745;
                color: white;
            }

            .logout-btn {
                background-color: #dc3545;
                color: white;
            }

            .search-section {
                margin-top: 25px;
            }

            .search-form input[type="text"] {
                padding: 8px;
                width: 250px;
                border: 1px solid #ccc;
                border-radius: 6px;
                margin-right: 10px;
            }

            .search-form input[type="submit"] {
                padding: 8px 14px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 6px;
                cursor: pointer;
            }

            .products-table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 30px;
            }

            .products-table th, .products-table td {
                border: 1px solid #ddd;
                padding: 10px;
            }

            .products-table th {
                background-color: #f8f9fa;
                text-align: left;
            }

            .products-table a {
                text-decoration: none;
                color: #007bff;
                font-weight: 500;
            }

            .products-table a:hover {
                text-decoration: underline;
                color: #0056b3;
            }

            .edit-btn {
                padding: 5px 10px;
                background-color: #ffc107;
                color: #000;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            .edit-btn:hover {
                background-color: #e0a800;
            }

            .error-message {
                margin-top: 20px;
                color: red;
                font-weight: bold;
            }

            .access-denied {
                text-align: center;
                padding: 50px;
            }

            .login-link {
                display: inline-block;
                margin-top: 20px;
                padding: 10px 15px;
                background-color: #007bff;
                color: white;
                text-decoration: none;
                border-radius: 5px;
            }

            .login-link:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <%
            boolean isInstructor = AuthUtils.isInstructor(request);
            UserDTO user = AuthUtils.getCurrentUser(request);
            String checkError = (String) request.getAttribute("checkError");
            List<ExamCategoriesDTO> list = (List<ExamCategoriesDTO>) request.getAttribute("list");
        %>
        <div class="container">
            <div class="header-section">
                <% if (user != null) { %>
                <h1>Welcome <%= user.getName() %>!</h1>
                <% } %>
                <div class="header-actions">
                    <% if (isInstructor) { %>
                    <a href="examsHome.jsp" class="add-product-btn">Create Exams</a>
                    <a href="MainController?action=logout" class="logout-btn">Logout</a>
                    <% } else if (user != null) { %>
                    <a href="MainController?action=logout" class="logout-btn">Logout</a>
                    <% } else { %>
                    <a href="login.jsp" class="logout-btn" style="background-color: green;">Login</a>
                    <% } %>
                </div>
            </div>

            <div class="content">
                <% if (list != null && !list.isEmpty()) { %>
                <table class="products-table">
                    <thead>
                        <tr>
                            <th>Category ID</th>
                            <th>Category Name</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (ExamCategoriesDTO p : list) { %>
                        <tr>
                            <td><%= p.getCategoryId() %></td>
                            <td>
                                <a href="MainController?action=viewExamsByCategory&categoryId=<%= p.getCategoryId() %>">
                                    <%= p.getCategoryName() %>
                                </a>
                            </td>
                            <td><%= p.getDescription() %></td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
                <% } else if (checkError != null && !checkError.isEmpty()) { %>
                <div class="error-message"><%= checkError %></div>
                <% } %>
            </div>
        </div>
    </body>
</html>
