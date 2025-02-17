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
import jakarta.servlet.RequestDispatcher;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.Vector;
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
        DAOOrderItems dao = new DAOOrderItems();
        String service = request.getParameter("service");
        if (service == null) { 
            service = "listOrderItem";
        }
        try (PrintWriter out = response.getWriter()) {
            if (service.equals("listOrderItem")) { //get request
                //check search or not
        //     String service = request.getParameter("service");               
             List<OrderItems> list = new ArrayList<>(); // Khởi tạo danh sách

            if (service.equals("listOrderItem")) { // list all
    // call DAO (model)
              int orderId = Integer.parseInt(request.getParameter("orderId"));
           list = dao.getOrderItems("select * from OrderItems where orderId =" + orderId);
            } else { // search
            String orderId = request.getParameter("orderID");
          list = dao.getOrderItems("select * from OrderItems where orderId =" + orderId);
          }
                request.setAttribute("data",list);
                request.setAttribute("tableTitle", "List of Orders");
                 request.setAttribute("papeTitle", "Orders manage");
               //call jsp (view)
                RequestDispatcher dispth
                        =request.getRequestDispatcher("order/orderItemList.jsp");
                //run jsp
               dispth.forward(request, response);
              // response.sendRedirect("URLOrder");
            }
           
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
        processRequest(request, response);
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

}
