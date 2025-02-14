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
                            <label for="role">Role:</label>
                            <select name="role" id="role" onchange="this.form.submit()">
                                <option value="">All</option>
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
                                <th>User Name</th>
                                <th>Role</th>
                                <th>Email</th>
                                <th>Image</th>
                                <th>Date Created</th>
                                <th>Creator</th>                           
                            </tr>
                        </thead>
                        <tbody id="table-tbody">
                            <c:forEach items="${U}" var="u">
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
                                    <td><img class="mt-2" style="border: solid #f5f2f2; border-radius: 20px" src="${u.getImage()}" width="200" height="200" alt="alt"/></td>
                                    <td>${u.getCreateAt()}</td>
                                    <td><c:forEach var="creator" items="${U}">
                                            <c:if test="${creator.getID() == u.getCreateBy()}">
                                                ${creator.getUserName()}
                                            </c:if>
                                        </c:forEach></td>
                                </tr>
                            </c:forEach>   
                        </tbody>
                    </table>                   
                </div>
            </div>
        </div>
        <%@include file="Component/pagination.jsp" %>

    </body>

    <div class="newDebt">
        <div class="newDebt-container">
            <button class="newDebt-add">
                Thêm mới 
            </button>
            <button class="newDebt-close js-close-newDebt">
                close
            </button>
            <div class="newDebt-header">Thông tin người nợ</div>
            <div class="newDebt-body">


                <table>
                    <thead id="newDebt-tableHeader">
                    </thead>
                    <tbody class="newDebt-tableTbody">                            
                        <tr class="newDebt-tableTbody-tr">
                            <td ><div class="newDebt-text"> Họ và tên (*):</div></td>
                            <td ><input class="newDebt-input" type="text" placeholder="Nguyen Van A"> </td>                                    
                        </tr>                            
                        <tr class="newDebt-tableTbody-tr">
                            <td ><div class="newDebt-text"> Địa chỉ:</div></td>
                            <td ><textarea class="newDebt-input" rows="5" cols="10" name="feedback"></textarea><br></td>                                    
                        </tr>                                   
                        <tr class="newDebt-tableTbody-tr">
                            <td ><div class="newDebt-text"> SĐT:</div></td>
                            <td ><input class="newDebt-input" type="number" > </td>                                    
                        </tr>                                   
                        <tr class="newDebt-tableTbody-tr">
                            <td ><div class="newDebt-text"> Email:</div></td>
                            <td ><input class="newDebt-input" type="text" > </td>                                    
                        </tr>                                   
                        <tr class="newDebt-tableTbody-tr">
                            <td ><div class="newDebt-text"> Tổng nợ:</div></td>
                            <td ><input class="newDebt-input newDebt-total" type="number" placeholder="0" readonly> </td>                                    
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
