<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                            <form action="ListRice" method="get">
                                <input type="text" name="search" placeholder="Nhập tên gạo">
                                <input type="text" name="search2" placeholder="Nhập mô tả">
                                <button>Bỏ lọc</button>
                                <button type="submit">Tìm kiếm</button>
                            </form>

                        <c:if test="${sessionScope.roleID == 2}"> <%-- Check if roleID is 1 --%>
                            <a href="./dashboard/insert_product.jsp"><button>Thêm gạo</button></a>
                            <a href="/DemoISP/ListProductCheckIs"><button>Thêm check</button></a>
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

                                <c:if test="${(sessionScope.roleID == 2 || sessionScope.roleID == 3)}"> <%-- Check if roleID is 1 --%>
                                    <th>Thời gian tạo</th>
                                    <th>Cập nhật lần cuối </th>
                                    <th>Tạo bởi</th>
                                    <th>Trạng thái</th>
                                    </c:if> <%-- End of roleID check for table headers --%>

                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${products}" var="p">
                                <c:if test="${not p.isIsDelete()}">
                                    <tr class="no-rows">
                                        <td>${p.getProductID()}</td>
                                        <td>${p.getProductName()}</td>
                                        <td>${p.getDescription()}</td>
                                        <td>${p.getPrice()}</td>
                                        <td>${p.getQuantity()}</td>
                                        <td><img src="${p.getImage()}"></td>

                                        <c:if test="${(sessionScope.roleID == 2 || sessionScope.roleID == 3)}"> <%-- Check if roleID is 1 --%>
                                            <td>${p.getCreateAt()}</td>
                                            <td>${p.getUpdateAt()}</td>
                                            <td>${p.getCreateBy()}</td>
                                            <td>${p.isIsDelete()}</td>

                                        </c:if> <%-- End of roleID check for table data --%>

                                        <c:if test="${(sessionScope.roleID == 2 || sessionScope.roleID == 3)}"> <%-- Check if roleID is 1 --%>
                                            <td><a href="UpdateProduct?productID=${p.getProductID()}"><button>Sửa</button></a></td>
                                        </c:if> <%-- End of roleID check for table data --%>


                                    </tr>
                                </c:if>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>

    <script>

    </script>

</html>