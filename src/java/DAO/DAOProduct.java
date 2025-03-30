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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import model.ProductPriceHistory;
import model.Zones;

public class DAOProduct extends DBContext {

    public static DAOProduct INSTANCE = new DAOProduct();

    public List<ProductPriceHistory> getAllExportPriceHistory(int userId, String priceType) {
        List<ProductPriceHistory> historyList = new ArrayList<>();
        int storeId = getStoreIdByUserId(userId);

        String sql = """
        SELECT pph.productID, p.productName, pph.price, pph.priceType, pph.changedAt, u.userName AS changedBy
        FROM ProductPriceHistory pph
        JOIN products p ON pph.productID = p.productID
        JOIN users u ON pph.changedBy = u.ID
        WHERE p.storeID = ? AND pph.priceType = ?  -- Lọc theo priceType
        ORDER BY pph.changedAt DESC
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, storeId);
            ps.setString(2, priceType); // Truyền giá trị 'import' hoặc 'sell'
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    historyList.add(new ProductPriceHistory(
                            rs.getInt("productID"),
                            rs.getString("productName"),
                            rs.getDouble("price"),
                            rs.getString("priceType"),
                            rs.getString("changedAt"),
                            rs.getString("changedBy")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyList;
    }

    public List<ProductPriceHistory> getAllImportPriceHistory(int userId, String priceType) {
        List<ProductPriceHistory> historyList = new ArrayList<>();
        int storeId = getStoreIdByUserId(userId);

        String sql = """
        SELECT pph.productID, p.productName, pph.price, pph.priceType, pph.changedAt, 
               u.userName AS changedBy, c.name AS supplierName  -- Thêm tên nhà cung cấp
        FROM ProductPriceHistory pph
        JOIN products p ON pph.productID = p.productID
        JOIN users u ON pph.changedBy = u.ID
        LEFT JOIN customers c ON pph.supplierID = c.customerID  -- Liên kết với bảng customers
        WHERE p.storeID = ? AND pph.priceType = ?  -- Lọc theo priceType
        ORDER BY pph.changedAt DESC
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, storeId);
            ps.setString(2, priceType); // Truyền giá trị 'import' hoặc 'sell'
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    historyList.add(new ProductPriceHistory(
                            rs.getInt("productID"),
                            rs.getString("productName"),
                            rs.getDouble("price"),
                            rs.getString("priceType"),
                            rs.getString("changedAt"),
                            rs.getString("changedBy"),
                            rs.getString("supplierName") // Truyền tên nhà cung cấp vào đối tượng ProductPriceHistory
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyList;
    }

    public int getTotalHistoryRecords(String keyword, int userId, String startDate, String endDate) {
        int storeId = getStoreIdByUserId(userId);
        int totalRecords = 0;
        System.out.println(startDate);
        System.out.println(endDate);

        // Truy vấn SQL
        String sql = """
        SELECT COUNT(*) AS total
        FROM ProductPriceHistory pph
        JOIN products p ON pph.productID = p.productID
        WHERE p.storeID = ? 
          AND pph.priceType = 'import'
          AND (p.productName COLLATE SQL_Latin1_General_CP1_CI_AI LIKE ? OR ? = '')
          AND (pph.changedAt BETWEEN ? AND DATEADD(SECOND, -1, DATEADD(DAY, 1, ?)))
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, storeId);                       // Tham số storeID
            ps.setString(2, "%" + keyword + "%");        // Tham số keyword cho tìm kiếm tên sản phẩm
            ps.setString(3, keyword);                    // Để so sánh nếu keyword trống
            ps.setString(4, startDate);                  // Truyền startDate
            ps.setString(5, endDate);                    // Truyền endDate (cuối ngày)

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalRecords = rs.getInt("total");   // Trả về tổng số bản ghi
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi ra nếu có vấn đề với SQL
        }
        return totalRecords;
    }

    public int getTotalHistoryExportRecords(String keyword, int userId, String startDate, String endDate) {
        int storeId = getStoreIdByUserId(userId);
        int totalRecords = 0;

        String sql = """
        SELECT COUNT(*) AS total
        FROM ProductPriceHistory pph
        JOIN products p ON pph.productID = p.productID
        WHERE p.storeID = ? 
          AND pph.priceType = 'sell'
          AND p.productName COLLATE SQL_Latin1_General_CP1_CI_AI LIKE ?
          AND (pph.changedAt BETWEEN ? AND DATEADD(SECOND, -1, DATEADD(DAY, 1, ?)))  -- Bao trọn ngày cuối
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, storeId);                   // Tham số storeID
            ps.setString(2, "%" + keyword + "%");    // Tham số keyword cho tìm kiếm tên sản phẩm
            ps.setString(3, startDate);              // Tham số startDate (ngày bắt đầu)
            ps.setString(4, endDate);                // Tham số endDate (ngày kết thúc)
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalRecords = rs.getInt("total"); // Trả về tổng số bản ghi
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRecords;
    }

