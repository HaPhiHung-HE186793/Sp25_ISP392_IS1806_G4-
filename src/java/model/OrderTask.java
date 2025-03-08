/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

/**
 *
 * @author Admin
 */
public class OrderTask {
    private String orderType;
     private int customerId;
    private int userId;
    private double totalAmount;
    private int porter;
    private String status;
    private double paidAmount;
    private double debtAmount;
    
    
    private List<OrderItems> orderDetails;

    public OrderTask() {
    }

    public OrderTask(String orderType, int customerId, int userId, double totalAmount, int porter, String status, double paidAmount, double debtAmount, List<OrderItems> orderDetails) {
        this.orderType = orderType;
        this.customerId = customerId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.porter = porter;
        this.status = status;
        this.paidAmount = paidAmount;
        this.debtAmount = debtAmount;
        this.orderDetails = orderDetails;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getPorter() {
        return porter;
    }

    public void setPorter(int porter) {
        this.porter = porter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getDebtAmount() {
        return debtAmount;
    }

    public void setDebtAmount(double debtAmount) {
        this.debtAmount = debtAmount;
    }

    public List<OrderItems> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderItems> orderDetails) {
        this.orderDetails = orderDetails;
    }

   
    
    
    
    
    
}
