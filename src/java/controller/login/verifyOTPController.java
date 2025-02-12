package controller.login;

import DAO.DAOUser;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Entity.User;

/**
 *
 * @author admin
 */
@WebServlet(name = "verifyOTPController", urlPatterns = {"/verifyOTP"})
public class verifyOTPController extends HttpServlet {

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
            out.println("<title>Servlet NewServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        try (PrintWriter out = response.getWriter()) {
            String enteredOTP = request.getParameter("otp");
            HttpSession session = request.getSession();
            Object storedOTPObject = session.getAttribute("otp");

            // Kiểm tra nếu OTP lưu trong session là Integer, chuyển đổi nó sang String
            String storedOTP;
            if (storedOTPObject instanceof Integer) {
                storedOTP = String.valueOf((Integer) storedOTPObject);
            } else {
                storedOTP = (String) storedOTPObject;
            }

            // So sánh OTP nhập vào với OTP lưu trong session
            if (enteredOTP.equals(storedOTP)) {
                // OTP verified, redirect to login page
                int ID = (Integer) session.getAttribute("ID"); // Sửa thành ID
                String userName = (String) session.getAttribute("userName"); // Sửa thành userName
                String userPassword = (String) session.getAttribute("userPassword"); // Sửa thành userPassword
                String email = (String) session.getAttribute("email"); // Giữ nguyên
                int roleID = (Integer) session.getAttribute("roleID"); // Sửa thành int roleID
                String createAt = (String) session.getAttribute("createAt"); // Thêm nếu có
                String updateAt = (String) session.getAttribute("updateAt"); // Thêm nếu có
                String createBy = (String) session.getAttribute("createBy"); // Thêm nếu có
                String isDelete = (String) session.getAttribute("isDelete"); // Thêm nếu có
                String deleteAt = (String) session.getAttribute("deleteAt"); // Thêm nếu có
                String deleteBy = (String) session.getAttribute("deleteBy"); // Thêm nếu có // Retrieve dob from session               
                DAOUser dao = new DAOUser();
                User user = new User( userName, userPassword, email, roleID, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);

           if (enteredOTP.equals(storedOTP)) {
            // OTP verified, redirect to login page
            response.sendRedirect("login.jsp");
        } else {
            // Invalid OTP, display error message
            request.setAttribute("error", "Invalid OTP");
            request.getRequestDispatcher("verifyOTP.jsp").forward(request, response);
        }
            }
    }
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
