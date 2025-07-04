<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Access Denied</title>
    <style>
        body {
            background-color: #f8d7da;
            font-family: Arial, sans-serif;
            color: #721c24;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .error-box {
            background-color: #f5c6cb;
            padding: 30px 40px;
            border-radius: 10px;
            border: 1px solid #f1b0b7;
            text-align: center;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        }

        .error-box h1 {
            margin: 0 0 15px;
            font-size: 24px;
        }

        .error-box p {
            font-size: 16px;
        }

        .error-box a {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            background-color: #c82333;
            color: white;
            padding: 10px 20px;
            border-radius: 6px;
        }

        .error-box a:hover {
            background-color: #a71d2a;
        }
    </style>
</head>
<body>
    <div class="error-box">
        <h1>Access Denied</h1>
        <p>You do not have permission to access this page.</p>
        <a href="login.jsp">Back to Login</a>
    </div>
</body>
</html>
