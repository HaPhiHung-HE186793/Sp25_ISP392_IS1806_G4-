<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./assets/css/style.css">
        <link rel="stylesheet" href="./assets/fonts/themify-icons/themify-icons.css">
        <title>Bảng Điều Khiển</title>
        <style>
            .newDebt-input {
                padding: 5px;
                width: 150%;
                margin: 15px;
            }

            .fixed-cell {
                width: 150px;  /* Cố định chiều rộng */
                height: 60px;  /* Cố định chiều cao */
                overflow: hidden; /* Ẩn nội dung vượt quá */
                text-overflow: ellipsis; /* Hiển thị dấu "..." nếu nội dung quá dài */
                white-space: nowrap; /* Không xuống dòng */
            }

            .scrollable-cell {
                width: 150px;  /* Cố định chiều rộng */
                max-height: 60px; /* Cố định chiều cao */
                overflow-y: auto; /* Cho phép cuộn dọc nếu nội dung vượt quá */
                word-wrap: break-word; /* Tự động xuống dòng */
                display: block; /* Bắt buộc để overflow hoạt động */
                padding: 5px;
            }

            .image-cell {
                width: 70px;
                height: 60px;
                display: flex;
                align-items: center;
                justify-content: center;
                background-color: rgba(255, 255, 255, 0.1); /* Màu nền nhẹ để dễ nhìn */
            }

            .product-image {
                width: 50px;
                height: 50px;
                object-fit: contain; /* Hiển thị ảnh mà không bị méo */
                cursor: pointer;
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


                    <div class="table-container">
                        <div class="table-header">
                            <h3>Chi tiết công nợ</h3>
                            <h3>Người nợ: ${customers.getName()}</h3>

                        <c:if test="${message == 'success'}">
                            <div class="newDebt-notification">Thêm nợ thành công </div>
                        </c:if>

                        <c:if test="${message == 'error'}">
                            <div class="newDebt-notificationError">Thêm nợ thất bại.</div>
                        </c:if>
                    </div>
                    <div class="filters">
                        <form action="ListDebtCustomer" method="post" >
                            <!--                            <select name="sortBy">
                                                            <option value="0">Trạng thái</option>
                                                            <option value="1">Thời gian tạo</option>
                                                            <option value="2">Ngày lập phiếu</option>
                            
                                                        </select>-->
                            <input name="searchStartDate" type="date" value="${searchStartDate}">
                            <input name="searchEndDate" type="date" value="${searchEndDate}">

                            <button style="background-color: #5bc0de;" >Lọc</button>
                        </form>
                        <!--<button onclick="window.location.href = '<%=request.getContextPath()%>/ListDebtCustomer?id=${customerid}'">Bỏ lọc</button>--> 


                        <button class="addNewDebt js-open-newDebt">Tạo phiếu nợ</button>
                        <button type="button" class="table-update-add" style=" background-color: #33CC33" onclick="window.location.href = '<%=request.getContextPath()%>/ListCustomer'">
                            Quay lại
                        </button>
                    </div>



                    <table>
                        <thead id="table-header">
                            <tr>
                                <th>ID</th>
                                <th>Trạng thái</th>
                                <th>Ảnh</th>
                                <th>Số tiền</th>
                                <th>Ghi chú</th>
                                <th>thời gian tạo</th>
                                <th>Ngày lập phiếu</th>
                                <th>Hành động</th>

                            </tr>
                        </thead>
                        <tbody id="table-tbody">
                            <c:forEach items="${listCustomer}" var="o" begin="${sessionScope.page.getStartItem()}" end="${sessionScope.page.getLastItem()}">
                                <tr class="no-rows">
                                    <!--<td colspan="8" style="text-align: center;">No rows found</td>-->
                                    <td >${o.getDebtID()}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${o.getPaymentStatus() == 0}">Vay nợ</c:when>
                                            <c:when test="${o.getPaymentStatus() == 1}">Trả nợ</c:when>
                                            <c:when test="${o.getPaymentStatus() == 2}">Đi vay</c:when>
                                            <c:when test="${o.getPaymentStatus() == 3}">Đi trả</c:when>
                                            <c:otherwise>Không xác định</c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td class="table-cell" style="width: 70px; height: 60px;">
                                        <c:if test="${not empty o.getImg()}">
                                            <img src="${o.getImg()}" class="product-image" style="width: 50px;height: 50px  ; cursor: pointer; object-fit: contain;"
                                                 onclick="showImageModal('${o.getImg()}')">
                                        </c:if>
                                    </td>

                                    <td style="max-width: 100px;
                                        margin-top: 20px;
                                        white-space: nowrap;
                                        overflow: hidden;
                                        text-overflow: ellipsis;
                                        display: inline-block;
                                        text-align: center;
                                        vertical-align: middle;">
                                        <fmt:setLocale value="de_DE" />
                                        <fmt:formatNumber value="${o.getAmount()}" type="number" minFractionDigits="0" /> VND
                                    </td>



                                    <td style="  max-width: 100px; /* Điều chỉnh độ rộng tối đa theo nhu cầu */
                                        white-space: nowrap;
                                        overflow: hidden;
                                        text-overflow: ellipsis;">${o.getDescription()}</td>

                                    <td >${o.getCreateAt()}</td>
                                    <td >${o.getUpdateAt()}</td>

                                    <td> 
                                        <a href="DetailDebtCustomer?debtid=${o.getDebtID()}">
                                            <button class="bg-red-600 text-white px-6 py-2 rounded-md hover:bg-red-700">Chi tiết</button>
                                        </a>
                                    </td>
                                </tr>

                            </c:forEach>   

                        </tbody>
                    </table>
                                    <%@include file="/Component/pagination.jsp" %>

                </div>
            </div>

        </div>

    </body>

    <div class="detailImg" id="imageModal" style="display: none;">
        <div class="detailImg-container">
            <div class="detailImg-close js-close-detailImg" onclick="closeImageModal()">
                <i class="ti-close"></i>
            </div>
            <div class="detailImg-img">
                <img id="largeImage" src="" style="        max-width: 700px;
                     max-height: 700px;">
            </div>
        </div>
    </div>




    <div class="newDebt">

        <div class="newDebt-container">



            <button class="newDebt-close js-close-newDebt">
                close
            </button>

            <div class="newDebt-header">Chi tiết nợ
            </div>
            <form action="AddNewCustomerDebt" method="post"  enctype="multipart/form-data">
                <button class="newDebt-add">
                    Thêm mới 
                </button>
                <div class="newDebt-body" style="    height: 445px;">                                
                    <table>
                        <thead id="newDebt-tableHeader">
                        </thead>
                        <tbody class="newDebt-tableTbody">
                            <tr>
                                <td ><input class="newDebt-input" name="customerid" value="${customers.getCustomerID()}" type="hidden"> </td>                                    
                            </tr>                               
                            <tr class="newDebt-tableTbody-tr">
                                <td ><div class="newDebt-text"> Loại nợ:</div></td>
                                <td >
                                    <select name="typeDebt" class="newDebt-input" required>
                                        <option value="0">Vay nợ</option>
                                        <option value="1">Trả nợ</option>
                                        <option value="2">Đi vay</option>
                                        <option value="3">Đi trả</option>
                                    </select>
                                </td>   
                            </tr>  


                            <tr class="newDebt-tableTbody-tr">
                                <td><div class="newDebt-text">Ảnh:</div></td>
                                <td><input class="newDebt-input" type="file" id="image"  name="image" accept="image/*"></td>
                            </tr> 
                            <tr class="newDebt-tableTbody-tr">
                                <td><div class="newDebt-text">Ngày lập phiếu:</div></td>
                                <td><input class="newDebt-input newDebt-date" name="dateTime" type="date"></td>
                            </tr>                                  
                            <tr class="newDebt-tableTbody-tr">
                                <td ><div class="newDebt-text"> Số tiền:</div></td>
                                <td ><input class="newDebt-input" name="debt" type="number" min="0"> </td>                                    
                            </tr>
                            <tr class="newDebt-tableTbody-tr">
                                <td ><div class="newDebt-text"> Ghi chú:</div></td>
                                <td ><textarea class="newDebt-input" name="description" rows="5" cols="10" name="feedback"></textarea><br></td>                                    
                            </tr>       
                        </tbody>
                    </table>

                </div>
            </form>

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




        function showImageModal(imgSrc) {
            document.getElementById("largeImage").src = imgSrc;
            document.getElementById("imageModal").style.display = "flex";
        }

        function closeImageModal() {
            document.getElementById("imageModal").style.display = "none";
        }


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

