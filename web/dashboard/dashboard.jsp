<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bảng Điều Khiển Bán Hàng</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <link rel="stylesheet" href="./assets/css/style.css">
        <link rel="stylesheet" href="./assets/fonts/themify-icons/themify-icons.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>






            .card {
                background-color: #eeeeeef5;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
                padding: 20px;
                margin-bottom: 0px;
                padding: 28px 20px;
            }

            h3 {
                font-size: 18px;
                font-weight: 600;
                margin-bottom: 20px;
                color: #333;
            }

            /* Stats boxes */
            .stats-container {
                display: flex;
                justify-content: space-between;
                flex-wrap: wrap;
            }

            .stat-box {
                display: flex;
                align-items: center;
                flex: 1;
                min-width: 200px;
                padding: 10px 20px;
                border-right: 1px solid #eee;
            }

            .stat-box:last-child {
                border-right: none;
            }

            .stat-icon {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                margin-right: 15px;
                color: white;
            }

            .blue {
                background-color: #2979ff;
            }

            .orange {
                background-color: #ff9800;
            }

            .red {
                background-color: #f44336;
            }

            .stat-info {
                flex: 1;
            }

            .stat-header {
                font-size: 14px;
                color: #666;
                margin-bottom: 5px;
            }

            .stat-value {
                font-size: 24px;
                font-weight: 700;
                margin-bottom: 5px;
                color: #333;
            }

            .stat-label {
                font-size: 14px;
                color: #666;
            }

            .percentage {
                font-weight: bold;
            }

            .negative {
                color: #f44336;
            }

            .positive {
                color: #4caf50;
            }

            /* Card header with revenue and dropdown */
            .card-header {
                display: flex;

                align-items: center;
                margin-bottom: 20px;
            }


            .revenue-amount {
                display: flex;
                align-items: center;
                color: #2979ff;
                font-weight: bold;
                font-size: 18px;
                margin-left: 20px;
            }

            .revenue-amount i {
                margin-right: 8px;
            }

            .dropdown {
                position: relative;
                display: inline-block;
                margin-left: auto;
            }

            .dropdown-btn1 {
                background-color: white;
                border: 1px solid #ddd;
                border-radius: 4px;
                padding: 8px 15px;
                cursor: pointer;
                display: flex;
                align-items: center;
                color: #2979ff;
                font-weight: 500;
            }

            .dropdown-btn i {
                margin-left: 8px;
            }

            .dropdown-content {
                display: none;
                position: absolute;
                right: 0;
                background-color: white;
                min-width: 160px;
                box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.1);
                z-index: 1;
                border-radius: 4px;
            }

            .dropdown-content a {
                color: #333;
                padding: 12px 16px;
                text-decoration: none;
                display: block;
            }

            .dropdown-content a:hover {
                background-color: #f5f5f5;
            }

            .dropdown:hover .dropdown-content {
                display: block;
            }

            /* Tabs */
            .tabs {
                display: flex;
                border-bottom: 1px solid #eee;
                margin-bottom: 20px;
            }

            .tab-btn {
                background: none;
                border: none;
                padding: 10px 20px;
                cursor: pointer;
                font-size: 14px;
                color: #666;
                position: relative;
            }

            .tab-btn.active {
                color: #2979ff;
                font-weight: 500;
            }

            .tab-btn.active::after {
                content: '';
                position: absolute;
                bottom: -1px;
                left: 0;
                width: 100%;
                height: 2px;
                background-color: #2979ff;
            }

            /* Chart container */
            .chart-container {
                height: 400px;
                position: relative;
            }

            /* Top products */
            .dashboard-row {
                display: flex;
                gap: 20px;
                margin-bottom: 20px;
            }

            .dashboard-col {
                flex: 1;
            }

            .top-products {
                height: 73%;
            }

            .product-list {
                display: flex;
                justify-content: space-between;
                gap: 15px;
                margin-top: 15px;
            }

            .product-item {
                flex: 1;
                display: flex;
                flex-direction: column;
                align-items: center;
                text-align: center;
                padding: 15px;
                border-radius: 8px;
                background-color: #f9f9f9;
                transition: transform 0.2s;
            }

            .product-item:hover {
                transform: translateY(-5px);
                box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            }

            .product-rank {
                width: 30px;
                height: 30px;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                font-weight: bold;
                margin-bottom: 10px;
                font-size: 14px;
            }

            .rank-1 {
                background-color: #FFD700;
                color: #fff;
            }

            .rank-2 {
                background-color: #C0C0C0;
                color: #fff;
            }

            .rank-3 {
                background-color: #CD7F32;
                color: #fff;
            }

            .product-image {
                width: 80px;
                height: 80px;
                border-radius: 8px;
                background-color: #fff;
                margin-bottom: 10px;
                display: flex;
                align-items: center;
                justify-content: center;
                overflow: hidden;
            }

            .product-image img {
                width: 100%;
                height: 100%;
                object-fit: cover;
            }

            .product-info {
                width: 100%;
            }

            .product-name {
                font-weight: 500;
                margin-bottom: 8px;
                font-size: 14px;
                color: #333;
            }

            .product-stats {
                display: flex;
                flex-direction: column;
                font-size: 13px;
                color: #666;
            }

            .product-quantity {
                margin-bottom: 5px;
                width: 90px;
            }

            .product-revenue {
                color: #2979ff;
                font-weight: 500;
            }
        </style>
    </head>
    <body>
        <div id="main">
            <jsp:include page="/Component/header.jsp"></jsp:include>
            <div class="menu ">  <jsp:include page="/Component/menu.jsp"></jsp:include> </div>     

                <div class="main-content">
                    <div class="notification">
                        Thông báo: Mọi người có thể liên hệ admin tại fanpage <a style="color: #5bc0de; text-decoration: none; transition: 0.3s;" href="https://github.com/HaPhiHung-HE186793/Sp25_ISP392_IS1806_G4-" target="_blank">
                            Group 4 ISP392
                        </a>
                    </div>
                <%
                    double revenueChange = (double) request.getAttribute("revenueChange");
                    String changeClass = (revenueChange < 0) ? "negative" : "positive";
                %>
                <div class="dashboard-row">
                    <!-- Today's Sales Results -->
                    <div class="dashboard-col">
                        <div class="card">
                            <h3>KẾT QUẢ BÁN HÀNG HÔM NAY</h3>
                            <div class="stats-container">
                                <div class="stat-box">
                                    <div class="stat-icon blue">
                                        <i class="fas fa-dollar-sign"></i>
                                    </div>
                                    <div class="stat-info">
                                        <div class="stat-header">${totalOrderToday} Hóa đơn</div>
                                        <div class="stat-value">
                                            <fmt:formatNumber value="${totalRevenueToday}" type="number" groupingUsed="true" maxFractionDigits="0" />đ</div>
                                        <div class="stat-label">Doanh thu</div>
                                    </div>
                                </div>

                                <div class="stat-box">
                                    <div class="stat-icon red">
                                        <i class="fas fa-arrow-down"></i>
                                    </div>
                                    <div class="stat-info">
                                        <div class="stat-value percentage <%= changeClass %>">
                                            <%= String.format("%.2f", revenueChange) %>%  
                                        </div>
                                        <div class="stat-label">So với cùng kỳ tháng trước</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Top Products -->
                    <div class="dashboard-col1">
                        <div class="card top-products">
                            <h3>TOP 3 GẠO BÁN CHẠY THÁNG NÀY</h3>
                            <div class="product-list">
                                <c:forEach var="i" begin="0" end="2">
                                    <div class="product-item">
                                        <div class="product-info">
                                            <div class="product-name">${productNames[i]}</div>
                                            <div class="product-stats">
                                                <div class="product-quantity">Đã bán: ${totalSold[i]} kg</div>
                                                <div class="product-revenue">
                                                    <fmt:formatNumber value="${totalRevenue[i]}" type="number" groupingUsed="true" maxFractionDigits="0" />đ
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Monthly Revenue -->
                <div class="card">
                    <div class="card-header">
                        <h3>DOANH THU THÁNG NÀY</h3>
                        <div class="revenue-amount">
                            <i class="fas fa-circle-info"></i>
                            <span><fmt:formatNumber value="${totalRevenueThisMonth}" type="number" groupingUsed="true" maxFractionDigits="0" />đ</span>
                        </div>
                        <div class="dropdown">
                            <select name="viewRevenue" id="viewRevenue" onchange="updateChartOnChange()">
                                <option value="0" ${viewRevenue == 0 ? 'selected' : ''}>7 ngày qua</option>
                                <option value="1" ${viewRevenue == 1 ? 'selected' : ''}>Tháng này</option>
                                <option value="2" ${viewRevenue == 2 ? 'selected' : ''}>Tháng trước</option>
                            </select>
                        </div>
                    </div>

                    <div class="tabs">
                        <button class="tab-btn" data-view="day">Theo ngày</button>
                        <button class="tab-btn" data-view="hour">Theo giờ</button>
                        <button class="tab-btn active" data-view="weekday">Theo thứ</button>
                    </div>

                    <div class="chart-container">
                        <canvas id="revenueChart"></canvas>
                    </div>
                </div>
            </div>
        </div>            



        <script>
            document.addEventListener('DOMContentLoaded', function () {
                // Tab switching
                const tabBtns = document.querySelectorAll('.tab-btn');
                tabBtns.forEach(btn => {
                    btn.addEventListener('click', function () {
                        tabBtns.forEach(b => b.classList.remove('active')); // Bỏ active ở tab khác
                        this.classList.add('active'); // Đánh dấu tab hiện tại

                        const viewType = this.getAttribute('data-view'); // Lấy viewType
                        const viewRevenue = document.getElementById('viewRevenue').value; // Lấy giá trị từ select
                        fetchDataFromServlet(viewType, viewRevenue); // Gọi Servlet lấy dữ liệu thật
                    });
                });

                // Initialize chart with default data (weekday)
                initChart();
                fetchDataFromServlet('weekday', '0'); // Lấy dữ liệu mặc định

                // Dropdown functionality
                const dropdownBtn = document.querySelector('.dropdown-btn');
                const dropdownContent = document.querySelector('.dropdown-content');

                dropdownBtn.addEventListener('click', function () {
                    dropdownContent.style.display = dropdownContent.style.display === 'block' ? 'none' : 'block';
                });

                // Close dropdown when clicking outside
                window.addEventListener('click', function (event) {
                    if (!event.target.matches('.dropdown-btn') && !event.target.matches('.dropdown-btn *')) {
                        dropdownContent.style.display = 'none';
                    }
                });
            });

