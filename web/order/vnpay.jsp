<%-- 
    Document   : vnpay
    Created on : Feb 17, 2025, 3:36:59 PM
    Author     : thang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <title>VNPay Payment</title>
</head>
<body>
    <h2>Thanh toán qua VNPay</h2>
    <form action="VNPayServlet" method="post">
        <label>Số tiền (VNĐ):</label>
        <input type="number" name="amount" required>
        <button type="submit">Thanh toán</button>
    </form>
    <hr>
    <a href="transactionHistory.jsp">Xem lịch sử giao dịch</a>
</body>
</html>
