/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.products;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAO.DAOProduct;
import java.util.List;
import model.Products;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.PrintWriter;
import model.User;
import model.pagination.Pagination;

/**
 *
 * @author ACER
 */
public class ListProducts extends HttpServlet {

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
        DAOProduct dBConnect = new DAOProduct();
        HttpSession session = request.getSession();
        Integer roleID = (Integer) session.getAttribute("roleID");
        if(roleID == 1 ){
            request.getRequestDispatcher("listusers").forward(request, response);
        }

        // Lấy storeID từ request
        Integer storeID = (Integer) session.getAttribute("storeID");

        List<Products> products = dBConnect.listAll1(storeID); // Truyền storeID vào

        // Pagination
        int totalProducts = products.size();
        int pageSize = 20;
        int currentPage = 1;

        if (request.getParameter("cp") != null) {
            currentPage = Integer.parseInt(request.getParameter("cp"));
        }
        request.setAttribute("currentPageUrl", "ListProducts?storeID=" + storeID); //check lai

        Pagination page = new Pagination(totalProducts, pageSize, currentPage);
        session.setAttribute("page", page);

        // Lấy danh sách sản phẩm theo trang
        int startIndex = page.getStartItem();
        int endIndex = Math.min(startIndex + pageSize, totalProducts);
        List<Products> paginatedProducts = products.subList(startIndex, endIndex);

        request.setAttribute("products", paginatedProducts);
        request.setAttribute("storeID", storeID); // Truyền storeID vào request
        RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboard/home.jsp");
        dispatcher.forward(request, response);
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
        processRequest(request, response);
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
