<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.page.getTotalPage() > 1}">
    <div class="text-center col-md-6 mt-5" style="margin-left: 20%">
        <nav class="text-center" aria-label="Page navigation example">
            <ul class="pagination text-center">
                <!-- Nếu không phải trang đầu tiên, hiển thị "First" và trang trước -->
                <c:if test="${sessionScope.page.getCurrentPage() > 1}">
                    <li class="page-item"><a class="page-link" href="listusers?cp=1">First</a></li>
                    <li class="page-item">
                        <a class="page-link" href="listusers?cp=${sessionScope.page.getCurrentPage() - 1}">&laquo;</a>
                    </li>
                </c:if>

                <!-- Hiển thị trang hiện tại và các trang xung quanh nếu có -->
                <c:if test="${sessionScope.page.getCurrentPage() > 1}">
                    <li class="page-item">
                        <a class="page-link" href="listusers?cp=${sessionScope.page.getCurrentPage() - 1}">
                            ${sessionScope.page.getCurrentPage() - 1}
                        </a>
                    </li>
                </c:if>

                <li class="page-item active">
                    <a class="page-link" href="listusers?cp=${sessionScope.page.getCurrentPage()}">
                        ${sessionScope.page.getCurrentPage()}
                    </a>
                </li>

                <c:if test="${sessionScope.page.getCurrentPage() < sessionScope.page.getTotalPage()}">
                    <li class="page-item">
                        <a class="page-link" href="listusers?cp=${sessionScope.page.getCurrentPage() + 1}">
                            ${sessionScope.page.getCurrentPage() + 1}
                        </a>
                    </li>
                </c:if>

                <!-- Nếu không phải trang cuối, hiển thị trang tiếp theo và "Last" -->
                <c:if test="${sessionScope.page.getCurrentPage() < sessionScope.page.getTotalPage()}">
                    <li class="page-item">
                        <a class="page-link" href="listusers?cp=${sessionScope.page.getCurrentPage() + 1}">&raquo;</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="listusers?cp=${sessionScope.page.getTotalPage()}">Last</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</c:if>








