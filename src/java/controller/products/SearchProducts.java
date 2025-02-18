package controller.products;

import DAO.DAOProduct;
import model.Products;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListRice", urlPatterns = {"/ListRice"}) // Matches your form action
public class SearchProducts extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("search"); // Get search term from form

        DAOProduct dao = new DAOProduct();
        List<Products> productsList;

        if (keyword != null && !keyword.isEmpty()) { // Check if keyword is not empty
            productsList = dao.searchProductsByName(keyword); // Call search method
        } else {
            // If keyword is empty, retrieve all products (or a default set)
            productsList = dao.listAll(); // You might need a getAllProducts() method in your DAO
        }

        request.setAttribute("products", productsList); // Set the list in request scope
        request.getRequestDispatcher("dashboard/home.jsp").forward(request, response); // Forward to your JSP
    }
}
