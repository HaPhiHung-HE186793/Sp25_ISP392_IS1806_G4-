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
import model.pagination.Pagination;
import model.User;

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
        Integer roleID = (Integer) session.getAttribute("roleID");
        Integer userID = (Integer) session.getAttribute("userID");
        if (roleID != 1) {
            response.sendRedirect("ListProducts"); // sửa thành đường dẫn của trang chủ sau khi hoàn thành code
            return;
        }
        String error = null;
        //update users
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            User U = dao.getUserbyID(id);
            // Không cho phép chỉnh sửa admin khác
            if (U.getRoleID() == 1 && userID != U.getID()) {
                error = "Không được phép chỉnh sửa admin khác.";
            } else {
                request.setAttribute("u", U);
                session.setAttribute("userIdUpdate", U.getID());
                request.getRequestDispatcher("updateuser").forward(request, response);
            }
        }
        //block user
        if (request.getParameter("blockid") != null) {
            int blockid = Integer.parseInt(request.getParameter("blockid"));
            User U = dao.getUserbyID(blockid);
            if (U.getRoleID() == 1) {
                error = "Không được phép khóa tài khoản admin khác.";
            } else {
                Integer deleteBy = (Integer) session.getAttribute("userID");
                dao.blockUserByID(blockid, deleteBy);
            }
        }
        //unlock user
        if (request.getParameter("unlockid") != null) {
            int unlockid = Integer.parseInt(request.getParameter("unlockid"));
            Integer createBy = (Integer) session.getAttribute("userID");
            dao.unlockUserByID(unlockid, createBy);
        }

        List<User> U = dao.listUsers();
        //seting pagination
        //check page
        //at the first time
        if (session.getAttribute("page") == null) {
            Page = new Pagination(U.size(), 10, 1);
            session.setAttribute("page", Page);
        } else if (request.getParameter("cp") != null) {
            int cp = Integer.parseInt(request.getParameter("cp"));
            Page = new Pagination(U.size(), 10, cp);
            session.setAttribute("page", Page);
        }

        for (User user : U) {
            User u = dao.getUserbyID(user.getCreateBy());
            String creatorName = u.getUserName();
            user.setCreatorName(creatorName);
        }

        request.setAttribute("error", error);
        session.setAttribute("U", U);
        request.getRequestDispatcher("user/list_users.jsp").forward(request, response);

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

        List<User> filteredUsers = null;
        String mess = null;
        String roleParam = request.getParameter("role");
        String keyword = request.getParameter("keyword");
        Integer role = (roleParam != null && !roleParam.isEmpty()) ? Integer.parseInt(roleParam) : null;

        // Xử lý lọc
        if (role == 0) {
            filteredUsers = dao.listUsers();
        } else 
        if (role != null && keyword != null && !keyword.isEmpty()) {
            filteredUsers = dao.getUsersByRoleAndKeyword(role, keyword);
        } else if (role != null) {
            filteredUsers = dao.getUsersByRole(role);
        } else if (keyword != null && !keyword.isEmpty()) {
            filteredUsers = dao.getUsersByKeyword(keyword);
        } else {
            if (filteredUsers == null || filteredUsers.isEmpty()){
                mess = "Không tìm thấy người dùng nào.";
            }
            filteredUsers = dao.listUsers(); // Lấy toàn bộ danh sách nếu không có lọc
        }

        for (User user : filteredUsers) {
            User u = dao.getUserbyID(user.getCreateBy());
            String creatorName = u.getUserName();
            user.setCreatorName(creatorName);
        }

        request.setAttribute("mess", mess);
        request.setAttribute("U", filteredUsers);
        request.setAttribute("selectedRole", role);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("user/list_users.jsp").forward(request, response);
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
