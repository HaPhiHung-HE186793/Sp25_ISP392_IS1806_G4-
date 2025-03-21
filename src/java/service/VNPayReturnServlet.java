package service;


import utils.VNPayUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name="VNPayReturnServlet", urlPatterns={"/vnpay_return"})
public class VNPayReturnServlet extends HttpServlet {
    private static final String VNP_HASH_SECRET = "JDGD38HENGYYDAI2XH808SA08EIJXSCV";  // Thay bằng Secret Key thực tế

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet VnPayReturnServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VnPayReturnServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> params = new HashMap<>();
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            params.put(entry.getKey(), entry.getValue()[0]);
        }

        String vnp_SecureHash = params.remove("vnp_SecureHash"); // Lấy mã checksum từ request

        // Kiểm tra checksum để đảm bảo dữ liệu không bị thay đổi
        String generatedHash = VNPayUtils.hmacSHA512(VNP_HASH_SECRET, VNPayUtils.createQueryString(params));

        if (generatedHash.equals(vnp_SecureHash)) {
            String vnp_ResponseCode = params.get("vnp_ResponseCode");
            if ("00".equals(vnp_ResponseCode)) {
                // Thanh toán thành công
                
                
                //cập nhật vào appointment status thành paid
//                int appointmentId = Integer.parseInt(params.get("vnp_TxnRef"));
//                appointmentDAO.updateStatusForPayInvoice(appointmentId);
                // Lấy amount và chia cho 100
                long amount = Long.parseLong(params.get("vnp_Amount")) / 100;
                request.setAttribute("message", "Giao dịch thành công!");
                request.setAttribute("orderId", params.get("vnp_TxnRef"));
                request.setAttribute("amount", amount);
                request.setAttribute("bankCode", params.get("vnp_BankCode"));
            } else {
                // Giao dịch thất bại
                request.setAttribute("message", "Giao dịch thất bại! Mã lỗi: " + vnp_ResponseCode);
            }
        } else {
            // Sai checksum, có thể dữ liệu đã bị thay đổi
            request.setAttribute("message", "Dữ liệu không hợp lệ!");
        }

        request.getRequestDispatcher("order/return.jsp").forward(request, response);
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