package controller.showOrder;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAO.DAOChart;
import model.Chart;
import jakarta.servlet.RequestDispatcher;
import java.util.Vector;
import DAO.DAOBestSeller;
import jakarta.servlet.http.HttpSession;
import model.BestSeller;

@WebServlet(name = "ControllerChart", urlPatterns = {"/URLChart"})
public class ControllerChart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer role = (Integer) session.getAttribute("roleID");
        if (role == 1) {
            response.sendRedirect("listusers"); // sửa thành đường dẫn của trang chủ sau khi hoàn thành code
            return;
        }
        DAOChart dao = new DAOChart();
        DAOBestSeller dao1 = new DAOBestSeller();
        Integer storeID = (Integer) request.getSession().getAttribute("storeID");
        String action = request.getParameter("action");

        String sql = "SELECT YEAR(o.createAt) AS year, SUM(o.totalAmount) AS totalAmount, u.storeID, o.orderType " +
                     "FROM orders o " +
                     "JOIN users u ON u.ID = o.userID ";

        if (storeID != null) {
            sql += "WHERE o.orderType= 1 and u.storeID = " + storeID + " ";
        }

        sql += "GROUP BY YEAR(o.createAt), u.storeID, o.orderType " +
               "ORDER BY YEAR(o.createAt)";

        // Lấy dữ liệu theo năm
        Vector<Chart> yearlyCharts = dao.getYearlyChart(sql);
        Integer[] yearsArray = new Integer[yearlyCharts.size()];
        for (int i = 0; i < yearlyCharts.size(); i++) {
            yearsArray[i] = yearlyCharts.get(i).getYear();
        }
        request.setAttribute("yearsArray", yearsArray);

        if ("monthly".equals(action)) {
            int year = Integer.parseInt(request.getParameter("year"));
            sql = "SELECT MONTH(o.createAt) AS month, SUM(o.totalAmount) AS totalAmount, u.storeID, o.orderType " +
                  "FROM orders o " +
                  "JOIN users u ON u.ID = o.userID " +
                  "WHERE o.orderType= 1 and YEAR(o.createAt) = " + year + " AND u.storeID = " + storeID + " " +
                  "GROUP BY MONTH(o.createAt), u.storeID, o.orderType " +
                  "ORDER BY MONTH(o.createAt)";

            Vector<Chart> monthlyCharts = dao.getMonthlyChart(sql, year);
            request.setAttribute("labelsArray", monthlyCharts.stream().map(Chart::getMonth).map(String::valueOf).toArray(String[]::new));
            request.setAttribute("totalAmountsArray", monthlyCharts.stream().map(Chart::getTotalAmount).toArray());
            request.setAttribute("pageTitle", "Biểu đồ tổng tiền theo tháng");
            request.setAttribute("selectedYear", year); // Lưu năm đã chọn
        } else if ("daily".equals(action)) {
            int year = Integer.parseInt(request.getParameter("year"));
            int month = Integer.parseInt(request.getParameter("month"));
            sql = "SELECT DAY(o.createAt) AS day, SUM(o.totalAmount) AS totalAmount, u.storeID, o.orderType " +
                  "FROM orders o " +
                  "JOIN users u ON u.ID = o.userID " +
                  "WHERE o.orderType= 1 and YEAR(o.createAt) = " + year + " AND MONTH(o.createAt) = " + month + " AND u.storeID = " + storeID + " " +
                  "GROUP BY DAY(o.createAt), u.storeID, o.orderType " +
                  "ORDER BY DAY(o.createAt)";

            Vector<Chart> dailyCharts = dao.getDailyChart(sql, year, month);
            request.setAttribute("labelsArray", dailyCharts.stream().map(Chart::getDay).map(String::valueOf).toArray(String[]::new));
            request.setAttribute("totalAmountsArray", dailyCharts.stream().map(Chart::getTotalAmount).toArray());
            request.setAttribute("pageTitle", "Biểu đồ tổng tiền theo ngày");
            request.setAttribute("selectedYear", year); // Lưu năm đã chọn
            request.setAttribute("selectedMonth", month); // Lưu tháng đã chọn
        } else {
            // Hiển thị biểu đồ theo năm
            request.setAttribute("labelsArray", yearlyCharts.stream().map(Chart::getYear).map(String::valueOf).toArray(String[]::new));
            request.setAttribute("totalAmountsArray", yearlyCharts.stream().map(Chart::getTotalAmount).toArray());
            request.setAttribute("pageTitle", "Biểu đồ tổng tiền theo năm");
        }
        // Lấy sản phẩm bán chạy nhất
           String bestSellingProductSql = "SELECT TOP 1 i.productName, SUM(i.price) AS price " +
                                "FROM orders o " +
                                "JOIN users u ON u.ID = o.userID " +
                                "JOIN OrderItems i ON o.orderID = i.orderID " +
                                "WHERE o.orderType = 1 AND u.storeID = " + storeID + " " +
                                "GROUP BY i.productName " +
                                "ORDER BY price DESC";

                Vector<BestSeller> bestSellingProduct = dao1.getProductSeller(bestSellingProductSql);
                request.setAttribute("bestSellingProduct", bestSellingProduct);

                // Lấy khách hàng mua nhiều nhất
                String bestCustomerSql = "SELECT TOP 1 c.name, SUM(i.price) AS price " +
                                          "FROM orders o " +
                                          "JOIN users u ON u.ID = o.userID " +
                                          "JOIN OrderItems i ON o.orderID = i.orderID " +
                                          "JOIN customers c ON o.customerID = c.customerID " +
                                          "WHERE o.orderType = 1 AND u.storeID = " + storeID + " " +
                                          "GROUP BY c.name " +
                                          "ORDER BY price DESC";

                Vector<BestSeller> bestCustomer = dao1.getCustomerSeller(bestCustomerSql);
                request.setAttribute("bestCustomer", bestCustomer);
        request.setAttribute("selectedAction", action); // Lưu hành động đã chọn

        RequestDispatcher dispth = request.getRequestDispatcher("order/chart.jsp");
        dispth.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Nếu cần xử lý POST, có thể thêm mã ở đây
    }

    @Override
    public String getServletInfo() {
        return "Servlet for displaying revenue charts";
    }
}