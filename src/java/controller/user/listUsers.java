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
        User user_current = (User) session.getAttribute("user");
        if (roleID != 1 && roleID != 2) {
            response.sendRedirect("ListProducts"); // sửa thành đường dẫn của trang chủ sau khi hoàn thành code
            return;
        }
        String error = null;
        //update users
//        if (request.getParameter("id") != null) {
//            int id = Integer.parseInt(request.getParameter("id"));
//            User U = dao.getUserbyID(id);
//            // Không cho phép chỉnh sửa admin khác
//            if (U.getRoleID() == 1 && userID != U.getID()) {
//                error = "Không được phép chỉnh sửa admin khác.";
//            } else {
//                request.setAttribute("u", U);
//                session.setAttribute("userIdUpdate", U.getID());
//                request.getRequestDispatcher("updateuser").forward(request, response);
//            }
//        }
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            User Users = dao.getUserbyID(id);
            if (Users != null) {
                // Không cho phép chỉnh sửa user cùng role
                if (Users.getRoleID() == user_current.getRoleID() && user_current.getID() != Users.getID()) {
                    error = "Không được phép chỉnh sửa.";
                } else if (Users.getRoleID() == 3 && user_current.getRoleID() == 1) {
                    error = "Không được phép chỉnh sửa.";
                } else if (Users.getRoleID() == 1 && user_current.getRoleID() == 2) {
                    error = "Không được phép chỉnh sửa.";
                } else {
                    request.setAttribute("u", Users);
                    session.setAttribute("userIdUpdate", Users.getID());
                    request.getRequestDispatcher("updateuser").forward(request, response);
                }
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

        List<User> U = null;
        if (roleID == 1) {
            U = dao.listUsers();
        } else {
            U = dao.listUsersByOwner(user_current.getID());
        }
        // Cập nhật pagination dựa trên số lượng kết quả tìm kiếm
        int totalUsers = U.size();
        int pageSize = 8;
        int currentPage = 1;

        if (request.getParameter("cp") != null) {
            currentPage = Integer.parseInt(request.getParameter("cp"));
        }

        Pagination page = new Pagination(totalUsers, pageSize, currentPage);
        session.setAttribute("page", page);
        request.setAttribute("currentPageUrl", "listusers"); // Hoặc "products"

        for (User user : U) {
            User u = dao.getUserbyID(user.getCreateBy());
            String creatorName = u.getUserName();
            user.setCreatorName(creatorName);
        }

        request.setAttribute("error", error);
        request.setAttribute("user_current", user_current);
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
        HttpSession session = request.getSession();
        User user_current = (User) session.getAttribute("user");
        List<User> filteredUsers = null;
        String mess = null;
        String roleParam = request.getParameter("role");
        String keyword = request.getParameter("keyword");
        Integer role = (roleParam != null && !roleParam.isEmpty()) ? Integer.parseInt(roleParam) : null;

        //search
        if (user_current.getRoleID() == 1) {
            filteredUsers = dao.listUsers();
        } else {
            filteredUsers = dao.listUsersByOwner(user_current.getID());
        }

        if (role != null && role != -1) {
            filteredUsers = dao.getUsersByRole(role, filteredUsers);
        }
        if (keyword != null && !keyword.isEmpty()) {
            List<User> tempUsers = dao.getUsersByKeyword(keyword, filteredUsers);
            if (!tempUsers.isEmpty()) {
                filteredUsers = tempUsers; // Chỉ cập nhật nếu có kết quả
            } else mess = "Không tìm thấy người dùng nào.";
        }
        if (filteredUsers == null || filteredUsers.isEmpty()) {
            mess = "Không tìm thấy người dùng nào.";
            System.out.println(mess);
            filteredUsers = (user_current.getRoleID() == 1)
                    ? dao.listUsers()
                    : dao.listUsersByOwner(user_current.getID());

        }

//        if (user_current.getRoleID() == 1) {
//            if (role != null && role == 0) {
//                filteredUsers = dao.listUsers();
//            } else if (role != -1 && keyword != null && !keyword.isEmpty()) {
//                boolean[] isKeywordSearchEmpty = {false};
//                filteredUsers = dao.getUsersByRoleAndKeyword(role, keyword, isKeywordSearchEmpty);
//                if (isKeywordSearchEmpty[0]) {
//                    mess = "Không tìm thấy người dùng nào.";
//                }
//            } else if (role != -1) {
//                filteredUsers = dao.getUsersByRole(role);
//            } else if (keyword != null && !keyword.isEmpty()) {
//                filteredUsers = dao.getUsersByKeyword(keyword);
//            }
//            if (filteredUsers == null || filteredUsers.isEmpty()) {
//                mess = "Không tìm thấy người dùng nào.";
//                filteredUsers = dao.listUsers(); // Lấy toàn bộ danh sách nếu không có lọc
//            }
//        }
//        if (user_current.getRoleID() == 2) {
//            if (keyword != null && !keyword.isEmpty()) {
//                filteredUsers = dao.getUsersByKeywordAndOwner(keyword, user_current.getID());
//            }
//            if (filteredUsers == null || filteredUsers.isEmpty()) {
//                mess = "Không tìm thấy người dùng nào.";
//                System.out.println(mess);
//                filteredUsers = dao.listUsersByOwner(user_current.getID());
//            }
//        }
        //lay ten creatrBy
        for (User user : filteredUsers) {
            User u = dao.getUserbyID(user.getCreateBy());
            String creatorName = u.getUserName();
            user.setCreatorName(creatorName);
        }
        // Cập nhật pagination dựa trên số lượng kết quả tìm kiếm
        int totalUsers = filteredUsers.size();
        int pageSize = 8;
        int currentPage = 1;

        if (request.getParameter("cp") != null) {
            currentPage = Integer.parseInt(request.getParameter("cp"));
        }
        request.setAttribute("currentPageUrl", "listusers"); // Hoặc "products"

        Pagination page = new Pagination(totalUsers, pageSize, currentPage);
        session.setAttribute("page", page);

        // Lấy danh sách user theo trang hiện tại
        int startIndex = page.getStartItem();
        int endIndex = Math.min(startIndex + pageSize, totalUsers);
        List<User> paginatedUsers = filteredUsers.subList(startIndex, endIndex);

        request.setAttribute("mess", mess);
        request.setAttribute("U", filteredUsers);
        request.setAttribute("selectedRole", role);
        request.setAttribute("keyword", keyword);
        request.setAttribute("user_current", user_current);
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
//if (request.getParameter("id") != null) {
//            int id = Integer.parseInt(request.getParameter("id"));
//            User Users = dao.getUserbyID(id);
//            // Không cho phép chỉnh sửa user cùng role
//            if (Users.getRoleID() == user_current.getRoleID() && userID != Users.getID()) {
//                error = "Không được phép chỉnh sửa.";
//            } else  if(Users.getRoleID() == 3  && user_current.getRoleID() == 1) {
//                error = "Không được phép chỉnh sửa.";
//            } else {
//                request.setAttribute("u", U);
//                session.setAttribute("userIdUpdate", Users.getID());
//                request.getRequestDispatcher("updateuser").forward(request, response);
//            }
//        }

//List<User> U = dao.listUsers();
//search theo role
//U = dao.getSearchbyRole( roleId, U);
//sau ddos search theo keyword
//U = dao.getSearchByKey( key, U);
// trong ham dao se tru di users trung lap
