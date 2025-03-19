/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.zone;

import DAO.DAOZones;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.Zones;

/**
 *
 * @author TIEN DAT PC
 */
public class AddZone extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    

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
         HttpSession session = request.getSession();

        /* TODO output your page here. You may use following sample code. */
        String name = request.getParameter("name");
        Integer createBy = (Integer) session.getAttribute("userID");
        Integer storeID = (Integer) session.getAttribute("storeID");

       
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createAt = now.format(formatter);  // Thời gian hiện tại
        String updateAt = createAt;  // Ban đầu updateAt cũng là thời gian hiện tại

        boolean isDelete = false;
        String deleteAt = null;
        Integer deleteBy = null;
        DAOZones dao = new DAOZones();
        Zones zone = new Zones();
        zone.setZoneName(name);
        zone.setCreateAt(createAt);
        zone.setUpdateAt(updateAt);
        zone.setCreateBy(createBy);
        zone.setIsDelete(isDelete);
        zone.setStoreID(storeID);
        int result = dao.insertZone(zone);
        if (result > 0) {
            request.setAttribute("message", "success");
        } else {
            request.setAttribute("message", "error");
        }
        new ListZone().doGet(request, response);
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
