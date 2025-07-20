<%-- 
    Document   : search
    Created on : Apr 26, 2025, 8:59:02 AM
    Author     : Computing Fundamental - HCM Campus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="pe.model.RoomForRentDto" %>
<%@page import="pe.model.RoomForRentDao" %>
<%@page import="pe.model.AccountDto" %>
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
        <%
            if (!AuthUtils.isLoggedIn(request)) {
                response.sendRedirect("login.jsp");
                return;
            }
           String keyword = (String) request.getAttribute("keyword");
           String checkError = (String) request.getAttribute("checkError");
           List<RoomForRentDto> list = (List<RoomForRentDto>) request.getAttribute("list");
           List<RoomForRentDto> fulllist = (List<RoomForRentDto>) request.getAttribute("fulllist");
        %>

        <div class="search-section">
            <form action="MainController" method="post" class="search-form">
                <input type="hidden" name="action" value="search"/>
                <label>Criteria for searching:</label>
                <input type="text" name="keyword" value="<%= keyword != null ? keyword : "" %>" placeholder=""/>
                <input type="submit" value="Search"/>
            </form>
        </div>
        <% if (list != null && !list.isEmpty()) { %>
        <table class="products-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Location</th>
                    <th>Description</th>
                    <th>Posted date</th>
                    <th>Price</th>
                    <th>Tool</th>
                </tr>
            </thead>
            <tbody>
                <% for (RoomForRentDto p : list) { %>
                <% if(p.getStatus() == -2) continue; %>
                <tr>
                    <td><%= p.getId() %></td>
                    <td><%= p.getTitle() %></td>
                    <td><%= p.getLocation() %></td>
                    <td><%= p.getDescription() %></td>
                    <td><%= p.getPostedDate() %></td>
                    <td><%= p.getPrice() %></td>
                    <% if(p.getStatus() != -1) { %>
                    <td>
                        <form action="MainController" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="delete"/>
                            <input type="hidden" name="id" value="<%= p.getId() %>"/>
                            <input type="hidden" name="keyword" value="<%= keyword != null ? keyword : "" %>" />
                            <input type="submit" value="Delete" class="edit-btn" />
                        </form>
                    </td>
                    <% } %>
                </tr>
                <% } %>
            </tbody>
        </table>
        <% } else if (checkError != null && !checkError.isEmpty()) { %>
        <div class="error-message" style="color: red;"><%= checkError %></div>
        <% } %>
    </body>
</html>
