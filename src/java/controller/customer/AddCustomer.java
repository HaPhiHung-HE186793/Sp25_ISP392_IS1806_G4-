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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.Customers;

/**
 *
 * @author TIEN DAT PC
 */
public class AddCustomer extends HttpServlet {

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

        request.getRequestDispatcher("ListCustomer").forward(request, response);

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

        /* TODO output your page here. You may use following sample code. */
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String totalDebtStr = request.getParameter("total");
        Integer createBy = (Integer) session.getAttribute("userID");
        BigDecimal debt = BigDecimal.ZERO;
        String debtStr = request.getParameter("debt");
        if (debtStr != null && !debtStr.trim().isEmpty()) {
            try {
                debt = new BigDecimal(debtStr);
            } catch (NumberFormatException e) {
                response.sendRedirect("ListCustomer");
                return;
            }
        }

        try {
            // Chỉ chấp nhận giá trị mặc định "0", nếu khác thì báo lỗi
            if (!"0".equals(totalDebtStr)) {
                request.setAttribute("message", "error");
                new ListCustomer().doGet(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("message", "error");
            new ListCustomer().doGet(request, response);
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String createAt = now.format(formatter);  // Thời gian hiện tại
        String updateAt = createAt;  // Ban đầu updateAt cũng là thời gian hiện tại

        boolean isDelete = false;
        String deleteAt = null;
        Integer deleteBy = null;
        DAOCustomers dao = new DAOCustomers();
        Customers customer = new Customers();
        customer.setName(name);
        customer.setAddress(address);
        customer.setPhone(phone);
        customer.setTotalDebt(debt);
        customer.setCreateAt(createAt);
        customer.setUpdateAt(updateAt);
        customer.setCreateBy(createBy);
        customer.setEmail(email);
        customer.setIsDelete(isDelete);

        int result = dao.insertNewCustomer(customer);
        if (result > 0) {
            request.setAttribute("message", "success");
        } else {
            request.setAttribute("message", "error");
        }
        new ListCustomer().doGet(request, response);

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
//public static void main(String[] args) {
//       DAOCustomers dao = new DAOCustomers();
//       Customers newCustomer = new Customers(3, "Nguyễn Thu B", "nguyenvan@gmail.com", "0987654321", "456 Đường XYZ", 2000.0, "2023-01-02", "2023-01-02", 1, false, null, 0);
//       int insertResult = dao.insertNewCustomer(newCustomer);
//}
}
