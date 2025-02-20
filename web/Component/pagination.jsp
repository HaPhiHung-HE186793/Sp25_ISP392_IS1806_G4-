<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="currentPageUrl" value="${requestScope.currentPageUrl}" />

<c:if test="${sessionScope.page.getTotalPage() > 1}">
    <div class="text-center col-md-6 mt-5" style="margin-left: 20%">
        <nav class="text-center" aria-label="Page navigation example">
            <ul class="pagination text-center">
                <c:if test="${sessionScope.page.getCurrentPage() > 1}">
                    <li class="page-item"><a class="page-link" href="${currentPageUrl}?cp=1">Đầu</a></li>
                    <li class="page-item">
                        <a class="page-link" href="${currentPageUrl}?cp=${sessionScope.page.getCurrentPage() - 1}">&laquo;</a>
                    </li>
                </c:if>

                <c:if test="${sessionScope.page.getCurrentPage() > 1}">
                    <li class="page-item">
                        <a class="page-link" href="${currentPageUrl}?cp=${sessionScope.page.getCurrentPage() - 1}">
                            ${sessionScope.page.getCurrentPage() - 1}
                        </a>
                    </li>
                </c:if>

                <li class="page-item active">
                    <a class="page-link" href="${currentPageUrl}?cp=${sessionScope.page.getCurrentPage()}">
                        ${sessionScope.page.getCurrentPage()}
                    </a>
                </li>

                <c:if test="${sessionScope.page.getCurrentPage() < sessionScope.page.getTotalPage()}">
                    <li class="page-item">
                        <a class="page-link" href="${currentPageUrl}?cp=${sessionScope.page.getCurrentPage() + 1}">
                            ${sessionScope.page.getCurrentPage() + 1}
                        </a>
                    </li>
                </c:if>

                <c:if test="${sessionScope.page.getCurrentPage() < sessionScope.page.getTotalPage()}">
                    <li class="page-item">
                        <a class="page-link" href="${currentPageUrl}?cp=${sessionScope.page.getCurrentPage() + 1}">&raquo;</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="${currentPageUrl}?cp=${sessionScope.page.getTotalPage()}">Cuối</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</c:if>
