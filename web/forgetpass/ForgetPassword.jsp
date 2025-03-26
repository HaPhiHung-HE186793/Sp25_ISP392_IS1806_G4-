<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Forgot Password</title>
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

            /* Thiết kế phần input email */
            input[type="text"] {
                width: 80%;
                padding: 5px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                font-size: 14px;
                text-align: left;
            }

            /* Thiết kế nút Next */
            input[type="submit"] {
                padding: 5px 15px;
                background-color: #6c757d;
                color: white;
                border: none;
                cursor: pointer;
                font-size: 14px;
                margin-right: 10px; /* Khoảng cách giữa các nút */
            }

            input[type="submit"]:hover {
                background-color: #5a6268; /* Màu tối hơn khi hover */
            }

            /* Thiết kế nút Quay lại */
            .back-button {
                padding: 5px 15px;
                background-color: orange; /* Màu cam */
                color: white; /* Màu chữ */
                border: none; /* Không viền */
                cursor: pointer; /* Con trỏ chuột khi hover */
                font-size: 14px;
            }

            .back-button:hover {
                background-color: darkorange; /* Màu tối hơn khi hover */
            }

            /* Thiết kế thông báo lỗi */
            .error-message {
                color: red;
                font-size: 12px;
                margin-top: 10px;
            }

            /* Thiết kế hàng chứa nút */
            .button-container {
                margin-top: 15px; /* Khoảng cách phía trên */
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Quên mật khẩu</h2>
            <p>Hãy nhập email mà bạn đã đăng ký</p>
            <form action="<%= request.getContextPath() %>/forgotpass" method="POST">
                <input type="text" name="email" placeholder="Nhập email" value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" required />
                <br />
                <input type="hidden" name="step" value="1" />
                
                <!-- Hàng chứa nút -->
                <div class="button-container">
                    <input type="submit" value="Tiếp theo" />
                    <input type="button" class="back-button" value="Quay lại" onclick="window.location.href='<%= request.getContextPath() %>/login/login.jsp'" />
                </div>

                <!-- Hiển thị thông báo lỗi nếu có -->
                <span class="error-message">
                    <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %>
                </span>
            </form>
        </div>
    </body>
</html>