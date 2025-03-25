/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.debt;

import DAO.DAOCustomers;
import DAO.DAODebtRecords;
import model.Customers;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DebtRecords;
import service.WebAppListener;

/**
 *
 * @author TIEN DAT PC
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class AddNewCustomerDebt extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        DAOCustomers daoCus = new DAOCustomers();
        response.setContentType("text/html;charset=UTF-8");
        int customerID = Integer.parseInt(request.getParameter("customerid"));
        Part image = request.getPart("image");
        String description = request.getParameter("description");
        int paymentStatus = Integer.parseInt(request.getParameter("typeDebt"));
        String updateAt = request.getParameter("dateTime");
        Integer storeID = (Integer) session.getAttribute("storeID");

        BigDecimal debt = BigDecimal.ZERO;
        String debtStr = request.getParameter("debt");
        if (debtStr != null && !debtStr.trim().isEmpty()) {
            try {
                debt = new BigDecimal(debtStr).abs();
            } catch (NumberFormatException e) {
                response.sendRedirect("ListCustomer");
                return;
            }
        } else {
            debt = new BigDecimal(0);
        }

        String imageLink = "";
        String imageDirectory = getServletContext().getRealPath("/Image/");

        if (image != null && image.getSize() > 0) {
            String fileName = image.getSubmittedFileName();
            File dir = new File(imageDirectory);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, fileName);
            image.write(file.getAbsolutePath());
            imageLink = "Image/" + fileName;
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createAt = now.format(formatter);  // Thời gian hiện tại

        if (updateAt == null || updateAt.trim().isEmpty()) {
            updateAt = createAt;
        }

        boolean isDelete = false;
        Integer createBy = (Integer) session.getAttribute("userID");
        DAODebtRecords dao = new DAODebtRecords();

        try {
            if (createBy != null) {
                DebtRecords debtRecord = new DebtRecords();
                debtRecord.setCustomerID(customerID);
                debtRecord.setAmount(debt);
                debtRecord.setPaymentStatus(paymentStatus);
                debtRecord.setCreateAt(createAt);
                debtRecord.setUpdateAt(updateAt);
                debtRecord.setIsDelete(isDelete);
                debtRecord.setCreateBy(createBy);
                debtRecord.setDescription(description);
                debtRecord.setImg(imageLink);
                debtRecord.setDescription(description);
                debtRecord.setStoreID(storeID);

                int debtID = dao.addDebtRecord(debtRecord); // Thêm vào database và lấy debtID

                // Cập nhật lại debtID trong đối tượng DebtRecord trước khi đẩy vào queue
                debtRecord.setDebtID(debtID);

                // Đẩy vào hàng đợi để xử lý
                WebAppListener.debtQueue.offer(debtRecord);

                response.sendRedirect("ListDebtCustomer?customerid=" + customerID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
