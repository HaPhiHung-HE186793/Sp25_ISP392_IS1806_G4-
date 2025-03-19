<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
        <link rel="stylesheet" href="./assets/fonts/themify-icons/themify-icons.css">
        <title>Bảng Điều Khiển</title>
        <style>
.newDebt-input {
    border-radius: 5px;
    padding: 10px;
    width: 200%;
    margin: 15px;
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


                    <div class="table-update">
                        <div class="table-header">
                            <h3>Khách hàng</h3>
                        <c:if test="${message == 'success'}">
                            <div id="successMessage" class="newDebt-notification">Cập nhật thành công!</div>
                        </c:if>

                        <c:if test="${message == 'error'}">
                            <div id="errorMessage" class="newDebt-notificationError">Cập nhật thất bại.</div>
                        </c:if>
                    </div>

                    <form action="UpdateCustomer" method="post"  style="margin-left: 100px;">
                        <table>                      

                            <tbody class="newDebt-tableTbody">
                                <tr >
                                    <td ><input name="id" value="${customers.getCustomerID()}" type="hidden"> </td>                                    
                                </tr>  
                                <tr class="newDebt-tableTbody-tr">
                                    <td ><div class="newDebt-text"> Họ và tên (*):</div></td>
                                    <td ><input class="newDebt-input" name="name" value="${customers.getName()}" type="text" placeholder="Nguyen Van A" required> </td>                                    
                                </tr>                            
                                <tr class="newDebt-tableTbody-tr">
                                    <td ><div class="newDebt-text"> Địa chỉ:</div></td>
                                    <td ><textarea class="newDebt-input" name="address" value="${customers.getAddress()}" rows="5" cols="10" name="feedback"></textarea><br></td>                                    
                                </tr>                                   
                                <tr class="newDebt-tableTbody-tr">
                                    <td ><div class="newDebt-text"> SĐT (*):</div></td>
                                    <td ><input class="newDebt-input" name="phone" value="${customers.getPhone()}" type="number" required> </td>                                    
                                </tr>                                   
                                <tr class="newDebt-tableTbody-tr">
                                    <td ><div class="newDebt-text"> Email:</div></td>
                                    <td ><input class="newDebt-input" name="email" value="${customers.getEmail()}" type="text" > </td>                                    
                                </tr>                                   
                                <tr class="newDebt-tableTbody-tr">
                                    <td ><div class="newDebt-text"> Tổng nợ:</div></td>
                                    <td ><input class="newDebt-input newDebt-total" name="total" value="${customers.getTotalDebt()}" type="number" placeholder="0" readonly> </td>                                    
                                </tr>  
                                <tr class="newDebt-tableTbody-tr">  

                                </tr>  
                            </tbody>

                        </table>
                                <button type="button" class="table-update-add" style=" background-color: #33CC33" onclick="window.location.href = '<%=request.getContextPath()%>/ListCustomer'">
                        Quay lại
                    </button>
                        <button class="table-update-add" style="margin-left:10px;">
                            Cập nhật
                        </button>
                                    
                    </form>

                </div>
            </div>
        </div>


    </body>

   

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


</html>

