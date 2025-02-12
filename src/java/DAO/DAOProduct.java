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
import Entity.products;
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
import java.util.ArrayList;
import java.util.List;
public class DAOProduct extends DBContext {
     public static DAOProduct INSTANCE= new DAOProduct();
    // Phương thức tìm kiếm sản phẩm theo tên (hỗ trợ tìm kiếm gần đúng)
   public List<products> searchProductsByName(String keyword) {
        List<products> productsList = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE LOWER(productName) LIKE LOWER(?) AND isDelete = 0";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.toLowerCase() + "%"); // Chuyển keyword thành chữ thường

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products product = new products();
                    product.setProductID(rs.getInt("productID"));
                    product.setProductName(rs.getString("productName"));
                    product.setDescription(rs.getString("description"));
                    product.setPrice(rs.getDouble("price"));
                    product.setQuantity(rs.getInt("quantity"));
                    product.setImage(rs.getString("image"));

                    // Giữ nguyên getString như yêu cầu
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
      public List<products> getProducts(String sql) {
        List<products> list = new ArrayList<>();
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

                products product = new products(productID, productName, description, price, quantity, image, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
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

    public int updateProduct(products product) {
        int n = 0;
        String sql = "UPDATE products SET productName=?, description=?, price=?, quantity=?, image=?, createAt=?, updateAt=?, createBy=?, isDelete=?, deleteAt=?, deleteBy=? WHERE productID=?";
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
            pre.setInt(12, product.getProductID());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int insertProduct(products product) {
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

    public void listAll() {
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

                products product = new products(productID, productName, description, price, quantity, image, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
                System.out.println(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
