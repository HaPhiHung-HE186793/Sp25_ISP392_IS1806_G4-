<%-- 
    Document   : historyImportPrice
    Created on : Mar 20, 2025, 1:51:23 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
            .search-container input[type="date"] {
                padding: 5px;         /* Giảm khoảng padding bên trong input để input gọn hơn */
                width: 140px;         /* Giảm chiều rộng của ô nhập ngày */
                font-size: 12px;      /* Giảm kích thước chữ bên trong ô nhập ngày */
            }

            .search-container {
                margin-bottom: 20px;  /* Giữ khoảng cách giữa phần tìm kiếm và các phần bên dưới */
                display: flex;        /* Xếp các thành phần (label, input) ngang hàng với nhau */
                align-items: center;  /* Căn chỉnh các thành phần theo chiều dọc cho đều nhau */
                gap: 10px;            /* Khoảng cách giữa các thành phần (label, input, button) */
                flex-wrap: wrap;      /* Nếu không đủ không gian, các thành phần sẽ tự động xuống dòng */
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

                        <jsp:include page="/Component/header.jsp"></jsp:include>
            <div class="menu ">  <jsp:include page="/Component/menu.jsp"></jsp:include> </div>



                <div class="main-content">
                    <h2>Lịch Sử Giá Nhập</h2>
                    

                    <div class="search-container">

                        <form id="searchForm" action="HistoryImportPriceServlet" method="GET">
                            <label for="searchInput">Tìm kiếm sản phẩm:</label>
                            <input type="text" id="searchInput" name="keyword" placeholder="Nhập tên sản phẩm..." value="${keyword}">

                        <label for="sortOrder">Sắp xếp:</label>
                        <select id="sortOrder" name="sortOrder" onchange="document.getElementById('searchForm').submit();">
                            <option value="desc" <c:if test="${sortOrder == 'desc'}">selected</c:if>>Mới nhất → Cũ nhất</option>
                            <option value="asc" <c:if test="${sortOrder == 'asc'}">selected</c:if>>Cũ nhất → Mới nhất</option>
                            </select>

                            

                        <label for="endDate">Đến ngày:</label>
                        <input type="date" id="endDate" name="endDate" value="${endDate}">

                        <button type="submit">Tìm kiếm</button>
                        <button type="button" onclick="resetFilters()">Xóa bộ lọc</button>  
                    </form>


                </div>







                <div style="text-align: right;">
                    <button type="button" onclick="exportToExcel()">Xuất Excel</button>
                    <button type="button" onclick="redirectToSellPriceHistory()">Xem Lịch Sử Giá Bán</button>
                </div>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.18.5/xlsx.full.min.js"></script>


                <div class="table-container">


                    <table border="1">
                        <thead>
                            <tr>
                                <th>Hình ảnh</th>
                                <th>Tên sản phẩm</th>
                                <th>Giá nhập</th>
                                <th>Nhà Cung Cấp</th>
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
                                    <td>
                                        <fmt:formatNumber value="${history.price}" type="number" groupingUsed="true" />
                                    </td>
                                    <td>${history.supplier}</td>
                                    <td>${history.changedAt}</td>
                                    <td>${history.changedBy}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="pagination">
                    <c:if test="${currentPage > 1}">
                        <a href="<c:url value='HistoryImportPriceServlet'>
                               <c:param name='page' value='${currentPage - 1}' />
                               <c:param name='sortOrder' value='${sortOrder}' />
                              
                               <c:param name='endDate' value='${endDate}' />
                               <c:param name='keyword' value='${keyword}' />
                           </c:url>">&laquo; Trước</a>
                    </c:if>

                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <a href="<c:url value='HistoryImportPriceServlet'>
                               <c:param name='page' value='${i}' />
                               <c:param name='sortOrder' value='${sortOrder}' />
                              
                               <c:param name='endDate' value='${endDate}' />
                               <c:param name='keyword' value='${keyword}' />
                           </c:url>" class="${i == currentPage ? 'active' : ''}">${i}</a>
                    </c:forEach>

                    <c:if test="${currentPage < totalPages}">
                        <a href="<c:url value='HistoryImportPriceServlet'>
                               <c:param name='page' value='${currentPage + 1}' />
                               <c:param name='sortOrder' value='${sortOrder}' />
                               
                               <c:param name='endDate' value='${endDate}' />
                               <c:param name='keyword' value='${keyword}' />
                           </c:url>">Sau &raquo;</a>
                    </c:if>
                </div>






            </div>
        </div>


        <script>
                        async function exportToExcel() {
                            try {
                                let response = await fetch("ExportExcelDataServlet?priceType=import");
                                let data = await response.json();

                                let worksheet = XLSX.utils.json_to_sheet(data.map(item => ({
                                        "Tên sản phẩm": item.productName,
                                        "Giá": item.price,
                                        "Nhà Cung Cấp": item.supplier,
                                        "Ngày thay đổi": item.changedAt,
                                        "Người thay đổi": item.changedBy // Giờ đây là userName thay vì ID
                                    })));

                                let workbook = XLSX.utils.book_new();
                                XLSX.utils.book_append_sheet(workbook, worksheet, "LichSuGiaNhap");

                                XLSX.writeFile(workbook, "LichSuGiaNhap.xlsx");
                            } catch (error) {
                                console.error("Lỗi khi xuất Excel:", error);
                                alert("Có lỗi xảy ra khi xuất Excel!");
                            }
                        }

                        function redirectToSellPriceHistory() {
                            window.location.href = "HistoryExportPriceServlet";
                        }

        </script>
        <script>
            function resetFilters() {
                document.getElementById('searchInput').value = ''; // Xóa từ khóa tìm kiếm
                document.getElementById('sortOrder').value = 'desc'; // Đặt sắp xếp về mặc định
                
                document.getElementById('endDate').value = '';   // Xóa ngày kết thúc
                document.getElementById('searchForm').submit(); // Submit lại form
            }



        </script>
        <script>

            // Lấy các phần tử cần ẩn/hiện
            const openAddNewDebt = document.querySelector('.js-hidden-menu'); // Nút toggle
            const newDebt = document.querySelector('.menu'); // Menu
            const newDebt1 = document.querySelector('.main-content'); // Nội dung chính
            const newDebt2 = document.querySelector('.sidebar'); // Sidebar

// Kiểm tra trạng thái đã lưu trong localStorage khi trang load
            document.addEventListener("DOMContentLoaded", function () {
                if (localStorage.getItem("menuHidden") === "true") {
                    newDebt.classList.add('hiden');
                    newDebt1.classList.add('hiden');
                    newDebt2.classList.add('hiden');
                }
            });

// Hàm toggle hiển thị
            function toggleAddNewDebt() {
                newDebt.classList.toggle('hiden');
                newDebt1.classList.toggle('hiden');
                newDebt2.classList.toggle('hiden');

                // Lưu trạng thái vào localStorage
                const isHidden = newDebt.classList.contains('hiden');
                localStorage.setItem("menuHidden", isHidden);
            }

// Gán sự kiện click
            openAddNewDebt.addEventListener('click', toggleAddNewDebt);


        </script>

   <script>
                
       // Lấy các phần tử cần ẩn/hiện
                        const openAddNewDebt = document.querySelector('.js-hidden-menu'); // Nút toggle
                        const newDebt = document.querySelector('.menu'); // Menu
                        const newDebt1 = document.querySelector('.main-content'); // Nội dung chính
                        const newDebt2 = document.querySelector('.sidebar'); // Sidebar

// Kiểm tra trạng thái đã lưu trong localStorage khi trang load
                        document.addEventListener("DOMContentLoaded", function () {
                            if (localStorage.getItem("menuHidden") === "true") {
                                newDebt.classList.add('hiden');
                                newDebt1.classList.add('hiden');
                                newDebt2.classList.add('hiden');
                            }
                        });

// Hàm toggle hiển thị
                        function toggleAddNewDebt() {
                            newDebt.classList.toggle('hiden');
                            newDebt1.classList.toggle('hiden');
                            newDebt2.classList.toggle('hiden');

                            // Lưu trạng thái vào localStorage
                            const isHidden = newDebt.classList.contains('hiden');
                            localStorage.setItem("menuHidden", isHidden);
                        }

// Gán sự kiện click
                        openAddNewDebt.addEventListener('click', toggleAddNewDebt);

                
            </script>


    </body>
</html>
