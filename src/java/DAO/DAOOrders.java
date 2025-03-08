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
import model.Orders;
import java.math.BigDecimal;
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
public class DAOOrders extends DBContext{
    
    public static DAOOrders INSTANCE= new DAOOrders();
    
    public int createOrder(int customerId,int userID,int createBy,BigDecimal totalAmount, int porter, String status,int orderType,BigDecimal paidAmount ) throws SQLException {
        String sql = "INSERT INTO orders (customerID,userID,createBy, totalAmount, porter, status,orderType,paidAmount) VALUES (?, ?, ?, ?, ?, ?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, userID);
            stmt.setInt(3, createBy);
            
            stmt.setBigDecimal(4, totalAmount);
            stmt.setInt(5, porter);
            stmt.setString(6, status);
            stmt.setInt(7, orderType);
             stmt.setBigDecimal(8, paidAmount);
            
            stmt.executeUpdate();
            
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Trả về orderID vừa tạo
                }
            }
        }
        return -1; // Trả về -1 nếu không tạo được
    }
//     public Vector<orders> getOrders(String sql) {
//        Vector<orders> vector = new Vector<orders>();
//        try {
//            Statement state = conn.createStatement();
//            ResultSet rs = state.executeQuery(sql);
//            while (rs.next()) {
//                int orderID = rs.getInt("orderID");
//                int customerID = rs.getInt("customerID");
//                int userID = rs.getInt("userID");
//                double totalAmount = rs.getDouble("totalAmount");
//                String createAt = rs.getString("createAt");
//                String updateAt = rs.getString("updateAt");
//                int createBy = rs.getInt("createBy");
//                int isDelete = rs.getInt("isDelete");
//                String deleteAt = rs.getString("deleteAt");
//                Boolean deleteBy = rs.getBoolean("deleteBy");
//                int porter = rs.getInt("porter");
//                String status = rs.getString("status");
//
//                orders order = new orders(orderID, customerID, userID, totalAmount, createAt, updateAt, createBy, true, deleteAt, isDelete, porter, status);
//                vector.add(order);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return vector;
//    }
  public List<Orders> getOrders(String sql) {
        List<Orders> list = new ArrayList<>();
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int orderID = rs.getInt("orderID");
                int customerID = rs.getInt("customerID");
                int userID = rs.getInt("userID");
                double totalAmount = rs.getDouble("totalAmount");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                int isDelete = rs.getInt("isDelete");
                String deleteAt = rs.getString("deleteAt");
                Boolean deleteBy = rs.getBoolean("deleteBy");
                int porter = rs.getInt("porter");
                String status = rs.getString("status");

                Orders order = new Orders(orderID, customerID, userID, totalAmount, createAt, updateAt, createBy, true, deleteAt, isDelete, porter, status);
                list.add(order);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    public int removeOrder(int orderID) {
        int n = 0;
        String sql = "DELETE FROM orders WHERE orderID=?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, orderID);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrders.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int updateOrder(Orders order) {
        int n = 0;
        String sql = "UPDATE orders SET customerID=?, userID=?, totalAmount=?, createAt=?, updateAt=?, createBy=?, isDelete=?, deleteAt=?, deleteBy=?, porter=?, status=? WHERE orderID=?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, order.getCustomerID());
            pre.setInt(2, order.getUserID());
            pre.setDouble(3, order.getTotalAmount());
            pre.setString(4, order.getCreateAt());
            pre.setString(5, order.getUpdateAt());
            pre.setInt(6, order.getCreateBy());
            pre.setBoolean(7, order.isIsDelete());
            pre.setString(8, order.getDeleteAt());
            pre.setInt(9, order.getDeleteBy());
            pre.setInt(10, order.getPorter());
            pre.setString(11, order.getStatus());
            pre.setInt(12, order.getOrderID());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrders.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int insertOrder(Orders order) {
        int n = 0;
        String sql = "INSERT INTO orders (customerID, userID, totalAmount, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, porter, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, order.getCustomerID());
            pre.setInt(2, order.getUserID());
            pre.setDouble(3, order.getTotalAmount());
            pre.setString(4, order.getCreateAt());
            pre.setString(5, order.getUpdateAt());
            pre.setInt(6, order.getCreateBy());
            pre.setBoolean(7, order.isIsDelete());
            pre.setString(8, order.getDeleteAt());
            pre.setInt(9, order.getDeleteBy());
            pre.setInt(10, order.getPorter());
            pre.setString(11, order.getStatus());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrders.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public void listAll() {
        String sql = "SELECT * FROM orders"; // Cập nhật tên bảng
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int orderID = rs.getInt("orderID");
                int customerID = rs.getInt("customerID");
                int userID = rs.getInt("userID");
                double totalAmount = rs.getDouble("totalAmount");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");
                int porter = rs.getInt("porter");
                String status = rs.getString("status");

                Orders order = new Orders(orderID, customerID, userID, totalAmount, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, porter, status);
                System.out.println(order);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DAOOrders dao = new DAOOrders();

        // 1. Thêm một đơn hàng mới
//        orders newOrder = new orders( 1, 1, 1300.0, "2023-01-01", "2023-01-01", 1, false, null, 0, 1, "Pending");
//        int insertResult = dao.insertOrder(newOrder);
//        System.out.println("Insert result: " + insertResult);

//        // 2. Cập nhật thông tin đơn hàng
//        orders orderToUpdate = new orders(1, 1, 1, 4000.0, "2023-01-02", "2023-01-02", 1, false, null, 0, 1, "Completed");
//        int updateResult = dao.updateOrder(orderToUpdate);
//        System.out.println("Update result: " + updateResult);
//
//        // 3. Xóa một đơn hàng
          int removeResult = dao.removeOrder(3); // Giả sử ID của đơn hàng cần xóa
//        System.out.println("Remove result: " + removeResult);

        // 4. Liệt kê tất cả đơn hàng
        dao.listAll();
    }
}
