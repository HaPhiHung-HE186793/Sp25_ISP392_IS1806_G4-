<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style>
            body {
                font-family: sans-serif;
            }
            h1 {
                color: #337ab7;
                text-align: center;
            }
            form {
                width: 300px;
                margin: 0 auto;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }
            label {
                display: block;
                margin-bottom: 5px;
            }
            input[type="text"] {
                width: 100%;
                padding: 10px;
                margin-bottom: 10px;
                border: 1px solid #ccc;
                border-radius: 3px;
                box-sizing: border-box;
            }
            input[type="submit"] {
                background-color: #337ab7;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 3px;
                cursor: pointer;
            }
            input[type="submit"]:hover {
                background-color: #21618c;
            }
            p {
                margin-top: 15px;
                text-align: center;
            }
            p.error {
                color: red;
            }
        </style>
    </head>
    <body>
        <h1>Verify OTP</h1>
        <form action="verifyOTP" method="post">
            <label for="otp">Enter OTP:</label>
            <input type="text" id="otp" name="otp" required><br><br>
            <input type="submit" value="Verify">
        </form>
        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
                out.println("<p style='color: red;'>"+error+"</p>");
            }
        %>
    </body>
</html>


