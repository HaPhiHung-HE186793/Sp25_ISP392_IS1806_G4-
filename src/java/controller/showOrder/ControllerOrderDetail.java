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
import model.ShowOrder;
import DAO.DAOOrders;
import DAO.DAOShowOrder;
import DAO.DAOOrderItems;
import DAO.DAOCustomerOrder;
import jakarta.servlet.RequestDispatcher;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.Vector;
import model.CustomerOrder;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="ControllerOrderDetail", urlPatterns={"/URLOrderDetail"})
public class ControllerOrderDetail extends HttpServlet {
   
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
    DAOOrderItems dao = new DAOOrderItems();
    DAOCustomerOrder dao2 = new DAOCustomerOrder();
    String service = request.getParameter("service");
    if (service == null) {
        service = "listOrderItem";
    }
//     String sql = "SELECT o.orderID, c.name, c.email, c.phone " +
//                     "FROM orders o " +
//                     "JOIN customers c ON o.customerID = c.customerID ";                    
    try (PrintWriter out = response.getWriter()) {
        if (service.equals("listOrderItem")) {
            List<OrderItems> list = new ArrayList<>();
            List<CustomerOrder> list2 = new ArrayList<>();// Khởi tạo danh sách
      //      List<CustomerOrder> list2 = dao2.getCustomerOrder(sql);
            //
            
            int orderId = Integer.parseInt(request.getParameter("orderId"));
             String sql = "SELECT o.orderID, c.name, c.email, c.phone " +
                     "FROM orders o " +
                     "JOIN customers c ON o.customerID = c.customerID " +
                     "WHERE o.orderID = " + orderId; 
            String productName = request.getParameter("productName");

            if (productName != null && !productName.isEmpty()) {
                list = dao.getOrderItems("SELECT * FROM OrderItems WHERE orderId = " + orderId + " AND productName LIKE '%" + productName + "%'");
                list2 = dao2.getCustomerOrder(sql);
            } else {
                list = dao.getOrderItems("SELECT * FROM OrderItems WHERE orderId = " + orderId);
                list2 = dao2.getCustomerOrder(sql);
            }

            request.setAttribute("data", list);
            request.setAttribute("data2", list2);
            request.setAttribute("tableTitle", "Danh sách sản phẩm trong hóa đơn");
            request.setAttribute("papeTitle", "Orders manage");
            RequestDispatcher dispth = request.getRequestDispatcher("order/orderItemList.jsp");
            dispth.forward(request, response);
        }
    }
}

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//       // processRequest(request, response);
//        DAOOrderItems dao = new DAOOrderItems();
//        String service = request.getParameter("service");
//        if (service == null) { 
//            service = "listOrderItem";
//        }
//        try (PrintWriter out = response.getWriter()) {
//            if (service.equals("listOrderItem")) { //get request
//                //check search or not
//        //     String service = request.getParameter("service");               
//             List<OrderItems> list = new ArrayList<>(); // Khởi tạo danh sách
//
//            if (service.equals("listOrderItem")) { // list all
//    // call DAO (model)
//              int orderId = Integer.parseInt(request.getParameter("orderId"));
//           list = dao.getOrderItems("select * from OrderItems where orderId =" + orderId);
//            } else { // search
//            String orderId = request.getParameter("orderID");
//          list = dao.getOrderItems("select * from OrderItems where orderId =" + orderId);
//          }
//                request.setAttribute("data",list);
//                request.setAttribute("tableTitle", "Danh sách sản phẩm trong hóa đơn");
//                 request.setAttribute("papeTitle", "Orders manage");
//               //call jsp (view)
//                RequestDispatcher dispth
//                        =request.getRequestDispatcher("order/orderItemList.jsp");
//                //run jsp
//               dispth.forward(request, response);
//              // response.sendRedirect("URLOrder");
//            }
//           
//        }
//    } 

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

}
