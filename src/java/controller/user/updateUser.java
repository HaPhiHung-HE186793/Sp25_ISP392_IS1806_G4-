/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import DAO.DAOUser;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import model.User;
import utils.mail;

/**
 *
 * @author nguyenanh
 */
public class updateUser extends HttpServlet {

    DAO.DAOUser daou = new DAOUser();
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    mail sendEmail = new mail();
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
            out.println("<title>Servlet updateUser</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateUser at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        Integer roleID = (Integer) session.getAttribute("roleID");
        if (roleID != 1) {
            response.sendRedirect("ListProducts"); // sửa thành đường dẫn của trang chủ sau khi hoàn thành code
            return;
        }
        Integer userid = (Integer) session.getAttribute("userIdUpdate");
        User user = daou.getUserbyID(userid);
        request.setAttribute("user", user);
        request.getRequestDispatcher("user/updateUser.jsp").forward(request, response);

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
        List<String> errors = new ArrayList<>();
        HttpSession session = request.getSession();
        Integer userid = (Integer) session.getAttribute("userIdUpdate");
        
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String newPassword = request.getParameter("password");
        String confirmPassword = request.getParameter("cfpass");
        String roleParam = request.getParameter("roleID");
 
        //lay ra user khi chi update
        User u = daou.getUserbyID(userid);
        int roleID = 0; // Mặc định giá trị không hợp lệ
        if (roleParam != null) {
            switch (roleParam) {
                case "1":
                    roleID = 1;
                    break;
                case "2":
                    roleID = 2;
                    break;
                case "3":
                    roleID = 3;
                    break;
                default:
                    errors.add("Vai trò không hợp lệ!");
            }
        } else {
            errors.add("Vui lòng chọn chức năng!");
        }
        // lấy id của user hiện tại
        Integer createBy = (Integer) session.getAttribute("userID");
        if (createBy == null) {
            response.sendRedirect("/login/login.jsp");
            return;
        }

        if (userName == null || userName.trim().isEmpty()) {
            errors.add("Vui lòng nhập đầy đủ họ tên!");
        }
        if (email == null || email.trim().isEmpty()) {
            errors.add("Vui lòng nhập email!");
        } else if (!Pattern.matches(EMAIL_REGEX, email)) {
            errors.add("Email không hợp lệ!");
        } else {            
            if (daou.isEmailExists(email) && !u.getEmail().equals(email)) {
                errors.add("Email đã tồn tại!");
            }
        }

        if (!newPassword.equals(confirmPassword)) {
            errors.add("Mật khẩu không trùng khớp");
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("userName", userName);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/user/updateUser.jsp").forward(request, response);
            return;
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            User newUser = new User();
            newUser.setID(userid);
            newUser.setUserName(userName);
            newUser.setEmail(email);
            newUser.setUserPassword(newPassword);
            newUser.setRoleID(roleID);
            newUser.setCreateBy(createBy);
            daou.updateUser2(newUser);
            //gửi email password được sinh ra cho người dùng           
            sendEmail.sendPasswordChangeConfirmation(email, newPassword);
            if(!u.getEmail().equals(email)){
            sendEmail.sendEmailChangeConfirmation(u.getEmail(), email);
            }
        }
        else {
            User newUser = new User();
            newUser.setID(userid);
            newUser.setUserName(userName);
            newUser.setEmail(email);
            newUser.setRoleID(roleID);
            newUser.setCreateBy(createBy);
            daou.updateUser3(newUser);
            if(!u.getEmail().equals(email)){
            sendEmail.sendEmailChangeConfirmation(u.getEmail(), email);
            }
        }

        // lay ra user sau khi update
        User user = daou.getUserbyID(userid);
        request.setAttribute("user", user);
        request.setAttribute("success", "Cập nhât thành công!");
        request.getRequestDispatcher("/user/updateUser.jsp").forward(request, response);
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
