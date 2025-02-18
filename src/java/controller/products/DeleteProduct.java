package controller.products;

import DAO.DAOProduct;
import model.Products;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

<<<<<<< HEAD

=======
@WebServlet(name = "DeleteProduct", urlPatterns = {"/DeleteProduct"}) // Correct URL mapping
>>>>>>> 78b7f86cdf0107bbd1f3700e282817d766c50fbc
public class DeleteProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
<<<<<<< HEAD
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
=======
        String productIDStr = request.getParameter("productID");

        if (productIDStr != null && !productIDStr.isEmpty()) {
            try {
                int productID = Integer.parseInt(productIDStr);

                DAOProduct dao = new DAOProduct();

                // Instead of removeProduct, use updateIsDelete to set isDelete to true
                int result = dao.updateIsDelete(productID, true);  // Set isDelete to true

                if (result > 0) {
                    request.setAttribute("message", "success");
                } else {
                    request.setAttribute("message", "error: Product not found or update failed");
                }

            } catch (NumberFormatException e) {
                request.setAttribute("message", "error: Invalid product ID format");
                e.printStackTrace();
            }
        } else {
            request.setAttribute("message", "error: Product ID is missing");
        }

        request.getRequestDispatcher("ListProducts").forward(request, response);
    }
}
>>>>>>> 78b7f86cdf0107bbd1f3700e282817d766c50fbc
