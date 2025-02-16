package controller.showOrder;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ShowOrder;
import DAO.DAOShowOrder;
import jakarta.servlet.RequestDispatcher;
import java.util.Vector;

public class ControllerOrder extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        DAOShowOrder dao = new DAOShowOrder();
        String service = request.getParameter("service");
        if (service == null) {
            service = "listshow";
        }

        int pageSize = 5; // Số hàng mỗi trang
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

        String sql = "SELECT o.orderID, c.name, u.userName, o.totalAmount, o.createAt, o.updateAt, o.porter, o.status " +
                     "FROM orders o " +
                     "JOIN customers c ON o.customerID = c.customerID " +
                     "JOIN users u ON o.userID = u.ID ";

        // Thêm điều kiện tìm kiếm
        if (customerName != null && !customerName.isEmpty()) {
            sql += "WHERE c.name LIKE '%" + customerName + "%' ";
        }
        if (selectedDate != null && !selectedDate.isEmpty()) {
            sql += (customerName != null ? "AND " : "WHERE ") + "CONVERT(date, o.createAt) = '" + selectedDate + "' ";
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
            Vector<ShowOrder> vector = dao.getShowOrder(sql);
            int totalAmount = 0; // Khởi tạo tổng giá tiền

            // Tính tổng giá tiền cho tất cả các bản ghi
            String totalAmountSql = "SELECT SUM(o.totalAmount) FROM orders o " +
                                    "JOIN customers c ON o.customerID = c.customerID " +
                                    "JOIN users u ON o.userID = u.ID ";

            // Thêm điều kiện tìm kiếm vào câu truy vấn tổng
            if (customerName != null && !customerName.isEmpty()) {
                totalAmountSql += "WHERE c.name LIKE '%" + customerName + "%' ";
            }
            if (selectedDate != null && !selectedDate.isEmpty()) {
                totalAmountSql += (customerName != null ? "AND " : "WHERE ") + "CONVERT(date, o.createAt) = '" + selectedDate + "' ";
            }

            totalAmount = dao.getTotalAmount(totalAmountSql); // Lấy tổng giá tiền từ database

            request.setAttribute("data", vector);
            request.setAttribute("tableTitle", "Danh sách hóa đơn");
            request.setAttribute("pageTitle", "order");
            request.setAttribute("currentPage", pageNumber);
            request.setAttribute("totalPages", getTotalPages(dao, pageSize));
            request.setAttribute("customerName", customerName);
            request.setAttribute("date", selectedDate);
            request.setAttribute("sortColumn", sortColumn);
            request.setAttribute("sortOrder", sortOrder); // Gửi thứ tự sắp xếp đến JSP
            request.setAttribute("totalAmount", totalAmount); // Gửi tổng giá tiền đến JSP
            RequestDispatcher dispth = request.getRequestDispatcher("showOrder.jsp");
            dispth.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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

    private int getTotalPages(DAOShowOrder dao, int pageSize) {
        String countSql = "SELECT COUNT(*) " +
                          "FROM orders o " +
                          "JOIN customers c ON o.customerID = c.customerID " +
                          "JOIN users u ON o.userID = u.ID";
        int totalRecords = dao.getTotalRecords(countSql);
        if (totalRecords == 0) {
            return 1;
        }
        return (int) Math.ceil((double) totalRecords / pageSize); // Sử dụng pageSize từ tham số
    }
}