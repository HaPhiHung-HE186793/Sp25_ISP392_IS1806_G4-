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
import model.Customers;
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

public class DAOCustomers extends DBContext {

    public static DAOCustomers INSTANCE = new DAOCustomers();

    // Danh sách kết quả tìm kiếm hiện tại để cộng dồn tiêu chí
    private List<Customers> currentSearchResults = new ArrayList<>();
    // Tìm kiếm theo tiêu chí động và cộng dồn

    public List<Customers> searchCustomers(String name, String phone, String fromDate, String toDate) {
        String sql = "SELECT * FROM customers WHERE isDelete = 0";
        List<String> filters = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            filters.add("name LIKE ?");
            params.add("%" + name + "%");
        }
        if (phone != null && !phone.isEmpty()) {
            filters.add("phone LIKE ?");
            params.add("%" + phone + "%");
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            filters.add("createAt >= ?");
            params.add(fromDate);
        }
        if (toDate != null && !toDate.isEmpty()) {
            filters.add("createAt <= ?");
            params.add(toDate);
        }

        if (!filters.isEmpty()) {
            sql += " AND " + String.join(" AND ", filters);
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            List<Customers> results = new ArrayList<>();
            while (rs.next()) {
                Customers customer = new Customers(
                        rs.getInt("customerID"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getDouble("totalDebt"),
                        rs.getString("createAt"),
                        rs.getString("updateAt"),
                        rs.getInt("createBy"),
                        rs.getBoolean("isDelete"),
                        rs.getString("deleteAt"),
                        rs.getInt("deleteBy")
                );
                results.add(customer);
            }

            if (!currentSearchResults.isEmpty()) {
                results.retainAll(currentSearchResults); // Giữ lại phần giao giữa kết quả cũ và mới
            }
            currentSearchResults = results; // Cập nhật danh sách hiện tại

            return results;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Xóa tiêu chí tìm kiếm, làm mới danh sách tìm kiếm
    public void resetSearch() {
        currentSearchResults.clear();
    }

    public Customers getCustomer(String id) {
        String sql = " select * from customers where customerID = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, id); // Truyền tham số vào dấu ?
            ResultSet rs = pre.executeQuery(); // Thực thi truy vấn

            while (rs.next()) {
                int customerID = rs.getInt("customerID");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                double totalDebt = rs.getDouble("totalDebt");  // Sử dụng double cho tổng nợ
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");  // Chuyển sang boolean
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");

                return new Customers(customerID, name, email, phone, address, totalDebt, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
              
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Customers> findByPhone(String searchPhone) {
        List<Customers> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE phone LIKE ? AND isDelete = 0";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + searchPhone + "%"); // Tìm số điện thoại chứa searchPhone
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Customers customer = new Customers(
                        rs.getInt("customerID"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getDouble("totalDebt"),
                        rs.getString("createAt"),
                        rs.getString("updateAt"),
                        rs.getInt("createBy"),
                        rs.getBoolean("isDelete"),
                        rs.getString("deleteAt"),
                        rs.getInt("deleteBy")
                );
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public Vector<Customers> getCustomers(String sql) {
        Vector<Customers> vector = new Vector<Customers>();
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int customerID = rs.getInt("customerID");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                double totalDebt = rs.getDouble("totalDebt");  // Sử dụng double cho tổng nợ
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");  // Chuyển sang boolean
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");

                Customers customer = new Customers(customerID, name, email, phone, address, totalDebt, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
                vector.add(customer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }

    public int removeCustomer(int customerID) {
        int n = 0;
        String sql = "DELETE FROM customers WHERE customerID=?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, customerID);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int updateCustomer(Customers customer) {
        int n = 0;
        String sql = "UPDATE customers SET name=?, email=?, phone=?, address=?, totalDebt=?, createAt=?, updateAt=?, createBy=?, isDelete=?, deleteAt=?, deleteBy=? WHERE customerID=?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, customer.getName()); // name
            pre.setString(2, customer.getEmail()); // email
            pre.setString(3, customer.getPhone()); // phone
            pre.setString(4, customer.getAddress()); // address
            pre.setDouble(5, customer.getTotalDebt()); // totalDebt
            pre.setString(6, customer.getCreateAt()); // createAt
            pre.setString(7, customer.getUpdateAt()); // updateAt
            pre.setInt(8, customer.getCreateBy()); // createBy
            pre.setBoolean(9, customer.isIsDelete()); // isDelete
            pre.setString(10, customer.getDeleteAt()); // deleteAt
            pre.setInt(11, customer.getDeleteBy()); // deleteBy
            pre.setInt(12, customer.getCustomerID()); // customerID
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int insertCustomer(Customers customer) {
        int n = 0;
        String sql = "INSERT INTO customers (name, email, phone, address, totalDebt, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, customer.getName()); // name
            pre.setString(2, customer.getEmail()); // email
            pre.setString(3, customer.getPhone()); // phone
            pre.setString(4, customer.getAddress()); // address
            pre.setDouble(5, customer.getTotalDebt()); // totalDebt
            pre.setString(6, customer.getCreateAt()); // createAt
            pre.setString(7, customer.getUpdateAt()); // updateAt
            pre.setInt(8, customer.getCreateBy()); // createBy
            pre.setBoolean(9, customer.isIsDelete()); // isDelete
            pre.setString(10, customer.getDeleteAt()); // deleteAt
            pre.setInt(11, customer.getDeleteBy()); // deleteBy
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int insertNewCustomer(Customers customer) {
        int n = 0;
        String sql = "INSERT INTO customers (name, email, phone, address, totalDebt, createAt, updateAt, createBy, isDelete) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, customer.getName()); // name
            pre.setString(2, customer.getEmail()); // email
            pre.setString(3, customer.getPhone()); // phone
            pre.setString(4, customer.getAddress()); // address
            pre.setDouble(5, customer.getTotalDebt()); // totalDebt
            pre.setString(6, customer.getCreateAt()); // createAt
            pre.setString(7, customer.getUpdateAt()); // updateAt
            pre.setInt(8, customer.getCreateBy()); // createBy
            pre.setBoolean(9, customer.isIsDelete()); // isDelete
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public List<Customers> listAll() {
        List<Customers> list = new ArrayList<>();
        String sql = "SELECT * FROM customers "; // Cập nhật tên bảng
        try {
                       Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int customerID = rs.getInt("customerID");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                double totalDebt = rs.getDouble("totalDebt"); // Sử dụng double cho tổng nợ
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete"); // Giả sử isDelete là kiểu int
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");

                Customers customer = new Customers(customerID, name, email, phone, address, totalDebt, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
                list.add(customer); // Thêm vào danh sách thay vì in ra
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list; // Trả về danh sách khách hàng
    }
    
        public List<Customers> listAllDebt(int id) {
        List<Customers> list = new ArrayList<>();
        String sql = "SELECT * FROM customers where createBy = ?"; // Cập nhật tên bảng
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id); // Truyền tham số vào dấu ?
            ResultSet rs = pre.executeQuery(); // Thực thi truy vấn
            while (rs.next()) {
                int customerID = rs.getInt("customerID");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                double totalDebt = rs.getDouble("totalDebt"); // Sử dụng double cho tổng nợ
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete"); // Giả sử isDelete là kiểu int
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");

                Customers customer = new Customers(customerID, name, email, phone, address, totalDebt, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
                list.add(customer); // Thêm vào danh sách thay vì in ra
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list; // Trả về danh sách khách hàng
    }

    public static void main(String[] args) {
        DAOCustomers dao = new DAOCustomers();
//        List<Customers> list = dao.listAll();
//        for (Customers o : list) {
            System.out.println(dao.getCustomer("21"));
//        }
//
//    // 1. Thêm một khách hàng mới
//    Customers newCustomer = new Customers(3, "Nguyễn Thu B", "nguyenvanb@gmail.com", "0987654321", "456 Đường XYZ", 2000.0, "2023-01-02", "2023-01-02", 1, false, null, 0);
//    int insertResult = dao.insertNewCustomer(newCustomer);
//    System.out.println("Insert result: " + insertResult);
//
//    // 2. Cập nhật thông tin khách hàng
////    customers customerToUpdate = new customers(3, "Nguyễn Thu B", "nguyenvanb@gmail.com", "0987654321", "456 Đường XYZ", 2000.0, "2023-01-02", "2023-01-02", 1, false, null, 0);
////    int updateResult = dao.updateCustomer(customerToUpdate);
////    System.out.println("Update result: " + updateResult);
//
//    // 3. Xóa một khách hàng
////    int removeResult = dao.removeCustomer(2); // Giả sử ID của khách hàng cần xóa là 1
////    System.out.println("Remove result: " + removeResult);
//
//     4. Liệt kê tất cả khách hàng
    }
}
