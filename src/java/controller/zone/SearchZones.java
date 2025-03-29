package controller.zone;

import DAO.DAOZones;
import model.Zones;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchZones", urlPatterns = {"/SearchZones"})
public class SearchZones extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy thông tin tìm kiếm từ request
        String zoneKeyword = request.getParameter("zoneSearch");
        String sortOrder = request.getParameter("sortOrder");
        HttpSession session = request.getSession();
        Integer storeID = (Integer) session.getAttribute("storeID");
        DAOZones zoneDAO = new DAOZones();
        List<Zones> zones = zoneDAO.searchAndFilterZones(zoneKeyword, sortOrder, storeID);

        request.setAttribute("zones", zones);
        request.getRequestDispatcher("zone/listzone.jsp").forward(request, response);
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
