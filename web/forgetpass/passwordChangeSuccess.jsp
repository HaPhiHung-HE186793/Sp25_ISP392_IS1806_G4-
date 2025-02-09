<%-- 
    Document   : passwordChangeSuccess
    Created on : 27 thg 9, 2024, 19:10:19
    Author     : bacht
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Password Changed</title>
        <style>
            .container {
                width: 300px;
                margin: 100px auto;
                text-align: center;
                border: 1px solid #000;
                padding: 20px;
                font-family: Arial, sans-serif;
            }

            h2 {
                font-size: 24px;
                margin-bottom: 20px;
                color: green;
            }

            p {
                font-size: 16px;
                margin-bottom: 20px;
                color: #333;
            }

            a {
                color: blue;
                text-decoration: none;
                font-weight: bold;
            }

            a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Password Changed Successfully!</h2>
            <p>Your password has been updated. You can now log in with your new password.</p>
            <p><a href="login.jsp">Back to Login</a></p>
        </div>
    </body>
</html>
