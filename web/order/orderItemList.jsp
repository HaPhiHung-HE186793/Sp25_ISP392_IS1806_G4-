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
</head>

<body>
    <div id="main">
        <jsp:include page="/Component/menu.jsp"></jsp:include>

        <div class="main-content">
            <div class="notification">
                Thông báo: Mọi người có thể liên hệ admin tại fanpage Group 4
            </div>

            <div class="table-container">
                <h3><%= request.getAttribute("tableTitle") %></h3>
                <table border="1">
                    <thead>
                        <tr>
                            <th>Order Item ID</th>
                            <th>Order ID</th>
                            <th>Product ID</th>
                            <th>Product Name</th>
                            <th>Price</th>
                            <th>Unit Price</th>
                            <th>Quantity</th>
                            <th>Description</th>
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
                            <td colspan="8" style="text-align: center;">No rows found</td>
                        </tr>
                        <%
                        }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</body>
</html>