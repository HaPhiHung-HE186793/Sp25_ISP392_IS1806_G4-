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
        DAOCustomers dao = new DAOCustomers();
        HttpSession session = request.getSession();
        List<Customers> listCustomer = new ArrayList<>();
        Integer role = (Integer) session.getAttribute("roleID");
        Integer createBy = (Integer) session.getAttribute("userID");

        listCustomer = dao.listCustomersByRole(createBy, role);

        request.setAttribute("listCustomer", listCustomer);
        request.getRequestDispatcher("customer/customer.jsp").forward(request, response);

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

        // Lấy các tham số từ request
        String name = request.getParameter("name");

        DAOCustomers dao = new DAOCustomers();
        HttpSession session = request.getSession();
        List<Customers> listCustomer = new ArrayList<>();
        int role = (Integer) session.getAttribute("roleID");
        int createBy = (Integer) session.getAttribute("userID");


        List<Customers> results = dao.listCustomersByRoleSearchName(createBy, role, name);

        // Lưu kết quả vào request
        request.setAttribute("listCustomer", results);

        // Chuyển tiếp kết quả tới JSP
        request.getRequestDispatcher("customer/customer.jsp").forward(request, response);
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

    public static void main(String[] args) {
        DAOCustomers dao = new DAOCustomers();
        List<Customers> listCustomer = new ArrayList<>();

        List<Customers> results = dao.listCustomersByRoleSearchName(2, 1, "yah");
        for (Customers o : results) {
            System.out.println(o.toString());
        }
    }
}
