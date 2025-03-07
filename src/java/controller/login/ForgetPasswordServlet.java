package controller.login;

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
import model.User;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebServlet(name = "ForgetPasswordServlet", urlPatterns = {"/forgotpass"})
public class ForgetPasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        
        String step = request.getParameter("step");
        DAOForget dao = new DAOForget();
        HttpSession session = request.getSession();

        if ("1".equals(step)) {
            String email = request.getParameter("email");
            User acc = dao.checkEmail(email);
            if (acc != null) {
                session.setAttribute("email", email);
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

            // Gửi OTP trong một luồng riêng
            executor.submit(new OTPTask(email, otpCode));

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

            Integer failedAttempts = (Integer) session.getAttribute("failedAttempts");
            if (failedAttempts == null) {
                failedAttempts = 0;
            }

            if (enteredOtp.equals(sessionOtp) && LocalDateTime.now().isBefore(otpGenerationTime.plusMinutes(1))) {
                session.setAttribute("failedAttempts", 0);
                request.setAttribute("email", email);
                request.getRequestDispatcher("forgetpass/ForgetPassword4.jsp").forward(request, response);
            } else {
                failedAttempts++;
                session.setAttribute("failedAttempts", failedAttempts);

                if (LocalDateTime.now().isAfter(otpGenerationTime.plusMinutes(1))) {
                    session.removeAttribute("otpCode");
                    session.removeAttribute("otpGenerationTime");
                    request.setAttribute("errorMessage", "OTP đã hết hạn. Vui lòng yêu cầu OTP mới.");
                } else if (failedAttempts >= 3) {
                    session.removeAttribute("otpCode");
                    session.removeAttribute("otpGenerationTime");
                    request.setAttribute("errorMessage", "Bạn đã nhập sai OTP quá 3 lần. Vui lòng yêu cầu OTP mới.");
                } else {
                    request.setAttribute("errorMessage", "OTP không chính xác. Hãy thử lại.");
                }
                
                request.setAttribute("email", email);
                request.getRequestDispatcher("forgetpass/ForgetPassword3.jsp").forward(request, response);
            }
        }

        if ("resend".equals(step)) {
            String email = (String) request.getParameter("email");
            User acc = dao.checkEmail(email);
            if (acc != null) {
                String otpCode = MailUtil.generateOTP();
                executor.submit(new OTPTask(email, otpCode));

                session.setAttribute("otpCode", otpCode);
                session.setAttribute("otpGenerationTime", LocalDateTime.now());

                request.setAttribute("email", email);
                request.getRequestDispatcher("forgetpass/ForgetPassword3.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Email không tồn tại trong hệ thống.");
                request.getRequestDispatcher("forgetpass/ForgetPassword.jsp").forward(request, response);
            }
        }

        // Step 4: Reset password
        if ("4".equals(step)) {
            String email = (String) session.getAttribute("email");
            String password = request.getParameter("password");
            String passwordConfirm = request.getParameter("passwordConfirm");

            if (password.equals(passwordConfirm)) {
                String hashedPassword = hashPassword(password);
                boolean isUpdated = dao.updatePassword(email, hashedPassword);
                
                if (isUpdated) {
                    session.removeAttribute("email");
                    response.sendRedirect("login/login.jsp");
                } else {
                    request.setAttribute("errorMessage", "Cập nhật mật khẩu thất bại. Hãy thử lại.");
                    request.setAttribute("email", email);
                    request.getRequestDispatcher("forgetpass/ForgetPassword4.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Mật khẩu không giống nhau. Hãy thử lại.");
                request.setAttribute("email", email);
                request.getRequestDispatcher("forgetpass/ForgetPassword4.jsp").forward(request, response);
            }
        }

        // Dừng ExecutorService
        executor.shutdown();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
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