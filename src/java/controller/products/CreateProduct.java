package controller.products;

import DAO.DAOProduct;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.Products;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
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
            request.setAttribute("message", "Lỗi: Giá không hợp lệ");
            request.getRequestDispatcher("/dashboard/insert_product.jsp").forward(request, response);
            return;
        }
        int quantity = 0;

        Part imagePart = request.getPart("image");
        String imageName = "";
        if (imagePart != null && imagePart.getSize() > 0) {
            String uploadDir = getServletContext().getRealPath("/Image/");
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdir();
            }
            imageName = imagePart.getSubmittedFileName();
            File imageFile = new File(uploadFolder, imageName);
            imagePart.write(imageFile.getAbsolutePath());
        }

        int createBy = (session.getAttribute("userID") != null) ? (int) session.getAttribute("userID") : 0;

        boolean isDelete = false;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createAt = now.format(formatter);
        String updateAt = createAt;
        String deleteAt = null;
        Integer deleteBy = 0;

        Products product = new Products(productName, description, price, quantity, "Image/" + imageName, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
        DAOProduct dao = new DAOProduct();

        if (dao.isProductNameExists(productName)) {
            request.setAttribute("message", "Lỗi: Tên sản phẩm đã tồn tại");
            request.getRequestDispatcher("/dashboard/insert_product.jsp").forward(request, response);
            return;
        }

        int result = dao.insertProduct(product);

        if (result > 0) {
            request.setAttribute("message", "Thêm sản phẩm thành công");
        } else {
            request.setAttribute("message", "Lỗi: Thêm sản phẩm thất bại");
        }

        request.getRequestDispatcher("ListProducts").forward(request, response);
    }
}
