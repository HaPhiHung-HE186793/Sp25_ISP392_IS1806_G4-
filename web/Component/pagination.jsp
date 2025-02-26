<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="currentPageUrl" value="${requestScope.currentPageUrl}" />

<form id="paginationForm" method="post" action="${currentPageUrl}">
    <input type="hidden" name="cp" id="cp" value="${requestScope.page.getCurrentPage()}">
    <input type="hidden" name="role" id="role" value="${requestScope.selectedRole}">
    <input type="hidden" name="keyword" id="keyword" value="${requestScope.keyword}">
    <input type="hidden" name="number" id="keyword" value="${requestScope.searchNumber}">
    <input type="hidden" name="startDate" id="keyword" value="${requestScope.searchStartDate}">
    <input type="hidden" name="endDate" id="keyword" value="${requestScope.searchEndDate}">
    <input type="hidden" name="name" id="keyword" value="${requestScope.searchName}">

</form>

<c:if test="${sessionScope.page.getTotalPage() > 1}">
    <div class="text-center col-md-6 mt-5" style="margin-left: 20%">
        <nav class="text-center" aria-label="Page navigation example">
            <ul class="pagination text-center">
                <c:if test="${sessionScope.page.getCurrentPage() > 1}">
                    <li class="page-item">
                        <button class="page-link" type="button" onclick="submitPage(1)">Đầu</button>
                    </li>
                    <li class="page-item">
                        <button class="page-link" type="button" onclick="submitPage(${sessionScope.page.getCurrentPage() - 1})">&laquo;</button>
                    </li>
                </c:if>

                <c:if test="${sessionScope.page.getCurrentPage() > 1}">
                    <li class="page-item">
                        <button class="page-link" type="button" onclick="submitPage(${sessionScope.page.getCurrentPage() - 1})">
                            ${sessionScope.page.getCurrentPage() - 1}
                        </button>
                    </li>
                </c:if>

                <li class="page-item active">
                    <button class="page-link" type="button" onclick="submitPage(${sessionScope.page.getCurrentPage()})">
                        ${sessionScope.page.getCurrentPage()}
                    </button>
                </li>

                <c:if test="${sessionScope.page.getCurrentPage() < sessionScope.page.getTotalPage()}">
                    <li class="page-item">
                        <button class="page-link" type="button" onclick="submitPage(${sessionScope.page.getCurrentPage() + 1})">
                            ${sessionScope.page.getCurrentPage() + 1}
                        </button>
                    </li>
                </c:if>

                <c:if test="${sessionScope.page.getCurrentPage() < sessionScope.page.getTotalPage()}">
                    <li class="page-item">
                        <button class="page-link" type="button" onclick="submitPage(${sessionScope.page.getCurrentPage() + 1})">&raquo;</button>
                    </li>
                    <li class="page-item">
                        <button class="page-link" type="button" onclick="submitPage(${sessionScope.page.getTotalPage()})">Cuối</button>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</c:if>

<script>
    function submitPage(page) {
        document.getElementById("cp").value = page;
        document.getElementById("paginationForm").submit();
    }
    function submitPage(page) {
        document.getElementById("cp").value = page;
        document.getElementById("paginationForm").submit();
    }
</script>

