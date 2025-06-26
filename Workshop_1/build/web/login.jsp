<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO" %>
<%@page import="utils.AuthUtils" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background: #f2f2f2;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .login-container {
                background-color: white;
                padding: 30px;
                border-radius: 12px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                width: 320px;
                text-align: center;
            }

            .login-container h2 {
                margin-bottom: 20px;
                color: #333;
            }

            .login-container input[type="text"],
            .login-container input[type="password"] {
                width: 100%;
                padding: 10px;
                margin: 10px 0;
                border: 1px solid #ccc;
                border-radius: 6px;
                box-sizing: border-box;
            }

            .login-container input[type="submit"] {
                width: 100%;
                padding: 10px;
                background-color: #4CAF50;
                border: none;
                color: white;
                border-radius: 6px;
                cursor: pointer;
                font-size: 16px;
            }

            .login-container input[type="submit"]:hover {
                background-color: #45a049;
            }

            .error-message {
                color: red;
                margin-top: 15px;
                font-size: 14px;
            }
        </style>
    </head>
    <body>
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
                <input type="text" name="strUserName" placeholder="Username" required />  
                <input type="password" name="strPassword" placeholder="Password" required />
                <input type="submit" value="Login"/>
            </form>
            <% if (!msg.isEmpty()) { %>
            <div class="error-message"><%= msg %></div>
            <% } %>
        </div>
        <% } %>
    </body>
</html>
