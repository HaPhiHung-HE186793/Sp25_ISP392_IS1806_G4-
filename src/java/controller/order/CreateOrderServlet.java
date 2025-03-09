/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.order;

import DAO.DAOOrderItems;
import DAO.DAOOrders;
import DAO.DAOUser;
import model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.OrderItems;
import model.OrderTask;
import service.OrderQueue;
import service.OrderWorker;

/**
 *
 * @author Admin
 */
public class CreateOrderServlet extends HttpServlet {

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
            out.println("<title>Servlet CreateOrderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateOrderServlet at " + request.getContextPath() + "</h1>");
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

        request.getRequestDispatcher("order/createOrder.jsp").forward(request, response);
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

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String orderType = request.getParameter("orderType"); // Nhập hay Xuất kho
        String userName = (String) session.getAttribute("username");
        int customerId = Integer.parseInt(request.getParameter("customerId")); // ID khách hàng

        int userId = (int) session.getAttribute("userID");
        int porter = Integer.parseInt(request.getParameter("porter"));

        Double totalDiscount = Double.parseDouble(request.getParameter("totalDiscount"));
        String status;

        if (totalDiscount >= 200000) {

            status = "Tổng tiền đã giảm : " + totalDiscount + "<br>" + request.getParameter("status");
        } else {
            status = request.getParameter("status");
        }

        Double totalOrderPrice = Double.parseDouble(request.getParameter("totalOrderPriceHidden")); // Tổng tiền

        Double paidAmount = 0.0;
        Double debtAmount = 0.0;

        String orderStatus = request.getParameter("orderStatus");

        if (orderStatus.equals("paid")) {

            paidAmount = totalOrderPrice;

        } else if (orderStatus.equals("partial")) {

            paidAmount = Double.parseDouble(request.getParameter("paidAmount"));
            debtAmount = totalOrderPrice - paidAmount;

        } else if (orderStatus.equals("unpaid")) {
            paidAmount = 0.0;
            debtAmount = totalOrderPrice;

        }

        try {
            // Lấy danh sách sản phẩm từ request
            String[] productIds = request.getParameterValues("productID");
            String[] productNames = request.getParameterValues("productName");
            String[] totalPrices = request.getParameterValues("totalPriceHidden");
            String[] unitPrices = request.getParameterValues("unitPriceHidden");
            String[] quantities = request.getParameterValues("totalWeight");
            String[] discounts = request.getParameterValues("discount");

            List<OrderItems> orderDetails = new ArrayList<>();
            if (productIds != null) {
                for (int i = 0; i < productIds.length; i++) {
                    int productID = Integer.parseInt(productIds[i]);
                    String productName = productNames[i];

                    Double price = Double.parseDouble(totalPrices[i]);
                    Double unitPrice = Double.parseDouble(unitPrices[i]);
                    Double discount = Double.parseDouble(discounts[i]);

                    int quantity = Integer.parseInt(quantities[i]);
                    if (quantity <= 0 || unitPrice <= 0 || price <= 0 || discount < 0) {

                        return;
                    }

                   

                    OrderItems orderItem = new OrderItems(productID, productName, price, unitPrice, quantity,discount);
                   

                    orderDetails.add(orderItem);

                }
            }

            // Khởi động Worker nếu chưa chạy
            OrderWorker.startWorker();

            // Đưa đơn hàng vào hàng đợi để xử lý
            OrderTask orderTask = new OrderTask(orderType, customerId, userId, totalOrderPrice, porter, status, paidAmount, debtAmount, orderDetails);
            OrderQueue.addOrder(orderTask);

            // Trả về JSON response cho AJAX
            response.getWriter().write("{\"status\": \"processing\"}");
        } catch (Exception ex) {
            Logger.getLogger(CreateOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Lỗi khi tạo đơn hàng, vui lòng thử lại.\"}");

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
