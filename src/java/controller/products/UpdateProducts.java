package controller.products;

import DAO.DAOProduct;
import DAO.DAOZones;
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
import java.util.ArrayList;
import java.util.List;
import model.Zones;

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
            HttpSession session = request.getSession();
            DAOProduct daoProduct = new DAOProduct();
            DAOZones daoZones = new DAOZones();
            Integer storeID = (Integer) session.getAttribute("storeID");
            // Lấy thông tin sản phẩm
            Products product = daoProduct.getProductById(productID);
            if (product == null) {
                response.sendRedirect("ListProducts");
                return;
            }
            String unitSize = daoProduct.getProductUnitSize(productID);
            // Lấy danh sách zone đã được chọn cho sản phẩm
            List<Integer> selectedZones = daoZones.getSelectedZoneIDsByProductID(productID);

            // Lấy toàn bộ danh sách zone
            List<Zones> zonesList = daoZones.getSelectedAndEmptyZones(storeID, productID);

            // Gửi dữ liệu lên JSP
            request.setAttribute("product", product);
            request.setAttribute("zonesList", zonesList);
            request.setAttribute("unitSize", unitSize);
            request.setAttribute("selectedZones", selectedZones); // Danh sách các zone đã chọn

            request.getRequestDispatcher("dashboard/update_products.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("ListProducts");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productID"));
            String productName = request.getParameter("productName");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            HttpSession session = request.getSession();
            // Lấy danh sách zone được chọn từ request
            String[] selectedZoneIDs = request.getParameterValues("zoneIDs");
            Integer storeID = (Integer) session.getAttribute("storeID");
            // Lấy sản phẩm hiện tại để giữ ảnh cũ nếu không cập nhật
            DAOProduct daoProduct = new DAOProduct();
            DAOZones daoZones = new DAOZones();
            Products existingProduct = daoProduct.getProductById(productId);
            String image = existingProduct.getImage();
            int userId = (int) session.getAttribute("userID");
            String unitSizeStr = request.getParameter("unitSize"); // Lấy chuỗi quy cách
            String[] unitSizeArray = unitSizeStr.split(","); // Tách thành mảng

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

                image = "Image/" + imageName;
            }

            // Cập nhật thời gian
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String updateAt = now.format(formatter);

            // Cập nhật thông tin sản phẩm
            boolean updated = daoProduct.updateProduct(productId, productName, description, price, image, updateAt);

            if (updated) { // Nếu cập nhật sản phẩm thành công thì mới xử lý zone
                boolean success = true; // Biến kiểm tra lỗi khi cập nhật zone

                // Kiểm tra xem có zone nào được chọn không
                if (selectedZoneIDs != null) {
                    for (String zoneIdStr : selectedZoneIDs) {
                        int zoneID = Integer.parseInt(zoneIdStr);
                        boolean updatedZone = daoZones.updateZoneWithProduct(zoneID, productId, storeID);
                        if (!updatedZone) {
                            success = false; // Nếu có lỗi
                        }
                    }
                }
                List<Integer> unitSizeValues = new ArrayList<>();
                for (String unitSize : unitSizeArray) {
                    try {
                        unitSizeValues.add(Integer.parseInt(unitSize.trim()));
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi chuyển đổi quy cách: " + unitSize);
                        success = false;
                    }
                }

                // Vòng lặp 2: Cập nhật lại toàn bộ unitSize mới
                boolean updatedUnitSize = daoProduct.updateUnitSizeByProductID(productId, unitSizeValues);
                if (!updatedUnitSize) {
                    success = false;
                }
                //quynh
                // chỉ ghi log nếu giá thay đổi
                if (oldPrice != price) {
                    boolean logged = DAOProduct.INSTANCE.logPriceChange(productId, BigDecimal.valueOf(price), "sell", userId, null);
                }
                // Kiểm tra trạng thái cập nhật zone
                if (success) {
                    request.setAttribute("message", "Cập nhật sản phẩm và khu vực thành công");
                } else {
                    request.setAttribute("message", "Lỗi: Một số khu vực không được cập nhật thành công");
                }
            } else {
                request.setAttribute("message", "Lỗi: Cập nhật sản phẩm thất bại");
            }

            // Chuyển hướng về danh sách sản phẩm
            request.getRequestDispatcher("ListProducts").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Lỗi: Dữ liệu đầu vào không hợp lệ!");
            request.getRequestDispatcher("dashboard/update_products.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Đã xảy ra lỗi trong quá trình cập nhật!");
            request.getRequestDispatcher("dashboard/update_products.jsp").forward(request, response);
        }
    }

}
