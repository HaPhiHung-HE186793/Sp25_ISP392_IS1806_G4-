<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.OrderItems, java.util.List, model.CustomerOrder, java.util.Vector, java.text.DecimalFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./assets/css/style.css">
    <link rel="stylesheet" href="./assets/fonts/themify-icons/themify-icons.css">
    <title><%= request.getAttribute("papeTitle") %></title>
    
    <style>
        .btn {
            padding: 5px 10px;
            background-color: #c9302c;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            transition: background-color 0.3s;
        }
        .btn:hover {
            background-color: #b52a2a;
        }
        .back-button {
            text-align: right;
            margin-top: 10px;
        }
        .customer-info {
            margin-bottom: 20px;
            border: 1px solid #ccc;
            padding: 10px;
            border-radius: 5px;
        }
        .pagination {
            margin-top: 20px;
        }
        .page-link {
            padding: 5px 10px;
            text-decoration: none;
            color: #000; /* Màu chữ mặc định */
            transition: background-color 0.3s; /* Hiệu ứng chuyển màu */
            border: 1px solid #ccc; /* Viền mặc định */
            border-radius: 4px; /* Bo góc */
            margin: 0 2px; /* Khoảng cách giữa các nút */
        }
        .page-link:hover,
        .page-link:focus {
            background-color: #1E90FF; /* Màu xanh nước biển khi hover hoặc focus */
            color: white; /* Màu chữ khi hover hoặc focus */
            outline: none; /* Bỏ viền mặc định */
        }
        .page-link.active {
            background-color: #1E90FF; /* Màu xanh nước biển cho nút đang hoạt động */
            color: white; /* Màu chữ cho nút đang hoạt động */
        }
        .refresh-container {
            margin-top: 20px; /* Khoảng cách từ bảng */
            text-align: left; /* Đặt nút ở bên trái */
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

            <div class="customer-info">
                <h3>Thông tin Khách Hàng</h3>
                <%
                List<CustomerOrder> list2 = (List<CustomerOrder>) request.getAttribute("data2");
                if (list2 != null && !list2.isEmpty()) {
                    for (CustomerOrder customerOrder : list2) {
                %>
                <p>
                    Tên: <%= customerOrder.getName() %><br>
                    Email: <%= customerOrder.getEmail() %><br>
                    Điện thoại: <%= customerOrder.getPhone() %>
                </p>
                <%
                    }
                } else {
                %>
                <p style="text-align: center;">Không có thông tin khách hàng</p>
                <%
                }
                %>
            </div>

            <div class="search-container">
                <form action="<%=request.getContextPath()%>/URLOrderDetail" method="get">
                    <input type="hidden" name="service" value="listOrderItem">
                    <input type="hidden" name="orderId" value="<%= request.getParameter("orderId") %>">
                    <input type="text" name="productName" placeholder="Nhập tên sản phẩm" 
                           value="<%= request.getParameter("productName") != null ? request.getParameter("productName") : "" %>" 
                           required>
                    <button type="submit" class="btn">Tìm kiếm</button>
                </form>
            </div>

            <div class="table-container">
                <h3><%= request.getAttribute("tableTitle") %></h3>
                
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>                            
                            <th>Mã sản phẩm</th>
                            <th>Tên sản phẩm</th>
                            <th>Tổng</th>
                            <th>Giá nhập</th>
                            <th>Số lượng</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
                        List<OrderItems> list = (List<OrderItems>) request.getAttribute("data");
                        if (list != null && !list.isEmpty()) {
                            for (OrderItems orderItem : list) {
                        %>
                        <tr>                            
                            <td><%= orderItem.getOrderitemID() %></td>
                            <td><%= orderItem.getProductID() %></td>
                            <td><%= orderItem.getProductName() %></td>
                            <td><%= decimalFormat.format(orderItem.getPrice()) %></td>
                            <td><%= orderItem.getUnitPrice() %></td>
                            <td><%= orderItem.getQuantity() %></td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="6" style="text-align: center;">Không có bản ghi</td>
                        </tr>
                        <%
                        }
                        %>
                    </tbody>
                </table>
            </div>
            <div class="refresh-container">
                <a href="<%=request.getContextPath()%>/URLOrderDetailIn?service=listOrderItem&orderId=<%= request.getParameter("orderId") %>" class="btn">Hiển thị lại</a>
            </div>
            <div class="pagination" aria-label="Order Pagination">
                <% 
                int currentPage = (Integer) request.getAttribute("currentPage");
                int totalPages = (Integer) request.getAttribute("totalPages");
                %>
                <% if (currentPage > 1) { %> 
                    <a href="URLOrderDetailIn?page=<%= currentPage - 1 %>&orderId=<%= request.getParameter("orderId") %>" class="page-link" aria-label="Previous Page">&laquo; Trước</a>
                <% } %>

                <% for (int i = 1; i <= totalPages; i++) { %>
                    <a href="URLOrderDetailIn?page=<%= i %>&orderId=<%= request.getParameter("orderId") %>" class="page-link <%= (i == currentPage) ? "active" : "" %>" aria-current="<%= (i == currentPage) ? "page" : "false" %>"><%= i %></a>
                <% } %>

                <% if (currentPage < totalPages) { %>
                    <a href="URLOrderDetailIn?page=<%= currentPage + 1 %>&orderId=<%= request.getParameter("orderId") %>" class="page-link" aria-label="Next Page">Sau &raquo;</a>
                <% } %>
            </div>

            <div class="back-button">
                <a href="<%=request.getContextPath()%>/URLOrderIn?service=listshow" class="btn">Quay lại</a>
            </div>
        </div>
    </div>

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