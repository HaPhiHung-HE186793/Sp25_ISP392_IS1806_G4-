/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class Chart {
//    private int orderID;
//    private String name;
//    private String userName; 
//    private String createAt; 
//    private String updateAt;    
//    private int porter; 
//    private String status;
//    private int shopID;
//    private String productName;
//    private double price;
    private Date createAt;
    private double totalAmount;
    private int storeID;

    public Chart() {
    }

    public Chart(Date createAt, double totalAmount, int storeID) {
        this.createAt = createAt;
        this.totalAmount = totalAmount;
        this.storeID = storeID;
    }
    
    public Chart(Date createAt, double totalAmount) {
        this.createAt = createAt;
        this.totalAmount = totalAmount;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }
    
    
    public int getYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createAt);
        return calendar.get(Calendar.YEAR);
    }
    public int getMonth() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(createAt);
    return calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0, cộng thêm 1 để có tháng thực tế
}

public int getDay() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(createAt);
    return calendar.get(Calendar.DAY_OF_MONTH);
}
    
}
