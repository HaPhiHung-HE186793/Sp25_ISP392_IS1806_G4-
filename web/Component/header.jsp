<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rice Storage</title>
    </head>
    <body>
        <div class="header">
            <div class="name-project">
                <h2>Quản lý gạo <i class="menu-hiden ti-menu  js-hidden-menu" style="margin-left: 10px;padding: 5px;"></i></h2>
            </div>
            <div class="balance">
                <div class="dropdown">
                    <button class="dropbtn">
                        ${sessionScope.username} <!-- Hiển thị tên người dùng -->
                    </button>
                    <div class="dropdown-content">
                        <a href="<%=request.getContextPath()%>/updateprofile">Hồ sơ</a>
                        <form action="<%=request.getContextPath()%>/loginURL?logoutUser" method="POST">
                            <input type="submit" name="submit" value="Đăng xuất" class="logout-button">
                            <input type="hidden" name="service" value="logoutUser">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
