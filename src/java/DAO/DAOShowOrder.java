/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBContext;

import model.ShowOrder;
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
public class DAOShowOrder extends DBContext {
//    public Vector<showOrder> getShowOrder(String sql) {
//    Vector<showOrder> vector = new Vector<showOrder>();
//    try {
//        Statement state = conn.createStatement();
//        ResultSet rs = state.executeQuery(sql);
//        while (rs.next()) {
//            int orderID = rs.getInt("orderID");
//            String name = rs.getString("name"); // Assuming 'name' is in your SQL
//            String userName = rs.getString("userName"); // Assuming 'userName' is in your SQL
//            double totalAmount = rs.getDouble("totalAmount");
//            String createAt = rs.getString("createAt");
//            String updateAt = rs.getString("updateAt");
//            int porter = rs.getInt("porter");
//            String status = rs.getString("status");
//
//            // Directly adding the data to the vector without creating a new instance
//            ShowOrder ShowOrder = new ShowOrder(orderID, name, userName, totalAmount, createAt, updateAt, porter, status);
//            vector.add(ShowOrder);
//        }
//    } catch (SQLException ex) {
//        ex.printStackTrace();
//    }
//    return vector;
//}
    public Vector<ShowOrder> getRemindOrder(String sql) {
    Vector<ShowOrder> vector = new Vector<ShowOrder>();
    try {
        System.out.println("Executing SQL: " + sql); // Ghi lại câu lệnh SQL
        Statement state = conn.createStatement();
        ResultSet rs = state.executeQuery(sql);
        while (rs.next()) {
            int orderID = rs.getInt("orderID");
            String name = rs.getString("name");
            String userName = rs.getString("userName");
            double totalAmount = rs.getDouble("totalAmount");
            String createAt = rs.getString("createAt");
            String updateAt = rs.getString("updateAt");
            int porter = rs.getInt("porter");
            String status = rs.getString("status");
            int storeID = rs.getInt("storeID");
            double paidAmount = rs.getDouble("paidAmount");
            String email = rs.getString("email");

            ShowOrder showOrder = new ShowOrder(orderID, name, userName, totalAmount, createAt, updateAt, porter, status, storeID, paidAmount,email);
            vector.add(showOrder);
        }
    } catch (SQLException ex) {
        System.err.println("SQL Error in getShowOrder: " + ex.getMessage());
        ex.printStackTrace(); // Ghi lại lỗi SQL
    }
    return vector;
}
    public Vector<ShowOrder> getShowOrder(String sql) {
    Vector<ShowOrder> vector = new Vector<ShowOrder>();
    try {
        System.out.println("Executing SQL: " + sql); // Ghi lại câu lệnh SQL
        Statement state = conn.createStatement();
        ResultSet rs = state.executeQuery(sql);
        while (rs.next()) {
            int orderID = rs.getInt("orderID");
            String name = rs.getString("name");
            String userName = rs.getString("userName");
            double totalAmount = rs.getDouble("totalAmount");
            String createAt = rs.getString("createAt");
            String updateAt = rs.getString("updateAt");
            int porter = rs.getInt("porter");
            String status = rs.getString("status");
            int storeID = rs.getInt("storeID");
            double paidAmount = rs.getDouble("paidAmount");

            ShowOrder showOrder = new ShowOrder(orderID, name, userName, totalAmount, createAt, updateAt, porter, status, storeID, paidAmount);
            vector.add(showOrder);
        }
    } catch (SQLException ex) {
        System.err.println("SQL Error in getShowOrder: " + ex.getMessage());
        ex.printStackTrace(); // Ghi lại lỗi SQL
    }
    return vector;
}
    public int getTotalAmount(String sql) {
        int totalAmount = 0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                totalAmount = rs.getInt(1); // Lấy tổng giá tiền từ kết quả truy vấn
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối, ResultSet, PreparedStatement nếu cần
        }
        return totalAmount;
    }
    public int getTotalRecords(String sql) {
    int totalRecords = 0;
    try {
        Statement state = conn.createStatement();
        ResultSet rs = state.executeQuery(sql);
        if (rs.next()) {
            totalRecords = rs.getInt(1); 
        }
    } catch (SQLException ex) {
        Logger.getLogger(DAOOrders.class.getName()).log(Level.SEVERE, null, ex);
    }
    return totalRecords;
}
   public static void main(String[] args) {
        DAOShowOrder dao = new DAOShowOrder();
        String sql = "SELECT o.orderID, c.name, u.userName, o.totalAmount, o.createAt, o.updateAt, o.porter, o.status " +
                     "FROM orders o " +
                     "JOIN customers c ON o.customerID = c.customerID " +
                     "JOIN users u ON o.userID = u.ID " +
                     "ORDER BY o.orderID " +
                     "OFFSET 1 ROWS FETCH NEXT 5 ROWS ONLY";  // Chỉnh sửa OFFSET nếu cần

        // Kiểm tra kết nối và truy vấn
        Vector<ShowOrder> orders = dao.getShowOrder(sql);
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            for (ShowOrder order : orders) {
                System.out.println("Order ID: " + order.getOrderID() + 
                                   ", Name: " + order.getName() + 
                                   ", User Name: " + order.getUserName() + 
                                   ", Total Amount: " + order.getTotalAmount() + 
                                   ", Created At: " + order.getCreateAt() + 
                                   ", Updated At: " + order.getUpdateAt() + 
                                   ", Porter: " + order.getPorter() + 
                                   ", Status: " + order.getStatus());
            }
        }
    }
}
