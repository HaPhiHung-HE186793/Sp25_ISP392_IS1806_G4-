<%-- 
    Document   : order
    Created on : Feb 8, 2025, 9:08:24 PM
    Author     : TIEN DAT PC
--%>


<%-- 
    Document   : home
    Created on : Feb 8, 2025, 5:59:03 PM
    Author     : TIEN DAT PC
--%>

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
                    </div>

                    <table>
                        <thead id="table-header">
                            <tr>
                                <th>Mã trung gian</th>
                                <th>Trạng thái</th>
                                <th>Người bán</th>
                                <th>Chủ đề</th>
                                <th>Phương thức</th>
                                <th>Công khai</th>
                                <th>Giá tiền</th>
                                <th>Ảnh</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                            <tbody id="table-tbody">

                                <tr class="no-rows">
                                                                    <!--<td colspan="8" style="text-align: center;">No rows found</td>-->
                                                                     <td >1</td>
                                                                    <td >2</td>
                                                                    <td >3</td>
                                                                    <td >4</td>
                                                                    <td >5</td>
                                                                    <td >No rows found</td> 
                                </tr>
                            </tbody>
                    </table>
                </div>
            </div>
        </div>
        
    </div>
    </body>
    
    

    <script>
        // Lấy tbody
        
        // Kiểm tra nếu có dữ liệu để hiển thị
        if (data.length > 0) {
            // Xóa các hàng "No rows found" nếu có
            const noRows = document.querySelectorAll('.no-rows');
            noRows.forEach(row => row.remove());

            // Tạo các hàng dữ liệu
            data.forEach(item => {
                const row = document.createElement("tr");

                // Tạo các ô trong hàng
                row.innerHTML = `
          <td>${item.maTrungGian}</td>
          <td>${item.trangThai}</td>
          <td>${item.nguoiBan}</td>
          <td>${item.chuDe}</td>
          <td>${item.phuongThuc}</td>
          <td>${item.congKhai}</td>
          <td>${item.giaTien}</td>
          <td><img src="${item.anh}" alt="Ảnh" style="width: 50px; height: 50px; object-fit: cover; border-radius: 4px;"></td>
          <td><button style="padding: 5px 10px; border: none; background-color: #d9534f; color: white; border-radius: 4px; cursor: pointer;">Hành động</button></td>
        `;

                tbody.appendChild(row);
            });
        } else {
            // Nếu không có dữ liệu, hiển thị thông báo "No rows found"
            const row = document.createElement("tr");
            row.className = "no-rows";

            const cell = document.createElement("td");
            cell.colSpan = document.querySelectorAll("thead th").length;
            cell.style.textAlign = "center";
            cell.textContent = "No rows found";

            row.appendChild(cell);
            tbody.appendChild(row);
        }
        
        
        


    </script>


</html>
