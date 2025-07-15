<%-- 
    Document   : login
    Created on : Apr 26, 2025, 8:58:20 AM
    Author     : Computing Fundamental - HCM Campus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="pe.model.UserDto" %>
<%@page import="pe.utils.AuthUtils" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h2>Login page</h2>
        <%
            if(AuthUtils.isLoggedIn(request)){
                response.sendRedirect("welcome.jsp");
            } else {
                Object objMS = request.getAttribute("message");
                String msg = (objMS == null) ? "" : (String)objMS;
        %>
        <div class="login-container">
            <h2>Login</h2>
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="login"/>
                <input type="text" name="strUserName" placeholder="Username" required /><br><br>
                <input type="password" name="strPassword" placeholder="Password" required /><br><br>
                <% if (!msg.isEmpty()) { %>
                <div class="error-message" style="color: red;"><%= msg %></div> <br>
                <% } %>
                <input type="submit" value="Login"/>
            </form>

        </div>
        <% } %>
    </body>
</html>
