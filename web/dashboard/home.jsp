<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./assets/css/style.css">
        <link rel="stylesheet" href="./assets/fonts/themify-icons/themify-icons.css">
        <title>Bảng Điều Khiển</title>
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
                        <h3>Sản phẩm</h3>
                        <div class="filters">
                            <form action="ListRice" method="get">
                                <input type="text" name="nameSearch" placeholder="Nhập tên gạo" value="${nameSearch}">
                            <input type="text" name="descSearch" placeholder="Nhập mô tả" value="${descSearch}">

                            <select name="stockStatus">
                                <option value="">Trạng thái</option>
                                <option value="available" ${stockStatus == 'available' ? 'selected' : ''}>Còn hàng</option>
                                <option value="outofstock" ${stockStatus == 'outofstock' ? 'selected' : ''}>Hết hàng</option>
                            </select>

                            <select name="priceSort">
                                <option value="">Sắp xếp theo giá</option>
                                <option value="asc" ${priceSort == 'asc' ? 'selected' : ''}>Giá thấp đến cao</option>
                                <option value="desc" ${priceSort == 'desc' ? 'selected' : ''}>Giá cao đến thấp</option>
                            </select>

                            <button type="submit">Tìm kiếm</button>
                            <a href="ListProducts"><button type="button">Bỏ lọc</button></a>
                        </form>




                        <c:if test="${sessionScope.roleID == 2}"> <%-- Check if roleID is 1 --%>
                            <a href="CreateProduct"><button>Thêm gạo</button></a>
                        </c:if> <%-- End of roleID check for buttons --%>

                    </div>

                    <table>
                        <thead id="table-header">
                            <tr>
                                <th>Mã gạo</th>
                                <th>Tên gạo</th>
                                <th>Mô tả</th>
                                <th>Giá</th>
                                <th>Số lượng</th>
                                <th>Ảnh</th>

                                <c:if test="${sessionScope.roleID == 2}"> <%-- Check if roleID is 1 --%>
                                    <th>Thời gian tạo</th>
                                    <th>Cập nhật lần cuối </th>

                                    <th>Ngừng bán</th>
                                    </c:if> <%-- End of roleID check for table headers --%>
                                <th>Hành động</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody id="productTableBody">
                            <c:forEach items="${products}" var="p" begin="${sessionScope.page.getStartItem()}" end="${sessionScope.page.getLastItem()}">
                                <c:if test="${(sessionScope.roleID == 2) or (not p.isIsDelete() and (sessionScope.roleID != 1))}">
                                    <tr class="no-rows">
                                        <td>${p.getProductID()}</td>
                                        <td>${p.getProductName()}</td>
                                        <td>${p.getDescription()}</td>
                                        <td>${p.getPrice()}</td>
                                        <td>${p.getQuantity()}</td>
                                        <td><img src="${p.getImage()}" alt="ảnh" style="width: 180px; height: 180px;"></td>

                                        <c:if test="${sessionScope.roleID == 2}"> <%-- Check if roleID is 1 --%>
                                            <td>${p.getCreateAt()}</td>
                                            <td>${p.getUpdateAt()}</td>

                                            <td><form action="UpdateIsDeleteServlet" method="post">
                                                    <input type="hidden" name="productId" value="${p.getProductID()}">
                                                    <input type="checkbox" name="isDeleted" value="true" ${p.isIsDelete() ? 'checked' : ''} 
                                                           onchange="this.form.submit()">  <%-- Submit form khi checkbox thay đổi --%>
                                                </form></td>

                                        </c:if> <%-- End of roleID check for table data --%>

                                        <c:if test="${sessionScope.roleID == 2}"> <%-- Check if roleID is 1 --%>
                                            <td><a href="UpdateProduct?productID=${p.getProductID()}"><button>Sửa</button></a></td>
                                            </c:if>
                                            <c:if test="${sessionScope.roleID == 2  or sessionScope.roleID == 3}">
                                            <td><a href="ProductDetail?productID=${p.getProductID()}"><button>Chi tiết</button></a></td>
                                        </c:if> <%-- End of roleID check for table data --%>


                                    </tr>
                                </c:if>
                            </c:forEach>
                        </tbody>
                    </table>

                    <script>

                    </script>
                </div>
            </div>
        </div>
        <%@include file="/Component/pagination.jsp" %>
    </body>

    <script>
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

    </script>

</html>