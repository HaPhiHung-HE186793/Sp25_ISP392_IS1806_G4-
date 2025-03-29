/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.order;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.OrderWorker;

/**
 *
 * @author Admin
 */
public class CheckOrderStatusServlet extends HttpServlet {

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
            out.println("<title>Servlet CheckOrderStatusServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckOrderStatusServlet at " + request.getContextPath() + "</h1>");
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
         response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Integer role = (Integer) session.getAttribute("roleID");
        if (role == 1) {
            response.sendRedirect("listusers"); // sửa thành đường dẫn của trang chủ sau khi hoàn thành code
            return;
        }
        int userId = Integer.parseInt(request.getParameter("userId"));
        boolean clearStatus = request.getParameter("clear") != null;
        if (clearStatus) {
            // Xóa trạng thái đơn hàng nếu có yêu cầu "clear"
            OrderWorker.clearProcessedOrder(userId);
            response.getWriter().write("{\"status\": \"cleared\"}");
            return;
        }

        // Kiểm tra xem user có đơn hàng nào đã xử lý xong không
        Integer orderId = OrderWorker.getProcessedOrder(userId);
        

       

        if (orderId == null) {
            response.getWriter().write("{\"status\": \"pending\"}");
            
        } else if (orderId == -1) {
            response.getWriter().write("{\"status\": \"error\"}");
            // Xóa trạng thái lỗi để lần sau kiểm tra không bị kẹt ở lỗi cũ
            OrderWorker.clearProcessedOrder(userId);
        } else {
            response.getWriter().write("{\"status\":\"done\"}");
            // Sau khi trả về thành công, xóa trạng thái để lần sau không nhầm lẫn với đơn mới
            OrderWorker.clearProcessedOrder(userId);
        }
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
        processRequest(request, response);
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
