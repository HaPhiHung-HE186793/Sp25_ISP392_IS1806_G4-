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

            <jsp:include page="/Component/menu.jsp"></jsp:include>

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
<!--                        <form action="ListDebtCustomer" method="post" >

                                                    <select>
                                                        <option value="">Trạng thái</option>
                                                        <option value="">A->Z</option>
                                                        <option value="">Z->A</option>
                            
                                                    </select>
                            <input name="name" type="text" placeholder="Tìm kiếm" value="${search}">
                            <input name="number" type="number" placeholder="Số điện thoại" value="${searchNumber}">
                            <input name="startDate" type="date" value="${searchStartDate}">
                            <input name="endDate" type="date" value="${searchEndDate}">-->
<!--                            <button style="background-color: #5bc0de;" >Lọc</button>-->
                        <!--</form>-->
                        <!--<button onclick="window.location.href = '<%=request.getContextPath()%>/ListCustomer'">Bỏ lọc</butto //n>--> 

                        <button class="addNewDebt js-open-newDebt">Tạo phiếu nợ</button>

                    </div>

                    <table>
                        <thead id="table-header">
                            <tr>
                                <th>ID</th>
                                <th>Trạng thái</th>
                                <th>Số tiền</th>
                                <th>thời gian tạo</th>
                                <th>Ngày lập phiếu</th>

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
                                    <td >${o.getAmount()}</td>
                                    <td >${o.getCreateAt()}</td>
                                    <td >${o.getUpdateAt()}</td>
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



            <button class="newDebt-close js-close-newDebt">
                close
            </button>

            <div class="newDebt-header">Chi tiết nợ
            </div>
            <form action="AddNewCustomerDebt" method="post"  >
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
                                <td><div class="newDebt-text">Ngày lập phiếu:</div></td>
                                <td><input class="newDebt-input newDebt-date" name="dateTime" type="datetime-local"></td>
                            </tr>                                  
                            <tr class="newDebt-tableTbody-tr">
                                <td ><div class="newDebt-text"> Số tiền:</div></td>
                                <td ><input class="newDebt-input" name="debt" type="number" min="0"> </td>                                    
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




    </script>


</html>

