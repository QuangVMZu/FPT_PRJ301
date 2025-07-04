<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Exam Result</title>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                background: #f0f2f5;
                margin: 0;
                padding: 0;
            }

            .container {
                max-width: 600px;
                margin: 100px auto;
                padding: 40px;
                background: white;
                border-radius: 10px;
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
                text-align: center;
            }

            h2 {
                color: #2c3e50;
                margin-bottom: 30px;
            }

            p {
                font-size: 20px;
                color: #34495e;
            }

            strong {
                color: #27ae60;
                font-size: 24px;
            }

            a {
                display: inline-block;
                margin-top: 30px;
                padding: 10px 20px;
                background-color: #3498db;
                color: white;
                text-decoration: none;
                border-radius: 5px;
                font-weight: bold;
            }

            a:hover {
                background-color: #2980b9;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Your Result</h2>
            <p>Score: <strong><%= request.getAttribute("score") %></strong> / <%= request.getAttribute("total") %></p>
            <p>Mark: <strong><%= request.getAttribute("mark") %></p>
            <a href="ExamsCategoryController?action=viewAllCategory">← Back to Categories</a>
        </div>
    </body>
</html>
