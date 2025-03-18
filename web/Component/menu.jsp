<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="header">
    <div class="name-project">
        <h2>Rice storage </h2>


    </div>



    <div class="balance">
        <form action="<%=request.getContextPath()%>/loginURL?logoutUser" method="POST">                      
            <input style="padding: 5px" type="submit" name="submit" value="Đăng xuất">          
            <input type="hidden" name="service" value="logoutUser">
        </form>
    </div>

</div>
<div class="sidebar">
    <div class="logo">Bảng Điều Khiển</div>




    <a href="<%=request.getContextPath()%>/ListProducts">Danh sách sản phẩm</a>
    <c:if test="${sessionScope.roleID==1}">
        <a href="<%=request.getContextPath()%>/listusers">Danh sách người dùng</a>
    </c:if>
    <c:if test="${sessionScope.roleID==2}">
        <a href="<%=request.getContextPath()%>/listusers">Danh sách người dùng</a>
    </c:if>

    <c:if test="${sessionScope.roleID == 2 or sessionScope.roleID == 3}">
        <a href="<%=request.getContextPath()%>/CreateOrderServlet">Tạo hóa đơn</a>
        <a href="<%=request.getContextPath()%>/URLOrder?service=listshow">Quản lý thanh toán</a>
        <a href="<%=request.getContextPath()%>/ListCustomer">Quản lý khách hàng</a>
        <a href="<%=request.getContextPath()%>/ListZone">Quản lý kho</a>
    </c:if>

    <!--<a href="#">Quản lý kho</a>-->
    <a href="updateprofile">Hồ sơ người dùng</a>
    <!-- Thêm các mục dài để hiển thị thanh trượt -->
    <!--    <a href="#">Dịch vụ</a>  
        <a href="#">Báo cáo tài chính</a>
        <a href="#">Báo cáo hàng hóa</a>
        <a href="#">Cài đặt hệ thống</a>
        <a href="#">Công cụ hỗ trợ</a>
        <a href="#">Liên hệ</a>
        <a href="#">Phản hồi</a>
        <a href="#">Lập báo cáo</a>
        <a href="#">Cài đặt bảo mật</a>-->


</div>
    
