/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author ADMIN
 */
import DAL.DBContext;
import java.math.BigDecimal;
import model.Products;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class DAOProduct extends DBContext {

    public static DAOProduct INSTANCE = new DAOProduct();

    public int getProductQuantity(int productId) {
        int quantity = 0;
        String sql = "SELECT quantity FROM products WHERE productID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                quantity = rs.getInt("quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quantity;
    }

    public Double getProductPrice(int productId) {
        String sql = "SELECT price FROM products WHERE productID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("price");
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy giá sản phẩm productID = " + productId + ": " + e.getMessage());
        }

        return 0.0; // Nếu lỗi hoặc không có sản phẩm thì trả về 0
    }

    public List<Integer> getProductUnitsByProductID(int productID) {
        List<Integer> unitSizes = new ArrayList<>();
        String sql = "SELECT unitSize FROM ProductUnits WHERE productID = ? ORDER BY unitSize ASC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                unitSizes.add(rs.getInt("unitSize"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return unitSizes;
    }

    public void exportProductQuantity(int productID, int quantitySold) {
        String sql = "UPDATE products SET quantity = quantity - ? WHERE productID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantitySold);
            ps.setInt(2, productID);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void importProductQuantity(int productID, int quantityAdded) {
        String sql = "UPDATE products SET quantity = quantity + ? WHERE productID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantityAdded);
            ps.setInt(2, productID);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Phương thức tìm kiếm sản phẩm theo tên (hỗ trợ tìm kiếm gần đúng)
    public List<Products> searchProductsByName(String keyword) {
        List<Products> productsList = new ArrayList<>();
        String sql = "SELECT * FROM products "
                + "WHERE productName COLLATE Vietnamese_CI_AI LIKE ? "
                + "AND isDelete = 0";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Loại bỏ dấu trước khi tìm kiếm
            String normalizedKeyword = removeVietnameseAccent(keyword.trim().toLowerCase());
            ps.setString(1, "%" + normalizedKeyword + "%");  // Tìm kiếm gần đúng

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Products product = new Products();
                    product.setProductID(rs.getInt("productID"));
                    product.setProductName(rs.getString("productName"));
                    product.setDescription(rs.getString("description"));
                    product.setPrice(rs.getDouble("price"));
                    product.setQuantity(rs.getInt("quantity"));
                    product.setImage(rs.getString("image"));

                    product.setCreateAt(rs.getString("createAt"));
                    product.setUpdateAt(rs.getString("updateAt"));
                    product.setCreateBy(rs.getInt("createBy"));
                    product.setIsDelete(rs.getBoolean("isDelete"));
                    product.setDeleteAt(rs.getString("deleteAt"));
                    product.setDeleteBy(rs.getInt("deleteBy"));

                    productsList.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productsList;
    }

    public static String removeVietnameseAccent(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(str).replaceAll("")
                .replaceAll("đ", "d")
                .replaceAll("Đ", "D");
    }

    public List<Products> searchProducts(String keyword) {
        List<Products> productsList = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE isDelete = 0";

        if (keyword != null && !keyword.isEmpty()) {
            sql += " AND (LOWER(productName) LIKE LOWER(?) OR LOWER(description) LIKE LOWER(?))";
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (keyword != null && !keyword.isEmpty()) {
                String searchKeyword = "%" + keyword.toLowerCase() + "%";
                ps.setString(1, searchKeyword);
                ps.setString(2, searchKeyword);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Products product = new Products();
                    product.setProductID(rs.getInt("productID"));
                    product.setProductName(rs.getString("productName"));
                    product.setDescription(rs.getString("description"));
                    product.setPrice(rs.getDouble("price"));
                    product.setQuantity(rs.getInt("quantity"));
                    product.setImage(rs.getString("image"));
                    product.setCreateAt(rs.getString("createAt"));
                    product.setUpdateAt(rs.getString("updateAt"));
                    product.setCreateBy(rs.getInt("createBy"));
                    product.setIsDelete(rs.getBoolean("isDelete"));
                    product.setDeleteAt(rs.getString("deleteAt"));
                    product.setDeleteBy(rs.getInt("deleteBy"));

                    productsList.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productsList;
    }
//    public Vector<products> getProducts(String sql) {
//        Vector<products> vector = new Vector<products>();
//        try {
//            Statement state = conn.createStatement();
//            ResultSet rs = state.executeQuery(sql);
//            while (rs.next()) {
//                int productID = rs.getInt("productID");
//                String productName = rs.getString("productName");
//                String description = rs.getString("description");
//                double price = rs.getDouble("price");
//                int quantity = rs.getInt("quantity");
//                String image = rs.getString("image");
//                String createAt = rs.getString("createAt");
//                String updateAt = rs.getString("updateAt");
//                int createBy = rs.getInt("createBy");
//                Boolean isDelete = rs.getBoolean("isDelete");
//                String deleteAt = rs.getString("deleteAt");
//                int deleteBy = rs.getInt("deleteBy");
//
//                products product = new products(productID, productName, description, price, quantity, image, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
//                vector.add(product);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return vector;
//    }

    public List<Products> getProducts(String sql) {
        List<Products> list = new ArrayList<>();
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int productID = rs.getInt("productID");
                String productName = rs.getString("productName");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String image = rs.getString("image");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");

                Products product = new Products(productID, productName, description, price, quantity, image, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
                list.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public int removeProduct(int productID) {
        int n = 0;
        String sql = "DELETE FROM products WHERE productID=?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, productID);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public boolean updateProduct(int productId, String productName, String description, double price, String image, String updateAt) {
        String sql = "UPDATE Products SET productName = ?, description = ?, price = ?, image = ?, updateAt = ? WHERE productID = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, productName);
            statement.setString(2, description);
            statement.setDouble(3, price);
            statement.setString(4, image);

            // Giữ updateAt là LocalDate nhưng lưu vào DB đúng cách
            statement.setString(5, updateAt);

            statement.setInt(6, productId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            Logger.getLogger(DAOProduct.class.getName()).log(Level.SEVERE, "Error updating product", e);
        }
        return false;
    }

    public boolean isProductNameExists(String productName) {
        String sql = "SELECT COUNT(*) FROM Products WHERE productName = ?"; // Assuming 'Products' is your table name
        try ( // Your DBConnect class
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, productName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Return true if count > 0 (product exists)
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately (log it, etc.)
        }
        return false; // Return false if there's an error or product doesn't exist
    }

    public int updateIsDelete(int productID, boolean isDelete) {
        int n = 0;
        String sql = "UPDATE products SET isDelete=? WHERE productID=?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) { // Try-with-resources
            pre.setBoolean(1, isDelete);
            pre.setInt(2, productID);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int insertProduct(Products product) {
        int n = 0;
        String sql = "INSERT INTO products (productName, description, price, quantity, image, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, product.getProductName());
            pre.setString(2, product.getDescription());
            pre.setDouble(3, product.getPrice());
            pre.setInt(4, product.getQuantity());
            pre.setString(5, product.getImage());
            pre.setString(6, product.getCreateAt());
            pre.setString(7, product.getUpdateAt());
            pre.setInt(8, product.getCreateBy());
            pre.setBoolean(9, product.isIsDelete());
            pre.setString(10, product.getDeleteAt());
            pre.setInt(11, product.getDeleteBy());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

//    public void listAll() {
//        String sql = "SELECT * FROM products"; // Cập nhật tên bảng
//        try {
//            Statement state = conn.createStatement();
//            ResultSet rs = state.executeQuery(sql);
//            while (rs.next()) {
//                int productID = rs.getInt("productID");
//                String productName = rs.getString("productName");
//                String description = rs.getString("description");
//                double price = rs.getDouble("price");
//                int quantity = rs.getInt("quantity");
//                String image = rs.getString("image");
//                String createAt = rs.getString("createAt");
//                String updateAt = rs.getString("updateAt");
//                int createBy = rs.getInt("createBy");
//                Boolean isDelete = rs.getBoolean("isDelete");
//                String deleteAt = rs.getString("deleteAt");
//                int deleteBy = rs.getInt("deleteBy");
//
//                Products product = new Products(productID, productName, description, price, quantity, image, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
//                System.out.println(product);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
    public List<Products> listAll() {
        List<Products> list = new ArrayList<>();
        String sql = "SELECT * FROM products"; // Cập nhật tên bảng
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int productID = rs.getInt("productID");
                String productName = rs.getString("productName");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String image = rs.getString("image");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");

                Products product = new Products(productID, productName, description, price, quantity, image, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
                list.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public Products getProductById(int productID) {
        Products product = null;
        String sql = "SELECT * FROM Products WHERE productID = ? AND isDelete = 0";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    product = new Products();
                    product.setProductID(rs.getInt("productID"));
                    product.setProductName(rs.getString("productName"));
                    product.setDescription(rs.getString("description"));
                    product.setPrice(rs.getDouble("price"));
                    product.setQuantity(rs.getInt("quantity"));
                    product.setImage(rs.getString("image"));
                    product.setCreateAt(rs.getString("createAt"));
                    product.setUpdateAt(rs.getString("updateAt"));
                    product.setCreateBy(rs.getInt("createBy"));
                    product.setIsDelete(rs.getBoolean("isDelete"));
                    product.setDeleteAt(rs.getString("deleteAt"));
                    product.setDeleteBy(rs.getInt("deleteBy"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public static void main(String[] args) {
        DAOProduct dao = new DAOProduct();

//        // 1. Thêm một sản phẩm mới
//        products newProduct = new products( "Sản phẩm D", "Mô tả sản phẩm A", 100.0, 10, "imageA.jpg", "2023-01-01", "2023-01-01", 1, false, null, 0);
//        int insertResult = dao.insertProduct(newProduct);
//        System.out.println("Insert result: " + insertResult);
//        // 2. Cập nhật thông tin sản phẩm
//        products productToUpdate = new products(2, "Sản phẩm B Phụ", "Mô tả sản phẩm B", 150.0, 20, "imageB.jpg", "2023-01-02", "2023-01-02",1, false, null, 0);
//        int updateResult = dao.updateProduct(productToUpdate);
//        System.out.println("Update result: " + updateResult);
//
//        // 3. Xóa một sản phẩm
//        int removeResult = dao.removeProduct(4); // Giả sử ID của sản phẩm cần xóa
//        System.out.println("Remove result: " + removeResult);
        // 4. Liệt kê tất cả sản phẩm
        dao.listAll();
    }
}
