package controller.products;

import DAO.DAOProduct;
import model.Products;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        
            int productID = Integer.parseInt(request.getParameter("productID"));
            String productName = request.getParameter("productName");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            
            
            

            String image = request.getParameter("image");

            String createAt = request.getParameter("createAt");
            String updateAt = request.getParameter("updateAt");

            int createBy = 0;
            String createByStr = request.getParameter("createBy");
            if (createByStr != null && !createByStr.isEmpty()) {
                try {
                    createBy = Integer.parseInt(createByStr);
                } catch (NumberFormatException e) {
                    request.setAttribute("errorMessage", "Invalid createBy format.");
                    request.getRequestDispatcher("dashboard/update_products.jsp").forward(request, response);
                    return;
                }
            } else {
                request.setAttribute("errorMessage", "CreateBy is required.");
                request.getRequestDispatcher("dashboard/update_products.jsp").forward(request, response);
                return;
            }

            boolean isDelete = Boolean.parseBoolean(request.getParameter("isDelete"));
            String deleteAt = request.getParameter("deleteAt");

            int deleteBy = 0;
            String deleteByStr = request.getParameter("deleteBy");
            if (deleteByStr != null && !deleteByStr.isEmpty()) {
                try {
                    deleteBy = Integer.parseInt(deleteByStr);
                } catch (NumberFormatException e) {
                    request.setAttribute("errorMessage", "Invalid deleteBy format.");
                    request.getRequestDispatcher("dashboard/update_products.jsp").forward(request, response);
                    return;
                }
            } else {
                request.setAttribute("errorMessage", "DeleteBy is required.");
                request.getRequestDispatcher("dashboard/update_products.jsp").forward(request, response);
                return;
            }

            Products product = new Products();
            product.setProductID(productID);
            product.setProductName(productName);
            product.setDescription(description);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setImage(image);
            product.setCreateAt(createAt);
            product.setUpdateAt(updateAt);
            product.setCreateBy(createBy);
            product.setIsDelete(isDelete);
            product.setDeleteAt(deleteAt);
            product.setDeleteBy(deleteBy);

            DAOProduct dao = new DAOProduct();
            int n = dao.updateProduct(product);

            if (n > 0) {
                response.sendRedirect("ListRice");
            } else {
                request.setAttribute("errorMessage", "Failed to update product.");
                request.getRequestDispatcher("dashboard/update_products.jsp").forward(request, response);
            }
        } 
    }

