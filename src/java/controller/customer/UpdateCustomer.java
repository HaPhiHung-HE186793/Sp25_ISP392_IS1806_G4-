/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import DAO.DAOCustomers;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.Customers;

/**
 *
 * @author TIEN DAT PC
 */
public class UpdateCustomer extends HttpServlet {

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
        String customerid = request.getParameter("customerid");
        DAOCustomers dao = new DAOCustomers();                
        Customers customers = dao.getCustomer(customerid);
        if(customers == null){
            response.sendRedirect("ListCustomer");
        }
        request.setAttribute("customers", customers);
        // Lấy message từ session và đặt vào request
        HttpSession session = request.getSession();
        String message = (String) session.getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            session.removeAttribute("message"); // Xóa sau khi dùng để tránh hiển thị lại
        }
        request.getRequestDispatcher("customer/editCustomer.jsp").forward(request, response);
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
        int id = Integer.parseInt(request.getParameter("id"));
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
        String updateAt = now.format(formatter);  // Thời gian hiện tại

        boolean isDelete = false;
        String deleteAt = null;
        Integer deleteBy = null;
        DAOCustomers dao = new DAOCustomers();

        Customers customer = new Customers();
        customer.setCustomerID(id);
        customer.setName(name);
        customer.setAddress(address);
        customer.setPhone(phone);
        customer.setTotalDebt(totalDebt);
        customer.setUpdateAt(updateAt);
        customer.setEmail(email);
        customer.setIsDelete(isDelete);
        int result = dao.updateCustomer(customer);
        if (result > 0) {
            session.setAttribute("message", "success");
        } else {
            session.setAttribute("message", "error");
        }
        response.sendRedirect("UpdateCustomer?customerid=" + id);

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
