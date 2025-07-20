<%-- 
    Document   : welcome
    Created on : Apr 26, 2025, 8:58:34 AM
    Author     : Computing Fundamental - HCM Campus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="pe.model.RoomForRentDto" %>
<%@page import="pe.model.RoomForRentDao" %>
<%@page import="pe.model.AccountDto" %>
<%@page import="pe.utils.AuthUtils" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome Page</title>
    </head>
    <body>
        <%
                AccountDto user = AuthUtils.getCurrentUser(request);
                String keyword = (String) request.getAttribute("keyword");
                String checkError = (String) request.getAttribute("checkError");
        %>
        <div class="container">
            <div class="header-section">
                <div class="header-actions">
                    <% if(user != null) {
                    %>
                    <h1>Welcome to <%= user.getFullName() %>!</h1>
                    <% } %>

                    <a href="search.jsp" class="logout-btn">search by name</a><br>
                    <% if (user != null) { %>
                    <a href="MainController?action=logout" class="logout-btn">Logout</a>
                    <% } %>
                </div>
            </div>
        </div>
    </body>
</html>