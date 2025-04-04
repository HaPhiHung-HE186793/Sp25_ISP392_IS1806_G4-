package DAO;

import DAL.DBContext;
import model.OrderItems;

import java.math.BigDecimal;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOOrderItems extends DBContext {

    public static DAOOrderItems INSTANCE = new DAOOrderItems();

    public Vector<OrderItems> getOrderItems(String sql) {
        Vector<OrderItems> vector = new Vector<OrderItems>();
        try {
            System.out.println("Executing SQL: " + sql); // Ghi lại câu lệnh SQL
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                OrderItems orderItem = new OrderItems(
                        rs.getInt("orderitemID"),
                        rs.getInt("orderID"),
                        rs.getInt("productID"),
                        rs.getString("productName"),
                        rs.getDouble("price"),
                        rs.getDouble("unitPrice"),
                        rs.getInt("quantity")
                );
                vector.add(orderItem);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }

    public int getTotalRecords(String sql, int orderId, Integer storeID) {
        int totalRecords = 0;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            if (storeID != null) {
                ps.setInt(2, storeID);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalRecords = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrderItems.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalRecords;
    }
    public Integer getStoreIdByOrderID(int orderID) {
        String sql = "SELECT storeID FROM orders WHERE orderID = ?";
        Integer storeId = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                storeId = rs.getInt("storeID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return storeId;
    }

    public boolean createOrderItem(int orderID, int productID, String productName, BigDecimal price, BigDecimal unitPrice, int quantity) {
        int storeId =getStoreIdByOrderID(orderID);
        String sql = "INSERT INTO OrderItems (orderID, productID, productName, price, unitPrice, quantity,storeID) VALUES (?, ?, ?, ?, ?, ?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            ps.setInt(2, productID);
            ps.setString(3, productName);
            ps.setBigDecimal(4, price);
            ps.setBigDecimal(5, unitPrice);
            ps.setInt(6, quantity);
             ps.setInt(7, storeId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng được thêm

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

    public int removeOrderItem(int orderitemID) {
        int n = 0;
        String sql = "DELETE FROM OrderItems WHERE orderitemID=?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, orderitemID);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrderItems.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int updateOrderItem(OrderItems orderItem) {
        int n = 0;
        String sql = "UPDATE OrderItems SET orderID=?, productID=?, productName=?, price=?, unitPrice=?, quantity=?, description=? WHERE orderitemID=?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, orderItem.getOrderID());
            pre.setInt(2, orderItem.getProductID());
            pre.setString(3, orderItem.getProductName());
            pre.setDouble(4, orderItem.getPrice());
            pre.setDouble(5, orderItem.getUnitPrice());
            pre.setInt(6, orderItem.getQuantity());
            pre.setString(7, orderItem.getDescription());
            pre.setInt(8, orderItem.getOrderitemID());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrderItems.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int insertOrderItem(OrderItems orderItem) {
        int n = 0;
        String sql = "INSERT INTO OrderItems (orderID, productID, productName, price, unitPrice, quantity, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, orderItem.getOrderID());
            pre.setInt(2, orderItem.getProductID());
            pre.setString(3, orderItem.getProductName());
            pre.setDouble(4, orderItem.getPrice());
            pre.setDouble(5, orderItem.getUnitPrice());
            pre.setInt(6, orderItem.getQuantity());
            pre.setString(7, orderItem.getDescription());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrderItems.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public void listAll() {
        String sql = "SELECT * FROM OrderItems"; // Cập nhật tên bảng
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int orderitemID = rs.getInt("orderitemID");
                int orderID = rs.getInt("orderID");
                int productID = rs.getInt("productID");
                String productName = rs.getString("productName");
                double price = rs.getDouble("price");
                double unitPrice = rs.getDouble("unitPrice");
                int quantity = rs.getInt("quantity");
                String description = rs.getString("description");

                OrderItems orderItem = new OrderItems(orderitemID, orderID, productID, productName, price, unitPrice, quantity, description);
                System.out.println(orderItem);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DAOOrderItems dao = new DAOOrderItems();

        // 1. Thêm một mục đơn hàng mới
//        orderItems newOrderItem = new orderItems( 1, 2, "Product b", 100.0, 95.0, 2, "Description of Product A");
//        int insertResult = dao.insertOrderItem(newOrderItem);
//        System.out.println("Insert result: " + insertResult);
        // 2. Cập nhật thông tin mục đơn hàng
//        orderItems orderItemToUpdate = new orderItems(1, 1, 1, "Product A Updated", 120.0, 115.0, 3, "Updated description");
//        int updateResult = dao.updateOrderItem(orderItemToUpdate);
//        System.out.println("Update result: " + updateResult);
//
//        // 3. Xóa một mục đơn hàng
        int removeResult = dao.removeOrderItem(2); // Giả sử ID của mục đơn hàng cần xóa
        System.out.println("Remove result: " + removeResult);

        // 4. Liệt kê tất cả mục đơn hàng
        dao.listAll();
    }
}
