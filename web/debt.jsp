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
            <div class="header">
                <div class="name-project">
                    <h2>Rice storage</h2>
                </div>


                <div class="balance">Login  </div>

            </div>
            <div class="sidebar">
                <div class="logo">Bảng Điều Khiển</div>
                <a href="#">Trang chủ</a>
                <a href="#">Quản lý thanh toán</a>
                <a href="#">Mua hàng</a>
                <a href="#">Trung gian</a>
                <a href="#">Dịch vụ</a>
                <!-- Thêm các mục dài để hiển thị thanh trượt -->
                <a href="#">Báo cáo tài chính</a>
                <a href="#">Báo cáo hàng hóa</a>
                <a href="#">Hồ sơ người dùng</a>
                <a href="#">Cài đặt hệ thống</a>
                <a href="#">Công cụ hỗ trợ</a>
                <a href="#">Liên hệ</a>
                <a href="#">Phản hồi</a>
                <!-- Các mục mới -->
                <a href="#">Quản lý người dùng</a>
                <a href="#">Quản lý đơn hàng</a>
                <a href="#">Thống kê bán hàng</a>
                <a href="#">Quản lý kho</a>
                <a href="#">Quản lý sản phẩm</a>
                <a href="#">Danh sách đối tác</a>
                <a href="#">Lịch sử giao dịch</a>
                <a href="#">Quản lý danh mục</a>
                <a href="#">Lập báo cáo</a>
                <a href="#">Quản lý tài khoản</a>
                <a href="#">Cài đặt bảo mật</a>
            </div>


            <div class="main-content">
                <div class="notification">
                    Thông báo: Mọi người có thể liên hệ admin tại fanpage Group 4
                </div>


                <div class="table-container">
                    <h3>Sản phẩm</h3>
                    <div class="filters">
                        <select>
                            <option value="">Trạng thái</option>
                            <option value="">A->Z</option>
                            <option value="">Z->A</option>

                        </select>
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
            <div class="newDebt-note"> Thêm khách hàng thành công!</div>
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

