<%-- 
    Document   : createUser
    Created on : 12 thg 2, 2025, 13:23:18
    Author     : nguyenanh
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./assets/css/style.css">
        <link rel="stylesheet" href="./assets/fonts/themify-icons/themify-icons.css">
        <title>Create User</title>
    </head>

    <body>

        <div id="main">
            <jsp:include page="/Component/menu.jsp"></jsp:include>

                <div class="main-content">
                    <div class="notification">
                        Thông báo: Mọi người có thể liên hệ admin tại fanpage Group 4
                    </div>

                    <div class="table-container" >
                        <div style="display: flex">
                            <h3 style="max-width: 29%;">Tạo tài khoản</h3>

                        <c:choose>
                            <c:when test="${not empty errors}">
                                <div class="notification2" id="errorNotification" style="color: red; max-width: 29%;margin-left: 25%;padding: 0px 5px;">
                                    <c:forEach var="error" items="${errors}">
                                        <p>${error}</p>
                                    </c:forEach>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="notification2" id="errorNotification" style="display: none; max-width: 29%;"></div>
                            </c:otherwise>
                        </c:choose> 
                        <c:choose>
                            <c:when test="${not empty success}">
                                <div class="notification2" id="messageNotification" style="color: green; max-width: 29%;margin-left: 25%;">
                                    ${success}
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="notification2" id="messageNotification" style="display: none; max-width: 29%;"></div>
                            </c:otherwise>
                        </c:choose>
                        <p id="passError" style="color: red; font-size: 14px; display: none; max-width: 29%;margin-left: 40%;">
                            Mật khẩu xác nhận không khớp!
                        </p>
                    </div>


                    <table>
                        <thead id="table-header">
                            <tr>
                                <th>Tên</th>                               
                                <th>Email</th>
                                <th>Mật Khẩu</th>
                                <th>Chức năng</th>
                                <th style="border-left: 1px solid black;">Hành động</th>
                            </tr>
                        </thead>
                        <tbody id="table-tbody">
                            <tr>
                        <form id="createUserForm" action="createuser" method="POST" enctype="multipart/form-data">
                            <td>
                                <input name="userName" id="userName" type="text" 
                                       placeholder="Tên của bạn" value="${userName}" required>
                            </td>
                            <td>
                                <input name="email" id="email" type="email" 
                                       placeholder="Email của bạn" value="${emaill}" required>
                            </td> 
                            <td>
                                <input name="password" id="password" type="password" 
                                       placeholder="Nhập mật khẩu" required>
                            </td>
                            <td>
                                <c:if test="${u.getRoleID() == 1}">
                                    <div>
                                        <input type="radio" name="roleID" value="1" id="admin" required>
                                        <label for="admin">Admin</label>
                                    </div>
                                    <div>
                                        <input type="radio" name="roleID" value="2" id="store_owner">
                                        <label for="store_owner">Chủ Cửa Hàng</label>
                                    </div>
                                </c:if>
                                <c:if test="${u.getRoleID() == 2}">
                                    <div>
                                        <input type="radio" name="roleID" value="3" id="employee">
                                        <label for="employee">Nhân Viên</label>
                                    </div>
                                </c:if>
                            </td>
                            <td style="border-left: 1px solid black;">     
                                <button type="button" onclick="sendOTP()">Tạo tài khoản</button>
                                <button hidden type="submit" class="btn btn-primary"></button>
                            </td>
                        </form>
                        </tr>
                        </tbody>
                    </table>                   
                </div>
                <button onclick="window.location.href = 'listusers'"  
                        style="padding: 5px 15px; font-size: 14px; min-width: 80px; background-color: green; color: white; border: none; border-radius: 5px; cursor: pointer; margin-right: 10px;">
                    Quay về danh sách người dùng
                </button>
            </div>                                
        </div>
        <!-- Thêm modal nhập OTP -->
        <div id="otpModal" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); justify-content: center; align-items: center;">
            <div style="background: white; padding: 20px; border-radius: 10px; text-align: center;">
                <h3 style="color: black">Xác nhận Email</h3>
                <p style="color: black">Nhập mã OTP đã gửi đến email của bạn</p>
                <input type="text" id="otpInput" placeholder="Nhập mã OTP" style="margin-bottom: 15px;">
                <p id="otpError" style="color: red;  display: none; ">Mã OTP không chính xác</p>
                <br>
                <button onclick="verifyOTP()">Xác nhận</button>
                <button onclick="closeOtpModal()">Hủy</button>
            </div>
        </div>
    </body>
    <script>
        function sendOTP() {
            let email = document.getElementById("email").value;
            if (email === "") {
                alert("Vui lòng nhập email trước khi gửi OTP!");
                return;
            }

            fetch("sendotp", {
                method: "POST",
                headers: {"Content-Type": "application/x-www-form-urlencoded"},
                body: "email=" + encodeURIComponent(email)
            })
                    .then(response => response.json())
                    .then(data => {
                        if (data.success) {                            
                            document.getElementById("otpModal").style.display = "flex"; // Chỉ hiển thị modal nếu thành công
                        } else {
                            alert("Lỗi: " + data.message); // Hiển thị lỗi nếu có
                        }
                    })
                    .catch(error => console.error("Lỗi gửi OTP:", error));
        }


        function verifyOTP() {
            let userOTP = document.getElementById("otpInput").value;

            fetch("verifyotp", {
                method: "POST",
                headers: {"Content-Type": "application/x-www-form-urlencoded"},
                body: "otp=" + encodeURIComponent(userOTP)
            })
                    .then(response => response.text())
                    .then(result => {
                        if (result === "OTP verified") {
                            closeOtpModal();
                            document.getElementById("createUserForm").submit();
                        } else {
                            document.getElementById("otpError").style.display = "block"; // Hiển thị lỗi
                            setTimeout(() => document.getElementById("otpError").style.display = "none", 3000);
                        }
                    })
                    .catch(error => console.error("Lỗi xác thực OTP:", error));
        }

        function closeOtpModal() {
            document.getElementById("otpModal").style.display = "none";
        }




        // Hàm ẩn thông báo sau 3 giây
        function hideNotification(notificationId) {
            setTimeout(function () {
                var notification = document.getElementById(notificationId);
                if (notification) {
                    notification.style.display = 'none';
                }
            }, 3000); // 3000 milliseconds = 3 seconds
        }

        hideNotification('errorNotification');
        hideNotification('messageNotification');



        document.addEventListener("DOMContentLoaded", function () {
            const password = document.getElementById("password");
            const cfpass = document.getElementById("cfpass");
            const passError = document.getElementById("passError");
            const form = document.querySelector("createUserForm");

            function validatePassword() {
                if (password.value !== cfpass.value) {
                    passError.style.display = "block"; // Hiện lỗi
                } else {
                    passError.style.display = "none"; // Ẩn lỗi
                }
            }

            // Kiểm tra mật khẩu ngay khi nhập
            password.addEventListener("input", validatePassword);
            cfpass.addEventListener("input", validatePassword);

            form.addEventListener("submit", function (event) {
                if (password.value !== cfpass.value) {
                    event.preventDefault(); // Chặn gửi form
                    alert("Mật khẩu xác nhận không khớp! Vui lòng nhập lại.");
                    cfpass.focus();
                }
            });
        });

    </script>
</html>
