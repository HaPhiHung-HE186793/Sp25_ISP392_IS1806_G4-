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
                            <h3>Danh sách cửa hàng </h3>

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
                            <input type="text" name="keyword" id="keyword" placeholder="Nhập từ khóa..." value="${param.keyword}">
                            <input name="startDateCreate" id="startDateCreate" type="date" value="${startDateCreate}">
                            <input name="startDateCreate" id="startDateCreate" type="date" value="${startDateCreate}">
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
                            <button class="addNewDebt" style="padding: 11px !important;" onclick="window.location = 'createstore'">Tạo cửa hàng mới</button>
                        </div>

                    </div>

                    <table>
                        <thead id="table-header">
                            <tr>
                                <th>Logo</th>
                                <th>Tên</th>
                                <th>Chủ của hàng</th>
                                <th>Email</th>
                                <th>Địa chỉ</th>
                                <th>Ngày tạo</th>
                                <th>Trạng thái</th> 
                                <th>Người tạo</th> 
                                <th style="border-left: 1px solid black;">Hành động</th>
                            </tr>
                        </thead>
                        <tbody id="table-tbody">
                            <c:set var="rowCount" value="0" />
                            <c:forEach items="${Store}" var="s" begin="${sessionScope.page.getStartItem()}" end="${sessionScope.page.getLastItem()}" varStatus="status">
                                <tr class="no-rows">
                                    <!--<td colspan="8" style="text-align: center;">No rows found</td>-->

                                    <td>${s.getLogostore()}</td>
                                    <td>${s.getStoreName()}</td>
                                    <td>${s.getOwnerName()}</td>                                 
                                    <td>${s.getEmail()}</td>     
                                    <td>${s.getAddress()}</td> 
                                    <td>
                                        <span>${s.getCreateAt().substring(0, 10)}</span><br>
                                        <span>${s.getCreateAt().substring(11)}</span>
                                    </td>
                                    <td>${s.getIsDelete() ? "Đã khóa" : "Hoạt động"}</td>
                                    <td>
                                        ${s.getCreateName()}
                                    </td>
                                    <td style="border-left: 1px solid black; display: flex; align-items: center;">
                                        <button onclick="window.location.href = 'liststore?id=${s.getStoreID()}'"  
                                                style="padding: 5px 15px; font-size: 14px; min-width: 80px; background-color: #5bc0de; color: white; border: none; border-radius: 5px; cursor: pointer; margin-right: 10px;">
                                            Cập nhật
                                        </button>


                                        <c:choose>
                                            <c:when test="${s.getIsDelete()}">
                                                <form class="toggleStatusForm" data-action="unlock">
                                                    <input type="hidden" name="storeIDBlock" value="${u.getID()}">                                                    
                                                    <button type="button" class="toggleStatusBtn"
                                                            style="padding: 5px 15px; font-size: 14px; min-width: 80px; background-color: green; color: white; border: none; border-radius: 5px; cursor: pointer; margin-right: 10px;">
                                                        Mở khóa
                                                    </button>
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <form class="toggleStatusForm" data-action="block">
                                                    <input type="hidden" name="storeIDBlock" value="${s.getStoreID()}">
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
                                                    var userIDBlock = form.find("input[name='storeIDBlock']").val();
                                                    var actionBlock = form.data("action"); // "block" hoặc "unlock"

                                                    $.ajax({
                                                        type: "POST",
                                                        url: "liststore",
                                                        data: {actionBlock: actionBlock, userIDBlock: userIDBlock},
                                                        success: function () {
                                                            history.replaceState(null, "", "liststore"); // Xóa id khỏi URL
                                                            location.reload(); // Reload để cập nhật trạng thái
                                                        },
                                                        error: function () {
                                                            $("#error-message").text("Không được phép khóa tài khoản admin khác.").show();
                                                            hideNotification('error-message');
                                                        }
                                                    });
                                                });
                                            });


    </script>

</html>
