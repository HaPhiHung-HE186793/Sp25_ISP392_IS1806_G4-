/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.debt;

import DAO.DAOCustomers;
import DAO.DAODebtRecords;
import model.Customers;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DebtRecords;

/**
 *
 * @author TIEN DAT PC
 */
public class AddNewCustomerDebt extends HttpServlet {

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

        DAOCustomers daoCus = new DAOCustomers();
        response.setContentType("text/html;charset=UTF-8");
        int customerID = Integer.parseInt(request.getParameter("customerid"));



        double debt = Integer.parseInt(request.getParameter("debt"));
        int paymentStatus = Integer.parseInt(request.getParameter("typeDebt"));
        String updateAt = request.getParameter("dateTime");

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createAt = now.format(formatter);  // Thời gian hiện tại

        if (updateAt == null || updateAt.trim().isEmpty()) {
            updateAt = createAt;
        }

        // Chuyển đổi ngày lập phiếu sang java.sql.Date
       
        HttpSession session = request.getSession();
        boolean isDelete = false;
        Integer createBy = (Integer) session.getAttribute("userID");

        DAODebtRecords dao = new DAODebtRecords();
        try {
            if (createBy != null) {
                DebtRecords debtRecord = new DebtRecords();
                debtRecord.setCustomerID(customerID);
                debtRecord.setAmount(debt);
                debtRecord.setPaymentStatus(paymentStatus);
                debtRecord.setCreateAt(createAt);
                debtRecord.setUpdateAt(updateAt);
                debtRecord.setIsDelete(isDelete); // Gán ngày lập phiếu
                debtRecord.setCreateBy(createBy);
                dao.addDebtRecord(debtRecord);
                response.sendRedirect("ListDebtCustomer?customerid=" + customerID);
            } 
        } catch (Exception e) {
            e.printStackTrace();
           
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
