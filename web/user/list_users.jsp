<%-- 
    Document   : createUser
    Created on : 11 thg 2, 2025, 23:06:15
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
        <title>List User</title>
    </head>

    <body>
        <div id="main">
            <jsp:include page="/Component/menu.jsp"></jsp:include>   

                <div class="main-content">
                    <div class="notification">
                        Thông báo: Mọi người có thể liên hệ admin tại fanpage <a style="color: #5bc0de; text-decoration: none; transition: 0.3s;" href="https://github.com/HaPhiHung-HE186793/Sp25_ISP392_IS1806_G4-" target="_blank">
                            Group 4 ISP392
                        </a>
                    </div>
                    <div class="table-container">
                        <div style="display: flex; gap: 300px;">
                            <h3>Danh sách người dùng</h3>

                        <c:choose>
                            <c:when test="${not empty error}">
                                <div class="notification2" id="errorNotification" style="color: red;">
                                    ${error}
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="notification2" id="errorNotification" style="display: none;"></div>
                            </c:otherwise>
                        </c:choose>                                
                                <div class="notification2" id="error-message" style="color: red; display: none;"></div>

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
                    <div class="filters">
                        <form method="post" action="listusers">
                            <c:if test="${user_current.getRoleID() == 1}">
                                <label for="role">Chức Năng:</label>
                                <select name="role" id="role" onchange="this.form.submit()">
                                    <option value="-1" hidden ${selectedRole == -1 ? 'selected' : ''}>Tất cả</option>
                                    <option value="-1">Tất cả</option>
                                    <option value="1" ${selectedRole == 1 ? 'selected' : ''}>Admin</option>
                                    <option value="2" ${selectedRole == 2 ? 'selected' : ''}>Chủ cửa hàng</option>
                                    <option value="3" ${selectedRole == 3 ? 'selected' : ''}>Nhân viên bán hàng</option>
                                </select>
                            </c:if>
                            <input type="text" name="keyword" id="keyword" placeholder="Nhập từ khóa..." value="${param.keyword}">
                            <input name="startDate" id="startDate" type="date" value="${startDate}">
                            <input name="endDate" id="endDate" type="date" value="${endDate}">
                            <select name="action" id="action" onchange="this.form.submit()">
                                <option value="-1" hidden ${selectedAction == -1 ? 'selected' : ''}>Tất cả trạng thái</option>
                                <option value="-1">Tất cả trạng thái</option>
                                <option value="0" ${selectedAction == 0 ? 'selected' : ''}>Hoạt động</option>
                                <option value="1" ${selectedAction == 1 ? 'selected' : ''}>Đã khóa</option>                     
                            </select>

                            <button type="submit">Tìm kiếm</button>
                            <button type="reset" onclick="window.location = 'listusers'">Bỏ lọc</button>
                        </form>


                        <div>
                            <button class="addNewDebt" style="padding: 11px !important;" onclick="window.location = 'createuser'">Tạo tài khoản mới</button>
                        </div>

                    </div>

                    <table>
                        <thead id="table-header">
                            <tr>
                                <th>ID</th>
                                <th>Tên</th>
                                <th>Chức năng</th>
                                <th>Email</th>
                                <th>Ngày tạo</th>
                                <th>Trạng thái</th> 
                                <th>Người tạo</th> 
                                <th style="border-left: 1px solid black;">Hành động</th>
                            </tr>
                        </thead>
                        <tbody id="table-tbody">
                            <c:set var="rowCount" value="0" />
                            <c:forEach items="${U}" var="u" begin="${sessionScope.page.getStartItem()}" end="${sessionScope.page.getLastItem()}" varStatus="status">
                                <tr class="no-rows">
                                    <!--<td colspan="8" style="text-align: center;">No rows found</td>-->

                                    <td>${u.getID()}</td>
                                    <td>${u.getUserName()}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${u.getRoleID() == 1}">Admin</c:when>
                                            <c:when test="${u.getRoleID() == 2}">Chủ cửa hàng</c:when>
                                            <c:when test="${u.getRoleID() == 3}">Nhân viên bán hàng</c:when>
                                            <c:otherwise>Không xác định</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${u.getEmail()}</td>                                                                    
                                    <td>
                                        <span>${u.getCreateAt().substring(0, 10)}</span><br>
                                        <span>${u.getCreateAt().substring(11)}</span>
                                    </td>
                                    <td>${u.getIsDelete() ? "Đã khóa" : "Hoạt động"}</td>
                                    <td>
                                        ${u.getCreatorName()}
                                    </td>
                                    <td style="border-left: 1px solid black; display: flex; align-items: center;">
                                        <button onclick="window.location.href = 'listusers?id=${u.getID()}'"  
                                                style="padding: 5px 15px; font-size: 14px; min-width: 80px; background-color: #5bc0de; color: white; border: none; border-radius: 5px; cursor: pointer; margin-right: 10px;">
                                            Cập nhật
                                        </button>


                                        <c:choose>
                                            <c:when test="${u.getIsDelete()}">
                                                <form class="toggleStatusForm" data-action="unlock">
                                                    <input type="hidden" name="userIDBlock" value="${u.getID()}">                                                    
                                                    <button type="button" class="toggleStatusBtn"
                                                            style="padding: 5px 15px; font-size: 14px; min-width: 80px; background-color: green; color: white; border: none; border-radius: 5px; cursor: pointer; margin-right: 10px;">
                                                        Mở khóa
                                                    </button>
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <form class="toggleStatusForm" data-action="block">
                                                    <input type="hidden" name="userIDBlock" value="${u.getID()}">
                                                    <button type="button" class="toggleStatusBtn"
                                                            style="padding: 5px 15px; font-size: 14px; min-width: 80px; background-color: red; color: white; border: none; border-radius: 5px; cursor: pointer;">
                                                        Khóa
                                                    </button>
                                                </form>
                                            </c:otherwise>
                                        </c:choose>


                                    </td>
                                </tr>
                                <c:set var="rowCount" value="${status.count}" />
                            </c:forEach>   
                            <!-- Bổ sung các hàng trống nếu danh sách có ít hơn 9 người dùng -->
                            <c:forEach begin="1" end="${9 - rowCount}">
                                <tr>
                                    <td colspan="8" style="height: 40px;"></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>    
                    <div style="margin-left: -220px;" >

                    </div>
                </div>
            </div>
        </div>
        <%@include file="/Component/pagination.jsp" %>
        <%@include file="/Component/footer.jsp" %>


    </body>


    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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

                                            //ajax submit block, unlock
                                            $(document).ready(function () {
                                                $(".toggleStatusBtn").click(function () {
                                                    var form = $(this).closest(".toggleStatusForm");
                                                    var userIDBlock = form.find("input[name='userIDBlock']").val();
                                                    var actionBlock = form.data("action"); // "block" hoặc "unlock"

                                                    $.ajax({
                                                        type: "POST",
                                                        url: "listusers",
                                                        data: {actionBlock: actionBlock, userIDBlock: userIDBlock},
                                                        success: function () {
                                                            location.reload(); // Reload để cập nhật trạng thái
                                                        },
                                                        error: function () {
                                                            $("#error-message").text("Không được phép khóa tài khoản admin khác.").show();
                                                        }
                                                    });
                                                });
                                            });


    </script>

</html>
