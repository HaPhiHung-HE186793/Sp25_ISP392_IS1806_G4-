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
                   <jsp:include page="/Component/header.jsp"></jsp:include>
            <div class="menu ">  <jsp:include page="/Component/menu.jsp"></jsp:include> </div>


            <div class="main-content">
                <div class="notification">
                    Thông báo: Mọi người có thể liên hệ admin tại fanpage Group 4
                </div>


                <div class="table-container">
                    <h3>New product</h3>
                    <div class="filters">
                        <select>
                            <option value="">Trạng thái</option>
                            <option value="">A->Z</option>
                            <option value="">Z->A</option>
                        </select>
                        <input type="text" placeholder="Từ">
                        <input type="text" placeholder="Đến">
                        <button type="submit">Save</button>
                        <a href="" class="bg-green-600 text-white px-6 py-2 rounded-md hover:bg-green-700"> Insert Product </a>

                    </div>

                    
                </div>
            </div>
        </div>
        
    </div>
    </body>
    
    

    <script>

       

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

</html>
