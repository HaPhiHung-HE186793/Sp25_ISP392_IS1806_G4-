/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.debt;

import DAO.DAOCustomers;
import DAO.DAODebtRecords;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Customers;
import model.DebtRecords;
import model.pagination.Pagination;

/**
 *
 * @author TIEN DAT PC
 */
public class ListHistoryDebt extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        HttpSession session = request.getSession();
        Integer storeID = (Integer) session.getAttribute("storeID");
        int role = (Integer) session.getAttribute("roleID");
        if (role == 3) {
            response.sendRedirect("ListDebtCustomer");
            return; // Dừng xử lý tiếp
        }
        DAODebtRecords dao = new DAODebtRecords();

        List<DebtRecords> listHistoryDebt = dao.listAllHistory(storeID + "");
        request.setAttribute("listHistoryDebt", listHistoryDebt);

        // Cập nhật pagination dựa trên số lượng kết quả tìm kiếm
        int totalUsers = listHistoryDebt.size();
        int pageSize = 10;
        int currentPage = 1;

        if (request.getParameter("cp") != null) {
            currentPage = Integer.parseInt(request.getParameter("cp"));
        }

        Pagination page = new Pagination(totalUsers, pageSize, currentPage);
        session.setAttribute("page", page);
        int startIndex = page.getStartItem();
        int endIndex = Math.min(startIndex + pageSize, totalUsers);
        List<DebtRecords> paginatedUsers = listHistoryDebt.subList(startIndex, endIndex);
        request.setAttribute("currentPageUrl", "ListHistoryDebt");
        request.getRequestDispatcher("debt/historyDebt.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        Integer storeID = (Integer) session.getAttribute("storeID");

        DAODebtRecords dao = new DAODebtRecords();
        int role = (Integer) session.getAttribute("roleID");
        if (role == 3) {
            response.sendRedirect("ListDebtCustomer");
            return; // Dừng xử lý tiếp
        }

        // Lấy các tham số từ request
//        String name = request.getParameter("name");
        String sortBy = request.getParameter("sortBy");
        String startDate = request.getParameter("searchStartDate");
        String endDate = request.getParameter("searchEndDate");
        String sql = "";
//// Điều kiện name   
//        if (name != null && !name.isEmpty()) {
//            sql += " AND c.name LIKE '%" + name + "%' ";
//        }

//        if (sortBy != null) {
//            switch (sortBy) {
//                case "2":
        if (startDate != null && !startDate.isEmpty()) {
            sql += " and CONVERT(date, d.createAt) >= '" + startDate + "' ";
        }

// Điều kiện endDate
        if (endDate != null && !endDate.isEmpty()) {
            sql += " and CONVERT(date, d.createAt) <= '" + endDate + "' ";
        }
//                case "1":
//                    if (startDate != null && !startDate.isEmpty()) {
//                        sql += " and CONVERT(date, d.createAt) >= '" + startDate + "' ";
//                    }
//
//// Điều kiện endDate
//                    if (endDate != null && !endDate.isEmpty()) {
//                        sql += " and CONVERT(date, d.createAt) <= '" + endDate + "' ";
//                    }
//            }

        List<DebtRecords> listHistoryDebt = dao.listHistorySearchName(storeID + "", sql);

        request.setAttribute("listHistoryDebt", listHistoryDebt);
        request.setAttribute("searchStartDate", startDate);
        request.setAttribute("searchEndDate", endDate);
//            request.setAttribute("sortBy", sortBy);

        // Cập nhật pagination dựa trên số lượng kết quả tìm kiếm
        int totalUsers = listHistoryDebt.size();
        int pageSize = 10;
        int currentPage = 1;

        if (request.getParameter("cp") != null) {
            currentPage = Integer.parseInt(request.getParameter("cp"));
        }

        Pagination page = new Pagination(totalUsers, pageSize, currentPage);
        session.setAttribute("page", page);
        int startIndex = page.getStartItem();
        int endIndex = Math.min(startIndex + pageSize, totalUsers);
        List<DebtRecords> paginatedUsers = listHistoryDebt.subList(startIndex, endIndex);
        request.setAttribute("currentPageUrl", "ListHistoryDebt");

        request.getRequestDispatcher("debt/historyDebt.jsp").forward(request, response);
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
