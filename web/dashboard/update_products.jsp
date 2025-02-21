<%-- update.jsp --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Update Product</title>
        <link rel="stylesheet" href="./assets/css/style.css">
        <link rel="stylesheet" href="./assets/fonts/themify-icons/themify-icons.css">
    </head>
    <body>
        <div id="main">
            <jsp:include page="/Component/menu.jsp"></jsp:include>

                <div class="main-content">
                    <div class="notification">
                        Thông báo: Mọi người có thể liên hệ admin tại fanpage Group 4
                    </div>

                    <div class="table-container">
                        <h1>Update Product</h1>

                    <c:if test="${not empty errorMessage}">
                        <p style="color: red;">${errorMessage}</p>
                    </c:if>

                    <form action="UpdateProduct" method="post">
                        <table>
                            <tr>
                                <td>Tên sản phẩm:</td>
                                <td><input type="text" name="productName" value="${product.productName}" required></td>
                            </tr>
                            <tr>
                                <td>Mô tả:</td>
                                <td><textarea name="description" rows="4" cols="50" required>${product.description}</textarea></td>
                            </tr>
                            <tr>
                                <td>Giá:</td>
                                <td><input type="number" name="price" value="${product.price}" min="0" required></td>
                            </tr>
                            <tr>
                                <td>Số lượng:</td>
                                <td><input type="number" name="q" value="${product.quantity}" min="0" required></td>
                            </tr>
                            <tr>
                                <td>Ảnh:</td>
                                <td><input type="text" name="image" value="${product.image}"></td>
                            </tr>
                            <%-- Hidden fields --%>
                            <input type="hidden" name="updateAt" value="${product.updateAt}">
                            <tr>
                                <td colspan="2"><input type="submit" value="Lưu"></td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>