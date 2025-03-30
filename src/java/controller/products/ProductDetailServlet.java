package controller.products;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAO.DAOProduct;
import java.util.List;
import model.Products;
import model.Zones;

@WebServlet("/ProductDetail")
public class ProductDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Kiểm tra và parse productID an toàn
            String productIDParam = request.getParameter("productID");
            if (productIDParam == null || productIDParam.isEmpty()) {
                request.setAttribute("errorMessage", "Thiếu mã sản phẩm.");
                request.getRequestDispatcher("/dashboard/ListProducts").forward(request, response);
                return;
            }

            int productID = Integer.parseInt(productIDParam);

            DAOProduct daoProduct = new DAOProduct();
            Products product = daoProduct.getProductByID(productID);

            // Kiểm tra nếu sản phẩm không tồn tại
            if (product == null) {
                request.setAttribute("errorMessage", "Sản phẩm không tồn tại hoặc đã bị xóa.");
                request.getRequestDispatcher("ListProducts").forward(request, response);
                return;
            }

            // Lấy danh sách khu vực chứa sản phẩm
            List<Zones> zoneList = daoProduct.getZonesByProduct(productID);

            // Đặt dữ liệu vào request scope
            request.setAttribute("product", product);
            request.setAttribute("zoneList", zoneList);
            request.getRequestDispatcher("/dashboard/product_details.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Mã sản phẩm không hợp lệ.");
            request.getRequestDispatcher("ListProducts").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi tải thông tin sản phẩm: " + e.getMessage());
            request.getRequestDispatcher("ListProducts").forward(request, response);
        }
    }

}
