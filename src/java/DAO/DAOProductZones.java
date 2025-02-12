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
import Entity.productZones; // Giả sử lớp Product đã được định nghĩa
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOProductZones extends DBContext {

//    public Vector<productZones> getProducts(String sql) {
//        Vector<productZones> vector = new Vector<productZones>();
//        try {
//            Statement state = conn.createStatement();
//            ResultSet rs = state.executeQuery(sql);
//            while (rs.next()) {
//                int productID = rs.getInt("productID");
//                int zoneID = rs.getInt("zoneID");
//                String createAt = rs.getString("createAt");
//                String updateAt = rs.getString("updateAt");
//                int createBy = rs.getInt("createBy");
//                Boolean isDelete = rs.getBoolean("isDelete");
//                String deleteAt = rs.getString("deleteAt");
//                int deleteBy = rs.getInt("deleteBy");
//
//                productZones product = new productZones(productID, zoneID, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
//                vector.add(product);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return vector;
//    }
      public List<productZones> getProducts(String sql) {
        List<productZones> list = new ArrayList<>();
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int productID = rs.getInt("productID");
                int zoneID = rs.getInt("zoneID");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");

                productZones product = new productZones(productID, zoneID, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
                list.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    public int removeProduct(int productID, int zoneID) {
        int n = 0;
        String sql = "DELETE FROM ProductZones WHERE productID=? AND zoneID=?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, productID);
            pre.setInt(2, zoneID);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOProductZones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int updateProduct(productZones productZones) {
        int n = 0;
        String sql = "UPDATE ProductZones SET createAt=?, updateAt=?, createBy=?, isDelete=?, deleteAt=?, deleteBy=? WHERE productID=? AND zoneID=?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, productZones.getCreateAt());
            pre.setString(2, productZones.getUpdateAt());
            pre.setInt(3, productZones.getCreateBy());
            pre.setBoolean(4, productZones.isIsDelete());
            pre.setString(5, productZones.getDeleteAt());
            pre.setInt(6, productZones.getDeleteBy());
            pre.setInt(7, productZones.getProductID());
            pre.setInt(8, productZones.getZoneID()); // Thêm zoneID vào điều kiện
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOProductZones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int insertProduct(productZones productZones) {
        int n = 0;
        String sql = "INSERT INTO ProductZones (productID, zoneID, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, productZones.getProductID());
            pre.setInt(2, productZones.getZoneID());
            pre.setString(3, productZones.getCreateAt());
            pre.setString(4, productZones.getUpdateAt());
            pre.setInt(5, productZones.getCreateBy());
            pre.setBoolean(6, productZones.isIsDelete());
            pre.setString(7, productZones.getDeleteAt());
            pre.setInt(8, productZones.getDeleteBy());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOProductZones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public void listAll() {
        String sql = "SELECT * FROM ProductZones"; // Cập nhật tên bảng
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int productID = rs.getInt("productID");
                int zoneID = rs.getInt("zoneID");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");

                productZones productZones = new productZones(productID, zoneID, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
                System.out.println(productZones);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DAOProductZones dao = new DAOProductZones();

        // 1. Thêm một sản phẩm mới
        productZones newProduct = new productZones(3, 2, "2023-02-01", "2023-01-01", 1, false, null, 0);
        int insertResult = dao.insertProduct(newProduct);
        System.out.println("Insert result: " + insertResult);

//        // 2. Cập nhật thông tin sản phẩm
//        productZones productToUpdate = new productZones(3, 2, "2024-01-02", "2023-01-02", 1, false, null, 0);
//        int updateResult = dao.updateProduct(productToUpdate);
//        System.out.println("Update result: " + updateResult);
//
//        // 3. Xóa một sản phẩm
        int removeResult = dao.removeProduct(3, 2); // Giả sử ID của sản phẩm và zone cần xóa
//        System.out.println("Remove result: " + removeResult);

        // 4. Liệt kê tất cả sản phẩm
        dao.listAll();
    }
}