// Chart initialization
            let revenueChart;

            function initChart() {
                const ctx = document.getElementById('revenueChart').getContext('2d');
                revenueChart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: [],
                        datasets: [{
                                label: 'Doanh thu',
                                data: [],
                                backgroundColor: '#2979ff',
                                borderWidth: 0,
                                borderRadius: 4
                            }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        scales: {
                            y: {
                                beginAtZero: true,
                                ticks: {
                                    callback: function (value) {
                                        if (value >= 1000000) {
                                            return (value / 1000000).toFixed(1) + ' tr';
                                        } else if (value >= 1000) {
                                            return (value / 1000).toFixed(0) + 'k';
                                        }
                                        return value;
                                    }
                                },
                                grid: {
                                    drawBorder: false
                                }
                            },
                            x: {
                                grid: {
                                    display: false
                                }
                            }
                        },
                        plugins: {
                            legend: {display: false},
                            tooltip: {
                                callbacks: {
                                    label: function (context) {
                                        let value = context.raw;
                                        return new Intl.NumberFormat('vi-VN').format(value) + ' đ';
                                    }
                                }
                            }
                        }
                    }
                });
            }


            function fetchDataFromServlet(viewType, viewRevenue) {
                fetch('dashboard?viewType=' + viewType + '&viewRevenue=' +viewRevenue)
                        .then(response => response.json())
                        .then(data => {
                            updateChart(data);
                        })
                        .catch(error => console.error('Lỗi khi lấy dữ liệu từ Servlet:', error));
            }

            function updateChartOnChange() {
                const activeTab = document.querySelector('.tab-btn.active').getAttribute('data-view');
                const viewRevenue = document.getElementById('viewRevenue').value; // Lấy giá trị từ select
                fetchDataFromServlet(activeTab, viewRevenue); // Lấy dữ liệu dựa trên tab hiện tại
            }


            function updateChart(data) {
                revenueChart.data.labels = data.labels;
                revenueChart.data.datasets[0].data = data.values;
                revenueChart.update();
            }


        </script>
        <script>

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

