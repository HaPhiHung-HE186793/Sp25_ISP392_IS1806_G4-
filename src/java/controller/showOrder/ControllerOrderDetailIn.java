/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.showOrder;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrderItems;
import model.CustomerOrder;
import DAO.DAOOrderItems;
import DAO.DAOCustomerOrder;
import jakarta.servlet.RequestDispatcher;
import java.util.List;
import java.util.Vector;
/**
 *
 * @author ADMIN
 */
@WebServlet(name="ControllerOrderDetailIn", urlPatterns={"/URLOrderDetailIn"})
public class ControllerOrderDetailIn extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet ControllerOrderDetailIn</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControllerOrderDetailIn at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAOOrderItems dao = DAOOrderItems.INSTANCE;
        DAOCustomerOrder dao2 = new DAOCustomerOrder();

        String service = request.getParameter("service");
        if (service == null) {
            service = "listOrderItem";
        }

        int pageSize = 5; // Số hàng mỗi trang
        int pageNumber = 1; // Số trang hiện tại
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            pageNumber = Integer.parseInt(pageParam);
        }

        int offset = (pageNumber - 1) * pageSize;
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String productName = request.getParameter("productName");

        String sql = "SELECT o.orderitemID, o.productID, o.productName, o.price, o.unitPrice, o.quantity, od.orderID " +
                     "FROM OrderItems o " +
                     "JOIN orders od ON o.orderID = od.orderID " +
                     "JOIN users u ON od.userID = u.ID " +
                     "WHERE od.orderID = " +orderId;

        Integer storeID = (Integer) request.getSession().getAttribute("storeID");
        if (storeID != null) {
            sql += " AND u.storeID = "+storeID ;
        }
        if (productName != null && !productName.isEmpty()) {
            sql += (storeID != null ? " AND " : " WHERE ") + "o.productName LIKE '%" + productName + "%' ";
        }
        sql += " ORDER BY o.orderitemID OFFSET  "+offset+" ROWS FETCH NEXT  "+pageSize+" ROWS ONLY";

        try (PrintWriter out = response.getWriter()) {
            Vector<OrderItems> list = dao.getOrderItems(sql);//, orderId, storeID, productName, offset, pageSize);
            List<CustomerOrder> list2 = dao2.getCustomerOrder(
                "SELECT o.orderID, c.name, c.email, c.phone FROM orders o JOIN customers c ON o.customerID = c.customerID WHERE o.orderID = " +
                orderId
            );

            // Tính tổng số bản ghi để tính tổng số trang
            int totalPages = getTotalPages(dao, pageSize, orderId, storeID);

            // Cài đặt các thuộc tính cho JSP
            request.setAttribute("data", list);
            request.setAttribute("data2", list2);
            request.setAttribute("tableTitle", "Danh sách sản phẩm trong hóa đơn nhập");
            request.setAttribute("papeTitle", "Orders manage");
            request.setAttribute("currentPage", pageNumber);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("productName", productName); // Để giữ lại giá trị tìm kiếm

            RequestDispatcher dispth = request.getRequestDispatcher("order/orderItemInList.jsp");
            dispth.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error processing request: " + e.getMessage());
            RequestDispatcher dispth = request.getRequestDispatcher("errorPage.jsp");
            dispth.forward(request, response);
        }
    }


    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
        private int getTotalPages(DAOOrderItems dao, int pageSize, int orderId, Integer storeID) {
                String countSql = "SELECT COUNT(*) FROM OrderItems o " +
                                  "JOIN orders od ON o.orderID = od.orderID " +
                                  "JOIN users u ON od.userID = u.ID " +
                                  "WHERE o.orderID = ?";

                if (storeID != null) {
                    countSql += " AND u.storeID = ?";
                }

                int totalRecords = dao.getTotalRecords(countSql, orderId, storeID);
                return (totalRecords == 0) ? 1 : (int) Math.ceil((double) totalRecords / pageSize);
            }
}
