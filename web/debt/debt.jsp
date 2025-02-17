<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./assets/css/style.css">
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
                            <h3>Sản phẩm</h3>
                        <c:if test="${message == 'success'}">
                            <div class="newDebt-notification">Thêm khách hàng thành công!</div>
                        </c:if>

                        <c:if test="${message == 'error'}">
                            <div class="newDebt-notificationError">Thêm khách hàng thất bại.</div>
                        </c:if>
                    </div>
                    <div class="filters">
                        <select>
                            <option value="">Trạng thái</option>
                            <option value="">A->Z</option>
                            <option value="">Z->A</option>

                        </select>
                        <input type="text" placeholder="Search">
                        <input type="text" placeholder="Từ">
                        <input type="text" placeholder="Đến">
                        <button>Bỏ lọc</button>
                        <button>Thu gọn</button>

                        <button class="addNewDebt js-open-newDebt">Thêm người nợ</button>

                    </div>

                    <table>
                        <thead id="table-header">
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Address</th>
                                <th>TotalDebt</th>
                                <th>CreateAt</th>
                                <th>UpdateAt</th>
                                <th>CreateBy</th>
                                <th>DeleteAt</th>
                                <th>DeleteBy</th>
                                <th>IsDelete</th>

                            </tr>
                        </thead>
                        <tbody id="table-tbody">
                            <c:forEach items="${listCustomers}" var="o">
                                <tr class="no-rows">
                                    <!--<td colspan="8" style="text-align: center;">No rows found</td>-->
                                    <td >${o.getCustomerID()}</td>
                                    <td >${o.getName()}</td>
                                    <td >${o.getEmail()}</td>
                                    <td >${o.getPhone()}</td>
                                    <td >${o.getAddress()}</td>
                                    <td >${o.getTotalDebt()}</td>
                                    <td >${o.getCreateAt()}</td>
                                    <td >${o.getUpdateAt()}</td>
                                    <td >${o.getCreateBy()}</td>
                                    <td >${o.getDeleteAt()}</td>
                                    <td >${o.getDeleteBy()}</td>
                                    <td >${o.getIsDelete()}</td>

                                </tr>
                            </c:forEach>   
                        </tbody>
                    </table>
                </div>
            </div>
        </div>


    </body>

    <div class="newDebt">
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
                            <td ><input class="newDebt-input" name="name" type="text" placeholder="Nguyen Van A"> </td>                                    
                        </tr>                            
                        <tr class="newDebt-tableTbody-tr">
                            <td ><div class="newDebt-text"> Địa chỉ:</div></td>
                            <td ><textarea class="newDebt-input" name="address" rows="5" cols="10" name="feedback"></textarea><br></td>                                    
                        </tr>                                   
                        <tr class="newDebt-tableTbody-tr">
                            <td ><div class="newDebt-text"> SĐT:</div></td>
                            <td ><input class="newDebt-input" name="phone" type="number" > </td>                                    
                        </tr>                                   
                        <tr class="newDebt-tableTbody-tr">
                            <td ><div class="newDebt-text"> Email:</div></td>
                            <td ><input class="newDebt-input" name="email" type="text" > </td>                                    
                        </tr>                                   
                        <tr class="newDebt-tableTbody-tr">
                            <td ><div class="newDebt-text"> Tổng nợ:</div></td>
                            <td ><input class="newDebt-input newDebt-total" name="total" type="number" placeholder="0" readonly> </td>                                    
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

