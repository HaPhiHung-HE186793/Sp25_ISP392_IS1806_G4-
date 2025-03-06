/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.order;

import DAO.DAOCustomers;
import model.Customers;
import DAO.DAOProduct;
import model.Customers;
import model.Products;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Admin
 */
public class SearchServlet extends HttpServlet {

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
            out.println("<title>Servlet SearchServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchServlet at " + request.getContextPath() + "</h1>");
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
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String keyword = request.getParameter("searchProduct");

        if (keyword != null && !keyword.trim().isEmpty()) {
            List<Products> products = DAOProduct.INSTANCE.searchProductsByName(keyword);

            if (products.isEmpty()) {
                out.println("<p>Không tìm thấy sản phẩm.</p>");
            } else {
                // Bạn có thể bọc toàn bộ danh sách trong 1 container lớn
                out.println("<div class='search-suggestions'>");

                for (Products product : products) {

                    // Lấy danh sách unitSizes từ bảng ProductUnits (đã trả về List<Integer>)
                    List<Integer> unitSizes = DAOProduct.INSTANCE.getProductUnitsByProductID(product.getProductID());

                    // Chuyển danh sách unitSizes thành chuỗi JavaScript Array
                    StringBuilder unitSizesStr = new StringBuilder("[");
                    for (int i = 0; i < unitSizes.size(); i++) {
                        unitSizesStr.append(unitSizes.get(i));
                        if (i < unitSizes.size() - 1) {
                            unitSizesStr.append(",");
                        }
                    }
                    unitSizesStr.append("]");
                    // Xuất HTML với unitSizes được truyền vào addProductToOrder()
                    out.println("<div class='product-item' onclick=\"addProductToOrder('"
                            + product.getProductID() + "','"
                            + product.getProductName() + "','"
                            + (int) product.getPrice() + "','"
                            + product.getQuantity() + "',"
                            + unitSizesStr.toString() + ")\">");
                    // Ảnh sản phẩm
                    out.println("<div class='product-image'>"
                            + "<img src='" + product.getImage() + "' alt='Product Image' />"
                            + "</div>");

                    // Container chứa nội dung
                    out.println("<div class='product-content'>");

                    // Hàng chứa tên, số lượng và giá
                    out.println("<div class='product-info'>");
                    out.println("<h3 class='product-name'>" + product.getProductName() + "</h3>");
                    out.println("<p class='product-quantity'>Số lượng: " + product.getQuantity() + "</p>");
                    out.println("<p class='product-price'>Giá: " + formatter.format(product.getPrice()) + "</p>");
                    out.println("</div>");

                    // Mô tả sản phẩm
                    out.println("<p class='product-description'>"
                            + (product.getDescription() != null ? product.getDescription() : "")
                            + "</p>");

                    out.println("</div>"); // đóng div .product-content
                    out.println("</div>"); // đóng div .product-item
                }

                out.println("</div>"); // đóng container lớn
            }
        }

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String searchPhone = request.getParameter("keyword");
        List<Customers> customers = DAOCustomers.INSTANCE.findByPhone(searchPhone);

        if (customers.isEmpty()) {
            out.println("<p>Không tìm thấy khach hang.</p>");
            out.println("<div class='customer-item add-new' onclick='openAddCustomerPopup()'>");
            out.println("<p>➕ Thêm khách hàng mới</p>");
            out.println("</div>");
        } else {
            // Bạn có thể bọc toàn bộ danh sách trong 1 container lớn

            out.println("<div class='search-suggestions'>");
            for (Customers customer : customers) {
                out.println("<div class='customer-item' onclick=\"selectCustomer('"
                        + customer.getName() + "', '"
                        + customer.getPhone() + "', '"
                        + customer.getTotalDebt() + "')\">");

                out.println("<h3>" + customer.getName() + "</h3>");
                out.println("<p>SĐT: " + customer.getPhone() + "</p>");

                out.println("</div>");
            }
            out.println("</div>");

        }

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
