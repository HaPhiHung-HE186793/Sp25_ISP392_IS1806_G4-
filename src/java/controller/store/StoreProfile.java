/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.store;

import DAO.DAOSchedule;
import DAO.DAOStore;
import DAO.DAOUser;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Schedule;
import model.Store;
import model.User;
import model.pagination.Pagination;

/**
 *
 * @author nguyenanh
 */
public class StoreProfile extends HttpServlet {
   DAOStore dao = new DAO.DAOStore();
   DAOSchedule daos = new DAOSchedule();
    Pagination Page;
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
            out.println("<title>Servlet StoreProfile</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StoreProfile at " + request.getContextPath () + "</h1>");
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
        HttpSession session = request.getSession();
        User user_current = (User) session.getAttribute("user");
        Integer roleID = (Integer) session.getAttribute("roleID");
        if ( roleID == 1 ) {
            response.sendRedirect("listusers"); 
            return; 
        }
        
        int storeid = (Integer) session.getAttribute("storeID");
        Store store = dao.getStoreById(storeid);
        
        if (request.getParameter("id") != null && !(request.getParameter("id") == "")) {
                request.setAttribute("Store", store);
                    session.setAttribute("storeIdUpdate", store.getStoreID());
                    request.getRequestDispatcher("updatestore").forward(request, response);
        }  

        request.setAttribute("user_current", user_current);
        request.setAttribute("Store", store);
        request.getRequestDispatcher("/store/storeProfile.jsp").forward(request, response);
     
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
