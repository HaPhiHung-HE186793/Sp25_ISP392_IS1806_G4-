<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Update Product</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/fonts/themify-icons/themify-icons.css">
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

                        <!-- Hiển thị thông báo lỗi nếu có -->
                    <c:if test="${not empty errorMessage}">
                        <p style="color: red;">${errorMessage}</p>
                    </c:if>

                    <form action="UpdateProduct" method="post" onsubmit="return validateForm()">
                        <input type="hidden" name="productID" value="${product.productID}">
                        <table>
                            <tr>
                                <td>Tên sản phẩm:</td>
                                <td><input type="text" name="productName" value="${param.productName != null ? param.productName : product.productName}" required></td>
                            </tr>
                            <tr>
                                <td>Mô tả:</td>
                                <td><textarea name="description" rows="4" cols="50" required>${param.description != null ? param.description : product.description}</textarea></td>
                            </tr>
                            <tr>
                                <td>Giá:</td>
                                <td>
                                    <input type="text" id="price" name="price" value="${param.price != null ? param.price : product.price}" required oninput="formatPrice(this)">
                                    <span id="error-message" style="color: red;"></span>
                                </td>
                            </tr>
                            <tr>
                                <td>Ảnh:</td>
                                <td><input type="text" name="image" value="${param.image != null ? param.image : product.image}"></td>
                            </tr>
                            <tr>
                                <td colspan="2"><input type="submit" value="Lưu"></td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>

        <script>
            function formatPrice(input) {
                input.value = input.value.replace(/^0+(\d+)/, '$1'); // Xóa số 0 ở đầu
                if (input.value < 0) {
                    document.getElementById("error-message").textContent = "Giá không được âm!";
                } else {
                    document.getElementById("error-message").textContent = "";
                }
            }

            function validateForm() {
                let priceInput = document.getElementById("price");
                let price = parseFloat(priceInput.value);

                if (isNaN(price) || price < 0) {
                    alert("Giá không thể âm hoặc không hợp lệ!");
                    return false;
                }

                priceInput.value = price;
                return true;
            }
        </script>
    </body>
</html>
