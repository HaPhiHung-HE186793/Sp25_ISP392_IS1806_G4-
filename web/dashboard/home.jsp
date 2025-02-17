<%-- 
    Document   : home
    Created on : Feb 8, 2025, 5:59:03 PM
    Author     : TIEN DAT PC
--%>
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
                            <input type="text" placeholder="Từ">
                            <input type="text" placeholder="Đến">
                            <button>Bỏ lọc</button>
                            <button>Thu gọn</button>
                            <a href="./dashboard/insert_product.jsp"><button>Thêm gạo</button></a>
                            <a href="/DemoISP/ListProductCheckIs"><button>Thêm check</button></a>
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
                                    <th>Thời gian tạo</th>
                                    <th>Cập nhật lần cuối </th>
                                    <th>Tạo bởi</th>
                                    <th>Trạng thái</th>
                                    <th>Xóa ở</th>
                                    <th>Xóa bởi</th>
                                </tr>
                            </thead>
                        <c:forEach items="${products}" var="p">
                            <c:if test="${not p.isIsDelete()}"> <%-- Only show if isDelete is false --%>
                                <tr class="no-rows">
                                    <td>${p.getProductID()}</td>
                                    <td>${p.getProductName()}</td>
                                    <td>${p.getDescription()}</td>
                                    <td>${p.getPrice()}</td>
                                    <td>${p.getQuantity()}</td>
                                    <td>${p.getImage()}</td>
                                    <td>${p.getCreateAt()}</td>
                                    <td>${p.getUpdateAt()}</td>
                                    <td>${p.getCreateBy()}</td>
                                    <td>${p.isIsDelete()}</td> <%-- Still display the isDelete value --%>
                                    <td>${p.getDeleteAt()}</td>
                                    <td>${p.getDeleteBy()}</td>
                                    <td>
                                        <c:if test="${not p.isIsDelete()}"> <%-- Delete link only if not deleted --%>
                                            <a href="DeleteProduct?productID=${p.getProductID()}" 
                                               onclick="return confirm('Are you sure you want to delete this product?')">
                                                Delete
                                            </a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:if> <%-- End of the if condition --%>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>


    </body>



    <script>


    </script>


</html>
