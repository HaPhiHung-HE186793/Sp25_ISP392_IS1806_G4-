<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Staff Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-image: url('<%=request.getContextPath()%>/image2/anhlogoRice.jpg'); /* Đường dẫn đến hình ảnh */
            
            background-size: cover; /* Đảm bảo hình ảnh phủ kín */
            background-position: center; /* Căn giữa hình ảnh */
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.1);
        }

        div {
            background-color: rgba(255, 255, 255, 0.9); /* Nền trắng với độ trong suốt */
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            width: 400px; /* Increased width */
            text-align: center;
        }

        h2 {
            color: #333;
            margin-bottom: 20px;
        }

        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            border: 1px solid #ced4da;
            border-radius: 4px;
            box-sizing: border-box;
            transition: border-color 0.3s;
        }

        input[type="email"]:focus,
        input[type="password"]:focus {
            border-color: #80bdff;
            outline: none;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
        }

        input[type="submit"],
        .forgot-password {
            background-color: #ff8c00; /* Orange */
            color: white;
            border: none;
            padding: 12px;
            border-radius: 4px;
            cursor: pointer;
            width: 100%; /* Full width */
            margin-top: 10px; /* Space above buttons */
            transition: background-color 0.3s;
        }

        .forgot-password {
            background-color: #6c757d; /* Gray */
        }

        input[type="submit"]:hover {
            background-color: #e07b00; /* Darker orange on hover */
        }

        .forgot-password:hover {
            background-color: #5a6268; /* Darker gray on hover */
        }

        .message {
            color: red;
            margin: 10px 0;
            font-size: 14px;
        }

        @media (max-width: 400px) {
            div {
                width: 90%; /* Responsive width */
            }
        }
    </style>
</head>
<body>
    <div>
        <h2>Login</h2>
        <%
            String message = (String) request.getAttribute("message");
            if (message != null) {
        %>
        <p class="message"><%= message %></p>
        <% } %>
        <form action="<%=request.getContextPath()%>/loginURL?loginUser" method="POST">            
            <input type="email" name="user" placeholder="Email" required>
            <input type="password" name="pass" placeholder="Mật khẩu" required>
            <input type="submit" name="submit" value="Đăng nhập">
            <input type="button" class="forgot-password" value="Quên mật khẩu?" onclick="window.location.href='<%= request.getContextPath() %>/forgetpass/ForgetPassword.jsp'">
            <input type="hidden" name="service" value="loginUser">
        </form>
    </div>
</body>
</html>