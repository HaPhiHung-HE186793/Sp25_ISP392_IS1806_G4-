<%-- 
    Document   : createUser
    Created on : 11 thg 2, 2025, 23:06:15
    Author     : nguyenanh
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.User" %>
<%@ page import="DAO.DAOUser" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./assets/css/style.css">
        <link rel="stylesheet" href="./assets/fonts/themify-icons/themify-icons.css">
        <title>List Schedule</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    </head>
    <style>
        /* Enhanced Modal and UI Styles */
        

        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.5);
            animation: fadeIn 0.3s ease;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
            }
            to {
                opacity: 1;
            }
        }

        .modal-content {
            background-color: #fefefe;
            margin: 10% auto;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
            width: 80%;
            max-width: 600px;
            animation: slideDown 0.3s ease;
        }

        @keyframes slideDown {
            from {
                transform: translateY(-30px);
                opacity: 0;
            }
            to {
                transform: translateY(0);
                opacity: 1;
            }
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
            transition: color 0.2s;
            margin-top: -10px;
        }

        .close:hover {
            color: #333;
        }

        .form-row {
            margin-bottom: 15px;
            display: flex;
            align-items: center;
        }

        .form-row label {
            display: inline-block;
            width: 180px;
            font-weight: 500;
            color: #555;
            margin-right: 10px;
        }

        .form-row input, .form-row select {
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            flex: 1;
            transition: border-color 0.2s, box-shadow 0.2s;
        }

        .form-row input:focus, .form-row select:focus {
            border-color: #4CAF50;
            box-shadow: 0 0 0 3px rgba(76, 175, 80, 0.2);
            outline: none;
        }

        .time-inputs {
            display: flex;
            align-items: center;
            gap: 10px;
            flex: 1;
        }

        .time-inputs input {
            flex: 1;
        }

        .time-inputs span {
            font-weight: 500;
            color: #555;
        }

        .btn-container {
            display: flex;
            justify-content: flex-end;
            gap: 10px;
            margin-top: 20px;
            padding-top: 15px;
            border-top: 1px solid #eee;
        }

        .btn {
            padding: 8px 16px;
            border-radius: 4px;
            font-size: 14px;
            font-weight: 500;
            cursor: pointer;
            transition: background-color 0.2s, transform 0.1s;
            border: none;
        }

        .btn:active {
            transform: translateY(1px);
        }

        .btn {
            background-color: #4CAF50;
            color: white;
        }

        .btn:hover {
            background-color: #3e9142;
        }

        .btn-secondary {
            background-color: #f1f1f1;
            color: #333;
        }

        .btn-secondary:hover {
            background-color: #e5e5e5;
        }

        /* Toggle Button Styles */
        .toggle-button {
            position: relative;
            display: inline-block;
            width: 60px;
            height: 30px;
            border-radius: 15px;
            background-color: #4CAF50;
            color: transparent;
            cursor: pointer;
            transition: background-color 0.3s;
            border: none;
            outline: none;
            overflow: hidden;
        }

        .toggle-button::before {
            content: "";
            position: absolute;
            top: 3px;
            left: 3px;
            width: 24px;
            height: 24px;
            border-radius: 50%;
            background-color: white;
            transition: transform 0.3s;
        }

        .toggle-button::after {
            content: "ON";
            position: absolute;
            right: 10px;
            top: 50%;
            transform: translateY(-50%);
            color: white;
            font-size: 12px;
            font-weight: bold;
        }

        .toggle-button[data-state="off"] {
            background-color: #f44336;
        }

        .toggle-button[data-state="off"]::before {
            transform: translateX(30px);
        }

        .toggle-button[data-state="off"]::after {
            content: "OFF";
            left: 10px;
            right: auto;
        }

        /* Notification Styles */
        .notification-message {
            position: fixed;
            top: 20px;
            right: 20px;
            padding: 12px 20px;
            border-radius: 4px;
            background-color: #d4edda;
            color: #155724;
            box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1);
            z-index: 1001;
            animation: slideIn 0.3s ease, fadeOut 0.3s ease 2.7s forwards;
            max-width: 300px;
        }

        .notification-message.error {
            background-color: #f8d7da;
            color: #721c24;
        }

        @keyframes slideIn {
            from {
                transform: translateX(100%);
                opacity: 0;
            }
            to {
                transform: translateX(0);
                opacity: 1;
            }
        }

        @keyframes fadeOut {
            from {
                opacity: 1;
            }
            to {
                opacity: 0;
                visibility: hidden;
            }
        }

        .status-active {
            background-color: #4caf50;
            color: white;
            padding: 8px 15px;
            border-radius: 20px;
            font-size: 14px;
            display: inline-block;
        }

        .status-locked {
            background-color: #e74c3c;
            color: white;
            padding: 8px 15px;
            border-radius: 20px;
            font-size: 14px;
            display: inline-block;
        }

        .action-btn {
            background: none;
            border: none;
            cursor: pointer;
            margin-right: 10px;
            font-size: 18px;
        }

        .edit-btn {
            color: #31b0d5;
        }

        .power-btn-active {
            color: #2ecc71;
        }

        .power-btn-locked {
            color: #e74c3c;
        }
    </style>

    <body>
        <div id="main">
            <jsp:include page="/Component/menu.jsp"></jsp:include>   

                <div class="main-content">
                    <div class="notification">
                        Thông báo: Mọi người có thể liên hệ admin tại fanpage <a style="color: #5bc0de; text-decoration: none; transition: 0.3s;" href="https://github.com/HaPhiHung-HE186793/Sp25_ISP392_IS1806_G4-" target="_blank">
                            Group 4 ISP392
                        </a>
                    </div>
                    <div class="table-container">
                        <div style="display: flex; gap: 300px;">
                            <h3>Danh sách lịch làm việc</h3>

                        <c:choose>
                            <c:when test="${not empty error}">
                                <div class="notification2" id="errorNotification" style="color: red;">
                                    ${error}
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="notification2" id="errorNotification" style="display: none;"></div>
                            </c:otherwise>
                        </c:choose>                                
                        <div class="notification2" id="error-message" style="color: red; display: none;"></div>

                        <c:choose>
                            <c:when test="${not empty mess}">
                                <div class="notification2" id="messageNotification" style="color: green;">
                                    ${mess}
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="notification2" id="messageNotification" style="display: none;"></div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="filters">
                        <form method="post" action="workschedule">

                            <input type="text" name="keyword" id="keyword" placeholder="Nhập từ khóa..." value="${param.keyword}">
                            <input name="startDate" id="startDate" type="date" value="${startDate}">
                            <input name="endDate" id="endDate" type="date" value="${endDate}">
                            <select name="selectedAction" id="action" onchange="this.form.submit()">
                                <option value="-1" ${selectedAction == -1 ? 'selected' : ''}>Tất cả trạng thái</option>
                                <option value="0" ${selectedAction == 0 ? 'selected' : ''}>Hoạt động</option>
                                <option value="1" ${selectedAction == 1 ? 'selected' : ''}>Đã khóa</option>                     
                            </select>


                            <button type="submit">Tìm kiếm</button>
                            <button type="reset" onclick="window.location = 'workschedule'">Bỏ lọc</button>
                        </form>


                        <div>
                            <button style="background-color: green;" onclick="openModal()">Thêm lịch làm việc</button>
                        </div>

                    </div>

                    <table>
                        <thead id="table-header">
                            <tr>
                                <th>Stt</th>
                                <th>Ca làm việc</th>
                                <th>Thời gian</th>
                                <th>Tổng giờ làm việc</th>
                                <th>Người tạo</th>
                                <th>Ngày tạo</th>
                                <th>Trạng thái</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody id="table-tbody">
                            <c:set var="rowCount" value="0" />
                            <c:forEach items="${schedule}" var="s" begin="${sessionScope.page.getStartItem()}" end="${sessionScope.page.getLastItem()}" varStatus="status">
                                <tr class="no-rows">
                                    <!--<td colspan="8" style="text-align: center;">No rows found</td>-->

                                    <td>${s.getScheduleID()}</td>
                                    <td>${s.getScheduleName()}</td>
                                    <td>${s.getStartDate().substring(0, 5)} - ${s.getEndDate().substring(0, 5)}</td>
                                    <td>${s.getWorkDuration()}</td>
                                    <td>${s.getCreateName()}</td>
                                    <td>${s.getCreateAt().substring(0, 10)}</td>
                                    <td>${s.isIsDelete() ? "Đã khóa" : "Hoạt động"}</span></td>
                                    <td>
                                        <div style="display: flex; gap: 10px; align-items: center;">
                                        <button class="action-btn edit-btn"><i class="fas fa-edit" onclick="window.location.href = 'workschedule?id=${s.getScheduleID()}'"></i></button>                                        
                                        <c:choose>
                                            <c:when test="${s.isIsDelete()}">
                                                <form class="toggleStatusForm" data-action="unlock">
                                                    <input type="hidden" name="ScheduleIDBlock" value="${s.getScheduleID()}">                                                    
                                                    <button class="action-btn power-btn-locked toggleStatusBtn"><i class="fas fa-power-off"></i></button>
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <form class="toggleStatusForm" data-action="block">
                                                    <input type="hidden" name="ScheduleIDBlock" value="${s.getScheduleID()}">
                                                    <button class="action-btn power-btn-active toggleStatusBtn"><i class="fas fa-power-off"></i></button>
                                                </form>
                                            </c:otherwise>
                                        </c:choose>
                                        </div>
                                    </td>
                                </tr>
                                <c:set var="rowCount" value="${status.count}" />
                            </c:forEach>   
                            <!-- Bổ sung các hàng trống nếu danh sách có ít hơn 9 người dùng -->
                            <c:forEach begin="1" end="${9 - rowCount}">
                                <tr>
                                    <td colspan="8" style="height: 40px;"></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>    
                    <div style="margin-left: -220px;" >

                    </div>
                </div>
            </div>
        </div>
        <%@include file="/Component/pagination.jsp" %>
        <%@include file="/Component/footer.jsp" %>

        <div id="myModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <h2 style="color: #333; margin-bottom: 20px;" id="modalTitle">Thêm lịch làm việc</h2>
                <form id="shiftForm" action="createschedule" method="post">
                    <input type="hidden" id="shiftId" value="">

                    <div class="form-row">
                        <label for="shiftName">Tên ca làm việc:</label>
                        <input name="shiftName" type="text" id="shiftName" placeholder="Nhập tên ca làm việc" required>
                    </div>

                    <div class="form-row">
                        <label for="shiftStart">Giờ làm việc:</label>
                        <div class="time-inputs">
                            <input name="shiftStart" type="time" id="shiftStart" required>
                            <span>Đến</span>
                            <input name="shiftEnd" type="time" id="shiftEnd"required>
                        </div>
                    </div>

                    <div class="form-row">
                        <label for="breakStart">Giờ cho phép chấm công:</label>
                        <div class="time-inputs">
                            <input name="breakStart" type="time" id="breakStart"required>
                            <span>Đến</span>
                            <input name="breakEnd" type="time" id="breakEnd"required>
                        </div>
                    </div>                    

                    <div class="form-row">
                        <label for="statusSelect">Trạng thái:</label>
                        <select name="statusSelect" id="statusSelect">
                            <option value="0">Hoạt động</option>
                            <option value="1">Không hoạt động</option>
                        </select>
                    </div>

                    <div class="btn-container">
                        <button type="button" class="btn btn-secondary" onclick="closeModal()">Bỏ qua</button>
                        <button type="submit" class="btn" >Thêm mới</button>
                    </div>
                </form>
            </div>
        </div>
        
        
        


    </body>


    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
                            function openModal() {
                                document.getElementById("myModal").style.display = "block";
                            }

                            function closeModal() {
                                document.getElementById("myModal").style.display = "none";
                            }

                            function saveShift() {
                                const name = document.getElementById("shiftName").value;
                                // Thêm logic để lưu ca làm việc
                                alert(`Ca làm việc "${name}" đã được thêm!`);
                                closeModal();
                                // Cập nhật bảng ở đây
                            }

                            window.onclick = function (event) {
                                if (event.target == document.getElementById("myModal")) {
                                    closeModal();
                                }
                            }
                            
                            function updateModal() {
                                document.getElementById("updateModal").style.display = "block";
                            }

                            function closeUpdateModal() {
                                document.getElementById("updateModal").style.display = "none";
                            }
                                                        
                            
                            
                            
                            //ajax submit block, unlock
                                            $(document).ready(function () {
                                                $(".toggleStatusBtn").click(function () {
                                                    event.preventDefault();
                                                    var form = $(this).closest(".toggleStatusForm");
                                                    var ScheduleIDBlock = form.find("input[name='ScheduleIDBlock']").val();
                                                    var actionBlock = form.data("action"); // "block" hoặc "unlock"

                                                    $.ajax({
                                                        type: "POST",
                                                        url: "workschedule",
                                                        data: {actionBlock: actionBlock, ScheduleIDBlock: ScheduleIDBlock},
                                                        success: function () {
                                                            location.reload(); // Reload để cập nhật trạng thái
                                                        },
                                                        error: function () {
                                                            $("#error-message").text("Không xóa được ca làm việc.").show();
                                                            hideNotification('error-message');
                                                        }
                                                    });
                                                });
                                            });



