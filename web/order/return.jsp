<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Kết quả thanh toán</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="assets/images/favicon.ico.png">
        <link href="assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/style.min.css" rel="stylesheet" type="text/css" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./assets/css/style.css">
        <link rel="stylesheet" href="./assets/fonts/themify-icons/themify-icons.css">
    </head>
    
    <style>
        /* Giao diện thông báo thành công */
.payment-success-container {
    background: #2d572c;
    color: #fff;
    padding: 20px;
    border-radius: 10px;
    text-align: center;
    margin-top: 20px;
}

.payment-success-title {
    font-size: 22px;
    font-weight: bold;
    color: #00ff00;
}

.order-id,
.order-amount,
.bank-name {
    color: #ffcc00;
    font-weight: bold;
}

/* Giao diện thông báo thất bại */
.payment-failure-container {
    background: #572c2c;
    color: #fff;
    padding: 20px;
    border-radius: 10px;
    text-align: center;
    margin-top: 20px;
}

.payment-failure-title {
    font-size: 22px;
    font-weight: bold;
    color: #ff4444;
}

.payment-message {
    font-size: 16px;
    font-weight: bold;
    color: #ff9999;
}

/* Nút quay về trang tạo hóa đơn */
.back-to-order {
    display: inline-block;
    margin-top: 15px;
    padding: 12px 18px;
    background: #ff9800;
    color: #fff;
    text-decoration: none;
    font-size: 16px;
    border-radius: 6px;
    transition: 0.3s;
}

.back-to-order:hover {
    background: #e68900;
}

    </style>
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
                    <div class="table-container">        
                    <c:choose>
                        <c:when test="${not empty orderId}">
                            <!-- Giao dịch thành công -->
                            <div class="payment-success-container">
                                <h4 class="payment-success-title">🎉 Giao dịch thành công!</h4>
                                <p class="payment-info">Mã đơn hàng: <strong class="order-id">${orderId}</strong></p>
                                <p class="payment-info">Số tiền: 
                                    <strong class="order-amount">
                                        <fmt:setLocale value="vi_VN" />
                                        <fmt:formatNumber value="${amount}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                                    </strong> VNĐ
                                </p>
                                <p class="payment-info">Ngân hàng: <strong class="bank-name">${bankCode}</strong></p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <!-- Giao dịch thất bại -->
                            <div class="payment-failure-container">
                                <h4 class="payment-failure-title">❌ Giao dịch thất bại!</h4>
                                <p class="payment-message">${message}</p>
                            </div>
                        </c:otherwise>
                    </c:choose>

                    <a href="CreateOrderServlet" class="back-to-order">🏠 Quay về tạo hóa đơn</a>

                </div>
                <%@include file="/Component/pagination.jsp" %>
            </div>
        </div>


        <script src="assets/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
