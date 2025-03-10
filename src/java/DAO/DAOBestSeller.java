/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBContext;


import model.BestSeller;
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
/**
 *
 * @author ADMIN
 */
public class DAOBestSeller extends DBContext{
    public Vector<BestSeller> getCustomerSeller(String sql) {
    Vector<BestSeller> vector = new Vector<BestSeller>();
    try {
        System.out.println("Executing SQL: " + sql); // Ghi lại câu lệnh SQL
        Statement state = conn.createStatement();
        ResultSet rs = state.executeQuery(sql);
        while (rs.next()) {           
            String name = rs.getString("name");
            double price = rs.getDouble("price");            
            BestSeller bestSeller = new BestSeller(name, price);
            vector.add(bestSeller);
        }
    } catch (SQLException ex) {
        System.err.println("SQL Error in getShowOrder: " + ex.getMessage());
        ex.printStackTrace(); // Ghi lại lỗi SQL
    }
    return vector;
}
    public Vector<BestSeller> getProductSeller(String sql) {
    Vector<BestSeller> vector = new Vector<BestSeller>();
    try {
        System.out.println("Executing SQL: " + sql); // Ghi lại câu lệnh SQL
        Statement state = conn.createStatement();
        ResultSet rs = state.executeQuery(sql);
        while (rs.next()) {                     
            double price = rs.getDouble("price"); 
            String productName = rs.getString("productName");
            BestSeller bestSeller1 = new BestSeller(price, productName);
            vector.add(bestSeller1);
        }
    } catch (SQLException ex) {
        System.err.println("SQL Error in getShowOrder: " + ex.getMessage());
        ex.printStackTrace(); // Ghi lại lỗi SQL
    }
    return vector;
}
    public static void main(String[] args) {
        DAOBestSeller dao = new DAOBestSeller();

        // Câu lệnh SQL cho sản phẩm bán chạy nhất
        String bestSellingProductSql = "SELECT TOP 1 i.productName, SUM(i.price) AS price " +
                                        "FROM orders o " +
                                        "JOIN users u ON u.ID = o.userID " +
                                        "JOIN OrderItems i ON o.orderID = i.orderID " +
                                        "WHERE o.orderType = 1 AND u.storeID = 1 " +
                                        "GROUP BY i.productName " +
                                        "ORDER BY COUNT(i.productName) DESC";

        // Lấy danh sách sản phẩm bán chạy nhất
        Vector<BestSeller> bestSellingProduct = dao.getProductSeller(bestSellingProductSql);
        
        // In kết quả sản phẩm bán chạy nhất
        System.out.println("Sản phẩm bán chạy nhất:");
        for (BestSeller product : bestSellingProduct) {
            System.out.println("Tên sản phẩm: " + product.getProductName() + ", Tổng doanh thu: " + product.getPrice());
        }

        // Câu lệnh SQL cho khách hàng mua nhiều nhất
        String bestCustomerSql = "SELECT TOP 1 c.name, SUM(i.price) AS price " +
                                  "FROM orders o " +
                                  "JOIN users u ON u.ID = o.userID " +
                                  "JOIN OrderItems i ON o.orderID = i.orderID " +
                                  "JOIN customers c ON o.customerID = c.customerID " +
                                  "WHERE o.orderType = 1 AND u.storeID = 1 " +
                                  "GROUP BY c.name " +
                                  "ORDER BY COUNT(c.name) DESC";

        // Lấy danh sách khách hàng mua nhiều nhất
        Vector<BestSeller> bestCustomer = dao.getCustomerSeller(bestCustomerSql);
        
        // In kết quả khách hàng mua nhiều nhất
        System.out.println("Khách hàng mua nhiều nhất:");
        for (BestSeller customer : bestCustomer) {
            System.out.println("Tên khách hàng: " + customer.getName() + ", Tổng doanh thu: " + customer.getPrice());
        }
    }
}
