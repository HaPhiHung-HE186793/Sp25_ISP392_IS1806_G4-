<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Chart,java.util.Vector,model.BestSeller"%>
<%@page import="com.google.gson.Gson"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.text.DecimalFormat"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./assets/css/style.css">
    <link rel="stylesheet" href="./assets/fonts/themify-icons/themify-icons.css">
    <title><%= request.getAttribute("pageTitle") %></title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        .modal {
            display: none; 
            position: fixed; 
            z-index: 1000; 
            left: 0;
            top: 0;
            width: 100%; 
            height: 100%; 
            overflow: auto; 
            background-color: rgba(0, 0, 0, 0.4);
        }

        .modal-content {
            background-color: #333; /* Đổi màu nền thành xám */
            color: white; /* Đổi màu chữ thành trắng để dễ đọc */
            margin: 15% auto; 
            padding: 20px;
            border: 1px solid #888;
            width: 200px; /* Điều chỉnh chiều rộng khoảng 6cm */
            height: 150px; /* Điều chỉnh chiều cao khoảng 8cm */
        }

        .empty-message {
            color: black; /* Màu chữ đen */
            font-size: 16px; /* Kích thước chữ */
            text-align: center; /* Căn giữa chữ */
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

        .button-container {
            display: flex; /* Sử dụng Flexbox */
            justify-content: space-between; /* Căn chỉnh các nút với khoảng cách giữa chúng */
            margin-top: 10px; /* Khoảng cách phía trên */
        }

        .btn {
            padding: 5px 10px;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none; /* Bỏ gạch chân */
            transition: background-color 0.3s;
        }

        .left-buttons .btn {
            background-color: #66bb6a; /* Màu xanh lá cây nhạt */
            margin-right: 10px; /* Khoảng cách giữa các nút bên trái */
        }

        .left-buttons .btn:hover {
            background-color: #76c76d; /* Màu tối hơn khi hover */
        }

        .back-button {
            background-color: #d9534f; /* Màu nút quay lại */
        }

        .back-button:hover {
            background-color: #c9302c; /* Màu tối hơn khi hover */
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
            <h1><%= request.getAttribute("pageTitle") %></h1>

            <!-- Form chọn cách hiển thị -->
            <form id="chartOptionForm">
                <label for="displayOption">Chọn cách hiển thị:</label>
                <select id="displayOption" onchange="showForm()" name="displayOption">
                    <option value="">Chọn tùy chọn</option>
                    <option value="year" <%= "year".equals(request.getAttribute("selectedAction")) ? "selected" : "" %>>Theo năm</option>
                    <option value="month" <%= "monthly".equals(request.getAttribute("selectedAction")) ? "selected" : "" %>>Theo tháng</option>
                    <option value="day" <%= "daily".equals(request.getAttribute("selectedAction")) ? "selected" : "" %>>Theo ngày</option>
                </select>
            </form>

            <!-- Form chọn năm và tháng -->
            <div id="monthlyForm" style="display: <%= "monthly".equals(request.getAttribute("selectedAction")) ? "block" : "none" %>;">
                <form action="<%= request.getContextPath() %>/URLChart" method="get">
                    <input type="hidden" name="action" value="monthly">
                    <label for="year">Chọn năm:</label>
                    <select name="year" id="year">
                        <% 
                            Integer[] yearsArray = (Integer[]) request.getAttribute("yearsArray");
                            Integer selectedYear = (Integer) request.getAttribute("selectedYear"); // Chỉ khai báo một lần
                            if (yearsArray != null && yearsArray.length > 0) {
                                for (Integer year : yearsArray) { 
                        %>
                            <option value="<%= year %>" <%= selectedYear != null && selectedYear.equals(year) ? "selected" : "" %>><%= year %></option>
                        <% 
                                } 
                            } else { 
                        %>
                            <option value="">Không có năm nào</option>
                        <% 
                            } 
                        %>
                    </select>
                    <button type="submit">Xem doanh thu theo tháng</button>
                </form>
            </div>

            <!-- Form chọn tháng và ngày -->
            <div id="dailyForm" style="display: <%= "daily".equals(request.getAttribute("selectedAction")) ? "block" : "none" %>;">
                <form action="<%= request.getContextPath() %>/URLChart" method="get">
                    <input type="hidden" name="action" value="daily">
                    <label for="year">Chọn năm:</label>
                    <select name="year" id="year">
                        <% 
                            // Sử dụng lại selectedYear đã khai báo
                            if (yearsArray != null && yearsArray.length > 0) {
                                for (Integer year : yearsArray) { 
                        %>
                            <option value="<%= year %>" <%= selectedYear != null && selectedYear.equals(year) ? "selected" : "" %>><%= year %></option>
                        <% 
                                } 
                            } else { 
                        %>
                            <option value="">Không có năm nào</option>
                        <% 
                            } 
                        %>
                    </select>
                    <label for="month">Chọn tháng:</label>
                    <select name="month" id="month">
                        <% 
                            Integer selectedMonth = (Integer) request.getAttribute("selectedMonth");
                            for (int i = 1; i <= 12; i++) { 
                        %>
                            <option value="<%= i %>" <%= selectedMonth != null && selectedMonth.equals(i) ? "selected" : "" %>><%= i %></option>
                        <% 
                            } 
                        %>
                    </select>
                    <button type="submit">Xem doanh thu theo ngày</button>
                </form>
            </div>

            <!-- Biểu đồ -->
            <div id="chartContainer" style="display: <%= request.getAttribute("labelsArray") != null && ((String[])request.getAttribute("labelsArray")).length > 0 ? "block" : "none" %>;">
                <canvas id="myChart"></canvas>
            </div>

            <script>
                function showForm() {
                    const option = document.getElementById('displayOption').value;
                    document.getElementById('monthlyForm').style.display = 'none';
                    document.getElementById('dailyForm').style.display = 'none';
                    document.getElementById('chartContainer').style.display = 'none';

                    if (option === 'month') {
                        document.getElementById('monthlyForm').style.display = 'block';
                    } else if (option === 'day') {
                        document.getElementById('dailyForm').style.display = 'block';
                    } else if (option === 'year') {
                        // Chuyển hướng đến đường dẫn cho tùy chọn "Theo năm"
                        window.location.href = "<%=request.getContextPath()%>/URLChart?service=listChart";
                    }
                }

                // Kiểm tra nếu có dữ liệu để hiển thị biểu đồ
                const labels = <%= new Gson().toJson(request.getAttribute("labelsArray")) %>;
                const totalAmounts = <%= new Gson().toJson(request.getAttribute("totalAmountsArray")) %>;

                if (labels.length > 0 && totalAmounts.length > 0) {
                    const ctx = document.getElementById('myChart').getContext('2d');
                    const myChart = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: labels,
                            datasets: [{
                                label: 'Tổng số tiền',
                                data: totalAmounts,
                                backgroundColor: 'rgba(201, 48, 44, 0.6)',
                                borderColor: 'rgba(201, 48, 44, 1)',
                                borderWidth: 1
                            }]
                        },
                        options: {
                            scales: {
                                y: {
                                    beginAtZero: true
                                }
                            }
                        }
                    });
                }
            </script>

            <!-- Nút hiển thị sản phẩm bán chạy nhất và khách hàng mua nhiều nhất -->
            <div class="button-container">
                <div class="left-buttons">
                    <button id="bestSellingButton" onclick="toggleBestSellingModal()" class="btn">Sản phẩm bán chạy nhất</button>
                    <button id="bestCustomerButton" onclick="toggleBestCustomerModal()" class="btn">Khách hàng mua nhiều nhất</button>
                </div>
                <a href="<%=request.getContextPath()%>/URLOrder?service=listshow" class="btn back-button">Quay lại</a>
            </div>

            <div id="bestSellingModal" class="modal" style="display: none;">
                <div class="modal-content">
                    <span class="close" onclick="toggleBestSellingModal()">&times;</span>
                    <h2>Sản phẩm bán chạy nhất</h2>
                    <div id="bestSellingContent">
                        <%
                            DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
                            Vector<BestSeller> bestSellingProduct = (Vector<BestSeller>) request.getAttribute("bestSellingProduct");
                            if (bestSellingProduct != null && !bestSellingProduct.isEmpty()) {
                                for (BestSeller product : bestSellingProduct) {
                        %>
                            <p>Tên sản phẩm: <%= product.getProductName() %></p>
                            <p>Tổng doanh thu: <%= decimalFormat.format(product.getPrice()) %></p>
                        <%
                                }
                            } else {
                        %>
                            <p class="empty-message">Không có sản phẩm nào.</p>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>

            <div id="bestCustomerModal" class="modal" style="display: none;">
                <div class="modal-content">
                    <span class="close" onclick="toggleBestCustomerModal()">&times;</span>
                    <h2>Khách hàng mua nhiều nhất</h2>
                    <div id="bestCustomerContent">
                        <%
                            
                            Vector<BestSeller> bestCustomer = (Vector<BestSeller>) request.getAttribute("bestCustomer");
                            if (bestCustomer != null && !bestCustomer.isEmpty()) {
                                for (BestSeller customer : bestCustomer) {
                        %>
                            <p>Tên khách hàng: <%= customer.getName() %></p>
                            <p>Tổng doanh thu: <%= decimalFormat.format(customer.getPrice()) %></p>
                        <%
                                }
                            } else {
                        %>
                            <p class="empty-message">Không có khách hàng nào.</p>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        function toggleBestSellingModal() {
            var modal = document.getElementById("bestSellingModal");
            modal.style.display = modal.style.display === "none" || modal.style.display === "" ? "block" : "none";
        }

        function toggleBestCustomerModal() {
            var modal = document.getElementById("bestCustomerModal");
            modal.style.display = modal.style.display === "none" || modal.style.display === "" ? "block" : "none";
        }

        // Đóng modal nếu người dùng nhấp ra ngoài modal
        window.onclick = function(event) {
            if (event.target.className === "modal") {
                event.target.style.display = "none";
            }
        }
        
        
        
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

    </script>
</body>
</html>