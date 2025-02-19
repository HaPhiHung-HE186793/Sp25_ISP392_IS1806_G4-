<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Forget Password - Get a verification code</title>
        <style>
            /* Thiết kế bố cục tổng thể của trang */
            .container {
                width: 300px;
                margin: 100px auto;
                border: 1px solid #000;
                padding: 20px;
                text-align: center;
                font-family: Arial, sans-serif;
            }

            h2 {
                font-size: 24px;
                margin-bottom: 10px;
            }

            p {
                font-size: 14px;
                margin-bottom: 20px;
                color: #666;
            }

            /* Thiết kế phần hiển thị email */
            .email-display {
                font-weight: bold;
                font-size: 14px;
                color: #333;
            }

            /* Thiết kế nút Send */
            input[type="submit"] {
                padding: 5px 15px;
                background-color: #6c757d;
                color: white;
                border: none;
                cursor: pointer;
                font-size: 14px;
            }

            input[type="submit"]:hover {
                background-color: #6c757d;
            }

        </style>
    </head>
    <body>
        <div class="container">
            <h2>Quên mật khẩu</h2>
            <h3>Lấy mã xác nhận</h3>
            <p>Gửi mã xác nhận đến: <span class="email-display"><%= request.getAttribute("email") != null ? request.getAttribute("email") : ".........." %></span></p>
            
            <!-- Form gửi mã xác nhận -->
            <form action="<%= request.getContextPath() %>/forgotpass" method="POST">
                <input type="hidden" name="email" value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" />
                <input type="hidden" name="step" value="2" />
                <input type="submit" value="Gửi" />
            </form>
        </div>
    </body>
</html>
