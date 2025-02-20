<%-- 
    Document   : changePassword
    Created on : 21 thg 2, 2025, 00:50:52
    Author     : nguyenanh
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.User" %>
<%@ page import="DAO.DAOUser" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./assets/css/style.css">
        <link rel="stylesheet" href="./assets/fonts/themify-icons/themify-icons.css">
        <title>Change Password</title>
        <style>
            .profile-container {
                max-width: 500px;
                margin: 30px auto;
                padding: 20px;
                background: #fff;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            .profile-container h2 {
                text-align: center;
                margin-bottom: 20px;
                font-size: 24px;
                color: #333;
                display: block; /* Đảm bảo thẻ h2 không bị ẩn */
            }

            .profile-container form {
                display: flex;
                flex-direction: column;
                align-items: center;
            }

            .profile-container label {
                display: block;
                margin: 10px 0 5px;
                font-weight: bold;
            }

            .profile-container input {
                width: 60%; /* Thu nhỏ chiều ngang */
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 16px;
            }

            .profile-container button {
                width: 60%; /* Nút cũng thu nhỏ theo form */
                padding: 10px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 5px;
                font-size: 16px;
                cursor: pointer;
                margin-top: 10px;
            }

            .profile-container button:hover {
                background-color: #0056b3;
            }

            .profile-buttons {
                display: flex;
                justify-content: center; /* Căn giữa ngang */
                gap: 70px; /* Tạo khoảng cách giữa hai nút */
                margin-top: 10px;
            }

            .profile-buttons button {
                width: 180px; /* Đặt kích thước nút đồng đều */
                padding: 10px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 5px;
                font-size: 16px;
                cursor: pointer;
            }

            .profile-buttons button:hover {
                background-color: #0056b3;
            }

            .profile-container label {
                display: block !important;
                margin-bottom: 0.5rem !important;
                font-size: 16px !important;
                line-height: 1.5 !important;
                padding-top: 0 !important;
                padding-bottom: 0 !important;
                color: #333;
            }


        </style>

    </head>
    <body>
        <div id="main">
            <jsp:include page="/Component/menu.jsp"></jsp:include>
                <div class="main-content">
                    <div class="notification">
                        Thông báo: Mọi người có thể liên hệ admin tại fanpage Group 4
                    </div>
                    <div class="profile-container">
                        <h2>Thay đổi mật khẩu</h2>
                        <form action="changepassword" method="post">                        
                            <label for="password">Mật khẩu cũ </label>
                            <input type="password" id="password" name="password" value="${password}" required>                                                
                            <label for="newpassword">Mật khẩu mới </label>
                            <input type="password" id="newpassword" name="newpassword" value="${newpassword}" required>
                            <label for="cfnewpass">Xác nhận mật khẩu mới </label>
                            <input type="password" id="cfnewpass" name="cfnewpass" value="${cfnewpass}" required>
                        <div class="profile-buttons">                            
                            <button type="button" onclick="location.href = 'updateprofile'">Cập nhật người dùng</button>
                            <button type="submit">Xác nhận thay đổi</button>
                        </div>
                    </form>                    
                </div>
                <div>
                    <c:choose>
                            <c:when test="${not empty errors}">
                                <div class="notification2" id="errorNotification" style="color: red;">
                                    <c:forEach var="error" items="${errors}">
                                        <p>${error}</p>
                                    </c:forEach>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="notification2" id="errorNotification" style="display: none;"></div>
                            </c:otherwise>
                        </c:choose> 
                    <c:choose>
                        <c:when test="${not empty mess}">
                            <div class="notification2" id="messageNotification" style="color: green;">
                                ${mess}
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="notification2" id="messageNotification" style="display: none;"></div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </body>
    <script>

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


    </script>
</html>