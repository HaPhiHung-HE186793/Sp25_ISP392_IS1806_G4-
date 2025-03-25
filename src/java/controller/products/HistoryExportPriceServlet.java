/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.products;

import DAO.DAOProduct;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.ProductPriceHistory;

/**
 *
 * @author Admin
 */
public class HistoryExportPriceServlet extends HttpServlet {

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
            out.println("<title>Servlet HistoryExportPriceServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HistoryExportPriceServlet at " + request.getContextPath() + "</h1>");
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
        int userId = (int) session.getAttribute("userID");
        int currentPage = 1; // Mặc định trang đầu tiên
        int recordsPerPage = 5; // Số bản ghi mỗi trang

        // Lấy số trang từ request
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                currentPage = 1; // Nếu lỗi, quay về trang 1
            }
        }

        // Lấy từ khóa tìm kiếm từ request
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = ""; // Nếu không có từ khóa, tìm tất cả
        }
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder == null || (!sortOrder.equals("asc") && !sortOrder.equals("desc"))) {
            sortOrder = "desc"; // Mặc định hiển thị mới nhất → cũ nhất
        }
        String startDate = request.getParameter("startDate"); // Ngày bắt đầu
        String endDate = request.getParameter("endDate");     // Ngày kết thúc
        if (startDate == null || startDate.isEmpty()) {
            startDate = "2025-01-01";  // Ngày bắt đầu mặc định từ rất lâu
        }
        if (endDate == null || endDate.isEmpty()) {
            endDate = LocalDate.now().toString();  // Ngày kết thúc mặc định là ngày hiện tại
        }

        List<ProductPriceHistory> HistoryList = new ArrayList<>();
        HistoryList = DAOProduct.INSTANCE.getExportPriceHistory(keyword, currentPage, recordsPerPage, userId, sortOrder, startDate, endDate);

        // Lấy tổng số bản ghi để tính tổng số trang
        int totalRecords = DAOProduct.INSTANCE.getTotalHistoryExportRecords(keyword, userId, startDate, endDate); // Truyền keyword + userId vào
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

        request.setAttribute("HistoryList", HistoryList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("keyword", keyword);
        request.setAttribute("sortOrder", sortOrder); // ✅ Thêm sortOrder để giữ trạng thái sắp xếp
        request.setAttribute("endDate", endDate);

        // Kiểm tra giá trị của danh sách trước khi chuyển tiếp
        System.out.println("Size of HistoryList: " + HistoryList.size());
        request.getRequestDispatcher("product/historyExportPrice.jsp").forward(request, response);
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
