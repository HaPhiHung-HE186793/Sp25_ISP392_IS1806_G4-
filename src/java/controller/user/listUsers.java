/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import DAO.DAOUser;
import Entity.Pagination.Pagination;
import Entity.User;

/**
 *
 * @author nguyenanh
 */
public class listUsers extends HttpServlet {

    DAOUser dao = new DAOUser();
    Pagination Page;

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet listUsers</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet listUsers at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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

        //processRequest(request, response);
        HttpSession session = request.getSession();
        List<User> U = dao.listUsers();

        int currentPage = 1;
        int totalPage = (int) Math.ceil((double) U.size() / 1); // Số trang dựa trên tổng dữ liệu
        // Kiểm tra nếu có giá trị cp trên URL
        if (request.getParameter("cp") != null) {
            try {
                int cp = Integer.parseInt(request.getParameter("cp"));
                if (cp > 0 && cp <= totalPage) {
                    currentPage = cp;
                }
            } catch (NumberFormatException e) {
                // Nếu cp không hợp lệ, giữ nguyên trang 1
            }
        }
        // Cập nhật session với trang hợp lệ
        Page = new Pagination(U.size(), 1, currentPage);
        session.setAttribute("page", Page);

        session.setAttribute("U", U);
        request.getRequestDispatcher("list_users.jsp").forward(request, response);

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

        List<User> filteredUsers;
        String roleParam = request.getParameter("role");
        String keyword = request.getParameter("keyword");
        Integer role = (roleParam != null && !roleParam.isEmpty()) ? Integer.parseInt(roleParam) : null;

        // Xử lý lọc
        if (role != null && keyword != null && !keyword.isEmpty()) {
            filteredUsers = dao.getUsersByRoleAndKeyword(role, keyword);
        } else if (role != null) {
            filteredUsers = dao.getUsersByRole(role);
        } else if (keyword != null && !keyword.isEmpty()) {
            filteredUsers = dao.getUsersByKeyword(keyword);
        } else {
            filteredUsers = dao.listUsers(); // Lấy toàn bộ danh sách nếu không có lọc
        }

        request.setAttribute("U", filteredUsers);
        request.setAttribute("selectedRole", role);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("list_users.jsp").forward(request, response);
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
