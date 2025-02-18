/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.products;

import DAO.DAOProduct;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.Products;

/**
 *
 * @author ACER
 */
public class CreateProduct extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(Integer.MAX_VALUE);
        String productName = request.getParameter("productName");
        String description = request.getParameter("description");
        double price = 0.0;
        try {
            price = Double.parseDouble(request.getParameter("price"));
        } catch (NumberFormatException e) {
            // ... (error handling)
        }
        int quantity = 0;
<<<<<<< HEAD
        try {
            quantity = Integer.parseInt(request.getParameter("quantity"));
        } catch (NumberFormatException e) {
            // ... (error handling)
        }
=======
>>>>>>> 78b7f86cdf0107bbd1f3700e282817d766c50fbc
        
        String imageName = request.getParameter("image"); // To store ONLY the image file name

        int createBy = 0;
        try {
            createBy = Integer.parseInt(request.getParameter("createBy"));
        } catch (NumberFormatException e) {
            // ... (error handling)
        }
        
        boolean isDelete = false; // nguoc lai neu o ben controller delete
        
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createAt = now.format(formatter);
        String updateAt = createAt; 
        String deleteAt = null;
        Integer deleteBy = 0;
        
        Products product = new Products(productName, description, price, quantity, imageName, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy); // Use imageName here
        DAOProduct dao = new DAOProduct();
        
        int result = dao.insertProduct(product);
        
        if (result > 0) {
            request.setAttribute("message", "success");
        } else {
            request.setAttribute("message", "error: Database insertion failed");
        }
        
        request.getRequestDispatcher("ListProducts").forward(request, response);
    }
    public static void main(String[] args) {
        // Create sample product data
        String productName = "Test Product";
        String description = "Test Description";
        double price = 99.99;
        int quantity = 10;
        String imageName = "test.jpg";
        int createBy = 1; // Replace with a valid user ID
        boolean isDelete = false;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createAt = now.format(formatter);
        String updateAt = createAt;
        String deleteAt = null;
        Integer deleteBy = 0;

        Products product = new Products(productName, description, price, quantity, imageName, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);

         // Create an instance of your servlet
        DAOProduct pro = new DAOProduct();
        int result = pro.insertProduct(product); // Call the helper method

        if (result > 0) {
            System.out.println("Test Passed: Product inserted successfully.");
        } else {
            System.out.println("Test Failed: Product insertion failed.");
        }
    }
}
