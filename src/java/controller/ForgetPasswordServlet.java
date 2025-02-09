package controller;

import DAO.DAOForget;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import Entity.User;

@WebServlet(name = "ForgetPasswordServlet", urlPatterns = {"/forgotpass"})
public class ForgetPasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String step = request.getParameter("step");
        DAOForget dao = new DAOForget();
        HttpSession session = request.getSession();

       if ("1".equals(step)) {
    String email = request.getParameter("email");
    User acc = dao.checkEmail(email);
    if (acc != null) {
        session.setAttribute("email", email); // Lưu email vào session
        request.setAttribute("email", email);
        request.getRequestDispatcher("forgetpass/ForgetPassword2.jsp").forward(request, response);
    } else {
        request.setAttribute("errorMessage", "Email không tồn tại trong hệ thống.");
        request.setAttribute("email", email);
        request.getRequestDispatcher("forgetpass/ForgetPassword.jsp").forward(request, response);
    }
}
        // Step 2: Generate and send OTP
        if ("2".equals(step)) {
            String email = request.getParameter("email");

            String otpCode = MailUtil.generateOTP();
            MailUtil.sendOTP(email, otpCode);

            session.setAttribute("otpCode", otpCode);
            session.setAttribute("otpGenerationTime", LocalDateTime.now());

            request.setAttribute("email", email);
            request.getRequestDispatcher("forgetpass/ForgetPassword3.jsp").forward(request, response);
        }

        // Step 3: Validate OTP
        if ("3".equals(step)) {
            String enteredOtp = request.getParameter("otp");
            String email = request.getParameter("email");
            String sessionOtp = (String) session.getAttribute("otpCode");
            LocalDateTime otpGenerationTime = (LocalDateTime) session.getAttribute("otpGenerationTime");

            if (enteredOtp.equals(sessionOtp) && LocalDateTime.now().isBefore(otpGenerationTime.plusMinutes(5))) {
                request.setAttribute("email", email);
                request.getRequestDispatcher("forgetpass/ForgetPassword4.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "OTP không chính xác hoặc đã hết hạn.");
                request.setAttribute("email", email);
                request.getRequestDispatcher("forgetpass/ForgetPassword3.jsp").forward(request, response);
            }
        }

        if ("resend".equals(step)) {
            String email = (String) request.getParameter("email");

            // Kiểm tra xem email có tồn tại trong hệ thống không
            User acc = dao.checkEmail(email);
            if (acc != null) {
                // Tạo và gửi lại OTP
                String otpCode = MailUtil.generateOTP();
                MailUtil.sendOTP(email, otpCode);

                // Lưu OTP và thời gian vào session
                session.setAttribute("otpCode", otpCode);
                session.setAttribute("otpGenerationTime", LocalDateTime.now());

                // Quay lại trang nhập mã OTP
                request.setAttribute("email", email);
                request.getRequestDispatcher("forgetpass/ForgetPassword3.jsp").forward(request, response);
            } else {
                // Xử lý nếu email không tồn tại (trường hợp này ít khi xảy ra nếu đã gửi OTP trước đó)
                request.setAttribute("errorMessage", "Email không tồn tại trong hệ thống.");
                request.getRequestDispatcher("forgetpass/ForgetPassword.jsp").forward(request, response);
            }
        }
// Step 4: Reset password
if ("4".equals(step)) {
    String email = (String) session.getAttribute("email"); // Lấy email từ session
    String password = request.getParameter("password");
    String passwordConfirm = request.getParameter("passwordConfirm");

    if (password.equals(passwordConfirm)) {
        String hashedPassword = hashPassword(password);
        boolean isUpdated = dao.updatePassword(email, hashedPassword);
        
        if (isUpdated) {
            response.sendRedirect("login.jsp");
        } else {
            request.setAttribute("errorMessage", "Failed to update password. Please try again.");
            request.setAttribute("email", email);
            request.getRequestDispatcher("forgetpass/ForgetPassword4.jsp").forward(request, response);
        }
    } else {
        request.setAttribute("errorMessage", "Passwords do not match. Please try again.");
        request.setAttribute("email", email);
        request.getRequestDispatcher("forgetpass/ForgetPassword4.jsp").forward(request, response);
    }
}
    }

    private String hashPassword(String password) {
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256"); // Sử dụng SHA-256
        md.update(password.getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    } catch (NoSuchAlgorithmException ex) {
        return null;
    }
}
}
