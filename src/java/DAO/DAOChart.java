/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBContext;
import model.Chart;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author ADMIN
 */
public class DAOChart extends DBContext {

    public Vector<Chart> getYearlyChart(String sql) {
        Vector<Chart> vector = new Vector<>();
        try {
            System.out.println("Executing SQL: " + sql);
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int year = rs.getInt(1); // Lấy năm
                double totalAmount = rs.getDouble(2); // Lấy tổng doanh thu
                int storeID = rs.getInt("storeID"); // Lấy storeID

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                Date dateChart = calendar.getTime();

                Chart chart = new Chart(dateChart, totalAmount, storeID);
                vector.add(chart);
            }
        } catch (SQLException ex) {
            System.err.println("SQL Error in getYearlyChart: " + ex.getMessage());
            ex.printStackTrace();
        }
        System.out.println("Number of records retrieved (Yearly): " + vector.size());
        return vector;
    }
    public Vector<Chart> getMonthlyChart(String sql, int year) {
    Vector<Chart> vector = new Vector<>();
    try {
        System.out.println("Executing SQL: " + sql);
        Statement state = conn.createStatement();
        ResultSet rs = state.executeQuery(sql);
        while (rs.next()) {
            int month = rs.getInt(1); // Lấy tháng
            double totalAmount = rs.getDouble(2); // Lấy tổng doanh thu
            int storeID = rs.getInt("storeID"); // Lấy storeID

            // Tạo đối tượng Chart với tháng thực tế
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, month - 1); // Tháng bắt đầu từ 0
            calendar.set(Calendar.YEAR, year); // Đặt năm đã chọn
            calendar.set(Calendar.DAY_OF_MONTH, 1); // Đặt ngày bất kỳ (ví dụ: ngày 1)
            Date dateChart = calendar.getTime();

            Chart chart = new Chart(dateChart, totalAmount, storeID); 
            vector.add(chart);
        }
    } catch (SQLException ex) {
        System.err.println("SQL Error in getMonthlyChart: " + ex.getMessage());
        ex.printStackTrace();
    }
    System.out.println("Number of records retrieved (Monthly): " + vector.size());
    return vector;
}
    public Vector<Chart> getDailyChart(String sql, int year, int month) {
    Vector<Chart> vector = new Vector<>();
    try {
        System.out.println("Executing SQL: " + sql);
        Statement state = conn.createStatement();
        ResultSet rs = state.executeQuery(sql);
        while (rs.next()) {
            int day = rs.getInt(1); // Lấy ngày
            double totalAmount = rs.getDouble(2); // Lấy tổng doanh thu
            int storeID = rs.getInt("storeID"); // Lấy storeID

            // Tạo đối tượng Chart với ngày thực tế
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.MONTH, month - 1); // Tháng bắt đầu từ 0
            calendar.set(Calendar.YEAR, year); // Đặt năm đã chọn
            Date dateChart = calendar.getTime();

            Chart chart = new Chart(dateChart, totalAmount, storeID); 
            vector.add(chart);
        }
    } catch (SQLException ex) {
        System.err.println("SQL Error in getDailyChart: " + ex.getMessage());
        ex.printStackTrace();
    }
    System.out.println("Number of records retrieved (Daily): " + vector.size());
    return vector;
}

//    public Vector<Chart> getMonthlyChart(String sql) {
//        Vector<Chart> vector = new Vector<>();
//        try {
//            System.out.println("Executing SQL: " + sql);
//            Statement state = conn.createStatement();
//            ResultSet rs = state.executeQuery(sql);
//            while (rs.next()) {
//                int month = rs.getInt(1); // Lấy tháng
//                double totalAmount = rs.getDouble(2); // Lấy tổng doanh thu
//                int storeID = rs.getInt("storeID"); // Lấy storeID
//
//                Chart chart = new Chart(new Date(), totalAmount, storeID); // Sử dụng ngày hiện tại
//                vector.add(chart);
//            }
//        } catch (SQLException ex) {
//            System.err.println("SQL Error in getMonthlyChart: " + ex.getMessage());
//            ex.printStackTrace();
//        }
//        System.out.println("Number of records retrieved (Monthly): " + vector.size());
//        return vector;
//    }
//
//    public Vector<Chart> getDailyChart(String sql) {
//        Vector<Chart> vector = new Vector<>();
//        try {
//            System.out.println("Executing SQL: " + sql);
//            Statement state = conn.createStatement();
//            ResultSet rs = state.executeQuery(sql);
//            while (rs.next()) {
//                int day = rs.getInt(1); // Lấy ngày
//                double totalAmount = rs.getDouble(2); // Lấy tổng doanh thu
//                int storeID = rs.getInt("storeID"); // Lấy storeID
//
//                Chart chart = new Chart(new Date(), totalAmount, storeID); // Sử dụng ngày hiện tại
//                vector.add(chart);
//            }
//        } catch (SQLException ex) {
//            System.err.println("SQL Error in getDailyChart: " + ex.getMessage());
//            ex.printStackTrace();
//        }
//        System.out.println("Number of records retrieved (Daily): " + vector.size());
//        return vector;
//    }
}