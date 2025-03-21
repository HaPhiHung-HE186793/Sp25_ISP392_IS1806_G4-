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
        padding: 10px 15px; /* Khoảng cách bên trong */
        font-size: 16px; /* Kích thước chữ */
        border: none; /* Không viền */
        cursor: pointer; /* Con trỏ chuột */
        border-radius: 4px; /* Bo góc */
        transition: background-color 0.3s; /* Hiệu ứng chuyển đổi */
        display: flex; /* Hiển thị dạng flex */
        align-items: center; /* Căn giữa theo chiều dọc */
    }

    .dropbtn:hover {
        background-color: #45a049; /* Màu nền khi hover */
    }

    .dropbtn::after {
        content: "▼"; /* Thêm biểu tượng mũi tên */
        font-size: 10px; /* Kích thước nhỏ hơn */
        margin-left: 8px; /* Khoảng cách với tên */
    }

    .dropdown-content {
        display: none; /* Ẩn nội dung dropdown */
        position: absolute; /* Đặt vị trí tuyệt đối */
        right: 0; /* Căn phải */
        background-color: #ffffff; /* Màu nền */
        min-width: 120px; /* Chiều rộng tối thiểu */
        box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2); /* Đổ bóng */
        z-index: 1; /* Đặt z-index */
        border-radius: 4px; /* Bo góc */
        overflow: hidden; /* Ẩn nội dung tràn */
        animation: fadeIn 0.2s ease; /* Hiệu ứng xuất hiện */
    }

    @keyframes fadeIn {
        from { opacity: 0; transform: translateY(-5px); }
        to { opacity: 1; transform: translateY(0); }
    }

    .dropdown:hover .dropdown-content {
        display: block; /* Hiển thị nội dung khi hover */
    }

    .dropdown-content a {
        color: #333; /* Màu chữ */
        padding: 8px 12px; /* Khoảng cách bên trong */
        text-decoration: none; /* Không gạch chân */
        display: block; /* Hiển thị dưới dạng khối */
        transition: background-color 0.2s; /* Hiệu ứng chuyển đổi */
        border-bottom: 1px solid #f1f1f1; /* Đường kẻ phân cách */
    }

    .dropdown-content a:hover {
        background-color: #f1f1f1; /* Màu nền khi hover */
        color: #4CAF50; /* Màu chữ khi hover */
    }

    .logout-button {
        background-color: #f44336; /* Màu nền cho nút đăng xuất */
        color: white; /* Màu chữ */
        border: none; /* Không viền */
        padding: 8px 12px; /* Khoảng cách bên trong */
        cursor: pointer; /* Con trỏ chuột */
        width: 100%; /* Chiều rộng 100% */
        text-align: left; /* Căn lề trái */
        transition: background-color 0.2s; /* Hiệu ứng chuyển đổi */
    }

    .logout-button:hover {
        background-color: #d32f2f; /* Màu nền khi hover */
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
            <a href="<%=request.getContextPath()%>/HistoryImportPriceServlet">Lịch sử giá</a>

            <a href="<%=request.getContextPath()%>/workschedule">Ca làm việc</a>

        </c:if>
        <c:if test="${sessionScope.roleID == 2 or sessionScope.roleID == 3}">
            <a href="<%=request.getContextPath()%>/CreateOrderServlet">Hóa đơn xuất</a>
            <a href="<%=request.getContextPath()%>/CreateImportOrderServlet">Hóa đơn nhập</a>
            <a href="<%=request.getContextPath()%>/URLOrder?service=listshow">Quản lý thanh toán</a>
            <a href="<%=request.getContextPath()%>/ListCustomer">Quản lý khách hàng</a>

            <a href="<%=request.getContextPath()%>/ListCustomer">Quản lý zone</a>

            <a href="<%=request.getContextPath()%>/storeprofile">Hồ sơ cửa hàng</a>

        </c:if>
        <a href="<%=request.getContextPath()%>/updateprofile">Hồ sơ người dùng</a>
    </div>
</body>
</html>

