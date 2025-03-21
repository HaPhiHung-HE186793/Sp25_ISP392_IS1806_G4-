package DAO;

import DAL.DBContext;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import controller.debt.AddNewCustomerDebt;
import java.math.BigDecimal;
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
import service.WebAppListener;

public class DAODebtRecords extends DBContext {

    public DBContext db;

    public DAODebtRecords() {
        db = new DBContext(); // Sử dụng kết nối từ DBContext
    }

    public DebtRecords getDebt(String id, int storeid) {
        String sql = " select * from DebtRecords where debtID = ? and  storeID = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, id); // Truyền tham số vào dấu ?
            pre.setInt(2, storeid); // Truyền tham số vào dấu ?

            ResultSet rs = pre.executeQuery(); // Thực thi truy vấn

            while (rs.next()) {
                int debtID = rs.getInt("debtID");
                int customerID = rs.getInt("customerID");
                int orderID = rs.getInt("orderID");
                BigDecimal amount = rs.getBigDecimal("amount");  // Sử dụng double cho tổng nợ
                int paymentStatus = rs.getInt("paymentStatus");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");  // Chuyển sang boolean
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");
                int storeID = rs.getInt("storeID");
                String description = rs.getString("description");
                String img = rs.getString("img");
                int status = rs.getInt("status");

                return new DebtRecords(debtID, customerID, orderID, amount, paymentStatus, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, storeID, description, img, status);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<DebtRecords> getUnprocessedDebts() {
        List<DebtRecords> unprocessedList = new ArrayList<>();
        String selectSQL = "SELECT debtID, customerID, paymentStatus, amount, status FROM DebtRecords WHERE status = 0";

        try (ResultSet rs = db.getData(selectSQL)) {
            while (rs.next()) {
                DebtRecords record = new DebtRecords();
                record.setDebtID(rs.getInt("debtID"));
                record.setCustomerID(rs.getInt("customerID"));
                record.setPaymentStatus(rs.getInt("paymentStatus"));
                record.setAmount(rs.getBigDecimal("amount"));
                record.setStatus(rs.getInt("status"));
                unprocessedList.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unprocessedList;
    }

    public int addDebtRecord(DebtRecords record) {
        String insertSQL = "INSERT INTO DebtRecords (customerID, orderID, amount, paymentStatus, createAt, updateAt, createBy, isDelete,storeID,description,img,status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";

        int generatedDebtID = -1;

        try (PreparedStatement ps = db.conn.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, record.getCustomerID());
            ps.setInt(2, record.getOrderID());
            ps.setBigDecimal(3, record.getAmount());
            ps.setInt(4, record.getPaymentStatus());
            ps.setString(5, record.getCreateAt());
            ps.setString(6, record.getUpdateAt());
            ps.setInt(7, record.getCreateBy());
            ps.setBoolean(8, record.isIsDelete());
            ps.setInt(9, record.getStoreID());
            ps.setString(10, record.getDescription());
            ps.setString(11, record.getImg());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedDebtID = rs.getInt(1); // Lấy debtID vừa được tạo
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm bản ghi nợ: " + e.getMessage());
        }

        return generatedDebtID; // Trả về debtID để thêm vào queue
    }

    public int addDebtRecord1(DebtRecords record) {
        String insertSQL = "INSERT INTO DebtRecords (customerID, orderID, amount, paymentStatus,createBy, isDelete,status) "
                + "VALUES (?, ?, ?, ?, ?, ?,0)";

        int generatedDebtID = -1;

        try (PreparedStatement ps = db.conn.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, record.getCustomerID());
            ps.setInt(2, record.getOrderID());
            ps.setBigDecimal(3, record.getAmount());
            ps.setInt(4, record.getPaymentStatus());

            ps.setInt(5, record.getCreateBy());
            ps.setBoolean(6, record.isIsDelete());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedDebtID = rs.getInt(1); // Lấy debtID vừa được tạo
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm bản ghi nợ: " + e.getMessage());
        }

        return generatedDebtID; // Trả về debtID để thêm vào queue
    }

    public void updateCustomerDebt(int customerId, int paymentStatus, BigDecimal amount) throws SQLException {
        String operation = switch (paymentStatus) {
            case 1, 2 ->
                "+";
            case 0, 3 ->
                "-";
            default ->
                throw new IllegalArgumentException("Trạng thái thanh toán không hợp lệ.");
        };

        String updateSQL = "UPDATE customers SET totalDebt = totalDebt " + operation + " ? WHERE customerID = ?";
        try (PreparedStatement ps = db.conn.prepareStatement(updateSQL)) {
            ps.setBigDecimal(1, amount);
            ps.setInt(2, customerId);
            ps.executeUpdate();
//            ps.close();
        }
    }

    public void markDebtAsProcessed(int debtId) throws SQLException {
        String updateSQL = "UPDATE DebtRecords SET status = 1 WHERE debtID = ?";
        try (PreparedStatement ps = db.conn.prepareStatement(updateSQL)) {
            ps.setInt(1, debtId);
            ps.executeUpdate();
//            ps.close();

        }
    }

    public int insertDeptRecord(DebtRecords record) {
        int n = 0;
        String sql = "INSERT INTO debtRecords (customerID, orderID, amount, paymentStatus, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, record.getCustomerID());
            pre.setInt(2, record.getOrderID());
            pre.setBigDecimal(3, record.getAmount());
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
            pre.setBigDecimal(3, record.getAmount());
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

    public List<DebtRecords> listCustomersByRoleSearchName(String createBylis, String name) {
        List<DebtRecords> list = new ArrayList<>();
        String sql = " SELECT * FROM debtRecords d \n"
                + " JOIN customers c ON d.customerID = c.customerID\n"
                + " WHERE c.customerID = ? \n"
                + name + " ORDER BY d.debtID DESC";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, createBylis); // Truyền tham số vào dấu ?
            ResultSet rs = pre.executeQuery(); // Thực thi truy vấn

            while (rs.next()) {
                int debtID = rs.getInt("debtID");
                int customerID = rs.getInt("customerID");
                int orderID = rs.getInt("orderID");
                BigDecimal amount = rs.getBigDecimal("amount");
                int paymentStatus = rs.getInt("paymentStatus");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");
                int store = rs.getInt("storeID");
                String description = rs.getString("description");
                String img = rs.getString("img");
                int status = rs.getInt("status");

                DebtRecords record = new DebtRecords(debtID, customerID, orderID, amount, paymentStatus, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, store, description, img, status);
                list.add(record);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAODebtRecords.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<DebtRecords> listAllbyName(String id) {
        List<DebtRecords> list = new ArrayList<>();
        String sql = " SELECT * FROM debtRecords d \n"
                + " JOIN customers c ON d.customerID = c.customerID\n"
                + " WHERE c.customerID = ? \n"
                + " ORDER BY d.debtID DESC";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, id); // Truyền tham số vào dấu ?
            ResultSet rs = pre.executeQuery(); // Thực thi truy vấn

            while (rs.next()) {
                int debtID = rs.getInt("debtID");
                int customerID = rs.getInt("customerID");
                int orderID = rs.getInt("orderID");
                BigDecimal amount = rs.getBigDecimal("amount");
                int paymentStatus = rs.getInt("paymentStatus");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");
                int store = rs.getInt("storeID");
                String description = rs.getString("description");
                String img = rs.getString("img");
                int status = rs.getInt("status");

                DebtRecords record = new DebtRecords(debtID, customerID, orderID, amount, paymentStatus, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, store, description, img, status);
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
                BigDecimal amount = rs.getBigDecimal("amount");
                int paymentStatus = rs.getInt("paymentStatus");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");
                int store = rs.getInt("storeID");
                String description = rs.getString("description");
                String img = rs.getString("img");
                int status = rs.getInt("status");

                DebtRecords record = new DebtRecords(debtID, customerID, orderID, amount, paymentStatus, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, store, description, img, status);
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
