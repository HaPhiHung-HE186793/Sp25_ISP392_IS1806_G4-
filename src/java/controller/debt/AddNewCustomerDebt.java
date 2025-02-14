/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.debt;

import DAO.DAOCustomers;
import model.Customers;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(Integer.MAX_VALUE);
            /* TODO output your page here. You may use following sample code. */
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String totalDebtStr = request.getParameter("total");
            double totalDebt = 0.0;
            try {
                totalDebt = Double.parseDouble(totalDebtStr);
            } catch (NumberFormatException e) {
                e.printStackTrace(); // In lỗi nếu có
            }
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String createAt = now.format(formatter);  // Thời gian hiện tại
            String updateAt = createAt;  // Ban đầu updateAt cũng là thời gian hiện tại
            int createBy =  Integer.parseInt((String) session.getAttribute(session.getId()));
            boolean isDelete = false;
            String deleteAt = null;
            Integer deleteBy = null;

            Customers customer = new Customers(name, email, phone, address, totalDebt, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
            DAOCustomers dao = new DAOCustomers();
            dao.insertCustomer(customer);

            int result = dao.insertCustomer(customer);
            if (result > 0) {
                request.setAttribute("message", "success");
            } else {
                request.setAttribute("message", "error");
            }
                    request.getRequestDispatcher("ListDebt").forward(request, response);

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
        processRequest(request, response);
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
