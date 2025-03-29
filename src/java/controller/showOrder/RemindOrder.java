/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.showOrder;

import DAO.DAOShowOrder;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;
import model.ShowOrder;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="RemindOrder", urlPatterns={"/URLRemindOrder"})
public class RemindOrder extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet RemindOrder</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RemindOrder at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAOShowOrder dao = new DAOShowOrder();
        HttpSession session = request.getSession();
        Integer role = (Integer) session.getAttribute("roleID");
        if (role == 1) {
            response.sendRedirect("listusers"); // sửa thành đường dẫn của trang chủ sau khi hoàn thành code
            return;
        }
        String service = request.getParameter("service");
        if (service == null) {
            service = "listshow";
        }

        int pageSize = 10; // Số hàng mỗi trang
        int pageNumber = 1; // Số trang hiện tại
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            pageNumber = Integer.parseInt(pageParam);
        }

        int offset = (pageNumber - 1) * pageSize;

        // Lấy tham số tìm kiếm và sắp xếp
        String customerName = request.getParameter("customerName");
        String selectedDate = request.getParameter("date");
        String sortColumn = request.getParameter("sortColumn");
        String sortOrder = request.getParameter("sortOrder");

        if (sortColumn == null) {
            sortColumn = "0"; // Giá trị mặc định
        }
        if (sortOrder == null) {
            sortOrder = "asc"; // Giá trị mặc định
        }

        String sql = "SELECT o.orderID, c.name, u.userName, o.paidAmount, o.totalAmount, o.createAt, o.updateAt, o.porter, o.status, u.storeID, o.orderType,c.email " +
                     "FROM orders o " +
                     "JOIN customers c ON o.customerID = c.customerID " +
                     "JOIN users u ON o.userID = u.ID "+
                     "WHERE o.paidAmount < o.totalAmount AND o.orderType = 1 ";

        // Lấy storeID từ session
        Integer storeID = (Integer) request.getSession().getAttribute("storeID");
        if (storeID != null) {
            sql += "AND u.storeID = " + storeID + " "; // Thêm điều kiện lọc theo storeID
        }

        // Thêm điều kiện tìm kiếm
        if (customerName != null && !customerName.isEmpty()) {
            sql += (storeID != null ? "AND " : "WHERE ") + "c.name LIKE '%" + customerName + "%' ";
        }
        if (selectedDate != null && !selectedDate.isEmpty()) {
            sql += (customerName != null ? "AND " : (storeID != null ? "AND " : "WHERE ")) + "CONVERT(date, o.createAt) = '" + selectedDate + "' ";
        }

        // Thêm điều kiện sắp xếp
        switch (sortColumn) {
            case "0":
                sql += "ORDER BY o.orderID " + sortOrder;
                break;
            case "1":
                sql += "ORDER BY c.name " + sortOrder;
                break;
            case "2":
                sql += "ORDER BY u.userName " + sortOrder;
                break;
            case "3":
                sql += "ORDER BY o.totalAmount " + sortOrder;
                break;
            case "4":
                sql += "ORDER BY o.createAt " + sortOrder;
                break;
            case "5":
                sql += "ORDER BY o.updateAt " + sortOrder;
                break;
            case "6":
                sql += "ORDER BY o.porter " + sortOrder;
                break;
            default:
                sql += "ORDER BY o.orderID " + sortOrder; // Mặc định
                break;
        }

        // Thêm logic cho phân trang
        sql += " OFFSET " + offset + " ROWS FETCH NEXT " + pageSize + " ROWS ONLY";

        try (PrintWriter out = response.getWriter()) {
            Vector<ShowOrder> vector = dao.getRemindOrder(sql);
            int totalAmount = 0; // Khởi tạo tổng giá tiền

            // Tính tổng giá tiền cho tất cả các bản ghi
            String totalAmountSql = "SELECT SUM(o.totalAmount) FROM orders o " +
                                    "JOIN customers c ON o.customerID = c.customerID " +
                                    "JOIN users u ON o.userID = u.ID "+
                                    "WHERE o.orderType = 1 ";

            // Thêm điều kiện lọc theo storeID
            if (storeID != null) {
                totalAmountSql += "AND u.storeID = " + storeID + " ";
            }

            // Thêm điều kiện tìm kiếm vào câu truy vấn tổng
            if (customerName != null && !customerName.isEmpty()) {
                totalAmountSql += (storeID != null ? "AND " : "WHERE ") + "c.name LIKE '%" + customerName + "%' ";
            }
            if (selectedDate != null && !selectedDate.isEmpty()) {
                totalAmountSql += (customerName != null ? "AND " : (storeID != null ? "AND " : "WHERE ")) + "CONVERT(date, o.createAt) = '" + selectedDate + "' ";
            }

            totalAmount = dao.getTotalAmount(totalAmountSql); // Lấy tổng giá tiền từ database
                                // Tính số lượng hóa đơn trả thiếu và tổng số tiền trả thiếu
                    String countSql = "SELECT COUNT(*) FROM orders o " +
                                      "JOIN users u ON o.userID = u.ID "+
                                      "WHERE o.paidAmount < o.totalAmount AND o.orderType = 1 ";

                    if (storeID != null) {
                        countSql += "AND u.storeID = " + storeID + " ";
                    }
                    if (selectedDate != null && !selectedDate.isEmpty()) {
            countSql += (customerName != null ? "AND " : (storeID != null ? "AND " : "WHERE ")) + "CONVERT(date, o.createAt) = '" + selectedDate + "' ";
        }

                    int count = dao.getTotalRecords(countSql); // Số lượng hóa đơn trả thiếu

                    String totalMissingSql = "SELECT SUM(o.totalAmount - o.paidAmount) FROM orders o " +
                                             "JOIN users u ON o.userID = u.ID "+
                                             "WHERE o.paidAmount < o.totalAmount AND o.orderType = 1 ";

                    if (storeID != null) {
                        totalMissingSql += "AND u.storeID = " + storeID + " ";
                    }
                    if (selectedDate != null && !selectedDate.isEmpty()) {
                    totalMissingSql += (customerName != null ? "AND " : (storeID != null ? "AND " : "WHERE ")) + "CONVERT(date, o.createAt) = '" + selectedDate + "' ";
        }

                    double totalMissingAmount = dao.getTotalAmount(totalMissingSql); // Tổng số tiền trả thiếu

                    // Gửi thông tin thống kê đến JSP
                    request.setAttribute("countMissingOrders", count);
                    request.setAttribute("totalMissingAmount", totalMissingAmount);
            request.setAttribute("data", vector);
            request.setAttribute("tableTitle", "Danh sách hóa đơn trả thiếu");
            request.setAttribute("pageTitle", "order");
            request.setAttribute("currentPage", pageNumber);
            request.setAttribute("totalPages", getTotalPages(dao, pageSize, storeID)); // Gọi getTotalPages với storeID
            request.setAttribute("customerName", customerName);
            request.setAttribute("date", selectedDate);
            request.setAttribute("sortColumn", sortColumn);
            request.setAttribute("sortOrder", sortOrder); // Gửi thứ tự sắp xếp đến JSP
            request.setAttribute("totalAmount", totalAmount); // Gửi tổng giá tiền đến JSP
            RequestDispatcher dispth = request.getRequestDispatcher("order/remindOrder.jsp");
            dispth.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private int getTotalPages(DAOShowOrder dao, int pageSize, Integer storeID) {
        String countSql = "SELECT COUNT(*) " +
                          "FROM orders o " +
                          "JOIN customers c ON o.customerID = c.customerID " +
                          "JOIN users u ON o.userID = u.ID "+
                          "WHERE o.paidAmount < o.totalAmount AND o.orderType = 1 ";

        // Thêm điều kiện lọc theo storeID
        if (storeID != null) {
            countSql += "AND u.storeID = " + storeID + " ";
        }

        int totalRecords = dao.getTotalRecords(countSql);
        if (totalRecords == 0) {
            return 1;
        }
        return (int) Math.ceil((double) totalRecords / pageSize); // Sử dụng pageSize từ tham số
    }
}