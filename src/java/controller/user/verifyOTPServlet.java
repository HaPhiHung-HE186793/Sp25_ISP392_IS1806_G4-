/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
/**
 *
 * @author nguyenanh
 */
@WebServlet(name = "verifyOTPServlet", urlPatterns = {"/verifyotp"})
public class verifyOTPServlet extends HttpServlet {

    private static final long OTP_EXPIRATION_TIME = 3 * 60 * 1000; // 3 phút (ms)

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userOtp = request.getParameter("otp");
        String sessionOtp = (String) session.getAttribute("otp");
        Long otpCreationTime = (Long) session.getAttribute("otpTime");

        long currentTime = System.currentTimeMillis();

        if (otpCreationTime == null || sessionOtp == null) {
            response.getWriter().write("OTP expired");
            return;
        }

        if ((currentTime - otpCreationTime) > OTP_EXPIRATION_TIME) {
            session.removeAttribute("otp"); // Xóa OTP đã hết hạn
            session.removeAttribute("otpTime");
            response.getWriter().write("OTP expired");
        } else if (userOtp.equals(sessionOtp)) {
            session.removeAttribute("otp"); // Xóa sau khi xác thực thành công
            session.removeAttribute("otpTime");
            response.getWriter().write("OTP verified");
        } else {
            response.getWriter().write("Incorrect OTP");
        }
    }
}

