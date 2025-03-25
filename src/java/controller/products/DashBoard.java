/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.products;

import DAO.DAOProduct;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import model.User;

/**
 *
 * @author nguyenanh
 */
public class DashBoard extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DashBoard</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DashBoard at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO.DAOProduct productDAO = new DAOProduct();
        HttpSession session = request.getSession();
        Integer roleID = (Integer) session.getAttribute("roleID");
        Integer storeId = (Integer) session.getAttribute("storeID");
        if (roleID == 1) {
            response.sendRedirect("listusers");
            return;
        }

        String viewType = request.getParameter("viewType");
        String viewRevenuePara = request.getParameter("viewRevenue");
        int viewRevenue = 0;
        try {
            if (viewRevenuePara != null && !viewRevenuePara.isEmpty()) {
                viewRevenue = Integer.parseInt(viewRevenuePara);
            }
        } catch (NumberFormatException e) {
            viewRevenue = 0; // Giá trị mặc định nếu lỗi
        }
        
        Map<String, Double> revenueByViewType;

        
        if (viewType != null) {
            if (viewRevenue == 1) {
                revenueByViewType = productDAO.getRevenueCurrentMonthByViewType(viewType, storeId); // Tháng này
            } else if (viewRevenue == 2) {
                revenueByViewType = productDAO.getRevenueLastMonthByViewType(viewType, storeId); // Tháng trước
            } else {
                revenueByViewType = productDAO.getRevenueLast7DaysByViewType(viewType, storeId); // 7 ngày qua
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            StringBuilder json = new StringBuilder("{\"labels\":[");
            boolean first = true;
            for (Map.Entry<String, Double> entry : revenueByViewType.entrySet()) {
                if (!first) {
                    json.append(",");
                }
                json.append("\"").append(entry.getKey()).append("\"");
                first = false;
            }
            json.append("],\"values\":[");
            first = true;
            for (Map.Entry<String, Double> entry : revenueByViewType.entrySet()) {
                if (!first) {
                    json.append(",");
                }
                json.append(entry.getValue());
                first = false;
            }
            json.append("]}");
            out.print(json.toString());
            out.flush();
            return;
        }
        double totalRevenue7Day = productDAO.getTotalRevenueLast7Days(storeId);;    // 7 ngày qua   
        double      totalRevenueThisMonth = productDAO.getTotalRevenueThisMonth(storeId); // Tháng này
         double       totalRevenueLastMonth = productDAO.getTotalRevenueLastMonth(storeId); // Tháng trước
        


        String[] productNames = productDAO.getTop3BestSellingRice(storeId);
        String[] totalSold = productDAO.getTop3TotalSold(storeId);
        String[] totalRevenue = productDAO.getTop3TotalRevenue(storeId);
        Double[] totalRevenueDouble = Arrays.stream(totalRevenue)
                .map(Double::parseDouble)
                .toArray(Double[]::new);

        int totalOrderToday = productDAO.getTotalOrdersToday(storeId);
        double totalRevenueToday = productDAO.getTotalRevenueToday(storeId);
        double revenueChange = productDAO.getRevenueChangePercentage(storeId);
        

        request.setAttribute("viewRevenue", viewRevenue);
        request.setAttribute("productNames", productNames);
        request.setAttribute("totalSold", totalSold);
        request.setAttribute("totalRevenue", totalRevenueDouble);
        request.setAttribute("totalOrderToday", totalOrderToday);
        request.setAttribute("totalRevenueThisMonth", totalRevenueThisMonth);
        request.setAttribute("totalRevenue7Day", totalRevenue7Day);
        request.setAttribute("totalRevenueLastMonth", totalRevenueLastMonth);
        request.setAttribute("totalRevenueToday", totalRevenueToday);
        request.setAttribute("revenueChange", revenueChange);

        request.getRequestDispatcher("dashboard/dashboard.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
