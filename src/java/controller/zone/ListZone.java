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
import java.util.ArrayList;
import java.util.List;
import model.Zones;
import model.pagination.Pagination;

/**
 *
 * @author TIEN DAT PC
 */
public class ListZone extends HttpServlet {
   
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
        
         DAOZones dao = new DAOZones();
        HttpSession session = request.getSession();
        List<Zones> listZone = new ArrayList<>();
        Integer role = (Integer) session.getAttribute("roleID");
        Integer createBy = (Integer) session.getAttribute("createBy");
        if(role == 1 ){
            request.getRequestDispatcher("listusers").forward(request, response);
        }
  
        listZone = dao.listZones(createBy+"");

        // Cập nhật pagination dựa trên số lượng kết quả tìm kiếm
        int totalUsers = listZone.size();
        int pageSize = 10;
        int currentPage = 1;

        if (request.getParameter("cp") != null) {
            currentPage = Integer.parseInt(request.getParameter("cp"));
        }

        Pagination page = new Pagination(totalUsers, pageSize, currentPage);
        session.setAttribute("page", page);
        int startIndex = page.getStartItem();
        int endIndex = Math.min(startIndex + pageSize, totalUsers);
        List<Zones> paginatedUsers = listZone.subList(startIndex, endIndex);
        request.setAttribute("currentPageUrl", "ListZone");


        request.setAttribute("listZone", listZone);
        request.getRequestDispatcher("zone/zone.jsp").forward(request, response);

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
