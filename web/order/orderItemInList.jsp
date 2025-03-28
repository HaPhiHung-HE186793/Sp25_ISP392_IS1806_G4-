<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.OrderItems, java.util.List, model.CustomerOrder, java.util.Vector, java.text.DecimalFormat, model.OrderPaper"%>
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
            color: #000;
            transition: background-color 0.3s;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin: 0 2px;
        }
        .page-link:hover,
        .page-link:focus {
            background-color: #1E90FF;
            color: white;
            outline: none;
        }
        .page-link.active {
            background-color: #1E90FF;
            color: white;
        }
        .refresh-container {
            margin-top: 20px;
            text-align: left;
        }
        #orderPaperTable {
            display: none;
            margin-top: 20px;
        }

        .modal {
            display: none; 
            position: fixed; 
            z-index: 1; 
            left: 0;
            top: 0;
            width: 100%; 
            height: 100%; 
            overflow: auto; 
            background-color: rgb(0,0,0); 
            background-color: rgba(0,0,0,0.4); 
        }

        .modal-content {
            background-color: #fefefe;
            margin: 15% auto; 
            padding: 20px;
            border: 1px solid #888;
            width: 60%; /* Giảm chiều ngang xuống 60% */
            color: black; /* Đổi màu chữ thành đen */
        }

        .modal-table {
            width: 100%; /* Đảm bảo bảng chiếm toàn bộ chiều rộng */
            border-collapse: collapse; /* Gộp các đường viền của các ô lại với nhau */
        }

        .modal-table th, .modal-table td {
            border: 1px solid #ccc; /* Thêm đường viền cho các ô */
            padding: 8px; /* Thêm khoảng cách bên trong các ô */
            text-align: left; /* Căn lề trái cho văn bản */
        }

        .modal-table th {
            background-color: #f2f2f2; /* Màu nền cho hàng tiêu đề */
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
                    String phone = customerOrder.getPhone();
                    String maskedPhone;

                    // Kiểm tra độ dài số điện thoại
                    if (phone.length() > 5) {
                        // Giữ 2 số đầu và 3 số cuối, thay thế phần giữa bằng dấu '*'
                        maskedPhone = phone.substring(0, 2) + "****" + phone.substring(phone.length() - 3);
                    } else {
                        // Nếu số điện thoại quá ngắn, không thay đổi
                        maskedPhone = phone;
                    }
            %>
            <p>
                Tên: <%= customerOrder.getName() %><br>
                Email: <%= customerOrder.getEmail() %><br>
                Điện thoại: <%= maskedPhone %>
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

            

            <div class="table-container">
                <h3><%= request.getAttribute("tableTitle") %></h3>
                
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>                            
                            <th>Mã sản phẩm</th>
                            <th>Tên sản phẩm</th>
                            <th>Giá</th>
                            <th>Đơn giá</th>
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
                
                <button id="toggleOrderPaper" class="btn">Phiếu Hóa Đơn</button>
            </div>

            <!-- Modal for Order Paper -->
            <div id="orderPaperModal" class="modal" style="display:none;">
                <div class="modal-content">
                    <span class="close">&times;</span>
                    <h3>Đơn Hàng</h3>
                    
                    <%
                    Vector<OrderPaper> list3 = (Vector<OrderPaper>) request.getAttribute("data3");
                    if (list3 != null && !list3.isEmpty()) {
                        OrderPaper orderPaper = list3.get(0); // Lấy thông tin đơn hàng từ phần tử đầu tiên
                    %>
                    <p>Mã hóa đơn: <%= orderPaper.getOrderID() %></p>
                    <p>Ngày tạo: <%= orderPaper.getCreateAt() %></p>
                    <p>Tên khách hàng: <%= orderPaper.getName() %></p>
                    <p>Người tạo: <%= orderPaper.getUserName() %></p>

                    <h4>Chi tiết sản phẩm:</h4>
                    <table class="modal-table">
                        <thead>
                            <tr>
                                <th>Mã sản phẩm</th>
                                <th>Tên sản phẩm</th>
                                <th>Giá</th>
                                <th>Số Lượng</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            for (OrderPaper order : list3) {
                            %>
                            <tr>
                                <td><%= order.getOrderitemID() %></td>
                                <td><%= order.getProductName() %></td>
                                <td><%= decimalFormat.format(order.getPrice()) %></td>
                                <td><%= order.getQuantity() %></td>
                            </tr>
                            <%
                            }
                            %>
                        </tbody>
                    </table>

                    <p>Thành tiền: <%= decimalFormat.format(orderPaper.getTotalAmount()) %></p>
                    <p>Khách đã trả: <%= decimalFormat.format(orderPaper.getPaidAmount()) %></p>
                    <button onclick="window.print();" class="btn">In Đơn Hàng</button>
                    <%
                    } else {
                    %>
                    <p>Không có thông tin đơn hàng</p>
                    <%
                    }
                    %>
                </div>
            </div>

            <script>
                document.getElementById("toggleOrderPaper").onclick = function() {
                    var modal = document.getElementById("orderPaperModal");
                    modal.style.display = "block";
                };

                document.querySelector(".close").onclick = function() {
                    var modal = document.getElementById("orderPaperModal");
                    modal.style.display = "none";
                };

                window.onclick = function(event) {
                    var modal = document.getElementById("orderPaperModal");
                    if (event.target === modal) {
                        modal.style.display = "none";
                    }
                };
            </script>

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