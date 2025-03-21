<%-- 
    Document   : historyImportPrice
    Created on : Mar 20, 2025, 1:51:23 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./assets/css/style.css">
        <link rel="stylesheet" href="./assets/fonts/themify-icons/themify-icons.css">
        <title>Bảng Điều Khiển</title>
        <style>
            .table-container {
                overflow-x: auto;
                width: 100%;
                margin-bottom: 20px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                border: none;
            }
            th, td {
                padding: 10px;
                text-align: left;
                border: none;
            }
            th {
                background-color: #f2f2f2;
            }
            .action-button, .blue-button {
                padding: 5px 10px;
                border: none;
                color: white;
                border-radius: 4px;
                cursor: pointer;
                text-decoration: none;
                display: inline-block;
                transition: background-color 0.3s;
            }
            .action-button {
                background-color: #d9534f;
            }
            .action-button:hover {
                background-color: #c9302c;
            }
            .blue-button {
                background-color: #007bff;
            }
            .blue-button:hover {
                background-color: #0056b3;
            }
            .search-container {
                margin-bottom: 20px;
                display: flex;
                align-items: center;
                gap: 10px;
            }
            .search-container label {
                font-weight: bold;
            }
            .search-container input {
                padding: 8px;
                width: 250px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 14px;
            }
            .search-container button {
                padding: 8px 15px;
                border: none;
                background-color: #28a745;
                color: white;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s;
            }
            .search-container button:hover {
                background-color: #218838;
            }
            .pagination {
                margin-top: 20px;
                text-align: center;
            }
            .pagination a {
                padding: 8px 12px;
                margin: 0 4px;
                border: 1px solid #ddd;
                text-decoration: none;
                color: #007bff;
            }
            .pagination a.active {
                background-color: #007bff;
                color: white;
            }
            .sort-container {
                display: flex;
                justify-content: flex-end;
                align-items: center;
                margin-bottom: 10px;
            }

            .sort-container label {
                margin-right: 8px;
            }



        </style>
    </head>
    <body>
        <div id="main">
            <jsp:include page="/Component/menu.jsp"></jsp:include>
                <div class="main-content">
                    <h2>Lịch Sử Giá Xuất</h2>

                    <div class="search-container">

                        <form id="searchForm" action="HistoryExportPriceServlet" method="GET">
                            <label for="searchInput">Tìm kiếm sản phẩm:</label>
                            <input type="text" id="searchInput" name="keyword" placeholder="Nhập tên sản phẩm..." value="${keyword}">

                        <label for="sortOrder">Sắp xếp:</label>
                        <select id="sortOrder" name="sortOrder" onchange="document.getElementById('searchForm').submit();">
                            <option value="desc" <c:if test="${sortOrder == 'desc'}">selected</c:if>>Mới nhất → Cũ nhất</option>
                            <option value="asc" <c:if test="${sortOrder == 'asc'}">selected</c:if>>Cũ nhất → Mới nhất</option>
                            </select>


                            <button type="submit">Tìm kiếm</button>
                        </form>

                    </div>







                    <div style="text-align: right;">
                        <button type="button" onclick="exportToExcel()">Xuất Excel</button>
                        <button type="button" onclick="redirectToSellPriceHistory()">Xem Lịch Sử Giá Nhập</button>
                    </div>
                    <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.18.5/xlsx.full.min.js"></script>


                    <div class="table-container">


                        <table border="1">
                            <thead>
                                <tr>
                                    <th>Hình ảnh</th>
                                    <th>Tên sản phẩm</th>
                                    <th>Giá nhập</th>
                                    <th>Ngày thay đổi</th>
                                    <th>Người thay đổi</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="history" items="${HistoryList}">
                                <tr>
                                    <td>
                                        <img src="${history.image}" alt="Ảnh sản phẩm" width="50" height="50">
                                    </td>
                                    <td>${history.productName}</td>
                                    <td>${history.price}</td>
                                    <td>${history.changedAt}</td>
                                    <td>${history.changedBy}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="pagination">
                    <c:if test="${currentPage > 1}">
                        <a href="HistoryExportPriceServlet?page=${currentPage - 1}&sortOrder=${sortOrder}">&laquo; Trước</a>
                    </c:if>

                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <a href="HistoryExportPriceServlet?page=${i}&sortOrder=${sortOrder}" class="${i == currentPage ? 'active' : ''}">${i}</a>
                    </c:forEach>

                    <c:if test="${currentPage < totalPages}">
                        <a href="HistoryExportPriceServlet?page=${currentPage + 1}&sortOrder=${sortOrder}">Sau &raquo;</a>
                    </c:if>
                </div>




            </div>
        </div>


        <script>
                            async function exportToExcel() {
                                try {
                                    let response = await fetch("ExportExcelDataServlet?priceType=sell");
                                    let data = await response.json();

                                    let worksheet = XLSX.utils.json_to_sheet(data.map(item => ({
                                            "Tên sản phẩm": item.productName,
                                            "Giá": item.price,
                                            "Loại giá": item.priceType,
                                            "Ngày thay đổi": item.changedAt,
                                            "Người thay đổi": item.changedBy // Giờ đây là userName thay vì ID
                                        })));

                                    let workbook = XLSX.utils.book_new();
                                    XLSX.utils.book_append_sheet(workbook, worksheet, "LichSuGiaXuat");

                                    XLSX.writeFile(workbook, "LichSuGiaXuat.xlsx");
                                } catch (error) {
                                    console.error("Lỗi khi xuất Excel:", error);
                                    alert("Có lỗi xảy ra khi xuất Excel!");
                                }
                            }

                            function redirectToSellPriceHistory() {
                                window.location.href = "HistoryImportPriceServlet";
                            }

        </script>



    </body>
</html>
