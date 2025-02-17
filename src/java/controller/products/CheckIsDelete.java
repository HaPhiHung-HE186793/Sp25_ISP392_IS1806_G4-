package controller.products;

import DAO.DAOProduct;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateIsDeleteServlet", urlPatterns = {"/UpdateIsDeleteServlet"})
public class CheckIsDelete extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        boolean isDeleted = Boolean.parseBoolean(request.getParameter("isDeleted"));

        DAOProduct dao = new DAOProduct();
        dao.updateIsDelete(productId, isDeleted); // Gọi phương thức cập nhật isDelete trong DAO
        request.getRequestDispatcher("ListProducts").forward(request, response);
//        response.getWriter().write("OK"); // Phản hồi về cho client (không bắt buộc)
    }
}
