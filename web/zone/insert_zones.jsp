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
            <jsp:include page="/Component/header.jsp"></jsp:include>
            <div class="menu ">  <jsp:include page="/Component/menu.jsp"></jsp:include> </div>

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
                            <td colspan="2">
                                <button type="button" onclick="window.location.href = '${pageContext.request.contextPath}/ListZones'">Quay lại</button>
                                <button type="submit">Thêm Khu Vực</button></td>
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
        <script>// Lấy các phần tử cần ẩn/hiện
            const openAddNew = document.querySelector('.js-hidden-menu'); // Nút toggle
            const newDebt0 = document.querySelector('.menu'); // Menu
            const newDebt1 = document.querySelector('.main-content'); // Nội dung chính
            const newDebt2 = document.querySelector('.sidebar'); // Sidebar

// Kiểm tra trạng thái đã lưu trong localStorage khi trang load
            document.addEventListener("DOMContentLoaded", function () {
                if (localStorage.getItem("menuHidden") === "true") {
                    newDebt0.classList.add('hiden');
                    newDebt1.classList.add('hiden');
                    newDebt2.classList.add('hiden');
                }
            });
// Hàm toggle hiển thị
            function toggleAddNewDebt() {
                newDebt0.classList.toggle('hiden');
                newDebt1.classList.toggle('hiden');
                newDebt2.classList.toggle('hiden');
                // Lưu trạng thái vào localStorage
                const isHidden = newDebt0.classList.contains('hiden');
                localStorage.setItem("menuHidden", isHidden);
            }

// Gán sự kiện click
            openAddNew.addEventListener('click', toggleAddNewDebt);

        </script>
    </body>
</html>
