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

        Integer roleID = (Integer) session.getAttribute("roleID");
        if(roleID == 1 ){
            request.getRequestDispatcher("listusers").forward(request, response);
        }
        if(roleID == 3 ){
            request.getRequestDispatcher("ListProducts").forward(request, response);
        }
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

        Integer storeID = (Integer) session.getAttribute("storeID");

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

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createAt = now.format(formatter);
        String updateAt = createAt;

        String[] selectedZones = request.getParameterValues("zoneIDs");
        if (selectedZones == null || selectedZones.length == 0) {
            request.setAttribute("message", "Lỗi: Bạn chưa chọn khu vực");
            request.getRequestDispatcher("/dashboard/insert_product.jsp").forward(request, response);
            return;
        }

        DAOProduct daoProduct = new DAOProduct();
        DAOZones daoZones = new DAOZones();

        if (daoProduct.isProductNameExists(productName, storeID)) {
            request.setAttribute("message", "Lỗi: Tên sản phẩm đã tồn tại trong cửa hàng này");
            request.getRequestDispatcher("/dashboard/insert_product.jsp").forward(request, response);
            return;
        }

        Products product = new Products(productName, description, price, 0, "Image/" + imageName, createAt, updateAt, createBy, false, null, 0, storeID);
        int productID = daoProduct.insertProduct(product);

        if (productID > 0) {
            for (String zoneIdStr : selectedZones) {
                int zoneID = Integer.parseInt(zoneIdStr);
                daoZones.updateZoneWithProduct(zoneID, productID, storeID);
            }

            // ✅ Lưu quy cách đóng gói vào bảng ProductUnits
            // Lấy giá trị từ input text
            String productUnitStr = request.getParameter("productUnit");

// Nếu không có dữ liệu, mặc định lưu 1
            if (productUnitStr == null || productUnitStr.trim().isEmpty()) {
                daoProduct.insertProductUnit(productID, 1);
            } else {
                // Tách các giá trị theo dấu ",", loại bỏ khoảng trắng thừa
                String[] unitStrings = productUnitStr.split(",");

                for (String unit : unitStrings) {
                    try {
                        // Chuyển đổi từng giá trị thành số nguyên
                        int unitSize = Integer.parseInt(unit.trim().replaceAll("[^0-9]", ""));
                        daoProduct.insertProductUnit(productID, unitSize);
                    } catch (NumberFormatException e) {
                        // Bỏ qua giá trị không hợp lệ
                        System.out.println("Bỏ qua giá trị không hợp lệ: " + unit);
                    }
                }
            }

            request.getRequestDispatcher("ListProducts").forward(request, response);
        }

    }
}
