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

        List<Products> products = dBConnect.listAll();     
        
        // Cập nhật pagination dựa trên số lượng kết quả tìm kiếm
        int totalUsers = products.size();
        int pageSize = 4;
        int currentPage = 1;

        if (request.getParameter("cp") != null) {
            currentPage = Integer.parseInt(request.getParameter("cp"));
        }
        request.setAttribute("currentPageUrl", "ListProducts"); // Hoặc "products"

        Pagination page = new Pagination(totalUsers, pageSize, currentPage);
        session.setAttribute("page", page);

        // Lấy danh sách user theo trang hiện tại
        int startIndex = page.getStartItem();
        int endIndex = Math.min(startIndex + pageSize, totalUsers);
        List<Products> paginatedUsers = products.subList(startIndex, endIndex);
        
        request.setAttribute("products", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboard/home.jsp");
        dispatcher.forward(request, response);
//        PrintWriter out = response.getWriter();
//        for(Products pro: products){
//        out.println("Product ID: " + pro.getProductID());
//        out.println("Product Name: " + pro.getProductName());
//        out.println("Description: " + pro.getDescription());
//        out.println("Price: " + pro.getPrice());
//        out.println("Quantity: " + pro.getQuantity());
//        out.println("Image: " + pro.getImage());
//        out.println("Create At: " + pro.getCreateAt());
//        out.println("Update At: " + pro.getUpdateAt());
//        out.println("Create By: " + pro.getCreateBy());
//        out.println("Is Delete: " + pro.isIsDelete());
//        out.println("Delete At: " + pro.getDeleteAt());
//        out.println("Delete By: " + pro.getDeleteBy());
//        out.println("--------------------"); // Separator between products
//        }
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
