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
import Entity.customers;
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
    public Vector<customers> getCustomers(String sql) {
    Vector<customers> vector = new Vector<customers>();
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
            String createBy = rs.getString("createBy");
            int isDelete = rs.getInt("isDelete");  // Chuyển sang boolean
            String deleteAt = rs.getString("deleteAt");
            String deleteBy = rs.getString("deleteBy");

            customers customer = new customers(customerID, name, email, phone, address, totalDebt, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
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
     public int updateCustomer(customers customer) {
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
        pre.setString(8, customer.getCreateBy()); // createBy
        pre.setInt(9, customer.getIsDelete()); // isDelete
        pre.setString(10, customer.getDeleteAt()); // deleteAt
        pre.setString(11, customer.getDeleteBy()); // deleteBy
        pre.setInt(12, customer.getCustomerID()); // customerID
        n = pre.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(DAOCustomers.class.getName()).log(Level.SEVERE, null, ex);
    }
    return n;
}
     public int insertCustomer(customers customer) {
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
        pre.setString(8, customer.getCreateBy()); // createBy
        pre.setInt(9, customer.getIsDelete()); // isDelete
        pre.setString(10, customer.getDeleteAt()); // deleteAt
        pre.setString(11, customer.getDeleteBy()); // deleteBy
        n = pre.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(DAOCustomers.class.getName()).log(Level.SEVERE, null, ex);
    }
    return n;
}
//     public void listAll() {
//    String sql = "SELECT * FROM customers"; // Cập nhật tên bảng
//    try {
//        Statement state = conn.createStatement();
//        ResultSet rs = state.executeQuery(sql);
//        while (rs.next()) {
//            int customerID = rs.getInt("customerID");
//            String name = rs.getString("name");
//            String email = rs.getString("email");
//            String phone = rs.getString("phone");
//            String address = rs.getString("address");
//            double totalDebt = rs.getDouble("totalDebt"); // Sử dụng double cho tổng nợ
//            String createAt = rs.getString("createAt");
//            String updateAt = rs.getString("updateAt");
//            String createBy = rs.getString("createBy");
//            int isDelete = rs.getInt("isDelete"); // Giả sử isDelete là kiểu int
//            String deleteAt = rs.getString("deleteAt");
//            String deleteBy = rs.getString("deleteBy");
//
//            customers customer = new customers(customerID, name, email, phone, address, totalDebt, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
//            System.out.println(customer);
//        }
//    } catch (SQLException ex) {
//        ex.printStackTrace();
//    } 
//}
//     
     
     public List<customers> listAll() {
    List<customers> list = new ArrayList<>();
    String sql = "SELECT * FROM customers"; // Cập nhật tên bảng
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
            String createBy = rs.getString("createBy");
            int isDelete = rs.getInt("isDelete"); // Giả sử isDelete là kiểu int
            String deleteAt = rs.getString("deleteAt");
            String deleteBy = rs.getString("deleteBy");

            customers customer = new customers(customerID, name, email, phone, address, totalDebt, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
            list.add(customer); // Thêm vào danh sách thay vì in ra
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } 
    return list; // Trả về danh sách khách hàng
}

     
     public static void main(String[] args) {
    DAOCustomers dao = new DAOCustomers();

    // 1. Thêm một khách hàng mới
    customers newCustomer = new customers( "Nguyễn Văn C", "nguyenvanC@gmail.com", "0123456789", "123 Đường ABC", 1000.0, "2023-01-01", "2023-01-01", "1", 0, null, null);
    int insertResult = dao.insertCustomer(newCustomer);
    System.out.println("Insert result: " + insertResult);

    // 2. Cập nhật thông tin khách hàng
//    customers customerToUpdate = new customers(3, "Nguyễn Thu B", "nguyenvanb@gmail.com", "0987654321", "456 Đường XYZ", 2000.0, "2023-01-02", "2023-01-02", "1", 0, null, null);
//    int updateResult = dao.updateCustomer(customerToUpdate);
//    System.out.println("Update result: " + updateResult);

    // 3. Xóa một khách hàng
//    int removeResult = dao.removeCustomer(2); // Giả sử ID của khách hàng cần xóa là 1
//    System.out.println("Remove result: " + removeResult);

    // 4. Liệt kê tất cả khách hàng
    dao.listAll();
}
}
