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
public class ListDebtCustomer extends HttpServlet {

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

        DAODebtRecords dao = new DAODebtRecords();
        DAOCustomers daoC = new DAOCustomers();
        String customerid = request.getParameter("customerid");
        
        Customers customers = daoC.getCustomer(customerid);
        
//        if(customers ==null){
//            response.sendRedirect("ListDebtCustomer");
//        }
        List<DebtRecords> listCustomer = dao.listAllbyName(customerid);
        request.setAttribute("listCustomer", listCustomer);
        request.setAttribute("customers", customers);

//        // Cập nhật pagination dựa trên số lượng kết quả tìm kiếm
//        int totalUsers = listCustomer.size();
//        int pageSize = 10;
//        int currentPage = 1;
//
//        if (request.getParameter("cp") != null) {
//            currentPage = Integer.parseInt(request.getParameter("cp"));
//        }
//
//        Pagination page = new Pagination(totalUsers, pageSize, currentPage);
//        session.setAttribute("page", page);
//        request.setAttribute("currentPageUrl", "ListDebtCustomer");

        request.getRequestDispatcher("debt/debt.jsp").forward(request, response);
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
//        DAODebtRecords dao = new DAODebtRecords();
//        DAOCustomers daoC = new DAOCustomers();
//        String customerid = request.getParameter("customerid");
//        Customers customers = daoC.getCustomer(customerid);
//        List<DebtRecords> listCustomer = dao.listAllbyName(customerid);
//        
//        
//         // Lấy các tham số từ request
//        String name = request.getParameter("name");
//        String number = request.getParameter("number");
//        String startDate = request.getParameter("startDate");
//        String endDate = request.getParameter("endDate");
//        String sql = "";
//// Điều kiện name
//        if (name != null && !name.isEmpty()) {
//            sql += "AND c.name LIKE '%" + name + "%' ";
//        }
//
//// Điều kiện startDate
//        if (startDate != null && !startDate.isEmpty()) {
//            sql +=  "and CONVERT(date, c.createAt) >= '" + startDate + "' ";
//        }
//
//// Điều kiện endDate
//        if (endDate != null && !endDate.isEmpty()) {
//            sql += "and CONVERT(date, c.updateAt) <= '" + endDate + "' ";
//        }
//
//// Điều kiện number
//        if (number != null && !number.isEmpty()) {
//            sql += "and c.phone like '" + number + "%' ";
//        }
//        
//        
//        
//        
//        request.setAttribute("listCustomer", listCustomer);
//        request.setAttribute("customers", customers);
//
//        request.getRequestDispatcher("debt/debt.jsp").forward(request, response);

//  HttpSession session = request.getSession();
//
//        DAODebtRecords dao = new DAODebtRecords();
//        DAOCustomers daoC = new DAOCustomers();
//        String customerid = request.getParameter("customerid");
//        
//        Customers customers = daoC.getCustomer(customerid);
//        
//        if(customers ==null){
//            response.sendRedirect("ListDebtCustomer");
//        }
//        List<DebtRecords> listCustomer = dao.listAllbyName(customerid);
//        request.setAttribute("listCustomer", listCustomer);
//        request.setAttribute("customers", customers);
//
//        // Cập nhật pagination dựa trên số lượng kết quả tìm kiếm
//        int totalUsers = listCustomer.size();
//        int pageSize = 10;
//        int currentPage = 1;
//
//        if (request.getParameter("cp") != null) {
//            currentPage = Integer.parseInt(request.getParameter("cp"));
//        }
//
//        Pagination page = new Pagination(totalUsers, pageSize, currentPage);
//        session.setAttribute("page", page);
//        request.setAttribute("currentPageUrl", "ListDebtCustomer");
//
//        request.getRequestDispatcher("debt/debt.jsp").forward(request, response);
    

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

//    public static void main(String[] args) {
//                        DAODebtRecords dao = new DAODebtRecords();
//
//        List<DebtRecords> list = dao.listAllbyName("21");
//        for (DebtRecords o : list) {
//            System.out.println(o.toString());
//        }
//    }
}
