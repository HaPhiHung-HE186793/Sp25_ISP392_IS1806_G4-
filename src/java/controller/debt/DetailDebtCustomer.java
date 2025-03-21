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
import model.Customers;
import model.DebtRecords;

/**
 *
 * @author TIEN DAT PC
 */
public class DetailDebtCustomer extends HttpServlet {

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

        DAODebtRecords dao = new DAODebtRecords();
        DAOCustomers daoC = new DAOCustomers();
        String debtId = request.getParameter("debtid");
        DebtRecords debtRecords = dao.getDebt(debtId, storeID);

        if (debtRecords == null || debtId == null || debtId.trim().isEmpty()) {
            response.sendRedirect("ListDebtCustomer");
            return; // Dừng xử lý tiếp
        }
        request.setAttribute("debtRecords", debtRecords);
        // Lấy message từ session và đặt vào request
        String message = (String) session.getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            session.removeAttribute("message"); // Xóa sau khi dùng để tránh hiển thị lại
        }
        request.getRequestDispatcher("debt/detailDebt.jsp").forward(request, response);

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
