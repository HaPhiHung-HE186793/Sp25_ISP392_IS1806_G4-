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

@WebServlet(name = "ListRice", urlPatterns = {"/ListRice"})
public class SearchProducts extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keywordName = request.getParameter("search"); // Get search term for name
        String keywordDescription = request.getParameter("search2"); // Get search term for description

        DAOProduct dao = new DAOProduct();
        List<Products> productsList;

        if ((keywordName != null && !keywordName.isEmpty()) || (keywordDescription != null && !keywordDescription.isEmpty())) {
            productsList = dao.searchProducts(keywordName, keywordDescription); // New search method
        } else {
            productsList = dao.listAll();
        }

        request.setAttribute("products", productsList);
        request.getRequestDispatcher("dashboard/home.jsp").forward(request, response);
    }
}
