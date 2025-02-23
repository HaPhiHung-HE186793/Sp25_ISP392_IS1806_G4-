package controller.products;

import DAO.DAOProduct;
import jakarta.servlet.RequestDispatcher;
import model.Products;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@WebServlet(name = "UpdateProduct", urlPatterns = {"/UpdateProduct"})
public class UpdateProducts extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int productID = Integer.parseInt(request.getParameter("productID"));

            DAOProduct dao = new DAOProduct();
            Products product = dao.getProductById(productID);

            if (product == null) {
                response.sendRedirect("ListRice"); // Or redirect to an error page
                return;
            }

            request.setAttribute("product", product);
            request.getRequestDispatcher("dashboard/update_products.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("ListRice"); // Or redirect to an error page
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int productId = Integer.parseInt(request.getParameter("productID"));
        String productName = request.getParameter("productName");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        String image = request.getParameter("image");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String updateAt = now.format(formatter);
        // Call the DAO method directly with the individual parameters
        DAOProduct dao = new DAOProduct();
        boolean updated = dao.updateProduct(productId, productName, description, price, image, updateAt);
        request.getRequestDispatcher("ListProducts").forward(request, response);
        // ... (rest of the servlet logic as before)

    }

}
