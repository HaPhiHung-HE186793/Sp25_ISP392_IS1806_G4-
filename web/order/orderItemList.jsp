<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.OrderItems, java.util.List"%>
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
            background-color: #c9302c; /* Màu nút */
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none; /* Bỏ gạch chân */
            transition: background-color 0.3s;
        }
        .btn:hover {
            background-color: #b52a2a; /* Màu tối hơn khi hover */
        }
        .back-button {
            text-align: right; /* Căn chỉnh nút về bên phải */
            margin-top: 10px; /* Thêm khoảng cách phía trên nút */
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
            <div class="search-container">
                <form action="<%=request.getContextPath()%>/URLOrderDetail" method="get">
                    <input type="hidden" name="service" value="listOrderItem">
                    <input type="hidden" name="orderId" value="<%= request.getParameter("orderId") %>">
                    <input type="text" name="productName" placeholder="Nhập tên sản phẩm" required>
                    <button type="submit" class="btn">Tìm kiếm</button>
                </form>
                <form action="<%=request.getContextPath()%>/URLOrderDetail" method="get" style="display:inline;">
                    <input type="hidden" name="service" value="listOrderItem">
                    <input type="hidden" name="orderId" value="<%= request.getParameter("orderId") %>">
                    <button type="submit" class="btn">Hiển thị toàn bộ sản phẩm</button>
                </form>
            </div>
            <div class="table-container">
                <h3><%= request.getAttribute("tableTitle") %></h3>
                <table border="1">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Mã hóa đơn</th>
                            <th>Mã sản phẩm</th>
                            <th>Tên sản phẩm</th>
                            <th>Giá</th>
                            <th>Đơn giá</th>
                            <th>Số lượng</th>
                            <th>Mô tả</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        List<OrderItems> list = (List<OrderItems>) request.getAttribute("data");
                        if (list != null && !list.isEmpty()) {
                            for (OrderItems orderItem : list) {
                        %>
                        <tr>
                            <td><%= orderItem.getOrderitemID() %></td>
                            <td><%= orderItem.getOrderID() %></td>
                            <td><%= orderItem.getProductID() %></td>
                            <td><%= orderItem.getProductName() %></td>
                            <td><%= orderItem.getPrice() %></td>
                            <td><%= orderItem.getUnitPrice() %></td>
                            <td><%= orderItem.getQuantity() %></td>
                            <td><%= orderItem.getDescription() %></td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="8" style="text-align: center;">Không có bản ghi</td>
                        </tr>
                        <%
                        }
                        %>
                    </tbody>
                </table>
            </div>
            <div class="back-button">
                <a href="<%=request.getContextPath()%>/URLOrder?service=listshow" class="btn">Quay lại</a>
            </div>
        </div>
    </div>

</body>


</html>