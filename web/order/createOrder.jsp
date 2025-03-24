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
        <% 
     String invoiceNumber = (String) request.getAttribute("invoiceNumber");
        %>

        <title>Hóa đơn <%= invoiceNumber %></title>



        <style>
            :root {
                --primary-color: #4CAF50;
                --primary-hover: #45a049;

                --darker-bg: #222222;
                --border-color: #444;
                --text-color: #e0e0e0;
                --header-bg: #333;
            }

            body {

                font-family: Arial, sans-serif;
                background-color: #1e1e1e;
                color: var(--text-color);
            }





            .order-container {
                display: flex;
                gap: 20px;
            }

            .left-panel, .right-panel {
                background-color: var(--dark-bg);
                border-radius: 5px;
                padding: 15px;
                box-shadow: 0 0 10px rgba(255, 255, 255, 0.1);
            }

            .left-panel {
                flex: 3;
            }

            .right-panel {
                flex: 2;
            }

            /* Form elements */
            input[type="text"],
            input[type="number"],
            input[type="date"],
            select,
            textarea {
                padding: 8px;
                border: 1px solid var(--border-color);
                border-radius: 4px;
                background-color: #333;
                color: var(--text-color);
                font-size: 14px;
                width: 100%;
                box-sizing: border-box;
                margin: 5px 0;
            }

            button {
                padding: 8px 16px;
                border: none;
                border-radius: 4px;
                background-color: var(--primary-color);
                color: white;
                font-size: 14px;
                cursor: pointer;
                margin: 5px 0;
            }

            button:hover {
                background-color: var(--primary-hover);
            }



            /* Tables */
            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 15px;
                background-color: var(--dark-bg);
                border-radius: 5px;
                overflow: hidden;
            }

            th, td {
                border: 1px solid var(--border-color);
                padding: 8px;
                text-align: left;
            }

            th {
                background-color: var(--header-bg);
                color: #fff;
                position: sticky;
                top: 0;
            }


            /* Order details and items */
            .order-items-container {
                margin-top: 15px;
            }

            #orderItems input {
                text-align: center;
            }
            #orderItems th:nth-child(3),
            #orderItems td:nth-child(3) {
                width: 7px; /* Điều chỉnh độ rộng theo ý muốn */
                min-width: 70px;
                text-align: center;
            }
            #orderItems th:nth-child(4),
            #orderItems td:nth-child(4) {
                width: 60px; /* Giảm chiều rộng cột */
                min-width: 60px;
                text-align: center;
            }





            /* Order summary */
            .order-summary {
                background-color: #333;
                padding: 10px;
                border-radius: 5px;
                margin-top: 15px;
            }

            .summary-row {
                display: flex;
                justify-content: space-between;
                margin: 8px 0;
            }

            .summary-row strong {
                font-weight: bold;
            }

            .grand-total {
                font-size: 1.2em;
                font-weight: bold;
                color: #4CAF50;
            }

            /* Payment section */
            .payment-options {
                margin: 15px 0;
            }

            .radio-group {
                display: flex;
                gap: 10px;
                margin: 10px 0;
            }

            /* Notes area */
            .order-notes {
                margin-top: 15px;
            }

            /* Responsive styles */
            @media (max-width: 1024px) {
                .order-container {
                    flex-direction: column;
                }
            }

            .info-row {
                display: flex;
                justify-content: space-between;
                margin-bottom: 8px;
                align-items: center;
            }

            .info-label {
                font-weight: bold;
                min-width: 150px;
            }

            .info-value {
                flex: 1;
            }

            .success-text {
                color: #4CAF50;
            }

            .waiting-text {
                color: #FFC107;
            }

            .error-text {
                color: #F44336;
            }

            /* Buttons for submit/actions */
            .action-buttons {
                display: flex;
                gap: 10px;
                margin-top: 20px;
            }

            .primary-btn {
                flex: 1;
                padding: 12px;
                font-size: 16px;
                background-color: #2196F3;
            }

            .print-btn {
                background-color: #9C27B0;
            }













            /* Search Input */
            .search-box input[type="text"] {
                width: 100%;
                padding: 12px 14px;
                font-size: 13px;
                border: 1px solid #ddd;
                border-radius: 8px;
                outline: none;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
                transition: all 0.3s ease;
            }

            .search-box input[type="text"]:focus {
                border-color: #4a90e2;
                box-shadow: 0 2px 8px rgba(74, 144, 226, 0.2);
            }

            .search-box input[type="text"]::placeholder {
                color: #aaa;
            }



            .main-content{
                font-size: 12px;
            }

            /* Container chứa danh sách gợi ý */
            .search-suggestions {
                max-height: 200px;
                overflow-y: auto;
                overflow-x: hidden;
                scrollbar-width: none;
                padding: 5px;
            }

            /* Mỗi sản phẩm gợi ý */
            .product-item {
                display: flex;
                align-items: center;


                background-color: var(--darker-bg);
                border: 1px solid var(--border-color);
                border-radius: 5px;
                margin-bottom: 3px;

                cursor: pointer;
            }

            /* Khi hover */
            .product-item:hover {
                background-color: var(--primary-hover);
                transform: scale(1.02);
                transition: all 0.3s ease;
            }


            /* Ảnh sản phẩm */
            .product-image img {
                width: 50px;
                height: 50px;
                border-radius: 5px;
                object-fit: cover;
            }

            /* Container chứa thông tin sản phẩm */
            .product-content {
                display: flex;
                flex-direction: column;
                flex: 1;
                margin-left: 25px;
            }

            /* Hàng chứa tên, số lượng, giá */
            .product-info {
                display: flex;
                align-items: center;
                justify-content: space-between;
                gap: 10px;
                width: 100%;
            }

            /* Tên sản phẩm */
            .product-name {
                font-size: 12px;
                font-weight: bold;
                flex: 2;
                min-width: 120px;
            }

            /* Số lượng */
            .product-quantity {
                font-size: 12px;
                color: var(--text-color);
                flex: 1;
                text-align: center;
            }

            /* Giá sản phẩm */
            .product-price {
                font-size: 12px;
                color: var(--text-color);
                flex: 1;
                text-align: center;
            }

            /* Mô tả sản phẩm */
            .product-description {
                font-size: 12px;
                color: #aaa; /* Màu xám nhạt */
                margin-top: 5px;
                flex: 3;
                text-align: left;
                padding-left: 10px;
            }

            /* Danh sách gợi ý */
            .search-suggestions {
                max-height: 180px; /* Giới hạn chiều cao */
                overflow-y: auto;
                overflow-x: hidden;
                padding: 5px;
                width: 100%; /* Đảm bảo bằng với ô tìm kiếm */
            }

            /* Mỗi khách hàng gợi ý */
            .customer-item {
                display: flex;
                align-items: center;

                padding: 6px 8px;
                cursor: pointer;
                transition: background-color 0.2s ease-in-out;
            }

            /* Khi hover */
            .customer-item:hover {
                background-color: var(--primary-hover);
                transform: scale(1.02);
                transition: all 0.3s ease;
            }

            /* Tên khách hàng */
            .customer-item h3 {
                font-size: 15px;
                font-weight: bold;
                margin: 0;
                color:white;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
                flex: 1;
            }

            /* Số điện thoại */
            .customer-item p {
                font-size: 14px;
                color: var(--text-color);
                margin: 0;
                font-weight: 500;
                white-space: nowrap;
            }

            .popup {
                display: none;
                position: fixed;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background: black;
                color: white;
                padding: 15px;
                border-radius: 5px;
                width: 280px;
                text-align: center;
            }

            .popup input {
                width: 100%;
                padding: 8px;
                margin: 5px 0;
                background: black;
                color: white;
                border: 1px solid white;
            }

            .popup button {
                background: #007bff;
                color: white;
                border: none;
                padding: 8px;
                cursor: pointer;
            }

            .close {
                position: absolute;
                top: 5px;
                right: 10px;
                cursor: pointer;
            }




        </style>


    </head>

    <body>
        <div id="main">
            <jsp:include page="/Component/header.jsp"></jsp:include>
            <div class="menu ">  <jsp:include page="/Component/menu.jsp"></jsp:include> </div>





                <div class="main-content">


                    <button onclick="openNewTab()">Thêm hóa đơn</button>

                    <script>
                        // Kiểm tra số hóa đơn hiện tại trong localStorage
                        let invoiceCount = localStorage.getItem("invoiceCount");

                        if (!invoiceCount) {
                            localStorage.setItem("invoiceCount", 1); // Nếu chưa có, đặt là 1
                        }

                        function openNewTab() {
                            let invoiceNumber = parseInt(localStorage.getItem("invoiceCount")) + 1;
                            localStorage.setItem("invoiceCount", invoiceNumber);

                            window.open('CreateOrderServlet?invoice=' + invoiceNumber, '_blank');
                        }
                    </script>





                    <h1 style="text-align: center">
                        Hóa Đơn Xuất
                    </h1>


                    <form id="orderForm" action="CreateOrderServlet" method="post">





                        <div class="order-container">

                            <!-- LEFT PANEL: Product search, order items, notes -->
                            <div class="left-panel">


                                <!-- Product search -->


                                <div class="search-box">

                                    <input type="text" id="search" placeholder="Nhập tên sản phẩm..." >


                                    <div id="suggestions" class="search-suggestions"></div>
                                </div>




                                <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                                <script>
                        $(document).ready(function () {
                            let currentLoad;
                            $("#search").on("input", function () {
                                if (currentLoad) {
                                    console.log("Clear timeout");
                                    clearTimeout(currentLoad);
                                }

                                let that = this;
                                currentLoad = setTimeout(function () {
                                    console.log("fetch customer");
                                    let query = $(that).val();
                                    if (query.length > 0) {
                                        $.ajax({
                                            url: "SearchServlet",
                                            type: "GET",
                                            data: {
                                                searchProduct: query,
                                                orderType: 1  // ✅ Đúng cú pháp
                                            },
                                            success: function (data) {
                                                if (data.trim() !== "") {
                                                    $("#suggestions").html(data).show();
                                                } else {
                                                    $("#suggestions").hide();
                                                }
                                            }
                                        });
                                    } else {
                                        $("#suggestions").hide();
                                    }
                                    currentLoad = null;
                                }, 500);
                            });
                            // Ẩn danh sách khi click ra ngoài
                            $(document).on("click", function (event) {
                                if (!$(event.target).closest("#search, #suggestions").length) {
                                    $("#suggestions").hide();
                                }
                            });
                            // Hiển thị lại danh sách khi focus vào ô tìm kiếm (nếu có nội dung)
                            $("#search").on("focus", function () {
                                if ($(this).val().length > 0) {
                                    $("#suggestions").show();
                                }
                            });
                        });
                                </script>










                                <input type="hidden" name="orderType" value="1"><!-- xuất -->

                                <!-- Order items table -->
                                <div class="order-items-container">
                                    <h3>Chi Tiết Đơn Hàng</h3>
                                    <table id="orderItems">
                                        <thead>
                                            <tr>
                                                <th>Sản Phẩm</th>
                                                <th>Số Lượng</th>
                                                <th>Đơn vị</th>
                                                <th>Khối Lượng(Kg)</th>
                                                <th>Đơn Giá/Kg</th>
                                                <th>Giảm Giá/Kg</th>
                                                <th>Thành Tiền</th>

                                                <th>Xóa</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <!-- Order items will be added here -->
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <td colspan="4" style="text-align: right;"><strong>Tổng tiền:</strong></td>

                                                <td colspan="4">
                                                    <input type="hidden" id="totalOrderPriceHidden" name="totalOrderPriceHidden">
                                                    <input type="text" id="totalOrderPrice" name="totalOrderPrice" readonly>
                                                </td>

                                            </tr>
                                            <tr>
                                                <td colspan="4" style="text-align: right;"><strong>Tổng tiền đã giảm:</strong></td>
                                                <td colspan="4">
                                                    <input type="hidden" id="totalDiscount" name="totalDiscount">
                                                    <input type="text" id="totalDiscountHidden" name="totalDiscountHidden"readonly >
                                                </td>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>

                                <!-- Order notes -->
                                <div class="order-notes">
                                    <h3>Ghi Chú Đơn Hàng</h3>
                                    <textarea id="status" name="status" rows="4" placeholder="Nhập ghi chú cho đơn hàng..."></textarea>
                                </div>
                            </div>

                            <!-- RIGHT PANEL: Order info, customer, payment -->
                            <div class="right-panel">
                                <h2>Thông Tin Hóa Đơn</h2>







                                <!-- Customer search -->


                                <div class="search-box">

                                    <input type="text" id="searchCustomerInput"  placeholder="Tìm khách hàng theo SĐT..." >


                                    <div id="suggestionsCustomer" class="search-suggestions"></div>


                                </div>

                                <!-- Ô hiển thị thông tin khách hàng sau khi chọn -->
                                <div id="customerInfo" class="customer-info" style="display: none;">
                                    <h3 class="info-value success-text" id="customerName"></h3>
                                    <p id="customerPhone"></p>
                                    <p id="customerDebt"></p>
                                    <input type="hidden" id="customerId" name="customerId">

                                </div>

                                <script>
                                    $(document).ready(function () {
                                        let currentLoad;
                                        $("#searchCustomerInput").on("input", function () {
                                            if (currentLoad) {
                                                console.log("Clear timeout");
                                                clearTimeout(currentLoad);
                                            }
                                            let that = this;
                                            currentLoad = setTimeout(function () {
                                                console.log("fetch customer");
                                                let query = $(that).val();
                                                if (query.length > 0) {
                                                    $.ajax({
                                                        url: "SearchServlet",
                                                        type: "POST",
                                                        data: {keyword: query},
                                                        success: function (data) {
                                                            if (data.trim() !== "") {
                                                                $("#suggestionsCustomer").html(data).show();
                                                            } else {
                                                                $("#suggestionsCustomer").hide();
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    $("#suggestionsCustomer").hide();
                                                }
                                                currentLoad = null;
                                            }, 500);
                                        });
                                        // Ẩn danh sách khi click ra ngoài
                                        $(document).on("click", function (event) {
                                            if (!$(event.target).closest("#searchCustomerInput, #suggestionsCustomer").length) {
                                                $("#suggestionsCustomer").hide();
                                            }
                                        });
                                        // Hiển thị lại danh sách khi focus vào ô tìm kiếm (nếu có nội dung)
                                        $("#searchCustomerInput").on("focus", function () {
                                            if ($(this).val().length > 0) {
                                                $("#suggestionsCustomer").show();
                                            }
                                        });
                                    });
                                    // Hàm chọn khách hàng từ danh sách
                                    function selectCustomer(id, name, phone, debt) {
                                        $("#customerId").val(id);
                                        $("#customerName").text("Khách hàng: " + name);

                                        // Ẩn 3 số cuối số điện thoại
                                        let maskedPhone = phone.slice(0, -3) + "***";
                                        $("#customerPhone").text("SĐT: " + maskedPhone);

                                        // Xác định cách hiển thị tổng nợ
                                        if (debt < 0) {
                                            $("#customerDebt").text("Khách nợ: " + formatNumberVND(-debt));
                                        } else if (debt > 0) {
                                            $("#customerDebt").text("Nợ Khách: " + formatNumberVND(debt));
                                        } else {
                                            $("#customerDebt").text("Tổng nợ: " + formatNumberVND(debt));
                                        }

                                        $("#customerInfo").show(); // Hiển thị div chứa thông tin khách hàng
                                    }



                                </script>




                                <script>
                                    function calculateBalance() {
                                        let totalOrderPrice = parseInt($("#totalOrderPriceHidden").val()) || 0;
                                        let paidAmount = parseInt($("#paidAmount").val()) || 0;
                                        if (isNaN(paidAmount) || paidAmount < 0) {
                                            paidAmount = 0;
                                            $("#paidAmount").val(0); // Cập nhật lại giá trị hiển thị
                                        }
                                        let balance = paidAmount - totalOrderPrice;

                                        let $balanceOptions = $("#balanceOptions");
                                        let $excessOption = $("#excessOption");
                                        let $debtOption = $("#debtOption");




                                        $("#balanceAmount").val(balance);
                                        $("#balanceAmountHidden").val(formatNumberVND(balance));




                                        if (balance > 0) {
                                            // Khách trả dư
                                            $excessOption.show();
                                            $debtOption.show();
                                            $balanceOptions.show();
                                            autoSelectOption("refund");
                                        } else if (balance < 0) {
                                            // Khách trả thiếu
                                            $excessOption.hide();
                                            $debtOption.show();
                                            $balanceOptions.show();
                                            autoSelectOption("debt");
                                        } else {
                                            // Thanh toán đúng số tiền
                                            $balanceOptions.hide();
                                        }

                                    }
                                    function autoSelectOption(value) {
                                        $("input[name='balanceAction'][value='" + value + "']").prop("checked", true);
                                    }
                                </script>

                                <div class="payment-options">

                                    <div class="info-row">
                                        <span class="info-label">Khách Thanh Toán:</span>
                                        <span class="info-value">
                                            <input type="number" id="paidAmount" name="paidAmount"oninput="calculateBalance()">
                                        </span>
                                    </div>

                                    <div class="info-row">
                                        <span class="info-label">Số tiền còn lại:</span>
                                        <span class="info-value">
                                            <input type="hidden" id="balanceAmount" name="balanceAmount">
                                            <input type="text" id="balanceAmountHidden" name="balanceAmountHidden" readonly>
                                        </span>
                                    </div>

                                    <div id="balanceOptions" style="display: none;">
                                        <div id="excessOption" style="display: none;">
                                            <label><input type="radio" name="balanceAction" value="refund"> Tiền thừa trả khách</label>
                                        </div>
                                        <div id="debtOption" style="display: none;">
                                            <label><input type="radio" name="balanceAction" value="debt"> Tính vào công nợ</label>
                                        </div>
                                    </div>
                                </div>



                                <script>


                                    $(document).ready(function () {
                                        $("#submitOrder").on("click", function (event) {
                                            if ($("#customerId").val() === "") {
                                                event.preventDefault(); // Ngăn form hoặc hành động tiếp theo
                                                alert("⚠️ Vui lòng chọn khách hàng trước khi tạo đơn hàng!");
                                            }
                                            let paidAmount = $("#paidAmount").val().trim();
                                            // Kiểm tra đã nhập số tiền thanh toán chưa
                                            if (!paidAmount || isNaN(parseFloat(paidAmount))) {
                                                event.preventDefault();
                                                alert("⚠️ Vui lòng nhập số tiền khách thanh toán!");
                                                return;
                                            }

                                            let selectedOption = $("input[name='balanceAction']:checked").val();
                                            // Kiểm tra nếu có tiền dư/thừa, phải chọn phương án xử lý
                                            if ($("#balanceOptions").is(":visible") && !selectedOption) {
                                                event.preventDefault();
                                                alert("⚠️ Vui lòng chọn cách xử lý số tiền còn lại!");
                                                return;
                                            }

                                        });
                                    });

                                </script>


                                <!-- Action buttons -->
                                <div class="action-buttons">

                                    <button type="submit" id="submitOrder">Tạo Hóa Đơn</button>
                                    <button id="printOrderBtn" type="button" class="print-btn">Thanh toán bằng VNPAY</button>
                                </div>

                                <p id="orderStatus"></p>
                            </div>

                        </div>


                        <script>
                            document.getElementById("printOrderBtn").addEventListener("click", function () {
                                // Lấy giá trị từ input paidAmount
                                let paidAmountValue = document.getElementById("paidAmount").value || 0;

                                // Chuyển đổi sang số nguyên để đảm bảo đúng định dạng
                                let amountValue = parseInt(paidAmountValue, 10) || 0;

                                // Tạo một form ẩn
                                let form = document.createElement("form");
                                form.method = "POST";
                                form.action = "vnpay_payment";

                                // Thêm input hidden cho số tiền thanh toán
                                let amountInput = document.createElement("input");
                                amountInput.type = "hidden";
                                amountInput.name = "amount";
                                amountInput.value = amountValue; // Gán giá trị từ paidAmount

                                // Thêm input hidden cho orderId
                                let orderIdInput = document.createElement("input");
                                orderIdInput.type = "hidden";
                                orderIdInput.name = "orderId";
                                orderIdInput.value = "1";

                                // Gắn input vào form
                                form.appendChild(amountInput);
                                form.appendChild(orderIdInput);
                                document.body.appendChild(form);

                                // Submit form
                                form.submit();
                            });

                        </script>




                        <script>
                            function addProductToOrder(productID, productName, pricePerKg, availableQuantity, unitSizes) {



                                // Kiểm tra nếu sản phẩm hết hàng
                                if (availableQuantity <= 0) {
                                    alert("Sản phẩm đã hết hàng! Không thể thêm vào đơn hàng.");
                                    return; // Dừng hàm nếu sản phẩm không còn hàng
                                }

                                var table = document.getElementById("orderItems").getElementsByTagName('tbody')[0];
                                // Nếu sản phẩm chưa có, thêm dòng mới

                                var newRow = table.insertRow(table.rows.length);
                                var cell1 = newRow.insertCell(0);
                                var cell2 = newRow.insertCell(1);
                                var cell3 = newRow.insertCell(2);
                                var cell4 = newRow.insertCell(3);
                                var cell5 = newRow.insertCell(4);
                                var cell6 = newRow.insertCell(5);
                                var cell7 = newRow.insertCell(6);
                                var cell8 = newRow.insertCell(7);
                                // Hiển thị tên sản phẩm
                                cell1.innerHTML =
                                        '<input type="hidden" name="productID" value="' + productID + '">' +
                                        '<input type="hidden" name="productName" value="' + productName + '">' +
                                        productName;
                                // Chọn đơn vị tính: kg hoặc bao
                                // Tạo dropdown từ danh sách unitSizes
                                var unitSelectHTML = '<select name="unitType" class="unitType">';
                                if (unitSizes && unitSizes.length > 0) {
                                    unitSizes.forEach(size => {
                                        unitSelectHTML += '<option value="' + size + '">' + (size === 1 ? "Kg" : "Bao (" + size + "kg)") + '</option>';
                                    });
                                } else {
                                    unitSelectHTML += '<option value="1">Kg</option>'; // Mặc định nếu không có dữ liệu
                                }
                                unitSelectHTML += '</select>';
                                cell3.innerHTML = unitSelectHTML;
                                cell2.innerHTML = '<input type="number" name="quantity" class="quantity" required value="1" min="1" max="' + availableQuantity + '">';
                                // Nếu xuất kho, giá cố định
                                cell5.innerHTML = '<input type="hidden" name="unitPriceHidden" class="unitPriceHidden" value="' + pricePerKg + '">' +
                                        '<input type="text" name="unitPrice" class="unitPrice" value="' + formatNumberVND(pricePerKg) + '" readonly>';
                                // Giảm giá (VND/kg)
                                cell6.innerHTML = '<input type="number" name="discount" class="discount" value="0" min="0">';
                                cell7.innerHTML =
                                        '<input type="hidden" name="totalPriceHidden" class="totalPriceHidden">' +
                                        '<input type="text" name="totalPrice" class="totalPrice" readonly>';
                                // Nút xóa
                                cell8.innerHTML = '<button type="button" onclick="deleteRow(this)">Xóa</button>';
                                // Thêm input ẩn để lưu totalWeight
                                cell4.innerHTML = '<input type="number" name="totalWeight" class="totalWeight" readonly >';
                                //không cho phép nhập âm số lượng và giảm giá

                               



                                var quantityInput = cell2.querySelector('.quantity');
                                // Không cho nhập số âm
                                quantityInput.addEventListener("input", function () {
                                    if (this.value < 0) {
                                        this.value = 1; // Đặt giá trị tối thiểu là 1
                                    }
                                    recalculateRow(newRow, pricePerKg, availableQuantity);
                                });
                                var unitTypeInput = cell3.querySelector('.unitType');
                                var discountInput = cell6.querySelector('.discount');
                                // Không cho nhập số âm
                                discountInput.addEventListener("input", function () {
                                    if (this.value < 0) {
                                        this.value = 0; // Đặt giá trị tối thiểu là 1
                                    }
                                     if (parseFloat(this.value) > pricePerKg) {
                                        alert("Giảm giá không được vượt quá giá của 1 kg gạo!");
                                        this.value = pricePerKg; // Đặt lại giá trị bằng giá gốc nếu vượt quá
                                    }
                                    recalculateRow(newRow, pricePerKg, availableQuantity);
                                });
                                unitTypeInput.addEventListener('change', () => recalculateRow(newRow, pricePerKg, availableQuantity));
                                discountInput.addEventListener('input', () => recalculateRow(newRow, pricePerKg, availableQuantity));
                                recalculateRow(newRow, pricePerKg, availableQuantity);
                            }
                            function recalculateRow(row, pricePerKg, availableQuantity) {

                                var quantityInput = row.querySelector(".quantity");
                                var unitTypeInput = row.querySelector(".unitType");
                                var discountInput = row.querySelector(".discount");
                                var totalPriceInput = row.querySelector(".totalPrice");
                                var totalPriceHidden = row.querySelector(".totalPriceHidden");
                                var totalWeightInput = row.querySelector(".totalWeight");
                                var unitMultiplier = parseInt(unitTypeInput.value);
                                var quantity = parseInt(quantityInput.value);
                                var totalWeight = quantity * unitMultiplier;
                                if (totalWeight > availableQuantity) {
                                    alert("Số lượng sản phẩm vượt quá tồn kho! Vui lòng nhập lại.");
                                    quantityInput.value = Math.floor(availableQuantity / unitMultiplier); // Tự động điều chỉnh số lượng phù hợp
                                    totalWeight = quantityInput.value * unitMultiplier; // Cập nhật lại tổng khối lượng
                                }



                                var discount = parseInt(discountInput.value) || 0;
                                var totalDiscount = totalWeight * discount;
                                var totalPrice = (totalWeight * pricePerKg) - totalDiscount;
                                totalWeightInput.value = totalWeight;
                                totalPriceHidden.value = totalPrice; // Lưu giá trị số
                                totalPriceInput.value = formatNumberVND(totalPrice);
                                updateTotalOrderPrice();
                            }









                            function deleteRow(button) {
                                var row = button.parentNode.parentNode;
                                row.parentNode.removeChild(row);
                                updateTotalOrderPrice();
                            }

                            function updateTotalOrderPrice() {
                                var total = 0;
                                var totalDiscount = 0;
                                var totalPriceInputs = document.querySelectorAll(".totalPriceHidden");
                                var discountInputs = document.querySelectorAll(".discount");
                                var quantityInputs = document.querySelectorAll(".quantity");
                                var unitTypeInputs = document.querySelectorAll(".unitType");
                                totalPriceInputs.forEach((input, index) => {
                                    total += parseInt(input.value) || 0;
                                    var discount = parseInt(discountInputs[index].value) || 0;
                                    var quantity = parseInt(quantityInputs[index].value);
                                    var unitMultiplier = parseInt(unitTypeInputs[index].value); // 1kg hoặc bao 10kg, 20kg, 50kg
                                    var totalWeight = quantity * unitMultiplier; // Tổng số kg của sản phẩm này

                                    totalDiscount += totalWeight * discount; // Tổng số tiền giảm giá của sản phẩm này
                                });
                                document.getElementById("totalOrderPriceHidden").value = total; // Lưu giá trị số thực
                                document.getElementById("totalOrderPrice").value = formatNumberVND(total);
                                document.getElementById("totalDiscount").value = totalDiscount;
                                document.getElementById("totalDiscountHidden").value = formatNumberVND(totalDiscount);
                            }






                        </script>

                        <script>


                            function formatNumberVND(number) {
                                return new Intl.NumberFormat('vi-VN').format(number);
                            }

                        </script>




                    </form>
                    <!-- Popup thêm khách hàng mới -->
                    <div id="addCustomerPopup" class="popup">

                        <span class="close" onclick="closeAddCustomerPopup()">&times;</span>
                        <h2>Thêm khách hàng mới</h2>

                        <form id="addCustomerForm">
                            <label for="newCustomerName">Tên khách hàng:</label>
                            <input type="text" id="newCustomerName" name="name" placeholder="Nhập tên khách hàng" required>

                            <label for="newCustomerPhone">Số điện thoại:</label>
                            <input type="number" id="newCustomerPhone" name="phone" placeholder="Nhập số điện thoại" required>

                            <!-- Các input ẩn để Servlet nhận đủ dữ liệu -->
                            <input type="hidden" name="address" value="">
                            <input type="hidden" name="email" value="">
                            <input type="hidden" name="total" value="0"> <!-- Để hợp lệ với Servlet -->

                            <button type="button" onclick="saveNewCustomer()">Lưu</button>
                        </form>

                    </div>



                    <script>

                        $("#paidAmount").on("input", function () {
                            if ($(this).val() < 0) {
                                $(this).val(0);
                            }
                        });


                    </script>
                    <script>
                        function openAddCustomerPopup() {
                            $("#addCustomerPopup").show();
                        }

                        function closeAddCustomerPopup() {
                            $("#addCustomerPopup").hide();
                        }

                        function saveNewCustomer() {
                            let name = $("#newCustomerName").val().trim();
                            let phone = $("#newCustomerPhone").val().trim();

                            if (name === "" || phone === "") {
                                alert("Vui lòng nhập đầy đủ thông tin!");
                                return;
                            }

                            // Tạo object chứa đầy đủ dữ liệu theo yêu cầu của Servlet
                            let customerData = {
                                name: name,
                                phone: phone,
                                address: "", // Giá trị mặc định
                                email: "", // Giá trị mặc định
                                total: "0" // Để Servlet chấp nhận
                            };
                            // Gửi dữ liệu lên server để lưu khách hàng mới
                            $.ajax({
                                url: "AddCustomer",
                                type: "POST",
                                data: customerData,
                                success: function (response) {
                                    // Kiểm tra nội dung phản hồi từ Servlet
                                    // Tạo một thẻ div ẩn để chứa response HTML
                                    let tempDiv = $("<div>").html(response);

// Kiểm tra xem có thông báo thành công hay không
                                    if (tempDiv.find("#successMessage").length > 0) {
                                        alert("Khách hàng đã được thêm!");
                                        closeAddCustomerPopup();
                                    } else if (tempDiv.find("#errorMessage").length > 0) {
                                        alert("Lỗi khi thêm khách hàng!");
                                    } else {
                                        alert("Không tìm thấy phản hồi từ server!");
                                    }

                                },

                            });
                        }

                    </script>

                    <script>

                        $(document).ready(function () {
                            $("#orderForm").submit(function (event) {
                                event.preventDefault(); // Ngăn chặn việc tải lại trang
                                $("#orderStatus").text("⏳ Đơn hàng đang xử lý..."); // Hiển thị trạng thái ngay lập tức
                                $("#submitOrder").prop("disabled", true); // Disable nút submit

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
                                    }
                                });
                            });
                            function checkOrderStatus() {
                                var userId = ${sessionScope.userID}; // Đảm bảo lấy đúng userID từ session

                                $.ajax({
                                    url: "CheckOrderStatusServlet",
                                    type: "GET",
                                    data: {userId: userId},
                                    dataType: "json",
                                    success: function (response) {
                                        if (response.status === "done") {
                                            $("#orderStatus").text("✅ Tạo đơn hàng thành công!");
                                        } else if (response.status === "error") {
                                            $("#orderStatus").text("❌ Lỗi: Tạo đơn hàng không thành công!");
                                        } else {
                                            setTimeout(checkOrderStatus, 1000); // Tiếp tục kiểm tra sau 1 giây
                                        }
                                    }
                                });
                            }

                            // ---- Code từ GitHub ----
                            // Lấy các phần tử cần ẩn/hiện
                            const openAddNewDebt = document.querySelector('.js-hidden-menu'); // Nút toggle
                            const newDebt = document.querySelector('.menu'); // Menu
                            const newDebt1 = document.querySelector('.main-content'); // Nội dung chính
                            const newDebt2 = document.querySelector('.sidebar'); // Sidebar

                            // Kiểm tra trạng thái đã lưu trong localStorage khi trang load
                            document.addEventListener("DOMContentLoaded", function () {
                                if (localStorage.getItem("menuHidden") === "true") {
                                    newDebt.classList.add('hiden');
                                    newDebt1.classList.add('hiden');
                                    newDebt2.classList.add('hiden');
                                }
                            });
                            // Hàm toggle hiển thị
                            function toggleAddNewDebt() {
                                newDebt.classList.toggle('hiden');
                                newDebt1.classList.toggle('hiden');
                                newDebt2.classList.toggle('hiden');
                                // Lưu trạng thái vào localStorage
                                const isHidden = newDebt.classList.contains('hiden');
                                localStorage.setItem("menuHidden", isHidden);
                            }

                            // Gán sự kiện click
                            openAddNewDebt.addEventListener('click', toggleAddNewDebt);
                        });



                </script>










            </div>

        </div>





    </body>







</html>
