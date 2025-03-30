package controller.zone;

import DAO.DAOZones;
import model.Zones;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class UpdateZones extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                HttpSession session = request.getSession();

        try {
            // Lấy tham số zoneID từ request
            String zoneIDParam = request.getParameter("zoneID");
            System.out.println("Received zoneID: " + zoneIDParam);
            Integer roleID = (Integer) session.getAttribute("roleID");
        if(roleID == 1 ){
            request.getRequestDispatcher("listusers").forward(request, response);
        }
        if(roleID == 3 ){
            request.getRequestDispatcher("ListZones").forward(request, response);
        }
            // Kiểm tra nếu zoneID không hợp lệ
            if (zoneIDParam == null || zoneIDParam.trim().isEmpty()) {
                request.setAttribute("message", "Lỗi: Không có ID khu vực được truyền!");
                request.getRequestDispatcher("ListZones").forward(request, response);
                return;
            }

            int zoneID = Integer.parseInt(zoneIDParam);
            DAOZones dao = new DAOZones();
            Zones zone = dao.getZoneByID(zoneID);

            if (zone == null) {
                request.setAttribute("message", "Lỗi: Không tìm thấy khu vực có ID " + zoneID);
                request.getRequestDispatcher("ListZones").forward(request, response);
                return;
            }

            // Gửi dữ liệu zone sang JSP
            request.setAttribute("zone", zone);
            request.getRequestDispatcher("zone/update_zones.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            System.out.println("Lỗi: Không thể chuyển đổi zoneID sang số nguyên");
            request.setAttribute("message", "Lỗi: ID khu vực không hợp lệ!");
            request.getRequestDispatcher("ListZones").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int zoneID = Integer.parseInt(request.getParameter("zoneID"));
            String zoneName = request.getParameter("zoneName");
            String description = request.getParameter("description"); // Lấy mô tả từ form
            HttpSession session = request.getSession();
            Integer storeID = (Integer) session.getAttribute("storeID");
            // Kiểm tra dữ liệu đầu vào
            if (zoneName == null || zoneName.trim().isEmpty()) {
                request.setAttribute("message", "Lỗi: Tên khu vực không được để trống!");
                request.getRequestDispatcher("zone/update_zones.jsp").forward(request, response);
                return;
            }

            DAOZones dao = new DAOZones();
            Zones existingZone = dao.getZoneByID(zoneID);
            if (existingZone == null) {
                request.setAttribute("message", "Lỗi: Không tìm thấy khu vực có ID " + zoneID);
                request.getRequestDispatcher("ListZones").forward(request, response);
                return;
            }

            String imagePath = existingZone.getImage(); // Mặc định giữ ảnh cũ

            // Xử lý upload ảnh mới (nếu có)
            Part imagePart = request.getPart("image");
            if (imagePart != null && imagePart.getSize() > 0) {
                String uploadDir = getServletContext().getRealPath("/Image/");
                File uploadFolder = new File(uploadDir);
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdir();
                }
                String imageName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
                File imageFile = new File(uploadFolder, imageName);
                imagePart.write(imageFile.getAbsolutePath());

                // Cập nhật đường dẫn ảnh mới
                imagePath = "Image/" + imageName;
            }

            // Cập nhật thời gian
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String updateAt = now.format(formatter);

            // Cập nhật zone vào DB
            boolean updated = dao.updateZone(zoneID, zoneName, description, imagePath, updateAt, storeID);

            if (updated) {
                request.setAttribute("message", "Cập nhật khu vực thành công!");
            } else {
                request.setAttribute("message", "Lỗi: Cập nhật khu vực thất bại!");
            }

            // Chuyển hướng về form chỉnh sửa sau khi cập nhật
            response.sendRedirect("ListZones");
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Lỗi: ID khu vực không hợp lệ!");
            request.getRequestDispatcher("ListZones").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi: " + e.getMessage());
            request.getRequestDispatcher("ListZones").forward(request, response);
        }
    }

}


