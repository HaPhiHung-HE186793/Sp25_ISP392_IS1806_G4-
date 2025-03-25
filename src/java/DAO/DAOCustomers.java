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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DAOCustomers extends DBContext {

    public static DAOCustomers INSTANCE = new DAOCustomers();

    public Customers getCustomer(String id, int storeid, int userRole) {
        String sql = " select * from customers where customerID = ? and  storeID = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, id); // Truyền tham số vào dấu ?
            pre.setInt(2, storeid); // Truyền tham số vào dấu ?

            ResultSet rs = pre.executeQuery(); // Thực thi truy vấn

            while (rs.next()) {
                int customerID = rs.getInt("customerID");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                BigDecimal totalDebt = rs.getBigDecimal("totalDebt");  // Sử dụng double cho tổng nợ
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");  // Chuyển sang boolean
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");
                int storeID = rs.getInt("storeID");

                            // Ẩn số điện thoại nếu role là "staff"
            if (userRole==3) {
                phone = maskPhoneNumber(phone);
            }
                
                return new Customers(customerID, name, email, phone, address, totalDebt, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, storeID);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getCustomerNameByID(String id) {
        String sql = "SELECT name FROM customers WHERE customerID = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, id);

            ResultSet rs = pre.executeQuery(); // Thực thi truy vấn
            if (rs.next()) { // Kiểm tra xem có dữ liệu hay không
                return rs.getString("name");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy khách hàng
    }

    public List<Customers> findByNameOrPhone(String searchValue, int storeID) {

        List<Customers> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE (phone LIKE ? OR name LIKE ?) AND isDelete = 0 AND storeID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + searchValue + "%"); // Tìm số điện thoại chứa searchPhone
            ps.setString(2, "%" + searchValue + "%");
            ps.setInt(3, storeID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Customers customer = new Customers(
                        rs.getInt("customerID"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getBigDecimal("totalDebt"),
                        rs.getString("createAt"),
                        rs.getString("updateAt"),
                        rs.getInt("createBy"),
                        rs.getBoolean("isDelete"),
                        rs.getString("deleteAt"),
                        rs.getInt("deleteBy"),
                        rs.getInt("storeID")
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
                String customerName = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                BigDecimal totalDebt = rs.getBigDecimal("totalDebt");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");
                int storeID = rs.getInt("storeID");

                Customers customer = new Customers(customerID, customerName, email, phone, address, totalDebt, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, storeID);
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

    public int countCustomersByRole(int userID, int roleID) {
        int count = 0;
        String sql = "";

        if (roleID == 1) { // Nếu là Admin
            sql = "SELECT COUNT(*) AS total FROM customers c "
                    + "WHERE createBy = ? "
                    + "   OR createBy IN ( "
                    + "       SELECT u.ID FROM users u "
                    + "       JOIN orders o ON o.userID = u.ID "
                    + "       JOIN customers c2 ON c2.customerID = o.customerID "
                    + "       WHERE u.roleID = 1 AND c2.createBy = ? "
                    + "   )";
        } else if (roleID == 2) { // Nếu là Owner
            sql = "SELECT COUNT(*) AS total FROM customers WHERE createBy = ?";
        }

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, userID);
            if (roleID == 1) {
                pre.setInt(2, userID);
            }

            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public int updateCustomer(Customers customer) {
        int n = 0;
        String sql = "UPDATE customers SET name=?, email=?, phone=?, address=?, updateAt=?, isDelete=? WHERE customerID=?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, customer.getName()); // name
            pre.setString(2, customer.getEmail()); // email
            pre.setString(3, customer.getPhone()); // phone
            pre.setString(4, customer.getAddress()); // address
            pre.setString(5, customer.getUpdateAt()); // updateAt
            pre.setBoolean(6, customer.isIsDelete()); // isDelete
            pre.setInt(7, customer.getCustomerID()); // customerID
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    
    
        public int updateCustomerNotPhone(Customers customer) {
        int n = 0;
        String sql = "UPDATE customers SET name=?, email=?, address=?, updateAt=?, isDelete=? WHERE customerID=?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, customer.getName()); // name
            pre.setString(2, customer.getEmail()); // email
            pre.setString(3, customer.getAddress()); // address
            pre.setString(4, customer.getUpdateAt()); // updateAt
            pre.setBoolean(5, customer.isIsDelete()); // isDelete
            pre.setInt(6, customer.getCustomerID()); // customerID
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    

    public int BanCustomer(Customers customer) {
        int n = 0;
        String sql = "UPDATE customers SET isDelete=? WHERE customerID=?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setBoolean(1, customer.isIsDelete()); // isDelete
            pre.setInt(2, customer.getCustomerID()); // customerID
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
            pre.setBigDecimal(5, customer.getTotalDebt()); // totalDebt
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
        String sql = "INSERT INTO customers (name, email, phone, address, totalDebt, createAt, updateAt, createBy, isDelete,storeID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, customer.getName()); // name
            pre.setString(2, customer.getEmail()); // email
            pre.setString(3, customer.getPhone()); // phone
            pre.setString(4, customer.getAddress()); // address
            pre.setBigDecimal(5, customer.getTotalDebt()); // totalDebt
            pre.setString(6, customer.getCreateAt()); // createAt
            pre.setString(7, customer.getUpdateAt()); // updateAt
            pre.setInt(8, customer.getCreateBy()); // createBy
            pre.setBoolean(9, customer.isIsDelete()); // isDelete
            pre.setInt(10, customer.getStoreID()); // isDelete

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
                String customerName = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                BigDecimal totalDebt = rs.getBigDecimal("totalDebt");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");
                int storeID = rs.getInt("storeID");

                Customers customer = new Customers(customerID, customerName, email, phone, address, totalDebt, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, storeID);
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
                String customerName = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                BigDecimal totalDebt = rs.getBigDecimal("totalDebt");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");
                int storeID = rs.getInt("storeID");

                Customers customer = new Customers(customerID, customerName, email, phone, address, totalDebt, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, storeID);
                list.add(customer); // Thêm vào danh sách thay vì in ra
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list; // Trả về danh sách khách hàng
    }

    public List<Customers> listCustomersByRole(int ID, int userRole) {
        List<Customers> list = new ArrayList<>();
        String sql = "";

        sql = "SELECT c.customerID, c.name, c.email, c.phone, \n"
                + "    c.address, c.totalDebt,c.storeID, \n"
                + "	FORMAT(c.createAt, 'yyyy-MM-dd HH:mm:ss') AS createAt, \n"
                + "    FORMAT(c.updateAt, 'yyyy-MM-dd HH:mm:ss') AS updateAt, \n"
                + "    c.createBy, c.isDelete, c.deleteAt, c.deleteBy\n"
                + "FROM customers c\n"
                + "JOIN users u ON c.createBy = u.ID\n"
                + "WHERE c.storeID = ?  ORDER BY c.customerID DESC; ";

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, ID);

            ResultSet rs = pre.executeQuery();
            while (rs.next()) {

                int customerID = rs.getInt("customerID");
                String customerName = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                BigDecimal totalDebt = rs.getBigDecimal("totalDebt");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");
                int storeID = rs.getInt("storeID");

                // Ẩn số điện thoại nếu role là "staff"
                if (userRole == 3) {
                    phone = maskPhoneNumber(phone);
                }

                Customers customer = new Customers(customerID, customerName, email, phone, address, totalDebt, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, storeID);
                list.add(customer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    private String maskPhoneNumber(String phone) {
        if (phone != null && phone.length() >= 6) {
            return phone.substring(0, 3) + "*****" + phone.substring(phone.length() - 3);
        }
        return phone; // Trả về số gốc nếu không đủ độ dài
    }

    public List<Customers> listCustomersByRoleSearchName(int storeIDa, String name, int userRole) {
        List<Customers> list = new ArrayList<>();
        String sql = "";

        sql = " SELECT c.customerID, c.name, c.email, c.phone, \n"
                + "    c.address, c.totalDebt,c.storeID, \n"
                + "	FORMAT(c.createAt, 'yyyy-MM-dd HH:mm:ss') AS createAt, \n"
                + "    FORMAT(c.updateAt, 'yyyy-MM-dd HH:mm:ss') AS updateAt, \n"
                + "    c.createBy, c.isDelete, c.deleteAt, c.deleteBy\n"
                + "FROM customers c\n"
                + "JOIN users u ON c.createBy = u.ID\n"
                + "WHERE c.storeID = ? "
                + name + " ORDER BY c.customerID DESC";

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, storeIDa);

            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int customerID = rs.getInt("customerID");
                String customerName = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                BigDecimal totalDebt = rs.getBigDecimal("totalDebt");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");
                int storeID = rs.getInt("storeID");

                // Ẩn số điện thoại nếu role là "staff"
                if (userRole == 3) {
                    phone = maskPhoneNumber(phone);
                }

                Customers customer = new Customers(customerID, customerName, email, phone, address, totalDebt, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, storeID);
                list.add(customer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

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

