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
import java.util.logging.Level;
import java.util.logging.Logger;

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

        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("username");
         //Lấy thông tin đơn hàng
        int customerId = Integer.parseInt(request.getParameter("customerId")); // ID khách hàng

        DAOUser daoUser = new DAOUser();

        User currentUser = daoUser.getCurrentUser(userName);
        int userId = currentUser.getCreateBy();
        int porter = Integer.parseInt(request.getParameter("porter"));

        Double totalDiscount = Double.parseDouble(request.getParameter("totalDiscount"));
        String status;

        if (totalDiscount >= 200000) {

            status = "Tổng tiền đã giảm : " + totalDiscount +"<br>"+ request.getParameter("status");
        } else {
            status = request.getParameter("status");
        }

        BigDecimal totalOrderPrice = new BigDecimal(request.getParameter("totalOrderPrice")); // Tổng tiền
       


        try {

            int orderId = DAOOrders.INSTANCE.createOrder(customerId,userId,userId, totalOrderPrice, porter, status);

            if (orderId != -1) {
                String[] productIds = request.getParameterValues("productID");
                String[] productNames = request.getParameterValues("productName");
                String[] totalPrices = request.getParameterValues("totalPrice");
                String[] unitPrices = request.getParameterValues("unitPrice");
                String[] quantities = request.getParameterValues("quantity");
                 String[] descriptions = request.getParameterValues("description");

                if (productIds != null) {
                    for (int i = 0; i < productIds.length; i++) {
                        int productID = Integer.parseInt(productIds[i]);
                        String productName = productNames[i];
                        BigDecimal price = new BigDecimal(totalPrices[i]);
                        BigDecimal unitPrice = new BigDecimal(unitPrices[i]);
                        int quantity = Integer.parseInt(quantities[i]);

                        if (quantity <= 0) {
                            request.setAttribute("ms", "Số lượng sản phẩm không hợp lệ!");
                            request.getRequestDispatcher("order/createOrder.jsp").forward(request, response);
                            return;  // Dừng ngay sau khi forward
                        }
                        
                        String description = descriptions[i];

                        boolean success = DAOOrderItems.INSTANCE.createOrderItem(orderId, productID, productName, price, unitPrice, quantity,description);

                        if (!success) {
                            request.setAttribute("ms", "Không đủ số lượng sản phẩm trong kho!");
                            request.getRequestDispatcher("order/createOrder.jsp").forward(request, response);
                            return;  // Dừng ngay sau khi forward
                        }
                    }
                }
                request.setAttribute("ms", "Tạo đơn hàng thành công");
            } else {
                request.setAttribute("ms", "Tạo đơn hàng thất bại");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreateOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("ms", "Lỗi khi tạo đơn hàng, vui lòng thử lại.");
            request.getRequestDispatcher("order/createOrder.jsp").forward(request, response);
            return;  // Dừng ngay sau khi forward
        }

// Nếu đã forward trước đó, thì sẽ không đến được đây
        request.getRequestDispatcher("order/createOrder.jsp").forward(request, response);

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
