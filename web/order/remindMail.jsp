<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
String email = (String) request.getAttribute("email");
String orderId = (String) request.getAttribute("orderId");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./assets/css/style.css">
    <title>Gửi Nhắc Nhở Thanh Toán</title>
    <style>
        .main-content {
            padding: 20px; /* Đảm bảo các thành phần bên trong có khoảng cách */
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="email"] {
            width: 20%; /* Giảm chiều ngang của ô nhập email */
            padding: 10px;
            margin-bottom: 20px; /* Khoảng cách giữa các trường */
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            background-color: #fff; /* Màu nền cho ô nhập */
            color: #000; /* Màu chữ trong ô nhập */
        }
        textarea {
            width: 80%; /* Giữ chiều rộng cho textarea */
            padding: 10px;
            margin-bottom: 20px; /* Khoảng cách giữa các trường */
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            background-color: #fff; /* Màu nền cho textarea */
            color: #000; /* Màu chữ trong textarea */
            height: 320px; /* Tăng chiều dọc của textarea */
        }
        .submit-button {
            background-color: #007bff; /* Màu nền nút gửi email */
            color: white;
            padding: 8px 12px; /* Giảm padding cho nút */
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px; /* Giảm kích thước chữ */
            transition: background-color 0.3s;
            width: 8%; /* Nút chiếm toàn bộ chiều rộng */
            margin-bottom: 15px; /* Khoảng cách giữa các nút */
        }
        .submit-button:hover {
            background-color: #0056b3; /* Màu nền khi hover */
        }
        .action-button {
            background-color: #6c757d; /* Màu nền nút quay lại */
            color: white;
            padding: 8px 12px; /* Giảm padding cho nút */
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px; /* Giảm kích thước chữ */
            border: none;
            width: 8%; /* Nút chiếm toàn bộ chiều rộng */
        }
        .action-button:hover {
            background-color: #5a6268; /* Màu nền khi hover */
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
            <h3>Gửi Nhắc Nhở Thanh Toán</h3>
            <form action="URLRemindMail" method="post">
                <input type="hidden" name="service" value="send"> <!-- Thêm service -->
                <div>
                    <label for="email">Địa chỉ email:</label>
                    <input type="email" id="email" name="email" required value="<%= email != null ? email : "" %>">
                </div>
                <div>
                    <label for="message">Nội dung:</label>
                    <textarea id="message" name="message" required placeholder="Nhập nội dung nhắc nhở..." rows="4"></textarea>
                </div>
                <input type="hidden" name="orderId" value="<%= orderId %>">
                <button type="submit" class="submit-button">Gửi Email</button>
            </form>
            <div>
                <button onclick="window.location.href='<%= request.getContextPath() %>/URLRemindOrder'" class="action-button">Quay lại</button>
            </div>
        </div>
    </div>
</body>
</html>