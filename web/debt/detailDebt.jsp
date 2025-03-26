<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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


            .newDebt-input {
                font-size: 12px;
                border-radius: 5px;
                padding: 6px;
                width: 100%;
                margin: 15px;
            }


            .newDebt-text {
                font-size: 13px;
            }
        </style>

    </head>

    <body>
        <div id="main">

            <jsp:include page="/Component/header.jsp"></jsp:include>
            <div class="menu ">  <jsp:include page="/Component/menu.jsp"></jsp:include> </div>

                <div class="main-content">
                    <div class="notification">
                        Thông báo: Mọi người có thể liên hệ admin tại fanpage Group 4
                    </div>


                    <div class="table-update">
                        <div class="table-header">
                            <h3>Chi tiết công nợ</h3>
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



                                <tr class="newDebt-tableTbody-tr">
                                    <td ><div class="newDebt-text"> ID:</div></td>
                                    <td ><input class="newDebt-input newDebt-total"  value="${debtRecords.getDebtID()}" type="text" > </td>                                    
                                </tr>                            
                                <tr class="newDebt-tableTbody-tr">
                                    <td><div class="newDebt-text">Trạng thái:</div></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${debtRecords.getPaymentStatus() == 0}">
                                                <input class="newDebt-input newDebt-total" name="total" value="Vay nợ" type="text" placeholder="0" readonly>
                                            </c:when>
                                            <c:when test="${debtRecords.getPaymentStatus() == 1}">
                                                <input class="newDebt-input newDebt-total" name="total" value="Trả nợ" type="text" placeholder="0" readonly>
                                            </c:when>
                                            <c:when test="${debtRecords.getPaymentStatus() == 2}">
                                                <input class="newDebt-input newDebt-total" name="total" value="Đi vay" type="text" placeholder="0" readonly>
                                            </c:when>
                                            <c:when test="${debtRecords.getPaymentStatus() == 3}">
                                                <input class="newDebt-input newDebt-total" name="total" value="Đi trả" type="text" placeholder="0" readonly>
                                            </c:when>
                                            <c:otherwise>
                                                <input class="newDebt-input newDebt-total" name="total" value="Không xác định" type="text" placeholder="0" readonly>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>


                                <tr class="newDebt-tableTbody-tr">
                                    <td><div class="newDebt-text"> Số tiền (VND):</div></td>
                                    <td>
                                        <input class="newDebt-input newDebt-total" name="total" 
                                               value="<fmt:formatNumber value='${debtRecords.getAmount()}' type='number' minFractionDigits='0' />" 
                                               type="text" placeholder="0" readonly> 
                                    </td>                                    
                                </tr>


                                <tr class="newDebt-tableTbody-tr">
                                    <td ><div class="newDebt-text"> Ảnh :</div></td>
                                    <td class="table-cell" style="width: 400px; height: 100px;"> 
                                        <c:if test="${not empty debtRecords.getImg()}">
                                            <img src="${debtRecords.getImg()}" class="product-image" style="    MARGIN-LEFT: 25px; width: 400px;height: 400px  ; cursor: pointer; object-fit: contain;"
                                                 onclick="showImageModal('${debtRecords.getImg()}')">
                                        </c:if>
                                    </td>  
                                </tr>                                   
                                <tr class="newDebt-tableTbody-tr">
                                    <td ><div class="newDebt-text">Ghi chú :</div></td>
                                    <td ><textarea class="newDebt-input newDebt-total"  rows="5" cols="10" >${debtRecords.getDescription()}</textarea><br></td>                                    
                                </tr>                                    
                                <tr class="newDebt-tableTbody-tr">
                                    <td ><div class="newDebt-text"> Ngày lập phiếu:</div></td>
                                    <td ><input class="newDebt-input newDebt-total"  value="${debtRecords.getUpdateAt()}" type="datetime-local" placeholder="0" readonly> </td>                                    
                                </tr>  
                                <tr class="newDebt-tableTbody-tr">  

                                </tr>  
                            </tbody>

                        </table>
                        <button type="button" class="table-update-add" style=" background-color: #33CC33" onclick="window.location.href = '<%=request.getContextPath()%>/ListDebtCustomer?customerid=${debtRecords.getCustomerID()}'">
                            Quay lại
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
    <script>

        // Lấy các phần tử cần ẩn/hiện
        const openAddNew = document.querySelector('.js-hidden-menu'); // Nút toggle
        const newDebt0 = document.querySelector('.menu'); // Menu
        const newDebt1 = document.querySelector('.main-content'); // Nội dung chính
        const newDebt2 = document.querySelector('.sidebar'); // Sidebar

// Kiểm tra trạng thái đã lưu trong localStorage khi trang load
        document.addEventListener("DOMContentLoaded", function () {
            if (localStorage.getItem("menuHidden") === "true") {
                newDebt0.classList.add('hiden');
                newDebt1.classList.add('hiden');
                newDebt2.classList.add('hiden');
            }
        });

// Hàm toggle hiển thị
        function toggleAddNewDebt() {
            newDebt0.classList.toggle('hiden');
            newDebt1.classList.toggle('hiden');
            newDebt2.classList.toggle('hiden');

            // Lưu trạng thái vào localStorage
            const isHidden = newDebt0.classList.contains('hiden');
            localStorage.setItem("menuHidden", isHidden);
        }

// Gán sự kiện click
        openAddNew.addEventListener('click', toggleAddNewDebt);


    </script>

</html>

