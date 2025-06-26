<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.StartupProjectsDTO" %>
<%@page import="utils.AuthUtils" %>
<%@page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Project Home</title>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f0f2f5;
            }

            .container {
                max-width: 800px;
                margin: 40px auto;
                background: #fff;
                padding: 30px 40px;
                border-radius: 10px;
                box-shadow: 0 4px 16px rgba(0,0,0,0.1);
            }

            .header {
                margin-bottom: 20px;
            }

            .header h1 {
                color: #333;
                text-align: center;
            }

            .back-link {
                text-decoration: none;
                color: #007bff;
                font-size: 14px;
                display: inline-block;
                margin-bottom: 15px;
            }

            .form-group {
                margin-bottom: 15px;
            }

            label {
                display: block;
                font-weight: bold;
                margin-bottom: 5px;
            }

            .required {
                color: red;
            }

            input[type="text"],
            input[type="date"],
            select,
            textarea {
                width: 100%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 6px;
                box-sizing: border-box;
                font-size: 14px;
            }

            textarea[readonly],
            input[readonly] {
                background-color: #f8f8f8;
            }

            textarea {
                height: 100px;
                resize: vertical;
            }

            select {
                background-color: white;
            }

            .button-group {
                margin-top: 20px;
            }

            .button-group input[type="submit"],
            .button-group input[type="reset"] {
                padding: 10px 16px;
                font-size: 14px;
                margin-right: 10px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
            }

            .button-group input[type="submit"] {
                background-color: #28a745;
                color: white;
            }

            .button-group input[type="reset"] {
                background-color: #ffc107;
                color: #000;
            }

            .error-message {
                color: red;
                margin-top: 15px;
                font-weight: bold;
            }

            .success-message {
                color: green;
                margin-top: 15px;
                font-weight: bold;
            }

            .access-denied {
                text-align: center;
                color: #c00;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <%
                    String checkError = (String) request.getAttribute("checkError");
                    String message = (String) request.getAttribute("message");
                    StartupProjectsDTO project = (StartupProjectsDTO) request.getAttribute("project");
                    Boolean isEdit = (Boolean)request.getAttribute("isEdit")!=null;
                    String keyword = (String) request.getAttribute("keyword");
            %>
            <div class="header">
                <a href="ProjectController?action=viewAllProjects" class="back-link">← Back to Projects</a>
                <h1><%= isEdit ? "EDIT PROJECT" : "ADD PROJECT" %></h1>
            </div>

            <div class="form-container">
                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="<%= isEdit ? "updateProject" : "addProject" %>"/>

                    <div class="form-group">
                        <label for="id">ID <span class="required">*</span></label>
                        <input type="text" id="id" name="project_id" required
                               value="<%= project != null ? project.getProjectID() : "" %>"
                               <%= isEdit ? "readonly" : "" %> />
                    </div>

                    <div class="form-group">
                        <label for="name">Name <span class="required">*</span></label>
                        <input type="text" id="name" name="project_name" required
                               value="<%= project != null ? project.getProjectName() : "" %>"
                               <%= isEdit ? "readonly" : "" %> />
                    </div>

                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea id="description" name="description" placeholder="Enter project description..." 
                                  <%= isEdit ? "readonly" : "" %>><%= project != null ? project.getDescription() : "" %></textarea>
                    </div>

                    <div class="form-group">
                        <label for="status">Status <span class="required">*</span></label>
                        <select id="status" name="status" required>
                            <option value="Ideation" <%= project != null && "Ideation".equals(project.getStatus()) ? "selected" : "" %>>Ideation</option>
                            <option value="Development" <%= project != null && "Development".equals(project.getStatus()) ? "selected" : "" %>>Development</option>
                            <option value="Launch" <%= project != null && "Launch".equals(project.getStatus()) ? "selected" : "" %>>Launch</option>
                            <option value="Scaling" <%= project != null && "Scaling".equals(project.getStatus()) ? "selected" : "" %>>Scaling</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="estimated">Estimated Date <span class="required">*</span></label>
                        <input type="date" id="estimated" name="estimated_launch" required
                               value="<%= (project != null && project.getEstimated() != null) 
                                   ? new SimpleDateFormat("yyyy-MM-dd").format(project.getEstimated()) 
                                   : "" %>"
                               <%= isEdit ? "readonly" : "" %> />
                    </div>

                    <div class="button-group">
                        <input type="hidden" name="keyword" value="<%= keyword != null ? keyword : "" %>" />
                        <input type="submit" value="<%= isEdit ? "Update Project" : "Add Project" %>"/>
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
