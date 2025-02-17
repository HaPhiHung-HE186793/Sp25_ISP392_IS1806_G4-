package controller.products;

import DAO.DAOProduct;
import model.Products;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteProduct", urlPatterns = {"/DeleteProduct"}) // Correct URL mapping
public class DeleteProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
