/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import DAL.DBContext;
/**
 *
 * @author ADMIN
 */
import model.OrderPaper;
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
public class DAOOrderPaper extends DBContext{
    public Vector<OrderPaper> getOrderPaper(String sql) {
    Vector<OrderPaper> vector = new Vector<OrderPaper>();
    try {
        System.out.println("Executing SQL: " + sql); // Log the SQL command 
        Statement state = conn.createStatement();
        ResultSet rs = state.executeQuery(sql);
        
        while (rs.next()) {
            // Retrieve data from ResultSet and assign to the new variables
            int orderID = rs.getInt("orderID");
            int orderitemID = rs.getInt("orderitemID");
            String productName = rs.getString("productName");
            Double price = rs.getDouble("price");
            int quantity = rs.getInt("quantity");
            String createAt = rs.getString("createAt");
            String name = rs.getString("name");
            String userName = rs.getString("userName");
            int orderType = rs.getInt("orderType");
            int storeID = rs.getInt("storeID");
            Double unitPrice = rs.getDouble("unitPrice");
            Double paidAmount = rs.getDouble("paidAmount");
            Double totalAmount = rs.getDouble("totalAmount");
            
            // Create OrderPaper object (assuming it has a constructor that accepts these parameters)
            OrderPaper orderPaper = new OrderPaper(orderID, orderitemID, productName, price, quantity, createAt, name, userName, orderType, storeID, unitPrice, paidAmount, totalAmount);
            vector.add(orderPaper);
        }
    } catch (SQLException ex) {
        System.err.println("SQL Error in getOrderPaper: " + ex.getMessage());
        ex.printStackTrace(); // Log SQL error
    }
    return vector;
}
}
