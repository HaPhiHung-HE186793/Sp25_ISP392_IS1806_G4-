/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.store;

import DAO.DAOStore;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import model.Store;
import model.User;


@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
/**
 *
 * @author nguyenanh
 */
public class UpdateStore extends HttpServlet {

    DAO.DAOStore daos = new DAOStore();
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateStore</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateStore at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer roleID = (Integer) session.getAttribute("roleID");
        User user_current = (User) session.getAttribute("user");        
        if (roleID != 1 && roleID != 2) {
            response.sendRedirect("ListProducts"); // sửa thành đường dẫn của trang chủ sau khi hoàn thành code
            return;
        }
        Integer storeIdUpdate = (Integer) session.getAttribute("storeIdUpdate");
        if (storeIdUpdate == null) {
            response.sendRedirect("liststore");
        }
        Store store = daos.getStoreById(storeIdUpdate);

        request.setAttribute("user_current", user_current);
        request.setAttribute("store", store);
        request.getRequestDispatcher("store/updateStore.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        HttpSession session = request.getSession();
        User user_current = (User) session.getAttribute("user");
        // Lấy dữ liệu từ form        
        Integer storeId = (Integer) session.getAttribute("storeIdUpdate");
        String storeName = request.getParameter("storeName");
        Part filePart = request.getPart("logo");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        String imageLink = request.getParameter("existingImage"); // Lấy ảnh cũ nếu không chọn ảnh mới
        String imageDirectory = getServletContext().getRealPath("/Image/");

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();
            File dir = new File(imageDirectory);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, fileName);
            filePart.write(file.getAbsolutePath());
            imageLink = "Image/" + fileName;
        }

// Kiểm tra dung lượng file
        if (filePart != null && filePart.getSize() > 10 * 1024 * 1024) {
            errors.add("File quá lớn! Vui lòng chọn file có dung lượng dưới 10MB.");
        }

        // Kiểm tra dữ liệu nhập
        if (storeName == null || storeName.trim().isEmpty()) {
            errors.add("Vui lòng nhập đầy đủ họ tên!");
        }
        if (phone == null || phone.trim().isEmpty()) {
            errors.add("Vui lòng nhập số điện thoại!");
        } else if (!phone.matches("0\\d{9}")) {
            errors.add("Số điện thoại phải có 10 chữ số và bắt đầu bằng số 0!");
        }
        if (address == null || address.trim().isEmpty()) {
            errors.add("Vui lòng nhập địa chỉ!");
        }
        if (!Pattern.matches(EMAIL_REGEX, email) && email != null && !email.trim().isEmpty()) {
            errors.add("Email không hợp lệ!");        
        }
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            Store store = daos.getStoreById(storeId);
            request.setAttribute("store", store);
            request.getRequestDispatcher("/store/updateStore.jsp").forward(request, response);
            return;
        }

        Store newStore = new Store(storeId, storeName, user_current.getID(), address, phone, email, imageLink);
        daos.updateStore2(newStore);

        request.setAttribute("success", "Thêm thành công!");
        request.setAttribute("storeName", storeName);
        request.setAttribute("logo", filePart);
        request.setAttribute("phone", phone);
        request.setAttribute("email", email);
        request.setAttribute("address", address);
        request.setAttribute("store", newStore);
        request.getRequestDispatcher("/store/updateStore.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
