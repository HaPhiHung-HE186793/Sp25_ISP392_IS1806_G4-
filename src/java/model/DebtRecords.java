/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class DebtRecords {
    private int debtID;
    private int customerID;
    private int orderID;
    private double amount; 
    private int paymentStatus;
    private String createAt; 
    private String updateAt; 
    private int createBy;
    private boolean isDelete; 
    private String deleteAt; 
    private int deleteBy;

    public DebtRecords(int debtID, int customerID, int orderID, double amount, int paymentStatus, String createAt, String updateAt, int createBy, boolean isDelete, String deleteAt, int deleteBy) {
        this.debtID = debtID;
        this.customerID = customerID;
        this.orderID = orderID;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createBy = createBy;
        this.isDelete = isDelete;
        this.deleteAt = deleteAt;
        this.deleteBy = deleteBy;
    }

    public DebtRecords(int customerID, int orderID, double amount, int paymentStatus, String createAt, String updateAt, int createBy, boolean isDelete, String deleteAt, int deleteBy) {
        this.customerID = customerID;
        this.orderID = orderID;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createBy = createBy;
        this.isDelete = isDelete;
        this.deleteAt = deleteAt;
        this.deleteBy = deleteBy;
    }
    
    

    public DebtRecords() {
    }

    public int getDebtID() {
        return debtID;
    }

    public void setDebtID(int debtID) {
        this.debtID = debtID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public boolean isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(String deleteAt) {
        this.deleteAt = deleteAt;
    }

    public int getDeleteBy() {
        return deleteBy;
    }

    public void setDeleteBy(int deleteBy) {
        this.deleteBy = deleteBy;
    }

    
    
}
