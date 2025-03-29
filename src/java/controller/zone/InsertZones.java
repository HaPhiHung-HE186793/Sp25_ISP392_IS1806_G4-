package controller.zone;

import DAO.DAOZones;
import model.Zones;
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

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class InsertZones extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Integer storeID = (Integer) session.getAttribute("storeID");
        session.setMaxInactiveInterval(Integer.MAX_VALUE);

        // Lấy dữ liệu từ form
        String zoneName = request.getParameter("zoneName");
        int createBy = (session.getAttribute("userID") != null) ? (int) session.getAttribute("userID") : 0;
        String description = request.getParameter("description");
        // Kiểm tra xem khu vực đã tồn tại chưa
        DAOZones dao = new DAOZones();
        if (dao.isZoneNameExists(zoneName, storeID)) {
            request.setAttribute("message", "Lỗi: Tên khu vực đã tồn tại!");
            request.getRequestDispatcher("zone/insert_zones.jsp").forward(request, response);
            return;
        }

        // Xử lý upload ảnh (nếu có)
        Part imagePart = request.getPart("image");
        String imageName = null;
        if(imagePart != null && imagePart.getSize() > 0){                        
            String uploadDir = getServletContext().getRealPath("/ImageZone/");
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdir();
            }
            imageName = imagePart.getSubmittedFileName();
            File imageFile = new File(uploadFolder, imageName);
            imagePart.write(imageFile.getAbsolutePath());        
        }

        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createAt = now.format(formatter);
        String updateAt = createAt; // Ban đầu updateAt cũng là thời điểm hiện tại

        // Tạo đối tượng Zone
        Zones newZone = new Zones(zoneName, createAt, updateAt, createBy, false, updateAt, createBy, storeID, imageName, description);

        // Thêm khu vực vào database
        int result = dao.insertZone1(newZone);

        if (result > 0) {
            request.setAttribute("message", "Thêm khu vực thành công!");
        } else {
            request.setAttribute("message", "Lỗi: Thêm khu vực thất bại!");
            request.getRequestDispatcher("a.jsp").forward(request, response);
        }

        // Chuyển hướng về danh sách khu vực
        request.getRequestDispatcher("ListZones").forward(request, response);
    }
}
