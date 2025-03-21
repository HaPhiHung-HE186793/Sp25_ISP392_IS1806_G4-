<%-- 
    Document   : updateUser
    Created on : 17 thg 2, 2025, 22:04:14
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
        <title>Update User</title>
    </head>
<!-- Select2 CSS -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/css/select2.min.css" rel="stylesheet" />
<!-- jQuery và Select2 JS -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/js/select2.min.js"></script>
    <style>
    /* Thay đổi màu nền và màu chữ cho dropdown Select2 */
    .select2-container--default .select2-selection--single {
        background-color: #fff;
        color: #000;
        border: 1px solid #333;
        height: 24px ; /* Giảm chiều cao */        
        line-height: 24px;
    }
    
    /* Màu chữ cho text hiển thị */
    .select2-container--default .select2-selection--single .select2-selection__rendered {
        color: #555;
        line-height: 24px;
        
    }
    
    /* Màu nền cho dropdown menu */
    .select2-container--default .select2-dropdown {
        background-color: #000;
        border: 1px solid #333;
    }
    
    /* Màu chữ và nền cho các option */
    .select2-container--default .select2-results__option {
        color: #fff;
        background-color: #333;
    }
    
    /* Màu nền khi hover option */
    .select2-container--default .select2-results__option--highlighted[aria-selected] {
        background-color: #333;
        color: #fff;
    }
    
    /* Màu nền cho option đã chọn */
    .select2-container--default .select2-results__option[aria-selected=true] {
        background-color: #444;
    }
    
    /* Màu cho mũi tên dropdown */
    .select2-container--default .select2-selection--single .select2-selection__arrow b {
        border-color: #000 transparent transparent transparent;
    }

        /* Search Input */
        .search-box input[type="text"] {
            width: 65%;
            padding: 2px 8px;
            font-size: 13px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
            transition: all 0.3s ease;
        }

        .search-suggestions {
            max-height: 200px;
            overflow-y: auto;
            overflow-x: hidden;
            scrollbar-width: none;
            padding: 5px;
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
                            <h3 style="max-width: 29%;">Cập nhật tài khoản</h3>

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
                        <p id="passError" style="color: red; font-size: 14px; display: none; max-width: 29%;margin-left: 25%;">
                            Mật khẩu xác nhận không khớp!
                        </p>
                    </div>

                    <table>
                        <thead id="table-header">
                            <tr>
                                <th>Tên</th>                               
                                <th>Email</th>
                                <th>Mật khẩu mới</th>
                                <th>Xác nhận mật khẩu</th>
                                <c:if test="${u.getRoleID() == 1}">
                                    <th>Cửa hàng</th>  
                                    </c:if>   
                                <th>Chức năng</th>
                                <th style="border-left: 1px solid black;">Hành động</th>
                            </tr>
                        </thead>
                        <tbody id="table-tbody">
                            <tr>
                        <form id="updateUserForm" action="updateuser" method="POST">
                            <td>
                                <input name="userName" id="userName" type="text" 
                                       placeholder="Tên của bạn" value="${user_update.getUserName()}">
                                <input hidden name="userid" value="${user_update.getID()}">
                            </td>
                            <td>
                                <input name="email" id="email" type="email" 
                                       placeholder="Email của bạn" value="${user_update.getEmail()}">
                            </td> 
                            <td>
                                <input name="password" id="password" type="password" 
                                       placeholder="Mật khẩu mới" >
                            </td>
                            <td>
                                <input name="cfpass" id="cfpass" type="password" 
                                       placeholder="Xác nhận mật khẩu" >                                
                            </td>
                            <c:if test="${u.getRoleID() == 1}">
                                <td>
                                    <select name="storeid" id="sortColumn" class="store-select">
                                        <option value="">Chọn cửa hàng</option>
                                        <c:forEach var="store" items="${storeList}">
                                            <option  value="${store.getStoreID()}" ${store.getStoreID() eq sortColumn ? "selected" : ""}>${store.getStoreName()}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </c:if> 
                            <c:if test="${u.getRoleID() == 2}">
                                <input hidden name="storeid" value="${user.getStoreID()}">
                            </c:if> 
                            <td>
                                <c:if test="${u.getRoleID() == 1}">
                                    <div>
                                        <input type="radio" name="roleID" value="1" id="admin" 
                                               ${user_update.getRoleID() == 1 ? "checked" : ""} required>
                                        <label for="admin">Admin</label>
                                    </div>
                                    <div>
                                        <input type="radio" name="roleID" value="2" id="store_owner" 
                                               ${user_update.getRoleID() == 2 ? "checked" : ""}>
                                        <label for="store_owner">Chủ Cửa Hàng</label>
                                    </div>
                                    <div>
                                        <input type="radio" name="roleID" value="3" id="employee" 
                                               ${user_update.getRoleID() == 3 ? "checked" : ""}>
                                        <label for="employee">Nhân Viên</label>
                                    </div>
                                </c:if>
                                <c:if test="${u.getRoleID() == 2}">
                                    <div>
                                        <input type="radio" name="roleID" value="3" id="employee" 
                                               ${user_update.getRoleID() == 3 ? "checked" : ""}>
                                        <label for="employee">Nhân Viên</label>
                                    </div>
                                </c:if>
                            </td>
                            <td style="border-left: 1px solid black;">                                    
                                <button type="submit" >Cập nhật <br> người dùng</button>
                            </td>
                        </form>
                        </tr>
                        </tbody>
                    </table>                   
                </div>
                <button onclick="window.location.href = 'listusers'"  
                        style="padding: 5px 15px; font-size: 14px; min-width: 80px; background-color: green; color: white; border: none; border-radius: 5px; cursor: pointer; margin-right: 10px;">
                    Quay về danh sách người dùng
                </button>
            </div>                                
        </div>
    </body>
    <script>
        $(document).ready(function () {
        $(".store-select").select2({
            placeholder: "Tìm kiếm cửa hàng...",
            allowClear: true
        });
        // Ẩn dropdown khi click ra ngoài
        $(document).on("click", function (e) {
            if (!$(e.target).closest(".select2-container").length) {
                $(".store-select").select2("close");
            }
        });
    });

        // search store
        $(document).ready(function () {
            $("#searchStoreInput").on("input", function () {
                let query = $(this).val();
                if (query.length > 0) {
                    $.ajax({
                        url: "searchstore",
                        type: "POST",
                        data: {keyword: query},
                        success: function (data) {
                            if (data.trim() !== "") {
                                $("#storeSuggestions").html(data).show();
                            } else {
                                $("#storeSuggestions").hide();
                            }
                        }
                    });
                } else {
                    $("#storeSuggestions").hide();
                }
            });

            // Ẩn dropdown khi click ra ngoài
            $(document).on("click", function (event) {
                if (!$(event.target).closest("#searchStoreInput, #storeSuggestions").length) {
                    $("#storeSuggestions").hide();
                }
            });

            // Hiển thị dropdown khi focus vào ô tìm kiếm (nếu có dữ liệu)
            $("#searchStoreInput").on("focus", function () {
                if ($(this).val().length > 0) {
                    $("#storeSuggestions").show();
                }
            });
        });

// Chọn cửa hàng từ dropdown
        function selectStore(id, name) {
            $("#searchStoreInput").val(name);
            $("#selectedStoreId").val(id);
            $("#storeSuggestions").hide();
        }
        
        // Hàm ẩn thông báo sau 3 giây
        function hideNotification(notificationId) {
            setTimeout(function () {
                var notification = document.getElementById(notificationId);
                if (notification) {
                    notification.style.display = 'none';
                }
            }, 3000); // 3000 milliseconds = 3 seconds
        }

        hideNotification('errorNotification');
        hideNotification('messageNotification');


        document.addEventListener("DOMContentLoaded", function () {
            const password = document.getElementById("password");
            const cfpass = document.getElementById("cfpass");
            const passError = document.getElementById("passError");
            const form = document.getElementById("updateUserForm"); // Đổi thành getElementById

            function validatePassword() {
                if (password.value !== cfpass.value) {
                    passError.style.display = "block"; // Hiện lỗi
                } else {
                    passError.style.display = "none"; // Ẩn lỗi
                }
            }

            // Kiểm tra mật khẩu ngay khi nhập
            password.addEventListener("input", validatePassword);
            cfpass.addEventListener("input", validatePassword);

            form.addEventListener("submit", function (event) {
                if (password.value !== cfpass.value) {
                    event.preventDefault(); // Chặn gửi form về servlet
                    alert("Mật khẩu xác nhận không khớp! Vui lòng nhập lại.");
                    cfpass.focus();
                }
            });
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

</html>