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
        Integer storeID = (Integer) session.getAttribute("storeID");

        if (storeID == null) {
            response.sendRedirect("login.jsp"); // Chuyển hướng nếu chưa đăng nhập
            return;
        }

        String nameSearch = request.getParameter("nameSearch") != null ? request.getParameter("nameSearch") : "";
        String descSearch = request.getParameter("descSearch") != null ? request.getParameter("descSearch") : "";
        String stockStatus = request.getParameter("stockStatus") != null ? request.getParameter("stockStatus") : "";
        String priceSort = request.getParameter("priceSort") != null ? request.getParameter("priceSort") : "";

        request.setAttribute("nameSearch", nameSearch);
        request.setAttribute("descSearch", descSearch);
        request.setAttribute("stockStatus", stockStatus);
        request.setAttribute("priceSort", priceSort);

        System.out.println("Received sortPrice from request: " + priceSort);

        DAOProduct dao = new DAOProduct();
        List<Products> productsList = dao.searchAndFilterProducts(nameSearch, descSearch, stockStatus, priceSort, storeID);

        int totalProducts = productsList.size();
        int pageSize = 10;
        int currentPage = request.getParameter("cp") != null ? Integer.parseInt(request.getParameter("cp")) : 1;

        Pagination page = new Pagination(totalProducts, pageSize, currentPage);
        request.setAttribute("page", page);

        int startIndex = page.getStartItem();
        int endIndex = Math.min(startIndex + pageSize, totalProducts);
        List<Products> paginatedProducts = productsList.subList(startIndex, endIndex);

        request.setAttribute("products", paginatedProducts);

        request.getRequestDispatcher("dashboard/home.jsp").forward(request, response);
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
