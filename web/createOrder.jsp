<%-- 
    Document   : home
    Created on : Feb 8, 2025, 5:59:03 PM
    Author     : TIEN DAT PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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


                <div class="balance">
                    <a href="loginURL?service=logoutUser">Log out</a>
                </div>

            </div>
            <div class="sidebar">
                <div class="logo">Bảng Điều Khiển</div>
                <a href="#">Trang chủ</a>
                <a href="#">Sổ ghi nợ</a>
                <a href="#">Quản lý kho</a>

                <a href="CreateOrderServlet">Tạo hóa đơn</a>
                <a href="#">Danh sách hóa đơn</a>
                <a href="#">Liên hệ</a>                
                <!--                 Thêm các mục dài để hiển thị thanh trượt 
                                <a href="#">Báo cáo tài chính</a>
                                <a href="#">Báo cáo hàng hóa</a>
                                <a href="#">Hồ sơ người dùng</a>
                                <a href="#">Cài đặt hệ thống</a>
                                <a href="#">Phân quyền</a>
                                <a href="#">Quản lý người dùng</a>
                                <a href="#">Quản lý đơn hàng</a>
                                <a href="#">Thống kê bán hàng</a>
                                <a href="#">Quản lý sản phẩm</a>
                                <a href="#">Lịch sử giao dịch</a>
                                <a href="#">Quản lý danh mục</a>
                                <a href="#">Quản lý tài khoản</a>-->
            </div>


            <div class="main-content">
                <div class="notification">
                    Thông báo: Mọi người có thể liên hệ admin tại fanpage Group 4
                </div>




                <div class="table-container">
                    <h3>Sản phẩm</h3>
                    <div class="filters">
                        <form action="SearchProductServlet" method="GET">
                            <label for="searchProduct">Tìm sản phẩm :</label>
                            <input type="text" id="searchProduct" name="searchProduct" placeholder="Nhập tên sản phẩm...">
                            <button type="submit">Tìm</button>

                        </form>
                    </div>

                    <c:if test="${id1==0}">
                        <c:choose>
                            <c:when test="${not empty products}">
                                <h4>Danh Sách Sản Phẩm</h4>
                                <table id="products" border="1" cellspacing="0" cellpadding="5">
                                    <thead>
                                        <tr>
                                            <th>Hình Ảnh</th>
                                            <th>Tên Sản Phẩm</th>
                                            <th>Số Lượng</th>
                                            <th>Đơn Giá</th>
                                            <th>Thao Tác</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="i" items="${products}">
                                            <tr>
                                                <td><img src="${i.image}" alt="Hình ảnh" width="50"></td>
                                                <td><c:out value="${i.productName}"/></td>
                                                <td><c:out value="${i.quantity}"/></td>
                                                <td><c:out value="${i.price}"/></td>
                                                <td>
                                                    <button onclick="addProductToOrder('${i.productID}', '${i.productName}', ${i.price})">THÊM</button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>    
                            </c:when>
                            <c:otherwise>
                                <p style="color: red; font-style: italic;">Không tìm thấy sản phẩm</p>

                            </c:otherwise>
                        </c:choose>
                    </c:if>



                    <h4>Khách Hàng</h4>
                    <div class="filters">

                        <form action="SearchCustomerServlet" method="GET">

                            <label for="searchCustomer">Tìm khách hàng:</label>
                            <input type="number" id="searchCustomer" name="searchCustomer" placeholder="Nhập số điện thoại...">
                            <button type="submit">Tìm</button>

                        </form>


                        <c:if test="${id==0}">
                            <c:choose>
                                <c:when test="${not empty customers}">

                                </c:when>
                                <c:otherwise>
                                    <option value="">Không tìm thấy khách hàng</option>
                                </c:otherwise>
                            </c:choose>


                        </c:if>



                    </div>




                    <div class="form-container">
                        <h3 style="color: red">Tạo Hóa Đơn</h3>



                        <script>
                            document.addEventListener("DOMContentLoaded", function () {
                                var today = new Date();
                                var formattedDate = today.toISOString().split('T')[0]; // Chuyển thành YYYY-MM-DD
                                document.getElementById("orderDate").value = formattedDate;
                            });


                        </script>






                        <form action="CreateOrderServlet" method="post">

                            <label for="orderDate">Ngày tạo đơn:</label>
                            <input type="date" id="orderDate" name="orderDate" readonly >
                            <br><br>




                            <label for="customerID">Khách Hàng:</label>
                            <select id="customerID" name="customerId" required>

                                <c:if test="${not empty customers}">
                                    <c:forEach var="customer" items="${customers}">
                                        <option value="${customer.customerID}">${customer.name} - ${customer.phone}</option>
                                    </c:forEach>
                                </c:if>



                            </select>





                            <br><br>


                            <label for="userID">Người Tạo:</label>

                            <p>${sessionScope.userName}</p>

                            <br>

                            <label for="porter">Số Người Bốc Vác:</label>
                            <input type="number" id="porter" name="porter" required
                                   style="width: 40px; height: 25px; border: 2px solid #000; border-radius: 5px; padding: 5px; text-align: center;">

                            <br><br>

                            <label for="status">Trạng Thái:</label><br><br>
                            <textarea id="status" name="status" rows="4" cols="40" required 
                                      style="border: 2px solid #000; border-radius: 5px; padding: 8px; resize: none;"></textarea>


                            <br><br>

                            <h3 style="color: red">Chi Tiết Đơn Hàng</h3>
                            <table id="orderItems">
                                <thead>
                                    <tr>
                                        <th>Sản Phẩm</th>
                                        <th>Số Lượng</th>
                                        <th>Đơn Giá</th>
                                        <th>Discount (%)</th>
                                        <th>Thành Tiền</th>
                                        <th>Thao Tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <!-- Các dòng sản phẩm sẽ được thêm vào đây -->
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <td colspan="3"><strong>Tổng cộng:</strong></td>
                                        <td><input type="number" id="totalOrderPrice" name="totalOrderPrice" readonly></td>
                                        <td></td>
                                    </tr>
                                </tfoot>
                            </table>
                            <br><br>

                            <button type="submit">Tạo Hóa Đơn</button>
                        </form>


                        <script>
                            function addProductToOrder(productID, productName, price) {
                                var table = document.getElementById("orderItems").getElementsByTagName('tbody')[0];
                                var newRow = table.insertRow(table.rows.length);

                                var cell1 = newRow.insertCell(0);
                                var cell2 = newRow.insertCell(1);
                                var cell3 = newRow.insertCell(2);
                                var cell4 = newRow.insertCell(3);
                                var cell5 = newRow.insertCell(4);
                                var cell6 = newRow.insertCell(5);

                                // Hiển thị tên sản phẩm
                                cell1.innerHTML = '<input type="hidden" name="productName" value="' + productName + '">' + productName;
                                cell2.innerHTML = '<input type="number" name="quantity" class="quantity" required value="1" min="1">';
                                cell3.innerHTML = '<input type="number" name="unitPrice" class="unitPrice" value="' + price + '" readonly>';
                                cell4.innerHTML = '<input type="number" name="discount" class="discount" value="0" min="0" max="100">';
                                cell5.innerHTML = '<input type="number" name="totalPrice" class="totalPrice" readonly>';
                                cell6.innerHTML = '<button type="button" onclick="deleteRow(this)">Xóa</button>';

                                // Gắn sự kiện tính toán tổng tiền
                                var quantityInput = cell2.querySelector('.quantity');
                                var unitPriceInput = cell3.querySelector('.unitPrice');
                                var discountInput = cell4.querySelector('.discount');
                                var totalPriceInput = cell5.querySelector('.totalPrice');

                                function recalculate() {
                                    calculateTotalPrice(quantityInput, unitPriceInput, discountInput, totalPriceInput);
                                }

                                quantityInput.addEventListener('input', recalculate);
                                discountInput.addEventListener('input', recalculate);

                                // Tính giá ban đầu
                                recalculate();
                                updateTotalOrderPrice();
                            }

                            function deleteRow(button) {
                                var row = button.parentNode.parentNode;
                                row.parentNode.removeChild(row);
                                updateTotalOrderPrice(); // Cập nhật tổng tiền sau khi xóa
                            }

                            function calculateTotalPrice(quantityInput, unitPriceInput, discountInput, totalPriceInput) {
                                var quantity = parseFloat(quantityInput.value) || 1;
                                var unitPrice = parseFloat(unitPriceInput.value) || 0;
                                var discount = parseFloat(discountInput.value) || 0;

                                if (discount < 0)
                                    discount = 0;
                                if (discount > 100)
                                    discount = 100;

                                var totalPrice = quantity * unitPrice * (1 - discount / 100);
                                totalPriceInput.value = totalPrice.toFixed(2);

                                updateTotalOrderPrice(); // Cập nhật tổng tiền đơn hàng
                            }

// Hàm tính tổng tiền của tất cả sản phẩm
                            function updateTotalOrderPrice() {
                                var total = 0;
                                var totalPriceInputs = document.querySelectorAll(".totalPrice");

                                totalPriceInputs.forEach(function (input) {
                                    total += parseFloat(input.value) || 0;
                                });

                                document.getElementById("totalOrderPrice").value = total.toFixed(2);
                            }
                        </script>


                    </div>


                </div>
            </div>
        </div>

    </div>
</body>






</html>
