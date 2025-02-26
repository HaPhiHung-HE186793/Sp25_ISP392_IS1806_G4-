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
import model.pagination.Pagination;

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
        Integer createBy = (Integer) session.getAttribute("createBy");
        
  
        listCustomer = dao.listCustomersByRole(createBy);

        // Cập nhật pagination dựa trên số lượng kết quả tìm kiếm
        int totalUsers = listCustomer.size();
        int pageSize = 10;
        int currentPage = 1;

        if (request.getParameter("cp") != null) {
            currentPage = Integer.parseInt(request.getParameter("cp"));
        }

        Pagination page = new Pagination(totalUsers, pageSize, currentPage);
        session.setAttribute("page", page);
        int startIndex = page.getStartItem();
        int endIndex = Math.min(startIndex + pageSize, totalUsers);
        List<Customers> paginatedUsers = listCustomer.subList(startIndex, endIndex);
        request.setAttribute("currentPageUrl", "ListCustomer");
        request.setAttribute("currentPageUrl", "ListCustomer");

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
        DAOCustomers dao = new DAOCustomers();

        // Lấy các tham số từ request
        String name = request.getParameter("name");
        String number = request.getParameter("number");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        
        
        
        String sql = "";
// Điều kiện name
        if (name != null && !name.isEmpty()) {
            sql += "AND c.name LIKE '%" + name + "%' ";
        }

// Điều kiện startDate
        if (startDate != null && !startDate.isEmpty()) {
            sql +=  "and CONVERT(date, c.createAt) >= '" + startDate + "' ";
        }

// Điều kiện endDate
        if (endDate != null && !endDate.isEmpty()) {
            sql += "and CONVERT(date, c.updateAt) <= '" + endDate + "' ";
        }

// Điều kiện number
        if (number != null && !number.isEmpty()) {
            sql += "and c.phone like '%" + number + "%' ";
        }

        HttpSession session = request.getSession();
        List<Customers> listCustomer = new ArrayList<>();
        int role = (Integer) session.getAttribute("roleID");
        int createBy = (Integer) session.getAttribute("createBy");


        listCustomer = dao.listCustomersByRoleSearchName(createBy, sql);
        request.setAttribute("searchName", name);
        request.setAttribute("searchNumber", number);
        request.setAttribute("searchStartDate", startDate);
        request.setAttribute("searchEndDate", endDate);
        request.setAttribute("listCustomer", listCustomer);


       // Cập nhật pagination dựa trên số lượng kết quả tìm kiếm
        int totalUsers = listCustomer.size();
        int pageSize = 10;
        int currentPage = 1;

        if (request.getParameter("cp") != null) {
            currentPage = Integer.parseInt(request.getParameter("cp"));
        }

        Pagination page = new Pagination(totalUsers, pageSize, currentPage);
        session.setAttribute("page", page);
        int startIndex = page.getStartItem();
        int endIndex = Math.min(startIndex + pageSize, totalUsers);
        List<Customers> paginatedUsers = listCustomer.subList(startIndex, endIndex);
        request.setAttribute("currentPageUrl", "ListCustomer");
        request.setAttribute("currentPageUrl", "ListCustomer");
        // Lưu kết quả vào request
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

    
    }

