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
import java.util.ArrayList;
import java.util.List;
import model.User;
import utils.mail;
import java.io.File;
import java.util.regex.Pattern;
import model.Store;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
/**
 *
 * @author nguyenanh
 */
public class CreateStore extends HttpServlet {

    DAO.DAOStore daos = new DAOStore();
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    private void AddStore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        HttpSession session = request.getSession();
        User user_current = (User) session.getAttribute("user");
        // Lấy dữ liệu từ form
        String storeName = request.getParameter("storeName");
        Part filePart = request.getPart("logo");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");          
        String address = request.getParameter("address");

        String imageLink = "";
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
        } else {
            imageLink = "Image/logo.png";
        }

        // lấy id của user hiện tại
        Integer createBy = (Integer) session.getAttribute("userID");

        long fileSize = filePart.getSize(); // Lấy kích thước file (bytes)
        if (fileSize > 10 * 1024 * 1024) { // Kiểm tra file lớn hơn 10MB
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
        if (email != null && !email.trim().isEmpty()) {
            if (!Pattern.matches(EMAIL_REGEX, email)) {
                errors.add("Email không hợp lệ!");
            }
        }
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("storeName", storeName);
            request.setAttribute("logo", filePart);
            request.setAttribute("phone", phone);
            request.setAttribute("emaill", email);
            request.setAttribute("address", address);
            request.setAttribute("u", user_current);
            request.getRequestDispatcher("/store/createStore.jsp").forward(request, response);
            return;
        }

        Store newStore = new Store(storeName, user_current.getID(), address, phone, email, imageLink);
        daos.createStore(newStore);

        request.setAttribute("success", "Thêm thành công!");
        request.setAttribute("storeName", storeName);
        request.setAttribute("logo", filePart);
        request.setAttribute("phone", phone);
        request.setAttribute("emaill", email);
        request.setAttribute("address", address);
        request.setAttribute("u", user_current);
        request.getRequestDispatcher("/store/createStore.jsp").forward(request, response);
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
        request.setAttribute("u", user_current);
        request.getRequestDispatcher("store/createStore.jsp").forward(request, response);
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
        AddStore(request, response);
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