    public List<ProductPriceHistory> getImportPriceHistory(String keyword, int page, int recordsPerPage, int userId, String sortOrder, String startDate, String endDate) {
        int storeId = getStoreIdByUserId(userId);  // Lấy storeID theo userId
        List<ProductPriceHistory> historyList = new ArrayList<>();
        int offset = (page - 1) * recordsPerPage;

        if (keyword == null || keyword.trim().isEmpty()) {
            keyword = "";  // Xử lý keyword nếu null hoặc trống
        }

        String sql = """
        SELECT pph.historyID, pph.productID, p.productName, p.image, 
               pph.price, pph.priceType, pph.changedAt, u.userName AS changedByName, c.[name] AS supplierName
        FROM ProductPriceHistory pph
        JOIN products p ON pph.productID = p.productID
        JOIN users u ON pph.changedBy = u.ID
        LEFT JOIN customers c ON pph.supplierID = c.customerID  -- Join nhà cung cấp (nếu có)
        WHERE p.storeID = ?  -- Lọc theo storeID trong ProductPriceHistory
          AND pph.priceType = 'import'
          AND (p.productName COLLATE SQL_Latin1_General_CP1_CI_AI LIKE ? OR ? = '')
          AND (pph.changedAt BETWEEN ? AND DATEADD(SECOND, -1, DATEADD(DAY, 1, ?)))  -- Lọc thời gian trong khoảng startDate và endDate
        ORDER BY pph.changedAt %s
        OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
    """.formatted(sortOrder);

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, storeId);                       // Thêm tham số storeID
            ps.setString(2, "%" + keyword + "%");        // Lọc theo tên sản phẩm (nếu có)
            ps.setString(3, keyword);                    // Bỏ qua lọc tên sản phẩm nếu keyword rỗng
            ps.setString(4, startDate);                  // Lọc theo ngày bắt đầu
            ps.setString(5, endDate);                    // Lọc theo ngày kết thúc
            ps.setInt(6, offset);                        // Phân trang
            ps.setInt(7, recordsPerPage);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductPriceHistory history = new ProductPriceHistory(
                            rs.getInt("historyID"),
                            rs.getInt("productID"),
                            rs.getString("productName"),
                            rs.getString("image"),
                            rs.getDouble("price"),
                            rs.getString("priceType"),
                            rs.getString("changedAt"),
                            rs.getString("changedByName"),
                            rs.getString("supplierName") // Lấy tên nhà cung cấp (nếu có)
                    );
                    historyList.add(history);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyList;
    }

    public List<ProductPriceHistory> getExportPriceHistory(String keyword, int page, int recordsPerPage, int userId, String sortOrder, String startDate, String endDate) {
        int storeId = getStoreIdByUserId(userId);
        List<ProductPriceHistory> historyList = new ArrayList<>();
        int offset = (page - 1) * recordsPerPage;

        String sql = """
        SELECT pph.historyID, pph.productID, p.productName, p.image, 
               pph.price, pph.priceType, pph.changedAt, u.userName AS changedByName, c.[name] AS supplierName
        FROM ProductPriceHistory pph
        JOIN products p ON pph.productID = p.productID
        JOIN users u ON pph.changedBy = u.ID
        LEFT JOIN customers c ON pph.supplierID = c.customerID  -- Join nhà cung cấp (nếu có)
        WHERE p.storeID = ? 
          AND pph.priceType = 'sell'
          AND pph.storeID = ?  -- Lọc theo storeID
          AND p.productName COLLATE SQL_Latin1_General_CP1_CI_AI LIKE ?  
          AND (pph.changedAt BETWEEN ? AND DATEADD(SECOND, -1, DATEADD(DAY, 1, ?)))  -- Thêm toàn bộ ngày cuối cùng
        ORDER BY pph.changedAt %s
        OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
    """.formatted(sortOrder);

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, storeId);  // Lọc theo storeID của sản phẩm
            ps.setInt(2, storeId);  // Lọc theo storeID trong ProductPriceHistory
            ps.setString(3, "%" + keyword + "%");
            ps.setString(4, startDate);             // Thêm tham số lọc ngày bắt đầu
            ps.setString(5, endDate);               // Thêm tham số lọc ngày kết thúc
            ps.setInt(6, offset);
            ps.setInt(7, recordsPerPage);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductPriceHistory history = new ProductPriceHistory(
                            rs.getInt("historyID"),
                            rs.getInt("productID"),
                            rs.getString("productName"),
                            rs.getString("image"),
                            rs.getDouble("price"),
                            rs.getString("priceType"),
                            rs.getString("changedAt"),
                            rs.getString("changedByName")
                    );
                    historyList.add(history);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyList;
    }

    public boolean updateImportPrice(int productId, BigDecimal importPrice) {
        String sql = "UPDATE products SET importPrice = ? WHERE productID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, importPrice);
            stmt.setInt(2, productId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public BigDecimal getImportPrice(int productId) {
        String sql = "SELECT importPrice FROM products WHERE productID = ?";
        BigDecimal importPrice = BigDecimal.ZERO;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                importPrice = rs.getBigDecimal("importPrice");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return importPrice;
    }

    public Integer getStoreIdByUserId(int userId) {
        String sql = "SELECT storeID FROM users WHERE ID = ?";
        Integer storeId = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                storeId = rs.getInt("storeID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return storeId;
    }

    public boolean logPriceChange(int productId, BigDecimal newPrice, String priceType, int userId, Integer supplierID) {
        int storeId = getStoreIdByUserId(userId);

        String sql = "INSERT INTO ProductPriceHistory (productID, price, priceType, changedBy,storeID,supplierID) VALUES (?, ?, ?, ?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.setBigDecimal(2, newPrice);
            stmt.setString(3, priceType); // 'import' hoặc 'sell'
            stmt.setInt(4, userId);
            stmt.setInt(5, storeId);
            if (supplierID == null) {
                stmt.setNull(6, java.sql.Types.INTEGER);  // Chèn NULL nếu không có supplierID
            } else {
                stmt.setInt(6, supplierID);  // Chèn giá trị supplierID bình thường nếu có
            }

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu ghi thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi
    }

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

    public List<String> getZonesByProductID(int productID) {
        List<String> zones = new ArrayList<>();
        String sql = "SELECT zoneName FROM zones "
                + "WHERE productID = ? AND isDelete = 0 "
                + "ORDER BY zoneName ASC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                zones.add(rs.getString("zoneName")); // Thêm từng zoneName vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return zones; // Trả về danh sách các tên kho
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

    public boolean exportProductQuantity(int productID, int quantitySold) {
        String sql = "UPDATE products SET quantity = quantity - ? WHERE productID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantitySold);
            ps.setInt(2, productID);

            int affectedRows = ps.executeUpdate(); // Số dòng bị ảnh hưởng

            return affectedRows > 0; // Nếu có ít nhất 1 dòng bị ảnh hưởng -> thành công

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Có lỗi -> trả về false
        }
    }

    public boolean importProductQuantity(int productID, int quantityAdded) {
        String sql = "UPDATE products SET quantity = quantity + ? WHERE productID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantityAdded);
            ps.setInt(2, productID);

            int affectedRows = ps.executeUpdate(); // Số dòng bị ảnh hưởng

            return affectedRows > 0; // Nếu có ít nhất 1 dòng bị ảnh hưởng -> thành công

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Có lỗi -> trả về false
        }
    }

    // Phương thức tìm kiếm sản phẩm theo tên (hỗ trợ tìm kiếm gần đúng)
    public List<Products> searchProductsByName(String keyword, int userId) {
        int storeId = getStoreIdByUserId(userId);

        List<Products> productsList = new ArrayList<>();
        String sql = "SELECT * FROM products "
                + "WHERE productName COLLATE Vietnamese_CI_AI LIKE ? "
                + "AND isDelete = 0 AND storeID = ? ";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Loại bỏ dấu trước khi tìm kiếm
            String normalizedKeyword = removeVietnameseAccent(keyword.trim().toLowerCase());
            ps.setString(1, "%" + normalizedKeyword + "%");  // Tìm kiếm gần đúng
            ps.setInt(2, storeId);

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

    public boolean isProductNameExists(String productName, int storeID) {
        String sql = "SELECT COUNT(*) FROM products WHERE productName = ? AND storeID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productName);
            ps.setInt(2, storeID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
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
        int productID = -1; // Giá trị mặc định nếu insert thất bại
        String sql = "INSERT INTO products (productName, description, price, quantity, image, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, storeID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Sử dụng RETURN_GENERATED_KEYS để lấy ID vừa được tạo
            PreparedStatement pre = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
            pre.setInt(12, product.getStoreID()); // Thêm storeID vào truy vấn

            int affectedRows = pre.executeUpdate();

            // Kiểm tra xem có bản ghi nào được thêm không
            if (affectedRows > 0) {
                ResultSet rs = pre.getGeneratedKeys();
                if (rs.next()) {
                    productID = rs.getInt(1); // Lấy productID vừa được tạo
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productID; // Trả về ID của sản phẩm vừa thêm
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

    public List<Products> listAll1(int storeID) {
        List<Products> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE storeID = ?"; // Lọc theo storeID
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, storeID); // Gán giá trị storeID vào câu lệnh SQL
            ResultSet rs = ps.executeQuery();
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

    public Products getProductByID(int productID) {
        Products product = null;
        String sql = "SELECT p.productID, p.productName, p.description, p.price, p.quantity, p.image, p.isDelete, "
                + "z.zoneName "
                + // ❌ Bỏ productQuantity
                "FROM products p "
                + "LEFT JOIN zones z ON p.productID = z.productID "
                + "WHERE p.productID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productID);
            try (ResultSet rs = ps.executeQuery()) {
                StringBuilder zoneNames = new StringBuilder();

                while (rs.next()) {
                    if (product == null) { // Chỉ khởi tạo đối tượng sản phẩm một lần
                        product = new Products();
                        product.setProductID(rs.getInt("productID"));
                        product.setProductName(rs.getString("productName"));
                        product.setDescription(rs.getString("description"));
                        product.setPrice(rs.getDouble("price"));
                        product.setQuantity(rs.getInt("quantity"));
                        product.setImage(rs.getString("image"));
                        product.setIsDelete(rs.getBoolean("isDelete"));
                    }

                    // Xử lý danh sách zone
                    String zoneName = rs.getString("zoneName");
                    if (zoneName != null) {
                        if (zoneNames.length() > 0) {
                            zoneNames.append(", ");
                        }
                        zoneNames.append(zoneName);
                    }
                }

                if (product != null) {
                    product.setZoneName(zoneNames.toString()); // Lưu danh sách khu vực vào sản phẩm
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    //hàm lấy tổng đơn hàng ngày hôm  nay
    public int getTotalOrdersToday(int storeId) {
        int totalOrders = 0;
        String sql = """
        SELECT COUNT(*) AS total 
        FROM orders 
        WHERE storeId = ? 
          AND CAST(createAt AS DATE) = CAST(GETDATE() AS DATE)
    """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, storeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    totalOrders = rs.getInt("total");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return totalOrders;
    }

    //hàm lấy tổng doanh thu ngày hôm nay
    public double getTotalRevenueToday(int storeId) {
        double totalRevenue = 0;
        String sql = """
        SELECT SUM(totalAmount) AS totalRevenue 
        FROM orders 
        WHERE storeId = ? 
          AND CAST(createAt AS DATE) = CAST(GETDATE() AS DATE)
    """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, storeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getBigDecimal("totalRevenue") != null) {
                    totalRevenue = rs.getDouble("totalRevenue");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return totalRevenue;
    }

    //hàm lấy tổng doanh thu tháng này
    public double getTotalRevenueThisMonth(int storeId) {
        double totalRevenue = 0;
        String sql = """
        SELECT SUM(totalAmount) AS totalRevenue 
        FROM orders 
        WHERE storeId = ? and orderType = 1
          AND MONTH(createAt) = MONTH(GETDATE()) 
          AND YEAR(createAt) = YEAR(GETDATE())
    """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, storeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getBigDecimal("totalRevenue") != null) {
                    totalRevenue = rs.getDouble("totalRevenue");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return totalRevenue;
    }
    // Hàm lấy tổng doanh thu của tháng trước

    public double getTotalRevenueLastMonth(int storeId) {
        double totalRevenue = 0;
        String sql = """
        SELECT SUM(totalAmount) AS totalRevenue 
        FROM orders 
        WHERE storeId = ? and orderType = 1
          AND MONTH(createAt) = MONTH(DATEADD(MONTH, -1, GETDATE())) 
          AND YEAR(createAt) = YEAR(DATEADD(MONTH, -1, GETDATE()))
    """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, storeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getBigDecimal("totalRevenue") != null) {
                    totalRevenue = rs.getDouble("totalRevenue");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return totalRevenue;
    }

    // Hàm lấy tổng doanh thu 7 ngày qua
    public double getTotalRevenueLast7Days(int storeId) {
        double totalRevenue = 0;
        String sql = """
        SELECT SUM(totalAmount) AS totalRevenue 
        FROM orders 
        WHERE storeId = ? and orderType = 1
          AND createAt >= DATEADD(DAY, -7, GETDATE())
    """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, storeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getBigDecimal("totalRevenue") != null) {
                    totalRevenue = rs.getDouble("totalRevenue");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return totalRevenue;
    }

    public int getBestSellingProduct() {
        int bestSellingProductId = -1;
        String sql = """
        WITH MonthlySales AS (
            SELECT TOP 1 oi.productID, SUM(oi.quantity) AS totalSold
            FROM orders o
            JOIN orderitems oi ON o.orderID = oi.orderID
            WHERE YEAR(o.createAt) = YEAR(GETDATE()) AND MONTH(o.createAt) = MONTH(GETDATE())
            GROUP BY oi.productID
            ORDER BY totalSold DESC
        ),
        LastMonthSales AS (
            SELECT TOP 1 oi.productID, SUM(oi.quantity) AS totalSold
            FROM orders o
            JOIN orderitems oi ON o.orderID = oi.orderID
            WHERE YEAR(o.createAt) = YEAR(DATEADD(MONTH, -1, GETDATE())) 
              AND MONTH(o.createAt) = MONTH(DATEADD(MONTH, -1, GETDATE()))
            GROUP BY oi.productID
            ORDER BY totalSold DESC
        )
        SELECT productID FROM MonthlySales
        UNION
        SELECT productID FROM LastMonthSales
        """;

        try (Statement state = conn.createStatement(); ResultSet rs = state.executeQuery(sql)) {
            if (rs.next()) {
                bestSellingProductId = rs.getInt("productID");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return bestSellingProductId;
    }

    //hàm lấy 3 sản phẩm bán chạy nhất tháng
    public String[] getTop3BestSellingRice(int storeId) {
        String[] topSellingRice = new String[3];
        String sql = """
        WITH MonthlySales AS (
            SELECT TOP 3 oi.productID, p.productName, SUM(oi.quantity) AS totalSold
            FROM OrderItems oi
            JOIN orders o ON oi.orderID = o.orderID
            JOIN products p ON oi.productID = p.productID
            WHERE o.storeId = ? 
              AND MONTH(o.createAt) = MONTH(GETDATE()) 
              AND YEAR(o.createAt) = YEAR(GETDATE())
            GROUP BY oi.productID, p.productName
            ORDER BY totalSold DESC
        )
        SELECT * FROM MonthlySales
        UNION ALL
        SELECT TOP 3 oi.productID, p.productName, SUM(oi.quantity) AS totalSold
        FROM OrderItems oi
        JOIN orders o ON oi.orderID = o.orderID
        JOIN products p ON oi.productID = p.productID
        WHERE o.storeId = ?
          AND MONTH(o.createAt) = MONTH(DATEADD(MONTH, -1, GETDATE())) 
          AND YEAR(o.createAt) = YEAR(DATEADD(MONTH, -1, GETDATE()))
          AND NOT EXISTS (SELECT 3 FROM MonthlySales) 
        GROUP BY oi.productID, p.productName
        ORDER BY totalSold DESC;
    """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, storeId);
            pstmt.setInt(2, storeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                int i = 0;
                while (rs.next() && i < 3) {
                    topSellingRice[i] = rs.getString("productName");
                    i++;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return topSellingRice;
    }

    //lấy 3 tổng đơn bán chạy nhất tháng
    public String[] getTop3TotalSold(int storeId) {
        String[] totalSold = new String[3];
        String sql = """
        WITH MonthlySales AS (
            SELECT TOP 3 oi.productID, p.productName, SUM(oi.quantity) AS totalSold
            FROM OrderItems oi
            JOIN orders o ON oi.orderID = o.orderID
            JOIN products p ON oi.productID = p.productID
            WHERE o.storeId = ? 
              AND MONTH(o.createAt) = MONTH(GETDATE()) 
              AND YEAR(o.createAt) = YEAR(GETDATE())
            GROUP BY oi.productID, p.productName
            ORDER BY totalSold DESC
        )
        SELECT * FROM MonthlySales
        UNION ALL
        SELECT TOP 3 oi.productID, p.productName, SUM(oi.quantity) AS totalSold
        FROM OrderItems oi
        JOIN orders o ON oi.orderID = o.orderID
        JOIN products p ON oi.productID = p.productID
        WHERE o.storeId = ?
          AND MONTH(o.createAt) = MONTH(DATEADD(MONTH, -1, GETDATE())) 
          AND YEAR(o.createAt) = YEAR(DATEADD(MONTH, -1, GETDATE()))
          AND NOT EXISTS (SELECT 3 FROM MonthlySales) 
        GROUP BY oi.productID, p.productName
        ORDER BY totalSold DESC;
    """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, storeId);
            pstmt.setInt(2, storeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                int index = 0;
                while (rs.next() && index < 3) {
                    totalSold[index++] = rs.getString("totalSold");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return totalSold;
    }

    //lấy tổng doanh thu của 3 sản phẩm bán chạy nhất tháng
    public String[] getTop3TotalRevenue(int storeId) {
        String[] totalRevenue = new String[3];
        String sql = """
        WITH MonthlySales AS (
            SELECT TOP 3 oi.productID, p.productName, SUM(oi.price) AS totalRevenue
            FROM OrderItems oi
            JOIN orders o ON oi.orderID = o.orderID
            JOIN products p ON oi.productID = p.productID
            WHERE o.storeId = ? and orderType = 1
              AND MONTH(o.createAt) = MONTH(GETDATE()) 
              AND YEAR(o.createAt) = YEAR(GETDATE())
            GROUP BY oi.productID, p.productName
            ORDER BY totalRevenue DESC
        )
        SELECT * FROM MonthlySales
        UNION ALL
        SELECT TOP 3 oi.productID, p.productName, SUM(oi.quantity * oi.price) AS totalRevenue
        FROM OrderItems oi
        JOIN orders o ON oi.orderID = o.orderID
        JOIN products p ON oi.productID = p.productID
        WHERE o.storeId = ?
          AND MONTH(o.createAt) = MONTH(DATEADD(MONTH, -1, GETDATE())) 
          AND YEAR(o.createAt) = YEAR(DATEADD(MONTH, -1, GETDATE()))
          AND NOT EXISTS (SELECT 3 FROM MonthlySales) 
        GROUP BY oi.productID, p.productName
        ORDER BY totalRevenue DESC;
    """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, storeId);
            pstmt.setInt(2, storeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                int index = 0;
                while (rs.next() && index < 3) {
                    totalRevenue[index++] = rs.getString("totalRevenue");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return totalRevenue;
    }

    // Lấy tổng doanh thu theo ngày, giờ hoặc ngày trong tuần của hôm nay này
    public Map<String, Double> getRevenueByViewType(String viewType, int storeId) {
        Map<String, Double> revenueData = new LinkedHashMap<>();
        String sql = null;

        if ("hour".equals(viewType)) {
            sql = "SELECT DATEPART(HOUR, createAt) AS hour, SUM(totalAmount) AS revenue "
                    + "FROM orders "
                    + "WHERE CAST(createAt AS DATE) = CAST(GETDATE() AS DATE) AND storeId = ? "
                    + "GROUP BY DATEPART(HOUR, createAt) "
                    + "ORDER BY DATEPART(HOUR, createAt)";
        }
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, storeId); // Gán giá trị storeId vào câu lệnh SQL
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    revenueData.put(rs.getString(1), rs.getDouble("revenue"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return revenueData;
    }

    // Lấy tổng doanh thu theo ngày, giờ hoặc ngày trong tuần của tháng hiện tại
    public Map<String, Double> getRevenueCurrentMonthByViewType(String viewType, int storeId) {
        Map<String, Double> revenueData = new LinkedHashMap<>();
        String sql;

        if ("weekday".equals(viewType)) {
            sql = """
            SELECT DATENAME(WEEKDAY, createAt) AS day, SUM(totalAmount) AS revenue
            FROM orders
            WHERE MONTH(createAt) = MONTH(GETDATE())
              AND YEAR(createAt) = YEAR(GETDATE())
              AND storeId = ?
            GROUP BY DATENAME(WEEKDAY, createAt)
            ORDER BY MIN(createAt)
        """;
        } else if ("day".equals(viewType)) {
            sql = """
            SELECT DAY(createAt) AS day, SUM(totalAmount) AS revenue
            FROM orders
            WHERE MONTH(createAt) = MONTH(GETDATE())
              AND YEAR(createAt) = YEAR(GETDATE())
              AND storeId = ?
            GROUP BY DAY(createAt)
            ORDER BY DAY(createAt)
        """;
        } else { // "hour"
            sql = """
            SELECT DATEPART(HOUR, createAt) AS hour, SUM(totalAmount) AS revenue
            FROM orders
            WHERE MONTH(createAt) = MONTH(GETDATE())
              AND YEAR(createAt) = YEAR(GETDATE())
              AND storeId = ?
            GROUP BY DATEPART(HOUR, createAt)
            ORDER BY DATEPART(HOUR, createAt)
        """;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, storeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    revenueData.put(rs.getString(1), rs.getDouble("revenue"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return revenueData;
    }

    // Lấy tổng doanh thu theo ngày, giờ hoặc ngày trong tuần của tháng trước
    public Map<String, Double> getRevenueLastMonthByViewType(String viewType, int storeId) {
        Map<String, Double> revenueData = new LinkedHashMap<>();
        String sql;

        if ("weekday".equals(viewType)) {
            sql = """
            SELECT DATENAME(WEEKDAY, createAt) AS day, SUM(totalAmount) AS revenue
            FROM orders
            WHERE MONTH(createAt) = MONTH(DATEADD(MONTH, -1, GETDATE()))
              AND YEAR(createAt) = YEAR(DATEADD(MONTH, -1, GETDATE()))
              AND storeId = ? and orderType = 1
            GROUP BY DATENAME(WEEKDAY, createAt)
            ORDER BY MIN(createAt)
        """;
        } else if ("day".equals(viewType)) {
            sql = """
            SELECT DAY(createAt) AS day, SUM(totalAmount) AS revenue
            FROM orders
            WHERE MONTH(createAt) = MONTH(DATEADD(MONTH, -1, GETDATE()))
              AND YEAR(createAt) = YEAR(DATEADD(MONTH, -1, GETDATE()))
              AND storeId = ? and orderType = 1
            GROUP BY DAY(createAt)
            ORDER BY DAY(createAt)
        """;
        } else { // "hour"
            sql = """
            SELECT DATEPART(HOUR, createAt) AS hour, SUM(totalAmount) AS revenue
            FROM orders
            WHERE CAST(createAt AS DATE) BETWEEN 
                  CAST(DATEADD(MONTH, -1, GETDATE()) AS DATE) 
                  AND CAST(EOMONTH(DATEADD(MONTH, -1, GETDATE())) AS DATE)
              AND storeId = ? and orderType = 1
            GROUP BY DATEPART(HOUR, createAt)
            ORDER BY DATEPART(HOUR, createAt)
        """;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, storeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    revenueData.put(rs.getString(1), rs.getDouble("revenue"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return revenueData;
    }

    // Lấy tổng doanh thu theo ngày, giờ hoặc ngày trong tuần của 7 ngày qua
    public Map<String, Double> getRevenueLast7DaysByViewType(String viewType, int storeId) {
        Map<String, Double> revenueData = new LinkedHashMap<>();
        String sql;

        if ("weekday".equals(viewType)) {
            sql = """
            SELECT DATENAME(WEEKDAY, createAt) AS day, SUM(totalAmount) AS revenue
            FROM orders
            WHERE createAt >= DATEADD(DAY, -6, CAST(GETDATE() AS DATE))
              AND storeId = ? and orderType = 1
            GROUP BY DATENAME(WEEKDAY, createAt)
            ORDER BY MIN(createAt)
        """;
        } else if ("day".equals(viewType)) {
            sql = """
            SELECT FORMAT(createAt, 'yyyy-MM-dd') AS day, SUM(totalAmount) AS revenue
            FROM orders
            WHERE createAt >= DATEADD(DAY, -6, CAST(GETDATE() AS DATE))
              AND storeId = ? and orderType = 1
            GROUP BY FORMAT(createAt, 'yyyy-MM-dd')
            ORDER BY MIN(createAt)
        """;
        } else { // "hour"
            sql = """
            SELECT DATEPART(HOUR, createAt) AS hour, SUM(totalAmount) AS revenue
            FROM orders
            WHERE createAt >= DATEADD(DAY, -6, CAST(GETDATE() AS DATE))
              AND storeId = ? and orderType = 1
            GROUP BY DATEPART(HOUR, createAt)
            ORDER BY DATEPART(HOUR, createAt)
        """;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, storeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    revenueData.put(rs.getString(1), rs.getDouble("revenue"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return revenueData;
    }

    public double getRevenueChangePercentage(int storeId) {
        String sql = "SELECT period, SUM(totalAmount) AS revenue FROM (\n"
                + "                    SELECT 'current' AS period, totalAmount \n"
                + "                    FROM orders WHERE MONTH(createAt) = MONTH(GETDATE()) \n"
                + "                  AND YEAR(createAt) = YEAR(GETDATE()) AND storeId = ? and orderType = 1 \n"
                + "                    UNION ALL \n"
                + "                 SELECT 'previous' AS period, totalAmount \n"
                + "                   FROM orders WHERE MONTH(createAt) = MONTH(DATEADD(MONTH, -1, GETDATE())) \n"
                + "                  AND YEAR(createAt) = YEAR(DATEADD(MONTH, -1, GETDATE())) AND storeId = ? and orderType = 1\n"
                + "                ) AS revenue_data GROUP BY period";

        double currentRevenue = 0, previousRevenue = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, storeId);
            pstmt.setInt(2, storeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String period = rs.getString("period");
                    double revenue = rs.getDouble("revenue");
                    if ("current".equals(period)) {
                        currentRevenue = revenue;
                    } else if ("previous".equals(period)) {
                        previousRevenue = revenue;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if (previousRevenue == 0) {
            if (currentRevenue > 0) {
                return 100.0; // Doanh thu tháng trước là 0, tháng này có doanh thu => tăng 100%
            } else {
                return 0.0; // Cả hai tháng đều không có doanh thu => không thay đổi
            }
        }

        return ((currentRevenue - previousRevenue) / previousRevenue) * 100;
    }

    public List<Products> searchAndFilterProducts(String nameKeyword, String descKeyword, String stockStatus, String sortPrice, int storeID) {
        List<Products> productsList = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE isDelete = 0 AND storeID = ?";

        if (nameKeyword != null && !nameKeyword.isEmpty()) {
            sql += " AND LOWER(productName) LIKE LOWER(?)";
        }
        if (descKeyword != null && !descKeyword.isEmpty()) {
            sql += " AND LOWER(description) LIKE LOWER(?)";
        }
        if (stockStatus != null && !stockStatus.isEmpty()) {
            if ("available".equals(stockStatus)) {
                sql += " AND quantity > 0";
            } else if ("outofstock".equals(stockStatus)) {
                sql += " AND quantity = 0";
            }
        }
        if ("asc".equals(sortPrice)) {
            sql += " ORDER BY price ASC";
        } else if ("desc".equals(sortPrice)) {
            sql += " ORDER BY price DESC";
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int paramIndex = 1;
            ps.setInt(paramIndex++, storeID);

            if (nameKeyword != null && !nameKeyword.isEmpty()) {
                ps.setString(paramIndex++, "%" + nameKeyword.toLowerCase() + "%");
            }
            if (descKeyword != null && !descKeyword.isEmpty()) {
                ps.setString(paramIndex++, "%" + descKeyword.toLowerCase() + "%");
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

    public List<Zones> getZonesByProduct(int productID) {
        List<Zones> zones = new ArrayList<>();
        String sql = "SELECT zoneID, zoneName, description "
                + "FROM zones "
                + "WHERE productID = ?";  // Lấy danh sách khu vực theo productID

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Zones zone = new Zones();
                zone.setZoneID(rs.getInt("zoneID"));
                zone.setZoneName(rs.getString("zoneName"));
                zone.setNavigation(rs.getString("description"));
                zones.add(zone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return zones;
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

    public boolean insertProductUnit(int productID, int unitSize) {
        String sql = "INSERT INTO ProductUnits (productID, unitSize) VALUES (?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productID);
            ps.setInt(2, unitSize);
            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getProductUnitSize(int productID) {
        String sql = "SELECT unitSize FROM ProductUnits WHERE productID = ?";
        StringBuilder unitSizes = new StringBuilder();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (unitSizes.length() > 0) {
                    unitSizes.append(", "); // Ngăn cách bằng dấu ", "
                }
                unitSizes.append(rs.getString("unitSize"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unitSizes.toString(); // Trả về danh sách unitSize dạng chuỗi
    }

    public boolean updateUnitSizeByProductID(int productID, List<Integer> newUnitSizes) {
        String deleteSql = "DELETE FROM ProductUnits WHERE productID = ?";
        String insertSql = "INSERT INTO ProductUnits (productID, unitSize) VALUES (?, ?)";

        try (PreparedStatement deletePs = conn.prepareStatement(deleteSql)) {
            deletePs.setInt(1, productID);
            deletePs.executeUpdate(); // Xóa tất cả unitSize cũ
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
            for (int unitSize : newUnitSizes) {
                insertPs.setInt(1, productID);
                insertPs.setInt(2, unitSize);
                insertPs.addBatch(); // Gom vào batch để tối ưu hóa
            }
            insertPs.executeBatch(); // Thực thi batch insert
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
        Map<String, Double> revenueByViewType = dao.getRevenueByViewType("hour", 3);
        String[] a = dao.getTop3TotalRevenue(2);
        double ab = dao.getRevenueChangePercentage(2);
        System.out.println(ab);
    }
}
