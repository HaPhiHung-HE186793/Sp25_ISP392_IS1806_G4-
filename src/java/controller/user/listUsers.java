/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import DAO.DAOStore;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import DAO.DAOUser;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.Store;
import model.pagination.Pagination;
import model.User;

/**
 *
 * @author nguyenanh
 */
public class listUsers extends HttpServlet {

    DAOUser dao = new DAOUser();
    DAOStore daos = new DAOStore();
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
        
        List<Store> storeList = daos.listStore();

        if (request.getParameter("id") != null && !(request.getParameter("id") == "")) {
            int id = Integer.parseInt(request.getParameter("id"));
            User Users = dao.getUserbyID(id);
            if (Users != null) {
                // Không cho phép chỉnh sửa user cùng role
                if (Users.getRoleID() == user_current.getRoleID() && user_current.getID() != Users.getID()) {
                    error = "Không được phép chỉnh sửa.";
                } else 
//                    if (Users.getRoleID() == 3 && user_current.getRoleID() == 1) {
//                    error = "Không được phép chỉnh sửa.";
//                } else 
                        if (Users.getRoleID() == 1 && user_current.getRoleID() == 2) {
                    error = "Không được phép chỉnh sửa.";
                } else {
                    request.setAttribute("u", Users);
                    session.setAttribute("userIdUpdate", Users.getID());
                    request.getRequestDispatcher("updateuser").forward(request, response);
                }
            }
        } 
//        //block user
//        if (request.getParameter("blockid") != null) {
//            int blockid = Integer.parseInt(request.getParameter("blockid"));
//            User U = dao.getUserbyID(blockid);
//            if (U.getRoleID() == 1) {
//                error = "Không được phép khóa tài khoản admin khác.";
//            } else {
//                Integer deleteBy = (Integer) session.getAttribute("userID");
//                dao.blockUserByID(blockid, deleteBy);
//            }
//        }
//        //unlock user
//        if (request.getParameter("unlockid") != null) {
//            int unlockid = Integer.parseInt(request.getParameter("unlockid"));
//            Integer createBy = (Integer) session.getAttribute("userID");
//            dao.unlockUserByID(unlockid, createBy);
//        }

        List<User> U = null;
        if (roleID == 1) {
            U = dao.listUsers();
        } else {
            U = dao.listUsersByStoreID(user_current.getStoreID());
        }
        // Cập nhật pagination dựa trên số lượng kết quả tìm kiếm
        int totalUsers = U.size();
        int pageSize = 9;
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

        String endDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        request.setAttribute("endDate", endDate);
        request.setAttribute("storeList", storeList);
        request.setAttribute("error", error);
        request.setAttribute("user_current", user_current);
        session.setAttribute("U", U);
        request.getRequestDispatcher("user/listUsers.jsp").forward(request, response);

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
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String action = request.getParameter("action");
        String storeIdPara = request.getParameter("storeid");
        Integer selectedAction = (action != null && !action.isEmpty()) ? Integer.parseInt(action) : null;
        Integer role = (roleParam != null && !roleParam.isEmpty()) ? Integer.parseInt(roleParam) : null;
        Integer storeId = (storeIdPara != null && !storeIdPara.isEmpty()) ? Integer.parseInt(storeIdPara) : null;

        String error = null;
        List<Store> storeList = daos.listStore();
        String actionBlock = request.getParameter("actionBlock");
        String userIDBlock = request.getParameter("userIDBlock");
        if (userIDBlock != null && !(userIDBlock == "")) { 
            int id = Integer.parseInt(userIDBlock);
            User U = dao.getUserbyID(id);
            if (U.getRoleID() == 1) {
                error = "Không được phép khóa tài khoản admin khác.";
            } else if ("block".equals(actionBlock)) {
                // Gọi DAO để khóa user
                Integer deleteBy = (Integer) session.getAttribute("userID");
                dao.blockUserByID(id, deleteBy);
            } else if ("unlock".equals(actionBlock)) {
                // Gọi DAO để mở khóa user
                Integer createBy = (Integer) session.getAttribute("userID");
                dao.unlockUserByID(id, createBy);
            }

        }
        if (error != null) {
            response.setStatus(400); // Báo lỗi cho AJAX
            response.getWriter().write(error);
        } else {
            response.setStatus(200);
        }

        //search
        if (user_current.getRoleID() == 1) {
            filteredUsers = dao.listUsers();
        } else {
            filteredUsers = dao.listUsersByStoreID(user_current.getStoreID());
        }

        if (role != null && role != -1) {
            filteredUsers = dao.getUsersByRole(role, filteredUsers);
        }
        if (keyword != null && !keyword.isEmpty()) {
            List<User> tempUsers = dao.getUsersByKeyword(keyword, filteredUsers);
            if (!tempUsers.isEmpty()) {
                filteredUsers = tempUsers; // Chỉ cập nhật nếu có kết quả
            } else {
                mess = "Không tìm thấy người dùng nào.";
            }
        }
        if (storeId != null && storeId != -1) {
            List<User> tempUsers = dao.getUsersByStore(storeId, filteredUsers);
            if (!tempUsers.isEmpty()) {
                filteredUsers = tempUsers; // Chỉ cập nhật nếu có kết quả
            } else {
                mess = "Không tìm thấy người dùng nào.";
            }
        }
        if (startDate != null && !startDate.isEmpty()) {
            List<User> tempUsers = dao.getUsersByDate(startDate, endDate, filteredUsers);
            if (!tempUsers.isEmpty()) {
                filteredUsers = tempUsers; // Chỉ cập nhật nếu có kết quả
            } else {
                mess = "Không tìm thấy người dùng nào.";
            }
        }
        if (selectedAction != null && selectedAction != -1) {
            List<User> tempUsers = dao.getUsersByAction(selectedAction, filteredUsers);
            if (!tempUsers.isEmpty()) {
                filteredUsers = tempUsers; // Chỉ cập nhật nếu có kết quả
            } else {
                mess = "Không tìm thấy người dùng nào.";
            }
        }
        if (filteredUsers == null || filteredUsers.isEmpty()) {
            mess = "Không tìm thấy người dùng nào.";
            System.out.println(mess);
            filteredUsers = (user_current.getRoleID() == 1)
                    ? dao.listUsers()
                    : dao.listUsersByStoreID(user_current.getStoreID());

        }

        //lay ten creatrBy
        for (User user : filteredUsers) {
            User u = dao.getUserbyID(user.getCreateBy());
            String creatorName = u.getUserName();
            user.setCreatorName(creatorName);
        }
        // Cập nhật pagination dựa trên số lượng kết quả tìm kiếm
        int totalUsers = filteredUsers.size();
        int pageSize = 9;
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

        request.setAttribute("storeList", storeList);
        request.setAttribute("mess", mess);
        request.setAttribute("error", error);
        request.setAttribute("U", filteredUsers);
        request.setAttribute("selectedRole", role);
        request.setAttribute("keyword", keyword);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("selectedAction", selectedAction);
        request.setAttribute("user_current", user_current);
        request.getRequestDispatcher("user/listUsers.jsp").forward(request, response);
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
