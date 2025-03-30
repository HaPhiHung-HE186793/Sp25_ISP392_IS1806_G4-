package controller.zone;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAO.DAOZones;
import java.util.List;
import model.Zones;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;
import model.pagination.Pagination;
import java.io.PrintWriter;
import model.Products;

public class ListZones extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAOZones daoZone = new DAOZones();
        HttpSession session = request.getSession();
        Integer storeID = (Integer) session.getAttribute("storeID");
        Integer roleID = (Integer) session.getAttribute("roleID");
        if(roleID == 1 ){
            request.getRequestDispatcher("listusers").forward(request, response);
        }
        // Lấy danh sách zone
        List<Zones> zones = daoZone.listAll1(storeID);
        int totalUsers = zones.size();
        int pageSize = 20;
        int currentPage = 1;

        if (request.getParameter("cp") != null) {
            currentPage = Integer.parseInt(request.getParameter("cp"));
        }
        request.setAttribute("currentPageUrl", "ListZones"); // Hoặc "products"

        Pagination page = new Pagination(totalUsers, pageSize, currentPage);
        request.setAttribute("page", page);

        // Lấy danh sách user theo trang hiện tại
        int startIndex = page.getStartItem();
        int endIndex = Math.min(startIndex + pageSize, totalUsers);
        List<Zones> paginatedUsers = zones.subList(startIndex, endIndex);

        request.setAttribute("zones", zones);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/zone/listzone.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet hiển thị danh sách khu vực";
    }
}
