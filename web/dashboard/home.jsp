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
                    </div>

                    <table>
                        <thead id="table-header">
                            <tr>
                                <th>Mã sản phẩm</th>
                                <th>Tên sản phẩm</th>
                                <th>Người bán</th>
                                <th>Chủ đề</th>
                                <th>Phương thức</th>
                                <th>Công khai</th>
                                <th>Giá tiền</th>
                                <th>Ảnh</th>
                                <th>Nơi chứa sản phẩm</th>
                            </tr>
                        </thead>
                        <tbody id="table-tbody">

                            <tr class="no-rows">

                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>


    </body>



    <script>


    </script>


</html>
