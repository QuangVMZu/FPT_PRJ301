<%-- 
    Document   : search
    Created on : Apr 26, 2025, 8:59:02 AM
    Author     : Computing Fundamental - HCM Campus
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
        <title>Search Page</title>
    </head>
    <body>
        <jsp:include page="welcome.jsp" />
        <% String keyword = (String) request.getAttribute("keyword");
           String checkError = (String) request.getAttribute("checkError");
           List<FashionDto> list = (List<FashionDto>) request.getAttribute("list");
           
        %>
        <div class="search-section">
            <form action="MainController" method="post" class="search-form">
                <input type="hidden" name="action" value="search"/>
                <label>Search project by name:</label>
                <input type="text" name="keyword" value="<%= keyword != null ? keyword : "" %>" placeholder="Enter project name..." required/>
                <input type="submit" value="Search"/>
            </form>
        </div>
        <% if (list != null && !list.isEmpty()) { %>
        <table class="products-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Size</th>
                    <th>Status</th>
                    <th>Tool</th>
                </tr>
            </thead>
            <tbody>
                <% for (FashionDto p : list) { %>
                <tr>
                    <td><%= p.getId() %></td>
                    <td><%= p.getName() %></td>
                    <td><%= p.getDescription() %></td>
                    <td><%= p.getPrice() %></td>
                    <td><%= p.getSize() %></td>
                    <td><%= p.getStatus() %></td>
                    <td>
                        <form action="MainController" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="edit"/>
                            <input type="hidden" name="id" value="<%= p.getId() %>"/>
                            <input type="hidden" name="keyword" value="<%= keyword != null ? keyword : "" %>" />
                            <input type="submit" value="Update" class="edit-btn" />
                        </form>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <% } else if (checkError != null && !checkError.isEmpty()) { %>
        <div class="error-message" style="color: red;"><%= checkError %></div>
        <% } %>
    </body>
</html>
