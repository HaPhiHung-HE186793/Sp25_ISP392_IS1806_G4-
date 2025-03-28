<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.ShowOrder, java.util.Vector"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.text.DecimalFormat"%>
<%
    DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
    int currentPage = (int) request.getAttribute("currentPage");
    int totalPages = (int) request.getAttribute("totalPages");
    String customerName = (String) request.getAttribute("customerName");
    String selectedDate = (String) request.getAttribute("date");
    String sortColumn = (String) request.getAttribute("sortColumn");
    String sortOrder = (String) request.getAttribute("sortOrder");
    int totalAmount = (int) request.getAttribute("totalAmount");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./assets/css/style.css">
    <link rel="stylesheet" href="./assets/fonts/themify-icons/themify-icons.css">
    <title><%= request.getAttribute("pageTitle") %></title>
    <script>
        let currentSortOrder = "<%= sortOrder != null ? sortOrder : "asc" %>";

        function performSearch() {
            const customerName = document.getElementById("customerName").value;
            const selectedDate = document.getElementById("datePicker").value;

            let url = 'URLOrder?customerName=' + encodeURIComponent(customerName);
            if (selectedDate) {
                url += '&date=' + encodeURIComponent(selectedDate);
            }
            url += '&sortColumn=<%= sortColumn %>';
            url += '&sortOrder=' + currentSortOrder;
            window.location.href = url;
        }

        function performSort() {
            const columnIndex = document.getElementById("sortColumn").value;
            currentSortOrder = (currentSortOrder === 'asc') ? 'desc' : 'asc';
            const url = 'URLOrder?sortColumn=' + columnIndex +
                        '&sortOrder=' + currentSortOrder +
                        '&customerName=<%= customerName != null ? customerName : "" %>' +
                        '&date=<%= selectedDate != null ? selectedDate : "" %>';
            window.location.href = url;
        }

        function resetFilters() {
            window.location.href = 'URLOrder';
        }
    </script>
    <style>
        /* CSS Styles... */
        .action-button {
            padding: 5px 10px;
            border: none;
            background-color: #d9534f; /* Màu đỏ */
            color: white;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            transition: background-color 0.3s;
        }
        .action-button:hover {
            background-color: #c9302c; /* Màu đỏ tối hơn khi hover */
        }
        .blue-button {
            padding: 5px 10px;
            border: none;
            background-color: #007bff; /* Màu xanh */
            color: white;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            transition: background-color 0.3s;
        }
        .blue-button:hover {
            background-color: #0056b3; /* Màu xanh tối hơn khi hover */
        }
        .table-container {
            overflow-x: auto;
            width: 100%;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            border: none;
        }
        th, td {
            padding: 10px;
            text-align: left;
            border: none;
        }
        th {
            background-color: #f2f2f2;
        }
        .pagination {
            margin-top: 20px;
        }
        .pagination a {
            padding: 8px 12px;
            margin: 0 4px;
            border: 1px solid #ddd;
            text-decoration: none;
            color: #007bff;
        }
        .pagination a.active {
            background-color: #007bff;
            color: white;
        }
        .total-amount {
            font-weight: bold;
            padding: 10px;
            border-top: 2px solid #ddd;
            text-align: right;
        }
        .reminder-button {
            background-color: #00aaff; /* Màu xanh nước biển */
            color: white;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            transition: background-color 0.3s;
            margin-top: 15px; /* Khoảng cách với các phần trên */
        }
        .reminder-button:hover {
            background-color: #0088cc; /* Màu tối hơn khi hover */
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
            
            <h3><%= request.getAttribute("tableTitle") %></h3>
            <div style="text-align: right; margin-bottom: 10px;">
                <button class="blue-button" onclick="window.location.href='<%=request.getContextPath()%>/URLOrderIn?service=listshow'">Hóa đơn nhập</button>
            </div>
            <div>
                
                <input type="text" id="customerName" placeholder="Nhập tên khách hàng" value="<%= customerName != null ? customerName : "" %>">
                
                
                <input type="date" id="datePicker" value="<%= selectedDate != null ? selectedDate : "" %>">
                
                <button class="action-button" onclick="performSearch()">Tìm kiếm</button>
                <button class="action-button" onclick="resetFilters()">Đặt lại</button>
            </div>
            <div>
                
                <select id="sortColumn">
                    <option value="0" <%= "0".equals(sortColumn) ? "selected" : "" %>>Mã hóa đơn</option>
                    <option value="1" <%= "1".equals(sortColumn) ? "selected" : "" %>>Tên khách hàng</option>
                    <option value="2" <%= "2".equals(sortColumn) ? "selected" : "" %>>Người tạo</option>
                    <option value="3" <%= "3".equals(sortColumn) ? "selected" : "" %>>Khách đã trả</option>
                    <option value="4" <%= "4".equals(sortColumn) ? "selected" : "" %>>Thành tiền</option>
                    <option value="5" <%= "5".equals(sortColumn) ? "selected" : "" %>>Ngày tạo</option>
                    <option value="6" <%= "6".equals(sortColumn) ? "selected" : "" %>>Cửu vạn</option>
                </select>
                <button class="action-button" onclick="performSort()">Sắp xếp</button>
            </div>
            <div class="table-container">
                <table id="orderTable" data-sort-order="asc">
                    <thead>
                        <tr>
                            <th>Mã hóa đơn</th>
                            <th>Tên khách hàng</th>
                            <th>Người tạo</th>
                            <th>Khách đã trả</th>
                            <th>Thành tiền</th>                           
                            <th>Ngày tạo</th>                           
                            <th>Cửu vạn</th>
                            <th>Mô tả</th>
                            <th>Chi tiết</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        Vector<ShowOrder> vector = (Vector<ShowOrder>) request.getAttribute("data");
                        if (vector != null && !vector.isEmpty()) {
                            for (ShowOrder showOrder : vector) {
                        %>
                        <tr>
                            <td><%= showOrder.getOrderID() %></td>
                            <td><%= showOrder.getName() %></td>
                            <td><%= showOrder.getUserName() %></td>
                            <td><%= decimalFormat.format(showOrder.getPaidAmount()) %></td>
                            <td><%= decimalFormat.format(showOrder.getTotalAmount()) %></td>
                            <td><%= showOrder.getCreateAt() %></td>                           
                            <td><%= showOrder.getPorter() %></td>
                            <td><%= showOrder.getStatus() %></td>
                            <td>
                                <button class="action-button" onclick="window.location.href='URLOrderDetail?service=listOrderItem&orderId=<%= showOrder.getOrderID() %>'">Chi tiết</button>
                            </td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="9" style="text-align: center;">No rows found</td>
                        </tr>
                        <%
                        }
                        %>
                    </tbody>
                </table>
            </div>
            <div class="total-amount" <%= (customerName != null && !customerName.isEmpty()) || (selectedDate != null && !selectedDate.isEmpty()) ? "" : "style='display:none;'" %>>Tổng doanh thu: <%= totalAmount %></div>
            <div class="pagination" aria-label="Quiz Pagination">
            <% if (currentPage > 1) { %>
                <a href="URLOrder?page=<%= currentPage - 1 %>&customerName=<%= customerName != null ? customerName : "" %>&date=<%= selectedDate != null ? selectedDate : "" %>&sortColumn=<%= sortColumn %>&sortOrder=<%= sortOrder %>" class="page-link" aria-label="Previous Page">&laquo; Trước</a>
            <% } %>

            <% for (int i = 1; i <= totalPages; i++) { %>
                <a href="URLOrder?page=<%= i %>&customerName=<%= customerName != null ? customerName : "" %>&date=<%= selectedDate != null ? selectedDate : "" %>&sortColumn=<%= sortColumn %>&sortOrder=<%= sortOrder %>" class="page-link <%= (i == currentPage) ? "active" : "" %>" aria-current="<%= (i == currentPage) ? "page" : "false" %>"><%= i %></a>
            <% } %>

            <% if (currentPage < totalPages) { %>
                <a href="URLOrder?page=<%= currentPage + 1 %>&customerName=<%= customerName != null ? customerName : "" %>&date=<%= selectedDate != null ? selectedDate : "" %>&sortColumn=<%= sortColumn %>&sortOrder=<%= sortOrder %>" class="page-link" aria-label="Next Page">Sau &raquo;</a>
            <% } %>
            </div>
            <div class="back-button">
                <a href="<%=request.getContextPath()%>/URLChart?service=listChart" class="action-button">Thống kê</a>
            </div>
            <div class="back-button">
                <a href="<%=request.getContextPath()%>/URLRemindOrder?service=listChart" class="reminder-button">Hóa đơn trả thiếu</a> <!-- Chỉnh sửa màu và khoảng cách -->
            </div>
        </div>
    </div>
</body>



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

</html>