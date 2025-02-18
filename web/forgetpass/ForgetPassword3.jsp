<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Forget Password - Enter the code</title>
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

            /* Thiết kế input mã code */
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
            <h2>Quên mật khẩu</h2>
            <p>Nhập mã xác nhận</p>
            <form action="<%= request.getContextPath() %>/forgotpass" method="POST">
                <input type="text" name="otp" placeholder="Nhập mã xác nhận" required />
                <br /><br />
                <input type="hidden" name="step" value="3" />
                <input type="submit" value="Tiếp theo" />

                <!-- Resend OTP button -->
                <button type="button" onclick="resendOTP()">Gửi lại OTP</button>
                <!-- Hiển thị thông báo lỗi nếu có -->
                <span class="error-message">
                    <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %>
                </span>
            </form>
            <script>
                // Resend OTP function
                function resendOTP() {
                    const form = document.createElement("form");
                    form.method = "POST";
                    form.action = "<%= request.getContextPath() %>/forgotpass";
                    
                    // Tạo input để gửi lại bước "resend"
                    const stepInput = document.createElement("input");
                    stepInput.type = "hidden";
                    stepInput.name = "step";
                    stepInput.value = "resend";
                    form.appendChild(stepInput);

                    // Lấy email từ session để gửi lại mã OTP
                    const emailInput = document.createElement("input");
                    emailInput.type = "hidden";
                    emailInput.name = "email";
                    emailInput.value = "<%= request.getAttribute("email") %>";
                    form.appendChild(emailInput);

                    document.body.appendChild(form);
                    form.submit();
                }
            </script>
        </div>
    </body>
</html>
