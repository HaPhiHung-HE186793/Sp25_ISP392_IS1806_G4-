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
            <jsp:include page="/Component/menu.jsp"></jsp:include>

                <div class="main-content">
                    <div class="notification">
                        Thông báo: Mọi người có thể liên hệ admin tại fanpage Group 4
                    </div>

                    <div class="table-container">
                        <h3>Sản phẩm</h3>
                        <div class="filters">
                            <select>
                                <option value="">Trạng thái</option>
                                <option value="">A->Z</option>
                                <option value="">Z->A</option>
                            </select>
                            <form id="searchForm">
                                <input type="text" name="search" id="searchInput" placeholder="Nhập tên gạo hoặc mô tả" value="${param.search}">
                            <button type="button" onclick="clearSearch()">Bỏ lọc</button>
                        </form>



                        <c:if test="${sessionScope.roleID == 2}"> <%-- Check if roleID is 1 --%>
                            <a href="./dashboard/insert_product.jsp"><button>Thêm gạo</button></a>
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

                            </tr>
                        </thead>
                        <tbody id="productTableBody">
                            <c:forEach items="${products}" var="p" begin="${sessionScope.page.getStartItem()}" end="${sessionScope.page.getLastItem()}">
                                <c:if test="${(sessionScope.roleID == 2) or (not p.isIsDelete() and (sessionScope.roleID != 1 and sessionScope.roleID != 3))}">
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
                                        </c:if> <%-- End of roleID check for table data --%>


                                    </tr>
                                </c:if>
                            </c:forEach>
                        </tbody>
                    </table>

                    <script>
                        document.getElementById("searchInput").addEventListener("input", function () {
                            fetchProducts();
                        });

                        function clearSearch() {
                            document.getElementById("searchInput").value = "";
                            fetchProducts();
                        }

                        function fetchProducts() {
                            let keyword = document.getElementById("searchInput").value;

                            fetch("ListRice?search=" + encodeURIComponent(keyword), {
                                method: "GET",
                                headers: {"X-Requested-With": "XMLHttpRequest"}
                            })
                                    .then(response => response.text())
                                    .then(data => {
// Parse nội dung HTML trả về và cập nhật bảng sản phẩm
                                        let tempDiv = document.createElement("div");
                                        tempDiv.innerHTML = data;
                                        let newTableBody = tempDiv.querySelector("#productTableBody").innerHTML;
                                        document.getElementById("productTableBody").innerHTML = newTableBody;
                                    });
                        }
                    </script>
                </div>
            </div>
        </div>
        <%@include file="/Component/pagination.jsp" %>
    </body>

    <script>

    </script>

</html>