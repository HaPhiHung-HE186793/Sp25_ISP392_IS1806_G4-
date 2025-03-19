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
        
        // Lưu storeID
        int storeID = 0;
        double[] monthlyTotals = new double[12]; // Mảng lưu tổng doanh thu cho từng tháng
        
        while (rs.next()) {
            int month = rs.getInt(1); // Lấy tháng
            double totalAmount = rs.getDouble(2); // Lấy tổng doanh thu
            storeID = rs.getInt("storeID"); // Lấy storeID

            monthlyTotals[month - 1] = totalAmount; // Lưu tổng doanh thu vào mảng
        }

        // Tạo đối tượng Chart cho từng tháng, bao gồm cả tháng không có dữ liệu
        for (int i = 0; i < 12; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, i); // Tháng bắt đầu từ 0
            calendar.set(Calendar.YEAR, year); // Đặt năm đã chọn
            calendar.set(Calendar.DAY_OF_MONTH, 1); // Đặt ngày bất kỳ (ví dụ: ngày 1)
            Date dateChart = calendar.getTime();

            // Nếu không có doanh thu cho tháng, sử dụng 0
            Chart chart = new Chart(dateChart, monthlyTotals[i], storeID); 
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

                    // Lưu storeID
                    int storeID = 0;
                    double[] dailyTotals = new double[31]; // Mảng lưu tổng doanh thu cho từng ngày

                    while (rs.next()) {
                        int day = rs.getInt(1); // Lấy ngày
                        double totalAmount = rs.getDouble(2); // Lấy tổng doanh thu
                        storeID = rs.getInt("storeID"); // Lấy storeID

                        dailyTotals[day - 1] = totalAmount; // Lưu tổng doanh thu vào mảng
                    }

                    // Tính số ngày trong tháng
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month - 1); // Tháng bắt đầu từ 0
                    int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                    // Tạo đối tượng Chart cho từng ngày, bao gồm cả ngày không có dữ liệu
                    for (int i = 1; i <= daysInMonth; i++) {
                        calendar.set(Calendar.DAY_OF_MONTH, i);
                        Date dateChart = calendar.getTime();

                        // Nếu không có doanh thu cho ngày, sử dụng 0
                        Chart chart = new Chart(dateChart, (i <= dailyTotals.length ? dailyTotals[i - 1] : 0), storeID); 
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