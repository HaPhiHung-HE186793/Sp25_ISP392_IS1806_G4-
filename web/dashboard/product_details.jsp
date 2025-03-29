<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/fonts/themify-icons/themify-icons.css">
        <title>Chi Tiết Sản Phẩm</title>
    </head>
    <body>

        <div id="main">
            <%-- Thêm menu --%>
            <jsp:include page="/Component/menu.jsp"></jsp:include>

                <div class="main-content">
                    <div class="product-container">
                        <h2 class="product-title">Chi Tiết Sản Phẩm</h2>

                    <c:if test="${not empty product}">
                        <p class="product-info"><strong>Tên Sản Phẩm:</strong> ${product.productName}</p>
                        <p class="product-info"><strong>Số Lượng:</strong> ${product.quantity}</p>
                        <p class="product-info"><strong>Giá:</strong> ${product.price} VNĐ</p>
                        <%-- Hiển thị mô tả sản phẩm nếu có --%>
                        <c:if test="${not empty product.description}">
                            <p class="product-info"><strong>Mô Tả:</strong> ${product.description}</p>
                        </c:if>
                        <%-- Kiểm tra nếu có danh sách zone --%>
                        <p class="product-info"><strong>Khu Vực:</strong> 
                            <c:choose>
                                <c:when test="${not empty zoneList}">
                                    <c:forEach var="zone" items="${zoneList}" varStatus="status">
                                        <span class="zone-name">${zone.zoneName} :</span>
                                        <span class="zone-description">${zone.navigation}</span>
                                        <br>
                                    </c:forEach> 
                                </c:when>
                                <c:otherwise>
                                    <span class="zone-name">Chưa có khu vực</span>
                                </c:otherwise>
                            </c:choose>
                        </p>

                        <p class="product-info"><strong>Trạng Thái:</strong> 
                            <c:choose>
                                <c:when test="${product.isDelete}">
                                    <span class="status status-inactive">Ngừng Bán</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="status status-active">Đang Bán</span>
                                </c:otherwise>
                            </c:choose>
                        </p>

                        <%-- Hiển thị ảnh sản phẩm nếu có --%>
                        <c:if test="${not empty product.image}">
                            <img src="${product.image}" class="product-image" alt="Ảnh sản phẩm">
                        </c:if>

                        <div class="button-group">
                            <a href="ListProducts">
                                <button class="btn btn-back">Quay lại danh sách</button>
                            </a>
                        </div>
                    </c:if>

                    <c:if test="${empty product}">
                        <p style="color: red;">Sản phẩm không tồn tại hoặc đã bị xóa.</p>
                    </c:if>
                </div>
            </div>
        </div>

        <style>
            body {
                background-color: #0a0a0a;
                color: #fff;
                font-family: Arial, sans-serif;
            }
            .product-container {
                width: 50%;
                margin: auto;
                padding: 20px;
                background: #111;
                border: 4px solid #e60000;
                text-align: center;
                box-shadow: 0px 0px 20px rgba(230, 0, 0, 0.7);
            }
            .product-title {
                font-size: 28px;
                font-weight: bold;
                color: #e60000;
                text-transform: uppercase;
                margin-bottom: 20px;
            }
            .product-info {
                font-size: 18px;
                margin: 10px 0;
                text-transform: uppercase;
            }
            .product-image {
                max-width: 300px;
                height: auto;
                margin: 15px 0;
                border: 4px solid #e60000;
                box-shadow: 0px 0px 15px rgba(230, 0, 0, 0.8);
            }
            .button-group {
                margin-top: 20px;
            }
            .btn {
                padding: 12px 18px;
                border: 3px solid #e60000;
                background-color: transparent;
                color: white;
                font-size: 16px;
                text-transform: uppercase;
                cursor: pointer;
                transition: 0.3s;
                margin: 5px;
            }
            .btn-back {
                border-color: white;
                color: white;
            }
            .btn-back:hover {
                background-color: #e60000;
                border-color: #e60000;
            }
            .btn-edit {
                background-color: #e60000;
            }
            .btn-edit:hover {
                background-color: #ff1a1a;
                border-color: #ff1a1a;
            }
            .status {
                font-weight: bold;
                font-size: 18px;
            }
            .status-active {
                color: #00ff00;
            }
            .status-inactive {
                color: #ff0000;
            }
        </style>

    </body>
</html>
