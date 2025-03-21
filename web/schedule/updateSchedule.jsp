<%-- 
    Document   : updateSchedule
    Created on : 20 thg 3, 2025, 23:15:28
    Author     : nguyenanh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./assets/css/style.css">
        <link rel="stylesheet" href="./assets/fonts/themify-icons/themify-icons.css">
        <title>Update Schedule</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    </head>

    <style>
        /* Enhanced Modal and UI Styles */

        .table-container {
            background-color: #222 !important;
            padding: 90px 20px !important;
            border-radius: 4px !important;
            overflow-y: scroll !important;
            scrollbar-width: thin !important;
            scrollbar-color: #555 #333 !important;
            max-height: 600px !important;
        }

        .form-row label {
            display: inline-block;
            width: 180px;
            font-weight: 500;
            color: #555;
            margin-right: 10px;
            font-family: Arial, sans-serif; /* Thay đổi font chữ tại đây */
            font-size: 17px;
        }

        .modal {

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
            margin: 20px auto;
            padding: 36px;
            border-radius: 8px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
            width: 80%;
            max-width: 650px;
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
                        <div style="display: flex;">
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
                    <div id="updateModal" >
                        <div class="modal-content">
                            <h2 id="modalTitle" style="color: #333; font-size: 36px; font-weight: bold; margin-bottom: 30px;">Cập nhật lịch làm việc</h2>
                            <form id="shiftForm" action="updateschedule" method="post">
                                <input type="hidden" name="shiftId" id="shiftId" value="${schedule.getScheduleID()}">

                                <div class="form-row">
                                    <label for="shiftName">Tên lịch làm việc:</label>
                                    <input name="shiftName" type="text" id="shiftName" placeholder="Nhập tên lịch làm việc" value="${schedule.getScheduleName()}" required>
                                </div>

                                <div class="form-row">
                                    <label for="shiftStart">Giờ làm việc:</label>
                                    <div class="time-inputs">
                                        <input name="shiftStart" type="time" id="shiftStart" value="${schedule.getStartDate()}" required>
                                        <span>Đến</span>
                                        <input name="shiftEnd" type="time" id="shiftEnd" value="${schedule.getEndDate()}" required>
                                    </div>
                                </div>

                                <div class="form-row">
                                    <label for="breakStart">Giờ cho phép chấm công:</label>
                                    <div class="time-inputs">
                                        <input name="breakStart" type="time" id="breakStart" value="${schedule.getBreakStart()}" required>
                                        <span>Đến</span>
                                        <input name="breakEnd" type="time" id="breakEnd" value="${schedule.getBreakEnd()}" required>
                                    </div>
                                </div>                    

                                <div class="form-row">
                                    <label for="statusSelect">Trạng thái:</label>
                                    <select name="statusSelect" id="statusSelect">
                                        <option value="0" ${!schedule.isIsDelete() ? 'selected' : ''}>Hoạt động</option>
                                        <option value="1" ${schedule.isIsDelete() ? 'selected' : ''}>Không hoạt động</option>

                                    </select>
                                </div>

                                <div class="btn-container">
                                    <button type="button" class="btn btn-secondary" onclick="window.location.href = 'workschedule'">Danh sách lịch làm việc</button>
                                    <button type="submit" class="btn" >Cập nhật</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
    </body>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>

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
                                            let errorDiv = document.getElementById("errorNotification");
                                            let messageDiv = document.getElementById("messageNotification");
                                            if (!errorDiv.innerText.trim()) {
                                                errorDiv.style.display = "none";
                                            }
                                            if (!messageDiv.innerText.trim()) {
                                                messageDiv.style.display = "none";
                                            }
                                        });

    </script>
</html>
