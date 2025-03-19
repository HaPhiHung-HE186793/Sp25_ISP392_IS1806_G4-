package controller.showOrder;

import controller.login.MailUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="RemindMail", urlPatterns={"/URLRemindMail"})
public class RemindMail extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service");
        
        if ("show".equals(service)) {
            // Hiển thị trang remindMail.jsp
            String email = request.getParameter("email");
            String orderId = request.getParameter("orderId");
            request.setAttribute("email", email);
            request.setAttribute("orderId", orderId);
            RequestDispatcher dispth = request.getRequestDispatcher("order/remindMail.jsp");
            dispth.forward(request, response);
        } else {
            // Có thể xử lý service khác nếu cần
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid service");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String service = request.getParameter("service");
        
        if ("send".equals(service)) {
            String email = request.getParameter("email");
            String message = request.getParameter("message");
            String orderId = request.getParameter("orderId");

            // Gửi mail
            MailUtilMes.sendOTP(email, message); // Cập nhật phương thức nếu cần

            // Chuyển hướng về trang nhắc nhở với thông báo thành công
            response.sendRedirect("URLRemindOrder?success=true");
        } else {
            // Có thể xử lý service khác nếu cần
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid service");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}