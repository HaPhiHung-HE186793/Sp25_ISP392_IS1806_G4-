package controller.products;

import DAO.DAOProduct;
import model.Products;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import model.pagination.Pagination;

@WebServlet(name = "ListRice", urlPatterns = {"/ListRice"})
public class SearchProducts extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String keyword = request.getParameter("search"); // Get search term
        request.setAttribute("search", keyword);

        DAOProduct dao = new DAOProduct();
        List<Products> productsList;

        if (keyword != null && !keyword.isEmpty()) {
            productsList = dao.searchProducts(keyword); // New search method
        } else {
            productsList = dao.listAll();
        }

        // Cập nhật pagination dựa trên số lượng kết quả tìm kiếm
        int totalUsers = productsList.size();
        int pageSize = 4;
        int currentPage = 1;

        if (request.getParameter("cp") != null) {
            currentPage = Integer.parseInt(request.getParameter("cp"));
        }
        request.setAttribute("currentPageUrl", "ListRice"); // Hoặc "products"

        Pagination page = new Pagination(totalUsers, pageSize, currentPage);
        session.setAttribute("page", page);

        // Lấy danh sách user theo trang hiện tại
        int startIndex = page.getStartItem();
        int endIndex = Math.min(startIndex + pageSize, totalUsers);
        List<Products> paginatedUsers = productsList.subList(startIndex, endIndex);

        request.setAttribute("products", productsList);
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            request.getRequestDispatcher("dashboard/home.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("dashboard/home.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
