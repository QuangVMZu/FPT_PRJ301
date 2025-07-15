<%-- 
    Document   : update
    Created on : Jul 15, 2025, 11:08:02 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="pe.model.FashionDto" %>
<%@page import="pe.model.FashionDao" %>
<%@page import="pe.model.UserDto" %>
<%@page import="pe.utils.AuthUtils" %>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="welcome.jsp" />
        <div class="container">
            <%
                    String checkError = (String) request.getAttribute("checkError");
                    String message = (String) request.getAttribute("message");
                    FashionDto project = (FashionDto) request.getAttribute("product");
                    String keyword = (String) request.getAttribute("keyword");
            %>
            <div class="header">
                <h1>Update product from</h1>
            </div>

            <div class="form-container">
                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="update"/>

                    <div class="form-group">
                        <label for="id">ID <span class="required">*</span></label>
                        <input type="text" id="id" name="id" required
                               value="<%= project != null ? project.getId() : "" %>"
                               readonly="readonly"/>
                    </div>

                    <div class="form-group">
                        <label for="name">Name <span class="required">*</span></label>
                        <input type="text" id="name" name="name" required
                               value="<%= project != null ? project.getName() : "" %>"/>
                    </div>

                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea id="description" name="description" placeholder="Enter product description..." 
                                  ><%= project != null ? project.getDescription() : "" %></textarea>
                    </div>

                    <div class="form-group">
                        <label for="price">Price <span class="required">*</span></label>
                        <input type="text" id="price" name="price" required
                               value="<%= project != null ? project.getPrice() : "" %>"/>
                    </div>

                    <div class="form-group">
                        <label for="size">Size <span class="required">*</span></label>
                        <input type="text" id="size" name="size" required
                               value="<%= project != null ? project.getSize() : "" %>"/>
                    </div>

                    <div class="form-group">
                        <label for="status">Status <span class="required">*</span></label>
                        <select id="status" name="status" required>
                            <option value="1" <%= project != null && project.getStatus() == 1 ? "selected" : "" %>>1 - Active</option>
                            <option value="0" <%= project != null && project.getStatus() == 0 ? "selected" : "" %>>0 - Inactive</option>
                        </select>
                    </div>
                    <div class="button-group">
                        <input type="hidden" name="keyword" value="<%= keyword != null ? keyword : "" %>" />
                        <input type="submit" value="Save to database"/>
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
