<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO" %>
<%@page import="utils.AuthUtils" %>
<%@page import="java.util.List" %>
<%@page import="model.StartupProjectsDTO" %>
<%@page import="java.text.SimpleDateFormat" %>
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
            boolean isFounder = AuthUtils.isFounder(request);
            UserDTO user = AuthUtils.getCurrentUser(request);
            String keyword = (String) request.getAttribute("keyword");
            String checkError = (String) request.getAttribute("checkError");
            List<StartupProjectsDTO> list = (List<StartupProjectsDTO>) request.getAttribute("list");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        %>
        <div class="container">
            <div class="header-section">
                <% if(user != null) {
                %>
                <h1>Welcome <%= user.getName() %>!</h1>
                <% } %>
                <div class="header-actions">
                    <% if (isFounder) { %>
                    <a href="projectHome.jsp" class="add-product-btn">Add Project</a>
                    <a href="MainController?action=logout" class="logout-btn">Logout</a>
                    <% } else if (user != null) { %>
                    <a href="MainController?action=logout" class="logout-btn">Logout</a>
                    <% } else { %>
                    <a style="background-color: green", href="login.jsp" class="logout-btn">Login</a>
                    <% } %>
                </div>
            </div>

            <div class="content">
                <div class="search-section">
                    <% if (isFounder) { %>
                    <form action="MainController" method="post" class="search-form">
                        <input type="hidden" name="action" value="searchProject"/>
                        <label>Search project by name:</label>
                        <input type="text" name="keyword" value="<%= keyword != null ? keyword : "" %>" placeholder="Enter project name..."/>
                        <input type="submit" value="Search"/>
                    </form>
                    <% } %>
                </div>

                <% if (list != null && !list.isEmpty()) { %>
                <table class="products-table">
                    <thead>
                        <tr>
                            <th>Project ID</th>
                            <th>Project Name</th>
                            <th>Description</th>
                            <th>Status</th>
                            <th>Estimated</th>
                                <% if (isFounder) { %>
                            <th>Action</th>
                                <% } %>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (StartupProjectsDTO p : list) { %>
                        <tr>
                            <td><%= p.getProjectID() %></td>
                            <td><%= p.getProjectName() %></td>
                            <td><%= p.getDescription() %></td>
                            <td><%= p.getStatus() %></td>
                            <td><%= df.format(p.getEstimated()) %></td>
                            <% if (isFounder) { %>
                            <td>
                                <form action="MainController" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="editProject"/>
                                    <input type="hidden" name="project_id" value="<%= p.getProjectID() %>"/>
                                    <input type="hidden" name="keyword" value="<%= keyword != null ? keyword : "" %>" />
                                    <input type="submit" value="Edit" class="edit-btn" />
                                </form>
                            </td>
                            <% } %>
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
