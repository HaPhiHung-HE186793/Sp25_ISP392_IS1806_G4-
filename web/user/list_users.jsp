<%-- 
    Document   : createUser
    Created on : 11 thg 2, 2025, 23:06:15
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
                        <h3>Danh sách người dùng</h3>
                        <div class="filters">
                            <form method="post" action="listusers">
                                <label for="role">Chức Năng:</label>
                                <select name="role" id="role" onchange="this.form.submit()">
                                    <option value="">Tất cả</option>
                                    <option value="1" ${selectedRole == 1 ? 'selected' : ''}>Admin</option>
                                <option value="2" ${selectedRole == 2 ? 'selected' : ''}>Chủ cửa hàng</option>
                                <option value="3" ${selectedRole == 3 ? 'selected' : ''}>Nhân viên bán hàng</option>
                            </select>
                            <input type="text" name="keyword" placeholder="Nhập từ khóa..." value="${param.keyword}">

                            <button type="submit">Tìm kiếm</button>
                            <button type="reset" onclick="window.location = 'listusers'">Bỏ lọc</button>
                        </form>


                        <button class="addNewDebt js-open-newDebt">Tạo tài khoản mới</button>

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
                            <c:forEach items="${U}" var="u" begin="${sessionScope.page.getStartItem()}" end="${sessionScope.page.getLastItem()}" >
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
                                    <td><c:forEach var="creator" items="${U}">
                                            <c:if test="${creator.getID() == u.getCreateBy()}">
                                                ${creator.getUserName()}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td style="border-left: 1px solid black; display: flex; align-items: center;">
                                        <button onclick="updateUser(${u.getID()})" 
                                                style="padding: 5px 15px; font-size: 14px; min-width: 80px; background-color: #5bc0de; color: white; border: none; border-radius: 5px; cursor: pointer; margin-right: 10px;">
                                            Update
                                        </button>
                                        <c:choose>
                                            <c:when test="${u.getIsDelete()}">
                                                <button onclick="toggleUserStatus(${u.getID()}, false)" 
                                                        style="padding: 5px 15px; font-size: 14px; min-width: 80px; background-color: green; color: white; border: none; border-radius: 5px; cursor: pointer; margin-right: 10px;">
                                                    Mở khóa
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                <button onclick="toggleUserStatus(${u.getID()}, true)" 
                                                        style="padding: 5px 15px; font-size: 14px; min-width: 80px; background-color: red; color: white; border: none; border-radius: 5px; cursor: pointer;">
                                                    Khóa
                                                </button>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>


                                </tr>
                            </c:forEach>   
                        </tbody>
                    </table>                   
                </div>
            </div>
        </div>


        <%@include file="/Component/pagination.jsp" %>

    </body>

    <div class="newDebt">
        <div class="newDebt-container">
            <button class="newDebt-add">
                Thêm mới 
            </button>
            <button class="newDebt-close js-close-newDebt">
                close
            </button>
            <div class="newDebt-header">Thông tin người dùng</div>
            <div class="newDebt-body">


                <table>
                    <thead id="newDebt-tableHeader">
                    </thead>
                    <tbody class="newDebt-tableTbody">                            
                        <tr class="newDebt-tableTbody-tr">
                            <td ><div class="newDebt-text"> Họ và tên:</div></td>
                            <td ><input class="newDebt-input" type="text" placeholder="Nguyen Van A"> </td>                                    
                        </tr>                            
                        <tr class="newDebt-tableTbody-tr">
                            <td ><div class="newDebt-text"> Mật khẩu:</div></td>
                            <td ><input name="password" id="password" type="password" 
                                        class="newDebt-input" placeholder="Nhập mật khẩu" required=""><br></td>                                    
                        </tr>       
                        <tr class="newDebt-tableTbody-tr">
                            <td ><div class="newDebt-text"> Email:</div></td>
                            <td ><input name="email" id="email" type="email" class="newDebt-input" placeholder="@example.com" value="${email}"></td>                                    
                        </tr> 
                        <tr class="newDebt-tableTbody-tr">
                            <td ><div class="newDebt-text"> Chức năng:</div></td>
                            <td >
                                <div class="form-check">
                                    <input class="newDebt-input" type="radio" name="roleID" value="1" id="admin" required>
                                    Admin
                                </div>
                                <div class="form-check">
                                    <input class="newDebt-input" type="radio" name="roleID" value="2" id="store_owner">
                                    Chủ Cửa Hàng
                                </div>
                                <div class="form-check">
                                    <input class="newDebt-input" type="radio" name="roleID" value="3" id="employee">
                                    Nhân Viên
                                </div>
                            </td>                                    
                        </tr>                                   

                    </tbody>
                </table>

            </div>
        </div>
    </div>

    <script>
        // Lấy tbody

        const openAddNewDebt = document.querySelector('.js-open-newDebt');//dung de lay ten class//
        const newDebt = document.querySelector('.newDebt');
        const closeNewDebt = document.querySelector('.js-close-newDebt');
        //ham hien thi//
        function showAddNewDebt() {
            newDebt.classList.add('open');
        }
        //ham an//
        function hideAddNewDebt() {
            newDebt.classList.remove('open');
            const inputs = newDebt.querySelectorAll('input,textarea');
            inputs.forEach(input => input.value = '');
        }


        openAddNewDebt.addEventListener('click', showAddNewDebt);


        closeNewDebt.addEventListener('click', hideAddNewDebt);




    </script>

</html>
