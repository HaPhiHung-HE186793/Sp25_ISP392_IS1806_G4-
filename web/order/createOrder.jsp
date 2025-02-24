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

        <style>

            /* --- Form và Input --- */
            .container {
                max-width: 1200px;
                margin: 0 auto;
                background-color: #2a2a2a;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(255, 255, 255, 0.1);
            }




            input[type="text"],
            input[type="number"],
            input[type="date"],
            select {
                padding: 8px;
                border: 1px solid #444;
                border-radius: 4px;
                background-color: #333;
                color: #e0e0e0;
                font-size: 14px;
                box-sizing: border-box;
                width: 200px; /* Điều chỉnh độ rộng tùy ý */
            }

            button {
                padding: 8px 16px;
                border: none;
                border-radius: 4px;
                background-color: #4CAF50;
                color: white;
                font-size: 14px;
                cursor: pointer;
            }

            button:hover {
                background-color: #45a049;
            }

            /* --- Bảng --- */
            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
                table-layout: fixed;
                background-color: #2a2a2a;
                border-radius: 5px;
                overflow: hidden;
            }

            th, td {
                border: 1px solid #444;
                padding: 10px;
                text-align: left;
                word-wrap: break-word;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
            }

            th {
                background-color: #333;
                color: #fff;
            }

            .form-container {
                background-color: #2a2a2a;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(255, 255, 255, 0.1);
                overflow-x: auto;
            }


            /* --- Bảng orderItems --- */
            #orderItems input[readonly],
            #orderItems input[type="text"],
            #orderItems input[type="number"],
            #orderItems input[type="date"],
            #orderItems select {
                background-color: #3a3a3a;
                width: 100%;
                box-sizing: border-box;
                text-align: center;
                color: #e0e0e0; /* Đảm bảo màu chữ sáng dễ nhìn */
                padding: 8px; /* Đồng bộ khoảng cách bên trong */
                border: 1px solid #444; /* Đồng bộ viền */
                border-radius: 4px; /* Đồng bộ bo góc */
            }


            /* --- Điều chỉnh kích thước cột bảng orderItems --- */
            #orderItems th:first-child, #orderItems td:first-child {
                width: 18%;
            }

            #orderItems th:nth-child(2), #orderItems td:nth-child(2),
            #orderItems th:nth-child(3), #orderItems td:nth-child(3),
            #orderItems th:nth-child(4), #orderItems td:nth-child(4),
            #orderItems th:nth-child(5), #orderItems td:nth-child(5) {
                width: 12%;
            }

            #orderItems th:nth-child(6), #orderItems td:nth-child(6),
            #orderItems th:nth-child(7), #orderItems td:nth-child(7) {
                width: 12%;
            }

            #orderItems th:last-child, #orderItems td:last-child {
                width: 7%;
            }

            textarea {
                padding: 8px;
                border: 1px solid #444;
                border-radius: 4px;
                background-color: #333;
                color: #e0e0e0;
                font-size: 14px;
                box-sizing: border-box;
                width: 200px; /* Giống với input */
                resize: none; /* Ngăn thay đổi kích thước */
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
                                                    <button onclick="addProductToOrder('${i.productID}', '${i.productName}', ${i.price}, ${i.quantity}, ${i.quantity})">
                                                        THÊM
                                                    </button>
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



                        <h2 style="color: red">Tạo Hóa Đơn</h2>






                        <form id="orderForm" action="CreateOrderServlet" method="post">



                            <strong>Người Tạo:</strong> <span style="color: green">${sessionScope.username}</span>
                            <br><br>




                            <table>
                                <tr>
                                    <td><label for="orderType">Loại Hóa Đơn:</label></td>
                                    <td>
                                        <select id="orderType" name="orderType">
                                            <option value="export">Xuất Kho</option>
                                            <option value="import">Nhập Kho</option>
                                        </select>
                                    </td>
                                </tr>

                                <tr>
                                    <td><label>Trạng thái đơn hàng:</label></td>
                                    <td>
                                        <input type="radio" id="paid" name="orderStatus" value="paid" onclick="togglePaymentFields()" checked>
                                        <label for="paid">Đã Thanh Toán Đủ</label>

                                        <input type="radio" id="partial" name="orderStatus" value="partial" onclick="togglePaymentFields()">
                                        <label for="partial">Thanh Toán Thiếu</label>

                                        <input type="radio" id="unpaid" name="orderStatus" value="unpaid" onclick="togglePaymentFields()">
                                        <label for="unpaid">Chưa Thanh Toán</label>
                                    </td>
                                </tr>

                                <tr id="paidAmountRow" style="display: none;">
                                    <td><label for="paidAmount">Số tiền đã trả:</label></td>
                                    <td><input type="number" id="paidAmount" name="paidAmount" oninput="calculateDebt()"></td>
                                </tr>

                                <tr id="debtRow" style="display: none;">
                                    <td><label for="debtAmount">Số tiền còn nợ:</label></td>
                                    <td><input type="number" id="debtAmount" name="debtAmount" readonly></td>
                                </tr>


                                <tr>
                                    <td><label for="orderDate">Ngày tạo đơn:</label></td>
                                    <td><input type="date" id="orderDate" name="orderDate" readonly></td>
                                </tr>

                                <tr>
                                    <td><label for="customerID">Khách Hàng:</label></td>
                                    <td>
                                        <select id="customerID" name="customerId" required>
                                            <c:if test="${not empty customers}">
                                                <c:forEach var="customer" items="${customers}">
                                                    <option value="${customer.customerID}" ${customer.customerID == param.selectedCustomerId ? 'selected' : ''}>
                                                        ${customer.name} - ${customer.phone}
                                                    </option>
                                                </c:forEach>
                                            </c:if>
                                        </select>
                                    </td>
                                </tr>

                                <c:if test="${id == 0 && empty customers}">
                                    <tr>
                                        <td colspan="2" style="color: red; font-style: italic; text-align: center; padding: 8px;">Không tìm thấy khách hàng</td>
                                    </tr>
                                </c:if>



                                <tr>
                                    <td><label for="porter">Số Người Bốc Vác:</label></td>
                                    <td>
                                        <input type="number" id="porter" name="porter" required min="0"
                                               >
                                    </td>
                                </tr>

                                <tr>
                                    <td><label for="status">Trạng Thái:</label></td>
                                    <td>
                                        <textarea id="status" name="status" rows="4" cols="40" required 
                                                  ></textarea>
                                    </td>
                                </tr>
                            </table>





                            <br><br>

                            <h3 style="color: red">Chi Tiết Đơn Hàng</h3>



                            <table id="orderItems">
                                <thead>
                                    <tr>
                                        <th>Sản Phẩm</th>
                                        <th>Đơn vị</th>
                                        <th>Số Lượng</th>
                                        <th>Đơn Giá</th>
                                        <th>Giảm Giá(Kg)</th>
                                        <th>Thành Tiền</th>
                                        <th>Chú Thích</th>
                                        <th>Chọn</th>
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


                            <p id="orderStatus"></p>
                            <script>

                                document.getElementById("paidAmount").addEventListener("input", function () {
                                    if (this.value < 0) {
                                        this.value = 0;
                                    }
                                });
                                document.getElementById("porter").addEventListener("input", function () {
                                    if (this.value < 0) {
                                        this.value = 0;
                                    }
                                });

                            </script>

                            <script>




                                function togglePaymentFields() {
                                    var isPartial = document.getElementById("partial").checked;
                                    // trả 1 phần
                                    var isUnpaid = document.getElementById("unpaid").checked;
                                    // chưa trả xíu nào
                                    var paidAmountRow = document.getElementById("paidAmountRow");
                                    // số tiền đã trả
                                    var debtRow = document.getElementById("debtRow");
                                    // số tiền còn nợ
                                    var paidAmountInput = document.getElementById("paidAmount");
                                    var debtAmountInput = document.getElementById("debtAmount");

                                    if (isPartial) {
                                        // nếu chọn trả 1 phần, hiển thị ô nhập số tiền đã trả và số tiền còn nợ


                                        paidAmountRow.style.display = "table-row";
                                        debtRow.style.display = "table-row";
                                        paidAmountInput.required = true;// yêu cầu nhập số tiền đã trả
                                        calculateDebt();// gọi hàm để tình toán số tiền còn nợ dựa trên đã trả bao nhiêu
                                    } else {
                                        paidAmountRow.style.display = "none";
                                        debtRow.style.display = "none";
                                        paidAmountInput.required = false;
                                        paidAmountInput.value = "";
                                        debtAmountInput.value = "";
                                    }
                                }

                                function calculateDebt() {
                                    var totalOrderPrice = parseFloat(document.getElementById("totalOrderPrice").value) || 0;
                                    // lấy giá trị tổng đơn hàng
                                    var paidAmount = parseFloat(document.getElementById("paidAmount").value) || 0;
                                    //Lấy số tiền đã trả (paidAmount). Nếu chưa nhập, mặc định là 0
                                    var debtAmountInput = document.getElementById("debtAmount");
                                    //Lấy ô hiển thị số tiền còn nợ (debtAmount)

                                    var debt = totalOrderPrice - paidAmount;
                                    debtAmountInput.value = debt > 0 ? debt.toFixed(2) : 0;

                                    //Tính số tiền còn nợ:
//Nếu số tiền còn nợ (debt) lớn hơn 0, hiển thị giá trị.
//Nếu không, đặt giá trị về 0 (không có nợ).
                                }

                            </script>

                            <script>


                                document.addEventListener("DOMContentLoaded", function () {
                                    loadPaymentData(); // Tải lại dữ liệu khi load trang

                                    // Lắng nghe sự kiện thay đổi trên radio button
                                    var statusRadios = document.querySelectorAll('input[name="orderStatus"]');
                                    statusRadios.forEach(radio => {
                                        radio.addEventListener("change", savePaymentData);
                                    });

                                    // Lắng nghe sự kiện nhập trên ô số tiền đã trả
                                    document.getElementById("paidAmount").addEventListener("input", function () {
                                        calculateDebt();
                                        savePaymentData();
                                    });
                                });

                                // Hàm lưu dữ liệu vào sessionStorage
                                function savePaymentData() {
                                    var orderStatus = document.querySelector('input[name="orderStatus"]:checked').value;
                                    var paidAmount = document.getElementById("paidAmount").value;
                                    var debtAmount = document.getElementById("debtAmount").value;

                                    sessionStorage.setItem("orderStatus", orderStatus);
                                    sessionStorage.setItem("paidAmount", paidAmount);
                                    sessionStorage.setItem("debtAmount", debtAmount);
                                }

// Hàm tải lại dữ liệu từ sessionStorage
                                function loadPaymentData() {
                                    var orderStatus = sessionStorage.getItem("orderStatus");
                                    var paidAmount = sessionStorage.getItem("paidAmount");
                                    var debtAmount = sessionStorage.getItem("debtAmount");
                                    // Nếu đã lưu orderStaus thì tự động chọn lại
                                    if (orderStatus) {
                                        document.getElementById(orderStatus).checked = true;
                                    }

                                    if (orderStatus === "partial") {
                                        document.getElementById("paidAmountRow").style.display = "table-row";
                                        document.getElementById("debtRow").style.display = "table-row";
                                        document.getElementById("paidAmount").value = paidAmount || "";
                                        document.getElementById("debtAmount").value = debtAmount || "";
                                    } else {
                                        document.getElementById("paidAmountRow").style.display = "none";
                                        document.getElementById("debtRow").style.display = "none";
                                    }
                                }
                            </script>



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
                            <script>
                                document.addEventListener("DOMContentLoaded", function () {
                                    let status = document.getElementById("status");
                                    let savedStatus = sessionStorage.getItem("inputedStatus");

                                    // Kiểm tra nếu có giá trị đã lưu trong sessionStorage
                                    if (savedStatus) {
                                        status.value = savedStatus;
                                    }

                                    // Lắng nghe sự kiện input khi người dùng thay đổi giá trị
                                    status.addEventListener("input", function () {
                                        // Lưu giá trị vào sessionStorage
                                        sessionStorage.setItem("inputedStatus", status.value);
                                    });
                                });
                            </script>

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
                                function addProductToOrder(productID, productName, pricePerKg, availableQuantity) {
                                    var orderType = document.getElementById("orderType").value; // Lấy loại hóa đơn

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

                                    if (orderType === "import") {
                                        // Nếu nhập kho, giá có thể thay đổi
                                        cell3.innerHTML = '<input type="number" name="quantity" class="quantity" required value="1" min="1">';


                                        cell4.innerHTML = '<input type="number" name="unitPrice" class="unitPrice" value="' + pricePerKg + '">';

                                    } else {
                                        cell3.innerHTML = '<input type="number" name="quantity" class="quantity" required value="1" min="1" max="' + availableQuantity + '">';

                                        // Nếu xuất kho, giá cố định
                                        cell4.innerHTML = '<input type="number" name="unitPrice" class="unitPrice" value="' + pricePerKg + '"  readonly>';
                                    }


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

                                    //không cho phép nhập âm số lượng và giảm giá



                                    // Gắn sự kiện tính toán tổng tiền
                                    var quantityInput = cell3.querySelector('.quantity');

                                    // Không cho nhập số âm
                                    quantityInput.addEventListener("input", function () {
                                        if (this.value < 0) {
                                            this.value = 0; // Đặt giá trị tối thiểu là 1
                                        }
                                    });

                                    var unitTypeInput = cell2.querySelector('.unitType');
                                    var discountInput = cell5.querySelector('.discount');

                                    // Không cho nhập số âm
                                    discountInput.addEventListener("input", function () {
                                        if (this.value < 0) {
                                            this.value = 0; // Đặt giá trị tối thiểu là 1
                                        }
                                    });
                                    var totalPriceInput = cell6.querySelector('.totalPrice');
                                    var totalWeightInput = cell9.querySelector('.totalWeight'); // Lấy input ẩn totalWeight
                                    function recalculate() {
                                        var unitMultiplier = parseInt(unitTypeInput.value); // 1kg hoặc bao 10kg, 20kg, 50kg
                                        var quantity = parseFloat(quantityInput.value) || 1;



                                        var totalWeight = quantity * unitMultiplier; // Tổng số kg mua
                                        if (orderType === "export" && totalWeight > availableQuantity) {
                                            alert("Số lượng sản phẩm vượt quá tồn kho! Vui lòng nhập lại.");
                                            quantityInput.value = Math.floor(availableQuantity / unitMultiplier); // Tự động điều chỉnh số lượng phù hợp
                                            totalWeight = quantityInput.value * unitMultiplier; // Cập nhật lại tổng khối lượng
                                        }

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

                        <!--  
                        Tải thư viện jQuery phiên bản 3.6.0 từ CDN.
Giúp sử dụng các hàm AJAX, chọn phần tử DOM, xử lý sự kiện dễ dàng hơn.
                        -->

                        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                        <script>
                            $(document).ready(function () {
    $("#orderForm").submit(function (event) {
        event.preventDefault(); // Ngăn chặn việc tải lại trang
        
        $("#orderStatus").text("⏳ Đơn hàng đang xử lý..."); // Hiển thị trạng thái ngay lập tức
        
        $.ajax({
            url: "CreateOrderServlet",
            type: "POST",
            data: $(this).serialize(), // Gửi dữ liệu form bằng AJAX
            dataType: "json",
            success: function (response) {
                if (response.status === "processing") {
                    checkOrderStatus(); // Kiểm tra trạng thái đơn hàng
                } else if (response.status === "error") {
                    $("#orderStatus").html("<span style='color: red;'>❌ " + response.message + "</span>");
                }
            },
           
        });
    });

    var retryCount = 0;
    function checkOrderStatus() {
        if (retryCount >= 5) {
            $("#orderStatus").html("<span style='color: red;'>❌ Quá thời gian xử lý, vui lòng thử lại!</span>");
            return;
        }

        var userId = ${sessionScope.userID}; // Đảm bảo lấy đúng userID từ session

        $.ajax({
            url: "CheckOrderStatusServlet",
            type: "GET",
            data: { userId: userId },
            dataType: "json",
            success: function (response) {
                if (response.status === "done") {
                    $("#orderStatus").text("✅ Tạo đơn hàng thành công!");
                } else {
                    retryCount++;
                    setTimeout(checkOrderStatus, 2000); // Tiếp tục kiểm tra sau 2 giây
                }
            },
            
        });
    }
});

                        </script>










                    </div>


                </div>
            </div>
        </div>


    </body>






</html>
