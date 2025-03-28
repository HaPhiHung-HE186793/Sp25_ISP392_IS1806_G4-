<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/fonts/themify-icons/themify-icons.css">
        <title>Bảng Điều Khiển</title>
    </head>

    <body>
        <div id="main">
            <jsp:include page="/Component/header.jsp"></jsp:include>
            <jsp:include page="/Component/menu.jsp"></jsp:include>
                <div class="main-content">
                    <div class="notification">
                        Thông báo: Mọi người có thể liên hệ admin tại fanpage Group 4
                    </div>
                    <div class="table-container">
                    <c:if test="${not empty message}">
                        <p style="color: red;">${message}</p>
                    </c:if>
                    <h3>Thêm sản phẩm mới</h3>
                    <form method="post" action="/DemoISP/CreateProduct" enctype="multipart/form-data">
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
                                <td>
                                    <input type="text" id="price" name="price" value="${param.price != null ? param.price : product.price}" required oninput="formatPrice(this)">
                                    <span id="error-message" style="color: red;"></span>
                                </td>
                            </tr>
                            <tr>
                                <td>Ảnh:</td>
                                <td>
                                    <input type="file" name="image" id="image" accept="image/*" required>
                                    <br>
                                    <img id="previewImage" src="#" alt="Xem trước ảnh" style="max-width: 200px; display: none; margin-top: 10px;">
                                </td>
                            </tr>
                            <tr>
                            <tr>
                                <td>Chọn khu vực</td>
                                <td>
                                    <div class="custom-dropdown">
                                        <div class="dropdown-btn" onclick="toggleDropdown()">
                                            <span id="selectedText">Chọn khu vực</span>
                                            <span>▼</span>
                                        </div>
                                        <div class="dropdown-content" id="dropdownList">
                                            <c:forEach var="zone" items="${zonesList}">
                                                <label>
                                                    <input type="checkbox" name="zoneIDs" value="${zone.zoneID}" 
                                                           <c:if test="${selectedZones.contains(zone.zoneID)}">checked</c:if> 
                                                               onchange="updateSelection()"> 
                                                    ${zone.zoneName}
                                                </label>
                                            </c:forEach>
                                        </div>
                                    </div>
                                    <div class="selected-items" id="selectedItems"></div>
                                </td>
                            </tr>


                            </tr>
                            <tr>
                                <td>Tạo bởi (User ID):</td>
                                <td>
                                    <input type="hidden" name="createBy" value="${sessionScope.userID}" required>
                                    <span id="createByDisplay">${sessionScope.username}</span>
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
        <style>body {
                background-color: #121212;
                color: white;
                font-family: Arial, sans-serif;
                text-align: center;
            }

            .main-content {
                width: 50%;
                margin: auto;
                text-align: left;
            }

            .notification {
                background-color: red;
                color: white;
                padding: 10px;
                font-weight: bold;
                text-align: center;
            }

            .table-container {
                background-color: #1E1E1E;
                padding: 20px;
                border-radius: 5px;
            }

            h3 {
                text-align: center;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            td {
                padding: 10px;
            }

            td:first-child {
                text-align: right;
                width: 30%;
                font-weight: bold;
            }

            td:last-child {
                text-align: left;
            }

            input, textarea, select {
                width: 100%;
                padding: 10px;
                background-color: white;  /* Đổi nền thành màu trắng */
                color: black;  /* Đổi màu chữ thành đen */
                border: 1px solid gray;
                border-radius: 8px; /* Bo tròn góc */
                display: block;
                box-sizing: border-box;
                font-size: 16px; /* Cỡ chữ lớn hơn */
            }

            button {
                background-color: red;
                color: white;
                padding: 10px 20px;
                border: none;
                cursor: pointer;
                width: 100%;
                display: block;
                margin: 20px auto;
            }

            button:hover {
                background-color: darkred;
            }

            img#previewImage {
                max-width: 200px;
                display: none;
                margin-top: 10px;
            }

            /* Căn chỉnh dropdown Zone cho đều với input trên */
            .select-container {
                width: 100%;
            }

            select {
                width: 100%;
                padding: 10px;
                background-color: #222;
                color: white;
                border: 1px solid gray;
                display: block;
                height: auto;
            }

            input:focus, textarea:focus, select:focus {
                border-color: blue;
                outline: none;
                box-shadow: 0px 0px 5px rgba(0, 0, 255, 0.5);
            }

            .custom-dropdown {
                position: relative;
                width: 100%;
            }

            .dropdown-btn {
                width: 100%;
                padding: 10px;
                background: white;
                color: black;
                border: 1px solid gray;
                border-radius: 8px;
                text-align: left;
                cursor: pointer;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .dropdown-content {
                display: none;
                position: absolute;
                width: 100%;
                background: white;
                border: 1px solid gray;
                border-radius: 8px;
                max-height: 200px;
                overflow-y: auto;
                z-index: 10;
            }

            .dropdown-content label {
                display: block;
                padding: 10px;
                cursor: pointer;
            }

            .dropdown-content label:hover {
                background: lightgray;
            }

            .selected-items {
                display: flex;
                flex-wrap: wrap;
                gap: 5px;
                margin-top: 5px;
            }


            /* Đảm bảo chữ dropdown luôn màu đen */
            .dropdown-content {
                background: white;
                color: black; /* Chữ màu đen */
                border: 1px solid gray;
                border-radius: 8px;
                max-height: 200px;
                overflow-y: auto;
                z-index: 10;
                padding: 5px;
            }

            .dropdown-content label {
                display: flex;
                align-items: center;
                padding: 10px;
                cursor: pointer;
                color: black; /* Đảm bảo chữ luôn hiển thị rõ */
            }

            .dropdown-content label:hover {
                background: lightgray;
            }

            /* Căn chỉnh checkbox */
            .dropdown-content input[type="checkbox"] {
                appearance: none;
                width: 16px;
                height: 16px;
                border: 2px solid gray;
                border-radius: 3px;
                margin-right: 8px;
                position: relative;
                cursor: pointer;
            }

            /* Tạo dấu tick */
            .dropdown-content input[type="checkbox"]:checked {
                background-color: blue;
                border-color: blue;
            }

            .dropdown-content input[type="checkbox"]:checked::after {
                content: "✔";
                font-size: 14px;
                font-weight: bold;
                color: white;
                position: absolute;
                left: 2px;
                top: -2px;
            }



        </style>
        <script>
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

            function toggleDropdown() {
                let dropdown = document.getElementById("dropdownList");
                dropdown.style.display = dropdown.style.display === "block" ? "none" : "block";
            }

            function updateSelection() {
                let checkboxes = document.querySelectorAll(".dropdown-content input[type='checkbox']");
                let selectedItemsDiv = document.getElementById("selectedItems");
                let selectedText = document.getElementById("selectedText");

                selectedItemsDiv.innerHTML = "";
                let selectedZones = [];

                checkboxes.forEach(checkbox => {
                    if (checkbox.checked) {
                        let zoneName = checkbox.parentElement.textContent.trim();
                        selectedZones.push(zoneName);

                        let span = document.createElement("span");
                        span.className = "selected-item";
                        selectedItemsDiv.appendChild(span);
                    }
                });

                selectedText.textContent = selectedZones.length > 0 ? selectedZones.join(", ") : "Chọn khu vực";
            }


            document.addEventListener("click", function (event) {
                let dropdown = document.getElementById("dropdownList");
                let button = document.querySelector(".dropdown-btn");

                if (!dropdown.contains(event.target) && !button.contains(event.target)) {
                    dropdown.style.display = "none";
                }
            });



        </script>
    </body>
</html>
