package DAO;

import DAL.DBContext;
import model.DebtRecords;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customers;

public class DAODebtRecords extends DBContext {

    public boolean addDebtRecord(DebtRecords record) {
        String insertSQL = "INSERT INTO DebtRecords (customerID, orderID, amount, paymentStatus, createAt, updateAt, createBy, isDelete) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(insertSQL)) {
            ps.setInt(1, record.getCustomerID());
            ps.setInt(2, record.getOrderID());
            ps.setDouble(3, record.getAmount());
            ps.setInt(4, record.getPaymentStatus());
            ps.setString(5, record.getCreateAt());
            ps.setString(6, record.getUpdateAt());
            ps.setInt(7, record.getCreateBy());
            ps.setBoolean(8, record.isIsDelete());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm bản ghi nợ: " + e.getMessage());
            return false;
        }

        return updateCustomerDebt(record);
    }

    private boolean updateCustomerDebt(DebtRecords record) {
        boolean success = false;

        while (!success) {
            double currentDebt = getCustomerTotalDebt(record.getCustomerID());

            // Tính toán tổng nợ mới dựa trên trạng thái thanh toán
            double newDebt = (record.getPaymentStatus() == 1 || record.getPaymentStatus() == 2)
                    ? currentDebt + record.getAmount()
                    : currentDebt - record.getAmount();

            String updateSQL = "UPDATE Customers SET totalDebt = ? WHERE customerID = ? AND totalDebt = ?";

            try (PreparedStatement ps = conn.prepareStatement(updateSQL)) {
                ps.setDouble(1, newDebt);
                ps.setInt(2, record.getCustomerID());
                ps.setDouble(3, currentDebt);

                int updatedRows = ps.executeUpdate();
                if (updatedRows > 0) {
                    success = true; // Cập nhật thành công
                }
            } catch (SQLException e) {
                System.out.println("Lỗi khi cập nhật tổng nợ: " + e.getMessage());
            }
        }

        return success;
    }

    public double getCustomerTotalDebt(int customerID) {
        String sql = "SELECT totalDebt FROM Customers WHERE customerID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("totalDebt");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy tổng nợ: " + e.getMessage());
        }
        return 0;
    }

    
    
    
    
    
    public Vector<DebtRecords> getDeptRecords(String sql) {
        Vector<DebtRecords> vector = new Vector<DebtRecords>();
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int debtID = rs.getInt("debtID");
                int customerID = rs.getInt("customerID");
                int orderID = rs.getInt("orderID");
                double amount = rs.getDouble("amount");
                int paymentStatus = rs.getInt("paymentStatus");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                String createBy = rs.getString("createBy");
                int isDelete = rs.getInt("isDelete");
                String deleteAt = rs.getString("deleteAt");
                String deleteBy = rs.getString("deleteBy");

                DebtRecords record = new DebtRecords(debtID, customerID, orderID, amount, paymentStatus, createAt, updateAt, debtID, true, deleteAt, isDelete);
                vector.add(record);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAODebtRecords.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public int insertDeptRecord(DebtRecords record) {
        int n = 0;
        String sql = "INSERT INTO debtRecords (customerID, orderID, amount, paymentStatus, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, record.getCustomerID());
            pre.setInt(2, record.getOrderID());
            pre.setDouble(3, record.getAmount());
            pre.setInt(4, record.getPaymentStatus());
            pre.setString(5, record.getCreateAt());
            pre.setString(6, record.getUpdateAt());
            pre.setInt(7, record.getCreateBy());
            pre.setBoolean(8, record.isIsDelete());
            pre.setString(9, record.getDeleteAt());
            pre.setInt(10, record.getDeleteBy());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAODebtRecords.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int updateDeptRecord(DebtRecords record) {
        int n = 0;
        String sql = "UPDATE debtRecords SET customerID=?, orderID=?, amount=?, paymentStatus=?, createAt=?, updateAt=?, createBy=?, isDelete=?, deleteAt=?, deleteBy=? WHERE debtID=?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, record.getCustomerID());
            pre.setInt(2, record.getOrderID());
            pre.setDouble(3, record.getAmount());
            pre.setInt(4, record.getPaymentStatus());
            pre.setString(5, record.getCreateAt());
            pre.setString(6, record.getUpdateAt());
            pre.setInt(7, record.getCreateBy());
            pre.setBoolean(8, record.isIsDelete());
            pre.setString(9, record.getDeleteAt());
            pre.setInt(10, record.getDeleteBy());
            pre.setInt(11, record.getDebtID());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAODebtRecords.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int removeDeptRecord(int debtID) {
        int n = 0;
        String sql = "DELETE FROM debtRecords WHERE debtID=?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, debtID);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAODebtRecords.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

//    public void listAll() {
//        String sql = "SELECT * FROM debtRecords"; // Cập nhật tên bảng
//        try {
//            Statement state = conn.createStatement();
//            ResultSet rs = state.executeQuery(sql);
//            while (rs.next()) {
//                int debtID = rs.getInt("debtID");
//                int customerID = rs.getInt("customerID");
//                int orderID = rs.getInt("orderID");
//                double amount = rs.getDouble("amount");
//                String paymentStatus = rs.getString("paymentStatus");
//                String createAt = rs.getString("createAt");
//                String updateAt = rs.getString("updateAt");
//                String createBy = rs.getString("createBy");
//                int isDelete = rs.getInt("isDelete");
//                String deleteAt = rs.getString("deleteAt");
//                String deleteBy = rs.getString("deleteBy");
//
//                debtRecords record = new debtRecords(debtID, customerID, orderID, amount, paymentStatus, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
//                System.out.println(record);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(DAODebtRecords.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public List<DebtRecords> listAllbyName(String id) {
        List<DebtRecords> list = new ArrayList<>();
        String sql = " SELECT * FROM debtRecords d \n"
                + " JOIN customers c ON d.customerID = c.customerID\n"
                + " WHERE c.customerID = ? \n"
                + " ORDER BY d.updateAt DESC";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, id); // Truyền tham số vào dấu ?
            ResultSet rs = pre.executeQuery(); // Thực thi truy vấn

            while (rs.next()) {
                int debtID = rs.getInt("debtID");
                int customerID = rs.getInt("customerID");
                int orderID = rs.getInt("orderID");
                double amount = rs.getDouble("amount");
                int paymentStatus = rs.getInt("paymentStatus");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");

                DebtRecords record = new DebtRecords(debtID, customerID, orderID, amount, paymentStatus, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
                list.add(record);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAODebtRecords.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<DebtRecords> listAll() {
        List<DebtRecords> list = new ArrayList<>();
        String sql = "SELECT * FROM debtRecords";
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int debtID = rs.getInt("debtID");
                int customerID = rs.getInt("customerID");
                int orderID = rs.getInt("orderID");
                double amount = rs.getDouble("amount");
                int paymentStatus = rs.getInt("paymentStatus");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");

                DebtRecords record = new DebtRecords(debtID, customerID, orderID, amount, paymentStatus, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
                list.add(record);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAODebtRecords.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

//    public static void main(String[] args) {
//        DAODebtRecords dao = new DAODebtRecords();
//
//        List<DebtRecords> list = dao.listAllbyName("21");
//        for (DebtRecords o : list) {
//            System.out.println(o.toString());
//        }
    // 1. Thêm một bản ghi nợ mới
//        DebtRecords newRecord = new DebtRecords(21, 1, 0, 0, "2023-01-02", "2023-01-02", 0, true, "2023-01-02", 0);
//        int insertResult = dao.insertDeptRecord(newRecord);
//        System.out.println("Insert result: " + insertResult);
//
//        // 2. Cập nhật thông tin bản ghi nợ
//        deptRecords recordToUpdate = new deptRecords(3, 1, 1001, 1000.0, "1", "2023-01-02", "2023-01-02", "1", false, null, 0);
//        int updateResult = dao.updateDeptRecord(recordToUpdate);
//        System.out.println("Update result: " + updateResult);
////
//        // 3. Xóa một bản ghi nợ
//        int removeResult = dao.removeDeptRecord(5); // Giả sử ID của bản ghi nợ cần xóa
//        System.out.println("Remove result: " + removeResult);
    // 4. Liệt kê tất cả bản ghi nợ
//        dao.listAll();
//    }
}
