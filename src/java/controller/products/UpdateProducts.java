package controller.products;

import DAO.DAOProduct;
import jakarta.servlet.RequestDispatcher;
import model.Products;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB: Kích thước tối đa lưu vào bộ nhớ trước khi ghi vào file
        maxFileSize = 1024 * 1024 * 10, // 10MB: Kích thước file tối đa
        maxRequestSize = 1024 * 1024 * 50 // 50MB: Kích thước tối đa của toàn bộ request
)
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

        try {
            int productId = Integer.parseInt(request.getParameter("productID"));
            String productName = request.getParameter("productName");
            String description = request.getParameter("description");
            String priceStr = request.getParameter("price").replaceFirst("^0+(\\d+)", "$1"); // Xóa số 0 ở đầu
            double price = Double.parseDouble(priceStr);

            // Lấy sản phẩm hiện tại từ DB để lấy ảnh cũ nếu không cập nhật ảnh mới
            DAOProduct dao = new DAOProduct();
            Products existingProduct = dao.getProductById(productId);
            String image = existingProduct.getImage(); // Giữ ảnh cũ mặc định
            //quynh
            double oldPrice = existingProduct.getPrice();

            // Xử lý upload ảnh mới (nếu có)
            Part imagePart = request.getPart("image");
            if (imagePart != null && imagePart.getSize() > 0) {
                String uploadDir = getServletContext().getRealPath("/Image/");
                File uploadFolder = new File(uploadDir);
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdir();
                }
                String imageName = imagePart.getSubmittedFileName();
                File imageFile = new File(uploadFolder, imageName);
                imagePart.write(imageFile.getAbsolutePath());

                // Cập nhật đường dẫn ảnh mới
                image = "Image/" + imageName;
            }

            // Kiểm tra giá trị hợp lệ
            if (price < 0) {
                request.setAttribute("errorMessage", "Giá sản phẩm không thể âm!");
                request.setAttribute("product", existingProduct);
                request.getRequestDispatcher("dashboard/update_products.jsp").forward(request, response);
                return;
            }

            // Cập nhật thời gian
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String updateAt = now.format(formatter);

            // Cập nhật sản phẩm vào DB
            boolean updated = dao.updateProduct(productId, productName, description, price, image, updateAt);
//quynh
            HttpSession session = request.getSession();
            int userId = (int) session.getAttribute("userID");

            if (updated) {
                //quynh
               // chỉ ghi log nếu giá thay đổi
               if(oldPrice!=price){
                   boolean logged = DAOProduct.INSTANCE.logPriceChange(productId, BigDecimal.valueOf(price), "sell", userId);
               }

                request.setAttribute("message", "Cập nhật sản phẩm thành công");
            } else {
                request.setAttribute("message", "Lỗi: Cập nhật sản phẩm thất bại");
            }

            // Chuyển hướng về danh sách sản phẩm
            request.getRequestDispatcher("ListProducts").forward(request, response);
        } catch (NumberFormatException e) {
            int productId = Integer.parseInt(request.getParameter("productID"));
            request.setAttribute("errorMessage", "Giá sản phẩm không hợp lệ!");
            DAOProduct dao = new DAOProduct();
            Products product = dao.getProductById(productId);
            request.setAttribute("product", product);
            request.getRequestDispatcher("dashboard/update_products.jsp").forward(request, response);
        }
    }
}
