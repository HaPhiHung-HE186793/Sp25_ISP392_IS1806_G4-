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

            let url = 'URLRemindOrder?customerName=' + encodeURIComponent(customerName);
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
            const url = 'URLRemindOrder?sortColumn=' + columnIndex +
                        '&sortOrder=' + currentSortOrder +
                        '&customerName=<%= customerName != null ? customerName : "" %>' +
                        '&date=<%= selectedDate != null ? selectedDate : "" %>';
            window.location.href = url;
        }

        function resetFilters() {
            window.location.href = 'URLRemindOrder';
        }
    </script>
    <style>
        /* CSS Styles... */
        .statistics {
        margin-bottom: 20px;
        border: 1px solid #ccc;
        padding: 10px;
        border-radius: 5px;
    }
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
/*            border: 1px solid #ddd;*/
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
            
            <div>
                
                <input type="text" id="customerName" placeholder="Nhập tên khách hàng" value="<%= customerName != null ? customerName : "" %>">
                
                
                <input type="date" id="datePicker" value="<%= selectedDate != null ? selectedDate : "" %>">
                
                <button class="action-button" onclick="performSearch()">Tìm kiếm</button>
                <button class="action-button" onclick="resetFilters()">Đặt lại</button>
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
                            <th>Tiền còn thiếu</th>
                            <th>Ngày tạo</th>                           
                            <th>Cửu vạn</th>
                            
                            
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
                            <td>
                            <%
                                double paidAmount = showOrder.getPaidAmount(); // Số tiền khách đã trả
                                double total = showOrder.getTotalAmount(); // Tổng số tiền 
                                double remainingAmount = total - paidAmount; // Tính tiền còn thiếu
                            %>
                            <%= decimalFormat.format(remainingAmount) %>
                        </td>
                            <td><%= showOrder.getCreateAt() %></td>                           
                            <td><%= showOrder.getPorter() %></td>
                            
                            <td>
                            
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
            <div class="pagination" aria-label="Quiz Pagination">
            <% if (currentPage > 1) { %>
                <a href="URLRemindOrder?page=<%= currentPage - 1 %>&customerName=<%= customerName != null ? customerName : "" %>&date=<%= selectedDate != null ? selectedDate : "" %>&sortColumn=<%= sortColumn %>&sortOrder=<%= sortOrder %>" class="page-link" aria-label="Previous Page">&laquo; Trước</a>
            <% } %>

            <% for (int i = 1; i <= totalPages; i++) { %>
                <a href="URLRemindOrder?page=<%= i %>&customerName=<%= customerName != null ? customerName : "" %>&date=<%= selectedDate != null ? selectedDate : "" %>&sortColumn=<%= sortColumn %>&sortOrder=<%= sortOrder %>" class="page-link <%= (i == currentPage) ? "active" : "" %>" aria-current="<%= (i == currentPage) ? "page" : "false" %>"><%= i %></a>
            <% } %>

            <% if (currentPage < totalPages) { %>
                <a href="URLRemindOrder?page=<%= currentPage + 1 %>&customerName=<%= customerName != null ? customerName : "" %>&date=<%= selectedDate != null ? selectedDate : "" %>&sortColumn=<%= sortColumn %>&sortOrder=<%= sortOrder %>" class="page-link" aria-label="Next Page">Sau &raquo;</a>
            <% } %>
            </div>
            <div class="statistics">
            <h3>Thống Kê Hóa Đơn Trả Thiếu</h3>
            <p>Số lượng hóa đơn trả thiếu: <%= request.getAttribute("countMissingOrders") %></p>
            <p>Tổng số tiền trả thiếu: <%= decimalFormat.format((Double) request.getAttribute("totalMissingAmount")) %></p>
        </div>
            <div class="back-button">
                <a href="<%=request.getContextPath()%>/URLOrder?service=listshow" class="action-button">Quay lại</a>
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