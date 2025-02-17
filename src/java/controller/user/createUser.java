/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import DAO.DAOUser;
import model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.awt.geom.GeneralPath;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import utils.mail;

/**
 *
 * @author nguyenanh
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class createUser extends HttpServlet {

    DAO.DAOUser daou = new DAOUser();
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    private void handleAddUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errors = new ArrayList<>();

        // Lấy dữ liệu từ form
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String roleParam = request.getParameter("roleID");

        // Xác định roleID từ giá trị radio button
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
        HttpSession session = request.getSession();
        Integer createBy = (Integer) session.getAttribute("userID");
        if (createBy == null) {
            response.sendRedirect("/login/login.jsp"); // Chuyển hướng về trang đăng nhập nếu chưa có userID
            return;
        }

        //Customer customer = new Customer(fullName,);
        // Kiểm tra dữ liệu nhập
        if (userName == null || userName.trim().isEmpty()) {
            errors.add("Vui lòng nhập đầy đủ họ tên!");
        }
        if (email == null || email.trim().isEmpty()) {
            errors.add("Vui lòng nhập email!");
        } else if (!Pattern.matches(EMAIL_REGEX, email)) {
            errors.add("Email không hợp lệ!");
        } else if (daou.isEmailExists(email)) {
            errors.add("Email đã tồn tại!");
        }

        // Nếu có lỗi, quay lại trang addCustomer.jsp với danh sách lỗi và dữ liệu đã nhập
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("userName", userName);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/user/createUser.jsp").forward(request, response);
            return;
        }

        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setEmail(email);
        newUser.setUserPassword(password);
        newUser.setRoleID(roleID);
        newUser.setCreateBy(createBy);
        newUser.setIsDelete(false);
        daou.insertNewUser(newUser);

        //gửi email password được sinh ra cho người dùng
        mail sendEmail = new mail();
        sendEmail.sendPasswordForUser(email, password);

        request.setAttribute("success", "Thêm thành công!");
        request.setAttribute("username", userName);
        request.setAttribute("email", email);
        request.getRequestDispatcher("/user/createUser.jsp").forward(request, response);
    }

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
            out.println("<title>Servlet createUser</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet createUser at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("/user/createUser.jsp").forward(request, response);
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
        handleAddUser(request, response);
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
