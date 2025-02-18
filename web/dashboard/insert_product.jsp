<%-- 
    Document   : insert_product
    Created on : Feb 16, 2025, 2:54:07 AM
    Author     : ACER
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="../assets/css/style.css">
        <link rel="stylesheet" href="../assets/fonts/themify-icons/themify-icons.css">
        <title>Bảng Điều Khiển</title>


    </head>

    <body>
        <div id="main">
            <jsp:include page="/Component/menu.jsp"></jsp:include>


                <div class="main-content">
                    <div class="notification">
                        Thông báo: Mọi người có thể liên hệ admin tại fanpage Group 4
                    </div>


                    <div class="table-container">
                        <h3>New product</h3>
                        <form method="post" action="/DemoISP/CreateProduct">
                            <table>
                                <tr>
                                    <td>Tên sản phẩm:</td>
                                    <td><input type="text" name="productName" required></td>
                                </tr>
                                <tr>
                                    <td>Mô tả:</td>
                                    <td><textarea name="description" rows="4" cols="50" required></textarea></td>
                                </tr>
                                <tr>
                                    <td>Giá:</td>
                                    <td><input type="number" name="price" min="0" required></td>
                                </tr>
                                <tr>
                                    <td>Ảnh:</td>
                                    <td><input type="text" name="image"></td>
                                </tr>
                                <tr>
                                    <td>Tạo bởi (User ID):</td>
                                    <td>
                                        <input type="hidden" name="createBy" value="${sessionScope.userID}" required>
                                    <span id="createByDisplay">${sessionScope.userID}</span>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2"><button type="submit">Lưu</button></td>

                            </tr>
                        </table>
                    </form>


                </div>
            </div>
        </div>

    </div>
</body>



<script>



</script>


</html>
