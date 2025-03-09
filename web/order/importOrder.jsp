<%-- 
    Document   : importOrder
    Created on : Mar 8, 2025, 6:20:20 PM
    Author     : Admin
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
            <jsp:include page="/Component/menu.jsp"></jsp:include>


                <div class="main-content">
                    <div class="notification">
                        Thông báo: Mọi người có thể liên hệ admin tại fanpage Group 4
                    </div>

                    <h1 style="text-align: center">
                        Hóa Đơn Nhập
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
                                        $("#search").on("input", function () {
                                            let query = $(this).val();
                                            if (query.length > 0) {
                                                $.ajax({
                                                    url: "SearchServlet",
                                                    type: "GET",
                                                    data: {
                                                        searchProduct: query,
                                                        orderType: 0  // ✅ Đúng cú pháp
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










                                <input type="hidden" name="orderType" value="0"><!-- xuất -->

                                <!-- Order items table -->
                                <div class="order-items-container">
                                    <h3>Chi Tiết Đơn Hàng</h3>
                                    <table id="orderItems">
                                        <thead>
                                            <tr>
                                                <th>Sản Phẩm</th>
                                                <th>Đơn vị</th>
                                                <th>Số Lượng</th>
                                                <th>Giá Nhập</th>
                                                <th>Giảm Giá(Kg)</th>
                                                <th>Thành Tiền</th>
                                                <th>Xóa</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <!-- Order items will be added here -->
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <td colspan="5" style="text-align: right;"><strong>Tổng tiền:</strong></td>

                                                <td colspan="2">
                                                    <input type="hidden" id="totalOrderPriceHidden" name="totalOrderPriceHidden">
                                                    <input type="text" id="totalOrderPrice" name="totalOrderPrice" readonly>
                                                </td>

                                            </tr>
                                            <tr>
                                                <td colspan="5" style="text-align: right;"><strong>Tổng tiền đã giảm:</strong></td>
                                                <td colspan="2"><input type="text" id="totalDiscount" name="totalDiscount" readonly></td>
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

                                <div class="info-row">
                                    <span class="info-label">Người Tạo:</span>
                                    <span class="info-value success-text">${sessionScope.username}</span>
                            </div>

                            <div class="info-row">
                                <span class="info-label">Ngày Tạo:</span>
                                <span class="info-value">
                                    <input type="date" id="orderDate" name="orderDate" readonly>
                                </span>
                            </div>



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
                                    $("#searchCustomerInput").on("input", function () {
                                        let query = $(this).val();
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


                                    // Hiển thị thông tin khách hàng đã chọn
                                    $("#customerName").text("Khách hàng: " + name);
                                    $("#customerPhone").text("SĐT: " + phone);
                                    $("#customerDebt").text("Tổng nợ: " + formatNumberVND(debt));
                                    $("#customerInfo").show(); // Hiển thị div chứa thông tin khách hàng

                                }

                            </script>

                            <!-- Thêm khách hàng mới -->



                            <!-- JavaScript -->
                            <script>
                                function openAddCustomerPopup() {
                                    document.getElementById("addCustomerPopup").style.display = "block";
                                }

                                function closeAddCustomerPopup() {
                                    document.getElementById("addCustomerPopup").style.display = "none";
                                }

                                function saveNewCustomer() {
                                    let name = document.getElementById("newCustomerName").value;
                                    let phone = document.getElementById("newCustomerPhone").value;

                                    if (name.trim() === "" || phone.trim() === "") {
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
                                            alert("Khách hàng đã được thêm!");
                                            closeAddCustomerPopup();
                                        },
                                        error: function () {
                                            alert("Lỗi khi thêm khách hàng!");
                                        }
                                    });
                                }

                            </script>

                            <div class="info-row">
                                <span class="info-label">Số Bao Bốc Vác:</span>
                                <span class="info-value">
                                    <input type="number" id="porter" name="porter" required min="0">
                                </span>
                            </div>

                            <!-- Payment status -->
                            <div class="payment-options">
                                <h3>Trạng Thái Thanh Toán</h3>
                                <div class="radio-group">
                                    <div>
                                        <input type="radio" id="paid" name="orderStatus" value="paid" onclick="togglePaymentFields()" checked>
                                        <label for="paid">Đã Thanh Toán Đủ</label>
                                    </div>

                                    <div>
                                        <input type="radio" id="partial" name="orderStatus" value="partial" onclick="togglePaymentFields()">
                                        <label for="partial">Thanh Toán Thiếu</label>
                                    </div>

                                    <div>
                                        <input type="radio" id="unpaid" name="orderStatus" value="unpaid" onclick="togglePaymentFields()">
                                        <label for="unpaid">Chưa Thanh Toán</label>
                                    </div>
                                </div>

                                <div id="paidAmountRow" style="display: none;">
                                    <div class="info-row">
                                        <span class="info-label">Số tiền đã trả:</span>
                                        <span class="info-value">
                                            <input type="number" id="paidAmount" name="paidAmount" oninput="calculateDebt()">
                                        </span>
                                    </div>
                                </div>

                                <div id="debtRow" style="display: none;">
                                    <div class="info-row">
                                        <span class="info-label">Số tiền còn nợ:</span>
                                        <span class="info-value">
                                            <input type="text" id="debtAmount" name="debtAmount" readonly>
                                        </span>
                                    </div>
                                </div>
                            </div>



                            <!-- Action buttons -->
                            <div class="action-buttons">

                                <button type="submit">Tạo Hóa Đơn</button>
                                <button id="printOrderBtn" type="button" class="print-btn">In Hóa Đơn</button>
                            </div>

                            <p id="orderStatus"></p>
                        </div>

                    </div>



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
                            var totalOrderPrice = parseFloat(document.getElementById("totalOrderPriceHidden").value) || 0;
                            // lấy giá trị tổng đơn hàng
                            var paidAmount = parseFloat(document.getElementById("paidAmount").value) || 0;
                            //Lấy số tiền đã trả (paidAmount). Nếu chưa nhập, mặc định là 0
                            var debtAmountInput = document.getElementById("debtAmount");
                            //Lấy ô hiển thị số tiền còn nợ (debtAmount)

                            var debt = totalOrderPrice - paidAmount;

                            debtAmountInput.value = formatNumberVND(debt > 0 ? debt : 0);

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
                        function addProductToOrder(productID, productName, unitSizes) {





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
                                    unitSelectHTML += '<option value="' + size + '">' + (size == 1 ? "Kg" : "Bao (" + size + "kg)") + '</option>';
                                });
                            } else {
                                unitSelectHTML += '<option value="1">Kg</option>'; // Mặc định nếu không có dữ liệu
                            }
                            unitSelectHTML += '</select>';
                            cell2.innerHTML = unitSelectHTML;


                            cell3.innerHTML = '<input type="number" name="quantity" class="quantity" required="required" value="1" min="1">';



                            cell4.innerHTML = '<input type="hidden" name="unitPriceHidden" class="unitPriceHidden">' +
                                    '<input type="number" name="unitPrice" class="unitPrice" min="0"  required>';








                            // Giảm giá (VND/kg)
                            cell5.innerHTML = '<input type="number" name="discount" class="discount" value="0" min="0">';
                            cell6.innerHTML =
                                    '<input type="hidden" name="totalPriceHidden" class="totalPriceHidden">' +
                                    '<input type="text" name="totalPrice" class="totalPrice" readonly>';


                            // Nút xóa
                            cell7.innerHTML = '<button type="button" onclick="deleteRow(this)">Xóa</button>';

                            // Thêm input ẩn để lưu totalWeight
                            cell8.innerHTML = '<input type="hidden" name="totalWeight" class="totalWeight">';

                            //không cho phép nhập âm số lượng và giảm giá




                            var quantityInput = cell3.querySelector('.quantity');

                            // Không cho nhập số âm
                            quantityInput.addEventListener("input", function () {
                                if (this.value < 0) {
                                    this.value = 1; // Đặt giá trị tối thiểu là 1
                                }
                                recalculateRow(newRow);

                            });


                            var unitTypeInput = cell2.querySelector('.unitType');
                            var discountInput = cell5.querySelector('.discount');
                            var unitPriceInput = cell4.querySelector('.unitPrice');

                            // Không cho nhập số âm
                            discountInput.addEventListener("input", function () {
                                if (this.value < 0) {
                                    this.value = 0; // Đặt giá trị tối thiểu là 1
                                }
                            });
                            unitPriceInput.addEventListener("input", function () {
                                if (this.value < 0) {
                                    this.value = 0; // Đặt giá trị tối thiểu là 1
                                }
                            });


                            unitTypeInput.addEventListener('change', () => recalculateRow(newRow));
                            discountInput.addEventListener('input', () => recalculateRow(newRow));
                            unitPriceInput.addEventListener('input', () => recalculateRow(newRow));

                            recalculateRow(newRow);




                        }
                        function recalculateRow(row) {

                            var quantityInput = row.querySelector(".quantity");
                            var unitTypeInput = row.querySelector(".unitType");
                            var discountInput = row.querySelector(".discount");
                            var totalPriceInput = row.querySelector(".totalPrice");
                            var totalPriceHidden = row.querySelector(".totalPriceHidden");
                            var totalWeightInput = row.querySelector(".totalWeight");
                            var unitPriceInput = row.querySelector(".unitPrice");
                            var unitPriceHidden = row.querySelector(".unitPriceHidden");


                            var unitMultiplier = parseInt(unitTypeInput.value);
                            var quantity = parseInt(quantityInput.value);
                            var totalWeight = quantity * unitMultiplier;
                            var unitPrice = parseInt(unitPriceInput.value) || 0;

                            unitPriceHidden.value = unitPrice;




                            var discount = parseInt(discountInput.value) || 0;
                            var totalDiscount = totalWeight * discount;

                            var totalPrice = ((totalWeight * unitPriceHidden.value) - totalDiscount) || 0;



                            totalWeightInput.value = totalWeight;
                            totalPriceHidden.value = totalPrice; // Lưu giá trị số
                            totalPriceInput.value = formatNumberVND(totalPrice);

                            updateTotalOrderPrice();
                            saveOrderToSessionStorage();
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
                            document.getElementById("totalDiscount").value = formatNumberVND(totalDiscount);
                        }

                        function saveOrderToSessionStorage() {
                            var orderItems = [];
                            document.querySelectorAll("#orderItems tbody tr").forEach(row => {
                                var productID = row.querySelector("input[name='productID']").value;
                                var productName = row.querySelector("input[name='productName']").value;
                                var unitType = row.querySelector(".unitType").value;
                                var quantity = row.querySelector(".quantity").value;
                                var unitPriceHidden = row.querySelector(".unitPriceHidden").value;
                                var discount = row.querySelector(".discount").value;
                                var totalPriceHidden = row.querySelector(".totalPriceHidden").value;



                                // Lấy danh sách unitSizes từ dropdown, đảm bảo nó luôn là mảng
                                var unitSizes = Array.from(row.querySelectorAll(".unitType option")).map(opt => parseInt(opt.value));


                                orderItems.push({productID, productName, unitType, quantity, unitPriceHidden, discount, totalPriceHidden, unitSizes});
                            });
                            sessionStorage.setItem("orderItems", JSON.stringify(orderItems));
                        }


                        function loadOrderFromSessionStorage() {
                            var orderItems = JSON.parse(sessionStorage.getItem("orderItems")) || [];
                            orderItems.forEach(item => {
                                // Đảm bảo có unitSizes từ dữ liệu đã lưu trước đó
                                var unitSizes = item.unitSizes || [1]; // Nếu không có, mặc định là [1] (Kg)

                                addProductToOrder(item.productID, item.productName, unitSizes);

                                var lastRow = document.querySelector("#orderItems tbody tr:last-child");

                                // Cập nhật danh sách option trong dropdown
                                var unitSelect = lastRow.querySelector(".unitType");
                                unitSelect.innerHTML = ""; // Xóa option cũ

                                unitSizes.forEach(size => {
                                    var option = document.createElement("option");
                                    option.value = size;
                                    option.text = (size == 1 ? "Kg" : "Bao (" + size + "kg)");
                                    unitSelect.appendChild(option);
                                });

                                // Đặt lại giá trị đã lưu
                                unitSelect.value = item.unitType;
                                lastRow.querySelector(".quantity").value = item.quantity;
                                lastRow.querySelector(".discount").value = item.discount;
                                lastRow.querySelector(".unitPriceHidden").value = item.unitPriceHidden;
                                lastRow.querySelector(".totalPriceHidden").value = item.totalPriceHidden;
                                lastRow.querySelector(".totalPrice").value = formatNumberVND(item.totalPriceHidden);
                                lastRow.querySelector(".unitPrice").value = formatNumberVND(item.unitPriceHidden);
                            });
                            updateTotalOrderPrice();
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
                        <input type="text" id="newCustomerPhone" name="phone" placeholder="Nhập số điện thoại" required>

                        <!-- Các input ẩn để Servlet nhận đủ dữ liệu -->
                        <input type="hidden" name="address" value="">
                        <input type="hidden" name="email" value="">
                        <input type="hidden" name="total" value="0"> <!-- Để hợp lệ với Servlet -->

                        <button type="button" onclick="saveNewCustomer()">Lưu</button>
                    </form>

                </div>




                <!--  
                Tải thư viện jQuery phiên bản 3.6.0 từ CDN.
Giúp sử dụng các hàm AJAX, chọn phần tử DOM, xử lý sự kiện dễ dàng hơn.
                -->


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
                                data: {userId: userId},
                                dataType: "json",
                                success: function (response) {
                                    if (response.status === "done") {
                                        $("#orderStatus").text("✅ Tạo đơn hàng thành công!");
                                    } else if (response.status === "error") {
                                        $("#orderStatus").text("❌ Lỗi: Tạo đơn hàng không thành công!");
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



    </body>






</html>
