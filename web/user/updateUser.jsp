<%-- 
    Document   : updateUser
    Created on : 17 thg 2, 2025, 22:04:14
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
        <title>Update User</title>
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
                            <h3 style="max-width: 29%;">Cập nhật tài khoản</h3>

                        <c:choose>
                            <c:when test="${not empty errors}">
                                <div class="notification" id="errorNotification" style="color: red; max-width: 29%;margin-left: 25%;padding: 0px 5px;">
                                    <c:forEach var="error" items="${errors}">
                                        <p>${error}</p>
                                    </c:forEach>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="notification" id="errorNotification" style="display: none; max-width: 29%;"></div>
                            </c:otherwise>
                        </c:choose> 
                        <c:choose>
                            <c:when test="${not empty success}">
                                <div class="notification" id="messageNotification" style="color: green; max-width: 29%;margin-left: 25%;">
                                    ${success}
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="notification" id="messageNotification" style="display: none; max-width: 29%;"></div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <table>
                        <thead id="table-header">
                            <tr>
                                <th>Tên</th>                               
                                <th>Email</th>
                                <th>Mật khẩu mới</th>
                                <th>Xác minh mật khẩu</th>
                                <th>Chức năng</th>
                                <th style="border-left: 1px solid black;">Hành động</th>
                            </tr>
                        </thead>
                        <tbody id="table-tbody">
                            <tr>
                        <form action="updateuser" method="POST">
                            <td>
                                <input name="userName" id="userName" type="text" 
                                       placeholder="Tên của bạn" value="${user_update.getUserName()}">
                                <input hidden name="userid" value="${user_update.getID()}">
                            </td>
                            <td>
                                <input name="email" id="email" type="email" 
                                       placeholder="Email của bạn" value="${user_update.getEmail()}">
                            </td> 
                            <td>
                                <input name="password" id="password" type="password" 
                                       placeholder="Mật khẩu mới" >
                            </td>
                            <td>
                                <input name="cfpass" id="cfpass" type="password" 
                                       placeholder="Xác nhận mật khẩu" >
                                <p id="passError" style="color: red; font-size: 14px; display: none;">
                                    Mật khẩu xác nhận không khớp!
                                </p>
                            </td>
                            <td>
                                <c:if test="${u.getRoleID() == 1}">
                                    <div>
                                        <input type="radio" name="roleID" value="1" id="admin" 
                                               ${user_update.getRoleID() == 1 ? "checked" : ""} required>
                                        <label for="admin">Admin</label>
                                    </div>
                                    <div>
                                        <input type="radio" name="roleID" value="2" id="store_owner" 
                                               ${user_update.getRoleID() == 2 ? "checked" : ""}>
                                        <label for="store_owner">Chủ Cửa Hàng</label>
                                    </div>
                                </c:if>
                                <c:if test="${u.getRoleID() == 2}">
                                    <div>
                                        <input type="radio" name="roleID" value="3" id="employee" 
                                               ${user_update.getRoleID() == 3 ? "checked" : ""}>
                                        <label for="employee">Nhân Viên</label>
                                    </div>
                                </c:if>
                            </td>
                            <td style="border-left: 1px solid black;">                                    
                                <button type="submit" class="btn btn-primary">Cập nhật người dùng</button>
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


        document.addEventListener("DOMContentLoaded", function () {
            const password = document.getElementById("password");
            const cfpass = document.getElementById("cfpass");
            const passError = document.getElementById("passError");
            const form = document.querySelector("form");

            function validatePassword() {
                if (password.value !== cfpass.value) {
                    passError.style.display = "block"; // Hiện lỗi
                } else {
                    passError.style.display = "none"; // Ẩn lỗi
                }
            }

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