<%-- 
    Document   : storeProfile
    Created on : 20 thg 3, 2025, 15:44:25
    Author     : nguyenanh
--%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.User" %>
<%@ page import="DAO.DAOUser" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./assets/css/style.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <title>Store Profile</title>
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

            .store-container {
                max-width: 800px;
                margin: 5px auto;
                padding: 30px;
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
                background: linear-gradient(90deg, #089308, #089308);
            }

            .store-header {
                text-align: center;
                margin-bottom: -15px;
                position: relative;
            }

            .store-logo-container {
                position: relative;
                width: 140px;
                height: 140px;
                margin: 0 auto 20px;
            }

            .store-logo {
                display: block;
                width: 100%;
                height: 100%;
                border-radius: 50%;
                object-fit: cover;
                border: 3px solid #089308;
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
                transition: var(--transition);
            }

            .store-logo:hover {
                transform: scale(1.05);
                box-shadow: 0 6px 20px rgba(78, 84, 200, 0.3);
            }

            .store-header h2 {
                font-size: 28px;
                margin: 15px 0;
                background: linear-gradient(90deg, #8ac84e, #37cc33);
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
                display: inline-block;
            }

            .store-info {
                display: grid;
                grid-template-columns: 1fr;
                gap: 20px;
                font-size: 14px;
                padding: 20px;
                background-color: rgba(0, 0, 0, 0.2);
                border-radius: var(--border-radius);
                margin-top: 20px;
            }
            .row {
                display: flex;
                justify-content: space-between;
            }

            .row p {
                flex: 1;
                display: flex;
                align-items: center;
                padding: 10px;
                background-color: rgba(255, 255, 255, 0.05);
                border-radius: 8px;
            }

            .store-info p {
                display: flex;
                align-items: center;
                margin: 0;
                padding: 10px 15px;
                border-radius: 8px;
                background-color: rgba(255, 255, 255, 0.05);
                transition: var(--transition);
            }

            .store-info p:hover {
                background-color: rgba(255, 255, 255, 0.1);
                transform: translateX(5px);
            }

            .store-info label {
                font-weight: bold;
                min-width: 180px;
                color: #089308;
                display: flex;
                align-items: center;
            }

            .store-info label {
                font-weight: bold;
                color: #089308;
                display: flex;
                align-items: center;
            }

            .store-info label i {
                margin-right: 10px;
                font-size: 18px;
            }

            .button-group {
                margin-top: 20px;
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


            @media (max-width: 768px) {
                .store-container {
                    margin: 20px;
                    padding: 20px;
                }

                .store-info {
                    padding: 15px;
                }

                .store-info p {
                    flex-direction: column;
                    align-items: flex-start;
                }

                .store-info label {
                    margin-bottom: 5px;
                }
            }

            /* Add a subtle animation */
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

    </head>
    <body>
        <div id="main">
            <jsp:include page="/Component/menu.jsp"></jsp:include>
                <div class="main-content">
                    <div class="notification">
                        Thông báo: Mọi người có thể liên hệ admin tại fanpage Group 4
                    </div>
                    <div class="store-container">
                        <div class="store-header">
                            <div class="store-logo-container">
                                <img src="${Store.getLogostore()}" alt="Store Logo" class="store-logo">
                        </div>
                        <h2>${Store.getStoreName()}</h2>
                    </div>
                    <div class="store-info">
                        <div class="row">
                            <p><label><i class="fas fa-user"></i> Owner:</label> <span>
                                    <c:forEach var="owner" items="${Store.getOwnerName()}">
                                        ${owner} <br>
                                    </c:forEach>
                                </span></p>
                            <p><label><i class="fas fa-map-marker-alt"></i> Address:</label> <span>${Store.getAddress()}</span></p>
                        </div>
                        <div class="row">
                            <p><label><i class="fas fa-phone"></i> Phone:</label> <span>${Store.getPhone()}</span></p>
                            <p><label><i class="fas fa-envelope"></i> Email:</label> <span>${Store.getEmail()}</span></p>
                        </div>
                        <div class="row">
                            <p><label><i class="fas fa-user-cog"></i> Created By:</label> <span>${Store.getCreateName()}</span></p>
                            <p><label><i class="fas fa-calendar-alt"></i> Created At:</label> <span>${Store.getCreateAt()}</span></p>
                        </div>
                        <p><label><i class="fas fa-users"></i> Number of Employees:</label> <span>${Store.CountStaff()}</span></p>
                    </div>
                    <div class="button-group">
                        <c:if test="${user_current.getRoleID() == 3}">
                            <button onclick="window.location.href = 'ListProducts'"  
                                    style="padding: 5px 15px; font-size: 14px;gap:30px ; min-width: 80px; background-color: #4e54c8; color: white; border: none; border-radius: 5px; cursor: pointer; margin-right: 10px;">
                                Quay về danh sách sản phẩm
                            </button>
                        </c:if>                        
                    </div>
                    <div class="button-group">
                    <c:if test="${user_current.getRoleID() == 2}">
                            <button onclick="window.location.href = 'ListProducts'"  
                                    style="padding: 5px 15px; font-size: 14px;gap:30px ; min-width: 80px; background-color: #4e54c8; color: white; border: none; border-radius: 5px; cursor: pointer; margin-right: 10px;">
                                Quay về danh sách sản phẩm
                            </button>
                            
                            <button onclick="window.location.href = 'storeprofile?id=${Store.getStoreID()}'"  
                                                style="padding: 5px 15px; font-size: 14px; min-width: 80px; background-color: #5bc0de; color: white; border: none; border-radius: 5px; cursor: pointer; margin-right: 10px;">
                                            Cập nhật
                            </button>
                        </c:if>
                    </div>  
                </div>  

            </div>
        </div>
    </body>

</html>




