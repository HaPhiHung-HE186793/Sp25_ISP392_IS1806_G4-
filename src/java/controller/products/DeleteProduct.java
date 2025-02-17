package controller.products;

import DAO.DAOProduct;
import model.Products;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


public class DeleteProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIDStr = request.getParameter("productID"); // Get productID from request

        if (productIDStr != null && !productIDStr.isEmpty()) {
            try {
                int productID = Integer.parseInt(productIDStr); // Parse productID to int

                DAOProduct dao = new DAOProduct();
                int result = dao.removeProduct(productID); // Call removeProduct method

                if (result > 0) {
                    request.setAttribute("message", "success"); // Set success message
                } else {
                    request.setAttribute("message", "error: Product not found or deletion failed"); // Set error message
                }

            } catch (NumberFormatException e) {
                request.setAttribute("message", "error: Invalid product ID format"); // Set error message
                e.printStackTrace(); // Log the error
            }
        } else {
            request.setAttribute("message", "error: Product ID is missing"); // Set error message
        }

        request.getRequestDispatcher("ListProducts").forward(request, response); // Redirect back to product list
    }
}