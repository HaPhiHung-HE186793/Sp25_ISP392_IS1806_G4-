<%-- 
    Document   : createUser
    Created on : 12 thg 2, 2025, 13:23:18
    Author     : nguyenanh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create User</title>
    </head>
    <body>
        <!--form add customer-->
        <form class="mt-4" action="createuser" method="POST" enctype="multipart/form-data">

            <div class="row">
                <div class="col-md-6">
                    <div class="mb-3">
                        <label class="form-label">User Name</label>
                        <input name="username" id="username" type="text" 
                               class="form-control" placeholder="UserName:" value="${username}">
                    </div>
                </div><!--end col-->   

                <div class="col-md-6">
                    <div class="mb-3">
                        <label class="form-label">Your Email</label>
                        <input name="email" id="email" type="email" class="form-control" placeholder="Your email:" value="${email}">
                    </div> 
                </div><!--end col-->
                <div class="mb-3">
                    <label class="form-label">Password</label>
                    <input name="password" id="password" type="password" 
                           class="form-control" placeholder="Enter your password">
                </div>


                <div class="col-md-6">
                    <div class="mb-3">
                        <label class="form-label">Chức Năng</label>
                        <div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="roleID" value="1" id="admin" required>
                                <label class="form-check-label" for="admin">
                                    Admin
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="roleID" value="2" id="store_owner">
                                <label class="form-check-label" for="store_owner">
                                    Chủ Cửa Hàng
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="roleID" value="3" id="employee">
                                <label class="form-check-label" for="employee">
                                    Nhân Viên
                                </label>
                            </div>
                        </div>
                    </div>
                </div>       

                <!-- Hiển thị thông báo lỗi chung -->
                <c:if test="${not empty errors}">
                    <div class="alert alert-danger mt-3">

                        <c:forEach var="error" items="${errors}">
                            <p>${error}</p>
                        </c:forEach>

                    </div>
                </c:if>

                <!-- End Error Message -->

                <!-- Hiển thị thông báo thành công nếu có -->
                <c:if test="${not empty success}">
                    <div class="alert alert-success mt-3">${success}</div>
                </c:if>  
            </div><!--end row-->

            <button type="submit" class="btn btn-primary">Tạo tài khoản</button>
        </form>
    </body>
</html>
