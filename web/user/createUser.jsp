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

                    <div class="table-container">
                        <h3>Tạo tài khoản mới</h3>

                    <c:choose>
                        <c:when test="${not empty errors}">
                            <div class="notification" id="errorNotification" style="color: red;">
                                <c:forEach var="error" items="${errors}">
                                    <p>${error}</p>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="notification" id="errorNotification" style="display: none;"></div>
                        </c:otherwise>
                    </c:choose> 
                    <c:choose>
                        <c:when test="${not empty success}">
                            <div class="notification" id="messageNotification" style="color: green;">
                                ${success}
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="notification" id="messageNotification" style="display: none;"></div>
                        </c:otherwise>
                    </c:choose>


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
                        <form action="createuser" method="POST" enctype="multipart/form-data">
                            <td>
                                <input name="userName" id="userName" type="text" 
                                       placeholder="Tên của bạn" value="${userName}">
                            </td>
                            <td>
                                <input name="email" id="email" type="email" 
                                       placeholder="Email của bạn" value="${email}">
                            </td> 
                            <td>
                                <input name="password" id="password" type="password" 
                                       placeholder="Nhập mật khẩu" required>
                            </td>
                            <td>
                                <div>
                                    <input type="radio" name="roleID" value="1" id="admin" required>
                                    <label for="admin">Admin</label>
                                </div>
                                <div>
                                    <input type="radio" name="roleID" value="2" id="store_owner">
                                    <label for="store_owner">Chủ Cửa Hàng</label>
                                </div>
                                <div>
                                    <input type="radio" name="roleID" value="3" id="employee">
                                    <label for="employee">Nhân Viên</label>
                                </div>
                            </td>
                            <td style="border-left: 1px solid black;">                                    
                                <button type="submit" class="btn btn-primary">Tạo tài khoản</button>
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

    </script>
</html>
