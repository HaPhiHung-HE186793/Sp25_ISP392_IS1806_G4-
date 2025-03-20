/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.order;

import DAO.DAOOrderItems;
import DAO.DAOOrders;
import DAO.DAOProduct;
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
        // Kiểm tra nếu phản hồi chưa được commit, tiến hành forward
        if (!response.isCommitted()) {
            request.getRequestDispatcher("order/createOrder.jsp").forward(request, response);
        } else {
            // Nếu đã commit, có thể xử lý một thông báo lỗi hoặc ghi log
            System.out.println("Phản hồi đã được commit, không thể forward.");
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

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String orderType = request.getParameter("orderType"); // Nhập hay Xuất kho
        String userName = (String) session.getAttribute("username");
        int customerId = Integer.parseInt(request.getParameter("customerId")); // ID khách hàng

        int userId = (int) session.getAttribute("userID");

        String status;
        if (orderType.equals("1")) {// xuất
            Double totalDiscount = Double.parseDouble(request.getParameter("totalDiscount"));
            if (totalDiscount >= 200000) {

                status = "Tổng tiền đã giảm : " + totalDiscount + "<br>" + request.getParameter("status");
            } else {
                status = request.getParameter("status");
            }

        } else {
            status = request.getParameter("status");

        }

        Double totalOrderPrice = Double.parseDouble(request.getParameter("totalOrderPriceHidden")); // Tổng tiền
        Double calculatedTotalAmount = 0.0;

        Double paidAmount = Double.parseDouble(request.getParameter("paidAmount"));
        Double debtAmount = Double.parseDouble(request.getParameter("balanceAmount"));

        String balanceAction = request.getParameter("balanceAction");
        Double epsilon = 1e-9; // Ngưỡng sai số rất nhỏ

        try {
            // Lấy danh sách sản phẩm từ request
            String[] productIds = request.getParameterValues("productID");
            String[] productNames = request.getParameterValues("productName");
            String[] totalPrices = request.getParameterValues("totalPriceHidden");
            String[] unitPrices = request.getParameterValues("unitPriceHidden");
            String[] quantities = request.getParameterValues("totalWeight");
            String[] discounts = null;
            if (orderType.equals("1")) {
                discounts = request.getParameterValues("discount");

            }

            List<OrderItems> orderDetails = new ArrayList<>();
            if (productIds != null) {
                for (int i = 0; i < productIds.length; i++) {
                    int productID = Integer.parseInt(productIds[i]);
                    String productName = productNames[i];

                    Double price = Double.parseDouble(totalPrices[i]);
                    Double unitPrice = Double.parseDouble(unitPrices[i]);
                    Double discount = 0.0;
                    if (orderType.equals("1")) { // xuất
                        discount = Double.parseDouble(discounts[i]);

                    }

                    int quantity = Integer.parseInt(quantities[i]);
                    Double actualUnitPrice = 0.0;
                    Double expectedPrice = 0.0;

                    if (orderType.equals("1")) {

                        int availablequantity = DAOProduct.INSTANCE.getProductQuantity(productID);
                        actualUnitPrice = DAOProduct.INSTANCE.getProductPrice(productID);

                        expectedPrice = (actualUnitPrice - discount) * quantity;

                        if (quantity > availablequantity || Math.abs(expectedPrice - price) > epsilon || Math.abs(actualUnitPrice - unitPrice) > epsilon) {
                            response.getWriter().write("{\"status\": \"error\", \"message\": \"Lỗi khi tạo đơn hàng, vui lòng thử lại.\"}");

                            return;
                        }

                    } else {// nhập
                        actualUnitPrice = unitPrice;
                        expectedPrice = actualUnitPrice * quantity;

                    }
                    calculatedTotalAmount = calculatedTotalAmount + expectedPrice;

                    if (quantity <= 0 || unitPrice <= 0 || price <= 0 || discount < 0) {
                        response.getWriter().write("{\"status\": \"error\", \"message\": \"Lỗi khi tạo đơn hàng, vui lòng thử lại.\"}");
                        return;
                    }
                      OrderItems orderItem;

                         orderItem = new OrderItems(productID, productName, expectedPrice, actualUnitPrice, quantity, discount);
                   

                    orderDetails.add(orderItem);

                }

                if (Math.abs(calculatedTotalAmount - totalOrderPrice) > epsilon) {

                    response.getWriter().write("{\"status\": \"error\", \"message\": \"Lỗi khi tạo đơn hàng, vui lòng thử lại.\"}");
                    return;
                }
            }

            if (debtAmount < 0) {
                debtAmount = (paidAmount - calculatedTotalAmount);

            } else if (debtAmount > 0 && balanceAction.equals("debt")) {
                debtAmount = (paidAmount - calculatedTotalAmount);

            } else {
                debtAmount = 0.0;
            }

            // Khởi động Worker nếu chưa chạy
            OrderWorker.startWorker();

            // Đưa đơn hàng vào hàng đợi để xử lý
            OrderTask orderTask = new OrderTask(orderType, customerId, userId, calculatedTotalAmount, status, paidAmount, debtAmount, orderDetails);

// Xóa trạng thái cũ để đảm bảo lấy đúng kết quả mới
            OrderWorker.clearProcessedOrder(userId);
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
