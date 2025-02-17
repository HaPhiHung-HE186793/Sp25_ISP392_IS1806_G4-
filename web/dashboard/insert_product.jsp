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
                        Tên sản phẩm: <input type="text" name="productName" required><br>
                        Mô tả: <textarea name="description" rows="4" cols="50" required></textarea><br>
                        Giá: <input type="number" name="price" min="0" required><br>
                        Ảnh: <input type="text" name="image"><br> 
                        Tạo bởi (User ID): <input type="number" name="createBy" required><br>
                        <input type="submit" value="Lưu">
                    </form>

                </div>
            </div>
        </div>

    </div>
</body>



<script>



</script>


</html>
