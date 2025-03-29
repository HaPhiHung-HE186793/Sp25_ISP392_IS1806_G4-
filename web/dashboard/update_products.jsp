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
            <jsp:include page="/Component/header.jsp"></jsp:include>
            <div class="menu ">  <jsp:include page="/Component/menu.jsp"></jsp:include> </div>


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

                    <form action="UpdateProduct" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
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
                                <td>
                                    <input type="file" name="image" id="image" accept="image/*">
                                    <br>
                                    <c:if test="${not empty product.image}">
                                        <img id="previewImage" src="${product.image}" alt="Ảnh sản phẩm" style="max-width: 200px; display: block; margin-top: 10px;">
                                    </c:if>
                                    <c:if test="${empty product.image}">
                                        <img id="previewImage" src="#" alt="Xem trước ảnh" style="max-width: 200px; display: none; margin-top: 10px;">
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>Quy cách:</td>
                                <td>
                                    <input type="text" name="unitSize" value="${param.unitSize != null ? param.unitSize : unitSize}" required>
                                    <small>Nhập các giá trị cách nhau bởi dấu phẩy (,)</small>
                                </td>
                            </tr>


                            <tr>
                                <td>Chọn khu vực:</td>
                                <td>
                                    <div class="dropdown-container">
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
                                    </div>
                                    <div class="selected-items" id="selectedItems"></div>
                                </td>
                            </tr>


                            <tr>
                                <td colspan="2">
                                    <button type="button" onclick="window.location.href = 'ListProducts'">Quay lại</button>
                                    <button type="submit">Lưu</button>
                                </td>

                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
        <style>.custom-dropdown {
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
                color: black;
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
            .dropdown-container {
                display: flex;
                justify-content: flex-end; /* Căn sang phải */
                width: 100%;
            }

            .custom-dropdown {
                width: 300px; /* Điều chỉnh độ rộng theo ý muốn */
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

            // Lấy các phần tử cần ẩn/hiện
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
