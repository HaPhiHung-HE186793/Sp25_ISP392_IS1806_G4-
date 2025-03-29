<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./assets/css/style.css">
        <link rel="stylesheet" href="./assets/fonts/themify-icons/themify-icons.css">
        <title>Bảng Điều Khiển</title>
    </head>
    <body>
        <div id="main">
            <jsp:include page="/Component/menu.jsp" />

            <div class="main-content">
                <div class="notification">
                    Thông báo: Mọi người có thể liên hệ admin tại fanpage Group 4
                </div>

                <div class="table-container">
                    <h3>Danh Sách Khu Vực</h3>

                    <!-- Form tìm kiếm đơn giản, không có script -->
                    <div class="filters">
                        <form id="searchForm" action="SearchZones" method="get">
                            <input type="text" name="zoneSearch" id="zoneSearch" placeholder="Nhập tên khu vực" value="${param.zoneSearch}">

                            <select name="sortOrder" id="sortOrder">
                                <option value="">Sắp xếp</option>
                                <option value="asc" ${param.sortOrder eq 'asc' ? 'selected' : ''}>A-Z</option>
                                <option value="desc" ${param.sortOrder eq 'desc' ? 'selected' : ''}>Z-A</option>
                            </select>

                            <button type="submit">Tìm kiếm</button>
                            <a href="ListZones"><button type="button">Bỏ lọc</button></a>

                        </form>

                        <c:if test="${sessionScope.roleID == 2}">
                            <a href="zone/insert_zones.jsp"><button>Thêm Khu Vực</button></a>
                        </c:if>
                    </div>

                    <table>
                        <thead>
                            <tr>
                                <th>Mã khu vực</th>
                                <th>Tên khu vực</th>
                                <th>Ảnh</th>
                                <th>Ngày tạo</th>
                                <th>Cập nhật lần cuối</th>
                                <th>Mô tả</th>
                                <th>Trạng thái</th>
                                    <c:if test="${sessionScope.roleID == 2}">
                                    <th>Hành động</th>
                                    </c:if>
                            </tr>
                        </thead>
                        <tbody id="zoneTableBody">
                            <c:forEach var="zone" items="${zones}">
                                <tr>
                                    <td>${zone.zoneID}</td>
                                    <td>${zone.zoneName}</td>
                                    <td>
                                        <c:if test="${not empty zone.image}">
                                            <img src="${zone.image}" 
                                                 alt="Zone Image" width="80" height="80"
                                                 style="object-fit: cover; border-radius: 5px;">
                                        </c:if>
                                    </td>
                                    <td>${zone.createAt}</td>
                                    <td>${zone.updateAt}</td>
                                    <td>${zone.navigation}</td>
                                    <td>
                                        <form action="UpdateZoneIsDeleteServlet" method="post">
                                            <input type="hidden" name="zoneID" value="${zone.zoneID}">
                                            <input type="checkbox" name="isDeleted" value="true" 
                                                   ${zone.isDelete ? 'checked' : ''}
                                                   onchange="this.form.submit()"> 
                                        </form>
                                    </td>
                                    <c:if test="${sessionScope.roleID == 2}">
                                        <td>
                                            <a href="UpdateZones?zoneID=${zone.zoneID}"><button>Sửa</button></a>
                                        </td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <%@ include file="/Component/pagination.jsp" %>

        <style>
            /* Responsive */
            @media (max-width: 768px) {
                .modal-content {
                    width: 90%;
                }
            }
        </style>
    </body>
</html>
