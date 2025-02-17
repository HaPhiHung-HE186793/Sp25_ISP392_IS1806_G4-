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
import java.util.ArrayList;
import java.util.List;
import model.Customers;
import model.User;

/**
 *
 * @author TIEN DAT PC
 */
public class ListCustomer extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        DAOCustomers dao = new DAOCustomers();
        HttpSession session = request.getSession();
        List<Customers> listCustomer = dao.listAll();
        request.setAttribute("message", "");
        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);
        request.setAttribute("listCustomer", listCustomer);
        request.getRequestDispatcher("customer/customer.jsp").forward(request, response);
        
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        
        // Lấy các tham số tìm kiếm từ request (từ form hoặc API call)
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        // Gọi phương thức tìm kiếm trong DAO
        DAOCustomers dao = DAOCustomers.INSTANCE;
        List<Customers> results = dao.searchCustomers(name, phone, fromDate, toDate);

        // Lưu kết quả vào request để chuyển tiếp tới JSP (hoặc trang web khác)
        request.setAttribute("customersList", results);
        
        // Chuyển tiếp kết quả tìm kiếm tới một JSP để hiển thị (hoặc có thể gửi lại JSON)
        request.getRequestDispatcher("customer/customer.jsp").forward(request, response);
        
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
