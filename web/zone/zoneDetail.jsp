<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
        <link rel="stylesheet" href="./assets/fonts/themify-icons/themify-icons.css">
        <title>Bảng Điều Khiển</title>


    </head>

    <body>
        <div id="main">

               <jsp:include page="/Component/header.jsp"></jsp:include>
            <div class="menu ">  <jsp:include page="/Component/menu.jsp"></jsp:include> </div>

                <div class="main-content">
                    <div class="notification">
                        Thông báo: Mọi người có thể liên hệ admin tại fanpage Group 4
                    </div>


                    <div class="table-container">
                        <div class="table-header">
                            <h3>Khách hàng</h3>
                        <c:if test="${message == 'success'}">
                            <div id="successMessage" class="newDebt-notification">Thêm khách hàng thành công!</div>
                        </c:if>

                        <c:if test="${message == 'error'}">
                            <div id="errorMessage" class="newDebt-notificationError">Thêm khách hàng thất bại.</div>
                        </c:if>
                    </div>
                    <div class="filters">
                        <!--                        <select>
                                                    <option value="">Trạng thái</option>
                                                    <option value="">A->Z</option>
                                                    <option value="">Z->A</option>
                        
                                                </select>-->
                        <form action="ListCustomer" method="post" >

                        <input name="name" type="text" placeholder="Tìm kiếm" value="${searchName}">
                        <button style="background-color: #5bc0de;" >Lọc</button>
                        </form>
                        <button onclick="window.location.href = '<%=request.getContextPath()%>/ListCustomer'">Bỏ lọc</button> 


                        <button class="addNewDebt js-open-newDebt">Thêm kho</button>

                    </div>

                    <table>
                        <thead id="table-header">
                            <tr>
                                <th>ID</th>
                                <th>Tên</th>
                                <th>Ngày tạo</th>
                                <th>Ngày cập nhật</th>
                                <th>Người tạo</th>
                                <th>ID cửa hàng</th>
                                <th>Chức năng</th>

                            </tr>
                        </thead>
                        <tbody id="table-tbody">
                             <c:forEach items="${listZone}" var="o" begin="${sessionScope.page.getStartItem()}" end="${sessionScope.page.getLastItem()}" >

                                <tr class="no-rows">
                                    <!--<td colspan="8" style="text-align: center;">No rows found</td>-->
                                    <td >${o.getZoneID()}</td>
                                    <td >${o.getZoneName()}</td>
                                    <td >${o.getCreateAt()}</td>
                                    <td >${o.getUpdateAt()}</td>
                                    <!--<td style="max-width: 150px;">${o.getAddress()}</td>-->
                                    <td >${o.getCreateBy()}</td>
                                    <td >${o.getStoreID()}</td>

                                    <td> 
                                        <a href="UpdateCustomer?customerid=${o.getZoneID()}">
                                            <button class="bg-red-600 text-white px-6 py-2 rounded-md hover:bg-red-700">Edit</button>
                                        </a>
                                        <a href="ListDebtCustomer?customerid=${o.getZoneID()}">
                                            <button class="addNewDebt js-open-newDebt">More</button>
                                        </a>
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
        <form action="AddCustomer" method="post" >
            <div class="newDebt-container">
                <button class="newDebt-add">
                    Thêm mới 
                </button>
                <button class="newDebt-close js-close-newDebt">
                    close
                </button>

                <div class="newDebt-header">Thông tin người nợ
                </div>

                <div class="newDebt-body"> 
                    <table>
                        <thead id="newDebt-tableHeader">
                        </thead>
                        <tbody class="newDebt-tableTbody">

                            <tr class="newDebt-tableTbody-tr">
                                <td ><div class="newDebt-text"> Họ và tên (*):</div></td>
                                <td ><input class="newDebt-input" name="name" type="text" placeholder="Nguyen Van A" required> </td>                                    
                            </tr>                            
                            <tr class="newDebt-tableTbody-tr">
                                <td ><div class="newDebt-text"> Địa chỉ:</div></td>
                                <td ><textarea class="newDebt-input" name="address" rows="5" cols="10" name="feedback"></textarea><br></td>                                    
                            </tr>                                   
                            <tr class="newDebt-tableTbody-tr">
                                <td ><div class="newDebt-text"> SĐT (*):</div></td>
                                <td ><input class="newDebt-input" name="phone" type="number" required> </td>                                    
                            </tr>                                   
                            <tr class="newDebt-tableTbody-tr">
                                <td ><div class="newDebt-text"> Email:</div></td>
                                <td ><input class="newDebt-input" name="email" type="text" > </td>                                    
                            </tr>                                   
                            <tr class="newDebt-tableTbody-tr">
                                <td ><div class="newDebt-text"> Tổng nợ:</div></td>
                                <td ><input class="newDebt-input newDebt-total" name="total" type="number" placeholder="0" value="0" readonly> </td>                                    
                            </tr>  
                            <tr class="newDebt-tableTbody-tr">
                                <td ><div class="newDebt-text"> Người tạo:</div></td>
                                <td ><input class="newDebt-input newDebt-total"  type="text" placeholder="${sessionScope.username}" readonly> </td>                                    
                            </tr>  
                        </tbody>
                    </table>

                </div>
            </div>
        </form>
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

        // Chờ 5 giây rồi ẩn thông báo
        setTimeout(function () {
            var successMsg = document.getElementById("successMessage");
            var errorMsg = document.getElementById("errorMessage");

            if (successMsg)
                successMsg.style.display = "none";
            if (errorMsg)
                errorMsg.style.display = "none";
        }, 5000); // 5000ms = 5 giây


            </script>

            <script>
                
                        const openAddNewDebt = document.querySelector('.js-hidden-menu'); // Lấy phần tử có class này
                        const newDebt = document.querySelector('.menu'); // Lấy phần tử cần ẩn/hiện
                        const newDebt1 = document.querySelector('.main-content'); // Lấy phần tử cần ẩn/hiện
                        const newDebt2 = document.querySelector('.sidebar'); // Lấy phần tử cần ẩn/hiện

// Hàm toggle hiển thị
                        function toggleAddNewDebt() {
                            newDebt.classList.toggle('hiden'); // Nếu có class 'hiden' thì xóa, nếu chưa có thì thêm
                            newDebt1.classList.toggle('hiden'); // Nếu có class 'hiden' thì xóa, nếu chưa có thì thêm
                            newDebt2.classList.toggle('hiden'); // Nếu có class 'hiden' thì xóa, nếu chưa có thì thêm

                        }

// Gán sự kiện click
                        openAddNewDebt.addEventListener('click', toggleAddNewDebt);
                
            </script>