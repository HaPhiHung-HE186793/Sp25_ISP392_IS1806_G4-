package controller.products;

import DAO.DAOProduct;
import DAO.DAOZones;
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
import java.util.List;
import model.Products;
import model.Zones;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class CreateProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer storeID = (Integer) session.getAttribute("storeID");
        DAOZones daoZone = new DAOZones();
        List<Zones> zonesList = daoZone.getEmptyZones(storeID);

        request.setAttribute("zonesList", zonesList);
        request.getRequestDispatcher("/dashboard/insert_product.jsp").forward(request, response);
    }

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

        // ✅ Lấy storeID từ request
        Integer storeID = (Integer) session.getAttribute("storeID");

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

        // ✅ Lấy danh sách zone được chọn từ form
        String[] selectedZones = request.getParameterValues("zoneIDs");

        if (selectedZones == null || selectedZones.length == 0) {
            request.setAttribute("message", "Lỗi: Bạn chưa chọn khu vực");
            request.getRequestDispatcher("/dashboard/insert_product.jsp").forward(request, response);
            return;
        }

        DAOProduct daoProduct = new DAOProduct();
        DAOZones daoZones = new DAOZones();

        // ✅ Kiểm tra sản phẩm đã tồn tại trong cửa hàng này
        if (daoProduct.isProductNameExists(productName, storeID)) {
            request.setAttribute("message", "Lỗi: Tên sản phẩm đã tồn tại trong cửa hàng này");
            request.getRequestDispatcher("/dashboard/insert_product.jsp").forward(request, response);
            return;
        }

        // ✅ Thêm sản phẩm vào bảng products, có storeID
        Products product = new Products(productName, description, price, quantity, "Image/" + imageName, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, storeID);
        int productID = daoProduct.insertProduct(product); // Trả về productID

        if (productID > 0) {
            for (String zoneIdStr : selectedZones) {
                int zoneID = Integer.parseInt(zoneIdStr);
                daoZones.updateZoneWithProduct(zoneID, productID, storeID); // ✅ Cập nhật storeID
            }
            request.setAttribute("message", "Thêm sản phẩm vào khu vực thành công");
        } else {
            request.setAttribute("message", "Lỗi: Thêm sản phẩm thất bại");
        }

        request.getRequestDispatcher("ListProducts").forward(request, response);
    }

}
