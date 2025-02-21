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
            <jsp:include page="/Component/menu.jsp"></jsp:include>


                <div class="main-content">
                    <div class="notification">
                        Thông báo: Mọi người có thể liên hệ admin tại fanpage Group 4
                    </div>




                    <div class="table-container">



                        <div class="filters">
                            <form action="SearchServlet" method="GET">


                                <label for="searchProduct">Tìm sản phẩm :</label>
                                <input type="text" name="searchProduct" placeholder="Nhập tên sản phẩm...">
                                <button type="submit">Tìm</button>

                            </form>
                            <br>
                            <form action="SearchServlet" method="POST">

                                <label for="searchCustomer">Tìm khách hàng:</label>
                                <input type="number" name="searchCustomer" placeholder="Nhập số điện thoại...">
                                <button type="submit">Tìm</button>

                            </form>
                        </div>

                    <c:if test="${id1==0}">
                        <c:choose>
                            <c:when test="${not empty products}">
                                <h4>Danh Sách Sản Phẩm</h4>
                                <table>
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


                    <div class="form-container">
                        <h3 style="color: red">Tạo Hóa Đơn</h3>





                        <form id="orderForm" action="CreateOrderServlet" method="post">

                            <label for="orderDate">Ngày tạo đơn:</label>
                            <input type="date" id="orderDate" name="orderDate" readonly >
                            <br><br>




                            <label for="customerID">Khách Hàng:</label>
                            <select id="customerID" name="customerId" required>
                                <c:if test="${not empty customers}">
                                    <c:forEach var="customer" items="${customers}">
                                        <option value="${customer.customerID}" ${customer.customerID == param.selectedCustomerId ? 'selected' : ''}>
                                            ${customer.name} - ${customer.phone}
                                        </option>
                                    </c:forEach>
                                </c:if>
                            </select>


                            <br>

                            <c:if test="${id == 0 && empty customers}">
                                <p style="color: red; font-style: italic;">Không tìm thấy khách hàng</p>
                            </c:if>

                            <script>

                                document.addEventListener("DOMContentLoaded", function () {
                                    let customerSelect = document.getElementById("customerID");
                                    let savedCustomerId = sessionStorage.getItem("selectedCustomerId");

                                    if (savedCustomerId) {
                                        customerSelect.value = savedCustomerId;
                                    }

                                    customerSelect.addEventListener("change", function () {
                                        sessionStorage.setItem("selectedCustomerId", customerSelect.value);
                                    });
                                });


                            </script>        




                            <br>


                            <label for="userID">Người Tạo:</label>

                            <p>${sessionScope.username}</p>



                            <label for="porter">Số Người Bốc Vác:</label>
                            <input type="number" id="porter" name="porter" required
                                   style="width: 40px; height: 25px; border: 2px solid #000; border-radius: 5px; padding: 5px; text-align: center;">

                            <br><br>


                            <script>
                                document.addEventListener("DOMContentLoaded", function () {
                                    let porter = document.getElementById("porter");
                                    let savedPorter = sessionStorage.getItem("inputedPorter");

                                    // Kiểm tra nếu có giá trị đã lưu trong sessionStorage
                                    if (savedPorter) {
                                        porter.value = savedPorter;
                                    }

                                    // Lắng nghe sự kiện input khi người dùng thay đổi giá trị
                                    porter.addEventListener("input", function () {
                                        // Lưu giá trị vào sessionStorage
                                        sessionStorage.setItem("inputedPorter", porter.value);
                                    });
                                });
                            </script>

                            <label for="status">Trạng Thái:</label><br><br>
                            <textarea id="status" name="status" rows="4" cols="40" required 
                                      style="border: 2px solid #000; border-radius: 5px; padding: 8px;"></textarea>


                            <br><br>

                            <h3 style="color: red">Chi Tiết Đơn Hàng</h3>

                            <p style="color: hotpink">Thông báo: ${requestScope.ms}</p>

                            <table id="orderItems" border="1" width="100%" cellspacing="0" cellpadding="5">
                                <thead>
                                    <tr>
                                        <th width="20%">Sản Phẩm</th>
                                        <th width="10%">Đơn vị</th>
                                        <th width="10%">Số Lượng</th>
                                        <th width="10%">Đơn Giá</th>
                                        <th width="10%">Giảm Giá (VND/kg)</th>
                                        <th width="15%">Thành Tiền</th>
                                        <th width="15%">Chú Thích</th>
                                        <th width="10%">Thao Tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <!-- Các dòng sản phẩm sẽ được thêm vào đây -->
                                </tbody>
                                <tfoot>
                                    <tr align="center">
                                        <td colspan="4"><strong>Tổng tiền:</strong></td>
                                        <td colspan="2"><input type="number" id="totalOrderPrice" name="totalOrderPrice" readonly></td>
                                    </tr>
                                    <tr align="center">
                                        <td colspan="4"><strong>Tổng tiền đã giảm:</strong></td>
                                        <td colspan="2"><input type="number" id="totalDiscount" name="totalDiscount" readonly></td>
                                    </tr>
                                </tfoot>
                            </table>

                            <br><br>

                            <button type="submit">Tạo Hóa Đơn</button>

                            <script>

                                document.addEventListener("DOMContentLoaded", function () {
                                    document.getElementById("orderForm").addEventListener("submit", function () {
                                        sessionStorage.clear(); // Xóa toàn bộ dữ liệu trong sessionStorage
                                    });
                                });

                            </script>

                            <script>
                                document.addEventListener("DOMContentLoaded", function () {
                                    let today = new Date();
                                    let formattedDate = today.toISOString().split('T')[0]; // Chuyển thành YYYY-MM-DD
                                    document.getElementById("orderDate").value = formattedDate;
                                    loadOrderFromSessionStorage();
                                });
                            </script>
                            <script>
                                function addProductToOrder(productID, productName, pricePerKg) {
                                    var table = document.getElementById("orderItems").getElementsByTagName('tbody')[0];
                                    var newRow = table.insertRow(table.rows.length);
                                    var cell1 = newRow.insertCell(0);
                                    var cell2 = newRow.insertCell(1);
                                    var cell3 = newRow.insertCell(2);
                                    var cell4 = newRow.insertCell(3);
                                    var cell5 = newRow.insertCell(4);
                                    var cell6 = newRow.insertCell(5);
                                    var cell7 = newRow.insertCell(6);
                                    var cell8 = newRow.insertCell(7);
                                    var cell9 = newRow.insertCell(8); // Thêm cột ẩn để lưu totalWeight
                                    // Hiển thị tên sản phẩm
                                    cell1.innerHTML =
                                            '<input type="hidden" name="productID" value="' + productID + '">' +
                                            '<input type="hidden" name="productName" value="' + productName + '">' +
                                            productName;
                                    // Chọn đơn vị tính: kg hoặc bao
                                    cell2.innerHTML = '<select name="unitType" class="unitType">' +
                                            '<option value="1">Kg</option>' +
                                            '<option value="10">Bao (10kg)</option>' +
                                            '<option value="20">Bao (20kg)</option>' +
                                            '<option value="50">Bao (50kg)</option>' +
                                            '</select>';



                                    // Số lượng sản phẩm
                                    cell3.innerHTML = '<input type="number" name="quantity" class="quantity" required value="1" min="1">';
                                    // Đơn giá (tự động tính dựa trên giá gạo 1kg)
                                    cell4.innerHTML = '<input type="number" name="unitPrice" class="unitPrice" value="' + pricePerKg + '"  readonly>';
                                    // Giảm giá (VND/kg)
                                    cell5.innerHTML = '<input type="number" name="discount" class="discount" value="0" min="0">';
                                    // Thành tiền (tự động cập nhật)
                                    cell6.innerHTML = '<input type="number" name="totalPrice" class="totalPrice" readonly>';
                                    // Nút chú thích
                                    cell7.innerHTML = '<input type="text" name="description" class="description">';
                                    // Nút xóa
                                    cell8.innerHTML = '<button type="button" onclick="deleteRow(this)">Xóa</button>';

                                    // Thêm input ẩn để lưu totalWeight
                                    cell9.innerHTML = '<input type="hidden" name="totalWeight" class="totalWeight">';


                                    // Gắn sự kiện tính toán tổng tiền
                                    var quantityInput = cell3.querySelector('.quantity');
                                    var unitTypeInput = cell2.querySelector('.unitType');
                                    var discountInput = cell5.querySelector('.discount');
                                    var totalPriceInput = cell6.querySelector('.totalPrice');
                                    var totalWeightInput = cell9.querySelector('.totalWeight'); // Lấy input ẩn totalWeight
                                    function recalculate() {
                                        var unitMultiplier = parseInt(unitTypeInput.value); // 1kg hoặc bao 10kg, 20kg, 50kg
                                        var quantity = parseFloat(quantityInput.value) || 1;
                                        var totalWeight = quantity * unitMultiplier; // Tổng số kg mua
                                        var discount = parseFloat(discountInput.value) || 0;
                                        var totalDiscount = totalWeight * discount; // Tổng số tiền giảm
                                        var totalPrice = (totalWeight * pricePerKg) - totalDiscount; // Thành tiền sau giảm giá
                                        // Cập nhật totalWeight vào input ẩn
                                        totalWeightInput.value = totalWeight;

                                        totalPriceInput.value = totalPrice.toFixed(2);
                                        updateTotalOrderPrice();
                                        saveOrderToSessionStorage();
                                    }

                                    quantityInput.addEventListener('input', recalculate);
                                    unitTypeInput.addEventListener('change', recalculate);
                                    discountInput.addEventListener('input', recalculate);

                                    recalculate();

                                }

                                function deleteRow(button) {
                                    var row = button.parentNode.parentNode;
                                    row.parentNode.removeChild(row);
                                    updateTotalOrderPrice();
                                    saveOrderToSessionStorage();
                                }

                                function updateTotalOrderPrice() {
                                    var total = 0;
                                    var totalDiscount = 0;
                                    var totalPriceInputs = document.querySelectorAll(".totalPrice");
                                    var discountInputs = document.querySelectorAll(".discount");
                                    var quantityInputs = document.querySelectorAll(".quantity");
                                    var unitTypeInputs = document.querySelectorAll(".unitType");
                                    totalPriceInputs.forEach((input, index) => {
                                        total += parseFloat(input.value) || 0;
                                        var discount = parseFloat(discountInputs[index].value) || 0;
                                        var quantity = parseFloat(quantityInputs[index].value) || 1;
                                        var unitMultiplier = parseInt(unitTypeInputs[index].value); // 1kg hoặc bao 10kg, 20kg, 50kg
                                        var totalWeight = quantity * unitMultiplier; // Tổng số kg của sản phẩm này

                                        totalDiscount += totalWeight * discount; // Tổng số tiền giảm giá của sản phẩm này
                                    });
                                    document.getElementById("totalOrderPrice").value = total.toFixed(2);
                                    document.getElementById("totalDiscount").value = totalDiscount.toFixed(2);
                                }

                                function saveOrderToSessionStorage() {
                                    var orderItems = [];
                                    document.querySelectorAll("#orderItems tbody tr").forEach(row => {
                                        var productID = row.querySelector("input[name='productID']").value;
                                        var productName = row.querySelector("input[name='productName']").value;
                                        var unitType = row.querySelector(".unitType").value;
                                        var quantity = row.querySelector(".quantity").value;
                                        var unitPrice = row.querySelector(".unitPrice").value;
                                        var discount = row.querySelector(".discount").value;
                                        var totalPrice = row.querySelector(".totalPrice").value;
                                        orderItems.push({productID, productName, unitType, quantity, unitPrice, discount, totalPrice});
                                    });
                                    sessionStorage.setItem("orderItems", JSON.stringify(orderItems));
                                }

                                function loadOrderFromSessionStorage() {
                                    var orderItems = JSON.parse(sessionStorage.getItem("orderItems")) || [];
                                    orderItems.forEach(item => {
                                        addProductToOrder(item.productID, item.productName, parseFloat(item.unitPrice));
                                        var lastRow = document.querySelector("#orderItems tbody tr:last-child");
                                        lastRow.querySelector(".unitType").value = item.unitType;
                                        lastRow.querySelector(".quantity").value = item.quantity;
                                        lastRow.querySelector(".discount").value = item.discount;
                                        lastRow.querySelector(".totalPrice").value = item.totalPrice;
                                    });
                                    updateTotalOrderPrice();
                                }

                            </script>
                        </form>





                    </div>


                </div>
            </div>
        </div>


    </body>






</html>
