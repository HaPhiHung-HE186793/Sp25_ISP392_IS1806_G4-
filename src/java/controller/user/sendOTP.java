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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import utils.mail;

/**
 *
 * @author nguyenanh
 */
public class sendOTP extends HttpServlet {

    DAO.DAOUser daou = new DAOUser();
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private static ExecutorService emailExecutor = Executors.newSingleThreadExecutor(); // Tạo luồng riêng để gửi email

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        HttpSession session = request.getSession();
        List<String> errors = new ArrayList<>();
        // Kiểm tra xem email đã tồn tại chưa (giả sử có hàm checkEmailExists)
        if (!Pattern.matches(EMAIL_REGEX, email)) {
            errors.add("Email không hợp lệ!");
        } else if (daou.isEmailExists(email)) {
            errors.add("Email đã tồn tại!");
        }

        // Nếu có lỗi, chuyển hướng về trang JSP và hiển thị lỗi
        // Nếu có lỗi, trả về JSON cho JavaScript xử lý
        if (!errors.isEmpty()) {
            out.print("{\"success\": false, \"message\": \"" + errors.get(0) + "\"}");
            out.flush();
            return;
        }

        // Nếu không có lỗi, tiếp tục tạo OTP và gửi email
        String otp = generateOTP();
        session.setAttribute("otp", otp);
        session.setAttribute("email", email);
        session.setAttribute("otpTime", System.currentTimeMillis());

        restartEmailExecutor();
        mail sendEmail = new mail();
        emailExecutor.execute(() -> sendEmail.sendMailVerify(email, otp));

        out.print("{\"success\": true, \"message\": \"OTP đã được gửi!\"}");
        out.flush();

    }

    private String generateOTP() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    private synchronized void restartEmailExecutor() {
        if (emailExecutor.isShutdown() || emailExecutor.isTerminated()) {
            emailExecutor = Executors.newFixedThreadPool(2);
        }
    }

}
