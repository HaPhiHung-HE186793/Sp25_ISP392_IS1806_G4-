<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
        <title>Thêm Khu Vực</title>
    </head>
    <body>
        <div id="main">
            <jsp:include page="/Component/menu.jsp"></jsp:include>
                <div class="main-content">
                    <h2>Thêm Khu Vực Mới</h2>

                <c:if test="${not empty message}">
                    <p style="color: red;">${message}</p>
                </c:if>

                <form action="/DemoISP/InsertZones" enctype="multipart/form-data" method="post">
                    <table>
                        <tr>
                            <td>Tên khu vực:</td>
                            <td><input type="text" name="zoneName" required></td>
                        </tr>
                        <tr>
                            <td>Ảnh:</td>
                            <td>
                                <input type="file" name="image" id="image" accept="image/*" >
                                <br>
                                <img id="previewImage" src="#" alt="Xem trước ảnh" style="max-width: 200px; display: none; margin-top: 10px;">
                            </td>
                        </tr>
                        <tr>
                            <td>Mô tả khu vực:</td>
                            <td><textarea name="description" rows="3" required></textarea></td>
                        </tr>
                        <tr>
                            <td>Tạo bởi (User ID):</td>
                            <td>
                                <input type="hidden" name="createBy" value="${sessionScope.userID}">
                                <span>${sessionScope.username}</span>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2"><button type="submit">Thêm Khu Vực</button></td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
<!--        <script>
            document.getElementById("image").addEventListener("change", function (event) {
                const file = event.target.files[0];
                if (file) {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        document.getElementById("previewImage").src = e.target.result;
                        document.getElementById("previewImage").style.display = "block";
                    };
                    reader.readAsDataURL(file);
                }
            });
        </script>-->
    </body>
</html>