//                            // Power buttons functionality
//                            const powerBtns = document.querySelectorAll('.power-btn-active, .power-btn-locked');
//                            powerBtns.forEach(btn => {
//                                btn.addEventListener('click', function () {
//                                    const row = this.closest('tr');
//                                    const shiftName = row.querySelector('td:nth-child(2)').textContent;
//                                    const isActive = this.classList.contains('power-btn-active');
//
//                                    if (isActive) {
//                                        console.log('Deactivating shift:', shiftName);
//                                        // Here you would call API to deactivate the shift
//                                        this.classList.remove('power-btn-active');
//                                        this.classList.add('power-btn-locked');
//                                        row.querySelector('.status-active').classList.remove('status-active');
//                                        row.querySelector('td:nth-child(6) span').classList.add('status-locked');
//                                        row.querySelector('td:nth-child(6) span').textContent = 'Đã khóa';
//                                    } else {
//                                        console.log('Activating shift:', shiftName);
//                                        // Here you would call API to activate the shift
//                                        this.classList.remove('power-btn-locked');
//                                        this.classList.add('power-btn-active');
//                                        row.querySelector('.status-locked').classList.remove('status-locked');
//                                        row.querySelector('td:nth-child(6) span').classList.add('status-active');
//                                        row.querySelector('td:nth-child(6) span').textContent = 'Hoạt động';
//                                    }
//                                });
//                            });

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


    </script>

</html>

