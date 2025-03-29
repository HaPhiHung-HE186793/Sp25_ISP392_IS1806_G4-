<%-- 
    Document   : updateStore
    Created on : 6 thg 3, 2025, 21:15:57
    Author     : nguyenanh
--%>

<%-- 
    Document   : updateStore
    Created on : 6 thg 3, 2025, 21:15:46
    Author     : nguyenanh
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./assets/css/style.css">
        <link rel="stylesheet" href="./assets/fonts/themify-icons/themify-icons.css">
        <title>Update Store</title>
    </head>
    
    <style>
        :root {
            --primary-color: #2a2a2a;
            --secondary-color: #3a3a3a;
            --accent-color: #4e54c8;
            --text-color: #ffffff;
            --text-secondary: #b3b3b3;
            --border-radius: 12px;
            --box-shadow: 0 8px 30px rgba(0, 0, 0, 0.2);
            --transition: all 0.3s ease;
        }

        .table-container {
        background-color: #222 !important;
        padding: 61px 20px !important;
        border-radius: 4px !important;
        overflow-y: scroll !important;
        scrollbar-width: thin !important;
        scrollbar-color: #555 #333 !important;
        max-height: 600px !important;
    }
    
        

        .store-container {
            max-width: 800px;
            margin: 8px auto;
            padding: 5px;
            background: linear-gradient(145deg, var(--primary-color), var(--secondary-color));
            border-radius: var(--border-radius);
            box-shadow: var(--box-shadow);
            position: relative;
            overflow: hidden;
        }

        .store-container::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 5px;
            background: linear-gradient(90deg, #8ac84e, #37cc33);
        }

        .store-header {
            text-align: center;
            margin-bottom: 15px;
            position: relative;
        }

        .store-header h2 {
            font-size: 32px;
            margin: 15px 0;
            background: linear-gradient(90deg, #8ac84e, #37cc33);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            display: inline-block;
        }

        .store-info {
            display: grid;
            gap: 1px;
            font-size: 16px;
            padding: 12px;
            background-color: rgba(0, 0, 0, 0.2);
            border-radius: var(--border-radius);
            margin-top: 20px;
        }

        .row {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
        }

        .row p {
            display: flex;
            flex-direction: column;
            background-color: rgba(255, 255, 255, 0.05);
            border-radius: 8px;
            padding: 15px;
            transition: var(--transition);
        }

        .row p:hover {
            background-color: rgba(255, 255, 255, 0.1);
            transform: translateY(-3px);
        }

        .store-info label {
            font-weight: bold;
            color: #089308;
            display: flex;
            align-items: center;
            margin-bottom: 8px;
        }

        .store-info label i {
            margin-right: 10px;
            font-size: 18px;
        }

        .store-info input[type="text"],
        .store-info input[type="email"],
        .store-info input[type="file"] {
            width: 100%;
            padding: 5px;
            background-color: rgba(255, 255, 255, 0.1);
            border: 1px solid rgba(255, 255, 255, 0.2);
            border-radius: 4px;
            color: var(--text-color);
            font-size: 14px;
        }

        .store-info input[type="file"] {
            padding: 3px;
        }

        .store-info input[type="radio"] {
            margin-right: 5px;
        }

        .button-group {
            margin-top: 8px;
            display: flex;
            gap: 220px;
            justify-content: center;
        }

        .button-group button {
            padding: 12px 24px;
            font-size: 12px;
            color: white;
            border: none;
            border-radius: var(--border-radius);
            cursor: pointer;
            transition: var(--transition);
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .button-group button:first-child {
            background-color: var(--accent-color);
        }

        .button-group button:last-child {
            background-color: #28a745;
        }

        .button-group button:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
        }

        @media (max-width: 768px) {
            .store-container {
                margin: 20px 10px;
                padding: 20px;
            }

            .store-info {
                padding: 15px;
            }

            .row {
                grid-template-columns: 1fr;
            }

            .button-group {
                flex-direction: column;
            }

            .button-group button {
                width: 100%;
            }
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .store-container {
            animation: fadeIn 0.6s ease-out;
        }
    </style>
    
    <body>
        <div id="main">
            <jsp:include page="/Component/header.jsp"></jsp:include>
            <div class="menu ">  <jsp:include page="/Component/menu.jsp"></jsp:include> </div>  

            <div class="main-content">
                <div class="notification">
                    Thông báo: Mọi người có thể liên hệ admin tại fanpage Group 4
                </div>
                <div class="table-container" >
                        <div style="display: flex">                            
                        <c:choose>
                            <c:when test="${not empty errors}">
                                <div class="notification2" id="errorNotification" style="color: red; max-width: 29%;margin-left: 25%;padding: 0px 5px;">
                                    <c:forEach var="error" items="${errors}">
                                        <p>${error}</p>
                                    </c:forEach>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="notification2" id="errorNotification" style="display: none; max-width: 29%;"></div>
                            </c:otherwise>
                        </c:choose> 
                        <c:choose>
                            <c:when test="${not empty success}">
                                <div class="notification2" id="messageNotification" style="color: green; max-width: 29%;margin-left: 25%;">
                                    ${success}
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="notification2" id="messageNotification" style="display: none; max-width: 29%;"></div>
                            </c:otherwise>
                        </c:choose>                        
                    </div>
                
                <div class="store-container">
                    <div class="store-header">
                        <h2>Cập nhật cửa hàng</h2>
                    </div>
                    <form id="updateStoreForm" action="updatestore" method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="storeId" value="${store.getStoreID()}">
                        <div class="store-info">
                            <div class="row">
                                <p>
                                    <label>Tên cửa hàng:</label>
                                    <input name="storeName" id="storeName" type="text" 
                                           placeholder="Tên cửa hàng" value="${store.getStoreName()}" required>
                                </p>
                                <p>
                                    <label>Logo:</label>
                                    <input name="logo" id="logo" type="file">
                                    <input type="hidden" name="existingImage" value="${store.getLogostore()}">
                                </p>
                            </div>
                            <div class="row">
                                <p>
                                    <label>Số điện thoại</label>
                                    <input name="phone" id="phone" type="text" 
                                           placeholder="Số điện thoại" value="${store.getPhone()}" required>
                                </p>
                                <p>
                                    <label>Email:</label>
                                    <input name="email" id="email" type="email" 
                                           placeholder="Email" value="${store.getEmail()}" >
                                </p>
                            </div>
                            <div class="row">                
                                <p>
                                    <label>Địa chỉ</label>
                                    <input name="address" id="address" type="text" 
                                           placeholder="Địa chỉ" value="${store.getAddress()}" required>
                                </p>
                            </div>
                        </div>
                        <div class="button-group">
                            <c:if test="${user.getRoleID() == 1}">
                            <button type="button" onclick="window.location.href = 'liststore'">
                                Quay về danh sách cửa hàng
                            </button>
                            </c:if>
                            <c:if test="${user.getRoleID() == 2}">
                            <button type="button" onclick="window.location.href = 'storeprofile'">
                                Quay về hồ sơ cửa hàng
                            </button>
                            </c:if>
                            <button type="submit">
                                Cập nhật cửa hàng
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        
        <script>
            function hideNotification(notificationId) {
                setTimeout(function () {
                    var notification = document.getElementById(notificationId);
                    if (notification) {
                        notification.style.display = 'none';
                    }
                }, 3000);
            }
            hideNotification('errorNotification');
            hideNotification('messageNotification');
            
            document.getElementById("logo").addEventListener("change", function () {
            const file = this.files[0]; // Lấy file đầu tiên
            const maxSize = 10 * 1024 * 1024; // 10MB

            if (file) {
                if (file.size > maxSize) {
                    alert("File size exceeds 2MB. Please select a smaller file.");
                    this.value = ""; // Reset input file
                }
            }
        });
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
