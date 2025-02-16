<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="header">
    <div class="name-project">
        <h2>Rice storage</h2>
    </div>


<<<<<<< HEAD
     <div class="balance">
        <a href="loginURL?service=logoutUser">LogOut</a>
=======
      <div class="balance">
        <a href="${loginURL}?service=logoutUser">LogOut</a>
>>>>>>> 267868a (them duong dan cua controllershoworder)
    </div>

</div>
<div class="sidebar">
    <div class="logo">Bảng Điều Khiển</div>
    <a href="#">Trang chủ</a>
    <a href="#">Hồ sơ người dùng</a>
    <a href="CreateOrderServlet">Tạo hóa đơn</a>
    <a href="#">Sản phẩm</a>
    <a href="#">Quản lý kho</a>
    <a href="URLOrder?service=listshow">Quản lý thanh toán</a>
    <a href="#">Trung gian</a>
    <a href="#">Dịch vụ</a>
    <!-- Thêm các mục dài để hiển thị thanh trượt -->
    <a href="#">Báo cáo tài chính</a>
    <a href="#">Báo cáo hàng hóa</a>
    <a href="#">Cài đặt hệ thống</a>
    <a href="#">Công cụ hỗ trợ</a>
    <a href="#">Liên hệ</a>
    <a href="#">Phản hồi</a>
    <!-- Các mục mới -->
    <c:if test="${sessionScope.roleID==1}">
    <a href="#">Quản lý người dùng</a>
    <a href="#">Quản lý đơn hàng</a>
    <a href="#">Thống kê bán hàng</a>
    <a href="#">Quản lý sản phẩm</a>
    <a href="#">Danh sách đối tác</a>
    <a href="#">Lịch sử giao dịch</a>
    <a href="#">Quản lý danh mục</a>
    <a href="#">Quản lý tài khoản</a>
    </c:if>
    <a href="#">Lập báo cáo</a>
    <a href="#">Cài đặt bảo mật</a>
</div>