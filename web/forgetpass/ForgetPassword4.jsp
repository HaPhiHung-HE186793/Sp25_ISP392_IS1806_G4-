<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Change Password</title>
        <style>
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

            /* Thiết kế input mật khẩu */
            input[type="password"] {
                width: 80%;
                padding: 5px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                font-size: 14px;
            }

            /* Thiết kế nút Submit */
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

            /* Thiết kế thông báo lỗi */
            .error-message {
                color: red;
                font-size: 12px;
                margin-top: 10px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Thay đổi mật khẩu</h2>
            <form action="<%= request.getContextPath() %>/forgotpass" method="POST">
                <table border="0">
                    <tr>
                        <td>Mật khẩu mới:</td>
                        <td><input type="password" name="password" value="" required/></td>
                    </tr>
                    <tr>
                        <td>Nhập lại mật khẩu:</td>
                        <td><input type="password" name="passwordConfirm" value="" required/></td>
                    </tr>
                </table>
                <input type="hidden" name="step" value="4" />
                <input type="submit" value="Gửi" />

                <!-- Hiển thị thông báo lỗi nếu mật khẩu không khớp -->
                <span class="error-message">
                    <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %>
                </span>
            </form>
        </div>
    </body>
</html>
