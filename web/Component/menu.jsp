<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rice Storage</title>
    <style>
        .dropdown {
            position: relative;
            display: inline-block;
        }

        .dropbtn {
            background-color: #4CAF50; /* Màu nền cho nút dropdown */
            color: white; /* Màu chữ */
            padding: 10px; /* Khoảng cách bên trong */
            font-size: 16px; /* Kích thước chữ */
            border: none; /* Không viền */
            cursor: pointer; /* Con trỏ chuột */
        }

        .dropdown-content {
            display: none; /* Ẩn nội dung dropdown */
            position: absolute; /* Đặt vị trí tuyệt đối */
            background-color: #f9f9f9; /* Màu nền */
            min-width: 50px; /* Chiều rộng tối thiểu */
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2); /* Đổ bóng */
            z-index: 1; /* Đặt z-index */
        }

        .dropdown:hover .dropdown-content {
            display: block; /* Hiển thị nội dung khi hover */
        }

        .dropdown-content a {
            color: black; /* Màu chữ */
            padding: 4px 6px; /* Khoảng cách bên trong */
            text-decoration: none; /* Không gạch chân */
            display: block; /* Hiển thị dưới dạng khối */
        }

        .dropdown-content a:hover {
            background-color: #f1f1f1; /* Màu nền khi hover */
        }

        .logout-button {
            background-color: #f44336; /* Màu nền cho nút đăng xuất */
            color: white; /* Màu chữ */
            border: none; /* Không viền */
            padding: 4px 6px; /* Khoảng cách bên trong */
            cursor: pointer; /* Con trỏ chuột */
            width: 100%; /* Chiều rộng 100% */
        }
    </style>
</head>
<body>

    <div class="sidebar">
        <div class="logo">Bảng Điều Khiển </div>
        <c:if test="${sessionScope.roleID == 2 or sessionScope.roleID == 3}">
        <a href="<%=request.getContextPath()%>/ListProducts">Danh sách sản phẩm</a>
        </c:if>
        <c:if test="${sessionScope.roleID==1}">
            <a href="<%=request.getContextPath()%>/listusers">Danh sách người dùng</a>
            <a href="<%=request.getContextPath()%>/liststore">Danh sách cửa hàng</a>
        </c:if>
        <c:if test="${sessionScope.roleID==2}">
            <a href="<%=request.getContextPath()%>/listusers">Danh sách người dùng</a>
        </c:if>
        <c:if test="${sessionScope.roleID == 2 or sessionScope.roleID == 3}">
            <a href="<%=request.getContextPath()%>/CreateOrderServlet">Hóa đơn xuất</a>
            <a href="<%=request.getContextPath()%>/CreateImportOrderServlet">Hóa đơn nhập</a>
            <a href="<%=request.getContextPath()%>/URLOrder?service=listshow">Quản lý thanh toán</a>
            <a href="<%=request.getContextPath()%>/ListCustomer">Quản lý khách hàng</a>
            <a href="<%=request.getContextPath()%>/ListCustomer">Quản lý zone</a>
        </c:if>
        <a href="<%=request.getContextPath()%>/updateprofile">Hồ sơ người dùng</a>
    </div>
</body>
</html>

